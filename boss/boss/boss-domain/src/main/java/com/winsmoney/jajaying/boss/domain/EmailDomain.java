package com.winsmoney.jajaying.boss.domain;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.winsmoney.jajaying.boss.domain.model.Mail;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮件服务
 *
 * @author howard he
 * @create 2018/7/11 10:42
 */
@Component
public class EmailDomain implements InitializingBean {

    private WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(EmailDomain.class);
    private JavaMailSender mailSender;
    @Value("${mail.username:crowdadmin@jajaying.com}")
    private String username;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public void afterPropertiesSet() throws Exception {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qiye.163.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtp");
        mailSender.setUsername(username);
        mailSender.setPassword("Aa1234");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        this.mailSender = mailSender;
    }

    public void send(String[] to, String subject, String text, boolean html) {
        Mail mail = new Mail();
        mail.setFrom(username);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(text);
        mail.setHtml(html);
        send(mail);
    }

    public void send(final Mail mail) {
        validateMail(mail);
        String from = mail.getFrom();
        if (Strings.isNullOrEmpty(from)) {
            from = username;
        }
        Boolean html = mail.getHtml();
        final boolean htmlFinal = html == null ? false : html;
        final String fromFinal = from;
        send(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, Charset.defaultCharset().displayName());
                helper.setFrom(fromFinal);
                helper.setTo(mail.getTo());
                if (mail.getCc() != null && mail.getCc().length > 0) {
                    helper.setCc(mail.getCc());
                }
                if (mail.getBcc() != null && mail.getBcc().length > 0) {
                    helper.setBcc(mail.getBcc());
                }
                if (!Strings.isNullOrEmpty(mail.getSubject())) {
                    helper.setSubject(mail.getSubject());
                }
                if (!Strings.isNullOrEmpty(mail.getText())) {
                    helper.setText(mail.getText(), htmlFinal);
                }
            }
        });
    }

    private void validateMail(Mail mail) {
        if (mail == null) {
            String error = "邮件信息为null. [Mail]";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
        List<String> addressList = Lists.newArrayList();
        String[] to = mail.getTo();
        String[] cc = mail.getCc();
        String[] bcc = mail.getBcc();
        addressList.addAll(asList(to));
        addressList.addAll(asList(cc));
        addressList.addAll(asList(bcc));
        for (String address : addressList) {
            checkAddress(address);
        }
    }

    private void send(MimeMessagePreparator mimeMessagePreparator) {
        mailSender.send(mimeMessagePreparator);
    }

    private List<String> asList(String[] array) {
        List<String> list = Lists.newArrayList();
        if (array != null) {
            for (String item : array) {
                list.add(item);
            }
        }
        return list;
    }

    private void checkAddress(String email) {
        if (!validateEmailAddress(email)) {
            String error = "邮件地址不正确，email: " + email;
            logger.error(error);
            throw new IllegalArgumentException(error);
        }
    }

    private boolean validateEmailAddress(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}

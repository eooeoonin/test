package com.winsmoney.jajaying.boss.domain.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author howard he
 * @create 2018/7/11 10:11
 */
public class Mail implements Serializable {

    /**
     * 发送者
     */
    private String from;
    /**
     * 收件人列表
     */
    private String[] to;
    /**
     * 抄送人列表
     */
    private String[] cc;
    /**
     * 密送人列表
     */
    private String[] bcc;
    /**
     * 邮件标题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String text;

    /**
     * 是否为html格式内容
     */
    private Boolean html;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getHtml() {
        return html;
    }

    public void setHtml(Boolean html) {
        this.html = html;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mail{");
        sb.append("from='").append(from).append('\'');
        sb.append(", to=").append(Arrays.toString(to));
        sb.append(", cc=").append(Arrays.toString(cc));
        sb.append(", bcc=").append(Arrays.toString(bcc));
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", html=").append(html);
        sb.append('}');
        return sb.toString();
    }
}

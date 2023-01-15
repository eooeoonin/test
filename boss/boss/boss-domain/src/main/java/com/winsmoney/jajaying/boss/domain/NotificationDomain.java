package com.winsmoney.jajaying.boss.domain;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.trade.service.request.BorrowReqVo;
import com.winsmoney.jajaying.trade.service.response.BorrowResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 通知服务
 *
 * @author howard he
 * @create 2018/7/24 09:57
 */
@Component
public class NotificationDomain {

    private WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(NotificationDomain.class);

    @Autowired
    private StaffDomain staffDomain;
    @Autowired
    private EmailDomain emailDomain;

    /**
     * 发送邮件
     *
     * @param roleId
     * @param borrow
     */
    public void sendEmail(String roleId, BorrowResVo borrow) {
        Staff condition = new Staff();
        condition.setRoleId(roleId);
        List<Staff> staffList = staffDomain.listStaff(condition);
        if (staffList == null || staffList.isEmpty()) {
            logger.warn("员工列表为空，roleId: {}", roleId);
            return;
        }
        List<String> emailList = Lists.newArrayList();
        for (Staff staff : staffList) {
            String email = staff.getEmail();
            if (Strings.isNullOrEmpty(email)) {
                logger.info("该员工的邮箱为空, 员工id: {}, 员工姓名: {}, 员工email: {}", staff.getId(), staff.getRealName(), staff.getEmail());
                continue;
            }
            emailList.add(email);
        }
        if (emailList.isEmpty()) {
            logger.warn("员工邮件列表为空, roleId: {}", roleId);
            return;
        }
        String subject = "您有新的借款申请【" + borrow.getBorrowTitle() + "】需要审批";
        String text = "您有新的借款申请【" + borrow.getBorrowTitle() + "】需要审批\n";
        text += "用户姓名：" + borrow.getBorrowUserName() + "\n";
        text += "用户ID：" + borrow.getBorrowUserCode() + "\n";
        text += "已到您的审核流程，请尽快审核。";
        try {
            emailDomain.send(emailList.toArray(new String[emailList.size()]), subject, text, false);
        } catch (Exception e) {
            logger.error("发送邮件出现异常, emailList: " + emailList, e);
        }
    }

}

package com.winsmoney.jajaying.boss.service;

import com.winsmoney.jajaying.settlecore.service.IAuditLogService;
import com.winsmoney.jajaying.settlecore.service.IWithdrawService;
import com.winsmoney.jajaying.settlecore.service.enums.StatusEnum;
import com.winsmoney.jajaying.settlecore.service.request.AuditLogReqVo;
import com.winsmoney.jajaying.settlecore.service.request.WithdrawReqVo;
import com.winsmoney.jajaying.settlecore.service.response.AuditLogResVo;
import com.winsmoney.jajaying.settlecore.service.response.SettlecoreCommonResult;
import com.winsmoney.jajaying.settlecore.service.response.WithdrawResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.Money;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.winsmoney.jajaying.settlecore.service.enums.AuditNode.firstNode;
import static com.winsmoney.jajaying.settlecore.service.enums.AuditOper.AUDIT_NEXT;

/**
 * ClassName: AuditLog
 * Description:  服务接口测试
 * date: 2016-07-28 03:06:53
 * @author: CodeCreator
 */
@ContextConfiguration("classpath:spring/boss-bean.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AuditLogServiceTest {

    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AuditLogServiceTest.class);

//    @Autowired
//    private IAuditLogService auditLogService;

    @Autowired
    private IAuditLogService auditLogService;

    @Autowired
    private IWithdrawService withdrawService;

    @Test
    public void exeAudit() {
        AuditLogReqVo auditLogReqVo = new AuditLogReqVo();
        auditLogReqVo.setIds(getData());
        auditLogReqVo.setEditedBy("操作人");
        auditLogReqVo.setComment("审核");
        auditLogReqVo.setCurrentStatus(Integer.valueOf(StatusEnum.INIT.getCode()));
        auditLogReqVo.setAuditOperEnum(AUDIT_NEXT);
        auditLogReqVo.setCurrentNode(firstNode);
        SettlecoreCommonResult<AuditLogResVo> resVoSettlecoreCommonResult = auditLogService.audit(auditLogReqVo);
        logger.info("结果:{}", resVoSettlecoreCommonResult);

        while (true) {
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getData() {
        List<String> ids = new ArrayList();
        ids.add("20161109C2256000000000000000000l");
//        ids.add("20161109C2256000000000000000000c");
//        ids.add("2016072899Withdraw5000001");
//        ids.add("2016072899Withdraw9000001");
//        ids.add("2016072999Withdraw10000001");
       // ids.add("2016072999Withdraw11000001");

        return ids;
    }


    @Test
    public void testInsertWithdraw() {
        WithdrawReqVo withdrawReqVo = new WithdrawReqVo();
        withdrawReqVo.setAmount(new Money(100));
        withdrawReqVo.setTradeDate(new Date(System.currentTimeMillis()));
        withdrawReqVo.setTradeId("1000024");
        withdrawReqVo.setUserCode("张三");
        withdrawReqVo.setOrigin("移动");
        withdrawReqVo.setBankCode("BBC");
        withdrawReqVo.setAccountType("2");
        //withdrawReqVo.setComment("审核通过");
        SettlecoreCommonResult<WithdrawResVo> result = withdrawService.insertWithdraw(withdrawReqVo);
        logger.info("录入结果：{}", result);
    }

}
package com.winsmoney.jajaying.boss.controller.settlecore;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.settlecore.service.*;
import com.winsmoney.jajaying.settlecore.service.enums.AuditNode;
import com.winsmoney.jajaying.settlecore.service.enums.AuditOper;
import com.winsmoney.jajaying.settlecore.service.enums.StatusEnum;
import com.winsmoney.jajaying.settlecore.service.request.AuditLogReqVo;
import com.winsmoney.jajaying.settlecore.service.request.WithdrawReqVo;
import com.winsmoney.jajaying.settlecore.service.response.AuditLogResVo;
import com.winsmoney.jajaying.settlecore.service.response.SettlecoreCommonResult;
import com.winsmoney.jajaying.settlecore.service.response.WithdrawDetailResVo;
import com.winsmoney.jajaying.settlecore.service.response.WithdrawResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 委托付款
 */
@Controller
@RequestMapping("payment/payment_audit")
public class WithDrawController {

    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(WithDrawController.class);

    @Autowired
    private IWithdrawService withdrawService;
    @Autowired
    private IAuditLogService auditLogService;
    @Autowired
    private IVerificationFailService verificationFailService;
    @Autowired
    private IRejectService rejectService;
    @Autowired
    private IRetryService retryService;

    SensitiveInfoUtils sensitiveInfoUtils = new SensitiveInfoUtils();

    /*
     * 待审核接口 查询第一节点 审核状态为1 订单状态为0
     */
    @SuppressWarnings("static-access")
	@RequestMapping(value = "/queryAuditData", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<WithdrawDetailResVo> queryAuditData(WithdrawDetailResVo withdrawDetailResVo, String pageNo, String pageSize) {

        withdrawDetailResVo.setStatus(StatusEnum.INIT.getCode());
        withdrawDetailResVo.setAuditNode(AuditNode.firstNode.getCode());
        logger.info("接口{}入参：" + JSONObject.toJSONString(withdrawDetailResVo));
        SettlecoreCommonResult<PageInfo<WithdrawDetailResVo>> result = withdrawService.pageListDetail(withdrawDetailResVo,
                (int) Double.parseDouble(pageNo),
                (int) Double.parseDouble(pageSize));
        logger.info("接口{}出参：" +
                JSONObject.toJSONString(result));
        PageInfo<WithdrawDetailResVo> list = result.getBusinessObject();

        for (int i = 0; i < list.getSize(); i++) {
            if (list.getList().get(i).getMobile() != null)
                list.getList().get(i).setMobile(sensitiveInfoUtils.mobilePhone(list.getList().get(i).getMobile()));
            if (list.getList().get(i).getBankNo() != null)
            	list.getList().get(i).setBankNo(sensitiveInfoUtils.bankCard(list.getList().get(i).getBankNo()));
            if (list.getList().get(i).getAccountNo() != null)
            	list.getList().get(i).setAccountNo(sensitiveInfoUtils.bankCard(list.getList().get(i).getAccountNo()));
        }
        return list;

    }

    /*
     * 查询第二节点 已退回
     */
    @RequestMapping(value = "/queryCallBackErrorData", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<WithdrawDetailResVo> queryCallBackErrorData(WithdrawDetailResVo withdrawDetailResVo, String pageNo, String pageSize) {

        withdrawDetailResVo.setAuditNode(AuditNode.thirdNode.getCode());
        SettlecoreCommonResult<PageInfo<WithdrawDetailResVo>> result = withdrawService.pageListDetail(withdrawDetailResVo,
                (int) Double.parseDouble(pageNo),
                (int) Double.parseDouble(pageSize));
        PageInfo<WithdrawDetailResVo> list = result.getBusinessObject();
        for (WithdrawDetailResVo WithdrawDetailResVo : list.getList()) {
            if (WithdrawDetailResVo.getMobile() != null) {
                WithdrawDetailResVo.setMobile(sensitiveInfoUtils.mobilePhone(WithdrawDetailResVo.getMobile()));
            }
            if (WithdrawDetailResVo.getAccountNo() != null)
            	WithdrawDetailResVo.setAccountNo(sensitiveInfoUtils.bankCard(WithdrawDetailResVo.getAccountNo()));
        }
        return list;
    }

    /*
     * 查询第三节点成功 已成功
     */
    @RequestMapping(value = "/querySuccessHistroyData", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<WithdrawResVo> querySuccessHistroyData(WithdrawReqVo withdrawReqVo, String pageNo, String pageSize) {
        withdrawReqVo.setStatus(StatusEnum.SUCCESS.getCode());
        SettlecoreCommonResult<PageInfo<WithdrawResVo>> result = withdrawService.pageListWithdraw(withdrawReqVo,
                (int) Double.parseDouble(pageNo),
                (int) Double.parseDouble(pageSize));
        PageInfo<WithdrawResVo> list = result.getBusinessObject();
        for (WithdrawResVo WithdrawDetailResVo : list.getList()) {
            if (WithdrawDetailResVo.getMobile() != null) {
                WithdrawDetailResVo.setMobile(sensitiveInfoUtils.mobilePhone(WithdrawDetailResVo.getMobile()));
            }
            if (WithdrawDetailResVo.getAccountNo() != null)
            	WithdrawDetailResVo.setAccountNo(sensitiveInfoUtils.bankCard(WithdrawDetailResVo.getAccountNo()));
        }
        return list;
    }

    /*
     * 查询第三节点失败 已失败
     */
    @RequestMapping(value = "/queryDefeatedHistroyData", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<WithdrawResVo> queryDefeatedHistroyData(
            WithdrawReqVo withdrawReqVo, String pageNo, String pageSize) throws Exception {

        withdrawReqVo.setStatus(StatusEnum.FAIL.getCode());
        SettlecoreCommonResult<PageInfo<WithdrawResVo>> result = withdrawService.pageListWithdraw(withdrawReqVo,
                (int) Double.parseDouble(pageNo),
                (int) Double.parseDouble(pageSize));
        PageInfo<WithdrawResVo> list = result.getBusinessObject();
        for (WithdrawResVo WithdrawDetailResVo : list.getList()) {
            if (WithdrawDetailResVo.getMobile() != null) {
                WithdrawDetailResVo.setMobile(sensitiveInfoUtils.mobilePhone(WithdrawDetailResVo.getMobile()));
            }
            if (WithdrawDetailResVo.getAccountNo() != null)
            	WithdrawDetailResVo.setAccountNo(sensitiveInfoUtils.bankCard(WithdrawDetailResVo.getAccountNo()));
        }
        return list;
    }

    @RequestMapping(value = "/queryAuditedData", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<WithdrawDetailResVo> queryAuditedData(WithdrawDetailResVo withdrawDetailResVo, String pageNo, String pageSize) {

        withdrawDetailResVo.setStatus(StatusEnum.INIT.getCode());
        withdrawDetailResVo.setAuditNode(AuditNode.secondNode.getCode());
        SettlecoreCommonResult<PageInfo<WithdrawDetailResVo>> result = withdrawService.pageListDetail(withdrawDetailResVo,
                (int) Double.parseDouble(pageNo),
                (int) Double.parseDouble(pageSize));

        PageInfo<WithdrawDetailResVo> list = result.getBusinessObject();
        for (int i = 0; i < list.getSize(); i++) {
            if (list.getList().get(i).getMobile() != null)
                list.getList().get(i).setMobile(sensitiveInfoUtils.mobilePhone(list.getList().get(i).getMobile()));
            if (list.getList().get(i).getAccountNo() != null)
            	list.getList().get(i).setAccountNo(sensitiveInfoUtils.bankCard(list.getList().get(i).getAccountNo()));
        }
        return list;

    }



    /*
     * 审核操作
     */
    @RequestMapping(value = "/auditData", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "通过提现申请")
    public String auditData(String[] ids, String bizCode, HttpServletRequest request) {
        String result = "success";
        AuditLogReqVo auditLogReqVo = new AuditLogReqVo();
        auditLogReqVo.setComment("审核通过");
        Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
        auditLogReqVo.setBizCode(bizCode);
        auditLogReqVo.setEditedBy(staffSession.getStaffName());
        List<String> list = new ArrayList<>();
        for (String id : ids)
            list.add(id);
        auditLogReqVo.setIds(list);
        try {
            logger.info("接口{}入参：" + JSONObject.toJSONString(auditLogReqVo), IAuditLogService.class);
            SettlecoreCommonResult<AuditLogResVo> select = auditLogService.audit(auditLogReqVo);
            logger.info("接口{}出参：" + JSONObject.toJSONString(select), IAuditLogService.class);
        } catch (Exception ex) {
            logger.error("{}", ex);
            result = "fail";
        }
        return result;
    }



    /*
     * 拒绝操作
     */
    @RequestMapping(value = "/refuseData", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "拒绝提现申请")
    public String refuseData(String[] ids, String bizCode, HttpServletRequest request) {
        String result = "success";
        AuditLogReqVo auditLogReqVo = new AuditLogReqVo();
        auditLogReqVo.setComment("审核失败");
        auditLogReqVo.setBizCode(bizCode);
        Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
        auditLogReqVo.setEditedBy(staffSession.getStaffName());
        List<String> list = new ArrayList<>();
        for (String id : ids)
            list.add(id);
        auditLogReqVo.setIds(list);
        try {
            logger.info("接口{}入参：" + JSONObject.toJSONString(auditLogReqVo), IAuditLogService.class);
            SettlecoreCommonResult<AuditLogResVo> select = rejectService.reject(auditLogReqVo);
            logger.info("接口{}出参：" + JSONObject.toJSONString(select), IAuditLogService.class);
        } catch (Exception ex) {
            logger.error("{}", ex);
            result = "fail";
        }
        return result;
    }


	/*
	 * 重新提现
	 */
    @RequestMapping(value = "/backSuccessData", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "重新提现申请")
    public String backSuccesstData(String id, String bizCode, HttpServletRequest request) {

        String result = "success";
        AuditLogReqVo auditLogReqVo = new AuditLogReqVo();
        List<String> list = new ArrayList<>();
        list.add(id);
        auditLogReqVo.setIds(list);
        auditLogReqVo.setBizCode(bizCode);
        auditLogReqVo.setComment("重试");
        Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
        auditLogReqVo.setEditedBy(staffSession.getStaffName());
        try {
            logger.info("接口{}入参：" + JSONObject.toJSONString(auditLogReqVo), IAuditLogService.class);
            SettlecoreCommonResult<AuditLogResVo> select = retryService.retry(auditLogReqVo);
            logger.info("接口{}出参：" + JSONObject.toJSONString(select), IAuditLogService.class);
        } catch (Exception e) {
            logger.error("{}", e);
            result = "fail";
        }
        return result;
    }

    /**
     * 确认失败操作
     * @param id
     * @param bizCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/backrefuseData", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "提现申请确认失败")
    public String backrefuseData(String id, String bizCode,String errorMsg,String errorCode, HttpServletRequest request) {
        String result = "success";
        AuditLogReqVo auditLogReqVo = new AuditLogReqVo();
        auditLogReqVo.setBizCode(bizCode);
        auditLogReqVo.setComment("确认失败");
        auditLogReqVo.setErrorMsg(errorMsg);
        auditLogReqVo.setErrorCode(errorCode);
        List<String> list = new ArrayList<>();
        list.add(id);
        auditLogReqVo.setIds(list);
        Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
        auditLogReqVo.setEditedBy(staffSession.getStaffName());
        try {
            logger.info("接口{}入参：" + JSONObject.toJSONString(auditLogReqVo), IAuditLogService.class);
            SettlecoreCommonResult<AuditLogResVo> select = verificationFailService.verificationFail(auditLogReqVo);
            logger.info("接口{}出参：" + JSONObject.toJSONString(select), IAuditLogService.class);
        } catch (Exception e) {
            logger.error("{}", e);
            result = "fail";
        }
        return result;
    }





}

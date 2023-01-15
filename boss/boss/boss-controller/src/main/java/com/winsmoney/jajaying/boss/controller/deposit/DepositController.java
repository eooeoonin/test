package com.winsmoney.jajaying.boss.controller.deposit;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;

import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.deposit.service.IAccountService;
import com.winsmoney.jajaying.deposit.service.ICheckAccountRecordService;
import com.winsmoney.jajaying.deposit.service.enums.RequestFrom;
import com.winsmoney.jajaying.deposit.service.request.CheckAccountRecordReqVo;
import com.winsmoney.jajaying.deposit.service.request.account.CheckAccountReqVo;
import com.winsmoney.jajaying.deposit.service.response.CheckAccountRecordResVo;
import com.winsmoney.jajaying.deposit.service.response.CommonResVo;
import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.LocalIPUtils;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 资金管理-存管对账
 * Created by chenkai1 on 2017/7/25.
 */
@Controller
@RequestMapping("/capital")
public class DepositController {
    private static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(DepositController.class);

    @Autowired
    private IAccountService depositAccountService;
    @Autowired
    private SequenceGenerator sequenceGenerator;


    @Autowired
    private ICheckAccountRecordService checkAccountRecord;

    @RequestMapping("/check")
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "资金管理-存管对账")
    public Result checkFileConfirm(CheckAccountReqVo checkAccountReqVo) {
        try {
            checkAccountReqVo.setRequestId(sequenceGenerator.getUUID());
            checkAccountReqVo.setRequestTime(new Date());
            checkAccountReqVo.setFrom(RequestFrom.PC);
            checkAccountReqVo.setIp(LocalIPUtils.getIp4Single());
            DepositCommonResult<CommonResVo> result = depositAccountService.checkFileConfirm(checkAccountReqVo);
            if (result.isSuccess()) {
                return Result.success(result);
            } else return Result.error(result.getResultCodeMsg());
        } catch (Exception e) {
            logger.error("接口调用失败", e);
            return Result.error("接口调用失败");
        }

    }
	/**
	 * 对账记录 分页列表
	 */
	@RequestMapping(value = "/checkAccountRecordList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CheckAccountRecordResVo> checkAccountRecordList(CheckAccountRecordReqVo request, String pageNo, String pageSize) {
		
		request.setBusiness("CHECK_ACCOUNT");
//		request.setAction("CONFIRM");
		logger.info("对账记录接口{}入参" + JSONObject.toJSONString(request), ICheckAccountRecordService.class);
		DepositCommonResult<PageInfo<CheckAccountRecordResVo>> result = checkAccountRecord.list(request, Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		logger.info("对账记录接口{}出参" + JSONObject.toJSONString(result), ICheckAccountRecordService.class);
		return result.getBusinessObject();
	}
}


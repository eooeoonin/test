package com.winsmoney.jajaying.boss.controller.gateway;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.deposit.service.IAuthRecordService;
import com.winsmoney.jajaying.deposit.service.IAuthService;
import com.winsmoney.jajaying.deposit.service.IChangeCodeService;
import com.winsmoney.jajaying.deposit.service.IInvestRecordService;
import com.winsmoney.jajaying.deposit.service.ILoanRecordService;
import com.winsmoney.jajaying.deposit.service.ILoanService;
import com.winsmoney.jajaying.deposit.service.IMarketingSubRecordService;
import com.winsmoney.jajaying.deposit.service.IPayRecordService;
import com.winsmoney.jajaying.deposit.service.IPayService;
import com.winsmoney.jajaying.deposit.service.IReleaseRecordService;
import com.winsmoney.jajaying.deposit.service.IRepayRecordSubApplyService;
import com.winsmoney.jajaying.deposit.service.IRepayRecordSubConfirmService;
import com.winsmoney.jajaying.deposit.service.IReturnCodeService;
import com.winsmoney.jajaying.deposit.service.ITradeService;
import com.winsmoney.jajaying.deposit.service.ITransRecordService;
import com.winsmoney.jajaying.deposit.service.enums.Action;
import com.winsmoney.jajaying.deposit.service.enums.RequestFrom;
import com.winsmoney.jajaying.deposit.service.request.AuthRecordReqVo;
import com.winsmoney.jajaying.deposit.service.request.BaseReqVo;
import com.winsmoney.jajaying.deposit.service.request.ChangeCodeReqVo;
import com.winsmoney.jajaying.deposit.service.request.CommonReqVo;
import com.winsmoney.jajaying.deposit.service.request.InvestRecordReqVo;
import com.winsmoney.jajaying.deposit.service.request.LoanRecordReqVo;
import com.winsmoney.jajaying.deposit.service.request.MarketingSubRecordReqVo;
import com.winsmoney.jajaying.deposit.service.request.PayRecordReqVo;
import com.winsmoney.jajaying.deposit.service.request.ReleaseRecordReqVo;
import com.winsmoney.jajaying.deposit.service.request.RepayRecordSubApplyReqVo;
import com.winsmoney.jajaying.deposit.service.request.RepayRecordSubConfirmReqVo;
import com.winsmoney.jajaying.deposit.service.request.ReturnCodeReqVo;
import com.winsmoney.jajaying.deposit.service.request.TransRecordReqVo;
import com.winsmoney.jajaying.deposit.service.request.loan.CreateLoanReqVo;
import com.winsmoney.jajaying.deposit.service.request.loan.KillInvestReqVo;
import com.winsmoney.jajaying.deposit.service.request.loan.RepayApplyReqVo;
import com.winsmoney.jajaying.deposit.service.request.pay.WithdrawReqVo;
import com.winsmoney.jajaying.deposit.service.response.AuthRecordResVo;
import com.winsmoney.jajaying.deposit.service.response.ChangeCodeResVo;
import com.winsmoney.jajaying.deposit.service.response.CommonResVo;
import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;
import com.winsmoney.jajaying.deposit.service.response.InvestRecordResVo;
import com.winsmoney.jajaying.deposit.service.response.LoanRecordResVo;
import com.winsmoney.jajaying.deposit.service.response.MarketingSubRecordResVo;
import com.winsmoney.jajaying.deposit.service.response.PayRecordResVo;
import com.winsmoney.jajaying.deposit.service.response.ReleaseRecordResVo;
import com.winsmoney.jajaying.deposit.service.response.RepayRecordSubApplyResVo;
import com.winsmoney.jajaying.deposit.service.response.RepayRecordSubConfirmResVo;
import com.winsmoney.jajaying.deposit.service.response.ReturnCodeResVo;
import com.winsmoney.jajaying.deposit.service.response.TransRecordResVo;
import com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo;
import com.winsmoney.jajaying.reconciliation.service.ICheckAccountDataService;
import com.winsmoney.jajaying.reconciliation.service.ICheckAccountRecordService;
import com.winsmoney.jajaying.reconciliation.service.IReconciliationService;
import com.winsmoney.jajaying.reconciliation.service.request.CheckAccountDataReqVo;
import com.winsmoney.jajaying.reconciliation.service.request.CheckAccountRecordReqVo;
import com.winsmoney.jajaying.reconciliation.service.request.CheckAccountReqVo;
import com.winsmoney.jajaying.reconciliation.service.request.ClearAccountReqVo;
import com.winsmoney.jajaying.reconciliation.service.response.BaseResVo;
import com.winsmoney.jajaying.reconciliation.service.response.CheckAccountDataResVo;
import com.winsmoney.jajaying.reconciliation.service.response.CheckAccountRecordResVo;
import com.winsmoney.jajaying.reconciliation.service.response.ReconciliationCommonResult;
import com.winsmoney.jajaying.trade.service.ILoanOperService;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.runtime.CommonResult;
import com.winsmoney.platform.framework.core.util.Money;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;

/**
 * @author gaohuaizhang
 *
 */
@Controller
@RequestMapping(value = "/gateway")
public class gatewayController {

	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(gatewayController.class);

	@Autowired
	private IReturnCodeService returnCode;
	@Autowired
	private IChangeCodeService changeCode;
	@Autowired
	private IAuthRecordService authRecord;
	@Autowired
	private IAuthService authService;
	@Autowired
	private IInvestRecordService investRecord;
	@Autowired
	private IPayRecordService payRecord;
	@Autowired
	private IPayService payService;
	@Autowired
	private ITransRecordService transRecord;
	@Autowired
	private ILoanService loanService;
	@Autowired
	private IReleaseRecordService releaseRecord;
	@Autowired
	private IRepayRecordSubApplyService repayRecordSubApply;
	@Autowired
	private IRepayRecordSubConfirmService repayRecordSubConfirm;
	@Autowired
	private IMarketingSubRecordService marketingSubRecord;
	@Autowired
	private SequenceGenerator sequenceGenerator;
	@Autowired
	private ITradeService tradeService;
	@Autowired
	private ILoanRecordService loanRecord;
	@Autowired
	private ILoanOperService loanOper;
	@Autowired
	private ICheckAccountRecordService checkAccountRecord;
	@Autowired
	private ICheckAccountDataService checkAccountData;
	@Autowired
	private IReconciliationService reconciliationService;

	/**
	 * 转码 分页列表
	 */
	@RequestMapping(value = "/change_code/listChangeCode", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<ChangeCodeResVo> listChangeCode(ChangeCodeReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getChannel())) {
			request.setChannel(null);
		}
		logger.info("转码接口{}入参：" + JSONObject.toJSONString(request), IChangeCodeService.class);
		DepositCommonResult<PageInfo<ChangeCodeResVo>> result = changeCode.list(request, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		logger.info("转码接口{}出参：" + JSONObject.toJSONString(result), IChangeCodeService.class);
		return result.getBusinessObject();
	}

	/**
	 * 转码 更新
	 */
	@RequestMapping(value = "/change_code/updateChangeCode", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新删除")
	public Integer updateChangeCode(ChangeCodeReqVo request, HttpServletRequest httrequest) {
		Staff staffSession = (Staff) httrequest.getSession().getAttribute("adminInfo");
		request.setEditedBy(staffSession.getStaffName());
		logger.info("转码接口{}入参：" + JSONObject.toJSONString(request), IChangeCodeService.class);
		DepositCommonResult<Integer> result = changeCode.update(request);
		logger.info("转码接口{}出参：" + JSONObject.toJSONString(result), IChangeCodeService.class);
		return result.getBusinessObject();
	}

	/**
	 * 转码删除
	 */
	@RequestMapping(value = "/change_code/deleteChangeCode", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "转码删除")
	public Integer deleteChangeCode(String id) {
		DepositCommonResult<Integer> result = changeCode.delete(id);
		return result.getBusinessObject();
	}

	/**
	 * 转码 单笔查询
	 */
	@RequestMapping(value = "/change_code/seletByIdChangeCode", method = RequestMethod.POST)
	@ResponseBody
	public ChangeCodeResVo seletByIdChangeCode(ChangeCodeReqVo request) {
		logger.info("转码接口{}入参：" + JSONObject.toJSONString(request), IChangeCodeService.class);
		DepositCommonResult<ChangeCodeResVo> result = changeCode.get(request);
		logger.info("转码接口{}出参：" + JSONObject.toJSONString(result), IChangeCodeService.class);
		return result.getBusinessObject();
	}

	/**
	 * 返回码 分页列表
	 */
	@RequestMapping(value = "/return_code/listReturnCode", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<ReturnCodeResVo> listReturnCode(ReturnCodeReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getChannel())) {
			request.setChannel(null);
		}
		if (StringUtils.isBlank(request.getBusiness())) {
			request.setBusiness(null);
		}
		if (StringUtils.isBlank(request.getOuterCode())) {
			request.setOuterCode(null);
		}
		logger.info("返回码接口{}入参：" + JSONObject.toJSONString(request), IReturnCodeService.class);
		DepositCommonResult<PageInfo<ReturnCodeResVo>> result = returnCode.list(request, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		logger.info("返回码接口{}出参：" + JSONObject.toJSONString(result), IReturnCodeService.class);
		return result.getBusinessObject();
	}

	/**
	 * 返回码 添加
	 */
	@RequestMapping(value = "/return_code/insertReturnCode", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "返回码 添加")
	public Integer insertReturnCode(ReturnCodeReqVo request, HttpServletRequest httrequest) {
		Staff staffSession = (Staff) httrequest.getSession().getAttribute("adminInfo");
		request.setEditedBy(staffSession.getStaffName());
		logger.info("返回码接口{}入参：" + JSONObject.toJSONString(request), IReturnCodeService.class);
		DepositCommonResult<Integer> result = returnCode.insert(request);
		logger.info("返回码接口{}出参：" + JSONObject.toJSONString(result), IReturnCodeService.class);
		return result.getBusinessObject();
	}

	/**
	 * 返回码 更新
	 */
	@RequestMapping(value = "/return_code/updateReturnCode", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "返回码 更新")
	public Integer updateReturnCode(ReturnCodeReqVo request, HttpServletRequest httrequest) {
		Staff staffSession = (Staff) httrequest.getSession().getAttribute("adminInfo");
		request.setEditedBy(staffSession.getStaffName());
		logger.info("返回码接口{}入参：" + JSONObject.toJSONString(request), IReturnCodeService.class);
		DepositCommonResult<Integer> result = returnCode.update(request);
		logger.info("返回码接口{}出参：" + JSONObject.toJSONString(result), IReturnCodeService.class);
		return result.getBusinessObject();
	}

	/**
	 * 返回码 删除
	 */
	@RequestMapping(value = "/return_code/deleteReturnCode", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "返回码 删除")
	public Integer deleteReturnCode(String id) {
		DepositCommonResult<Integer> result = returnCode.delete(id);
		return result.getBusinessObject();
	}

	/**
	 * 返回码 单笔查询
	 */
	@RequestMapping(value = "/return_code/seletByIdReturnCode", method = RequestMethod.POST)
	@ResponseBody
	public ReturnCodeResVo seletByIdReturnCode(ReturnCodeReqVo request) {
		logger.info("返回码接口{}入参：" + JSONObject.toJSONString(request), IReturnCodeService.class);
		DepositCommonResult<ReturnCodeResVo> result = returnCode.get(request);
		logger.info("返回码接口{}出参：" + JSONObject.toJSONString(result), IReturnCodeService.class);
		return result.getBusinessObject();
	}

	/**
	 * 认证记录 分页列表
	 */
	@RequestMapping(value = "/auth_record/authRecordList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<AuthRecordResVo> authRecordList(AuthRecordReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getUserId())) {
			request.setUserId(null);
		}
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		logger.info("认证记录接口{}入参：" + JSONObject.toJSONString(request), IAuthRecordService.class);
		DepositCommonResult<PageInfo<AuthRecordResVo>> result = authRecord.list(request, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		logger.info("认证记录接口{}出参：" + JSONObject.toJSONString(result), IAuthRecordService.class);
		return result.getBusinessObject();
	}

	/**
	 * 认证记录 补单
	 */
	@RequestMapping(value = "/auth_record/revise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "认证记录 补单")
	public Result authRevise(String requestId) {
		DepositCommonResult<UserInfoResVo> result = null;
		try {
			BaseReqVo request = new BaseReqVo();
			request.setRequestId(requestId);
			request.setUserId(null);
			request.setRequestTime(new Date());
			request.setIp("");
			request.setFrom(RequestFrom.H5);

			logger.info("认证记录补单接口{}入参" + JSONObject.toJSONString(request), ILoanService.class);
			result = authService.bindCardRevise(request);
			logger.info("认证记录补单接口{}出参" + JSONObject.toJSONString(result), ILoanService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("补单,异常", e);
		}
		return Result.error("补单,失败");
	}

	/**
	 * (新)投标记录 分页列表
	 */
	@RequestMapping(value = "/invest_record/newInvestRecordList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<InvestRecordResVo> newInvestRecordList(InvestRecordReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getUserId())) {
			request.setUserId(null);
		}
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		if (StringUtils.isBlank(request.getLoanId())) {
			request.setLoanId(null);
		}
		if (StringUtils.isBlank(request.getBusiness())) {
			request.setBusiness(null);
		}
		logger.info("投标记录接口{}入参" + JSONObject.toJSONString(request), IInvestRecordService.class);
		DepositCommonResult<PageInfo<InvestRecordResVo>> result = investRecord.list(request, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		logger.info("投标记录接口{}出参" + JSONObject.toJSONString(result), IInvestRecordService.class);
		return result.getBusinessObject();
	}
	
	/**
	 * 投标记录 分页列表
	 */
	@RequestMapping(value = "/invest_record/investRecordList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<InvestRecordResVo> investRecordList(InvestRecordReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getUserId())) {
			request.setUserId(null);
		}
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		if (StringUtils.isBlank(request.getLoanId())) {
			request.setLoanId(null);
		}
		request.setBusiness("INVEST");
		logger.info("投标记录接口{}入参" + JSONObject.toJSONString(request), IInvestRecordService.class);
		DepositCommonResult<PageInfo<InvestRecordResVo>> result = investRecord.list(request, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		logger.info("投标记录接口{}出参" + JSONObject.toJSONString(result), IInvestRecordService.class);
		return result.getBusinessObject();
	}

	/**
	 * 投标记录 补单
	 */
	@RequestMapping(value = "/invest_record/revise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "投标记录 补单")
	public Result investRevise(String requestId, String userId) {
		DepositCommonResult<CommonResVo> result = null;
		try {
			BaseReqVo request = new BaseReqVo();
			request.setRequestId(requestId);
			request.setUserId(userId);
			request.setRequestTime(new Date());
			request.setIp("");
			request.setFrom(RequestFrom.H5);

			logger.info("投标记录补单接口{}入参" + JSONObject.toJSONString(requestId), ILoanService.class);
			result = loanService.investRevise(request);
			logger.info("投标记录补单接口{}出参" + JSONObject.toJSONString(result), ILoanService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("补单,异常", e);
		}
		return Result.error("补单,失败");
	}

	/**
	 * 投标记录 撤销
	 */
	@RequestMapping(value = "/invest_record/repeal", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "投标记录 撤销")
	public Result investRepeal(String investId) {
		TradeCommonResult<Integer> result = null;
		try {

			logger.info("投标记录撤销接口{}入参" + JSONObject.toJSONString(investId), ILoanService.class);
			result = loanOper.cancleInvest(investId);
			logger.info("投标记录撤销接口{}出参" + JSONObject.toJSONString(result), ILoanService.class);

			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("撤销,异常", e);
		}
		return Result.error("撤销,失败");
	}

	/**
	 * 充值记录 分页列表
	 */
	@RequestMapping(value = "/pay_record_recharge/payRecordRechargeList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<PayRecordResVo> payRecordRechargeList(PayRecordReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getUserId())) {
			request.setUserId(null);
		}
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		request.setFlowDirction(1);
		logger.info("支付记录接口{}入参" + JSONObject.toJSONString(request), IPayRecordService.class);
		DepositCommonResult<PageInfo<PayRecordResVo>> result = payRecord.list(request, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		logger.info("支付记录接口{}出参" + JSONObject.toJSONString(result), IPayRecordService.class);
		return result.getBusinessObject();
	}

	/**
	 * 提现记录 分页列表
	 */
	@RequestMapping(value = "/pay_record_withdraw/payRecordWithdrawList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<PayRecordResVo> payRecordWithdrawList(PayRecordReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getUserId())) {
			request.setUserId(null);
		}
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		request.setPayType("WITHDRAW");
		logger.info("支付记录接口{}入参" + JSONObject.toJSONString(request), IPayRecordService.class);
		DepositCommonResult<PageInfo<PayRecordResVo>> result = payRecord.list(request, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		logger.info("支付记录接口{}出参" + JSONObject.toJSONString(result), IPayRecordService.class);
		return result.getBusinessObject();
	}

	/**
	 * 支付记录 补单
	 */
	@RequestMapping(value = "/pay_record_recharge/rechargeRevise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "支付记录 补单")
	public Result payRechargeRevise(CommonReqVo request) {

		DepositCommonResult<CommonResVo> result = null;
		if (StringUtils.isBlank(request.getUserId())) {
			request.setUserId(null);
		}
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		try {
			logger.info("充值记录补单接口{}入参：" + JSONObject.toJSONString(request), IPayService.class);
			BaseReqVo reqVo = new BaseReqVo();
			reqVo.setFrom(RequestFrom.H5);
			reqVo.setIp("1.1.1.1");
			reqVo.setRequestId(request.getRequestId());
			reqVo.setRequestTime(new Date());
			reqVo.setUserId(request.getUserId());
			result = payService.rechargeRevise(reqVo);
			logger.info("充值记录补单接口{}出参：" + JSONObject.toJSONString(result), IPayService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			logger.error("补单异常", e);
		}
		return Result.error("补单失败");
	}

	/**
	 * 提现记录 补单
	 */
	@RequestMapping(value = "/pay_record_withdraw/withdrawRevise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "提现记录 补单")
	public Result payRevise(CommonReqVo request) {

		DepositCommonResult<CommonResVo> result = null;
		if (StringUtils.isBlank(request.getUserId())) {
			request.setUserId(null);
		}
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		try {
			logger.info("提现记录补单接口{}入参：" + JSONObject.toJSONString(request), IPayService.class);
			BaseReqVo reqVo = new BaseReqVo();
			reqVo.setFrom(RequestFrom.H5);
			reqVo.setIp("1.1.1.1");
			reqVo.setRequestId(request.getRequestId());
			reqVo.setRequestTime(new Date());
			reqVo.setUserId(request.getUserId());
			result = payService.withdrawRevise(reqVo);
			logger.info("提现记录补单接口{}出参：" + JSONObject.toJSONString(result), IPayService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			logger.error("补单异常", e);
		}
		return Result.error("补单失败");
	}

	/**
	 * 提现记录 拦截
	 */
	@RequestMapping(value = "/pay_record_withdraw/withdrawIntercept", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "提现记录 拦截")
	public Result withdrawIntercept(WithdrawReqVo request) {

		DepositCommonResult<CommonResVo> result = null;
		if (StringUtils.isBlank(request.getPreRequestId())) {
			request.setPreRequestId(null);
		}
		if (StringUtils.isBlank(request.getUserId())) {
			request.setUserId(null);
		}
		String uuid = sequenceGenerator.getUUID();
		request.setRequestId(uuid);
		request.setRequestTime(new Date());
		request.setIp("");
		request.setFrom(RequestFrom.H5);
		request.setAction(Action.CONFIRM);
		try {
			logger.info("提现记录拦截接口{}入参：" + JSONObject.toJSONString(request), IPayService.class);
			// DepositCommonResult<CommonResVo> withdrawIntercept(WithdrawReqVo
			// request);
			result = payService.withdrawIntercept(request);
			logger.info("提现记录拦截接口{}出参：" + JSONObject.toJSONString(result), IPayService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			logger.error("拦截异常", e);
		}
		return Result.error("拦截失败");
	}

	/**
	 * 通用记录 分页列表
	 */
	@RequestMapping(value = "/trans_record/transRecordList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<TransRecordResVo> transRecordList(TransRecordReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getUserId())) {
			request.setUserId(null);
		}
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		logger.info("通用记录接口{}入参：" + JSONObject.toJSONString(request), ITransRecordService.class);
		DepositCommonResult<PageInfo<TransRecordResVo>> result = transRecord.list(request, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		logger.info("通用记录接口{}出参：" + JSONObject.toJSONString(result), ITransRecordService.class);
		return result.getBusinessObject();
	}

	/**
	 * 放款记录 分页列表
	 */
	@RequestMapping(value = "/release_record/releaseRecordList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<ReleaseRecordResVo> releaseRecordList(ReleaseRecordReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getLoanId())) {
			request.setLoanId(null);
		}
		// 属性名 更改 requestId
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		logger.info("放款记录接口{}入参：" + JSONObject.toJSONString(request), IReleaseRecordService.class);
		DepositCommonResult<PageInfo<ReleaseRecordResVo>> result = releaseRecord.list(request, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		logger.info("放款记录接口{}出参：" + JSONObject.toJSONString(result), IReleaseRecordService.class);
		return result.getBusinessObject();
	}

	/**
	 * 放款补单
	 */
	@RequestMapping(value = "/release_record/revise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "放款补单")
	public Result releaseRevise(BaseReqVo request) {
		try {
			if (StringUtils.isBlank(request.getRequestId())) {
				request.setRequestId(null);
			}
			if (StringUtils.isBlank(request.getUserId())) {
				request.setUserId(null);
			}
			request.setFrom(RequestFrom.H5);
			request.setRequestTime(new Date());
			request.setIp("");
			logger.info("放款补单接口{}入参：" + JSONObject.toJSONString(request), ILoanService.class);
			DepositCommonResult<CommonResVo> result = loanService.releaseRevise(request);
			logger.info("放款补单接口{}出参：" + JSONObject.toJSONString(result), ILoanService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("放款补单,异常", e);
		}
		return Result.error("放款补单,失败");
	}

	/**
	 * 申请还款记录 分页列表
	 */
	@RequestMapping(value = "/repay_record/repayRecordApplyList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<RepayRecordSubApplyResVo> repayRecordApplyList(RepayRecordSubApplyReqVo request, String pageNo,
			String pageSize) {
		if (StringUtils.isBlank(request.getLoanId())) {
			request.setLoanId(null);
		}
		if (StringUtils.isBlank(request.getBorrowerUserId())) {
			request.setBorrowerUserId(null);
		}
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		logger.info("申请还款记录接口{}入参：" + JSONObject.toJSONString(request), IRepayRecordSubApplyService.class);
		DepositCommonResult<PageInfo<RepayRecordSubApplyResVo>> result = repayRecordSubApply.list(request,
				Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		logger.info("申请还款记录接口{}出参：" + JSONObject.toJSONString(result), IRepayRecordSubApplyService.class);
		return result.getBusinessObject();
	}

	/**
	 * 确认还款记录 分页列表
	 */
	@RequestMapping(value = "/repay_record/repayRecordConfirmList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<RepayRecordSubConfirmResVo> repayRecordConfirmList(RepayRecordSubConfirmReqVo request,
			String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getLoanId())) {
			request.setLoanId(null);
		}
		if (StringUtils.isBlank(request.getBorrowerId())) {
			request.setBorrowerId(null);
		}
		logger.info("确认还款记录接口{}入参：" + JSONObject.toJSONString(request), IRepayRecordSubConfirmService.class);
		DepositCommonResult<PageInfo<RepayRecordSubConfirmResVo>> result = repayRecordSubConfirm.list(request,
				Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		logger.info("确认还款记录接口{}出参：" + JSONObject.toJSONString(result), IRepayRecordSubConfirmService.class);
		return result.getBusinessObject();
	}

	/**
	 * 申请还款 补单
	 */
	@RequestMapping(value = "/repay_record/repayRecordApplyRevise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "申请还款 补单")
	public Result repayRecordApplyRevise(String requestId) {
		DepositCommonResult<CommonResVo> result = null;
		try {
			BaseReqVo reqVo = new BaseReqVo();
			reqVo.setRequestTime(new Date());
			reqVo.setIp("1.1.1.1");
			reqVo.setFrom(RequestFrom.OTHER);
			reqVo.setUserId(null);
			if (StringUtil.isBlank(requestId)) {
				reqVo.setRequestId(null);
			} else {
				reqVo.setRequestId(requestId);
			}
			logger.info("申请还款补单接口{}入参：" + JSONObject.toJSONString(reqVo), ILoanService.class);
			result = loanService.preRepayRevise(reqVo);
			logger.info("申请还款补单接口{}出参：" + JSONObject.toJSONString(result), ILoanService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("申请还款补单,异常", e);
		}
		return Result.error("申请还款补单,失败");
	}

	/**
	 * 申请还款 撤销
	 * 
	 * @param requestId
	 * @return
	 */
	@RequestMapping(value = "/repay_record/repayRecordApplyRepeal", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "申请还款 撤销")
	public Result repayRecordApplyRepeal(RepayApplyReqVo request) {
		DepositCommonResult<CommonResVo> result = null;
		try {
			request.setRequestTime(new Date());
			request.setIp("1.1.1.1");
			request.setFrom(RequestFrom.OTHER);
			String uuid = sequenceGenerator.getUUID();
			request.setRequestId(uuid);
			if (StringUtils.isBlank(request.getPreRequestId())) {
				request.setPreRequestId(null);
			} 
			if (StringUtils.isBlank(request.getLoanId())) {
				request.setLoanId(null);
			}
			if (new Money("0").greaterThan(new Money(request.getPrincipalAmount().toString()))) {
				request.setPrincipalAmount(null);
			}

			logger.info("申请还款撤销接口{}入参：" + JSONObject.toJSONString(request), ILoanService.class);
			result = loanService.repayCancel(request);
			logger.info("申请还款撤销接口{}出参：" + JSONObject.toJSONString(result), ILoanService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("申请还款撤销,异常", e);
		}
		return Result.error("申请还款撤销,失败");
	}

	/**
	 * 确认还款 补单
	 */
	@RequestMapping(value = "/repay_record/repayRecordConfirmRevise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "确认还款 补单")
	public Result repayRecordConfirmRevise(String requestId) {
		DepositCommonResult<CommonResVo> result = null;
		try {
			BaseReqVo reqVo = new BaseReqVo();
			reqVo.setRequestTime(new Date());
			reqVo.setIp("1.1.1.1");
			reqVo.setFrom(RequestFrom.H5);
			reqVo.setUserId(null);
			if (StringUtil.isBlank(requestId)) {
				reqVo.setRequestId(null);
			} else {
				reqVo.setRequestId(requestId);
			}
			logger.info("确认还款补单接口{}入参：" + JSONObject.toJSONString(reqVo), ILoanService.class);
			result = loanService.repayRevise(reqVo);
			logger.info("确认还款补单接口{}出参：" + JSONObject.toJSONString(result), ILoanService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("确认还款补单,异常", e);
		}
		return Result.error("确认还款补单,失败");
	}

	/**
	 * 流标记录 分页列表
	 */
	@RequestMapping(value = "/kill_invest_record/killInvestRecordList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<InvestRecordResVo> killInvestRecordList(InvestRecordReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		request.setBusiness("KILL_INVEST");
		logger.info("流标记录接口{}入参：" + JSONObject.toJSONString(request), IInvestRecordService.class);
		DepositCommonResult<PageInfo<InvestRecordResVo>> result = null;
		result = investRecord.list(request, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		logger.info("流标记录接口{}出参：" + JSONObject.toJSONString(result), IInvestRecordService.class);
		return result.getBusinessObject();
	}

	/**
	 * 流标记录 重试流标
	 */
	@RequestMapping(value = "/kill_invest_record/retry", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "流标记录 重试流标")
	public Result killInvestRetry(KillInvestReqVo request) {
		try {
			if (StringUtils.isBlank(request.getRequestId())) {
				request.setRequestId(null);
			}
			if (StringUtils.isBlank(request.getUserId())) {
				request.setUserId(null);
			}
			request.setFrom(RequestFrom.H5);
			request.setRequestTime(new Date());
			request.setIp("");
			if (StringUtils.isBlank(request.getPreRequestId())) {
				request.setPreRequestId(null);
			}
			if (new Money("0").greaterThan(new Money(request.getAmount().toString()))) {
				request.setAmount(null);
			}
			logger.info("重试流标接口{}入参：" + JSONObject.toJSONString(request), IInvestRecordService.class);
			DepositCommonResult<CommonResVo> result = null;
			result = loanService.killInvest(request);
			logger.info("重试流标接口{}出参：" + JSONObject.toJSONString(result), IInvestRecordService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("重试流标,异常", e);
		}
		return Result.error("重试流标,失败");
	}

	/**
	 * 流标记录 补单
	 */
	@RequestMapping(value = "/kill_invest_record/revise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "流标记录 补单")
	public Result killInvestRevise(KillInvestReqVo request) {
		DepositCommonResult<CommonResVo> result = null;
		try {
			if (StringUtils.isBlank(request.getRequestId())) {
				request.setRequestId(null);
			}
			if (StringUtils.isBlank(request.getUserId())) {
				request.setUserId(null);
			}
			request.setFrom(RequestFrom.H5);
			request.setRequestTime(new Date());
			request.setIp("");
			logger.info("流标记录补单接口{}入参" + JSONObject.toJSONString(request), ILoanService.class);
			result = loanService.killReviseInvest(request);
			logger.info("流标记录补单接口{}出参" + JSONObject.toJSONString(result), ILoanService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("补单,异常", e);
		}
		return Result.error("补单,失败");
	}
	/**
	 * 流标记录 重试流标
	 */
	@RequestMapping(value = "/invest_record/killretry", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "流标记录 重试流标")
	public Result killretry(KillInvestReqVo request) {
		try {
			if (StringUtils.isBlank(request.getRequestId())) {
				request.setRequestId(null);
			}
			if (StringUtils.isBlank(request.getUserId())) {
				request.setUserId(null);
			}
			request.setFrom(RequestFrom.H5);
			request.setRequestTime(new Date());
			request.setIp("");
			if (StringUtils.isBlank(request.getPreRequestId())) {
				request.setPreRequestId(null);
			}
			if (new Money("0").greaterThan(new Money(request.getAmount().toString()))) {
				request.setAmount(null);
			}
			logger.info("重试流标接口{}入参：" + JSONObject.toJSONString(request), IInvestRecordService.class);
			DepositCommonResult<CommonResVo> result = null;
			result = loanService.killInvest(request);
			logger.info("重试流标接口{}出参：" + JSONObject.toJSONString(result), IInvestRecordService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("重试流标,异常", e);
		}
		return Result.error("重试流标,失败");
	}
	
	/**
	 * 流标记录 补单
	 */
	@RequestMapping(value = "/invest_record/killrevise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "流标记录 补单")
	public Result killrevise(KillInvestReqVo request) {
		DepositCommonResult<CommonResVo> result = null;
		try {
			if (StringUtils.isBlank(request.getRequestId())) {
				request.setRequestId(null);
			}
			if (StringUtils.isBlank(request.getUserId())) {
				request.setUserId(null);
			}
			request.setFrom(RequestFrom.H5);
			request.setRequestTime(new Date());
			request.setIp("");
			logger.info("流标记录补单接口{}入参" + JSONObject.toJSONString(request), ILoanService.class);
			result = loanService.killReviseInvest(request);
			logger.info("流标记录补单接口{}出参" + JSONObject.toJSONString(result), ILoanService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("补单,异常", e);
		}
		return Result.error("补单,失败");
	}

	/**
	 * 营销款记录 分页列表
	 */
	@RequestMapping(value = "/send_redmoney_record/sendRedmoneyRecordList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<MarketingSubRecordResVo> sendRedmoneyRecordList(MarketingSubRecordReqVo request, String pageNo,
			String pageSize) {
		if (StringUtils.isBlank(request.getMainRequestId())) {
			request.setMainRequestId(null);
		}
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		logger.info("营销款记录接口{}入参：" + JSONObject.toJSONString(request), IMarketingSubRecordService.class);
		DepositCommonResult<PageInfo<MarketingSubRecordResVo>> result = null;
		result = marketingSubRecord.list(request, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		logger.info("营销款记录接口{}出参：" + JSONObject.toJSONString(result), IMarketingSubRecordService.class);
		return result.getBusinessObject();
	}

	/**
	 * 营销款 补单
	 */
	@RequestMapping(value = "/send_redmoney_record/revise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "营销款 补单")
	public Result revise(String requestId) {
		DepositCommonResult<CommonResVo> result = null;
		try {
			BaseReqVo reqVo = new BaseReqVo();
			reqVo.setRequestTime(new Date());
			reqVo.setIp("1.1.1.1");
			reqVo.setFrom(RequestFrom.H5);
			reqVo.setUserId(null);
			if (StringUtil.isBlank(requestId)) {
				reqVo.setRequestId(null);
			} else {
				reqVo.setRequestId(requestId);
			}
			logger.info("营销款补单接口{}入参：" + JSONObject.toJSONString(reqVo), ITradeService.class);
			result = tradeService.marketingRevise(reqVo);
			logger.info("营销款补单接口{}出参：" + JSONObject.toJSONString(result), ITradeService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("补单,异常", e);
		}
		return Result.error("补单,失败");
	}

	/**
	 * 标的记录 分页列表
	 */
	@RequestMapping(value = "/loan_record/loanRecordList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<LoanRecordResVo> loanRecordList(LoanRecordReqVo request, String pageNo, String pageSize) {
		if (StringUtils.isBlank(request.getUserId())) {
			request.setUserId(null);
		}
		if (StringUtils.isBlank(request.getRequestId())) {
			request.setRequestId(null);
		}
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		if (StringUtils.isBlank(request.getLoanId())) {
			request.setLoanId(null);
		}
		logger.info("标的记录接口{}入参" + JSONObject.toJSONString(request), ILoanRecordService.class);
		DepositCommonResult<PageInfo<LoanRecordResVo>> result = loanRecord.list(request, Integer.parseInt(pageNo),
				Integer.parseInt(pageSize));
		logger.info("标的记录接口{}出参" + JSONObject.toJSONString(result), ILoanRecordService.class);
		return result.getBusinessObject();
	}

	/**
	 * 标的记录 补单
	 * 
	 * @param requestId
	 * @return
	 */
	@RequestMapping(value = "/loan_record/loanRecordRevise", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "标的记录 补单")
	public Result loanRecordRevise(String requestId) {
		DepositCommonResult<CommonResVo> result = null;
		try {
			CreateLoanReqVo reqVo = new CreateLoanReqVo();
			reqVo.setRequestTime(new Date());
			reqVo.setIp("1.1.1.1");
			reqVo.setFrom(RequestFrom.OTHER);
			reqVo.setUserId(null);
			if (StringUtil.isBlank(requestId)) {
				reqVo.setRequestId(null);
			} else {
				reqVo.setRequestId(requestId);
			}
			logger.info("标的补单接口{}入参：" + JSONObject.toJSONString(reqVo), ILoanService.class);
			result = loanService.loanRevise(reqVo);
			logger.info("标的补单接口{}出参：" + JSONObject.toJSONString(result), ILoanService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("补单,异常", e);
		}
		return Result.error("补单,失败");
	}

	/**
	 * 标的记录 委托处理
	 * 
	 * @param requestId
	 * @return
	 */
	@RequestMapping(value = "/loan_record/authEntrustPay", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "标的记录 委托处理")
	public Result authEntrustPay(String requestId) {
		DepositCommonResult<CommonResVo> result = null;
		try {
			CreateLoanReqVo reqVo = new CreateLoanReqVo();
			reqVo.setRequestTime(new Date());
			reqVo.setIp("1.1.1.1");
			reqVo.setFrom(RequestFrom.OTHER);
			reqVo.setUserId("");
			if (StringUtil.isBlank(requestId)) {
				reqVo.setRequestId(null);
			} else {
				reqVo.setRequestId(requestId);
			}
			logger.info("标的委托处理接口{}入参：" + JSONObject.toJSONString(reqVo), ILoanService.class);
			result = loanService.authEntrustPay(reqVo);
			logger.info("标的委托处理接口{}出参：" + JSONObject.toJSONString(result), ILoanService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("委托处理,异常", e);
		}
		return Result.error("委托处理,失败");
	}
	
	/**
	 * 对账记录 分页列表
	 */
	@RequestMapping(value = "/check_account_record/checkAccountRecordList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CheckAccountRecordResVo> checkAccountRecordList(CheckAccountRecordReqVo request, String pageNo, String pageSize) {
		
		if (StringUtils.isBlank(request.getSequenceId())) {
			request.setSequenceId(null);
		}
		
		logger.info("对账记录接口{}入参" + JSONObject.toJSONString(request), ICheckAccountRecordService.class);
		ReconciliationCommonResult<PageInfo<CheckAccountRecordResVo>> result = checkAccountRecord.list(request, Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		logger.info("对账记录接口{}出参" + JSONObject.toJSONString(result), ICheckAccountRecordService.class);
		return result.getBusinessObject();
	}
	/**
	 * 对账记录 对账申请
	 */
	@RequestMapping(value = "/check_account_record/applyCheckAccount", method = RequestMethod.POST)
	@ResponseBody
	public String applyCheckAccount(CheckAccountReqVo request) {
		CommonResult<BaseResVo> result = null;
		
		String uuid = sequenceGenerator.getUUID();
		request.setRequestId(uuid);
		request.setRequestTime(new Date());
		logger.info("对账接口{}入参" + JSONObject.toJSONString(request), IReconciliationService.class);
		result = reconciliationService.applyCheckAccount(request);
		logger.info("对账接口{}出参" + JSONObject.toJSONString(result), IReconciliationService.class);
		return result.getResultCodeMsg();
	}
	/**
	 * 对账记录 对账确认
	 */
	@RequestMapping(value = "/check_account_record/confirmCheckAccount", method = RequestMethod.POST)
	@ResponseBody
	public String confirmCheckAccount(CheckAccountReqVo request) {
		CommonResult<BaseResVo> result = null;
		
		String uuid = sequenceGenerator.getUUID();
		request.setRequestId(uuid);
		request.setRequestTime(new Date());
		logger.info("对账确认接口{}入参" + JSONObject.toJSONString(request), IReconciliationService.class);
		result = reconciliationService.confirmCheckAccount(request);
		logger.info("对账确认接口{}出参" + JSONObject.toJSONString(result), IReconciliationService.class);
		return result.getResultCodeMsg();
	}
	
	/**
	 * 对账数据 分页列表
	 */
	@RequestMapping(value = "/check_account_data/checkAccountDataList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CheckAccountDataResVo> checkAccountDataList(CheckAccountDataReqVo request, String pageNo, String pageSize) {
		
		if(StringUtil.isBlank(request.getSequenceId())){
			request.setSequenceId(null);
		}
		if(StringUtil.isBlank(request.getBatch())){
			request.setBatch(null);
		}
		if(StringUtil.isBlank(request.getAccountType())){
			request.setAccountType(null);
		}
		if(StringUtil.isBlank(request.getTransStatus())){
			request.setTransStatus(null);
		}
		logger.info("对账数据接口{}入参" + JSONObject.toJSONString(request), ICheckAccountDataService.class);
		ReconciliationCommonResult<PageInfo<CheckAccountDataResVo>> result = checkAccountData.list(request, Integer.parseInt(pageNo),Integer.parseInt(pageSize));
		logger.info("对账数据接口{}出参" + JSONObject.toJSONString(result), ICheckAccountDataService.class);
		return result.getBusinessObject();
	}
	
	/**
	 * 对账数据 平账
	 */
	@RequestMapping(value = "/check_account_data/clearAccount", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "对账数据 平账")
	public Result clearAccount(ClearAccountReqVo request) {
		try {
			
			String uuid = sequenceGenerator.getUUID();
			request.setRequestId(uuid);
			request.setRequestTime(new Date());
			logger.info("对账数据 平账接口{}入参：" + JSONObject.toJSONString(request), IReconciliationService.class);
			CommonResult<BaseResVo> result = reconciliationService.clearCheckAccount(request);
			logger.info("对账数据 平账接口{}出参：" + JSONObject.toJSONString(result), IReconciliationService.class);
			if (result.isSuccess()) {
				return Result.success(result.getResultCode() + result.getResultCodeMsg(), result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("平账,异常", e);
		}
		return Result.error("平账,失败");
	}
}
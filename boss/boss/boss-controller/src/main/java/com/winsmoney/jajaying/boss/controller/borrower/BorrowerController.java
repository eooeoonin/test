package com.winsmoney.jajaying.boss.controller.borrower;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.client.BankInfoClient;
import com.winsmoney.jajaying.basedata.service.client.SystemConfigClient;
import com.winsmoney.jajaying.basedata.service.request.BankInfoReqVo;
import com.winsmoney.jajaying.basedata.service.response.BankInfoResVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.controller.BaseController;
import com.winsmoney.jajaying.boss.controller.borrow.BorrowController;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.BorrowResBo;
import com.winsmoney.jajaying.boss.domain.model.EnterpriseUserResBo;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.trade.service.IAdjustmentRecordService;
import com.winsmoney.jajaying.trade.service.IBorrowRepayPlanService;
import com.winsmoney.jajaying.trade.service.IBorrowService;
import com.winsmoney.jajaying.trade.service.IBorrowerService;
import com.winsmoney.jajaying.trade.service.IDepositAdjustmentService;
import com.winsmoney.jajaying.trade.service.IGuaranteeService;
import com.winsmoney.jajaying.trade.service.IInterestPlansService;
import com.winsmoney.jajaying.trade.service.request.BorrowRepayPlanReqVo;
import com.winsmoney.jajaying.trade.service.request.BorrowReqVo;
import com.winsmoney.jajaying.trade.service.request.DepositAdjustmentReqVo;
import com.winsmoney.jajaying.trade.service.response.BorrowRepayPlanResVo;
import com.winsmoney.jajaying.trade.service.response.BorrowResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IBindBankCardService;
import com.winsmoney.jajaying.user.service.IEnterpriseEditLogService;
import com.winsmoney.jajaying.user.service.IEnterpriseUserService;
import com.winsmoney.jajaying.user.service.IUserExtendInfoService;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.IUserRoleInfoService;
import com.winsmoney.jajaying.user.service.IUserService;
import com.winsmoney.jajaying.user.service.enums.ApprovalStatus;
import com.winsmoney.jajaying.user.service.enums.EnterpriseRegisterCertType;
import com.winsmoney.jajaying.user.service.enums.LoginType;
import com.winsmoney.jajaying.user.service.enums.UserRoleType;
import com.winsmoney.jajaying.user.service.enums.UserType;
import com.winsmoney.jajaying.user.service.request.BindBankCardReqVo;
import com.winsmoney.jajaying.user.service.request.BorrowerBossReqVo;
import com.winsmoney.jajaying.user.service.request.EnterpriseEditLogReqVo;
import com.winsmoney.jajaying.user.service.request.EnterpriseUserReqVo;
import com.winsmoney.jajaying.user.service.request.RegisterEnterpriseUserReqVo;
import com.winsmoney.jajaying.user.service.request.RegisterReqVo;
import com.winsmoney.jajaying.user.service.request.UserExtendInfoReqVo;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.request.UserRoleInfoReqVo;
import com.winsmoney.jajaying.user.service.response.BindBankCardResVo;
import com.winsmoney.jajaying.user.service.response.BorrowerBossResVo;
import com.winsmoney.jajaying.user.service.response.EnterpriseEditLogResVo;
import com.winsmoney.jajaying.user.service.response.EnterpriseUserResVo;
import com.winsmoney.jajaying.user.service.response.RegisterEnterpriseUserResVo;
import com.winsmoney.jajaying.user.service.response.UserAccountInfoResVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserExtendInfoResVo;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.jajaying.user.service.response.UserRoleInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.DateUtil;
import com.winsmoney.platform.framework.core.util.Money;

/**
 * 借款人管理
 */
@Controller
@RequestMapping(value = "/borrower")
public class BorrowerController extends BaseController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(BorrowerController.class);
	@Autowired
	private IBorrowRepayPlanService borrowRepayPlanService;
	@Autowired
	private IEnterpriseUserService enterpriseUserService;
	@Autowired
	private IBorrowService borrowService;
	@Autowired
	private IEnterpriseEditLogService enterpriseEditLogService;
	@Autowired
	private IAdjustmentRecordService adjustmentRecordService;
	@Autowired
	private IInterestPlansService interestPlansService;
	// 取得账户余额
	@Autowired
	private BankInfoClient bankInfoClient;
	@Autowired
	private SystemConfigClient systemConfigClient;
	@Autowired
	private IDepositAdjustmentService depositAdjustmentService;
	@Autowired
	private IUserRoleInfoService userRoleInfo;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserExtendInfoService userExtendInfoService;
	@Autowired
	private IBindBankCardService bindBankCardService;
	@Autowired
	private IBorrowerService borrowerService;
	@Autowired
	private IGuaranteeService guaranteeService;
	
	/**
	 * 借款人列表分页
	 */
	@RequestMapping(value = "/borrowerlist/borrowersWithPage", method = RequestMethod.POST)
	@ResponseBody
	public Result borrowerList(BorrowerBossReqVo borrowerBossReqVo, int pageNo, int pageSize) {
		try {
			if(StringUtils.isEmpty(borrowerBossReqVo.getUserId())) {
				borrowerBossReqVo.setUserId(null);
			}
			if(StringUtils.isEmpty(borrowerBossReqVo.getUserName())) {
				borrowerBossReqVo.setUserName(null);
			}
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowerBossReqVo), IEnterpriseUserService.class);
			UserCommonResult<PageInfo<BorrowerBossResVo>> result = userService.listBorrowUserBoss(borrowerBossReqVo, pageNo, pageSize);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IEnterpriseUserService.class);
			if (result.isSuccess()) {
				return Result.success(result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}

		} catch (Exception e) {
			logger.error("取得借款人分页列表异常", e);
		}
		return Result.error("取得借款人分页列表异常");
	}

	@RequestMapping(value = "/borrowerlist/getUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result getUserInfo(String userId) {
		List r = new ArrayList();
		try {
			UserRoleInfoReqVo u = new UserRoleInfoReqVo();
			u.setUserId(userId);
		UserCommonResult<UserInfoResVo>	r1 = userInfoService.getByRoleUserId(userId);
		UserCommonResult<UserRoleInfoResVo>	r2 = userRoleInfo.get(u);
			r.add(r1.getBusinessObject());
			r.add(r2.getBusinessObject());
			return Result.success(r);
		} catch (Exception e) {
			logger.error("取得借款人信息异常", e);
			return Result.error("取得借款人信息异常");
		}
	}
	
	/**
	 * 取得借款人信息
	 */
	@RequestMapping(value = "/borrowerlist/get/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public Result get(@PathVariable("userId") String userId) {
		try {
			logger.info("接口{}入参:" + JSONObject.toJSONString(userId), IEnterpriseUserService.class);
			EnterpriseUserReqVo euq = new EnterpriseUserReqVo();
			euq.setUserId(userId);
			UserCommonResult<EnterpriseUserResVo> result = enterpriseUserService.getEnterpriseUser(euq);
			UserExtendInfoReqVo userExtendInfoReqVo = new UserExtendInfoReqVo();
			userExtendInfoReqVo.setUserId(userId);
			UserCommonResult<UserExtendInfoResVo> einf = userExtendInfoService.getUserExtendInfo(userExtendInfoReqVo);
			result.getBusinessObject().setEmail(einf.getBusinessObject().getEmail());
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IEnterpriseUserService.class);
			if (result.isSuccess()) {
				EnterpriseUserResBo enterpriseUserResBo = BeanMapper.copyObject(result.getBusinessObject(),
						EnterpriseUserResBo.class);
				EnterpriseEditLogReqVo enterpriseEditLogReqVo = new EnterpriseEditLogReqVo();
				enterpriseEditLogReqVo.setEnterpriseUserId(userId);
				logger.info("接口{}入参:" + JSONObject.toJSONString(enterpriseEditLogReqVo),
						IEnterpriseEditLogService.class);
				UserCommonResult<PageInfo<EnterpriseEditLogResVo>> logResult = enterpriseEditLogService
						.list(enterpriseEditLogReqVo, 1, 2000);
				logger.info("接口{}出参:" + JSONObject.toJSONString(logResult), IEnterpriseEditLogService.class);
				if (logResult.isSuccess()) {
					enterpriseUserResBo.setLogs(logResult.getBusinessObject().getList());
				}
				return Result.success(enterpriseUserResBo);
			} else {
				return Result.error("取得借款人信息异常");
			}
		} catch (Exception e) {
			logger.error("取得借款人信息异常", e);
		}
		return Result.error("取得借款人信息异常");
	}

	/**
	 * 获取角色
	 * @param userId
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/borrowerlist/getUserRole", method = RequestMethod.POST)
	@ResponseBody
	public String getbaseInfo(@RequestParam("userId") String userId, @RequestParam("role")String role) {
		// 根据ID查询用户角色
		UserRoleInfoReqVo userRoleInfoReqVo = new UserRoleInfoReqVo();
		userRoleInfoReqVo.setUserId(userId);
		userRoleInfoReqVo.setRoleCode( UserRoleType.getType( role ));
		logger.info("查询用户角色接口{}入参:" + JSONObject.toJSONString(userId), IUserRoleInfoService.class);
		UserCommonResult<UserRoleInfoResVo> result = userRoleInfo.get(userRoleInfoReqVo);
		logger.info("查询用户角色接口{}出参:" + JSONObject.toJSONString(result), IUserRoleInfoService.class);

		return result.getBusinessObject().getRoleCode().toString();

	}

	/**
	 * 企业基本信息
	 */
	@RequestMapping(value = "/borrowerlist/getbaseInfo/{borrowerId}", method = RequestMethod.POST)
	@ResponseBody
	public Result getbaseInfo(@PathVariable("borrowerId") String borrowerId, UserAccountInfoResVo request) {
		try {
			// 根据ID查询企业基本信息
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowerId), IEnterpriseUserService.class);
			UserCommonResult<EnterpriseUserResVo> result = enterpriseUserService.getById(borrowerId);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IEnterpriseUserService.class);
			BorrowReqVo borrowReqVo = new BorrowReqVo();

			borrowReqVo.setBorrowUserCode(result.getBusinessObject().getId());
			EnterpriseUserResBo enterpriseUserResBo = BeanMapper.copyObject(result.getBusinessObject(),
					EnterpriseUserResBo.class);
			try {
				logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
				TradeCommonResult<List<BorrowResVo>> listBorrow = borrowService.listBorrow(borrowReqVo);
				logger.info("接口{}出参:" + JSONObject.toJSONString(listBorrow), IBorrowService.class);
				enterpriseUserResBo.setBorrowCount(listBorrow.getBusinessObject().size());
			} catch (Exception e) {
				logger.error("取得企业借款信息异常", e);
			}
			EnterpriseEditLogReqVo enterpriseEditLogReqVo = new EnterpriseEditLogReqVo();
			enterpriseEditLogReqVo.setEnterpriseUserId(borrowerId);
			logger.info("接口{}入参:" + JSONObject.toJSONString(enterpriseEditLogReqVo), IEnterpriseEditLogService.class);
			UserCommonResult<PageInfo<EnterpriseEditLogResVo>> logResult = enterpriseEditLogService
					.list(enterpriseEditLogReqVo, 1, 2000);
			logger.info("接口{}出参:" + JSONObject.toJSONString(logResult), IEnterpriseEditLogService.class);
			enterpriseUserResBo.setLogs(logResult.getBusinessObject().getList());
			UserAccountInfoResVo userAccountInfoReqVo = new UserAccountInfoResVo();
			userAccountInfoReqVo.setUserId(result.getBusinessObject().getUserId());
			com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo userInfoResVo = getBalance(result.getBusinessObject().getUserId());
			UserAccountInfoResVo userAccountInfo = new UserAccountInfoResVo();
			userAccountInfo.setBalance(userInfoResVo.getBalance());
			enterpriseUserResBo.setUserAccountInfoResVo(userAccountInfo);
//			AccountCommonResult<MainAccountInfoResVo> accountCommonResult = queryAccountService
//					.queryGateWayAccountInfo(result.getBusinessObject().getUserId());
//			logger.info("接口{}出参:" + JSONObject.toJSONString(accountCommonResult));
//			if (accountCommonResult.isSuccess()) {
//				MainAccountInfoResVo businessObject = accountCommonResult.getBusinessObject();
//				if (null != businessObject.getRedmoneyBalance() && null != businessObject.getRedmoneyFreezeAmount()) {
//					enterpriseUserResBo.setRedMoney(
//							businessObject.getRedmoneyBalance().subtract(businessObject.getRedmoneyFreezeAmount()));
//				}
//			}
			// TODO
			enterpriseUserResBo.setRedMoney(new Money());
			return Result.success(enterpriseUserResBo);
		} catch (Exception e) {
			logger.error("取得企业基本信息异常", e);
		}
		return Result.error("取得企业基本信息异常");
	}

	/**
	 * 企业投资信息
	 */
	@RequestMapping(value = "/borrowerlist/getInvestInfo/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public Result getInvestInfo(@PathVariable("userId") String userId) {
		logger.info("接口{}入参:" + JSONObject.toJSONString(userId), IEnterpriseUserService.class);
		EnterpriseUserReqVo enterpriseUserReqVo = new EnterpriseUserReqVo();
		enterpriseUserReqVo.setUserId(userId);
		UserCommonResult<EnterpriseUserResVo> result = enterpriseUserService.getEnterpriseUser(enterpriseUserReqVo);
		logger.info("接口{}出参:" + JSONObject.toJSONString(result), IEnterpriseUserService.class);
		return Result.success(result);
	}

	/**
	 * 企业借款信息
	 */
	@RequestMapping(value = "/borrowerlist/getLoanInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result getLoanInfo(BorrowReqVo borrowReqVo, int pageNo, int pageSize) {

		try {
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			TradeCommonResult<PageInfo<BorrowResVo>> result = borrowService.listBorrow(borrowReqVo, pageNo, pageSize);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IBorrowService.class);
			PageInfo<BorrowResBo> newPage = new PageInfo<BorrowResBo>();
			List<BorrowResBo> borrowResBoList = BeanMapper.mapList(result.getBusinessObject().getList(),
					BorrowResBo.class);
			for (BorrowResBo borrowResBo : borrowResBoList) {
				String date = BorrowController.computeBorrowDates(borrowResBo.getLastReleaseTime(),
						borrowResBo.getLastRepayTime());
				borrowResBo.setInvestDate(date);

				// 查询下期还款信息
				BorrowRepayPlanReqVo borrowRepayPlanReqVo = new BorrowRepayPlanReqVo();
				borrowRepayPlanReqVo.setBorrowCode(borrowResBo.getId());
				logger.info("接口{}入参:" + JSONObject.toJSONString(borrowRepayPlanReqVo), IBorrowRepayPlanService.class);
				TradeCommonResult<BorrowRepayPlanResVo> borrowPlanReault = borrowRepayPlanService
						.get(borrowRepayPlanReqVo);
				logger.info("接口{}出参:" + JSONObject.toJSONString(borrowPlanReault), IBorrowRepayPlanService.class);
				BorrowRepayPlanResVo businessObject = borrowPlanReault.getBusinessObject();

				// 下次还款金额
				// 下期应还= 本金 + 利息 + 罚息
				Money nextReapyAmount = new Money();
				if (businessObject.getPrincipalAmount() != null)
					nextReapyAmount = nextReapyAmount.addTo(businessObject.getPrincipalAmount());
				if (businessObject.getInterestAmount() != null)
					nextReapyAmount = nextReapyAmount.addTo(businessObject.getInterestAmount());// 金额
				if (businessObject.getPenaltyAmount() != null)
					nextReapyAmount = nextReapyAmount.addTo(businessObject.getPenaltyAmount());
				borrowResBo.setNextPayAmount(nextReapyAmount.getAmount().toString());
				String nextPayDate = DateUtil.formatDate(businessObject.getInterestEndDate(), "YYYY-MM-dd");
				borrowResBo.setNextPayDate(nextPayDate);
				if (null != borrowResBo.getBorrowRate()) {
					borrowResBo.setBorrowRate(borrowResBo.getBorrowRate().divide(new BigDecimal(100)));
				}
			}
			newPage = BeanMapper.map(result.getBusinessObject(), PageInfo.class);
			newPage.setList(borrowResBoList);
			return Result.success(newPage);
		} catch (Exception e) {
			logger.error("取得企业借款信息异常", e);
		}
		return Result.error("取得企业借款信息异常");
	}

	/**
	 * 取得银行信息
	 */
	@RequestMapping(value = "/{menu}/getBankInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result getBankInfo() {
		BankInfoReqVo bankInfoReqVo = new BankInfoReqVo();
		logger.info("接口{}入参:" + JSONObject.toJSONString(bankInfoReqVo), BankInfoClient.class);
		BasedataCommonResult<PageInfo<BankInfoResVo>> result = bankInfoClient.list(bankInfoReqVo, 1, 1000);
		logger.info("接口{}出参:" + JSONObject.toJSONString(result), BankInfoClient.class);
		if (result.isSuccess()) {
			return Result.success(result);
		} else
			return Result.error("从基础数据取得银行信息异常");
	}
	
	
	/**
	 * 取得PERSON信息
	 */
	@RequestMapping(value = "/{menu}/getPersonInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result getPersonInfo(String userId) {
		UserCommonResult<UserInfoResVo> userInfo = userInfoService.getByRoleUserId(userId);
		UserRoleInfoReqVo userRoleInfoReqVo = new UserRoleInfoReqVo();
		userRoleInfoReqVo.setUserRoleId(userId);
		UserCommonResult<UserRoleInfoResVo> roleInfo = userRoleInfo.get(userRoleInfoReqVo);
		UserExtendInfoReqVo userExtendInfoReqVo = new UserExtendInfoReqVo();
		userExtendInfoReqVo.setUserId(userInfo.getBusinessObject().getId());
		UserCommonResult<UserExtendInfoResVo> einf = userExtendInfoService.getUserExtendInfo(userExtendInfoReqVo);
//		BindBankCardReqVo bindBankCardReqVo = new BindBankCardReqVo();
//		bindBankCardReqVo.setUserId(userId);
		UserCommonResult<BindBankCardResVo> bbcr = bindBankCardService.getByRoleUserId(userId);
		List result = new ArrayList();
		result.add(userInfo);
		result.add(roleInfo);
		result.add(einf);
		result.add(bbcr);
		if (userInfo.isSuccess()) {
			return Result.success(result);
		} else
			return Result.error("取得信息异常");
	}

	/**
	 * 企业借款人充值
	 */
	@RequestMapping(value = "/borrowerlist/recharge", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "企业借款人充值")
	public Result recharge(DepositAdjustmentReqVo adjustmentRecordReqVo, HttpServletRequest request) {
		try {
			logger.info("接口{}入参:" + JSONObject.toJSONString(adjustmentRecordReqVo), IAdjustmentRecordService.class);
			TradeCommonResult<String> result = depositAdjustmentService.depositAdjustment(adjustmentRecordReqVo);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IAdjustmentRecordService.class);
			if (result.isSuccess()) {
				return Result.success(result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			logger.error("借款人充值失败", e);
		}
		return Result.error("借款人充值失败");
	}

}

package com.winsmoney.jajaying.boss.controller.user;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import com.winsmoney.jajaying.user.service.*;
import com.winsmoney.jajaying.user.service.enums.UserRoleType;
import com.winsmoney.jajaying.user.service.request.*;
import com.winsmoney.jajaying.user.service.response.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.award.service.IUserAwardService;
import com.winsmoney.jajaying.award.service.request.UserAwardReqVo;
import com.winsmoney.jajaying.award.service.response.AwardCommonResult;
import com.winsmoney.jajaying.award.service.response.UserAwardResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.dao.po.LnvestmentPo;
import com.winsmoney.jajaying.boss.dao.po.LnvitePeopleListPo;
import com.winsmoney.jajaying.boss.domain.RedmoneyInfoDomain;
import com.winsmoney.jajaying.boss.domain.model.RedmoneyInfo;
import com.winsmoney.jajaying.boss.domain.mq.BossMQSender;
import com.winsmoney.jajaying.boss.domain.mq.MsgMQTopic;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.deposit.service.IInfoService;
import com.winsmoney.jajaying.deposit.service.request.BaseReqVo;
import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;
import com.winsmoney.jajaying.redmoney.service.IBigRedmoneyStatisticsService;
import com.winsmoney.jajaying.redmoney.service.IRedMoneyQueryService;
import com.winsmoney.jajaying.redmoney.service.IRedMoneyService;
import com.winsmoney.jajaying.redmoney.service.enums.IssuerType;
import com.winsmoney.jajaying.redmoney.service.enums.MerchantCode;
import com.winsmoney.jajaying.redmoney.service.enums.RedMoneySourceTypeEnum;
import com.winsmoney.jajaying.redmoney.service.enums.RedMoneyTypeEnum;
import com.winsmoney.jajaying.redmoney.service.request.BigRedmoneyStatisticsReqVo;
import com.winsmoney.jajaying.redmoney.service.request.CreateRedMoneyReqVo;
import com.winsmoney.jajaying.redmoney.service.request.SendRedMoneyReqVo;
import com.winsmoney.jajaying.redmoney.service.response.BigRedmoneyStatisticsResVo;
import com.winsmoney.jajaying.redmoney.service.response.CreateRedMoneyResVo;
import com.winsmoney.jajaying.redmoney.service.response.QueryRedMoneyTradeResVo;
import com.winsmoney.jajaying.redmoney.service.response.RedmoneyCommonResult;
import com.winsmoney.jajaying.redmoney.service.response.SendRedMoneyResVo;
import com.winsmoney.jajaying.trade.service.IInviteProfitService;
import com.winsmoney.jajaying.trade.service.ILoanInvestorService;
import com.winsmoney.jajaying.trade.service.ILoanService;
import com.winsmoney.jajaying.trade.service.request.InviteProfitReqVo;
import com.winsmoney.jajaying.trade.service.request.LoanInvestorReqVo;
import com.winsmoney.jajaying.trade.service.request.LoanReqVo;
import com.winsmoney.jajaying.trade.service.response.LoanInvestorResVo;
import com.winsmoney.jajaying.trade.service.response.LoanInvestorStatisticsResVo;
import com.winsmoney.jajaying.trade.service.response.LoanResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.DateUtil;
import com.winsmoney.platform.framework.core.util.Money;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;
import com.winsmoney.platform.framework.redis.api.LockManager;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;

@Controller
@RequestMapping("/user/user_list")
public class IUserController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(IUserController.class);

	@Autowired
	private RedmoneyInfoDomain redMoneyDomain;
	@Autowired
	private IRedMoneyService redMoneyService;
	@Autowired
	private SequenceGenerator sequenceGenerator;
	// ????????????
	@Autowired
	private IEnterpriseUserService enterpriseUserService;
	@Autowired
	private IUserAuthService authService;
	@Autowired
	private IWxInfoService wxInfoService;
	@Autowired
	private IBindBankCardService bindBankCardService;
	@Autowired
	private IUserExtendInfoService extendInfoService;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserRoleInfoService userRoleInfoService;
	@Autowired
	private ILoanInvestorService loanInvestorService;
	@Autowired
	private ILoanService loanService;
	@Autowired
	private IInviteProfitService inviteProfitService;
	@Autowired
	private IUserAddressService userAddressService;
	@Autowired
	private IUserAwardService userAwardService;
	@Autowired
	private IUserRiskService userRiskService;
	@Autowired
	private IBigRedmoneyStatisticsService bigRedmoneyStatisticsService;
	@Resource(name = "bossJdbcTemplate")
	private JdbcTemplate template;
	@Autowired
	private BossMQSender bossMQSender;
	@Autowired
	private LockManager lockManager;
	@Autowired
	private IBindLogService iBindLogService;
	@Autowired
	private IRedMoneyQueryService redMoneyQueryService;
	@Autowired
	private IInfoService infoService;
	/**
	 * ????????????
	 */
	@RequestMapping("selectuserRiskService")
	@ResponseBody
	public Result selectuserRiskService(UserRiskReqVo userRiskReqVo) {
		UserCommonResult<UserRiskResVo> get = userRiskService.get(userRiskReqVo);
		if (get.isSuccess()) {
			return Result.success(get.getBusinessObject());
		} else {
			return Result.error(get.getResultCodeMsg());
		}
	}

	/**
	 * ????????????
	 *
	 * @param inviteProfitReqVo
	 * @return
	 */
	@RequestMapping("selectReceiptAward")
	@ResponseBody
	public PageInfo<UserAwardResVo> selectReceiptAward(UserAwardReqVo userAwardResVo, int pageNo, int pageSize) {
		AwardCommonResult<PageInfo<UserAwardResVo>> list = userAwardService.list(userAwardResVo, pageNo, pageSize);
		return list.getBusinessObject();
	}

	/**
	 * ????????????
	 *
	 * @param inviteProfitReqVo
	 * @return
	 */
	@RequestMapping("selectReceiptAddress")
	@ResponseBody
	public PageInfo<UserAddressResVo> selectReceiptAddress(UserAddressReqVo userAddressReqVo, int pageNo, int pageSize) {
		UserCommonResult<PageInfo<UserAddressResVo>> list = userAddressService.list(userAddressReqVo, pageNo, pageSize);
		return list.getBusinessObject();
	}

	/**
	 * ???????????????
	 */
	@RequestMapping("selectInvestorTotalAmountByUserId")
	@ResponseBody
	public LoanInvestorStatisticsResVo selectInvestorTotalAmountByUserId(String userId) {
		TradeCommonResult<LoanInvestorStatisticsResVo> investorTotalAmount = loanInvestorService.investorTotalAmount(userId);
		return investorTotalAmount.getBusinessObject();
	}

	/**
	 * ????????????
	 *
	 * @param userId
	 * @return
	 */
	@RequestMapping("selectInviterByUserId")
	@ResponseBody
	public Money selectInviterByUserId(InviteProfitReqVo inviteProfitReqVo) {
		TradeCommonResult<Money> queryTotalInvotedProfit = inviteProfitService.queryTotalInviteddProfit(inviteProfitReqVo);
		return queryTotalInvotedProfit.getBusinessObject();
	}

	/**
	 * ??????????????????
	 *
	 * @param userInfoReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("selectReferrerByUserId")
	@ResponseBody
	public int selectReferrerByUserId(UserInfoReqVo userInfoReqVo) {
		UserCommonResult<Integer> listUserInfo = userInfoService.countReferrer1(userInfoReqVo.getReferrer1());
		Integer invitedCounts = listUserInfo.getBusinessObject();
		return invitedCounts;
	}

	/**
	 * ???????????????
	 *
	 * @param userInfoReqVo
	 * @param pageNo
	 * @param pageSize
	 * @param inviteProfitReqVo
	 * @return
	 */
	@RequestMapping("lnvitePeopleList")
	@ResponseBody
	public PageInfo<LnvitePeopleListPo> LnvitePeopleList(UserInfoReqVo userInfoReqVo, int pageNo, int pageSize, InviteProfitReqVo inviteProfitReqVo) {
		PageInfo<LnvitePeopleListPo> newPage = new PageInfo<LnvitePeopleListPo>();
		UserCommonResult<PageInfo<UserInfoResVo>> listUserInfo = userInfoService.listUserInfo(userInfoReqVo, pageNo, pageSize);
		PageInfo<UserInfoResVo> userList = listUserInfo.getBusinessObject();
		List<LnvitePeopleListPo> lnvitePeopleList = new ArrayList<LnvitePeopleListPo>();
		List<UserInfoResVo> userinfolisttwo = listUserInfo.getBusinessObject().getList();
		for (int i = 0; i < userinfolisttwo.size(); i++) {
//			if(userinfolisttwo.get(i).getRegisterMobile() != null || !"".equals(userinfolisttwo.get(i).getRegisterMobile())) {
//				userinfolisttwo.get(i).setRegisterMobile(SensitiveInfoUtils.mobilePhone(userinfolisttwo.get(i).getRegisterMobile()));
//			}
			UserRoleInfoReqVo userRoleInfoReqVo = new UserRoleInfoReqVo();
			userRoleInfoReqVo.setUserRoleId( userinfolisttwo.get(i).getId() );
			UserCommonResult<UserRoleInfoResVo> result = userRoleInfoService.get( userRoleInfoReqVo ) ;
			LnvitePeopleListPo lnvitePeople = new LnvitePeopleListPo();
			lnvitePeople.setCreateTime(userinfolisttwo.get(i).getCreateTime());
			lnvitePeople.setRegisterMobile(userinfolisttwo.get(i).getRegisterMobile());
			lnvitePeople.setNickName(userinfolisttwo.get(i).getNickName());
			lnvitePeople.setLnvitePeopleStatus(result.getBusinessObject().getAuthState().toString());
			InviteProfitReqVo oneLevelReq = new InviteProfitReqVo();
			oneLevelReq.setInviter(userInfoReqVo.getReferrer1());
			oneLevelReq.setInvitee(userinfolisttwo.get(i).getId());
			TradeCommonResult<Money> oneLevelMoney = inviteProfitService.queryTotalInviteddProfit(oneLevelReq);
			if (null != oneLevelMoney && null != oneLevelMoney.getBusinessObject()) {
				lnvitePeople.setInviteAmount(oneLevelMoney.getBusinessObject());
			} else {
				lnvitePeople.setInviteAmount(new Money());
			}
			lnvitePeopleList.add(lnvitePeople);
		}
		newPage = BeanMapper.map(userList, PageInfo.class);
		newPage.setList(lnvitePeopleList);
		return newPage;
	}

	/**
	 * ????????????
	 *
	 * @param loanInvestorReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("selectLoanInvestor")
	@ResponseBody
	public PageInfo<LnvestmentPo> selectLoanInvestor(LoanReqVo loanReqVo, LoanInvestorReqVo loanInvestorReqVo, int pageNo, int pageSize) {
		PageInfo<LnvestmentPo> newPage = new PageInfo<LnvestmentPo>();
		TradeCommonResult<PageInfo<LoanInvestorResVo>> listLoanInvestor = loanInvestorService.listLoanInvestor(loanInvestorReqVo, pageNo, pageSize);
		PageInfo<LoanInvestorResVo> loanInvestorResList = listLoanInvestor.getBusinessObject();
		List<LoanInvestorResVo> loanInvestorResVoList = loanInvestorResList.getList();
		List<LnvestmentPo> lnvestmentPoList = new ArrayList<LnvestmentPo>();
		for (int i = 0; i < loanInvestorResVoList.size(); i++) {
			LnvestmentPo lnvestmentPo = new LnvestmentPo();
			lnvestmentPo.setLoanCode(loanInvestorResVoList.get(i).getLoanCode());
			lnvestmentPo.setInvestAmount(loanInvestorResVoList.get(i).getInvestAmount());
			lnvestmentPo.setInvestTime(loanInvestorResVoList.get(i).getInvestTime());
			lnvestmentPo.setRedMoneyAmount(loanInvestorResVoList.get(i).getRedMoneyAmount());
			lnvestmentPo.setActualPay(loanInvestorResVoList.get(i).getInvestAmount().subtract(loanInvestorResVoList.get(i).getRedMoneyAmount()));
			loanReqVo.setId(loanInvestorResVoList.get(i).getLoanCode());
			lnvestmentPo.setInvestorStatus(loanInvestorResVoList.get(i).getInvestorStatus());// ????????????
			TradeCommonResult<LoanResVo> listLoan = loanService.getLoan(loanReqVo);
			LoanResVo loanResVO = listLoan.getBusinessObject();
			lnvestmentPo.setTitle(loanResVO.getTitle());
			lnvestmentPo.setYearRate(loanResVO.getYearRate().divide( new BigDecimal( 100 )));
			lnvestmentPo.setProjectCycle((long) DateUtil.daysInterestBetween(loanResVO.getInterestEndDate(), loanResVO.getInterestStartDate()));
			lnvestmentPo.setStatus(loanResVO.getStatus());
			lnvestmentPoList.add(lnvestmentPo);
		}
		newPage = BeanMapper.map(loanInvestorResList, PageInfo.class);
		newPage.setList(lnvestmentPoList);
		return newPage;
	}

	/**
	 * ???????????????
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("selectAuthUserBuPhone")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "???????????????")
	public BindCardResVo selectAuthUserBuPhone(BindCardReqVo request) {
		UserCommonResult<BindCardResVo> unBindCard = authService.unBindCard(request);
		return unBindCard.getBusinessObject();
	}

	/**
	 * IWxInfoService
	 *
	 * @param request
	 * @return ??????????????????
	 */
	@RequestMapping("/selectWxUserById")
	@ResponseBody
	public WxInfoResVo selectWxUserById(WxInfoReqVo request) {
		UserCommonResult<WxInfoResVo> get = wxInfoService.get(request);
		return get.getBusinessObject();
	}

	/**
	 * IBindBankCardService
	 *
	 * @param bindBankCardReqVo
	 * @return ??????????????????
	 */
	@RequestMapping("/selectBindBackById")
	@ResponseBody
	public BindBankCardResVo selectBindBackById(BindBankCardReqVo bindBankCardReqVo) {
		UserCommonResult<BindBankCardResVo> get = bindBankCardService.getByRoleUserId(bindBankCardReqVo.getUserId());
		if (get.getBusinessObject().getCertNo() != null)
			get.getBusinessObject().setCertNo(SensitiveInfoUtils.idCardNum(get.getBusinessObject().getCertNo()));
		if (get.getBusinessObject().getBankCardNo() != null)
			get.getBusinessObject().setBankCardNo(SensitiveInfoUtils.bankCard(get.getBusinessObject().getBankCardNo()));
		if (get.getBusinessObject().getBankMobile() != null)
			get.getBusinessObject().setBankMobile(SensitiveInfoUtils.mobilePhone(get.getBusinessObject().getBankMobile()));
		return get.getBusinessObject();
	}

	/**
	 * IUserExtendInfoService
	 *
	 * @param userExtendInfoReqVo
	 * @return ??????????????????
	 */
	@RequestMapping("/selectUserExtendInfoById")
	@ResponseBody
	public UserExtendInfoResVo selectUserExtendInfoById(UserExtendInfoReqVo userExtendInfoReqVo) {
		UserCommonResult<UserExtendInfoResVo> getById = extendInfoService.getUserExtendInfo(userExtendInfoReqVo);
		return getById.getBusinessObject();
	}

	/**
	 * IEnterpriseUserService
	 *
	 * @param id
	 * @return ????????????
	 */
	@RequestMapping("getEnterPriseUser")
	@ResponseBody
	public PageInfo<EnterpriseUserResVo> getEnterPriseUser(EnterpriseUserReqVo enterpriseUserReqVo, int pageNo, int pageSize) throws Exception {
		if (StringUtils.isBlank(enterpriseUserReqVo.getLegalPersonName())) {
			enterpriseUserReqVo.setLegalPersonName(null);
		}
		if (StringUtils.isBlank(enterpriseUserReqVo.getEnterpriseName())) {
			enterpriseUserReqVo.setEnterpriseName(null);
		}
		UserCommonResult<PageInfo<EnterpriseUserResVo>> getEnterpriseUser = enterpriseUserService.listEnterpriseUser(enterpriseUserReqVo, pageNo, pageSize);
		PageInfo<EnterpriseUserResVo> enterPriseUserList = new PageInfo<EnterpriseUserResVo>();
		enterPriseUserList = getEnterpriseUser.getBusinessObject();
		return enterPriseUserList;
	}

	/***
	 * IUserInfoService ??????ID????????????
	 * 
	 * @param userId
	 * @return ????????????
	 */
	@RequestMapping("/selectUserById")
	@ResponseBody
	public UserInfoResVo selectUserById(String userId,Integer isCustomer) {
		UserCommonResult<UserInfoResVo> listUserInfo = userInfoService.getByRoleUserId(userId);
		UserInfoReqVo userInfoReq = new UserInfoReqVo();
		
		UserRoleInfoReqVo userRoleInfoReqVo = new UserRoleInfoReqVo();
		userRoleInfoReqVo.setUserRoleId(userId );
		UserCommonResult<UserRoleInfoResVo> result = userRoleInfoService.get( userRoleInfoReqVo ) ;
		
		listUserInfo.getBusinessObject().setEditedBy(result.getBusinessObject().getAuthState().getCode());
		if (listUserInfo.getBusinessObject().getReferrer1() != null) {
			userInfoReq.setId(listUserInfo.getBusinessObject().getReferrer1());
			UserCommonResult<UserInfoResVo> getUserInfo = userInfoService.getByRoleUserId(listUserInfo.getBusinessObject().getReferrer1());
			listUserInfo.getBusinessObject().setReferrer1(getUserInfo.getBusinessObject().getRegisterMobile());
		}
		if (listUserInfo.getBusinessObject().getReferrer2() != null) {
			userInfoReq.setId(listUserInfo.getBusinessObject().getReferrer2());
			UserCommonResult<UserInfoResVo> getUserInfo2 = userInfoService.getByRoleUserId(listUserInfo.getBusinessObject().getReferrer2());
			listUserInfo.getBusinessObject().setReferrer2(getUserInfo2.getBusinessObject().getRegisterMobile());
		}
		if (listUserInfo.getBusinessObject().getAgent() != null) {
			userInfoReq.setId(listUserInfo.getBusinessObject().getAgent());
			UserCommonResult<UserInfoResVo> getUserInfo3 = userInfoService.getByRoleUserId(listUserInfo.getBusinessObject().getAgent());
			listUserInfo.getBusinessObject().setAgent(getUserInfo3.getBusinessObject().getRegisterMobile());
		}
		if (listUserInfo.getBusinessObject().getCertNo() != null)
			if(null != isCustomer && isCustomer == 1) {
				listUserInfo.getBusinessObject().setCertNo(StringUtils.leftPad(StringUtils.right(listUserInfo.getBusinessObject().getCertNo(), 4), StringUtils.length(listUserInfo.getBusinessObject().getCertNo()), "*"));
			}else{
				listUserInfo.getBusinessObject().setCertNo(SensitiveInfoUtils.idCardNum(listUserInfo.getBusinessObject().getCertNo()));
			}
//		if (listUserInfo.getBusinessObject().getRegisterMobile() != null)
//			listUserInfo.getBusinessObject().setRegisterMobile(SensitiveInfoUtils.mobilePhone(listUserInfo.getBusinessObject().getRegisterMobile()));
//		if (listUserInfo.getBusinessObject().getReferrer1() != null)
//			listUserInfo.getBusinessObject().setReferrer1(SensitiveInfoUtils.mobilePhone(listUserInfo.getBusinessObject().getReferrer1()));
//		if (listUserInfo.getBusinessObject().getReferrer2() != null)
//			listUserInfo.getBusinessObject().setReferrer2(SensitiveInfoUtils.mobilePhone(listUserInfo.getBusinessObject().getReferrer2()));
		return listUserInfo.getBusinessObject();
	}

	/**
	 * IUserInfoService ????????????
	 *
	 * @param msgTemplateResVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userSelectAll")
	@ResponseBody
	public PageInfo<UserInfoResVo> getUserInfoSelected(UserInfoReqVo userInfoReqVo, int pageNo, int pageSize, String userId) throws Exception {
		try {
			if (StringUtils.isBlank(userInfoReqVo.getRegisterMobile())) {
				userInfoReqVo.setRegisterMobile(null);
			}
			if (StringUtils.isBlank(userInfoReqVo.getRealName())) {
				userInfoReqVo.setRealName(null);
			}
			if (StringUtils.isBlank(userInfoReqVo.getId())) {
				userInfoReqVo.setId(null);
			}
			if (StringUtils.isBlank(userInfoReqVo.getNickName())) {
				userInfoReqVo.setNickName(null);
			}
			UserCommonResult<PageInfo<UserInfoResVo>> listUserInfo = userInfoService.listUserInfo(userInfoReqVo, pageNo, pageSize);
			PageInfo<UserInfoResVo> userinfoList = new PageInfo<UserInfoResVo>();
//			UserInfoReqVo userInfoReq = new UserInfoReqVo();
			for (int i = 0; i < listUserInfo.getBusinessObject().getList().size(); i++) {
				if (listUserInfo.getBusinessObject().getList().get(i).getReferrer1() != null) {
//					userInfoReq.setId(listUserInfo.getBusinessObject().getList().get(i).getReferrer1());
					UserCommonResult<UserInfoResVo> getUserInfo = userInfoService.getByRoleUserId(listUserInfo.getBusinessObject().getList().get(i).getReferrer1());
					listUserInfo.getBusinessObject().getList().get(i).setReferrer1(SensitiveInfoUtils.mobilePhone(getUserInfo.getBusinessObject().getRegisterMobile()));
				}
				if (listUserInfo.getBusinessObject().getList().get(i).getReferrer2() != null) {
//					userInfoReq.setId(listUserInfo.getBusinessObject().getList().get(i).getReferrer2());
					UserCommonResult<UserInfoResVo> getUserInfo2 = userInfoService.getByRoleUserId(listUserInfo.getBusinessObject().getList().get(i).getReferrer2());
					listUserInfo.getBusinessObject().getList().get(i).setReferrer2(SensitiveInfoUtils.mobilePhone(getUserInfo2.getBusinessObject().getRegisterMobile()));
				}
			}
			userinfoList = listUserInfo.getBusinessObject();
			for (UserInfoResVo userInfoResVo : userinfoList.getList()) {
				UserRoleInfoReqVo userRoleInfoReqVo = new UserRoleInfoReqVo();
				userRoleInfoReqVo.setUserId( userInfoResVo.getId());
				UserCommonResult<UserRoleInfoResVo> result = userRoleInfoService.get( userRoleInfoReqVo ) ;
				if(result.isSuccess() && result.getBusinessObject().getAuthState() != null) {
					userInfoResVo.setEditedBy(result.getBusinessObject().getAuthState().getCode());
				}
				if (userInfoResVo.getCertNo() != null)
					userInfoResVo.setCertNo(SensitiveInfoUtils.idCardNum(userInfoResVo.getCertNo()));
				if (userInfoResVo.getRegisterMobile() != null)
					userInfoResVo.setRegisterMobile(SensitiveInfoUtils.mobilePhone(userInfoResVo.getRegisterMobile()));
			}
			return userinfoList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}

	/**
	 * IUserInfoService ????????????(???????????????)
	 * 
	 * @param userInfoReqVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userSelectByPhone")
	@ResponseBody
	public Result userSelectByPhone(UserInfoReqVo userInfoReqVo, int pageNo, int pageSize) {
		try {
			if (StringUtils.isEmpty(userInfoReqVo.getRegisterMobile())) {
				userInfoReqVo.setRegisterMobile(null);
			}
			if (StringUtils.isEmpty(userInfoReqVo.getNickName())) {
				userInfoReqVo.setNickName(null);
			}
			if (StringUtils.isEmpty(userInfoReqVo.getId())) {
				userInfoReqVo.setId(null);
			}
			UserCommonResult<PageInfo<UserInfoResVo>> result = userInfoService.listUserInfo(userInfoReqVo, pageNo, pageSize);
			if (result.isSuccess()) {
				for(UserInfoResVo u : result.getBusinessObject().getList()) {
					if(u.getRegisterMobile() != null && !"".equals(u.getRegisterMobile())) {
						u.setRegisterMobile(SensitiveInfoUtils.mobilePhone(u.getRegisterMobile()));
					}
				}
				return Result.success(result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}

		} catch (Exception e) {
			logger.error("????????????????????????????????????", e);
		}
		return Result.error("????????????????????????????????????");
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param userInfoReqVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userSelectByNickName")
	@ResponseBody
	public UserInfoResVo userSelectByNickName(UserInfoReqVo userInfoReqVo) throws Exception {
		if (StringUtils.isBlank(userInfoReqVo.getNickName())) {
			return null;
		}
		UserCommonResult<UserInfoResVo> listUserInfo = userInfoService.getUserInfoByName(userInfoReqVo.getNickName(), UserRoleType.INVEST.getCode());
		return listUserInfo.getBusinessObject();
	}

	@RequestMapping("/sendRedMoney")
	@ResponseBody
	public Map sendRedMoney(SendRedMoneyReqVo sendRedMoneyReqVo) throws Exception {
		Map result = new HashMap<String, String>();
		UserRoleInfoReqVo userRole = new UserRoleInfoReqVo();
		userRole.setUserId( sendRedMoneyReqVo.getUserId() );
		userRole.setRoleCode( UserRoleType.INVEST);
		UserCommonResult<UserRoleInfoResVo> res = userRoleInfoService.get( userRole );
		UserCommonResult<UserInfoResVo> listUserInfo = userInfoService.getByRoleUserId(sendRedMoneyReqVo.getUserId());
		UserInfoResVo userInfo = listUserInfo.getBusinessObject();
		sendRedMoneyReqVo.setUserId( res.getBusinessObject().getUserRoleId() );
		sendRedMoneyReqVo.setSendSms(true);
		sendRedMoneyReqVo.setRealName(userInfo.getRealName());
		sendRedMoneyReqVo.setRegistrationId(userInfo.getRegistrationId());
		sendRedMoneyReqVo.setDeviceType(userInfo.getFrom());
		sendRedMoneyReqVo.setMobile(userInfo.getRegisterMobile());
		sendRedMoneyReqVo.setOutOrderNo(sequenceGenerator.getUUID());
		sendRedMoneyReqVo.setTradeTime(new Date());
		sendRedMoneyReqVo.setRedmoneyType(RedMoneyTypeEnum.CASH);
		sendRedMoneyReqVo.setSourceTypeEnum(RedMoneySourceTypeEnum.SYSTEM);
		sendRedMoneyReqVo.setMerchantCode(MerchantCode.P2P);
		sendRedMoneyReqVo.setIssuer(IssuerType.CUSTOMER);
		RedmoneyCommonResult<SendRedMoneyResVo> sendRedMoneyResult = redMoneyService.sendRedMoney(sendRedMoneyReqVo);
		RedmoneyInfo redmoneyInfo = new RedmoneyInfo();
		if (sendRedMoneyResult.isSuccess()) {
			redmoneyInfo.setType("0");// ??????
			redmoneyInfo.setUserId(sendRedMoneyReqVo.getUserId());// ??????ID
			redmoneyInfo.setAccountMoney(sendRedMoneyReqVo.getAmount());// ????????????
			redmoneyInfo.setRealName(sendRedMoneyReqVo.getRealName());// ????????????
			redmoneyInfo.setTimes(sendRedMoneyReqVo.getExpireDay());// ????????????
			redmoneyInfo.setPhone(sendRedMoneyReqVo.getMobile());// ?????????
			redmoneyInfo.setStatus("0");// 0??????
			int insert = redMoneyDomain.insert(redmoneyInfo);
			result.put("result", "????????????");
		} else {
			redmoneyInfo.setType("1");// ??????
			redmoneyInfo.setUserId(sendRedMoneyReqVo.getUserId());// ??????ID
			redmoneyInfo.setAccountMoney(sendRedMoneyReqVo.getAmount());// ????????????
			redmoneyInfo.setRealName(sendRedMoneyReqVo.getRealName());// ????????????
			redmoneyInfo.setTimes(sendRedMoneyReqVo.getExpireDay());// ????????????
			redmoneyInfo.setPhone(sendRedMoneyReqVo.getMobile());// ?????????
			redmoneyInfo.setStatus("0");// 0??????
			int insert = redMoneyDomain.insert(redmoneyInfo);
			result.put("result", sendRedMoneyResult.getResultCodeMsg());
		}
		return result;
	}

	/**
	 * ??????????????????
	 */
	@RequestMapping("/sendRedMoney/list")
	@ResponseBody
	public PageInfo<RedmoneyInfo> list(RedmoneyInfo condition, int pageNo, int pageSize) {
		PageInfo<RedmoneyInfo> list = redMoneyDomain.list(condition, pageNo, pageSize);
		return list;
	}

	/**
	 * ????????????
	 */
	@RequestMapping("/selectAccountInfo")
	@ResponseBody
	public Result selectAccountInfo(String userId) {
		AccountResForm accountResForm = new AccountResForm();
	     RedmoneyCommonResult<QueryRedMoneyTradeResVo> result = redMoneyQueryService.queryRedMoneyBalance(userId);
	        if(result.isFailure()) {
	            throw new RuntimeException(result.getResultCodeMsg());
	        }
	        QueryRedMoneyTradeResVo resVo=result.getBusinessObject();
	        BaseReqVo baseReqVo=new BaseReqVo();
	        baseReqVo.setUserId(userId);
	        DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> get = infoService.queryUserInfo(baseReqVo);
	        if(get.isFailure()) {
	            throw new RuntimeException(get.getResultCodeMsg());
	       } 
		
		if (result.isSuccess()) {
			QueryRedMoneyTradeResVo businessObject = result.getBusinessObject();
			com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo a = get.getBusinessObject();
			Money balance = businessObject.getBalance();
			Money freezeAmount = a.getFreezeAmount();
			if (balance != null && freezeAmount != null) {
				accountResForm.setBalance(a.getAvailableBalance());// ????????????
				accountResForm.setFreezeAmount(a.getFreezeAmount());
			}
			accountResForm.setRedMoneyBalance(businessObject.getBalance());
			return Result.success(accountResForm);
		} else
			return Result.error("?????????????????????????????????");
	}

	/**
	 * ????????????
	 */
	@RequestMapping("/cancelRealNameAuth")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "????????????")
	public Result cancelRealNameAuth(String userId, String certNo) {
//		UserInfoReqVo userInfoReqVo = new UserInfoReqVo();
//		userInfoReqVo.setCertNo(certNo);
		UserCommonResult<UserInfoResVo> getUserInfo = userInfoService.getByCertNo(certNo,UserRoleType.INVEST.getCode());
		if (getUserInfo.isSuccess()) {
			if (userId.equals(getUserInfo.getBusinessObject().getId())) {
				ClearUserAuthReqVo realNameAuthReqVo = new ClearUserAuthReqVo();
				realNameAuthReqVo.setUserId(userId);
				UserCommonResult<ClearUserAuthResVo> clearUserAuth = authService.clearUserAuth(realNameAuthReqVo);
				if (clearUserAuth.isSuccess()) {
					return Result.success(clearUserAuth.getBusinessObject());
				} else {
					return Result.error(clearUserAuth.getResultCodeMsg());
				}
			} else {
				getUserInfo.setResultCodeMsg("????????????????????????");
				return Result.error(getUserInfo.getResultCodeMsg());
			}
		} else {
			return Result.error(getUserInfo.getResultCodeMsg());
		}
	}

	/**
	 * ????????????????????????
	 */
	@RequestMapping("/bigRedMoneyList")
	@ResponseBody
	public PageInfo<BigRedmoneyStatisticsResVo> bigRedMoneyList(BigRedmoneyStatisticsReqVo bigRedmoneyStatisticsReqVo, int pageNo, int pageSize) {
		try {
			if (bigRedmoneyStatisticsReqVo.getUserId() == "") {
				bigRedmoneyStatisticsReqVo.setUserId(null);
			}
			RedmoneyCommonResult<PageInfo<BigRedmoneyStatisticsResVo>> list = bigRedmoneyStatisticsService.list(bigRedmoneyStatisticsReqVo, pageNo, pageSize);
			return list.getBusinessObject();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ??????????????????
	 */
	@RequestMapping("/createBigRedMoney")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "??????????????????")
	public Result createBigRedMoney(CreateRedMoneyReqVo createRedMoneyReqVo) {
		createRedMoneyReqVo.setInvestCode(sequenceGenerator.getUUID());
		createRedMoneyReqVo.setTradeTime(new Date());
		RedmoneyCommonResult<CreateRedMoneyResVo> createBigRedMoney = redMoneyService.bossCreateQuotaBigRedMoney(createRedMoneyReqVo);
		if (createBigRedMoney.isSuccess()) {
			return Result.success(createBigRedMoney.getBusinessObject());
		} else {
			return Result.error(createBigRedMoney.getResultCodeMsg());
		}
	}

	/**
	 * ????????????????????????
	 */
	@RequestMapping("/createBatchRedMoney")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "????????????????????????")
	public Result createBatchRedMoney(Money money) {
		String resultData = "success";
		Date now = new Date();
		Date now_10 = new Date(now.getTime() - 600000); // 10??????????????????
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ?????????????????????????????????
		String nowTime_10 = dateFormat.format(now_10);
		int count = 0;
		int successNum = 0;
		List<Map<String, Object>> result = null;
		Lock lock = lockManager.createLock("createBatchRedMoney");
		try {
			if (lock.tryLock()) {
				do {
					result = template.queryForList("select c_user_id from t_tmp_user where c_create_date <= ? ORDER BY c_create_date limit ?,100 ", nowTime_10, count);
					for (Map m : result) {
						String userId = m.get("c_user_id").toString();
						Map data = new HashMap();
						String requestId = sequenceGenerator.getUUID();
						data.put("userId", userId);
						data.put("earningAmount", money);
						data.put("investCode", requestId);
						data.put("tradeTime", now);
						boolean returnResult = bossMQSender.sendMQ(MsgMQTopic.PAY_SHARE_PACKET_BOSS, data);
						if (!returnResult) {
							resultData = "error";
						} else {
							successNum++;
						}
					}
					count += 100;
				} while (result.size() == 100);
			} else {
				resultData = "lockerror";
			}
		} catch (DataAccessException e1) {
			resultData = "error";
		} finally {
			lock.unlock();
		}

		if ("success".equals(resultData)) {
			return Result.success(resultData);
		} else if ("lockerror".equals(resultData)) {
			return Result.success("lockerror");
		} else {
			if (successNum > 0) {
				return Result.error("literror");
			} else {
				return Result.error("allerror");
			}
		}

	}

	/***
	 * IUserInfoService
	 *
	 * @param userId
	 * @return ????????????
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public UserInfoResVo getById(String userId) {
		UserCommonResult<UserInfoResVo> byIdResult = userInfoService.getByRoleUserId(userId);
		return byIdResult.getBusinessObject();
	}
	// TODO: 2018/10/22  ???????????? ?????????????????????????????????????????????
	@RequestMapping("/updateUserGroup")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "???????????????")
	public Result updateUserGroup(UserInfoReqVo userInfoReqvo) {
		UserCommonResult<Integer> updateResult = null;
		try {
			updateResult = userInfoService.updateUserGroupByUserId(userInfoReqvo.getId() , userInfoReqvo.getGroup());
			if (updateResult.isSuccess()) {
				return Result.success("?????????????????????");
			} else
				return Result.error("?????????????????????");
		} catch (Exception e) {
			logger.error("??????????????????????????????", e);
			return Result.error("??????????????????????????????");
		}
	}

	/**
	 * ???????????????????????????????????????????????????
	 * 
	 * @param bindLogReqVo
	 *            ????????????
	 * @param pageNo
	 *            ????????????
	 * @param pageSize
	 *            ????????????
	 * @return ???????????????????????????????????????????????????
	 */
	@RequestMapping("/queryBindLog")
	@ResponseBody
	public Result queryBindLog(BindLogReqVo vo, int pageNo, int pageSize) {
		try {
			UserCommonResult<PageInfo<BindLogResVo>> result = iBindLogService.list(vo, pageNo, pageSize);
			if (result.isSuccess()) {
				return Result.success(result);
			} else {
				return Result.error(result.getResultCodeMsg());
			}

		} catch (Exception e) {
			logger.error("???????????????????????????????????????????????????", e);
		}
		return Result.error("???????????????????????????????????????????????????");
	}

}
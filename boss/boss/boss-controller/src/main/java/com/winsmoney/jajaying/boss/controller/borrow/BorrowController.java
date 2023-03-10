package com.winsmoney.jajaying.boss.controller.borrow;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.controller.BaseController;
import com.winsmoney.jajaying.boss.controller.login.AdminUserController;
import com.winsmoney.jajaying.boss.controller.model.BorrowExtends;
import com.winsmoney.jajaying.boss.controller.model.BorrowVo;
import com.winsmoney.jajaying.boss.controller.model.CityListType;
import com.winsmoney.jajaying.boss.controller.model.EntRelationType;
import com.winsmoney.jajaying.boss.controller.model.IndustryType;
import com.winsmoney.jajaying.boss.controller.model.InterestExcelPlans;
import com.winsmoney.jajaying.boss.controller.model.InterestPlans;
import com.winsmoney.jajaying.boss.controller.model.LoanVo;
import com.winsmoney.jajaying.boss.controller.model.MarriageType;
import com.winsmoney.jajaying.boss.controller.model.RepayUserVo;
import com.winsmoney.jajaying.boss.controller.project.LoanResForm;
import com.winsmoney.jajaying.boss.controller.utils.ExcelUtils;
import com.winsmoney.jajaying.boss.controller.utils.FileUDUtils;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.NotificationDomain;
import com.winsmoney.jajaying.boss.domain.model.BorrowResBo;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.PropertiesFactoryUtil;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.deposit.service.IInfoService;
import com.winsmoney.jajaying.deposit.service.request.BaseReqVo;
import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;
import com.winsmoney.jajaying.protocol.service.ISealImageRecordService;
import com.winsmoney.jajaying.protocol.service.enums.TransStatus;
import com.winsmoney.jajaying.protocol.service.request.SealImageRecordReqVo;
import com.winsmoney.jajaying.protocol.service.response.ProtocolCommonResult;
import com.winsmoney.jajaying.protocol.service.response.SealImageRecordResVo;
import com.winsmoney.jajaying.trade.service.IBorrowExtendService;
import com.winsmoney.jajaying.trade.service.IBorrowFileService;
import com.winsmoney.jajaying.trade.service.IBorrowRepayPlanService;
import com.winsmoney.jajaying.trade.service.IBorrowService;
import com.winsmoney.jajaying.trade.service.IBorrowerService;
import com.winsmoney.jajaying.trade.service.IEarlyRepayWhiteListService;
import com.winsmoney.jajaying.trade.service.IGuaranteeService;
import com.winsmoney.jajaying.trade.service.IInterestPlansService;
import com.winsmoney.jajaying.trade.service.ILoanInvestorService;
import com.winsmoney.jajaying.trade.service.ILoanOperService;
import com.winsmoney.jajaying.trade.service.ILoanService;
import com.winsmoney.jajaying.trade.service.IOtherRepaySettingService;
import com.winsmoney.jajaying.trade.service.IRepayRecordDetailService;
import com.winsmoney.jajaying.trade.service.IRepayRecordService;
import com.winsmoney.jajaying.trade.service.enums.BorrowExtendType;
import com.winsmoney.jajaying.trade.service.enums.BorrowStatus;
import com.winsmoney.jajaying.trade.service.enums.FileType;
import com.winsmoney.jajaying.trade.service.enums.RepayType;
import com.winsmoney.jajaying.trade.service.enums.RepayWay;
import com.winsmoney.jajaying.trade.service.request.BorrowExtendReqVo;
import com.winsmoney.jajaying.trade.service.request.BorrowFileReqVo;
import com.winsmoney.jajaying.trade.service.request.BorrowRepayPlanReqVo;
import com.winsmoney.jajaying.trade.service.request.BorrowReqVo;
import com.winsmoney.jajaying.trade.service.request.BorrowerReqVo;
import com.winsmoney.jajaying.trade.service.request.EarlyRepayWhiteListReqVo;
import com.winsmoney.jajaying.trade.service.request.GuaranteeReqVo;
import com.winsmoney.jajaying.trade.service.request.InterestPlansReqVo;
import com.winsmoney.jajaying.trade.service.request.LoanReqVo;
import com.winsmoney.jajaying.trade.service.request.OtherRepaySettingReqVo;
import com.winsmoney.jajaying.trade.service.request.RepayRecordDetailReqVo;
import com.winsmoney.jajaying.trade.service.request.RepayRecordReqVo;
import com.winsmoney.jajaying.trade.service.request.RepayReqVo;
import com.winsmoney.jajaying.trade.service.request.RepayWarningReqVo;
import com.winsmoney.jajaying.trade.service.response.BorrowExtendResVo;
import com.winsmoney.jajaying.trade.service.response.BorrowFileResVo;
import com.winsmoney.jajaying.trade.service.response.BorrowRepayPlanResVo;
import com.winsmoney.jajaying.trade.service.response.BorrowResVo;
import com.winsmoney.jajaying.trade.service.response.BorrowerResVo;
import com.winsmoney.jajaying.trade.service.response.EarlyRepayWhiteListResVo;
import com.winsmoney.jajaying.trade.service.response.GuaranteeResVo;
import com.winsmoney.jajaying.trade.service.response.InterestPlansResVo;
import com.winsmoney.jajaying.trade.service.response.LoanFileResVo;
import com.winsmoney.jajaying.trade.service.response.LoanInvestorResVo;
import com.winsmoney.jajaying.trade.service.response.LoanResVo;
import com.winsmoney.jajaying.trade.service.response.OtherRepaySettingResVo;
import com.winsmoney.jajaying.trade.service.response.RepayRecordDetailResVo;
import com.winsmoney.jajaying.trade.service.response.RepayRecordResVo;
import com.winsmoney.jajaying.trade.service.response.RepayWarningResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IEnterpriseUserService;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.IUserService;
import com.winsmoney.jajaying.user.service.enums.UserType;
import com.winsmoney.jajaying.user.service.request.BorrowUserInfoReqVo;
import com.winsmoney.jajaying.user.service.request.EnterpriseUserReqVo;
import com.winsmoney.jajaying.user.service.response.BorrowUserInfoResVo;
import com.winsmoney.jajaying.user.service.response.EnterpriseUserResVo;
import com.winsmoney.jajaying.user.service.response.UserAccountInfoResVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.DateUtil;
import com.winsmoney.platform.framework.core.util.Money;

@Controller
@RequestMapping("/borrow")
public class BorrowController extends BaseController {
	private static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(BorrowController.class);
	@Autowired
	private IBorrowService borrowService;
	@Autowired
	private IEnterpriseUserService enterpriseUserService;
	@Autowired
	private IBorrowFileService borrowFileService;
	@Autowired
	private IBorrowExtendService borrowExtendService;
	@Autowired
	private IBorrowRepayPlanService borrowRepayPlanService;
	@Autowired
	private IInterestPlansService interestPlansService;
	@Autowired
	private IInfoService infoService;
	@Autowired
	private ILoanOperService loanOperService;
	@Autowired
	private ILoanService loanService;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private ILoanInvestorService loanInvestorService;
	@Autowired
	private IRepayRecordService repayRecordService;
	@Autowired
	private IRepayRecordDetailService repayRecordDetailService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IGuaranteeService guaranteeService;
	@Autowired
	private IBorrowerService borrowerService;
	@Autowired
	private NotificationDomain notificationDomain;
	@Autowired
	private ISealImageRecordService sealImageRecord;
	@Autowired
	private IEarlyRepayWhiteListService earlyRepayWhiteListService;
	@Autowired
	private IOtherRepaySettingService otherRepaySettingService;
	@Value("${tj.borrow.roles}")
	private String tjRoles;
	@Value("${bj.borrow.roles}")
	private String bjRoles;

	/**
	 * ??????????????????
	 */
	private List<String> tjRoleList = Lists.newArrayList();
	/**
	 * ??????????????????
	 */
	private List<String> bjRoleList = Lists.newArrayList();

	/**
	 * ?????????
	 */
	@PostConstruct
	public void init() {
		if (tjRoles != null && !tjRoles.isEmpty()) {
			String[] tjArray = tjRoles.split(",");
			if (tjArray != null) {
				for (String role : tjArray) {
					tjRoleList.add(role.trim());
				}
			}
		}
		if (bjRoles != null && !bjRoles.isEmpty()) {
			String[] bjArray = bjRoles.split(",");
			if (bjArray != null) {
				for (String role : bjArray) {
					bjRoleList.add(role.trim());
				}
			}
		}
	}

	// borrowRepayPlanService.repayWarning(arg0, arg1, arg2);
	/**
	 * ??????????????????
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/{menu}/repayWarningList", method = RequestMethod.POST)
	@ResponseBody
	public Result repayWarning(RepayWarningReqVo repayWarningReqVo, int pageNo, int pageSize) {
		try {
			if (Strings.isNullOrEmpty(repayWarningReqVo.getBorrowTitle())) {
				repayWarningReqVo.setBorrowTitle(null);
			}
			if (Strings.isNullOrEmpty(repayWarningReqVo.getOaFlowCode())) {
				repayWarningReqVo.setOaFlowCode(null);
			}
			if (Strings.isNullOrEmpty(repayWarningReqVo.getBorrowUserCode())) {
				repayWarningReqVo.setBorrowUserCode(null);
			}
			TradeCommonResult<PageInfo<RepayWarningResVo>> re = borrowRepayPlanService.repayWarning(repayWarningReqVo,
					pageNo, pageSize);
			if (re.isSuccess()) {
				return Result.success(re);
			} else {
				return Result.error("????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/whitelist/getWhitelist", method = RequestMethod.POST)
	@ResponseBody
	public Result getWhitelist(EarlyRepayWhiteListReqVo repayWhiteListReqVo, int pageNo, int pageSize) {
		try {
			if (StringUtils.isEmpty(repayWhiteListReqVo.getBorrowName())) {
				repayWhiteListReqVo.setBorrowName(null);
			}
			if (StringUtils.isEmpty(repayWhiteListReqVo.getOaFlowCode())) {
				repayWhiteListReqVo.setOaFlowCode(null);
			}
			logger.info("??????{}??????:" + JSONObject.toJSONString(repayWhiteListReqVo), IEnterpriseUserService.class);
			TradeCommonResult<PageInfo<EarlyRepayWhiteListResVo>> result = earlyRepayWhiteListService
					.repayWhiteList(repayWhiteListReqVo, pageNo, pageSize);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result), IEnterpriseUserService.class);
			if (result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			} else {
				return Result.error(result.getResultCodeMsg());
			}

		} catch (Exception e) {
			logger.error("?????????????????????????????????????????????", e);
		}
		return Result.error("?????????????????????????????????????????????");
	}

	@RequestMapping(value = "/whitelist/delWhitelist", method = RequestMethod.POST)
	@ResponseBody
	public Result delWhitelist(String id) {
		try {
			logger.info("??????{}??????:" + JSONObject.toJSONString(id), String.class);
			TradeCommonResult<Integer> result = earlyRepayWhiteListService.delete(id);
			logger.info("??????{}??????:" + JSONObject.toJSONString(id), String.class);
			if (result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			} else {
				return Result.error(result.getResultCodeMsg());
			}

		} catch (Exception e) {
			logger.error("?????????????????????????????????", e);
		}
		return Result.error("?????????????????????????????????");
	}

	@RequestMapping(value = "/repayOption/addOption", method = RequestMethod.POST)
	@ResponseBody
	public Result addOption(HttpServletRequest request,String changeType,EarlyRepayWhiteListReqVo earlyRepayWhiteListReqVo,OtherRepaySettingReqVo otherRepaySettingReqVo) {
		try {
			  Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
			if("1".equals(changeType)) {
				earlyRepayWhiteListReqVo.setEditedBy(staffSession.getStaffName());
				logger.info("??????{}??????:" + JSONObject.toJSONString(earlyRepayWhiteListReqVo), String.class);
				TradeCommonResult<EarlyRepayWhiteListResVo> re = earlyRepayWhiteListService.insert(earlyRepayWhiteListReqVo);
				logger.info("??????{}??????:" + JSONObject.toJSONString(re), String.class);
				if(re.isSuccess()) {
					return  Result.success("????????????"); 
				}else {
					return  Result.error("????????????"); 
				}
			}else if("2".equals(changeType)) {
				otherRepaySettingReqVo.setEditedBy(staffSession.getStaffName());
				logger.info("??????{}??????:" + JSONObject.toJSONString(otherRepaySettingReqVo), String.class);
				TradeCommonResult<OtherRepaySettingResVo> re2 = otherRepaySettingService.insert(otherRepaySettingReqVo);
				logger.info("??????{}??????:" + JSONObject.toJSONString(re2), String.class);
				if(re2.isSuccess()) {
					return  Result.success("????????????"); 
				}else {
					return  Result.error("????????????"); 
				}
			}else {
				return  Result.success("????????????");
			}

		} catch (Exception e) {
			logger.error("????????????", e);
			return Result.error("????????????");
		}
	}
	
	@RequestMapping(value = " /{menu}/getDetail", method = RequestMethod.POST)
	@ResponseBody
	public Result getDetail(String borrowId) {
		try {
			List resultAll = new ArrayList();
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowId), String.class);
			TradeCommonResult<BorrowResVo> result1 = borrowService.getBorrowById(borrowId);
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowId), String.class);
			GuaranteeReqVo guaranteeReqVo = new GuaranteeReqVo();
			guaranteeReqVo.setBorrowId(borrowId);
			guaranteeReqVo.setMainFlag(1);
			TradeCommonResult<GuaranteeResVo> result2 = guaranteeService.get(guaranteeReqVo);
			GuaranteeReqVo guaranteeReqVo2 = new GuaranteeReqVo();
			guaranteeReqVo2.setBorrowId(borrowId);
			guaranteeReqVo2.setMainFlag(0);
			TradeCommonResult<GuaranteeResVo> result3 = guaranteeService.get(guaranteeReqVo2);
			EarlyRepayWhiteListReqVo earlyRepayWhiteListReqVo = new EarlyRepayWhiteListReqVo();
			earlyRepayWhiteListReqVo.setBorrowCode(borrowId);
			TradeCommonResult<EarlyRepayWhiteListResVo> result4 = earlyRepayWhiteListService
					.get(earlyRepayWhiteListReqVo);
			TradeCommonResult<BorrowRepayPlanResVo> reapyPlan;
			resultAll.add(result1.getBusinessObject());
			resultAll.add(result2.getBusinessObject());
			resultAll.add(result3.getBusinessObject());
			resultAll.add(result4.getBusinessObject());
			if(result1.getBusinessObject().getStatus().equals("FINISH")) {
				BorrowRepayPlanResVo brp  = new BorrowRepayPlanResVo();
				brp.setCurrPhase(result1.getBusinessObject().getTotalPhase());
				brp.setTotalPhase(result1.getBusinessObject().getTotalPhase());
				resultAll.add(brp);
			}else {
				reapyPlan = borrowRepayPlanService.selectNextRepayPhase(borrowId);
				resultAll.add(reapyPlan.getBusinessObject());
			}
			if (result1.isSuccess()) {
				return Result.success(resultAll);
			} else {
				return Result.error("????????????");
			}
		} catch (Exception e) {
			logger.error("?????????????????????????????????", e);
			return Result.error("?????????????????????????????????");
		}
	}
	@RequestMapping(value = "/repayOption/repayOptionlist", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<BorrowVo> repayOptionlist(BorrowReqVo borrowReqVo, int pageNo, int pageSize) {
		PageInfo<BorrowVo> newPage = new PageInfo<BorrowVo>();
		if (StringUtils.isBlank(borrowReqVo.getBorrowTitle()))
			borrowReqVo.setBorrowTitle(null);
		if (StringUtils.isBlank(borrowReqVo.getOaFlowCode()))
			borrowReqVo.setOaFlowCode(null);
		try {
			List<BorrowStatus> list = new ArrayList<BorrowStatus>();
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			TradeCommonResult<PageInfo<BorrowResVo>> result = borrowService.repayList(borrowReqVo, pageNo, pageSize);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowService.class);
			if (result.isSuccess()) {
				PageInfo<BorrowResVo> page = result.getBusinessObject();
				List<BorrowVo> borrowVoList = BeanMapper.mapList(page.getList(), BorrowVo.class);
				for (BorrowVo borrowVo : borrowVoList) {
					// ??????????????????
					if(null != borrowVo.getBorrowTerm() && null != borrowVo.getTermType()) {
						borrowVo.setBorrowDates(borrowVo.getBorrowTerm() + borrowVo.getTermType().getMessage());
					}else {
						String date = computeBorrowDates(borrowVo.getLastReleaseTime(), borrowVo.getLastRepayTime());
						borrowVo.setBorrowDates(date);
					}
					borrowVo.setEditedBy(borrowVo.getStatus().getName());
					// ??????????????????????????????
					logger.info("??????{}??????:" + JSONObject.toJSONString(borrowVo), IBorrowRepayPlanService.class);
					TradeCommonResult<BorrowRepayPlanResVo> reapyPlan = borrowRepayPlanService
							.selectNextRepayPhase(borrowVo.getId());
					logger.info("??????{}??????:" + JSONObject.toJSONString(reapyPlan), IBorrowRepayPlanService.class);
					BorrowRepayPlanResVo phase = reapyPlan.getBusinessObject();
					borrowVo.setNextPhase(Integer.toString(phase.getCurrPhase()));
					borrowVo.setTotalPhase(Integer.toString(phase.getTotalPhase()));
					// ????????????????????????
					BorrowRepayPlanReqVo borrowRepayPlanReqVo = new BorrowRepayPlanReqVo();
					borrowRepayPlanReqVo.setBorrowCode(borrowVo.getId());
					borrowRepayPlanReqVo.setPhase(phase.getCurrPhase());

					logger.info("??????{}??????:" + JSONObject.toJSONString(borrowRepayPlanReqVo),
							IBorrowRepayPlanService.class);
					TradeCommonResult<BorrowRepayPlanResVo> borrowPlanReault = borrowRepayPlanService
							.get(borrowRepayPlanReqVo);
					logger.info("??????{}??????:" + JSONObject.toJSONString(borrowPlanReault), IBorrowRepayPlanService.class);
					BorrowRepayPlanResVo borrowPlan = borrowPlanReault.getBusinessObject();
					// ????????????= ?????? + ?????? + ??????
					Money nextReapyAmount = new Money();
					if (borrowPlan.getPrincipalAmount() != null) {
						nextReapyAmount = nextReapyAmount.addTo(borrowPlan.getPrincipalAmount());
						if (borrowPlan.getInterestAmount() != null) {
							nextReapyAmount = nextReapyAmount.addTo(borrowPlan.getInterestAmount());
							if (borrowPlan.getPenaltyAmount() != null)
								nextReapyAmount = nextReapyAmount.addTo(borrowPlan.getPenaltyAmount());
						}
					}
					borrowVo.setNextReapyAmount(nextReapyAmount);
					borrowVo.setNextRepayTime(borrowPlan.getInterestEndDate());
				}
				newPage = BeanMapper.map(page, PageInfo.class);
				newPage.setList(borrowVoList);
			}
		} catch (Exception e) {
			logger.error("????????????????????????", e);
		}

		return newPage;
	}
	/**   
	 * ????????????
	 *
	 * @param borrowReqVo
	 * @return
	 */
	@RequestMapping(value = "/{menu}/addBorrow", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "????????????")
	public Result addBorrow(BorrowReqVo borrowReqVo, String purpose, String risk, String approvalOptions,
			String approver, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String staffName = staffSession.getStaffName();
		borrowReqVo.setEditedBy(staffName);
		String borrowTitle = borrowReqVo.getBorrowTitle();
		BorrowReqVo borrowReqVoWithTitle = new BorrowReqVo();
		borrowReqVoWithTitle.setBorrowTitle(borrowTitle);
		TradeCommonResult<BorrowResVo> borrow = borrowService.getBorrow(borrowReqVoWithTitle);
		if (null != borrow.getBusinessObject() && null != borrow.getBusinessObject().getId()) {
			return Result.error("????????????????????????????????????????????????");
		}
		List<BorrowExtendReqVo> list = new ArrayList<BorrowExtendReqVo>();
		// ????????????
		BorrowExtendReqVo borrowExtendReqVo = new BorrowExtendReqVo();
		borrowExtendReqVo.setExtendType(BorrowExtendType.RISK);
		borrowExtendReqVo.setMark(risk);
		borrowExtendReqVo.setEditedBy(staffName);
		list.add(borrowExtendReqVo);
		// ????????????
		BorrowExtendReqVo borrowExtendReqVo2 = new BorrowExtendReqVo();
		borrowExtendReqVo2.setExtendType(BorrowExtendType.PURPOSE);
		borrowExtendReqVo2.setMark(purpose);
		borrowExtendReqVo2.setEditedBy(staffName);
		list.add(borrowExtendReqVo2);
		// ????????????
		BorrowExtendReqVo borrowExtendReqVo3 = new BorrowExtendReqVo();
		borrowExtendReqVo3.setExtendType(BorrowExtendType.INIT);
		borrowExtendReqVo3.setMark(approvalOptions);
		borrowExtendReqVo3.setEditedBy(staffName);
		list.add(borrowExtendReqVo3);
		borrowReqVo.setBorrowExtendList(list);
		borrowReqVo.setStatus(BorrowStatus.APPROVAL1);
		logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
		borrowReqVo.setBorrowRate(borrowReqVo.getBorrowRate().multiply(new BigDecimal(100)));
		TradeCommonResult<BorrowResVo> result = borrowService.insertBorrow(borrowReqVo);
		logger.info("??????{}?????????:" + JSONObject.toJSONString(result), IBorrowService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject().getId());
		} else
			return Result.error("??????????????????");

	}

	@RequestMapping(value = "/{menu}/upload/{borrowId}")
	@ResponseBody
	public String add(MultipartHttpServletRequest request, @PathVariable String borrowId,
			HttpServletRequest httpRequest, String bizeCode) {
		String originalName = null;
		Map<String, MultipartFile> map = request.getFileMap();
		MultipartFile file = map.get("files");
		String value = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty(bizeCode);
		String LocalhostUrl = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("rootPath");
		if (file != null && !file.isEmpty()) {
			originalName = file.getOriginalFilename();// ??????????????????+?????????
			Date date = new Date();
			String uuid = UUID.randomUUID().toString();
			String dateName = new SimpleDateFormat("/yyyy/MM/dd/").format(date);
			int index = 0;

			index = originalName.lastIndexOf(".");
			String extendName = originalName.substring(index);// ???????????????
			String filePath = LocalhostUrl + value + dateName + uuid + extendName;
			FileUDUtils.uploadFile(filePath, file);
			BorrowFileReqVo borrowFileReqVo = new BorrowFileReqVo();
			borrowFileReqVo.setBorrowCode(borrowId);
			borrowFileReqVo.setFileUrl(dateName + uuid + extendName);
			borrowFileReqVo.setTitle(file.getName());
			borrowFileReqVo.setMark("??????");
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			String role = staffSession.getStaffName();
			borrowFileReqVo.setEditedBy(role);
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowFileReqVo), IBorrowFileService.class);
			TradeCommonResult<BorrowFileResVo> result = borrowFileService.insertBorrowFile(borrowFileReqVo);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowFileService.class);
			if (result.isSuccess()) {
				return "success";
			}
		}
		return "fail";
	}

	/**
	 * ?????????????????????
	 * 
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/{menu}/borrowerList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<BorrowUserInfoResVo> borrowerList(BorrowUserInfoReqVo request, int pageNo, int pageSize) {
		   if (StringUtils.isBlank(request.getLegalPersonMobile())) {
            request.setLegalPersonMobile(null);
        }
        if (StringUtils.isBlank(request.getEnterpriseName())) {
            request.setEnterpriseName(null);
        }else {
        	request.setLegalPersonName(request.getEnterpriseName());
        }
        if (StringUtils.isBlank(request.getLegalPersonName())) {
            request.setLegalPersonName(null);
        }else{
        	 request.setEnterpriseName(request.getLegalPersonName());
        }

		UserCommonResult<PageInfo<BorrowUserInfoResVo>> result = userService.listBorrowUser(request, pageNo, pageSize);
		return result.getBusinessObject();	
		
	}
	
	@RequestMapping(value = "/repayOption/borrowerList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<BorrowUserInfoResVo> reborrowerList(BorrowUserInfoReqVo request, int pageNo, int pageSize) {
		   if (StringUtils.isBlank(request.getLegalPersonMobile())) {
            request.setLegalPersonMobile(null);
        }
        if (StringUtils.isBlank(request.getEnterpriseName())) {
            request.setEnterpriseName(null);
        }else {
        	request.setLegalPersonName(request.getEnterpriseName());
        }
        if (StringUtils.isBlank(request.getLegalPersonName())) {
            request.setLegalPersonName(null);
        }else{
        	 request.setEnterpriseName(request.getLegalPersonName());
        	 
        }
        request.setUserType("GUARANTEE");
		UserCommonResult<PageInfo<BorrowUserInfoResVo>> result = userService.listBorrowUser(request, pageNo, pageSize);
		return result.getBusinessObject();	
		
	}
	
	private String getAreaByRole(String roleId) {
		String res = null;
		if (tjRoleList.contains(roleId)) {
			res = CityListType.TianJin.getCode();
		} else if (bjRoleList.contains(roleId)) {
			res = CityListType.BeiJing.getCode();
		}
		// ?????????????????????????????????
		if (tjRoleList.indexOf(roleId) == bjRoleList.indexOf(roleId)) {
			res = null;
		}
		return res;
	}

	/**
	 * ???????????? ??????????????????(????????????????????????)
	 *
	 * @param borrowReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/{menu}/borrowList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<BorrowVo> borrowList(BorrowReqVo borrowReqVo, int pageNo, int pageSize, String statusType,
			String phase, String checkStatus, HttpServletRequest request) {
		if (Strings.isNullOrEmpty(borrowReqVo.getBorrowTitle())) {
			borrowReqVo.setBorrowTitle(null);
		}
		if (Strings.isNullOrEmpty(borrowReqVo.getOaFlowCode())) {
			borrowReqVo.setOaFlowCode(null);
		}
		if (Strings.isNullOrEmpty(borrowReqVo.getBorrowUserCode())) {
			borrowReqVo.setBorrowUserCode(null);
		}
		FilterStatus filterStatus = FilterStatus.ALL;
		if (!Strings.isNullOrEmpty(checkStatus)) {
			filterStatus = FilterStatus.valueOf(checkStatus);
		}
		Staff staff = (Staff) request.getSession().getAttribute(AdminUserController.USERNAME);
		if (staff != null) {
			// ??????????????????
			String roleId = staff.getRoleId();
			String area = getAreaByRole(roleId);
			if (area != null && !area.isEmpty()) {
				borrowReqVo.setBorrowLocation(area);
			}
		}
		try {
			if (borrowReqVo.getBorrowUserName() == "") {
				borrowReqVo.setBorrowUserName(null);
			}
			List<BorrowStatus> list = new ArrayList<BorrowStatus>();
			Map<BorrowStatus, Integer> m = new HashMap<BorrowStatus, Integer>();
			if ("use".equals(statusType)) {
				list.add(BorrowStatus.INUSE);
				list.add(BorrowStatus.LOCK);
				list.add(BorrowStatus.SPLIT);
			}
			if ("overdue".equals(statusType)) {
				list.add(BorrowStatus.OVERDUE);
			}
			if ("refuse".equals(statusType)) {// ????????????
				list.add(BorrowStatus.REFUSE1);
				list.add(BorrowStatus.REFUSE2);
			}
			if ("invalid".equals(statusType)) {// ????????????-?????????????????????
				list.add(BorrowStatus.INVALID1);
				list.add(BorrowStatus.INVALID2);
				list.add(BorrowStatus.INVALID3);
				list.add(BorrowStatus.FINISH);
			}
			if ("repay".equals(statusType)) {// ????????????
				list.add(BorrowStatus.LOCK);
				list.add(BorrowStatus.SPLIT);
			}
			if ("review".equals(statusType)) {// ??????????????????
				list.addAll(filterStatus.borrowStatusList);
				if (filterStatus == FilterStatus.ALL) {
					list.add(BorrowStatus.SUBMIT);
					list.add(BorrowStatus.PASS_BUS_MANAGER);
					list.add(BorrowStatus.BACK_BUS_MANAGER);
					list.add(BorrowStatus.REFUSE_BUS_MANAGER);
					list.add(BorrowStatus.PASS_FIRST_CHECK);
					list.add(BorrowStatus.BACK_FIRST_CHECK);
					list.add(BorrowStatus.REFUSE_FIRST_CHECK);
					list.add(BorrowStatus.PASS_RECHECK);
					list.add(BorrowStatus.BACK_RECHECK);
					list.add(BorrowStatus.REFUSE_RECHECK);
					list.add(BorrowStatus.PASS_FINANCE);
					list.add(BorrowStatus.BACK_FINANCE);
					list.add(BorrowStatus.REFUSE_FINANCE);
					list.add(BorrowStatus.PASS_FINAL_CHECK);
					list.add(BorrowStatus.BACK_FINAL_CHECK);
					list.add(BorrowStatus.REFUSE_FINAL_CHECK);
				}
				m.put(BorrowStatus.SUBMIT, 1);
				m.put(BorrowStatus.BACK_FIRST_CHECK, 1);

			}
			if ("firstReview".equals(statusType)) {// ????????????
				list.addAll(filterStatus.borrowStatusList);
				if (filterStatus == FilterStatus.ALL) {
					list.add(BorrowStatus.PASS_BUS_MANAGER);
					list.add(BorrowStatus.PASS_FIRST_CHECK);
					list.add(BorrowStatus.BACK_FIRST_CHECK);
					list.add(BorrowStatus.REFUSE_FIRST_CHECK);
					list.add(BorrowStatus.PASS_RECHECK);
					list.add(BorrowStatus.BACK_RECHECK);
					list.add(BorrowStatus.REFUSE_RECHECK);
					list.add(BorrowStatus.PASS_FINANCE);
					list.add(BorrowStatus.BACK_FINANCE);
					list.add(BorrowStatus.REFUSE_FINANCE);
					list.add(BorrowStatus.PASS_FINAL_CHECK);
					list.add(BorrowStatus.BACK_FINAL_CHECK);
					list.add(BorrowStatus.REFUSE_FINAL_CHECK);
				}
				m.put(BorrowStatus.PASS_BUS_MANAGER, 1);
				m.put(BorrowStatus.BACK_RECHECK, 1);

			}
			if ("reReview".equals(statusType)) {// ????????????
				list.addAll(filterStatus.borrowStatusList);
				if (filterStatus == FilterStatus.ALL) {
					list.add(BorrowStatus.PASS_FIRST_CHECK);
					list.add(BorrowStatus.PASS_RECHECK);
					list.add(BorrowStatus.BACK_RECHECK);
					list.add(BorrowStatus.REFUSE_RECHECK);
					list.add(BorrowStatus.PASS_FINANCE);
					list.add(BorrowStatus.BACK_FINANCE);
					list.add(BorrowStatus.REFUSE_FINANCE);
					list.add(BorrowStatus.PASS_FINAL_CHECK);
					list.add(BorrowStatus.BACK_FINAL_CHECK);
					list.add(BorrowStatus.REFUSE_FINAL_CHECK);
				}
				m.put(BorrowStatus.PASS_FIRST_CHECK, 1);
				m.put(BorrowStatus.BACK_FINANCE, 1);

			}
			if ("financeReview".equals(statusType)) {// ???????????? ??????
				list.addAll(filterStatus.borrowStatusList);
				if (filterStatus == FilterStatus.ALL) {
					list.add(BorrowStatus.PASS_RECHECK);
					list.add(BorrowStatus.PASS_FINANCE);
					list.add(BorrowStatus.BACK_FINANCE);
					list.add(BorrowStatus.REFUSE_FINANCE);
					list.add(BorrowStatus.PASS_FINAL_CHECK);
					list.add(BorrowStatus.BACK_FINAL_CHECK);
					list.add(BorrowStatus.REFUSE_FINAL_CHECK);
				}
				m.put(BorrowStatus.PASS_RECHECK, 1);
				m.put(BorrowStatus.BACK_FINAL_CHECK, 1);
			}
			if ("finalReview".equals(statusType)) {// ????????????
				list.addAll(filterStatus.borrowStatusList);
				if (filterStatus == FilterStatus.ALL) {
					list.add(BorrowStatus.PASS_FINANCE);
					list.add(BorrowStatus.PASS_FINAL_CHECK);
					list.add(BorrowStatus.BACK_FINAL_CHECK);
					list.add(BorrowStatus.REFUSE_FINAL_CHECK);
				}
				m.put(BorrowStatus.PASS_FINANCE, 1);
			}
			if (!list.isEmpty()) {
				borrowReqVo.setStatusIn(list);
				borrowReqVo.setOrderStatus(m);
			}
			// ????????????
			if (filterStatus.equals(FilterStatus.ALL)) {
				borrowReqVo.setStatusIn(null);
			}
			TradeCommonResult<PageInfo<BorrowResVo>> result = borrowService.listBorrow(borrowReqVo, pageNo, pageSize);
			PageInfo<BorrowVo> newPage = new PageInfo<BorrowVo>();
			if (result.isSuccess()) {
				PageInfo<BorrowResVo> page = result.getBusinessObject();
				List<BorrowVo> borrowVoList = BeanMapper.mapList(page.getList(), BorrowVo.class);
				for (BorrowVo borrowVo : borrowVoList) {
					String date = "";
					borrowVo.setEditedBy(borrowVo.getStatus().getName());
					if (borrowVo.getLastReleaseTime() != null && borrowVo.getLastRepayTime() != null) {
						// ??????????????????
						date = computeBorrowDates(borrowVo.getLastReleaseTime(), borrowVo.getLastRepayTime());
					}
					if (borrowVo.getGuaranteeUserId() != null) {
						GuaranteeReqVo grq = new GuaranteeReqVo();
						grq.setGuaranteeUserId(borrowVo.getGuaranteeUserId());
						TradeCommonResult<GuaranteeResVo> result3 = guaranteeService.get(grq);
						if (result3.isSuccess()) {
							borrowVo.setGuaranteeUserName(result3.getBusinessObject().getGuaranteeUserName());
						}
					}
					borrowVo.setBorrowDates(date);
					// borrowVo.setBorrowRate( borrowVo.getBorrowRate().divide(
					// new BigDecimal( 100 )));
					if (phase != null && !"".equals(phase)) {
						TradeCommonResult<BorrowRepayPlanResVo> r = borrowRepayPlanService
								.querySetRepayPlanFee(borrowVo.getId(), phase);
						Boolean isNeed = r.getBusinessObject().getIsNeedSet();
						borrowVo.setMark(isNeed.toString());
					} else {
						TradeCommonResult<BorrowRepayPlanResVo> r = borrowRepayPlanService
								.querySetRepayPlanFee(borrowVo.getId(), null);
						Boolean isNeed = r.getBusinessObject().getIsNeedSet();
						borrowVo.setMark(isNeed.toString());
					}
				}
				newPage = BeanMapper.map(page, PageInfo.class);
				newPage.setList(borrowVoList);
			}
			return newPage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * ????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/{menu}/borrowData", method = RequestMethod.POST)
	@ResponseBody
	public Result borrowData(String id, int phase, String statusType, String beforeDate) {
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		BorrowRepayPlanReqVo brp = new BorrowRepayPlanReqVo();
		brp.setBorrowCode(id);
		if (phase != 0) {
			brp.setPhase(phase);
		}
		TradeCommonResult<PageInfo<BorrowRepayPlanResVo>> a = borrowRepayPlanService.list(brp, 1, 1000);
		// if(phase != 0) {
		// RepayReqVo r = new RepayReqVo();
		// r.setBorrowId(id);
		// r.setPhase(phase);
		// r.setRepayType(RepayType.valueOf(statusType));
		// if(null != beforeDate && !"".equals(beforeDate)) {
		//
		// try {
		// Date date = dateFormat.parse(beforeDate);
		// r.setActualInterestEndDate(date);
		// } catch (ParseException e) {
		// return Result.error("??????????????????");
		// }
		//
		// }
		// TradeCommonResult<Money> n =
		// borrowRepayPlanService.getPlatformProfit(r);
		// for(BorrowRepayPlanResVo bb : a.getBusinessObject().getList()){
		// bb.setRepayFee(n.getBusinessObject());
		// }
		// }
		BorrowReqVo brv = new BorrowReqVo();
		brv.setId(id);
		TradeCommonResult<BorrowResVo> re = borrowService.getBorrow(brv);
		List result = new ArrayList();
		if (a.isSuccess() && re.isSuccess()) {
			result.add(re.getBusinessObject());
			result.add(a.getBusinessObject().getList());
			return Result.success(result);
		} else {
			return Result.error("error");
		}
	}

	/**
	 * ???????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/{menu}/updateRepayPlan", method = RequestMethod.POST)
	@ResponseBody
	public Result updateRepayPlan(String[] ids, String[] amounts) {
		try {
			List<BorrowRepayPlanReqVo> l = new ArrayList<BorrowRepayPlanReqVo>();
			for (int i = 0; i < ids.length; i++) {
				BorrowRepayPlanReqVo b = new BorrowRepayPlanReqVo();
				b.setId(ids[i]);
				b.setRepayFee(new Money(amounts[i]));
				b.setRepayFeeFlag(true);
				l.add(b);
			}
			TradeCommonResult<Integer> a = borrowRepayPlanService.updateList(l);
			if (a.isSuccess()) {
				return Result.success("success");
			}
			return Result.error("error");

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ??????????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/l_asset_list/deleteBorrowFile/{fileId}", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteLoanFile(@PathVariable @NotBlank String fileId, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		BorrowFileReqVo borrowFileReqVo = new BorrowFileReqVo();
		borrowFileReqVo.setId(fileId);
		borrowFileReqVo.setEditedBy(role);
		logger.info("??????{}??????:" + JSONObject.toJSONString(borrowFileReqVo), IBorrowFileService.class);
		TradeCommonResult<Integer> result = borrowFileService.deleteBorrowFile(borrowFileReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowFileService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ????????????
	 * 
	 * @param borrowReqVo
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/l_asset_list/saveBorrow", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "????????????")
	public Result saveBorrow(BorrowReqVo borrowReqVo, HttpServletRequest httpRequest) {
		try {
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			String role = staffSession.getStaffName();
			borrowReqVo.setEditedBy(role);
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			if (null != borrowReqVo.getBorrowRate()) {
				borrowReqVo.setBorrowRate(borrowReqVo.getBorrowRate().multiply(new BigDecimal(100)));
			}
			TradeCommonResult<BorrowResVo> result = borrowService.updateBorrow(borrowReqVo);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowService.class);
			if (result.isSuccess()) {
				logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowRepayPlanService.class);
				TradeCommonResult<BorrowRepayPlanResVo> borrowRepayPlanResVo = borrowRepayPlanService
						.updateBorrowRepayPlan(borrowReqVo.getId());
				logger.info("??????{}??????:" + JSONObject.toJSONString(borrowRepayPlanResVo), IBorrowRepayPlanService.class);
				if (borrowRepayPlanResVo.isSuccess())
					return Result.success(borrowRepayPlanResVo.getResultCodeMsg());
				else
					return Result.error(borrowRepayPlanResVo.getResultCodeMsg());
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			return Result.error("error");
		}
	}

	/**
	 * ????????????
	 * 
	 * @param borrowReqVo
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/l_asset_list/cancelBorrow", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "????????????")
	public Result cancelBorrow(BorrowReqVo borrowReqVo, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		borrowReqVo.setEditedBy(role);
		borrowReqVo.setStatus(BorrowStatus.INVALID3);
		logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
		borrowReqVo.setBorrowRate(borrowReqVo.getBorrowRate().multiply(new BigDecimal(100)));
		TradeCommonResult<BorrowResVo> result = borrowService.updateBorrow(borrowReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowService.class);
		if (result.isSuccess()) {
			return Result.success(result.getResultCodeMsg());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ??????????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/l_asset_list/getBorrowFileList/{borrowCode}", method = RequestMethod.POST)
	@ResponseBody
	public Result getBorrowFileList(@PathVariable @NotBlank String borrowCode) {
		BorrowFileReqVo borrowFileReqVo = new BorrowFileReqVo();
		borrowFileReqVo.setBorrowCode(borrowCode);
		TradeCommonResult<List<BorrowFileResVo>> result = borrowFileService.listBorrowFile(borrowFileReqVo);
		if (result.isSuccess()) {
			return Result.success(BeanMapper.mapList(result.getBusinessObject(), LoanFileResVo.class));
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ???????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/l_RepayRecord/getRepayRecord", method = RequestMethod.POST)
	@ResponseBody
	public Result getRepayRecord(RepayRecordReqVo repayRecordReqVo, int pageNo, int pageSize) {
		TradeCommonResult<PageInfo<RepayRecordResVo>> result = repayRecordService.list(repayRecordReqVo, pageNo,
				pageSize);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ???????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/l_RepayRecord/getRepayRecordDetail", method = RequestMethod.POST)
	@ResponseBody
	public Result getRepayRecordDetail(RepayRecordDetailReqVo repayRecordDetailReqVo, int pageNo, int pageSize) {
		TradeCommonResult<PageInfo<RepayRecordDetailResVo>> result = repayRecordDetailService
				.list(repayRecordDetailReqVo, pageNo, pageSize);
		if (result.isSuccess()) {
			for (RepayRecordDetailResVo vo : result.getBusinessObject().getList()) {
				UserCommonResult<UserInfoResVo> userResult = userInfoService.getByRoleUserId(vo.getInvestUserId());
				vo.setInvestUserName(userResult.getBusinessObject().getRealName());
			}
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ????????????
	 * 
	 * @param borrowRepayPlanReqVo
	 * @return
	 */
	@RequestMapping(value = "/l_RepayRecord/repayType", method = RequestMethod.POST)
	@ResponseBody
	public Result repayType(BorrowRepayPlanReqVo borrowRepayPlanReqVo) {
		TradeCommonResult<BorrowRepayPlanResVo> borrowPlanReault = borrowRepayPlanService.get(borrowRepayPlanReqVo);
		if (borrowPlanReault.isSuccess()) {
			if (formatDate(new Date()).before(formatDate(borrowPlanReault.getBusinessObject().getInterestEndDate()))) {// ??????
				return Result.success("ADVANCE");
			} else if (formatDate(new Date())
					.after(formatDate(borrowPlanReault.getBusinessObject().getInterestEndDate()))) {// ??????
				return Result.success("OVERDUE");
			} else
				return Result.success("COMMON");
		} else
			return Result.error(borrowPlanReault.getResultCodeMsg());
	}

	/**
	 * ????????????
	 * 
	 * @param borrowReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/repayList/list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<BorrowVo> repayList(BorrowReqVo borrowReqVo, int pageNo, int pageSize) {
		PageInfo<BorrowVo> newPage = new PageInfo<BorrowVo>();
		if (StringUtils.isBlank(borrowReqVo.getBorrowUserName()))
			borrowReqVo.setBorrowUserName(null);
		try {
			List<BorrowStatus> list = new ArrayList<BorrowStatus>();
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			TradeCommonResult<PageInfo<BorrowResVo>> result = borrowService.repayList(borrowReqVo, pageNo, pageSize);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowService.class);
			if (result.isSuccess()) {
				PageInfo<BorrowResVo> page = result.getBusinessObject();
				List<BorrowVo> borrowVoList = BeanMapper.mapList(page.getList(), BorrowVo.class);
				for (BorrowVo borrowVo : borrowVoList) {
					// ??????????????????
					String date = computeBorrowDates(borrowVo.getLastReleaseTime(), borrowVo.getLastRepayTime());
					borrowVo.setBorrowDates(date);
					// ??????????????????????????????
					logger.info("??????{}??????:" + JSONObject.toJSONString(borrowVo), IBorrowRepayPlanService.class);
					TradeCommonResult<BorrowRepayPlanResVo> reapyPlan = borrowRepayPlanService
							.selectNextRepayPhase(borrowVo.getId());
					logger.info("??????{}??????:" + JSONObject.toJSONString(reapyPlan), IBorrowRepayPlanService.class);
					BorrowRepayPlanResVo phase = reapyPlan.getBusinessObject();
					borrowVo.setNextPhase(Integer.toString(phase.getCurrPhase()));
					borrowVo.setTotalPhase(Integer.toString(phase.getTotalPhase()));
					// ????????????????????????
					BorrowRepayPlanReqVo borrowRepayPlanReqVo = new BorrowRepayPlanReqVo();
					borrowRepayPlanReqVo.setBorrowCode(borrowVo.getId());
					borrowRepayPlanReqVo.setPhase(phase.getCurrPhase());

					logger.info("??????{}??????:" + JSONObject.toJSONString(borrowRepayPlanReqVo),
							IBorrowRepayPlanService.class);
					TradeCommonResult<BorrowRepayPlanResVo> borrowPlanReault = borrowRepayPlanService
							.get(borrowRepayPlanReqVo);
					logger.info("??????{}??????:" + JSONObject.toJSONString(borrowPlanReault), IBorrowRepayPlanService.class);
					BorrowRepayPlanResVo borrowPlan = borrowPlanReault.getBusinessObject();
					// ????????????= ?????? + ?????? + ??????
					Money nextReapyAmount = new Money();
					if (borrowPlan.getPrincipalAmount() != null) {
						nextReapyAmount = nextReapyAmount.addTo(borrowPlan.getPrincipalAmount());
						if (borrowPlan.getInterestAmount() != null) {
							nextReapyAmount = nextReapyAmount.addTo(borrowPlan.getInterestAmount());
							if (borrowPlan.getPenaltyAmount() != null)
								nextReapyAmount = nextReapyAmount.addTo(borrowPlan.getPenaltyAmount());
						}
					}
					borrowVo.setNextReapyAmount(nextReapyAmount);
					borrowVo.setNextRepayTime(borrowPlan.getInterestEndDate());
				}
				newPage = BeanMapper.map(page, PageInfo.class);
				newPage.setList(borrowVoList);
			}
		} catch (Exception e) {
			logger.error("????????????????????????", e);
		}

		return newPage;
	}

	/**
	 * ????????????
	 *
	 * @param borrowId
	 * @return
	 */
	@RequestMapping(value = "/borrowerDetail", method = RequestMethod.POST)
	@ResponseBody
	public BorrowResVo borrowerDetail(String borrowId) {
		TradeCommonResult<BorrowResVo> result = borrowService.getBorrowById(borrowId);
		BorrowResVo borrowVo = result.getBusinessObject();
		borrowVo.setBorrowRate(borrowVo.getBorrowRate().divide(new BigDecimal(100)));
		return borrowVo;
	}

	/**
	 * ????????????
	 *
	 * @param borrowReqVo
	 * @return
	 */
	@RequestMapping(value = "/{menu}/updateBorrow", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "????????????")
	public String updateBorrow(BorrowReqVo borrowReqVo, BorrowExtends borrowExtends) {
		String approveResult = borrowExtends.getApproveResult();
		if ("n".equals(approveResult)) {// ??????
			// ????????????????????????????????????
			if (BorrowStatus.REFUSE1 == borrowReqVo.getStatus()) {
				borrowReqVo.setStatus(BorrowStatus.INVALID1);
			} else {
				borrowReqVo.setStatus(BorrowStatus.INVALID2);
			}
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			borrowReqVo.setBorrowRate(borrowReqVo.getBorrowRate().multiply(new BigDecimal(100)));
			TradeCommonResult<BorrowResVo> result = borrowService.updateBorrow(borrowReqVo);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowService.class);
			if (result.isSuccess()) {
				return "success";
			}
			return "fail";
		} else {
			borrowReqVo.setBorrowRate(borrowReqVo.getBorrowRate().multiply(new BigDecimal(100)));
			borrowReqVo.setStatus(BorrowStatus.APPROVAL1);
		}
		List<BorrowExtendReqVo> list = new ArrayList<BorrowExtendReqVo>();
		// ????????????
		BorrowExtendReqVo borrowExtendReqVo = new BorrowExtendReqVo();
		borrowExtendReqVo.setMark(borrowExtends.getRisk());
		borrowExtendReqVo.setId(borrowExtends.getRiskId());
		list.add(borrowExtendReqVo);
		// ????????????
		BorrowExtendReqVo borrowExtendReqVo2 = new BorrowExtendReqVo();
		borrowExtendReqVo2.setMark(borrowExtends.getPurpose());
		borrowExtendReqVo2.setId(borrowExtends.getPurposeId());
		list.add(borrowExtendReqVo2);
		// ????????????
		BorrowExtendReqVo borrowExtendReqVo3 = new BorrowExtendReqVo();
		borrowExtendReqVo3.setExtendType(BorrowExtendType.EDIT);
		borrowExtendReqVo3.setMark(borrowExtends.getApproveContent());
		borrowExtendReqVo3.setEditedBy(borrowExtends.getApprover());
		borrowExtendReqVo3.setBorrowCode(borrowReqVo.getId());
		list.add(borrowExtendReqVo3);
		borrowReqVo.setBorrowExtendList(list);
		// ????????????
		logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
		TradeCommonResult<BorrowResVo> result = borrowService.updateBorrowAndExtends(borrowReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowService.class);
		if (result.isSuccess()) {
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowRepayPlanService.class);
			TradeCommonResult<BorrowRepayPlanResVo> borrowRepayPlanResVo = borrowRepayPlanService
					.updateBorrowRepayPlan(borrowReqVo.getId());
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowRepayPlanResVo), IBorrowRepayPlanService.class);
			if (borrowRepayPlanResVo.isSuccess())
				return "success";
			else
				return "fail";
		}
		return "fail";
	}

	/**
	 * ????????????
	 *
	 * @param borrowId
	 * @return
	 */
	@RequestMapping(value = "/getBorrow", method = RequestMethod.POST)
	@ResponseBody
	public BorrowVo getBorrow(String borrowId) {
		TradeCommonResult<BorrowResVo> result = borrowService.getBorrowById(borrowId);
		// ??????
		BorrowExtendReqVo borrowExtendReqVo = new BorrowExtendReqVo();
		borrowExtendReqVo.setBorrowCode(borrowId);
		logger.info("??????{}??????:" + JSONObject.toJSONString(borrowExtendReqVo), IBorrowExtendService.class);
		TradeCommonResult<PageInfo<BorrowExtendResVo>> extendResult = borrowExtendService
				.listBorrowExtend(borrowExtendReqVo, 1, 1000);
		logger.info("??????{}??????:" + JSONObject.toJSONString(extendResult), IBorrowExtendService.class);
		BorrowVo borrowVo = BeanMapper.map(result.getBusinessObject(), BorrowVo.class);
		borrowVo.setBorrowExtendList(extendResult.getBusinessObject().getList());

		// ??????????????????
		BorrowFileReqVo borrowFileReqVo = new BorrowFileReqVo();
		borrowFileReqVo.setBorrowCode(borrowId);
		logger.info("??????{}??????:" + JSONObject.toJSONString(borrowFileReqVo), IBorrowFileService.class);
		TradeCommonResult<PageInfo<BorrowFileResVo>> fileResult = borrowFileService.listBorrowFile(borrowFileReqVo, 1,
				1000);
		logger.info("??????{}??????:" + JSONObject.toJSONString(fileResult), IBorrowFileService.class);
		for (BorrowFileResVo borrowFileResVo : fileResult.getBusinessObject().getList()) {
			borrowFileResVo.setFileUrl(borrowFileResVo.getFileUrl());
		}
		borrowVo.setBorrowFileList(fileResult.getBusinessObject().getList());

		// ??????????????????
		String date = computeBorrowDates(borrowVo.getLastReleaseTime(), borrowVo.getLastRepayTime());
		borrowVo.setBorrowDates(date);

		// ?????????????????????
		EnterpriseUserReqVo enterpriseUserReqVo = new EnterpriseUserReqVo();
		enterpriseUserReqVo.setUserId(borrowVo.getBorrowUserCode());
		logger.info("??????{}??????:" + JSONObject.toJSONString(enterpriseUserReqVo), IEnterpriseUserService.class);
		UserCommonResult<EnterpriseUserResVo> result2 = enterpriseUserService.getEnterpriseUser(enterpriseUserReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result2), IEnterpriseUserService.class);
		borrowVo.setBorrowUserName(result2.getBusinessObject().getLegalPersonName());
		if (borrowVo.getCollectedAmount() == null) {
			borrowVo.setRemainAmount(borrowVo.getBorrowAmount());
		} else {
			borrowVo.setRemainAmount(borrowVo.getBorrowAmount().subtract(borrowVo.getCollectedAmount()));
		}
		return borrowVo;
	}

	/**
	 * ????????????
	 *
	 * @param borrowExtendReqVo
	 * @param approveResult
	 *            ???????????? y ?????? n ??????
	 * @param type
	 *            ???????????? first ?????? second ??????
	 * @return
	 */
	@RequestMapping(value = "/{menu}/approveBorrow", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "????????????")
	public String approveBorrow(BorrowExtendReqVo borrowExtendReqVo, String approveResult, String type, String borrowId,
			HttpServletRequest httpRequest) {
		// ??????????????????
		BorrowReqVo borrowReqVo = new BorrowReqVo();
		if ("y".equals(approveResult)) {
			if ("first".equals(type)) {
				borrowReqVo.setStatus(BorrowStatus.APPROVAL2);
			} else {
				borrowReqVo.setStatus(BorrowStatus.INUSE);
			}
		} else {
			if ("first".equals(type)) {
				borrowReqVo.setStatus(BorrowStatus.REFUSE1);
			} else {
				borrowReqVo.setStatus(BorrowStatus.REFUSE2);
			}

		}
		borrowReqVo.setId(borrowId);
		borrowService.updateBorrow(borrowReqVo);
		// ??????????????????
		if ("first".equals(type)) {
			borrowExtendReqVo.setExtendType(BorrowExtendType.FIRSTTRIAL);
		} else {
			borrowExtendReqVo.setExtendType(BorrowExtendType.REVIEW);
		}
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String staffName = staffSession.getStaffName();
		borrowExtendReqVo.setEditedBy(staffName);
		borrowExtendReqVo.setBorrowCode(borrowId);
		logger.info("??????{}??????:" + JSONObject.toJSONString(borrowExtendReqVo), IBorrowExtendService.class);
		TradeCommonResult<BorrowExtendResVo> result = borrowExtendService.insertBorrowExtend(borrowExtendReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowExtendService.class);
		if (result.isSuccess()) {
			return "success";
		}
		return "fail";
	}

	/**
	 * ????????????
	 *
	 * @param borrowId
	 * @return
	 */
	@RequestMapping(value = "/repayList/detail", method = RequestMethod.POST)
	@ResponseBody
	public BorrowVo repayDetail(String borrowId) {
		BorrowVo borrow = new BorrowVo();
		try {
			TradeCommonResult<BorrowResVo> result = borrowService.getBorrowById(borrowId);
			borrow = BeanMapper.map(result.getBusinessObject(), BorrowVo.class);
			// ????????????????????????
			BaseReqVo baseReqVo = new BaseReqVo();
			baseReqVo.setUserId(borrow.getBorrowUserCode());
			DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> userResult = infoService
					.queryUserInfo(baseReqVo);
			com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo user = userResult.getBusinessObject();
			borrow.setBalance(user.getAvailableBalance());
			EnterpriseUserReqVo enterpriseUserReqVo = new EnterpriseUserReqVo();
			enterpriseUserReqVo.setUserId(borrow.getBorrowUserCode());
			logger.info("??????{}??????:" + JSONObject.toJSONString(enterpriseUserReqVo), IEnterpriseUserService.class);
			UserCommonResult<EnterpriseUserResVo> result2 = enterpriseUserService
					.getEnterpriseUser(enterpriseUserReqVo);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result2), IEnterpriseUserService.class);
			borrow.setBorrowUserName(result2.getBusinessObject().getLegalPersonName());
			// ??????????????????
			String date = computeBorrowDates(borrow.getLastReleaseTime(), borrow.getLastRepayTime());
			borrow.setBorrowDates(date);
			BorrowRepayPlanReqVo borrowRepayPlanReqVo = new BorrowRepayPlanReqVo();
			borrowRepayPlanReqVo.setBorrowCode(borrowId);
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowRepayPlanReqVo), IBorrowRepayPlanService.class);
			TradeCommonResult<PageInfo<BorrowRepayPlanResVo>> borrowRepayPlanResult = borrowRepayPlanService
					.list(borrowRepayPlanReqVo, 1, 1000);
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowRepayPlanResult), IBorrowRepayPlanService.class);
			borrow.setBorrowRepayPlans(borrowRepayPlanResult.getBusinessObject().getList());
			borrow.setNowDate(new Date());// ???????????????????????????"????????????"???"??????"???"????????????"???????????????
		} catch (Exception e) {
			logger.error("????????????????????????", e);
		}
		// ??????????????????????????????
		return borrow;
	}

	/**
	 * ???????????????
	 *
	 * @param borrowId
	 * @return
	 */
	@RequestMapping(value = "/repayList/borrowerDetail", method = RequestMethod.POST)
	@ResponseBody
	public BorrowVo repayBorrowerDetail(String borrowId) {
		TradeCommonResult<BorrowResVo> result = borrowService.getBorrowById(borrowId);
		BorrowVo borrow = BeanMapper.map(result.getBusinessObject(), BorrowVo.class);
		UserAccountInfoResVo uerAccountInfoReqVo = new UserAccountInfoResVo();
		uerAccountInfoReqVo.setUserId(borrow.getBorrowUserCode());
		// ????????????????????????
		EnterpriseUserReqVo enterpriseUserReqVo = new EnterpriseUserReqVo();
		enterpriseUserReqVo.setUserId(borrow.getBorrowUserCode());
		logger.info("??????{}??????:" + JSONObject.toJSONString(enterpriseUserReqVo), IEnterpriseUserService.class);
		UserCommonResult<EnterpriseUserResVo> result2 = enterpriseUserService.getEnterpriseUser(enterpriseUserReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result2), IEnterpriseUserService.class);
		borrow.setBorrowUserName(result2.getBusinessObject().getLegalPersonName());
		// ??????????????????
		// String date = computeBorrowDates(borrow.getLastReleaseTime(),
		// borrow.getLastRepayTime());
		// borrow.setBorrowDates(date);

		BorrowRepayPlanReqVo borrowRepayPlanReqVo = new BorrowRepayPlanReqVo();
		borrowRepayPlanReqVo.setBorrowCode(borrowId);
		logger.info("??????{}??????:" + JSONObject.toJSONString(borrowRepayPlanReqVo), IBorrowRepayPlanService.class);
		TradeCommonResult<PageInfo<BorrowRepayPlanResVo>> borrowRepayPlanResult = borrowRepayPlanService
				.list(borrowRepayPlanReqVo, 1, 1000);
		logger.info("??????{}??????:" + JSONObject.toJSONString(borrowRepayPlanResult), IBorrowRepayPlanService.class);
		borrow.setBorrowRepayPlans(borrowRepayPlanResult.getBusinessObject().getList());

		return borrow;
	}

	/**
	 * ??????
	 *
	 * @param borrowId
	 * @param phase
	 * @param repayType
	 * @return
	 */
	@RequestMapping(value = { "/repayList/repay", "/l_RepayRecord/repay" }, method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "??????")
	public TradeCommonResult<Integer> repay(String borrowId, int phase, String repayType, Date actualInterestEndDate) {
		logger.info(">>>>>>>>>>????????????borrowId=" + borrowId + "??????phase=" + phase + "repayType=" + repayType);
		if (RepayType.ADVANCE.getCode().equals(repayType)) {
			// ????????????????????????????????????
			logger.info("??????{}??????:{borrowId=" + borrowId + "}", IBorrowService.class);
			TradeCommonResult<BorrowResVo> borrowResult = borrowService.getBorrowById(borrowId);
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowResult), IBorrowService.class);
			BorrowResVo borrow = borrowResult.getBusinessObject();
			// ????????????
			/*
			 * int borrowMonth =
			 * DateUtil.monthsInterestBetween(borrow.getLastReleaseTime(),
			 * borrow.getLastRepayTime()); // ????????????????????????????????? if (borrowMonth < 3) {
			 * return new TradeCommonResult<Integer>("99999", "?????????????????????", 0); }
			 * // ??????????????????????????????????????? if (3 <= borrowMonth && borrowMonth < 6) { //
			 * ???????????????????????? int month = DateUtil.monthsInterestBetween(new Date(),
			 * borrow.getLastRepayTime()); if (month > 1) { return new
			 * TradeCommonResult<Integer>("99999", "?????????????????????", 0); } } //
			 * ?????????????????????????????????????????? if (borrowMonth >= 6) { // ???????????????????????? int month =
			 * DateUtil.monthsInterestBetween(new Date(),
			 * borrow.getLastRepayTime()); if (month > 2) { return new
			 * TradeCommonResult<Integer>("99999", "?????????????????????", 0); } }
			 */
		}

		RepayReqVo repayReqVo = new RepayReqVo();
		repayReqVo.setBorrowId(borrowId);
		repayReqVo.setPhase(phase);
		repayReqVo.setRepayWay(RepayWay.BORROWER);
		repayReqVo.setRepayType(RepayType.getType(repayType));
		if (null != actualInterestEndDate) {
			repayReqVo.setActualInterestEndDate(actualInterestEndDate);
		}
		logger.info("??????{}??????:" + JSONObject.toJSONString(repayReqVo), ILoanOperService.class);
		TradeCommonResult<Integer> result = loanOperService.repay(repayReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), ILoanOperService.class);
		// logger.info(">>>>>>>>>>????????????borrowId=" + borrowId + "??????phase=" + phase
		// + "repayType=" + repayType + "result=" +
		// JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * ??????????????????
	 * 
	 * @param borrowId
	 * @return
	 */
	@RequestMapping(value = { "/repayList/loanList", "/l_asset_list/loanList" }, method = RequestMethod.POST)
	@ResponseBody
	public Result loanList(String borrowId) {
		LoanReqVo loanreqVo = new LoanReqVo();
		loanreqVo.setBorrowerCode(borrowId);
		TradeCommonResult<PageInfo<LoanResVo>> result = loanService.listLoan(loanreqVo, 1, 1000);
		if (result.isSuccess()) {
			PageInfo pageInfo = result.getBusinessObject();
			pageInfo.setList(BeanMapper.mapList(pageInfo.getList(), LoanResForm.class));
			return Result.success(pageInfo);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ??????????????????
	 *
	 * @param borrowId
	 * @param phase
	 * @return
	 */
	@RequestMapping(value = "/repayList/earlyRepayDetail", method = RequestMethod.POST)
	@ResponseBody
	public BorrowVo loanDetail(String borrowId, int phase, Date actualInterestEndDate) {
		TradeCommonResult<BorrowResVo> result = borrowService.getBorrowById(borrowId);
		BorrowVo borrow = BeanMapper.map(result.getBusinessObject(), BorrowVo.class);
		logger.info("??????{}??????:{borrowId=" + borrowId + ",phase=" + phase + "}", IBorrowRepayPlanService.class);
		TradeCommonResult<BorrowRepayPlanResVo> borrowRepayPlanResult = borrowRepayPlanService
				.queryEarlyRepayPlan(borrowId, phase, actualInterestEndDate);
		logger.info("??????{}??????:" + JSONObject.toJSONString(borrowRepayPlanResult), IBorrowRepayPlanService.class);
		List<BorrowRepayPlanResVo> list = new ArrayList<BorrowRepayPlanResVo>();
		BorrowRepayPlanResVo vo = borrowRepayPlanResult.getBusinessObject();
		list.add(vo);
		borrow.setBorrowRepayPlans(list);
		// ????????????????????????
		SimpleDateFormat ftimes = new SimpleDateFormat("yyyy-MM-dd");
		borrow.setRemainingDays(
				ftimes.format(borrowRepayPlanResult.getBusinessObject().getInterestEndDate()).toString());
		borrow.setInvestCycle(computeBorrowDates(borrow.getLastReleaseTime(), borrow.getLastRepayTime()));
		borrow.setNextPhase(Integer.toString(phase));
		return borrow;
	}

	/**
	 * ?????????????????????
	 *
	 * @param loanId
	 * @param phase
	 * @return
	 */
	@RequestMapping(value = "/repayList/userDetail", method = RequestMethod.POST)
	@ResponseBody
	public LoanVo userDetail(String loanId, int phase) {
		LoanReqVo condition = new LoanReqVo();
		condition.setId(loanId);
		logger.info("??????{}??????:" + JSONObject.toJSONString(condition), ILoanService.class);
		TradeCommonResult<LoanResVo> loanRes = loanService.getLoan(condition);
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanRes), ILoanService.class);
		LoanResVo loanResVo = loanRes.getBusinessObject();
		LoanVo loanVo = BeanMapper.map(loanResVo, LoanVo.class);
		// ?????????????????????????????????
		InterestPlansReqVo interestPlansReqVo = new InterestPlansReqVo();
		interestPlansReqVo.setLoanCode(loanId);
		interestPlansReqVo.setPhase(phase);
		logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansReqVo), IInterestPlansService.class);
		TradeCommonResult<List<InterestPlansResVo>> interestPlans = interestPlansService
				.listInterestPlans(interestPlansReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlans), IInterestPlansService.class);
		List<InterestPlans> plans = new ArrayList<InterestPlans>();
		for (InterestPlansResVo interestPlansResVo : interestPlans.getBusinessObject()) {
			InterestPlans plan = BeanMapper.map(interestPlansResVo, InterestPlans.class);
			// ????????????
			plan.setPenalty(
					interestPlansResVo.getEarlyRepayment().addTo(interestPlansResVo.getPlatformEarlyRepayment()));
			logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansResVo), IUserInfoService.class);
			UserCommonResult<UserInfoResVo> userResult = userInfoService
					.getByRoleUserId(interestPlansResVo.getUserCode());
			logger.info("??????{}??????:" + JSONObject.toJSONString(userResult), IUserInfoService.class);
			UserInfoResVo user = userResult.getBusinessObject();
			plan.setUserName(user.getRealName());
			plan.setPhone(user.getRegisterMobile());
			logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansResVo), ILoanInvestorService.class);
			TradeCommonResult<LoanInvestorResVo> investResult = loanInvestorService
					.getById(interestPlansResVo.getInvestorCode());
			logger.info("??????{}??????:" + JSONObject.toJSONString(investResult), ILoanInvestorService.class);
			plan.setRedMoneyAmount(investResult.getBusinessObject().getRedMoneyAmount());
			plans.add(plan);
		}
		// ????????????????????????
		loanVo.setRemainingDays(DateUtil.daysBetween(loanVo.getInterestEndDate(), loanVo.getInterestStartDate()) + "");
		loanVo.setInterestPlans(plans);
		return loanVo;
	}

	/**
	 * ??????Excel??????
	 *
	 * @param loanId
	 * @param phase
	 * @param response
	 */
	@RequestMapping(value = "/export")
	public void export(String loanId, int phase, HttpServletResponse response) {
		try {
			// ?????????????????????????????????
			InterestPlansReqVo interestPlansReqVo = new InterestPlansReqVo();
			interestPlansReqVo.setLoanCode(loanId);
			interestPlansReqVo.setPhase(phase);
			logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansReqVo), IInterestPlansService.class);
			TradeCommonResult<List<InterestPlansResVo>> interestPlans = interestPlansService
					.listInterestPlans(interestPlansReqVo);
			logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlans), IInterestPlansService.class);
			List<InterestExcelPlans> plans = new ArrayList<InterestExcelPlans>();
			for (InterestPlansResVo interestPlansResVo : interestPlans.getBusinessObject()) {
				InterestExcelPlans plan = new InterestExcelPlans();
				// ????????????
				UserCommonResult<UserInfoResVo> userResult = userInfoService
						.getByRoleUserId(interestPlansResVo.getUserCode());
				UserInfoResVo user = userResult.getBusinessObject();
				logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansResVo), ILoanInvestorService.class);
				TradeCommonResult<LoanInvestorResVo> investResult = loanInvestorService
						.getById(interestPlansResVo.getInvestorCode());
				logger.info("??????{}??????:" + JSONObject.toJSONString(investResult), ILoanInvestorService.class);
				plan.setDiscount(new Money());

				plan.setInterest(interestPlansResVo.getInterestAmount().addTo(interestPlansResVo.getEarlyRepayment())
						.addTo(interestPlansResVo.getPlatformEarlyRepayment()));
				plan.setInvestAmount(interestPlansResVo.getPrincipalAmount());
				plan.setPhase(phase);
				plan.setPhone(user.getRegisterMobile());
				plan.setPrincipalAmount(interestPlansResVo.getPrincipalAmount());
				plan.setRedMoneyAmount(investResult.getBusinessObject().getRedMoneyAmount());
				plan.setRealPay(interestPlansResVo.getPrincipalAmount()
						.subtractFrom(investResult.getBusinessObject().getRedMoneyAmount()));
				Money m1 = new Money();
				Money m2 = new Money();
				m1.setAmount(plan.getInvestAmount().getAmount());
				m2.setAmount(plan.getInterest().getAmount());
				plan.setTotal(m1.addTo(m2));
				plan.setUserCode(interestPlansResVo.getInvestorCode());
				plan.setUserName(user.getRealName());
				if ("INIT".equals(interestPlansResVo.getStatus())) {
					plan.setStatus("?????????");
				}
				if ("OVERDUE".equals(interestPlansResVo.getStatus())) {
					plan.setStatus("?????????");
				}
				if ("FINISH".equals(interestPlansResVo.getStatus())) {
					plan.setStatus("?????????");
				}
				plans.add(plan);
			}
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String("settle.xls".getBytes("GBK"), "ISO-8859-1"));
			String[] headers;
			ExcelUtils<InterestExcelPlans> utils = new ExcelUtils<InterestExcelPlans>();
			headers = new String[] { "??????ID", "?????????", "??????", "????????????", "????????????", "????????????", "????????????", "??????", "??????", "??????", "??????",
					"??????" };
			utils.exportExcel("??????????????????", headers, plans, response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
		} catch (Exception e) {
			logger.error("excel??????????????????", e);
		}
	}

	public static String computeBorrowDates(Date date1, Date date2) {
		int result;
		String dateStr1 = DateUtil.formatDate(date1, "YYYY-MM-dd");
		String dateStr2 = DateUtil.formatDate(date2, "YYYY-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		if (dateStr1.equals(dateStr2)) {
			return "1???";
		}
		// if (dateStr1.split("-")[2].equals(dateStr2.split("-")[2])) {
		// result = DateUtil.monthsInterestBetween(date1, date2);
		// return result + "???";
		// }
		result = DateUtil.daysInterestBetween(date2, date1);
		return result + "???";
	}

	private Date formatDate(Date date) {
		String dateStr = DateUtil.formatDate(date, "yyyy-MM-dd");
		Date returnDate = DateUtil.parseDate(dateStr, "yyyy-MM-dd");
		return returnDate;// ?????????
	}

	@RequestMapping(value = "/{menu}/delBorrowFile", method = RequestMethod.POST)
	@ResponseBody
	public Result delBorrowFile(BorrowFileReqVo borrowFileReqVo, HttpServletRequest servletRequest) {
		Staff staffSession = (Staff) servletRequest.getSession().getAttribute("adminInfo");
		String staffName = staffSession.getStaffName();
		borrowFileReqVo.setEditedBy(staffName);
		TradeCommonResult<Integer> a = borrowFileService.deleteBorrowFile(borrowFileReqVo);
		if (a.isSuccess()) {
			return Result.success("success");
		} else {
			return Result.error("error");
		}
	}

	/**
	 * ???????????????????????????
	 */
	@RequestMapping(value = "/{menu}/audit", method = RequestMethod.POST)
	@ResponseBody
	public Result getbaseInfo1(String borrowId, HttpServletRequest servletRequest) {

		Staff staffSession = (Staff) servletRequest.getSession().getAttribute("adminInfo");
		String staffName = staffSession.getStaffName();
		try {
			List resultFinal = new ArrayList();
			// ??????ID????????????????????????
			logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(borrowId), IBorrowerService.class);
			TradeCommonResult<BorrowResVo> result = borrowService.getBorrowById(borrowId);
			// ?????????????????????
			HashMap<String, String> map = new HashMap<>();
			String releaseUserName = StringUtils.EMPTY;
			String releaseCertNo = StringUtils.EMPTY;
			if (StringUtils.isNotBlank(result.getBusinessObject().getReleaseUserId())) {
				UserCommonResult<UserInfoResVo> userInfoResult = userInfoService
						.getByRoleUserId(result.getBusinessObject().getReleaseUserId());
				if (UserType.PERSON.equals(userInfoResult.getBusinessObject().getUserType())) {
					releaseUserName = userInfoResult.getBusinessObject().getRealName();
					releaseCertNo = userInfoResult.getBusinessObject().getCertNo();
					map.put("releaseUserName", releaseUserName);
					map.put("releaseCertNo", releaseCertNo);
					map.put("userId", result.getBusinessObject().getReleaseUserId());
				} else {
					EnterpriseUserReqVo reqVo2 = new EnterpriseUserReqVo();
					reqVo2.setUserId(userInfoResult.getBusinessObject().getId());
					UserCommonResult<EnterpriseUserResVo> reeuser = enterpriseUserService.getEnterpriseUser(reqVo2);
					releaseUserName = reeuser.getBusinessObject().getLegalPersonName();
					releaseCertNo = reeuser.getBusinessObject().getLegalPersonCertNo();
					map.put("releaseUserName", releaseUserName);
					map.put("releaseCertNo", releaseCertNo);
					map.put("userId", result.getBusinessObject().getReleaseUserId());
				}
			}

			// ???????????????id??????????????????
			SealImageRecordReqVo reqVo = new SealImageRecordReqVo();
			reqVo.setTransStatus(TransStatus.SUCCESS);
			reqVo.setUserId(result.getBusinessObject().getBorrowUserCode());
			ProtocolCommonResult<SealImageRecordResVo> borrowerSealImageResVo = sealImageRecord.get(reqVo);
			// ???????????????id??????????????????
			SealImageRecordReqVo reqVo1 = new SealImageRecordReqVo();
			reqVo1.setTransStatus(TransStatus.SUCCESS);
			reqVo1.setUserId(result.getBusinessObject().getGuaranteeUserId());
			ProtocolCommonResult<SealImageRecordResVo> guaranteeSealImageResVo = sealImageRecord.get(reqVo1);

			BorrowExtendReqVo borrowExtendReqVo = new BorrowExtendReqVo();
			borrowExtendReqVo.setBorrowCode(borrowId);
			TradeCommonResult<PageInfo<BorrowExtendResVo>> brvl = borrowExtendService
					.listBorrowExtend(borrowExtendReqVo, 1, 100);
			for (BorrowExtendResVo b : brvl.getBusinessObject().getList()) {
				if (b.getExtendType() != null) {
					b.setBorrowCode(b.getExtendType().getName());
				}
				if (b.getStatus() != null) {
					b.setStatus(BorrowStatus.valueOf(b.getStatus()).getName());
				}
			}
			logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(result), IBorrowerService.class);
			BorrowReqVo borrowReqVo = new BorrowReqVo();
			BorrowerReqVo brv = new BorrowerReqVo();
			brv.setBorrowId(borrowId);
			BorrowResBo borrowResBo = BeanMapper.map(result.getBusinessObject(), BorrowResBo.class);
			if (null != borrowResBo.getBorrowLocation())
				borrowResBo.setBorrowLocation(CityListType.valueOf(borrowResBo.getBorrowLocation()).getName());
			if (null != borrowResBo.getBorrowRate()) {
				borrowResBo.setBorrowRate(borrowResBo.getBorrowRate().divide(new BigDecimal(100)));
			}
			GuaranteeReqVo grq = new GuaranteeReqVo();
			grq.setBorrowId(borrowId);
			grq.setMainFlag(1);
			try {
				// ????????????
				logger.info("????????????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
				TradeCommonResult<BorrowerResVo> result2 = borrowerService.get(brv);
				if (result2.isSuccess() && result2.getBusinessObject() != null) {
					if (result2.getBusinessObject().getIndustry() != null) {
						result2.getBusinessObject().setIndustry(
								IndustryType.valueOf(result2.getBusinessObject().getIndustry()).getMessage());
					}
					if (result2.getBusinessObject().getMarriage() != null) {
						result2.getBusinessObject().setMarriage(
								MarriageType.valueOf(result2.getBusinessObject().getMarriage()).getMessage());
					}
				}
				logger.info("????????????{}??????:" + JSONObject.toJSONString(result2), IBorrowService.class);

				// ????????????
				logger.info("????????????{}??????:" + JSONObject.toJSONString(grq), IGuaranteeService.class);
				TradeCommonResult<GuaranteeResVo> result3 = guaranteeService.get(grq);
				if (result3.isSuccess() && result3.getBusinessObject().getRelationOfEnterprise() != null) {
					result3.getBusinessObject().setRelationOfEnterprise(EntRelationType
							.valueOf(result3.getBusinessObject().getRelationOfEnterprise()).getMessage());
				}
				if (result3.isSuccess() && result3.getBusinessObject().getMarriage() != null) {
					result3.getBusinessObject()
							.setMarriage(MarriageType.valueOf(result3.getBusinessObject().getMarriage()).getMessage());
				}
				logger.info("????????????{}??????:" + JSONObject.toJSONString(result3), IGuaranteeService.class);
				GuaranteeReqVo grq2 = new GuaranteeReqVo();
				grq2.setBorrowId(borrowId);
				grq2.setMainFlag(0);
				TradeCommonResult<PageInfo<GuaranteeResVo>> result4 = guaranteeService.list(grq2, 1, 100);
				if (result4.isSuccess() && result4.getBusinessObject().getList().size() > 0) {
					for (GuaranteeResVo g : result4.getBusinessObject().getList()) {
						SealImageRecordReqVo reqVo11 = new SealImageRecordReqVo();
						reqVo11.setTransStatus(TransStatus.SUCCESS);
						reqVo11.setUserId(g.getGuaranteeUserId());
						ProtocolCommonResult<SealImageRecordResVo> gr = sealImageRecord.get(reqVo11);
						g.setEditedBy(gr.getBusinessObject().getSavePath());
					}
				}
				borrowResBo.setBorrowerResVo(result2.getBusinessObject());
				borrowResBo.setGuaranteeResVo(result3.getBusinessObject());
				borrowResBo.setEditedBy(staffName);
				BorrowFileReqVo borrowFileReqVo = new BorrowFileReqVo();
				borrowFileReqVo.setBorrowCode(borrowId);
				borrowFileReqVo.setFileType(FileType.CREDIT);
				TradeCommonResult<List<BorrowFileResVo>> bFResVo = borrowFileService.listBorrowFile(borrowFileReqVo);
				for (BorrowFileResVo borrowFileResVo : bFResVo.getBusinessObject()) {
					borrowFileResVo.setMark("CREDIT");
				}
				BorrowFileReqVo borrowFileReqVo1 = new BorrowFileReqVo();
				borrowFileReqVo1.setBorrowCode(borrowId);
				borrowFileReqVo1.setFileType(FileType.CREDIT_RISK);
				TradeCommonResult<List<BorrowFileResVo>> bFResVo1 = borrowFileService.listBorrowFile(borrowFileReqVo1);
				for (BorrowFileResVo borrowFileResVo : bFResVo1.getBusinessObject()) {
					borrowFileResVo.setMark("CREDIT_RISK");
				}
				EnterpriseUserReqVo enterpriseUserReqVo = new EnterpriseUserReqVo();
				enterpriseUserReqVo.setUserId(result2.getBusinessObject().getBorrowerId());
				UserCommonResult<EnterpriseUserResVo> enterpriseUserResVov = enterpriseUserService
						.getEnterpriseUser(enterpriseUserReqVo);
				resultFinal.add(borrowResBo);
				resultFinal.add(bFResVo.getBusinessObject());
				resultFinal.add(enterpriseUserResVov);
				resultFinal.add(brvl.getBusinessObject().getList());
				resultFinal.add(bFResVo1.getBusinessObject());
				resultFinal.add(borrowerSealImageResVo.getBusinessObject());
				resultFinal.add(guaranteeSealImageResVo.getBusinessObject());
				resultFinal.add(result4.getBusinessObject().getList());// ?????????????????????
				resultFinal.add(map);
			} catch (Exception e) {
				logger.error("???????????????????????????", e);
			}

			return Result.success(resultFinal);
		} catch (Exception e) {
			logger.error("???????????????????????????", e);
		}
		return Result.error("???????????????????????????");
	}

	/**
	 * ?????? ??????
	 */
	@RequestMapping(value = "/{menu}/finalReview", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "????????????")
	public Result finalReview(BorrowReqVo borrow, String mark, String reviewCode, String editBy,
			String borrowExtendType, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		List<BorrowExtendReqVo> list = new ArrayList<BorrowExtendReqVo>();
		BorrowExtendReqVo borrowExtendReqVo = new BorrowExtendReqVo();
		borrowExtendReqVo.setBorrowCode(borrow.getId());
		borrowExtendReqVo.setEditedBy(editBy);// ?????????
		borrowExtendReqVo.setExtendType(BorrowExtendType.valueOf(borrowExtendType));// ????????????
		borrowExtendReqVo.setMark(mark);// ????????????
		borrowExtendReqVo.setStatus(reviewCode);// ????????????
		list.add(borrowExtendReqVo);
		borrow.setBorrowExtendList(list);
		borrow.setStatus(BorrowStatus.valueOf(reviewCode));
		TradeCommonResult<BorrowResVo> result = borrowService.approveBorrow(borrow);
		if (result.isSuccess()) {
			if (borrowExtendReqVo.getExtendType().equals(BorrowExtendType.FINAL_CHECK)) {
				return Result.success("????????????");
			}
			if (borrow.getStatus().equals(BorrowStatus.PASS_RECHECK)
					|| borrow.getStatus().equals(BorrowStatus.PASS_FINANCE)) {
				// ??????????????????
				sendEmail(staffSession, borrow.getId(), true);
			} else if (borrow.getStatus().equals(BorrowStatus.BACK_RECHECK)
					|| borrow.getStatus().equals(BorrowStatus.BACK_FINANCE)) {
				// ??????????????????
				sendEmail(staffSession, borrow.getId(), false);
			}

			return Result.success("????????????");
		} else {
			return Result.error("????????????");
		}
	}

	/**
	 * ?????? ????????????
	 */
	@RequestMapping(value = "/{menu}/assetAudit", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "?????? ????????????")
	public String assetAudit(BorrowReqVo borrow, String approveResult, String auditRecommendations,
			String projectDetails, String borrowId, HttpServletRequest httpRequest,String zijiuer) {
		borrow.setReleaseUserId(zijiuer);
		if (borrow.getOaFlowCode() != null) {
			borrow.setOaFlowCode(borrow.getOaFlowCode().trim());
		}
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String staffName = staffSession.getStaffName();

		List<BorrowExtendReqVo> list = new ArrayList<BorrowExtendReqVo>();
		// ????????????
		BorrowExtendReqVo borrowExtendReqVo1 = new BorrowExtendReqVo();
		borrowExtendReqVo1.setExtendType(BorrowExtendType.PROJECT);
		borrowExtendReqVo1.setMark(projectDetails);
		borrowExtendReqVo1.setBorrowCode(borrowId);
		// ????????????
		BorrowExtendReqVo borrowExtendReqVo2 = new BorrowExtendReqVo();
		borrowExtendReqVo2.setBorrowCode(borrowId);
		borrowExtendReqVo2.setExtendType(BorrowExtendType.BUS_MANAGER);
		borrowExtendReqVo2.setMark(auditRecommendations);
		borrowExtendReqVo2.setStatus(approveResult);
		borrowExtendReqVo2.setEditedBy(staffName);

		list.add(borrowExtendReqVo1);
		list.add(borrowExtendReqVo2);
		// ???????????????
		if (borrow.getBorrowRate() != null) {
			borrow.setBorrowRate(borrow.getBorrowRate().multiply(new BigDecimal(100)));
		}
		borrow.setBorrowExtendList(list);
		borrow.setId(borrowId);
		borrow.setStatus(BorrowStatus.valueOf(approveResult));
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(borrow), IBorrowService.class);
		TradeCommonResult<BorrowResVo> result = borrowService.approveBorrow(borrow);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), IBorrowService.class);
		if (result.isSuccess()) {
			if (borrow.getStatus().equals(BorrowStatus.PASS_BUS_MANAGER)) {
				// ??????????????????
				sendEmail(staffSession, borrowId, true);
			}
			return "success";
		}
		return "fail";
	}

	/**
	 * ??????
	 */
	@RequestMapping(value = "/{menu}/firstAssetAudit", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "?????? ??????")
	public String firstAssetAudit(BorrowReqVo borrow, String approveResult, String auditRecommendations,
			String securityAssurance, String riskControl, String borrowId, HttpServletRequest httpRequest,
			String[] riskfiles, String[] riskfilesname, String isSave, String saveId, String zijiuer) {

		borrow.setReleaseUserId(zijiuer);
		if(null != borrow.getOaFlowCode()) {
			borrow.setOaFlowCode(borrow.getOaFlowCode().trim());
		}
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String staffName = staffSession.getStaffName();

		ArrayList<BorrowFileReqVo> arrayList = new ArrayList<BorrowFileReqVo>();
		if (riskfiles != null) {
			for (int i = 0; i < riskfiles.length; i++) {
				BorrowFileReqVo borrowFileReqVo = new BorrowFileReqVo();
				borrowFileReqVo.setEditedBy(staffName);
				borrowFileReqVo.setBorrowCode(borrowId);
				borrowFileReqVo.setFileType(FileType.CREDIT_RISK);
				borrowFileReqVo.setFileUrl(riskfiles[i]);
				borrowFileReqVo.setTitle(riskfilesname[i]);
				arrayList.add(borrowFileReqVo);
			}
		}
		// ??????????????????
		List<BorrowExtendReqVo> list = new ArrayList<BorrowExtendReqVo>();
		// ????????????
		BorrowExtendReqVo borrowExtendReqVo1 = new BorrowExtendReqVo();
		borrowExtendReqVo1.setExtendType(BorrowExtendType.SECURITY);
		borrowExtendReqVo1.setMark(securityAssurance);
		borrowExtendReqVo1.setBorrowCode(borrowId);
		// ????????????
		BorrowExtendReqVo borrowExtendReqVo3 = new BorrowExtendReqVo();
		borrowExtendReqVo3.setExtendType(BorrowExtendType.RISK);
		borrowExtendReqVo3.setMark(riskControl);
		borrowExtendReqVo3.setBorrowCode(borrowId);

		// ????????????

		BorrowExtendReqVo borrowExtendReqVo2 = new BorrowExtendReqVo();
		if ("yes".equals(isSave)) {
			borrowExtendReqVo2.setAction("save");

			if (saveId != null && !"".equals(saveId)) {
				borrowExtendReqVo2.setId(saveId);
			}
		} else {
			borrow.setStatus(BorrowStatus.valueOf(approveResult));
		}
		borrowExtendReqVo2.setBorrowCode(borrowId);
		borrowExtendReqVo2.setExtendType(BorrowExtendType.FIRST_CHECK);
		borrowExtendReqVo2.setMark(auditRecommendations);
		borrowExtendReqVo2.setStatus(approveResult);
		borrowExtendReqVo2.setEditedBy(staffName);

		list.add(borrowExtendReqVo1);
		list.add(borrowExtendReqVo2);
		list.add(borrowExtendReqVo3);
		borrow.setBorrowFileList(arrayList);
		borrow.setBorrowExtendList(list);
		borrow.setId(borrowId);
		if (borrow.getBorrowAmount() != null) {
			borrow.setBorrowAmount(borrow.getBorrowAmount().multiplyBy(new BigDecimal(10000)));
		}
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(borrow), IBorrowService.class);
		TradeCommonResult<BorrowResVo> result = borrowService.approveBorrow(borrow);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), IBorrowService.class);
		if (result.isSuccess()) {
			if (BorrowStatus.PASS_FIRST_CHECK.equals(borrow.getStatus()) && !"yes".equals(isSave)) {
				// ??????????????????
				sendEmail(staffSession, borrowId, true);
			} else if (BorrowStatus.BACK_FIRST_CHECK.equals(borrow.getStatus()) && !"yes".equals(isSave)) {
				// ??????????????????
				sendEmail(staffSession, borrowId, false);
			}
			return "success";
		}
		return "fail";
	}

	/**
	 * ????????????
	 * 
	 * @param staff
	 */
	private void sendEmail(Staff staff, String borrowId, boolean next) {
		if (staff == null) {
			logger.error("???????????????null, {}", staff);
			return;
		}
		String preRoleId = staff.getRoleId();
		if (Strings.isNullOrEmpty(preRoleId)) {
			logger.error("????????????????????? null, {}", staff);
			return;
		}
		String nextRoleId = "";
		int tjIndex = tjRoleList.indexOf(preRoleId);
		if (tjIndex >= 0 && tjIndex != tjRoleList.size() - 1) {
			int index = next ? (tjIndex + 1) : (tjIndex - 1);
			if (index >= 0) {
				nextRoleId = tjRoleList.get(index);
			}
		}
		int bjIndex = bjRoleList.indexOf(preRoleId);
		if (bjIndex >= 0 && bjIndex != bjRoleList.size() - 1) {
			int index = next ? (bjIndex + 1) : (bjIndex - 1);
			if (index >= 0) {
				nextRoleId = bjRoleList.get(index);
			}
		}
		if (nextRoleId.isEmpty()) {
			logger.debug("??????????????????, {}", nextRoleId);
			return;
		}
		BorrowResVo borrowResVo = new BorrowResBo();
		try {
			TradeCommonResult<BorrowResVo> borrowResVoTradeCommonResult = borrowService.getBorrowById(borrowId);
			borrowResVo = borrowResVoTradeCommonResult.getBusinessObject();
		} catch (Exception e) {
			logger.error("????????????????????????????????????dubbo ??????????????????borrowId: " + borrowId, e);
		}
		notificationDomain.sendEmail(nextRoleId, borrowResVo);
	}

	private List<BorrowStatus> convertBorrowStatus(FilterStatus filterStatus) {
		if (filterStatus == null) {
			filterStatus = FilterStatus.ALL;
		}
		return filterStatus.borrowStatusList;
	}

	/**
	 * ????????????
	 */
	public enum FilterStatus {
		/* ???????????? */
		BUS_MANAGER_CHECK(
				Lists.newArrayList(BorrowStatus.SUBMIT, BorrowStatus.BACK_BUS_MANAGER, BorrowStatus.BACK_FIRST_CHECK)),
		/* ???????????? */
		FIRST_CHECK(Lists.newArrayList(BorrowStatus.PASS_BUS_MANAGER, BorrowStatus.BACK_RECHECK)),
		/* ???????????? */
		RECHECK(Lists.newArrayList(BorrowStatus.PASS_FIRST_CHECK, BorrowStatus.BACK_FINANCE)),
		/* ???????????? */
		FINANCE_CHECK(Lists.newArrayList(BorrowStatus.PASS_RECHECK, BorrowStatus.BACK_FINAL_CHECK)),
		/* ???????????? */
		FINAL_CHECK(Lists.newArrayList(BorrowStatus.PASS_FINANCE)),
		/* ???????????? */
		PASS(Lists.newArrayList(BorrowStatus.PASS_FINAL_CHECK, BorrowStatus.LOCK, BorrowStatus.SPLIT)),
		/* ????????? */
		OPEN(Lists.newArrayList(BorrowStatus.OPEN)),
		/* ????????? */
		RELEASE(Lists.newArrayList(BorrowStatus.RELEASE)),
		/* ????????? */
		FINISH(Lists.newArrayList(BorrowStatus.FINISH)),
		/* ????????? */
		REFUSE(Lists.newArrayList(BorrowStatus.REFUSE_BUS_MANAGER, BorrowStatus.REFUSE_FIRST_CHECK,
				BorrowStatus.REFUSE_RECHECK, BorrowStatus.REFUSE_FINANCE, BorrowStatus.REFUSE_FINAL_CHECK)),
		/* ?????? */
		ALL(Lists.newArrayList(BorrowStatus.OPEN, BorrowStatus.RELEASE, BorrowStatus.KILLED, BorrowStatus.OVERDUE,
				BorrowStatus.LOCK));

		/**
		 * ????????????
		 */
		private List<BorrowStatus> borrowStatusList;

		FilterStatus(List<BorrowStatus> borrowStatusList) {
			this.borrowStatusList = borrowStatusList;
		}

		public List<BorrowStatus> getBorrowStatusList() {
			return borrowStatusList;
		}
	}

	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/repay_user_list/repayUserList", method = RequestMethod.POST)
	@ResponseBody
	public Result repayUserList(OtherRepaySettingReqVo request, int pageNo, int pageSize,
			HttpServletRequest httpRequest) {

		Staff staff = (Staff) httpRequest.getSession().getAttribute(AdminUserController.USERNAME);
		if (staff != null) {
			// ??????????????????
			String roleId = staff.getRoleId();
			String area = getAreaByRole(roleId);
			if (area != null && !area.isEmpty()) {
				request.setEditedBy(area);
			}
		}
		if (StringUtils.isBlank(request.getBorrowName())) {
			request.setBorrowName(null);
		}
		if (StringUtils.isBlank(request.getOaFlowCode())) {
			request.setOaFlowCode(null);
		}
		if (StringUtils.isBlank(request.getOtherRepayUserName())) {
			request.setOtherRepayUserName(null);
		}

		logger.info("????????????????????????{}?????????" + JSONObject.toJSONString(request), IOtherRepaySettingService.class);
		TradeCommonResult<PageInfo<OtherRepaySettingResVo>> result = otherRepaySettingService.list(request, pageNo, pageSize);
		logger.info("????????????????????????{}?????????" + JSONObject.toJSONString(result), IOtherRepaySettingService.class);
		ArrayList<RepayUserVo> arrayList = new ArrayList<RepayUserVo>();
		List<OtherRepaySettingResVo> list = result.getBusinessObject().getList();
		PageInfo<RepayUserVo> pageInfo = new PageInfo<RepayUserVo>();
		for (int i = 0; i < list.size(); i++) {
			TradeCommonResult<BorrowResVo> borrowVo = borrowService.getBorrowById(list.get(i).getBorrowId());
			RepayUserVo repayUserVo = new RepayUserVo();
			repayUserVo.setId(list.get(i).getId());
			repayUserVo.setBorrowId(list.get(i).getBorrowId());
			repayUserVo.setBorrowName(list.get(i).getBorrowName());
			repayUserVo.setOaFlowCode(list.get(i).getOaFlowCode());
			repayUserVo.setOtherRepayUserName(list.get(i).getOtherRepayUserName());
			if (null != borrowVo.getBusinessObject()) {
				repayUserVo.setEnterpriseName(borrowVo.getBusinessObject().getBorrowUserName());
			} else {
				repayUserVo.setEnterpriseName(null);
			}
			arrayList.add(repayUserVo);
		}
		pageInfo.setList(arrayList);
		pageInfo.setPages(result.getBusinessObject().getPages());
		pageInfo.setPageSize(result.getBusinessObject().getPageSize());
		if (result.isSuccess()) {
			return Result.success(pageInfo);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	@RequestMapping(value = " /repay_user_list/getDetail", method = RequestMethod.POST)
	@ResponseBody
	public Result getRepayUserDetail(String borrowId,String id) {
		try {
			List resultAll = new ArrayList();
			logger.info("????????????????????????????????????{}??????:" + JSONObject.toJSONString(borrowId), String.class);
			TradeCommonResult<BorrowResVo> result1 = borrowService.getBorrowById(borrowId);
			logger.info("????????????????????????????????????{}??????:" + JSONObject.toJSONString(borrowId), String.class);
			GuaranteeReqVo guaranteeReqVo = new GuaranteeReqVo();
			guaranteeReqVo.setBorrowId(borrowId);
			guaranteeReqVo.setMainFlag(1);
			TradeCommonResult<GuaranteeResVo> result2 = guaranteeService.get(guaranteeReqVo);
			GuaranteeReqVo guaranteeReqVo2 = new GuaranteeReqVo();
			guaranteeReqVo2.setBorrowId(borrowId);
			guaranteeReqVo2.setMainFlag(0);
			TradeCommonResult<GuaranteeResVo> result3 = guaranteeService.get(guaranteeReqVo2);
			OtherRepaySettingReqVo settingReqVo = new OtherRepaySettingReqVo();
			settingReqVo.setBorrowId(borrowId);
			settingReqVo.setId(id);
			TradeCommonResult<OtherRepaySettingResVo> settingResVo = otherRepaySettingService.get(settingReqVo);
			OtherRepaySettingResVo settingResVo2 = settingResVo.getBusinessObject();
			TradeCommonResult<BorrowResVo> borrowVo = borrowService.getBorrowById(borrowId);
			RepayUserVo repayUserVo = new RepayUserVo();
			repayUserVo.setOtherRepayUserName(settingResVo2.getOtherRepayUserName());
			repayUserVo.setCertId(settingResVo2.getCertId());
			repayUserVo.setEditedBy(settingResVo2.getEditedBy());
			repayUserVo.setCreateTime(settingResVo2.getCreateTime());
			repayUserVo.setMark(settingResVo2.getMark());
			repayUserVo.setEnterpriseName(borrowVo.getBusinessObject().getBorrowUserName());
			resultAll.add(result1.getBusinessObject());
			resultAll.add(result2.getBusinessObject());
			resultAll.add(result3.getBusinessObject());
			resultAll.add(repayUserVo);
			TradeCommonResult<BorrowRepayPlanResVo> reapyPlan;
			if(result1.getBusinessObject().getStatus().toString().equals("FINISH")) {
				BorrowRepayPlanResVo brp  = new BorrowRepayPlanResVo();
				brp.setCurrPhase(result1.getBusinessObject().getTotalPhase());
				brp.setTotalPhase(result1.getBusinessObject().getTotalPhase());
				resultAll.add(brp);
			}else {
				reapyPlan = borrowRepayPlanService.selectNextRepayPhase(borrowId);
				resultAll.add(reapyPlan.getBusinessObject());
			}
			if (result1.isSuccess()) {
				return Result.success(resultAll);
			} else {
				return Result.error("????????????");
			}
		} catch (Exception e) {
			logger.error("????????????????????????????????????", e);
			return Result.error("????????????????????????????????????");
		}
	}

	@RequestMapping(value = "/repay_user_list/delRepayUser", method = RequestMethod.POST)
	@ResponseBody
	public Result delRepayUser(String id, String mark) {
		try {
			logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(id), String.class);
			TradeCommonResult<OtherRepaySettingResVo> result = otherRepaySettingService.deleteWithMark(id, mark);
			logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(result), OtherRepaySettingResVo.class);
			if (result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			} else {
				return Result.error(result.getResultCodeMsg());
			}

		} catch (Exception e) {
			logger.error("????????????????????????", e);
		}
		return Result.error("????????????????????????");
	}

}

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
	 * 天津房贷角色
	 */
	private List<String> tjRoleList = Lists.newArrayList();
	/**
	 * 北京房贷角色
	 */
	private List<String> bjRoleList = Lists.newArrayList();

	/**
	 * 初始化
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
	 * 还款预警列表
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
				return Result.error("查询失败");
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
			logger.info("接口{}入参:" + JSONObject.toJSONString(repayWhiteListReqVo), IEnterpriseUserService.class);
			TradeCommonResult<PageInfo<EarlyRepayWhiteListResVo>> result = earlyRepayWhiteListService
					.repayWhiteList(repayWhiteListReqVo, pageNo, pageSize);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IEnterpriseUserService.class);
			if (result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			} else {
				return Result.error(result.getResultCodeMsg());
			}

		} catch (Exception e) {
			logger.error("取得提前还款报名单分页列表异常", e);
		}
		return Result.error("取得提前还款报名单分页列表异常");
	}

	@RequestMapping(value = "/whitelist/delWhitelist", method = RequestMethod.POST)
	@ResponseBody
	public Result delWhitelist(String id) {
		try {
			logger.info("接口{}入参:" + JSONObject.toJSONString(id), String.class);
			TradeCommonResult<Integer> result = earlyRepayWhiteListService.delete(id);
			logger.info("接口{}出参:" + JSONObject.toJSONString(id), String.class);
			if (result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			} else {
				return Result.error(result.getResultCodeMsg());
			}

		} catch (Exception e) {
			logger.error("删除提前还款报名单失败", e);
		}
		return Result.error("删除提前还款报名单失败");
	}

	@RequestMapping(value = "/repayOption/addOption", method = RequestMethod.POST)
	@ResponseBody
	public Result addOption(HttpServletRequest request,String changeType,EarlyRepayWhiteListReqVo earlyRepayWhiteListReqVo,OtherRepaySettingReqVo otherRepaySettingReqVo) {
		try {
			  Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
			if("1".equals(changeType)) {
				earlyRepayWhiteListReqVo.setEditedBy(staffSession.getStaffName());
				logger.info("接口{}入参:" + JSONObject.toJSONString(earlyRepayWhiteListReqVo), String.class);
				TradeCommonResult<EarlyRepayWhiteListResVo> re = earlyRepayWhiteListService.insert(earlyRepayWhiteListReqVo);
				logger.info("接口{}出参:" + JSONObject.toJSONString(re), String.class);
				if(re.isSuccess()) {
					return  Result.success("操作成功"); 
				}else {
					return  Result.error("操作失败"); 
				}
			}else if("2".equals(changeType)) {
				otherRepaySettingReqVo.setEditedBy(staffSession.getStaffName());
				logger.info("接口{}入参:" + JSONObject.toJSONString(otherRepaySettingReqVo), String.class);
				TradeCommonResult<OtherRepaySettingResVo> re2 = otherRepaySettingService.insert(otherRepaySettingReqVo);
				logger.info("接口{}出参:" + JSONObject.toJSONString(re2), String.class);
				if(re2.isSuccess()) {
					return  Result.success("操作成功"); 
				}else {
					return  Result.error("操作失败"); 
				}
			}else {
				return  Result.success("操作失败");
			}

		} catch (Exception e) {
			logger.error("操作失败", e);
			return Result.error("操作失败");
		}
	}
	
	@RequestMapping(value = " /{menu}/getDetail", method = RequestMethod.POST)
	@ResponseBody
	public Result getDetail(String borrowId) {
		try {
			List resultAll = new ArrayList();
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowId), String.class);
			TradeCommonResult<BorrowResVo> result1 = borrowService.getBorrowById(borrowId);
			logger.info("接口{}出参:" + JSONObject.toJSONString(borrowId), String.class);
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
				return Result.error("服务异常");
			}
		} catch (Exception e) {
			logger.error("删除提前还款报名单失败", e);
			return Result.error("删除提前还款报名单失败");
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
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			TradeCommonResult<PageInfo<BorrowResVo>> result = borrowService.repayList(borrowReqVo, pageNo, pageSize);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IBorrowService.class);
			if (result.isSuccess()) {
				PageInfo<BorrowResVo> page = result.getBusinessObject();
				List<BorrowVo> borrowVoList = BeanMapper.mapList(page.getList(), BorrowVo.class);
				for (BorrowVo borrowVo : borrowVoList) {
					// 计算借款时间
					if(null != borrowVo.getBorrowTerm() && null != borrowVo.getTermType()) {
						borrowVo.setBorrowDates(borrowVo.getBorrowTerm() + borrowVo.getTermType().getMessage());
					}else {
						String date = computeBorrowDates(borrowVo.getLastReleaseTime(), borrowVo.getLastRepayTime());
						borrowVo.setBorrowDates(date);
					}
					borrowVo.setEditedBy(borrowVo.getStatus().getName());
					// 查询借款下期还款期数
					logger.info("接口{}入参:" + JSONObject.toJSONString(borrowVo), IBorrowRepayPlanService.class);
					TradeCommonResult<BorrowRepayPlanResVo> reapyPlan = borrowRepayPlanService
							.selectNextRepayPhase(borrowVo.getId());
					logger.info("接口{}出参:" + JSONObject.toJSONString(reapyPlan), IBorrowRepayPlanService.class);
					BorrowRepayPlanResVo phase = reapyPlan.getBusinessObject();
					borrowVo.setNextPhase(Integer.toString(phase.getCurrPhase()));
					borrowVo.setTotalPhase(Integer.toString(phase.getTotalPhase()));
					// 查询下期还款信息
					BorrowRepayPlanReqVo borrowRepayPlanReqVo = new BorrowRepayPlanReqVo();
					borrowRepayPlanReqVo.setBorrowCode(borrowVo.getId());
					borrowRepayPlanReqVo.setPhase(phase.getCurrPhase());

					logger.info("接口{}入参:" + JSONObject.toJSONString(borrowRepayPlanReqVo),
							IBorrowRepayPlanService.class);
					TradeCommonResult<BorrowRepayPlanResVo> borrowPlanReault = borrowRepayPlanService
							.get(borrowRepayPlanReqVo);
					logger.info("接口{}出参:" + JSONObject.toJSONString(borrowPlanReault), IBorrowRepayPlanService.class);
					BorrowRepayPlanResVo borrowPlan = borrowPlanReault.getBusinessObject();
					// 下期应还= 本金 + 利息 + 罚息
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
			logger.error("还款列表查询出错", e);
		}

		return newPage;
	}
	/**   
	 * 添加借款
	 *
	 * @param borrowReqVo
	 * @return
	 */
	@RequestMapping(value = "/{menu}/addBorrow", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加借款")
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
			return Result.error("借款名称已经存在，请修改借款名称");
		}
		List<BorrowExtendReqVo> list = new ArrayList<BorrowExtendReqVo>();
		// 风控措施
		BorrowExtendReqVo borrowExtendReqVo = new BorrowExtendReqVo();
		borrowExtendReqVo.setExtendType(BorrowExtendType.RISK);
		borrowExtendReqVo.setMark(risk);
		borrowExtendReqVo.setEditedBy(staffName);
		list.add(borrowExtendReqVo);
		// 借款用途
		BorrowExtendReqVo borrowExtendReqVo2 = new BorrowExtendReqVo();
		borrowExtendReqVo2.setExtendType(BorrowExtendType.PURPOSE);
		borrowExtendReqVo2.setMark(purpose);
		borrowExtendReqVo2.setEditedBy(staffName);
		list.add(borrowExtendReqVo2);
		// 审批意见
		BorrowExtendReqVo borrowExtendReqVo3 = new BorrowExtendReqVo();
		borrowExtendReqVo3.setExtendType(BorrowExtendType.INIT);
		borrowExtendReqVo3.setMark(approvalOptions);
		borrowExtendReqVo3.setEditedBy(staffName);
		list.add(borrowExtendReqVo3);
		borrowReqVo.setBorrowExtendList(list);
		borrowReqVo.setStatus(BorrowStatus.APPROVAL1);
		logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
		borrowReqVo.setBorrowRate(borrowReqVo.getBorrowRate().multiply(new BigDecimal(100)));
		TradeCommonResult<BorrowResVo> result = borrowService.insertBorrow(borrowReqVo);
		logger.info("接口{}入出参:" + JSONObject.toJSONString(result), IBorrowService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject().getId());
		} else
			return Result.error("新增借款失败");

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
			originalName = file.getOriginalFilename();// 获得原文件名+扩展名
			Date date = new Date();
			String uuid = UUID.randomUUID().toString();
			String dateName = new SimpleDateFormat("/yyyy/MM/dd/").format(date);
			int index = 0;

			index = originalName.lastIndexOf(".");
			String extendName = originalName.substring(index);// 获取后缀名
			String filePath = LocalhostUrl + value + dateName + uuid + extendName;
			FileUDUtils.uploadFile(filePath, file);
			BorrowFileReqVo borrowFileReqVo = new BorrowFileReqVo();
			borrowFileReqVo.setBorrowCode(borrowId);
			borrowFileReqVo.setFileUrl(dateName + uuid + extendName);
			borrowFileReqVo.setTitle(file.getName());
			borrowFileReqVo.setMark("备注");
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			String role = staffSession.getStaffName();
			borrowFileReqVo.setEditedBy(role);
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowFileReqVo), IBorrowFileService.class);
			TradeCommonResult<BorrowFileResVo> result = borrowFileService.insertBorrowFile(borrowFileReqVo);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IBorrowFileService.class);
			if (result.isSuccess()) {
				return "success";
			}
		}
		return "fail";
	}

	/**
	 * 借款人列表查询
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
		// 判断角色是否为财务审核
		if (tjRoleList.indexOf(roleId) == bjRoleList.indexOf(roleId)) {
			res = null;
		}
		return res;
	}

	/**
	 * 初审列表 借款列表查询(初审，复审，拒绝)
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
			// 获取角色名称
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
			if ("refuse".equals(statusType)) {// 拒绝列表
				list.add(BorrowStatus.REFUSE1);
				list.add(BorrowStatus.REFUSE2);
			}
			if ("invalid".equals(statusType)) {// 历史资产-已废弃、已完成
				list.add(BorrowStatus.INVALID1);
				list.add(BorrowStatus.INVALID2);
				list.add(BorrowStatus.INVALID3);
				list.add(BorrowStatus.FINISH);
			}
			if ("repay".equals(statusType)) {// 还款列表
				list.add(BorrowStatus.LOCK);
				list.add(BorrowStatus.SPLIT);
			}
			if ("review".equals(statusType)) {// 审核借款列表
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
			if ("firstReview".equals(statusType)) {// 初审列表
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
			if ("reReview".equals(statusType)) {// 复审列表
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
			if ("financeReview".equals(statusType)) {// 财务审核 列表
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
			if ("finalReview".equals(statusType)) {// 终审列表
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
			// 查询全部
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
						// 计算借款时间
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
	 * 还款计划
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
		// return Result.error("时间设置错误");
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
	 * 更新手续费
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
	 * 删除借款文件
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
		logger.info("接口{}入参:" + JSONObject.toJSONString(borrowFileReqVo), IBorrowFileService.class);
		TradeCommonResult<Integer> result = borrowFileService.deleteBorrowFile(borrowFileReqVo);
		logger.info("接口{}出参:" + JSONObject.toJSONString(result), IBorrowFileService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * 保存修改
	 * 
	 * @param borrowReqVo
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/l_asset_list/saveBorrow", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "修改借款")
	public Result saveBorrow(BorrowReqVo borrowReqVo, HttpServletRequest httpRequest) {
		try {
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			String role = staffSession.getStaffName();
			borrowReqVo.setEditedBy(role);
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			if (null != borrowReqVo.getBorrowRate()) {
				borrowReqVo.setBorrowRate(borrowReqVo.getBorrowRate().multiply(new BigDecimal(100)));
			}
			TradeCommonResult<BorrowResVo> result = borrowService.updateBorrow(borrowReqVo);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IBorrowService.class);
			if (result.isSuccess()) {
				logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowRepayPlanService.class);
				TradeCommonResult<BorrowRepayPlanResVo> borrowRepayPlanResVo = borrowRepayPlanService
						.updateBorrowRepayPlan(borrowReqVo.getId());
				logger.info("接口{}出参:" + JSONObject.toJSONString(borrowRepayPlanResVo), IBorrowRepayPlanService.class);
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
	 * 撤销资产
	 * 
	 * @param borrowReqVo
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping(value = "/l_asset_list/cancelBorrow", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "撤销资产")
	public Result cancelBorrow(BorrowReqVo borrowReqVo, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		borrowReqVo.setEditedBy(role);
		borrowReqVo.setStatus(BorrowStatus.INVALID3);
		logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
		borrowReqVo.setBorrowRate(borrowReqVo.getBorrowRate().multiply(new BigDecimal(100)));
		TradeCommonResult<BorrowResVo> result = borrowService.updateBorrow(borrowReqVo);
		logger.info("接口{}出参:" + JSONObject.toJSONString(result), IBorrowService.class);
		if (result.isSuccess()) {
			return Result.success(result.getResultCodeMsg());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * 借款文件列表
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
	 * 还款申请单
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
	 * 申请单详情
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
	 * 还款状态
	 * 
	 * @param borrowRepayPlanReqVo
	 * @return
	 */
	@RequestMapping(value = "/l_RepayRecord/repayType", method = RequestMethod.POST)
	@ResponseBody
	public Result repayType(BorrowRepayPlanReqVo borrowRepayPlanReqVo) {
		TradeCommonResult<BorrowRepayPlanResVo> borrowPlanReault = borrowRepayPlanService.get(borrowRepayPlanReqVo);
		if (borrowPlanReault.isSuccess()) {
			if (formatDate(new Date()).before(formatDate(borrowPlanReault.getBusinessObject().getInterestEndDate()))) {// 提前
				return Result.success("ADVANCE");
			} else if (formatDate(new Date())
					.after(formatDate(borrowPlanReault.getBusinessObject().getInterestEndDate()))) {// 逾期
				return Result.success("OVERDUE");
			} else
				return Result.success("COMMON");
		} else
			return Result.error(borrowPlanReault.getResultCodeMsg());
	}

	/**
	 * 还款列表
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
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			TradeCommonResult<PageInfo<BorrowResVo>> result = borrowService.repayList(borrowReqVo, pageNo, pageSize);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IBorrowService.class);
			if (result.isSuccess()) {
				PageInfo<BorrowResVo> page = result.getBusinessObject();
				List<BorrowVo> borrowVoList = BeanMapper.mapList(page.getList(), BorrowVo.class);
				for (BorrowVo borrowVo : borrowVoList) {
					// 计算借款时间
					String date = computeBorrowDates(borrowVo.getLastReleaseTime(), borrowVo.getLastRepayTime());
					borrowVo.setBorrowDates(date);
					// 查询借款下期还款期数
					logger.info("接口{}入参:" + JSONObject.toJSONString(borrowVo), IBorrowRepayPlanService.class);
					TradeCommonResult<BorrowRepayPlanResVo> reapyPlan = borrowRepayPlanService
							.selectNextRepayPhase(borrowVo.getId());
					logger.info("接口{}出参:" + JSONObject.toJSONString(reapyPlan), IBorrowRepayPlanService.class);
					BorrowRepayPlanResVo phase = reapyPlan.getBusinessObject();
					borrowVo.setNextPhase(Integer.toString(phase.getCurrPhase()));
					borrowVo.setTotalPhase(Integer.toString(phase.getTotalPhase()));
					// 查询下期还款信息
					BorrowRepayPlanReqVo borrowRepayPlanReqVo = new BorrowRepayPlanReqVo();
					borrowRepayPlanReqVo.setBorrowCode(borrowVo.getId());
					borrowRepayPlanReqVo.setPhase(phase.getCurrPhase());

					logger.info("接口{}入参:" + JSONObject.toJSONString(borrowRepayPlanReqVo),
							IBorrowRepayPlanService.class);
					TradeCommonResult<BorrowRepayPlanResVo> borrowPlanReault = borrowRepayPlanService
							.get(borrowRepayPlanReqVo);
					logger.info("接口{}出参:" + JSONObject.toJSONString(borrowPlanReault), IBorrowRepayPlanService.class);
					BorrowRepayPlanResVo borrowPlan = borrowPlanReault.getBusinessObject();
					// 下期应还= 本金 + 利息 + 罚息
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
			logger.error("还款列表查询出错", e);
		}

		return newPage;
	}

	/**
	 * 借款详情
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
	 * 更新借款
	 *
	 * @param borrowReqVo
	 * @return
	 */
	@RequestMapping(value = "/{menu}/updateBorrow", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新借款")
	public String updateBorrow(BorrowReqVo borrowReqVo, BorrowExtends borrowExtends) {
		String approveResult = borrowExtends.getApproveResult();
		if ("n".equals(approveResult)) {// 废弃
			// 标示初审废弃还是复审废弃
			if (BorrowStatus.REFUSE1 == borrowReqVo.getStatus()) {
				borrowReqVo.setStatus(BorrowStatus.INVALID1);
			} else {
				borrowReqVo.setStatus(BorrowStatus.INVALID2);
			}
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			borrowReqVo.setBorrowRate(borrowReqVo.getBorrowRate().multiply(new BigDecimal(100)));
			TradeCommonResult<BorrowResVo> result = borrowService.updateBorrow(borrowReqVo);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result), IBorrowService.class);
			if (result.isSuccess()) {
				return "success";
			}
			return "fail";
		} else {
			borrowReqVo.setBorrowRate(borrowReqVo.getBorrowRate().multiply(new BigDecimal(100)));
			borrowReqVo.setStatus(BorrowStatus.APPROVAL1);
		}
		List<BorrowExtendReqVo> list = new ArrayList<BorrowExtendReqVo>();
		// 风控措施
		BorrowExtendReqVo borrowExtendReqVo = new BorrowExtendReqVo();
		borrowExtendReqVo.setMark(borrowExtends.getRisk());
		borrowExtendReqVo.setId(borrowExtends.getRiskId());
		list.add(borrowExtendReqVo);
		// 借款用途
		BorrowExtendReqVo borrowExtendReqVo2 = new BorrowExtendReqVo();
		borrowExtendReqVo2.setMark(borrowExtends.getPurpose());
		borrowExtendReqVo2.setId(borrowExtends.getPurposeId());
		list.add(borrowExtendReqVo2);
		// 审批意见
		BorrowExtendReqVo borrowExtendReqVo3 = new BorrowExtendReqVo();
		borrowExtendReqVo3.setExtendType(BorrowExtendType.EDIT);
		borrowExtendReqVo3.setMark(borrowExtends.getApproveContent());
		borrowExtendReqVo3.setEditedBy(borrowExtends.getApprover());
		borrowExtendReqVo3.setBorrowCode(borrowReqVo.getId());
		list.add(borrowExtendReqVo3);
		borrowReqVo.setBorrowExtendList(list);
		// 更新借款
		logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
		TradeCommonResult<BorrowResVo> result = borrowService.updateBorrowAndExtends(borrowReqVo);
		logger.info("接口{}出参:" + JSONObject.toJSONString(result), IBorrowService.class);
		if (result.isSuccess()) {
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowRepayPlanService.class);
			TradeCommonResult<BorrowRepayPlanResVo> borrowRepayPlanResVo = borrowRepayPlanService
					.updateBorrowRepayPlan(borrowReqVo.getId());
			logger.info("接口{}出参:" + JSONObject.toJSONString(borrowRepayPlanResVo), IBorrowRepayPlanService.class);
			if (borrowRepayPlanResVo.isSuccess())
				return "success";
			else
				return "fail";
		}
		return "fail";
	}

	/**
	 * 借款详情
	 *
	 * @param borrowId
	 * @return
	 */
	@RequestMapping(value = "/getBorrow", method = RequestMethod.POST)
	@ResponseBody
	public BorrowVo getBorrow(String borrowId) {
		TradeCommonResult<BorrowResVo> result = borrowService.getBorrowById(borrowId);
		// 查询
		BorrowExtendReqVo borrowExtendReqVo = new BorrowExtendReqVo();
		borrowExtendReqVo.setBorrowCode(borrowId);
		logger.info("接口{}入参:" + JSONObject.toJSONString(borrowExtendReqVo), IBorrowExtendService.class);
		TradeCommonResult<PageInfo<BorrowExtendResVo>> extendResult = borrowExtendService
				.listBorrowExtend(borrowExtendReqVo, 1, 1000);
		logger.info("接口{}出参:" + JSONObject.toJSONString(extendResult), IBorrowExtendService.class);
		BorrowVo borrowVo = BeanMapper.map(result.getBusinessObject(), BorrowVo.class);
		borrowVo.setBorrowExtendList(extendResult.getBusinessObject().getList());

		// 查询文件信息
		BorrowFileReqVo borrowFileReqVo = new BorrowFileReqVo();
		borrowFileReqVo.setBorrowCode(borrowId);
		logger.info("接口{}入参:" + JSONObject.toJSONString(borrowFileReqVo), IBorrowFileService.class);
		TradeCommonResult<PageInfo<BorrowFileResVo>> fileResult = borrowFileService.listBorrowFile(borrowFileReqVo, 1,
				1000);
		logger.info("接口{}出参:" + JSONObject.toJSONString(fileResult), IBorrowFileService.class);
		for (BorrowFileResVo borrowFileResVo : fileResult.getBusinessObject().getList()) {
			borrowFileResVo.setFileUrl(borrowFileResVo.getFileUrl());
		}
		borrowVo.setBorrowFileList(fileResult.getBusinessObject().getList());

		// 计算借款时间
		String date = computeBorrowDates(borrowVo.getLastReleaseTime(), borrowVo.getLastRepayTime());
		borrowVo.setBorrowDates(date);

		// 查询借款方名称
		EnterpriseUserReqVo enterpriseUserReqVo = new EnterpriseUserReqVo();
		enterpriseUserReqVo.setUserId(borrowVo.getBorrowUserCode());
		logger.info("接口{}入参:" + JSONObject.toJSONString(enterpriseUserReqVo), IEnterpriseUserService.class);
		UserCommonResult<EnterpriseUserResVo> result2 = enterpriseUserService.getEnterpriseUser(enterpriseUserReqVo);
		logger.info("接口{}出参:" + JSONObject.toJSONString(result2), IEnterpriseUserService.class);
		borrowVo.setBorrowUserName(result2.getBusinessObject().getLegalPersonName());
		if (borrowVo.getCollectedAmount() == null) {
			borrowVo.setRemainAmount(borrowVo.getBorrowAmount());
		} else {
			borrowVo.setRemainAmount(borrowVo.getBorrowAmount().subtract(borrowVo.getCollectedAmount()));
		}
		return borrowVo;
	}

	/**
	 * 审批借款
	 *
	 * @param borrowExtendReqVo
	 * @param approveResult
	 *            审批结果 y 通过 n 退会
	 * @param type
	 *            审批类型 first 初审 second 复审
	 * @return
	 */
	@RequestMapping(value = "/{menu}/approveBorrow", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "审批借款")
	public String approveBorrow(BorrowExtendReqVo borrowExtendReqVo, String approveResult, String type, String borrowId,
			HttpServletRequest httpRequest) {
		// 跟新借款状态
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
		// 添加审批记录
		if ("first".equals(type)) {
			borrowExtendReqVo.setExtendType(BorrowExtendType.FIRSTTRIAL);
		} else {
			borrowExtendReqVo.setExtendType(BorrowExtendType.REVIEW);
		}
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String staffName = staffSession.getStaffName();
		borrowExtendReqVo.setEditedBy(staffName);
		borrowExtendReqVo.setBorrowCode(borrowId);
		logger.info("接口{}入参:" + JSONObject.toJSONString(borrowExtendReqVo), IBorrowExtendService.class);
		TradeCommonResult<BorrowExtendResVo> result = borrowExtendService.insertBorrowExtend(borrowExtendReqVo);
		logger.info("接口{}出参:" + JSONObject.toJSONString(result), IBorrowExtendService.class);
		if (result.isSuccess()) {
			return "success";
		}
		return "fail";
	}

	/**
	 * 还款详情
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
			// 查询借款企业名称
			BaseReqVo baseReqVo = new BaseReqVo();
			baseReqVo.setUserId(borrow.getBorrowUserCode());
			DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> userResult = infoService
					.queryUserInfo(baseReqVo);
			com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo user = userResult.getBusinessObject();
			borrow.setBalance(user.getAvailableBalance());
			EnterpriseUserReqVo enterpriseUserReqVo = new EnterpriseUserReqVo();
			enterpriseUserReqVo.setUserId(borrow.getBorrowUserCode());
			logger.info("接口{}入参:" + JSONObject.toJSONString(enterpriseUserReqVo), IEnterpriseUserService.class);
			UserCommonResult<EnterpriseUserResVo> result2 = enterpriseUserService
					.getEnterpriseUser(enterpriseUserReqVo);
			logger.info("接口{}出参:" + JSONObject.toJSONString(result2), IEnterpriseUserService.class);
			borrow.setBorrowUserName(result2.getBusinessObject().getLegalPersonName());
			// 计算借款时间
			String date = computeBorrowDates(borrow.getLastReleaseTime(), borrow.getLastRepayTime());
			borrow.setBorrowDates(date);
			BorrowRepayPlanReqVo borrowRepayPlanReqVo = new BorrowRepayPlanReqVo();
			borrowRepayPlanReqVo.setBorrowCode(borrowId);
			logger.info("接口{}入参:" + JSONObject.toJSONString(borrowRepayPlanReqVo), IBorrowRepayPlanService.class);
			TradeCommonResult<PageInfo<BorrowRepayPlanResVo>> borrowRepayPlanResult = borrowRepayPlanService
					.list(borrowRepayPlanReqVo, 1, 1000);
			logger.info("接口{}出参:" + JSONObject.toJSONString(borrowRepayPlanResult), IBorrowRepayPlanService.class);
			borrow.setBorrowRepayPlans(borrowRepayPlanResult.getBusinessObject().getList());
			borrow.setNowDate(new Date());// 系统当前时间，用于"提前还款"、"还款"、"逾期还款"的按钮显示
		} catch (Exception e) {
			logger.error("还款详情查询出错", e);
		}
		// 判断是否支持提前还款
		return borrow;
	}

	/**
	 * 借款人详情
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
		// 查询借款企业名称
		EnterpriseUserReqVo enterpriseUserReqVo = new EnterpriseUserReqVo();
		enterpriseUserReqVo.setUserId(borrow.getBorrowUserCode());
		logger.info("接口{}入参:" + JSONObject.toJSONString(enterpriseUserReqVo), IEnterpriseUserService.class);
		UserCommonResult<EnterpriseUserResVo> result2 = enterpriseUserService.getEnterpriseUser(enterpriseUserReqVo);
		logger.info("接口{}出参:" + JSONObject.toJSONString(result2), IEnterpriseUserService.class);
		borrow.setBorrowUserName(result2.getBusinessObject().getLegalPersonName());
		// 计算借款时间
		// String date = computeBorrowDates(borrow.getLastReleaseTime(),
		// borrow.getLastRepayTime());
		// borrow.setBorrowDates(date);

		BorrowRepayPlanReqVo borrowRepayPlanReqVo = new BorrowRepayPlanReqVo();
		borrowRepayPlanReqVo.setBorrowCode(borrowId);
		logger.info("接口{}入参:" + JSONObject.toJSONString(borrowRepayPlanReqVo), IBorrowRepayPlanService.class);
		TradeCommonResult<PageInfo<BorrowRepayPlanResVo>> borrowRepayPlanResult = borrowRepayPlanService
				.list(borrowRepayPlanReqVo, 1, 1000);
		logger.info("接口{}出参:" + JSONObject.toJSONString(borrowRepayPlanResult), IBorrowRepayPlanService.class);
		borrow.setBorrowRepayPlans(borrowRepayPlanResult.getBusinessObject().getList());

		return borrow;
	}

	/**
	 * 还款
	 *
	 * @param borrowId
	 * @param phase
	 * @param repayType
	 * @return
	 */
	@RequestMapping(value = { "/repayList/repay", "/l_RepayRecord/repay" }, method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "还款")
	public TradeCommonResult<Integer> repay(String borrowId, int phase, String repayType, Date actualInterestEndDate) {
		logger.info(">>>>>>>>>>开始还款borrowId=" + borrowId + "期数phase=" + phase + "repayType=" + repayType);
		if (RepayType.ADVANCE.getCode().equals(repayType)) {
			// 判断借款是否可以提前还款
			logger.info("接口{}入参:{borrowId=" + borrowId + "}", IBorrowService.class);
			TradeCommonResult<BorrowResVo> borrowResult = borrowService.getBorrowById(borrowId);
			logger.info("接口{}出参:" + JSONObject.toJSONString(borrowResult), IBorrowService.class);
			BorrowResVo borrow = borrowResult.getBusinessObject();
			// 借款周期
			/*
			 * int borrowMonth =
			 * DateUtil.monthsInterestBetween(borrow.getLastReleaseTime(),
			 * borrow.getLastRepayTime()); // 小于三个月不能提前还款 if (borrowMonth < 3) {
			 * return new TradeCommonResult<Integer>("99999", "暂不能提前还款", 0); }
			 * // 三到六个月可提前一个月还款 if (3 <= borrowMonth && borrowMonth < 6) { //
			 * 计算提前还款月数 int month = DateUtil.monthsInterestBetween(new Date(),
			 * borrow.getLastRepayTime()); if (month > 1) { return new
			 * TradeCommonResult<Integer>("99999", "暂不能提前还款", 0); } } //
			 * 六个月或以上可提前两个月还款 if (borrowMonth >= 6) { // 计算提前还款月数 int month =
			 * DateUtil.monthsInterestBetween(new Date(),
			 * borrow.getLastRepayTime()); if (month > 2) { return new
			 * TradeCommonResult<Integer>("99999", "暂不能提前还款", 0); } }
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
		logger.info("接口{}入参:" + JSONObject.toJSONString(repayReqVo), ILoanOperService.class);
		TradeCommonResult<Integer> result = loanOperService.repay(repayReqVo);
		logger.info("接口{}出参:" + JSONObject.toJSONString(result), ILoanOperService.class);
		// logger.info(">>>>>>>>>>还款结果borrowId=" + borrowId + "期数phase=" + phase
		// + "repayType=" + repayType + "result=" +
		// JSONObject.toJSONString(result));
		return result;
	}

	/**
	 * 借款标的列表
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
	 * 标的还款详情
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
		logger.info("接口{}入参:{borrowId=" + borrowId + ",phase=" + phase + "}", IBorrowRepayPlanService.class);
		TradeCommonResult<BorrowRepayPlanResVo> borrowRepayPlanResult = borrowRepayPlanService
				.queryEarlyRepayPlan(borrowId, phase, actualInterestEndDate);
		logger.info("接口{}出参:" + JSONObject.toJSONString(borrowRepayPlanResult), IBorrowRepayPlanService.class);
		List<BorrowRepayPlanResVo> list = new ArrayList<BorrowRepayPlanResVo>();
		BorrowRepayPlanResVo vo = borrowRepayPlanResult.getBusinessObject();
		list.add(vo);
		borrow.setBorrowRepayPlans(list);
		// 计算借款剩余天数
		SimpleDateFormat ftimes = new SimpleDateFormat("yyyy-MM-dd");
		borrow.setRemainingDays(
				ftimes.format(borrowRepayPlanResult.getBusinessObject().getInterestEndDate()).toString());
		borrow.setInvestCycle(computeBorrowDates(borrow.getLastReleaseTime(), borrow.getLastRepayTime()));
		borrow.setNextPhase(Integer.toString(phase));
		return borrow;
	}

	/**
	 * 投资人还款详情
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
		logger.info("接口{}入参:" + JSONObject.toJSONString(condition), ILoanService.class);
		TradeCommonResult<LoanResVo> loanRes = loanService.getLoan(condition);
		logger.info("接口{}出参:" + JSONObject.toJSONString(loanRes), ILoanService.class);
		LoanResVo loanResVo = loanRes.getBusinessObject();
		LoanVo loanVo = BeanMapper.map(loanResVo, LoanVo.class);
		// 查询借款下标的还款计划
		InterestPlansReqVo interestPlansReqVo = new InterestPlansReqVo();
		interestPlansReqVo.setLoanCode(loanId);
		interestPlansReqVo.setPhase(phase);
		logger.info("接口{}入参:" + JSONObject.toJSONString(interestPlansReqVo), IInterestPlansService.class);
		TradeCommonResult<List<InterestPlansResVo>> interestPlans = interestPlansService
				.listInterestPlans(interestPlansReqVo);
		logger.info("接口{}出参:" + JSONObject.toJSONString(interestPlans), IInterestPlansService.class);
		List<InterestPlans> plans = new ArrayList<InterestPlans>();
		for (InterestPlansResVo interestPlansResVo : interestPlans.getBusinessObject()) {
			InterestPlans plan = BeanMapper.map(interestPlansResVo, InterestPlans.class);
			// 计算罚息
			plan.setPenalty(
					interestPlansResVo.getEarlyRepayment().addTo(interestPlansResVo.getPlatformEarlyRepayment()));
			logger.info("接口{}入参:" + JSONObject.toJSONString(interestPlansResVo), IUserInfoService.class);
			UserCommonResult<UserInfoResVo> userResult = userInfoService
					.getByRoleUserId(interestPlansResVo.getUserCode());
			logger.info("接口{}出参:" + JSONObject.toJSONString(userResult), IUserInfoService.class);
			UserInfoResVo user = userResult.getBusinessObject();
			plan.setUserName(user.getRealName());
			plan.setPhone(user.getRegisterMobile());
			logger.info("接口{}入参:" + JSONObject.toJSONString(interestPlansResVo), ILoanInvestorService.class);
			TradeCommonResult<LoanInvestorResVo> investResult = loanInvestorService
					.getById(interestPlansResVo.getInvestorCode());
			logger.info("接口{}出参:" + JSONObject.toJSONString(investResult), ILoanInvestorService.class);
			plan.setRedMoneyAmount(investResult.getBusinessObject().getRedMoneyAmount());
			plans.add(plan);
		}
		// 计算借款剩余天数
		loanVo.setRemainingDays(DateUtil.daysBetween(loanVo.getInterestEndDate(), loanVo.getInterestStartDate()) + "");
		loanVo.setInterestPlans(plans);
		return loanVo;
	}

	/**
	 * 导出Excel表格
	 *
	 * @param loanId
	 * @param phase
	 * @param response
	 */
	@RequestMapping(value = "/export")
	public void export(String loanId, int phase, HttpServletResponse response) {
		try {
			// 查询借款下标的还款计划
			InterestPlansReqVo interestPlansReqVo = new InterestPlansReqVo();
			interestPlansReqVo.setLoanCode(loanId);
			interestPlansReqVo.setPhase(phase);
			logger.info("接口{}入参:" + JSONObject.toJSONString(interestPlansReqVo), IInterestPlansService.class);
			TradeCommonResult<List<InterestPlansResVo>> interestPlans = interestPlansService
					.listInterestPlans(interestPlansReqVo);
			logger.info("接口{}出参:" + JSONObject.toJSONString(interestPlans), IInterestPlansService.class);
			List<InterestExcelPlans> plans = new ArrayList<InterestExcelPlans>();
			for (InterestPlansResVo interestPlansResVo : interestPlans.getBusinessObject()) {
				InterestExcelPlans plan = new InterestExcelPlans();
				// 计算罚息
				UserCommonResult<UserInfoResVo> userResult = userInfoService
						.getByRoleUserId(interestPlansResVo.getUserCode());
				UserInfoResVo user = userResult.getBusinessObject();
				logger.info("接口{}入参:" + JSONObject.toJSONString(interestPlansResVo), ILoanInvestorService.class);
				TradeCommonResult<LoanInvestorResVo> investResult = loanInvestorService
						.getById(interestPlansResVo.getInvestorCode());
				logger.info("接口{}出参:" + JSONObject.toJSONString(investResult), ILoanInvestorService.class);
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
					plan.setStatus("待还款");
				}
				if ("OVERDUE".equals(interestPlansResVo.getStatus())) {
					plan.setStatus("逾期中");
				}
				if ("FINISH".equals(interestPlansResVo.getStatus())) {
					plan.setStatus("已还款");
				}
				plans.add(plan);
			}
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String("settle.xls".getBytes("GBK"), "ISO-8859-1"));
			String[] headers;
			ExcelUtils<InterestExcelPlans> utils = new ExcelUtils<InterestExcelPlans>();
			headers = new String[] { "用户ID", "手机号", "姓名", "当前期数", "实付金额", "使用红包", "投资金额", "贴息", "还本", "还息", "合计",
					"状态" };
			utils.exportExcel("提前还款明细", headers, plans, response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
		} catch (Exception e) {
			logger.error("excel表格导出失败", e);
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
			return "1天";
		}
		// if (dateStr1.split("-")[2].equals(dateStr2.split("-")[2])) {
		// result = DateUtil.monthsInterestBetween(date1, date2);
		// return result + "月";
		// }
		result = DateUtil.daysInterestBetween(date2, date1);
		return result + "天";
	}

	private Date formatDate(Date date) {
		String dateStr = DateUtil.formatDate(date, "yyyy-MM-dd");
		Date returnDate = DateUtil.parseDate(dateStr, "yyyy-MM-dd");
		return returnDate;// 返回年
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
	 * 房贷借款人基本信息
	 */
	@RequestMapping(value = "/{menu}/audit", method = RequestMethod.POST)
	@ResponseBody
	public Result getbaseInfo1(String borrowId, HttpServletRequest servletRequest) {

		Staff staffSession = (Staff) servletRequest.getSession().getAttribute("adminInfo");
		String staffName = staffSession.getStaffName();
		try {
			List resultFinal = new ArrayList();
			// 根据ID查询企业基本信息
			logger.info("企业基本信息接口{}入参:" + JSONObject.toJSONString(borrowId), IBorrowerService.class);
			TradeCommonResult<BorrowResVo> result = borrowService.getBorrowById(borrowId);
			// 资金接收人回显
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

			// 根据借款人id查询印模信息
			SealImageRecordReqVo reqVo = new SealImageRecordReqVo();
			reqVo.setTransStatus(TransStatus.SUCCESS);
			reqVo.setUserId(result.getBusinessObject().getBorrowUserCode());
			ProtocolCommonResult<SealImageRecordResVo> borrowerSealImageResVo = sealImageRecord.get(reqVo);
			// 根据担保人id查询印模信息
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
			logger.info("企业基本信息接口{}出参:" + JSONObject.toJSONString(result), IBorrowerService.class);
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
				// 借款相关
				logger.info("借款接口{}入参:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
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
				logger.info("借款接口{}出参:" + JSONObject.toJSONString(result2), IBorrowService.class);

				// 担保相关
				logger.info("担保接口{}入参:" + JSONObject.toJSONString(grq), IGuaranteeService.class);
				TradeCommonResult<GuaranteeResVo> result3 = guaranteeService.get(grq);
				if (result3.isSuccess() && result3.getBusinessObject().getRelationOfEnterprise() != null) {
					result3.getBusinessObject().setRelationOfEnterprise(EntRelationType
							.valueOf(result3.getBusinessObject().getRelationOfEnterprise()).getMessage());
				}
				if (result3.isSuccess() && result3.getBusinessObject().getMarriage() != null) {
					result3.getBusinessObject()
							.setMarriage(MarriageType.valueOf(result3.getBusinessObject().getMarriage()).getMessage());
				}
				logger.info("担保接口{}出参:" + JSONObject.toJSONString(result3), IGuaranteeService.class);
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
				resultFinal.add(result4.getBusinessObject().getList());// 次级担保人信息
				resultFinal.add(map);
			} catch (Exception e) {
				logger.error("取得借款人信息异常", e);
			}

			return Result.success(resultFinal);
		} catch (Exception e) {
			logger.error("取得借款人信息异常", e);
		}
		return Result.error("取得借款人信息异常");
	}

	/**
	 * 审核 终审
	 */
	@RequestMapping(value = "/{menu}/finalReview", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "审核借款")
	public Result finalReview(BorrowReqVo borrow, String mark, String reviewCode, String editBy,
			String borrowExtendType, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		List<BorrowExtendReqVo> list = new ArrayList<BorrowExtendReqVo>();
		BorrowExtendReqVo borrowExtendReqVo = new BorrowExtendReqVo();
		borrowExtendReqVo.setBorrowCode(borrow.getId());
		borrowExtendReqVo.setEditedBy(editBy);// 操作者
		borrowExtendReqVo.setExtendType(BorrowExtendType.valueOf(borrowExtendType));// 审核阶段
		borrowExtendReqVo.setMark(mark);// 审核意见
		borrowExtendReqVo.setStatus(reviewCode);// 审核结果
		list.add(borrowExtendReqVo);
		borrow.setBorrowExtendList(list);
		borrow.setStatus(BorrowStatus.valueOf(reviewCode));
		TradeCommonResult<BorrowResVo> result = borrowService.approveBorrow(borrow);
		if (result.isSuccess()) {
			if (borrowExtendReqVo.getExtendType().equals(BorrowExtendType.FINAL_CHECK)) {
				return Result.success("审核成功");
			}
			if (borrow.getStatus().equals(BorrowStatus.PASS_RECHECK)
					|| borrow.getStatus().equals(BorrowStatus.PASS_FINANCE)) {
				// 发送邮件通知
				sendEmail(staffSession, borrow.getId(), true);
			} else if (borrow.getStatus().equals(BorrowStatus.BACK_RECHECK)
					|| borrow.getStatus().equals(BorrowStatus.BACK_FINANCE)) {
				// 发送邮件通知
				sendEmail(staffSession, borrow.getId(), false);
			}

			return Result.success("审核成功");
		} else {
			return Result.error("审核失败");
		}
	}

	/**
	 * 审核 商务经理
	 */
	@RequestMapping(value = "/{menu}/assetAudit", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "审核 商务经理")
	public String assetAudit(BorrowReqVo borrow, String approveResult, String auditRecommendations,
			String projectDetails, String borrowId, HttpServletRequest httpRequest,String zijiuer) {
		borrow.setReleaseUserId(zijiuer);
		if (borrow.getOaFlowCode() != null) {
			borrow.setOaFlowCode(borrow.getOaFlowCode().trim());
		}
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String staffName = staffSession.getStaffName();

		List<BorrowExtendReqVo> list = new ArrayList<BorrowExtendReqVo>();
		// 项目详情
		BorrowExtendReqVo borrowExtendReqVo1 = new BorrowExtendReqVo();
		borrowExtendReqVo1.setExtendType(BorrowExtendType.PROJECT);
		borrowExtendReqVo1.setMark(projectDetails);
		borrowExtendReqVo1.setBorrowCode(borrowId);
		// 审核信息
		BorrowExtendReqVo borrowExtendReqVo2 = new BorrowExtendReqVo();
		borrowExtendReqVo2.setBorrowCode(borrowId);
		borrowExtendReqVo2.setExtendType(BorrowExtendType.BUS_MANAGER);
		borrowExtendReqVo2.setMark(auditRecommendations);
		borrowExtendReqVo2.setStatus(approveResult);
		borrowExtendReqVo2.setEditedBy(staffName);

		list.add(borrowExtendReqVo1);
		list.add(borrowExtendReqVo2);
		// 利率万分位
		if (borrow.getBorrowRate() != null) {
			borrow.setBorrowRate(borrow.getBorrowRate().multiply(new BigDecimal(100)));
		}
		borrow.setBorrowExtendList(list);
		borrow.setId(borrowId);
		borrow.setStatus(BorrowStatus.valueOf(approveResult));
		logger.info("借款审核接口{}入参:" + JSONObject.toJSONString(borrow), IBorrowService.class);
		TradeCommonResult<BorrowResVo> result = borrowService.approveBorrow(borrow);
		logger.info("借款审核接口{}出参:" + JSONObject.toJSONString(result), IBorrowService.class);
		if (result.isSuccess()) {
			if (borrow.getStatus().equals(BorrowStatus.PASS_BUS_MANAGER)) {
				// 发送邮件通知
				sendEmail(staffSession, borrowId, true);
			}
			return "success";
		}
		return "fail";
	}

	/**
	 * 初审
	 */
	@RequestMapping(value = "/{menu}/firstAssetAudit", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "审核 初审")
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
		// 风控文件上传
		List<BorrowExtendReqVo> list = new ArrayList<BorrowExtendReqVo>();
		// 安全保障
		BorrowExtendReqVo borrowExtendReqVo1 = new BorrowExtendReqVo();
		borrowExtendReqVo1.setExtendType(BorrowExtendType.SECURITY);
		borrowExtendReqVo1.setMark(securityAssurance);
		borrowExtendReqVo1.setBorrowCode(borrowId);
		// 风控措施
		BorrowExtendReqVo borrowExtendReqVo3 = new BorrowExtendReqVo();
		borrowExtendReqVo3.setExtendType(BorrowExtendType.RISK);
		borrowExtendReqVo3.setMark(riskControl);
		borrowExtendReqVo3.setBorrowCode(borrowId);

		// 审核信息

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
		logger.info("借款初审接口{}入参:" + JSONObject.toJSONString(borrow), IBorrowService.class);
		TradeCommonResult<BorrowResVo> result = borrowService.approveBorrow(borrow);
		logger.info("借款初审接口{}出参:" + JSONObject.toJSONString(result), IBorrowService.class);
		if (result.isSuccess()) {
			if (BorrowStatus.PASS_FIRST_CHECK.equals(borrow.getStatus()) && !"yes".equals(isSave)) {
				// 发送邮件通知
				sendEmail(staffSession, borrowId, true);
			} else if (BorrowStatus.BACK_FIRST_CHECK.equals(borrow.getStatus()) && !"yes".equals(isSave)) {
				// 发送邮件通知
				sendEmail(staffSession, borrowId, false);
			}
			return "success";
		}
		return "fail";
	}

	/**
	 * 发送邮件
	 * 
	 * @param staff
	 */
	private void sendEmail(Staff staff, String borrowId, boolean next) {
		if (staff == null) {
			logger.error("员工信息为null, {}", staff);
			return;
		}
		String preRoleId = staff.getRoleId();
		if (Strings.isNullOrEmpty(preRoleId)) {
			logger.error("员工角色信息为 null, {}", staff);
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
			logger.debug("员工角色为空, {}", nextRoleId);
			return;
		}
		BorrowResVo borrowResVo = new BorrowResBo();
		try {
			TradeCommonResult<BorrowResVo> borrowResVoTradeCommonResult = borrowService.getBorrowById(borrowId);
			borrowResVo = borrowResVoTradeCommonResult.getBusinessObject();
		} catch (Exception e) {
			logger.error("发邮件时，调用借款申请的dubbo 接口出错了。borrowId: " + borrowId, e);
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
	 * 状态筛选
	 */
	public enum FilterStatus {
		/* 商务审核 */
		BUS_MANAGER_CHECK(
				Lists.newArrayList(BorrowStatus.SUBMIT, BorrowStatus.BACK_BUS_MANAGER, BorrowStatus.BACK_FIRST_CHECK)),
		/* 风控初审 */
		FIRST_CHECK(Lists.newArrayList(BorrowStatus.PASS_BUS_MANAGER, BorrowStatus.BACK_RECHECK)),
		/* 风控复审 */
		RECHECK(Lists.newArrayList(BorrowStatus.PASS_FIRST_CHECK, BorrowStatus.BACK_FINANCE)),
		/* 财务审核 */
		FINANCE_CHECK(Lists.newArrayList(BorrowStatus.PASS_RECHECK, BorrowStatus.BACK_FINAL_CHECK)),
		/* 募前终审 */
		FINAL_CHECK(Lists.newArrayList(BorrowStatus.PASS_FINANCE)),
		/* 审核成功 */
		PASS(Lists.newArrayList(BorrowStatus.PASS_FINAL_CHECK, BorrowStatus.LOCK, BorrowStatus.SPLIT)),
		/* 募集中 */
		OPEN(Lists.newArrayList(BorrowStatus.OPEN)),
		/* 还款中 */
		RELEASE(Lists.newArrayList(BorrowStatus.RELEASE)),
		/* 已完成 */
		FINISH(Lists.newArrayList(BorrowStatus.FINISH)),
		/* 已拒绝 */
		REFUSE(Lists.newArrayList(BorrowStatus.REFUSE_BUS_MANAGER, BorrowStatus.REFUSE_FIRST_CHECK,
				BorrowStatus.REFUSE_RECHECK, BorrowStatus.REFUSE_FINANCE, BorrowStatus.REFUSE_FINAL_CHECK)),
		/* 全部 */
		ALL(Lists.newArrayList(BorrowStatus.OPEN, BorrowStatus.RELEASE, BorrowStatus.KILLED, BorrowStatus.OVERDUE,
				BorrowStatus.LOCK));

		/**
		 * 借款状态
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
	 * 还款对象列表
	 */
	@RequestMapping(value = "/repay_user_list/repayUserList", method = RequestMethod.POST)
	@ResponseBody
	public Result repayUserList(OtherRepaySettingReqVo request, int pageNo, int pageSize,
			HttpServletRequest httpRequest) {

		Staff staff = (Staff) httpRequest.getSession().getAttribute(AdminUserController.USERNAME);
		if (staff != null) {
			// 获取角色名称
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

		logger.info("还款对象列表接口{}入参：" + JSONObject.toJSONString(request), IOtherRepaySettingService.class);
		TradeCommonResult<PageInfo<OtherRepaySettingResVo>> result = otherRepaySettingService.list(request, pageNo, pageSize);
		logger.info("还款对象列表接口{}出参：" + JSONObject.toJSONString(result), IOtherRepaySettingService.class);
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
			logger.info("获取还款设置页面信息接口{}入参:" + JSONObject.toJSONString(borrowId), String.class);
			TradeCommonResult<BorrowResVo> result1 = borrowService.getBorrowById(borrowId);
			logger.info("获取还款设置页面信息接口{}出参:" + JSONObject.toJSONString(borrowId), String.class);
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
				return Result.error("服务异常");
			}
		} catch (Exception e) {
			logger.error("获取还款设置页面信息失败", e);
			return Result.error("获取还款设置页面信息失败");
		}
	}

	@RequestMapping(value = "/repay_user_list/delRepayUser", method = RequestMethod.POST)
	@ResponseBody
	public Result delRepayUser(String id, String mark) {
		try {
			logger.info("删除还款对象接口{}入参:" + JSONObject.toJSONString(id), String.class);
			TradeCommonResult<OtherRepaySettingResVo> result = otherRepaySettingService.deleteWithMark(id, mark);
			logger.info("删除还款对象接口{}出参:" + JSONObject.toJSONString(result), OtherRepaySettingResVo.class);
			if (result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			} else {
				return Result.error(result.getResultCodeMsg());
			}

		} catch (Exception e) {
			logger.error("删除还款对象失败", e);
		}
		return Result.error("删除还款对象失败");
	}

}

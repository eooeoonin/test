package com.winsmoney.jajaying.boss.controller.project;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JLabel;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IProtocolService;
import com.winsmoney.jajaying.basedata.service.ISystemConfigService;
import com.winsmoney.jajaying.basedata.service.request.ProtocolReqVo;
import com.winsmoney.jajaying.basedata.service.request.SystemConfigReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.ProtocolResVo;
import com.winsmoney.jajaying.basedata.service.response.SystemConfigResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.controller.BaseController;
import com.winsmoney.jajaying.boss.controller.model.BorrowVo;
import com.winsmoney.jajaying.boss.controller.model.InterestExcelPlans;
import com.winsmoney.jajaying.boss.controller.model.InterestPlans;
import com.winsmoney.jajaying.boss.controller.model.InvestUserExcelPlans;
import com.winsmoney.jajaying.boss.controller.model.LoanVo;
import com.winsmoney.jajaying.boss.controller.utils.ExcelUtils;
import com.winsmoney.jajaying.boss.controller.utils.ImageSizer;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.PropertiesFactoryUtil;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.deposit.service.IInfoService;
import com.winsmoney.jajaying.deposit.service.request.BaseReqVo;
import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;
import com.winsmoney.jajaying.trade.service.IBorrowExtendService;
import com.winsmoney.jajaying.trade.service.IBorrowRepayPlanService;
import com.winsmoney.jajaying.trade.service.IBorrowService;
import com.winsmoney.jajaying.trade.service.IGuaranteeService;
import com.winsmoney.jajaying.trade.service.IInterestPlansService;
import com.winsmoney.jajaying.trade.service.IKillRecordDetailService;
import com.winsmoney.jajaying.trade.service.IKillRecordService;
import com.winsmoney.jajaying.trade.service.ILoanExtendService;
import com.winsmoney.jajaying.trade.service.ILoanFileService;
import com.winsmoney.jajaying.trade.service.ILoanInvestorService;
import com.winsmoney.jajaying.trade.service.ILoanOperService;
import com.winsmoney.jajaying.trade.service.ILoanRepayRecordService;
import com.winsmoney.jajaying.trade.service.ILoanService;
import com.winsmoney.jajaying.trade.service.IReleaseRecordService;
import com.winsmoney.jajaying.trade.service.enums.BorrowExtendType;
import com.winsmoney.jajaying.trade.service.enums.BorrowStatus;
import com.winsmoney.jajaying.trade.service.enums.InterestPlansStatus;
import com.winsmoney.jajaying.trade.service.enums.LoanExtendType;
import com.winsmoney.jajaying.trade.service.enums.LoanStatus;
import com.winsmoney.jajaying.trade.service.enums.LoanType;
import com.winsmoney.jajaying.trade.service.enums.TradeOrderStatus;
import com.winsmoney.jajaying.trade.service.request.BorrowExtendReqVo;
import com.winsmoney.jajaying.trade.service.request.BorrowReqVo;
import com.winsmoney.jajaying.trade.service.request.GuaranteeReqVo;
import com.winsmoney.jajaying.trade.service.request.InterestPlansReqVo;
import com.winsmoney.jajaying.trade.service.request.KillRecordDetailReqVo;
import com.winsmoney.jajaying.trade.service.request.KillRecordReqVo;
import com.winsmoney.jajaying.trade.service.request.LoanExtendReqVo;
import com.winsmoney.jajaying.trade.service.request.LoanFileReqVo;
import com.winsmoney.jajaying.trade.service.request.LoanInvestorReqVo;
import com.winsmoney.jajaying.trade.service.request.LoanRepayRecordReqVo;
import com.winsmoney.jajaying.trade.service.request.LoanReqVo;
import com.winsmoney.jajaying.trade.service.request.ReleaseRecordReqVo;
import com.winsmoney.jajaying.trade.service.response.BorrowExtendResVo;
import com.winsmoney.jajaying.trade.service.response.BorrowResVo;
import com.winsmoney.jajaying.trade.service.response.GuaranteeResVo;
import com.winsmoney.jajaying.trade.service.response.InterestPlansResVo;
import com.winsmoney.jajaying.trade.service.response.KillRecordDetailResVo;
import com.winsmoney.jajaying.trade.service.response.KillRecordResVo;
import com.winsmoney.jajaying.trade.service.response.LoanExtendResVo;
import com.winsmoney.jajaying.trade.service.response.LoanFileResVo;
import com.winsmoney.jajaying.trade.service.response.LoanInvestorResVo;
import com.winsmoney.jajaying.trade.service.response.LoanInvestorStatisticsResVo;
import com.winsmoney.jajaying.trade.service.response.LoanRepayRecordResVo;
import com.winsmoney.jajaying.trade.service.response.LoanResVo;
import com.winsmoney.jajaying.trade.service.response.LoanStatisticsResVo;
import com.winsmoney.jajaying.trade.service.response.ReleaseRecordResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IEnterpriseUserService;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.IUserService;
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
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * Created by wei on 2016/8/15.
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {
	private static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ILoanService loanService;
	@Autowired
	private IBorrowService borrowService;
	@Autowired
	private IBorrowExtendService borrowExtendService;
	@Autowired
	private IBorrowRepayPlanService borrowRepayPlanService;
	@Autowired
	private ILoanExtendService loanExtendService;
	@Autowired
	private ILoanFileService loanFileService;
	@Autowired
	private IProtocolService protocolService;
	@Autowired
	private ILoanOperService loanOperService;
	@Autowired
	private ILoanInvestorService loanInvestorService;
	@Autowired
	private IEnterpriseUserService enterpriseUserService;
	@Autowired
	private ILoanRepayRecordService loanRepayRecordService;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IInterestPlansService interestPlansService;
	@Autowired
	private IKillRecordService killRecordService;
	@Autowired
	private IKillRecordDetailService killRecordDetailService;
	@Autowired
	private IReleaseRecordService releaseRecordService;
	@Autowired
	private ISystemConfigService systemConfigService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IGuaranteeService guaranteeService;
	@Autowired
	private IInfoService infoService;
	/**
	 * ??????????????????(????????????????????????)
	 *
	 * @param borrowReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = { "/p_batch_pro/borrowList", "/p_add_pro/borrowList" }, method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<BorrowForm> borrowList(BorrowReqVo borrowReqVo, int pageNo, int pageSize) {
		try {
			if (StringUtils.isEmpty(borrowReqVo.getBorrowTitle())) {
				borrowReqVo.setBorrowTitle(null);
			}
			if (borrowReqVo.getBorrowType() != null && StringUtils.isEmpty(borrowReqVo.getBorrowType().getCode()))
				borrowReqVo.setBorrowType(null);
			if (StringUtils.isBlank(borrowReqVo.getBorrowUserName())) {
				borrowReqVo.setBorrowUserName(null);
			}
			List<BorrowStatus> bstatus = new ArrayList<>();
			bstatus.add(BorrowStatus.INUSE);
			bstatus.add(BorrowStatus.SPLIT);
			bstatus.add(BorrowStatus.PASS_FINAL_CHECK);
			borrowReqVo.setStatusIn(bstatus);
			borrowReqVo.setIsFullFlag("true");
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			TradeCommonResult<PageInfo<BorrowResVo>> result = borrowService.listBorrow(borrowReqVo, pageNo, pageSize);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowService.class);
			PageInfo<BorrowForm> newPage = new PageInfo<BorrowForm>();
			if (result.isSuccess()) {
				PageInfo<BorrowResVo> page = result.getBusinessObject();
				List<BorrowForm> borrowVoList = BeanMapper.mapList(page.getList(), BorrowForm.class);
				for (BorrowForm borrowVo : borrowVoList) {
					if(null != borrowVo.getReleaseUserId()) {
						UserCommonResult<UserInfoResVo> uirv = userInfoService.getByRoleUserId(borrowVo.getReleaseUserId());
						if(uirv.getBusinessObject().getUserType() != null && "PERSON".equals(uirv.getBusinessObject().getUserType().getCode())) {
							borrowVo.setReleaseUserCard(uirv.getBusinessObject().getRealName());
						}else {
							EnterpriseUserReqVo enterpriseUserReqVo  = new EnterpriseUserReqVo();
							enterpriseUserReqVo.setUserId(borrowVo.getReleaseUserId());
							UserCommonResult<EnterpriseUserResVo> eurv = enterpriseUserService.getEnterpriseUser(enterpriseUserReqVo);
							borrowVo.setReleaseUserCard(eurv.getBusinessObject().getLegalPersonName());
						}
					}else {
						borrowVo.setReleaseUserCard(null);
					}
					
					GuaranteeReqVo grq = new GuaranteeReqVo();
					grq.setBorrowId(borrowVo.getId());
					TradeCommonResult<GuaranteeResVo> result3 = guaranteeService.get(grq);
					borrowVo.setGuaranteeUserName(result3.getBusinessObject().getGuaranteeUserName());
					borrowVo.setGuaranteeUserCard(result3.getBusinessObject().getGuaranteeIdCard());
					/*EnterpriseUserReqVo enterpriseUserReqVo = new EnterpriseUserReqVo();
					enterpriseUserReqVo.setUserId(borrowVo.getBorrowUserCode());
					logger.info("??????{}??????:" + JSONObject.toJSONString(enterpriseUserReqVo), IEnterpriseUserService.class);
					UserCommonResult<EnterpriseUserResVo> result2 = enterpriseUserService
							.getEnterpriseUser(enterpriseUserReqVo);
					logger.info("??????{}??????:" + JSONObject.toJSONString(result2), IEnterpriseUserService.class);
					borrowVo.setBorrowUserName(result2.getBusinessObject().getEnterpriseName());*/
					/*
					 * BorrowExtendReqVo borrowExtendReqVo = new BorrowExtendReqVo();
					 * borrowExtendReqVo.setBorrowCode(borrowVo.getId());
					 * borrowExtendReqVo.setExtendType(BorrowExtendType.REVIEW);
					 * logger.info("??????{}??????:" + JSONObject.toJSONString(borrowExtendReqVo),
					 * IBorrowExtendService.class); TradeCommonResult<BorrowExtendResVo>
					 * borrowExtend = borrowExtendService .getBorrowExtend(borrowExtendReqVo);
					 * logger.info("??????{}??????:" + JSONObject.toJSONString(borrowExtend),
					 * IBorrowExtendService.class);
					 * borrowVo.setReviewDate(borrowExtend.getBusinessObject().getCreateTime());//
					 * ??????????????????
					 */

					BorrowExtendReqVo extendReqVo = new BorrowExtendReqVo();
					extendReqVo.setBorrowCode(borrowVo.getId());
					logger.info("??????{}??????:" + JSONObject.toJSONString(extendReqVo), IBorrowExtendService.class);
					TradeCommonResult<List<BorrowExtendResVo>> extendResult = borrowExtendService
							.listBorrowExtend(extendReqVo);
					logger.info("??????{}??????:" + JSONObject.toJSONString(extendResult), IBorrowExtendService.class);
					for (BorrowExtendResVo b : extendResult.getBusinessObject()) {
						if (BorrowExtendType.RISK.equals(b.getExtendType())) {
							borrowVo.setRisktMessage(b.getMark());
						}else
						if (BorrowExtendType.SECURITY.equals(b.getExtendType())) {
							borrowVo.setSaveMessage(b.getMark());
						}else
						if (BorrowExtendType.PROJECT.equals(b.getExtendType())) {
							borrowVo.setProjectMessage(b.getMark());
						}
					}
					borrowVo.setBorrowExtendList(extendResult.getBusinessObject());
					borrowVo.setBalance(borrowVo.getBorrowAmount().subtract(borrowVo.getBorrowedAmount()));
					// borrowVo.setBorrowRate( borrowVo.getBorrowRate().divide( new
					// BigDecimal( 100 )) );
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
	 * ??????OR???????????????
	 * 
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/p_add_pro/borrowUserList", method = RequestMethod.POST)
	@ResponseBody
	public Result borrowUserList(BorrowUserInfoReqVo request, int pageNo, int pageSize) {
		try {
			if (StringUtils.isBlank(request.getEnterpriseName())) {
				request.setEnterpriseName(null);
			}
			logger.info("??????{}??????:" + JSONObject.toJSONString(request), IUserService.class);
			UserCommonResult<PageInfo<BorrowUserInfoResVo>> result = userService.listBorrowUser(request, pageNo,
					pageSize);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result), IUserService.class);
			if (result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("??????????????????");
		}
	}

	@RequestMapping(value = "/show/showLoanBorrow/{loanCode}", method = RequestMethod.POST)
	@ResponseBody
	public Result showLoanBorrow(@PathVariable @NotBlank String loanCode) {
		LoanReqVo loanReqVo = new LoanReqVo();
		loanReqVo.setId(loanCode);
		TradeCommonResult<LoanResVo> result = loanService.getLoan(loanReqVo);
		if (result.isSuccess()) {
			TradeCommonResult<BorrowResVo> borrowResult = borrowService
					.getBorrowById(result.getBusinessObject().getBorrowerCode());
			if (borrowResult.isFailure()) {
				return Result.error(borrowResult.getResultCodeMsg());
			}
			BorrowResVo borrowResVo = borrowResult.getBusinessObject();
			borrowResVo.setBorrowRate(borrowResVo.getBorrowRate());
			return Result.success(BeanMapper.map(borrowResVo, BorrowForm.class));
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ??????????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/show/getInvestUserList/{loanCode}", method = RequestMethod.POST)
	@ResponseBody
	public Result getInvestUserList(@PathVariable @NotBlank String loanCode) {
		LoanInvestUserForm loanInvestUserForm = new LoanInvestUserForm();
		LoanInvestorReqVo loanInvestorReqVo = new LoanInvestorReqVo();
		loanInvestorReqVo.setLoanCode(loanCode);
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanInvestorReqVo), ILoanInvestorService.class);
		TradeCommonResult<LoanInvestorStatisticsResVo> loanInvestorStatistics = loanInvestorService
				.loanInvestorStatistics(loanCode);
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanInvestorStatistics), ILoanInvestorService.class);
		if (loanInvestorStatistics.isSuccess()) {
			loanInvestUserForm.setBiddingAmount(loanInvestorStatistics.getBusinessObject().getBiddingAmount());
			loanInvestUserForm.setUseCashAmount(loanInvestorStatistics.getBusinessObject().getUseCashAmount());
			loanInvestUserForm.setCashAmount(loanInvestorStatistics.getBusinessObject().getCashAmount());
			loanInvestUserForm.setInvestCount(loanInvestorStatistics.getBusinessObject().getInvestCount());
			loanInvestUserForm.setLoanAmount(loanInvestorStatistics.getBusinessObject().getLoanAmount());
			loanInvestUserForm.setRedmoneyAmount(loanInvestorStatistics.getBusinessObject().getRedmoneyAmount());
			logger.info("??????{}??????:" + JSONObject.toJSONString(loanInvestorReqVo), ILoanInvestorService.class);
			TradeCommonResult<List<LoanInvestorResVo>> result = loanInvestorService.listLoanInvestor(loanInvestorReqVo);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result), ILoanInvestorService.class);
			if (result.isSuccess()) {
				List<InvestUserForm> list = BeanMapper.mapList(result.getBusinessObject(), InvestUserForm.class);
				List<InvestUserForm> investUserFormList = new ArrayList<InvestUserForm>();
				for (InvestUserForm investUserForm : list) {
					if (TradeOrderStatus.SUCCESS.getCode().equals(investUserForm.getInvestorStatus())) {
						logger.info("??????{}??????:" + JSONObject.toJSONString(investUserForm), IUserInfoService.class);
						UserCommonResult<UserInfoResVo> resVoUserCommonResult = userInfoService
								.getByRoleUserId(investUserForm.getInvestorUserCode());
						logger.info("??????{}??????:" + JSONObject.toJSONString(resVoUserCommonResult), IUserInfoService.class);
						if (resVoUserCommonResult.isSuccess()) {
							investUserForm.setInvestorUserCode(resVoUserCommonResult.getBusinessObject().getId());
							investUserForm.setUserName(SensitiveInfoUtils
									.chineseName(resVoUserCommonResult.getBusinessObject().getRealName()));
							investUserForm.setUserPhone(SensitiveInfoUtils
									.mobilePhone(resVoUserCommonResult.getBusinessObject().getRegisterMobile()));
							investUserForm.setUserProperties(resVoUserCommonResult.getBusinessObject().getGroup());
						}
						if (null != investUserForm.getAddInterestVal()) {
							investUserForm
									.setAddInterestVal(investUserForm.getAddInterestVal().divide(new BigDecimal(100)));
						} else {
							investUserForm.setAddInterestVal(BigDecimal.ZERO);
						}
						investUserFormList.add(investUserForm);
					}
				}
				loanInvestUserForm.setInvestUserForms(investUserFormList);

				return Result.success(loanInvestUserForm);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} else {
			return Result.error(loanInvestorStatistics.getResultCodeMsg());
		}
	}

	/**
	 * ??????????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/{menu}/getLoanFileList/{loanCode}", method = RequestMethod.POST)
	@ResponseBody
	public Result getLoanFileList(@PathVariable @NotBlank String loanCode) {
		LoanFileReqVo loanFileReqVo = new LoanFileReqVo();
		loanFileReqVo.setLoanCode(loanCode);
		TradeCommonResult<List<LoanFileResVo>> result = loanFileService.listLoanFile(loanFileReqVo);
		if (result.isSuccess()) {
			return Result.success(BeanMapper.mapList(result.getBusinessObject(), LoanFileResVo.class));
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ????????????
	 *
	 * @param loanReqVo
	 */
	@RequestMapping(value = "/p_add_pro/add", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "????????????")
	public Result createLoan(LoanReqVo loanReqVo, HttpServletRequest httpRequest) {

		if (loanReqVo.getRedMoneyScale() == null) {
			loanReqVo.setRedMoneyScale("0");
		}
		if("??????".equals(loanReqVo.getReleaseUserId()) || null == loanReqVo.getReleaseUserId()) {
			loanReqVo.setReleaseUserId(null);
		}
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		loanReqVo.setRecommendOrder("1");// ????????????
		loanReqVo.setEditedBy(role);
		loanReqVo.setStatus(LoanStatus.INIT);
		List<LoanExtendReqVo> loanExtendReqVoList1 = new ArrayList<>();
		List<LoanExtendReqVo> loanExtendReqVoList = loanReqVo.getLoanExtendList();
		for (LoanExtendReqVo loanExtendReqVo : loanExtendReqVoList) {
			loanExtendReqVo.setEditedBy(role);
			loanExtendReqVoList1.add(loanExtendReqVo);
		}
		loanReqVo.setLoanExtendList(loanExtendReqVoList1);
		BigDecimal ss = new BigDecimal(0);
		if (loanReqVo.getLoanType().getCode() != "ADDINTEREST") {
			loanReqVo.setFloatRate(ss);
		}
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanReqVo), ILoanService.class);
		loanReqVo.setYearRate(loanReqVo.getYearRate().multiply(new BigDecimal(100)));
		loanReqVo.setFloatRate(loanReqVo.getFloatRate().multiply(new BigDecimal(100)));
		TradeCommonResult<LoanResVo> tradeCommonResult = loanService.insertLoan(loanReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(tradeCommonResult), ILoanService.class);
		if (tradeCommonResult.isSuccess()) {
			LoanResVo loanResVo = tradeCommonResult.getBusinessObject();
			loanResVo.setYearRate(loanResVo.getYearRate().divide(new BigDecimal(100)));
			loanResVo.setFloatRate(loanResVo.getFloatRate().divide(new BigDecimal(100)));
			return Result.success(tradeCommonResult.getBusinessObject());
		} else {
			return Result.error(tradeCommonResult.getResultCodeMsg());
		}
	}

	/**
	 * ??????????????????
	 *
	 * @param projectBatchFroms
	 * @return
	 */
	@RequestMapping(value = "/p_batch_pro/add", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "??????????????????")
	public Result createBatchLoan(ProjectBatchFrom projectBatchFroms, HttpServletRequest httpRequest) {

		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		for (LoanExtendReqVo loanExtendReqVo : projectBatchFroms.getLoanExtendList()) {
			loanExtendReqVo.setEditedBy(role);
		}
		List<LoanReqVo> list = new ArrayList<>();
		List<BatchProject> batchProjectList = projectBatchFroms.getList();
		if (null == batchProjectList || batchProjectList.size() < 0) {
			return Result.error("???????????????????????????");
		}
		for (BatchProject batchProject : projectBatchFroms.getList()) {
			LoanReqVo loanReqVo = BeanMapper.map(batchProject, LoanReqVo.class);

			if (loanReqVo.getRedMoneyScale() == null) {
				loanReqVo.setRedMoneyScale("0");
			}
			if (loanReqVo.getFloatRate() == null) {
				loanReqVo.setFloatRate(BigDecimal.ZERO);
			}
			loanReqVo.setRecommendOrder("1");// ????????????
			loanReqVo.setEditedBy(role);
			loanReqVo.setStatus(LoanStatus.INIT);
			loanReqVo.setBorrowerCode(projectBatchFroms.getBorrowerCode());
			loanReqVo.setContractNo(projectBatchFroms.getContractNo());
			loanReqVo.setRepayType(projectBatchFroms.getRepayType());
			loanReqVo.setBeginInvest(projectBatchFroms.getBeginInvest());
			loanReqVo.setStepInvest(projectBatchFroms.getStepInvest());
			loanReqVo.setMaxInvest(projectBatchFroms.getMaxInvest());
			loanReqVo.setUseRedPacket(projectBatchFroms.getUseRedPacket());
			loanReqVo.setAddInterestCardFlag(projectBatchFroms.getAddInterestCardFlag());
			loanReqVo.setCashCardFlag(projectBatchFroms.getCashCardFlag());
			loanReqVo.setLoanExtendList(projectBatchFroms.getLoanExtendList());
			loanReqVo.setRedMoneyScale(projectBatchFroms.getRedMoneyScale());
			loanReqVo.setRepayDesc(projectBatchFroms.getRepayDesc());
			loanReqVo.setAutoInvestMaxAmount(projectBatchFroms.getAutoInvestMaxAmount());
			list.add(loanReqVo);
		}
		logger.info("??????{}??????:" + JSONObject.toJSONString(list), ILoanService.class);
		TradeCommonResult<List<LoanResVo>> result = loanService.insertLoanList(list);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), ILoanService.class);
		if (result.isSuccess())
			return Result.success(result.getResultCodeMsg());
		else
			return Result.error(result.getResultCodeMsg());
	}

	/**
	 * ???????????????
	 */
	@RequestMapping(value = "/p_wait_list/list", method = { RequestMethod.POST })
	@ResponseBody
	public Result getInitLoan(LoanReqVo loanReqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(loanReqVo.getId())) {
			loanReqVo.setId(null);
		}
		if (StringUtils.isBlank(loanReqVo.getTitle())) {
			loanReqVo.setTitle(null);
		}
		loanReqVo.setStatus(LoanStatus.INIT);
		TradeCommonResult<PageInfo<LoanResVo>> result = loanService.listLoan(loanReqVo, pageNo, pageSize);
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
	 * @param loanReqVo
	 */
	@RequestMapping(value = "/{menu}/saveLoan", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "??????????????????")
	public Result saveLoan(LoanReqVo loanReqVo, HttpServletRequest httpRequest) {
		if (loanReqVo.getRedMoneyScale() == null) {
			loanReqVo.setRedMoneyScale("0");
		}
		if (loanReqVo.getUseRedPacket() == null) {
			loanReqVo.setUseRedPacket(false);
		}
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		loanReqVo.setRecommendOrder("1");// ????????????
		loanReqVo.setEditedBy(role);
		List<LoanExtendReqVo> loanExtendReqVoList = loanReqVo.getLoanExtendList();
		for (LoanExtendReqVo loanExtendReqVo : loanExtendReqVoList) {
			loanExtendReqVo.setEditedBy(role);
			loanExtendReqVo.setLoanCode(loanReqVo.getId());
			TradeCommonResult<LoanExtendResVo> result1;
			logger.info("??????{}??????:" + JSONObject.toJSONString(loanExtendReqVo), ILoanExtendService.class);
			if (StringUtils.isNotBlank(loanExtendReqVo.getId())) {
				result1 = loanExtendService.updateLoanExtend(loanExtendReqVo);
			} else {
				result1 = loanExtendService.insertLoanExtend(loanExtendReqVo);
			}
			logger.info("??????{}??????:" + JSONObject.toJSONString(result1), ILoanExtendService.class);
			if (!result1.isSuccess()) {
				return Result.error(result1.getResultCodeMsg());
			}
		}
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanReqVo), ILoanService.class);

		if (null != loanReqVo.getYearRate()) {
			loanReqVo.setYearRate(loanReqVo.getYearRate().multiply(new BigDecimal(100)));
		}
		if (null != loanReqVo.getFloatRate()) {
			loanReqVo.setFloatRate(loanReqVo.getFloatRate().multiply(new BigDecimal(100)));
		}
		TradeCommonResult<LoanResVo> result = loanService.updateLoan(loanReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), ILoanService.class);
		if (result.isSuccess()) {
			return Result.success(result.getResultCodeMsg());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ????????????
	 *
	 * @param loanCode
	 * @return
	 */
	@RequestMapping(value = "/{menu}/openLoan/{loanCode}", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "????????????")
	public Result openLoan(@PathVariable @NotBlank String loanCode, LoanReqVo loanReqVo,
			HttpServletRequest httpRequest) {
		logger.info(">>>>>>>>>>????????????loanCode=" + loanCode + "????????????loanReqVo=" + JSONObject.toJSONString(loanReqVo));
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		loanReqVo.setId(loanCode);
		loanReqVo.setRecommendOrder("1");// ????????????
		loanReqVo.setEditedBy(role);
		loanReqVo.setStatus(LoanStatus.OPEN);// ????????????
		List<LoanExtendReqVo> loanExtendReqVoList = loanReqVo.getLoanExtendList();
		for (LoanExtendReqVo loanExtendReqVo : loanExtendReqVoList) {
			loanExtendReqVo.setEditedBy(role);
			loanExtendReqVo.setLoanCode(loanReqVo.getId());
			TradeCommonResult<LoanExtendResVo> result1;
			if (StringUtils.isNotBlank(loanExtendReqVo.getId())) {
				result1 = loanExtendService.updateLoanExtend(loanExtendReqVo);
			} else {
				result1 = loanExtendService.insertLoanExtend(loanExtendReqVo);
			}
			if (!result1.isSuccess()) {
				return Result.error(result1.getResultCodeMsg());
			}
		}
		if (null != loanReqVo.getFloatRate()) {
			loanReqVo.setFloatRate(loanReqVo.getFloatRate().multiply(new BigDecimal(100)));
		}
		if (null != loanReqVo.getYearRate()) {
			loanReqVo.setYearRate(loanReqVo.getYearRate().multiply(new BigDecimal(100)));
		}
		TradeCommonResult<LoanResVo> result = loanService.openLoan(loanReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), ILoanService.class);
		if (result.isSuccess()) {
			return Result.success(result.getResultCodeMsg());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ????????????
	 *
	 * @param loanReqVo
	 * @return
	 */
	@RequestMapping(value = "/{menu}/killLoan", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "????????????")
	public Result killLoan(LoanReqVo loanReqVo, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		List<LoanExtendReqVo> loanExtendReqVoList = loanReqVo.getLoanExtendList();
		for (LoanExtendReqVo loanExtendReqVo : loanExtendReqVoList) {
			loanExtendReqVo.setEditedBy(role);
			loanExtendReqVo.setLoanCode(loanReqVo.getId());
			TradeCommonResult<LoanExtendResVo> result1;
			logger.info("??????{}??????:" + JSONObject.toJSONString(loanExtendReqVo), ILoanExtendService.class);
			if (StringUtils.isNotBlank(loanExtendReqVo.getId())) {
				result1 = loanExtendService.updateLoanExtend(loanExtendReqVo);
			} else {
				result1 = loanExtendService.insertLoanExtend(loanExtendReqVo);
			}
			logger.info("??????{}??????:" + JSONObject.toJSONString(result1), ILoanExtendService.class);
			if (!result1.isSuccess()) {
				return Result.error(result1.getResultCodeMsg());
			}
		}
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanReqVo), ILoanOperService.class);
		TradeCommonResult<Integer> killLoanresult = loanOperService.killLoan(loanReqVo.getId());
		logger.info("??????{}??????:" + JSONObject.toJSONString(killLoanresult), ILoanOperService.class);
		if (killLoanresult.isSuccess())
			return Result.success(killLoanresult.getResultCodeMsg());
		else
			return Result.error(killLoanresult.getResultCodeMsg());
	}

	/**
	 * ???????????????
	 *
	 * @param loanReqVo
	 * @return
	 */
	@RequestMapping(value = "/{menu}/killRecordLoan", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "???????????????")
	public Result killRecordLoan(LoanReqVo loanReqVo) {
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanReqVo), ILoanOperService.class);
		TradeCommonResult<Integer> killLoanresult = loanOperService.killLoan(loanReqVo.getId());
		logger.info("??????{}??????:" + JSONObject.toJSONString(killLoanresult), ILoanOperService.class);
		if (killLoanresult.isSuccess())
			return Result.success(killLoanresult.getResultCodeMsg());
		else
			return Result.error(killLoanresult.getResultCodeMsg());
	}

	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/p_start_list/list", method = { RequestMethod.POST })
	@ResponseBody
	public Result getOpenLoan(LoanReqVo loanReqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(loanReqVo.getId())) {
			loanReqVo.setId(null);
		}
		if (StringUtils.isBlank(loanReqVo.getTitle())) {
			loanReqVo.setTitle(null);
		}
		loanReqVo.setStatus(LoanStatus.OPEN);
		TradeCommonResult<PageInfo<LoanResVo>> result = loanService.queryLoanListByStatus(loanReqVo, pageNo, pageSize);
		if (result.isSuccess()) {
			PageInfo pageInfo = result.getBusinessObject();
			pageInfo.setList(BeanMapper.mapList(pageInfo.getList(), LoanResForm.class));
			return Result.success(pageInfo);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ???????????????
	 */
	@RequestMapping(value = "/p_raise_list/list", method = { RequestMethod.POST })
	@ResponseBody
	public Result getOpenedLoan(LoanReqVo loanReqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(loanReqVo.getId())) {
			loanReqVo.setId(null);
		}
		if (StringUtils.isBlank(loanReqVo.getTitle())) {
			loanReqVo.setTitle(null);
		}
		loanReqVo.setStatus(LoanStatus.OPENED);
		TradeCommonResult<PageInfo<LoanResVo>> result = loanService.queryLoanListByStatus(loanReqVo, pageNo, pageSize);
		if (result.isSuccess()) {
			PageInfo pageInfo = result.getBusinessObject();
			pageInfo.setList(BeanMapper.mapList(pageInfo.getList(), LoanResForm.class));
			return Result.success(pageInfo);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ?????????????????????
	 */
	@RequestMapping(value = "/p_raise_list/getKillLoanStatus/{loanCode}", method = { RequestMethod.POST })
	@ResponseBody
	public Result getKillLoanStatus(@PathVariable @NotBlank String loanCode) {
		KillRecordReqVo killRecordReqVo = new KillRecordReqVo();
		killRecordReqVo.setLoanId(loanCode);
		TradeCommonResult<KillRecordResVo> killRecordResVo = killRecordService.get(killRecordReqVo);
		if (killRecordResVo.isSuccess()) {
			return Result.success(killRecordResVo.getBusinessObject());
		} else {
			return Result.error(killRecordResVo.getResultCodeMsg());
		}

	}

	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/p_raise_list/getKillLoanDetails/{loanCode}", method = { RequestMethod.POST })
	@ResponseBody
	public Result getKillLoanDetails(@PathVariable @NotBlank String loanCode, int pageNo, int pageSize) {
		KillRecordDetailReqVo killRecordDetailReqVo = new KillRecordDetailReqVo();
		killRecordDetailReqVo.setLoanId(loanCode);
		TradeCommonResult<PageInfo<KillRecordDetailResVo>> KillRecordDetailResVo = killRecordDetailService
				.list(killRecordDetailReqVo, pageNo, pageSize);
		if (KillRecordDetailResVo.isSuccess()) {
			return Result.success(KillRecordDetailResVo.getBusinessObject());
		} else {
			return Result.error(KillRecordDetailResVo.getResultCodeMsg());
		}
	}

	/**
	 * ???????????????
	 */
	@RequestMapping(value = "/p_killrecord/getKillRecord", method = { RequestMethod.POST })
	@ResponseBody
	public Result getKillRecord(KillRecordReqVo killRecordReqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(killRecordReqVo.getLoanId())) {
			killRecordReqVo.setLoanId(null);
		}
		if (StringUtils.isBlank(killRecordReqVo.getLoanName())) {
			killRecordReqVo.setLoanName(null);
		}
		TradeCommonResult<PageInfo<KillRecordResVo>> killRecordResVo = killRecordService.list(killRecordReqVo, pageNo,
				pageSize);
		if (killRecordResVo.isSuccess()) {
			return Result.success(killRecordResVo.getBusinessObject());
		} else
			return Result.error(killRecordResVo.getResultCodeMsg());

	}

	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/p_killrecord/getKillRecordDetail", method = { RequestMethod.POST })
	@ResponseBody
	public Result getKillLoanDetails(KillRecordDetailReqVo killRecordDetailReqVo, int pageNo, int pageSize) {
		TradeCommonResult<PageInfo<KillRecordDetailResVo>> KillRecordDetailResVo = killRecordDetailService
				.list(killRecordDetailReqVo, pageNo, pageSize);
		if (KillRecordDetailResVo.isSuccess())
			return Result.success(KillRecordDetailResVo.getBusinessObject());
		else
			return Result.error(KillRecordDetailResVo.getResultCodeMsg());
	}

	/**
	 * ???????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/p_releaserecord/getReleaseRecord", method = RequestMethod.POST)
	@ResponseBody
	public Result getReleaseRecord(ReleaseRecordReqVo releaseRecordReqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(releaseRecordReqVo.getLoanId())) {
			releaseRecordReqVo.setLoanId(null);
		}
		if (StringUtils.isBlank(releaseRecordReqVo.getLoanName())) {
			releaseRecordReqVo.setLoanName(null);
		}
		TradeCommonResult<PageInfo<ReleaseRecordResVo>> result = releaseRecordService.list(releaseRecordReqVo, pageNo,
				pageSize);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/p_raise_list/stopLoan", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "????????????")
	public Result stopLoan(String loanCode) {
		TradeCommonResult<Integer> result = loanService.stopLoan(loanCode);
		if (result.isSuccess())
			return Result.success(result.getResultCodeMsg());
		else
			return Result.error(result.getResultCodeMsg());
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/p_waitlenders_list/list", method = { RequestMethod.POST })
	@ResponseBody
	public Result getFullLoan(LoanReqVo loanReqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(loanReqVo.getId())) {
			loanReqVo.setId(null);
		}
		if (StringUtils.isBlank(loanReqVo.getTitle())) {
			loanReqVo.setTitle(null);
		}
		List<LoanStatus> list = new ArrayList<LoanStatus>();
		list.add(LoanStatus.FULL);
		list.add(LoanStatus.STOP);
		loanReqVo.setStatusIn(list);
		TradeCommonResult<PageInfo<LoanResVo>> result = loanService.listLoan(loanReqVo, pageNo, pageSize);
		if (result.isSuccess()) {
			PageInfo pageInfo = result.getBusinessObject();
			pageInfo.setList(BeanMapper.mapList(pageInfo.getList(), LoanResForm.class));
			return Result.success(pageInfo);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ????????????
	 */
	@RequestMapping(value = "/{menu}/releaseLoan", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "????????????")
	public Result releaseLoan(LoanReqVo loanReqVo, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		List<LoanExtendReqVo> loanExtendReqVoList = loanReqVo.getLoanExtendList();
		for (LoanExtendReqVo loanExtendReqVo : loanExtendReqVoList) {
			loanExtendReqVo.setEditedBy(role);
			loanExtendReqVo.setLoanCode(loanReqVo.getId());
			TradeCommonResult<LoanExtendResVo> result1;
			logger.info("??????{}??????:" + JSONObject.toJSONString(loanExtendReqVo), ILoanExtendService.class);
			if (StringUtils.isNotBlank(loanExtendReqVo.getId())) {
				result1 = loanExtendService.updateLoanExtend(loanExtendReqVo);
			} else {
				result1 = loanExtendService.insertLoanExtend(loanExtendReqVo);
			}
			logger.info("??????{}??????:" + JSONObject.toJSONString(result1), ILoanExtendService.class);
			if (!result1.isSuccess()) {
				return Result.error(result1.getResultCodeMsg());
			}
		}
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanReqVo), ILoanOperService.class);
		TradeCommonResult<Integer> result = loanOperService.releaseloan(loanReqVo.getId());
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), ILoanOperService.class);
		if (result.isSuccess()) {
			return Result.success(result.getResultCodeMsg());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ???????????????
	 */
	@RequestMapping(value = "/p_releaserecord/releaseRecordLoan", method = { RequestMethod.POST })
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "???????????????")
	public Result releaseRecordLoan(String loanId, HttpServletRequest httpRequest) {
		logger.info("??????{}??????:{loanId=" + loanId + "}", ILoanOperService.class);
		TradeCommonResult<Integer> result = loanOperService.releaseloan(loanId);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), ILoanOperService.class);
		if (result.isSuccess()) {
			return Result.success(result.getResultCodeMsg());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ????????? ?????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/p_repayment_list/list", method = { RequestMethod.POST })
	@ResponseBody
	public Result getDisburseLoan(LoanReqVo loanReqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(loanReqVo.getId())) {
			loanReqVo.setId(null);
		}
		if (StringUtils.isBlank(loanReqVo.getTitle())) {
			loanReqVo.setTitle(null);
		}
		loanReqVo.setStatus(LoanStatus.DISBURSE);
		TradeCommonResult<PageInfo<LoanResVo>> result = loanService.listLoan(loanReqVo, pageNo, pageSize);
		if (result.isSuccess()) {
			PageInfo pageInfo = result.getBusinessObject();
			pageInfo.setList(BeanMapper.mapList(pageInfo.getList(), LoanResForm.class));
			return Result.success(pageInfo);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ?????????
	 */
	@RequestMapping(value = "/p_completed_list/list", method = { RequestMethod.POST })
	@ResponseBody
	public Result getCompletedLoan(LoanReqVo loanReqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(loanReqVo.getId())) {
			loanReqVo.setId(null);
		}
		if (StringUtils.isBlank(loanReqVo.getTitle())) {
			loanReqVo.setTitle(null);
		}
		loanReqVo.setStatus(LoanStatus.COMPLETED);
		TradeCommonResult<PageInfo<LoanResVo>> result = loanService.listLoanEnd(loanReqVo, pageNo, pageSize);
		if (result.isSuccess()) {
			PageInfo pageInfo = result.getBusinessObject();
			pageInfo.setList(BeanMapper.mapList(pageInfo.getList(), LoanResForm.class));
			return Result.success(pageInfo);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	@RequestMapping(value = "/show/getRepaymentInfo/{loanCode}", method = { RequestMethod.POST })
	@ResponseBody
	public Result getRepaymentInfo(@PathVariable @NotBlank String loanCode) {
		RepaymentForm repaymentForm = new RepaymentForm();
		LoanReqVo loanReqVo = new LoanReqVo();
		loanReqVo.setId(loanCode);
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanReqVo), ILoanService.class);
		TradeCommonResult<LoanResVo> loanResult = loanService.getLoan(loanReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanResult), ILoanService.class);
		if (loanResult.isSuccess()) { // ??????????????????
			LoanResVo loanResVo = loanResult.getBusinessObject();
			repaymentForm.setLoanRate(loanResVo.getYearRate().divide(new BigDecimal(100)));
			repaymentForm.setLoanTitle(loanResVo.getTitle());
			logger.info("??????{}??????:" + JSONObject.toJSONString(loanResVo), IBorrowService.class);
			TradeCommonResult<BorrowResVo> borrowResult = borrowService.getBorrowById(loanResVo.getBorrowerCode()); // ??????????????????
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowResult), IBorrowService.class);
			if (borrowResult.isSuccess()) {
				repaymentForm.setBorrowTile(borrowResult.getBusinessObject().getBorrowTitle());
				repaymentForm
						.setBorrowRate(borrowResult.getBusinessObject().getBorrowRate().divide(new BigDecimal(100)));
				EnterpriseUserReqVo enterpriseUserReqVo = new EnterpriseUserReqVo();
				enterpriseUserReqVo.setUserId(borrowResult.getBusinessObject().getBorrowUserCode());
				logger.info("??????{}??????:" + JSONObject.toJSONString(enterpriseUserReqVo), IEnterpriseUserService.class);
				UserCommonResult<EnterpriseUserResVo> enterpriseResult = enterpriseUserService
						.getEnterpriseUser(enterpriseUserReqVo);// ????????????????????????
				enterpriseResult.getBusinessObject().setUserId(borrowResult.getBusinessObject().getBorrowUserCode());
				logger.info("??????{}??????:" + JSONObject.toJSONString(enterpriseResult), IEnterpriseUserService.class);
				if (enterpriseResult.isSuccess()) {
					EnterpriseUserResVo enterpriseUserResVo = enterpriseResult.getBusinessObject();
					UserAccountInfoResVo userAccountInfoReqVo = new UserAccountInfoResVo();
					userAccountInfoReqVo.setUserId(enterpriseUserResVo.getUserId());
					  	BaseReqVo baseReqVo=new BaseReqVo();
				        baseReqVo.setUserId(enterpriseUserResVo.getUserId());
				        DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> result1 = infoService.queryUserInfo(baseReqVo);
				        if (result1.isSuccess()) {
						repaymentForm.setEnterpriseUserName(enterpriseUserResVo.getEnterpriseName());
						if (null == result1.getBusinessObject().getBalance()) {
							return Result.error("????????????");
						} else {
							repaymentForm.setEnterpriseUserBalance(result1.getBusinessObject().getBalance()
									.subtract(result1.getBusinessObject().getFreezeAmount()));
							InterestPlansReqVo interestPlansReqVo = new InterestPlansReqVo();
							interestPlansReqVo.setLoanCode(loanCode);
							logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansReqVo),
									IInterestPlansService.class);
							TradeCommonResult<List<InterestPlansResVo>> result = interestPlansService
									.listLoanInterestPlans(interestPlansReqVo);// ??????????????????
							logger.info("??????{}??????:" + JSONObject.toJSONString(result), IInterestPlansService.class);
							if (result.isSuccess())
								repaymentForm.setInterestPlansResVos(result.getBusinessObject());
							return Result.success(repaymentForm);
						}
					} else {
						return Result.error(result1.getResultCodeMsg());
					}
				} else {
					return Result.error(enterpriseResult.getResultCodeMsg());
				}
			} else {
				return Result.error(borrowResult.getResultCodeMsg());
			}
		} else {
			return Result.error(loanResult.getResultCodeMsg());
		}
	}

	/**
	 * ??????????????????
	 * @param loanCode
	 * @return
	 */
	@RequestMapping(value = "/show/repayList/loanDetail/{loanCode}", method = RequestMethod.POST)
	@ResponseBody
	public BorrowVo loanDetail(@PathVariable @NotBlank String loanCode) {
		try {
			LoanReqVo loanReqVo = new LoanReqVo();
			loanReqVo.setId(loanCode);
			logger.info("??????{}??????:" + JSONObject.toJSONString(loanReqVo), ILoanService.class);
			TradeCommonResult<LoanResVo> loanResult = loanService.getLoan(loanReqVo);
			logger.info("??????{}??????:" + JSONObject.toJSONString(loanResult), ILoanService.class);
			BorrowVo borrow = new BorrowVo();
			if (loanResult.isSuccess()) {
				String borrowId = loanResult.getBusinessObject().getBorrowerCode();
				logger.info("??????{}??????:{borrowId=" + borrowId + "}", IBorrowService.class);
				TradeCommonResult<BorrowResVo> result = borrowService.getBorrowById(borrowId);
				logger.info("??????{}??????:" + JSONObject.toJSONString(result), IBorrowService.class);
				borrow = BeanMapper.map(result.getBusinessObject(), BorrowVo.class);
				InterestPlansReqVo interestPlansReqVo = new InterestPlansReqVo();
				interestPlansReqVo.setBorrowCode(borrowId);
				interestPlansReqVo.setLoanCode(loanCode);
				LoanRepayRecordReqVo loanRepayRecordReqVo = new LoanRepayRecordReqVo();
				// loanRepayRecordReqVo.setBorrowId(borrowId);
				loanRepayRecordReqVo.setLoanId(loanCode);
				// loanRepayRecordReqVo.setPlatformProfit(null);
				// interestPlansReqVo.setStatus(InterestPlansStatus.REPAY_COMMEN);//???sql????????????
				// ?????????????????????????????????
				logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansReqVo), IInterestPlansService.class);
				// TradeCommonResult<List<InterestPlansResVo>> interestPlans =
				// interestPlansService.listLoanInterestPlans(interestPlansReqVo);
				TradeCommonResult<PageInfo<LoanRepayRecordResVo>> interestPlans = loanRepayRecordService
						.list(loanRepayRecordReqVo, 1, 1000);
				logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlans.getBusinessObject().getList()),
						IInterestPlansService.class);
				// List<InterestPlans> plans = new ArrayList<InterestPlans>();
				// for (InterestPlansResVo interestPlansResVo :
				// interestPlans.getBusinessObject()) {
				// InterestPlans plan = BeanMapper.map(interestPlansResVo,
				// InterestPlans.class);
				// LoanReqVo loan = new LoanReqVo();
				// loan.setId(interestPlansResVo.getLoanCode());
				// logger.info("??????{}??????:" + JSONObject.toJSONString(loan),
				// ILoanService.class);
				// TradeCommonResult<LoanResVo> loanRes =
				// loanService.getLoan(loan);
				// logger.info("??????{}??????:" + JSONObject.toJSONString(loanRes),
				// ILoanService.class);
				// plan.setModifyTime(loanRes.getBusinessObject().getModifyTime());
				// plan.setLoanName(loanRes.getBusinessObject().getTitle());
				// // ????????????
				// plan.setPenalty(interestPlansResVo.getOverdueInterest().addTo(interestPlansResVo.getEarlyRepayment()));
				// // ????????????
				// plan.setLoanRate(loanRes.getBusinessObject().getYearRate());
				// plans.add(plan);
				// borrow.setPhase(interestPlansResVo.getBasePhase());
				// }
				// ????????????????????????
				borrow.setRemainingDays(
						DateUtil.daysBetween(borrow.getLastRepayTime(), borrow.getLastReleaseTime()) + "");
				borrow.setLoanRepayRecordResVo(interestPlans.getBusinessObject().getList());
				// borrow.setInterestPlans(plans);
			}
			return borrow;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * ?????????????????????
	 * @param loanCode
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/show/repayList/userDetail/{loanCode}", method = RequestMethod.POST)
	@ResponseBody
	public Object userDetail(@PathVariable @NotBlank String loanCode, String pageNo, String pageSize) {
		LoanReqVo condition = new LoanReqVo();
		condition.setId(loanCode);
		TradeCommonResult<LoanResVo> loanRes = loanService.getLoan(condition);
		LoanResVo loanResVo = loanRes.getBusinessObject();
		LoanVo loanVo = BeanMapper.map(loanResVo, LoanVo.class);
		PageInfo pageInfo = null;
		// ?????????????????????????????????
		InterestPlansReqVo interestPlansReqVo = new InterestPlansReqVo();
		interestPlansReqVo.setLoanCode(loanCode);
		List<InterestPlansStatus> statusIn = new ArrayList<InterestPlansStatus>();
		statusIn.add(InterestPlansStatus.REPAY_COMMEN);
		statusIn.add(InterestPlansStatus.REPAY_EARLY);
		statusIn.add(InterestPlansStatus.REPAY_OVERDUE);
		interestPlansReqVo.setStatusIn(statusIn);
		logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansReqVo), IInterestPlansService.class);
		TradeCommonResult<PageInfo<InterestPlansResVo>> interestPlans = interestPlansService
				.listInterestPlans(interestPlansReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlans), IInterestPlansService.class);
		pageInfo = interestPlans.getBusinessObject();
		List<InterestPlans> plans = new ArrayList<InterestPlans>();
		for (InterestPlansResVo interestPlansResVo : interestPlans.getBusinessObject().getList()) {
			InterestPlans plan = BeanMapper.map(interestPlansResVo, InterestPlans.class);
			// ??????????????????????????????????????????????????????
			plan.setPenalty(interestPlansResVo.getEarlyRepayment().addTo(interestPlansResVo.getOverdueInterest()));
			logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansResVo), IUserInfoService.class);
			UserCommonResult<UserInfoResVo> userResult = userInfoService.getByRoleUserId(interestPlansResVo.getUserCode());
			logger.info("??????{}??????:" + JSONObject.toJSONString(userResult), IInterestPlansService.class);
			UserInfoResVo user = userResult.getBusinessObject();
			plan.setUserName(SensitiveInfoUtils.chineseName(user.getRealName()));
			plan.setPhone(SensitiveInfoUtils.mobilePhone(user.getRegisterMobile()));
			logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansResVo), ILoanInvestorService.class);
			TradeCommonResult<LoanInvestorResVo> investResult = loanInvestorService
					.getById(interestPlansResVo.getInvestorCode());
			logger.info("??????{}??????:" + JSONObject.toJSONString(investResult), ILoanInvestorService.class);
			plan.setRedMoneyAmount(investResult.getBusinessObject().getRedMoneyAmount());
			plan.setAmount(investResult.getBusinessObject().getInvestAmount());// ???????????????????????????

			plans.add(plan);
		}
		// ????????????????????????
		loanVo.setRemainingDays(
				DateUtil.daysBetween(loanVo.getInterestEndDate(), loanVo.getInterestStartDate()) + 1 + "");
		pageInfo.setList(plans);
		loanVo.setInterestPlansPages(pageInfo);
		loanVo.setYearRate(loanVo.getYearRate().divide(new BigDecimal(100)));
		return JSONObject.toJSON(loanVo);
	}

	/**
	 * ???????????????
	 */
	@RequestMapping(value = "/p_overdue_list/list", method = { RequestMethod.POST })
	@ResponseBody
	public Result getOverDueLoan(int pageNo, int pageSize) {
		TradeCommonResult<PageInfo<LoanResVo>> result = loanService.listoverDueLoan(pageNo, pageSize);
		if (result.isSuccess()) {
			PageInfo pageInfo = result.getBusinessObject();
			pageInfo.setList(BeanMapper.mapList(pageInfo.getList(), LoanResForm.class));
			return Result.success(pageInfo);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ???????????? ??????
	 *
	 * @return
	 */
	@RequestMapping(value = "/p_history_list/list")
	@ResponseBody
	public Result getAllHistoryLoan(LoanReqVo loanReqVo, int pageNo, int pageSize) {
		List<LoanStatus> list = new ArrayList<LoanStatus>();
		list.add(LoanStatus.COMPLETED);
		list.add(LoanStatus.KILLED);
		loanReqVo.setStatusIn(list);
		if (loanReqVo.getYearRate() != null) {
			loanReqVo.setYearRate(loanReqVo.getYearRate().multiply(new BigDecimal(100)));
		} else {
			loanReqVo.setYearRate(null);
		}
		if(loanReqVo.getTitle() == null || "".equals(loanReqVo.getTitle())) {
			loanReqVo.setTitle(null);
		}
		if (loanReqVo.getLoanType() != null)
			if (StringUtils.isBlank(loanReqVo.getLoanType().getCode()))
				loanReqVo.setLoanType(null);
		TradeCommonResult<PageInfo<LoanResVo>> result = loanService.listLoan(loanReqVo, pageNo, pageSize);
		if (result.isSuccess()) {
			PageInfo pageInfo = result.getBusinessObject();
			List<LoanResForm> loanResFormList = new ArrayList<LoanResForm>();
			for (LoanResVo loanResVo : result.getBusinessObject().getList()) {
				LoanResForm loanResForm = BeanMapper.map(loanResVo, LoanResForm.class);
				TradeCommonResult<LoanInvestorStatisticsResVo> loanInvestorStatistics = loanInvestorService
						.loanInvestorStatistics(loanResVo.getId());
				loanResForm.setRedMoneyAmount(loanInvestorStatistics.getBusinessObject().getRedmoneyAmount());// ??????????????????
				if (loanInvestorStatistics.getBusinessObject().getTotalProceeds() != null
						&& loanInvestorStatistics.getBusinessObject().getCollectProceeds() != null)
					loanResForm.setProfit(loanInvestorStatistics.getBusinessObject().getTotalProceeds()
							.addTo(loanInvestorStatistics.getBusinessObject().getCollectProceeds()));
				if (loanInvestorStatistics.getBusinessObject().getTotalProceeds() != null
						&& loanInvestorStatistics.getBusinessObject().getCollectProceeds() == null)
					loanResForm.setProfit(loanInvestorStatistics.getBusinessObject().getTotalProceeds());
				if (loanInvestorStatistics.getBusinessObject().getTotalProceeds() == null
						&& loanInvestorStatistics.getBusinessObject().getCollectProceeds() != null)
					loanResForm.setProfit(loanInvestorStatistics.getBusinessObject().getCollectProceeds());
				loanResFormList.add(loanResForm);
			}
			pageInfo.setList(loanResFormList);
			return Result.success(pageInfo);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/p_pro_revenue/show", method = { RequestMethod.POST })
	@ResponseBody
	public Result getAllLoanStatistics() {
		TradeCommonResult<LoanStatisticsResVo> result = loanService.getLoanStatistics();
		if (result.isSuccess())
			return Result.success(result.getBusinessObject());
		else
			return Result.error(result.getResultCodeMsg());
	}

	/**
	 * ??????????????????
	 *
	 * @param loanCode
	 * @return
	 */
	@RequestMapping(value = "/{menu}/showLoan/{loanCode}", method = { RequestMethod.POST })
	@ResponseBody
	public Result showLoan(@PathVariable @NotBlank String loanCode, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		LoanReqVo loanReqVo = new LoanReqVo();
		loanReqVo.setId(loanCode);
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanReqVo), ILoanService.class);
		TradeCommonResult<LoanResVo> loanResult = loanService.getLoan(loanReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanResult), ILoanService.class);
		if (loanResult.isSuccess()) {
			LoanResVo loanResVo = loanResult.getBusinessObject();
			LoanResForm loanResForm = BeanMapper.map(loanResVo, LoanResForm.class);
			loanResForm.setOperUser(role);
			logger.info("??????{}??????:" + JSONObject.toJSONString(loanResVo), IProtocolService.class);
			BasedataCommonResult<ProtocolResVo> protocol = protocolService.getById(loanResVo.getContractNo());
			logger.info("??????{}??????:" + JSONObject.toJSONString(protocol), IProtocolService.class);
			if (protocol.isFailure()) {
				return Result.error(protocol.getResultCodeMsg());
			}
			loanResForm.setContractName(protocol.getBusinessObject().getName());
			BorrowReqVo borrowReqVo = new BorrowReqVo();
			borrowReqVo.setId(loanResVo.getBorrowerCode());
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowReqVo), IBorrowService.class);
			TradeCommonResult<BorrowResVo> borrowResult = borrowService.getBorrow(borrowReqVo);
			logger.info("??????{}??????:" + JSONObject.toJSONString(borrowResult), IBorrowService.class);
			if (borrowResult.isSuccess()) {
				BorrowResVo borrowResVo = borrowResult.getBusinessObject();
				loanResForm.setBorrowerCode(borrowResVo.getId());
				loanResForm.setBorrowAmount(borrowResVo.getBorrowAmount());
				loanResForm.setBorrowTitle(borrowResVo.getBorrowTitle());
				loanResForm.setBorrowedAmount(borrowResVo.getBorrowedAmount());
				loanResForm.setBorrowType(borrowResVo.getBorrowType());
				loanResForm.setBorrowRate(borrowResVo.getBorrowRate());
				loanResForm.setRepayTypeName(borrowResVo.getRepayType().getName());
				// ???????????????????????????
				String groupName = handlerGroupName(loanResForm.getInvestGroupCode());
				loanResForm.setInvestGroupCodeName(groupName);
				LoanExtendReqVo loanExtendReqVo = new LoanExtendReqVo();
				loanExtendReqVo.setLoanCode(loanResVo.getId());
				logger.info("??????{}??????:" + JSONObject.toJSONString(loanExtendReqVo), ILoanExtendService.class);
				TradeCommonResult<List<LoanExtendResVo>> loanExtendResult = loanExtendService
						.listLoanExtend(loanExtendReqVo);
				logger.info("??????{}??????:" + JSONObject.toJSONString(loanExtendResult), ILoanExtendService.class);
				if (loanExtendResult.isSuccess()) {
					List<LoanExtendResVo> loanExtendResVos = loanExtendResult.getBusinessObject();
					for (LoanExtendResVo loanExtendResVo : loanExtendResVos) {
						if (loanExtendResVo.getExtendType().equals(LoanExtendType.DESC)) {
							loanResForm.setDescId(loanExtendResVo.getId());
							loanResForm.setDesc(loanExtendResVo.getMark());
						} else if (loanExtendResVo.getExtendType().equals(LoanExtendType.PURPOSE)) {
							loanResForm.setPurposeId(loanExtendResVo.getId());
							loanResForm.setPurpose(loanExtendResVo.getMark());
						} else if (loanExtendResVo.getExtendType().equals(LoanExtendType.RISK)) {
							loanResForm.setRiskId(loanExtendResVo.getId());
							loanResForm.setRisk(loanExtendResVo.getMark());
						} else if (loanExtendResVo.getExtendType().equals(LoanExtendType.INITOPERDESC)) {
							loanResForm.setInitoperdescId(loanExtendResVo.getId());
							loanResForm.setInitoperdesc(loanExtendResVo.getMark());
							loanResForm.setInitoperdescTime(loanExtendResVo.getModifyTime());
							loanResForm.setInitoperdescEditBy(loanExtendResVo.getEditedBy());
						} else if (loanExtendResVo.getExtendType().equals(LoanExtendType.OPENOPERDESC)) {
							loanResForm.setOpenoperdescId(loanExtendResVo.getId());
							loanResForm.setOpenoperdesc(loanExtendResVo.getMark());
							loanResForm.setOpenoperdescTime(loanExtendResVo.getModifyTime());
							loanResForm.setOpenoperdescEditBy(loanExtendResVo.getEditedBy());
						} else if (loanExtendResVo.getExtendType().equals(LoanExtendType.OPENEDOPERDESC)) {
							loanResForm.setOpenedoperdescId(loanExtendResVo.getId());
							loanResForm.setOpenedoperdesc(loanExtendResVo.getMark());
							loanResForm.setOpenedoperdescTime(loanExtendResVo.getModifyTime());
							loanResForm.setOpenedoperdescEditBy(loanExtendResVo.getEditedBy());
						} else if (loanExtendResVo.getExtendType().equals(LoanExtendType.FULLOPERDESC)) {
							loanResForm.setFulloperdescId(loanExtendResVo.getId());
							loanResForm.setFulloperdesc(loanExtendResVo.getMark());
							loanResForm.setFulloperdescTime(loanExtendResVo.getModifyTime());
							loanResForm.setFulloperdescEditBy(loanExtendResVo.getEditedBy());
						} else if (loanExtendResVo.getExtendType().equals(LoanExtendType.DISBURSEOPERDESC)) {
							loanResForm.setDisbureoperdescId(loanExtendResVo.getId());
							loanResForm.setDisbureoperdesc(loanExtendResVo.getMark());
							loanResForm.setDisbureoperdescTime(loanExtendResVo.getModifyTime());
							loanResForm.setDisbureoperdescEditBy(loanExtendResVo.getEditedBy());
						}
					}
				} else {
					return Result.error(loanExtendResult.getResultCodeMsg());
				}
			} else {
				return Result.error(borrowResult.getResultCodeMsg());
			}
			return Result.success(loanResForm);
		} else {
			return Result.error(loanResult.getResultCodeMsg());
		}
	}

	private String handlerGroupName(String investGroupCode) {
		String groupName = StringUtils.EMPTY;
		if (StringUtils.isBlank(investGroupCode)) {
			return groupName;
		}
		SystemConfigReqVo systemConfigReqVo = new SystemConfigReqVo();
		systemConfigReqVo.setClassCode("userGroups");
		systemConfigReqVo.setCodeKey(investGroupCode);
		systemConfigReqVo.setUnit("0");
		systemConfigReqVo.setSystemCode("P05");
		BasedataCommonResult<SystemConfigResVo> systemConfig = null;
		try {
			systemConfig = systemConfigService.getSystemConfig(systemConfigReqVo);
			if (systemConfig.isSuccess()) {
				groupName = systemConfig.getBusinessObject().getCodeValue();
			}
		} catch (Exception e) {
			logger.error("??????????????????????????????????????????", e);
		}
		return groupName;
	}

	/**
	 * ????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/{menu}/getContractList", method = RequestMethod.POST)
	@ResponseBody
	public Result getContractList() {
		ProtocolReqVo protocolReqVo = new ProtocolReqVo();
		protocolReqVo.setType("p2p"); // transfer
		protocolReqVo.setAvailable(1);
		BasedataCommonResult<PageInfo<ProtocolResVo>> result = protocolService.list(protocolReqVo, 0, 100);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject().getList());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/{menu}/getLoanTypeList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getLoanTypeList() {
		Map<String, String> loanTypes = new LinkedHashMap<String, String>();
		for (LoanType obj : LoanType.class.getEnumConstants()) {
			loanTypes.put(obj.getCode(), obj.getName());
		}
		return loanTypes;
	}

	/**
	 * ??????????????????????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/{menu}/getInvestGroupCode", method = RequestMethod.POST)
	@ResponseBody
	public Result getInvestGroupCode() {
		SystemConfigReqVo systemConfigReqVo = new SystemConfigReqVo();
		systemConfigReqVo.setClassCode("userGroups");
		systemConfigReqVo.setUnit("0");
		systemConfigReqVo.setAvailable(1);
		BasedataCommonResult<PageInfo<SystemConfigResVo>> systemConfigResult = null;
		try {
			systemConfigResult = systemConfigService.listSystemConfig(systemConfigReqVo, 1, 60);
			if (systemConfigResult.isSuccess()) {
				return Result.success(systemConfigResult.getBusinessObject());
			} else
				return Result.error("????????????");
		} catch (Exception e) {
			logger.error("????????????????????????????????????", e);
			return Result.error("????????????????????????????????????");
		}
	}

	/**
	 * ??????????????????
	 *
	 * @return
	 */
	@RequestMapping(value = "/{menu}/deleteLoanFile/{fileId}", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "??????????????????")
	public Result deleteLoanFile(@PathVariable @NotBlank String fileId, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		LoanFileReqVo loanFileReqVo = new LoanFileReqVo();
		loanFileReqVo.setId(fileId);
		loanFileReqVo.setEditedBy(role);
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanFileReqVo), ILoanFileService.class);
		TradeCommonResult<Integer> result = loanFileService.deleteLoanFile(loanFileReqVo);
		logger.info("??????{}??????:" + JSONObject.toJSONString(result), ILoanFileService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}

	/**
	 * ??????????????????
	 *
	 * @param files
	 * @param loanCode
	 * @return
	 */
	@RequestMapping(value = "/{menu}/uploadLoanFile/{loanCode}", method = { RequestMethod.POST })
	@ResponseBody
	public Result uploadLoanFile(@RequestParam MultipartFile[] files, @PathVariable String loanCode,
			HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
//		String a = System.getProperty("user.dir");
//		String warterPath = a + "\\boss-resources\\src\\main\\webapp\\jajaying.png";
		List<LoanFileReqVo> loanExtendReqVoList = new ArrayList<LoanFileReqVo>();
		for (MultipartFile multipartFile : files) {
			LoanFileReqVo loanFileReqVo = new LoanFileReqVo();
			String fileName = multipartFile.getOriginalFilename();
			FileOutputStream fos = null;
			String originalName = null;
			String loanOrigin = PropertiesFactoryUtil.getProperties("META-INF/config.properties")
					.getProperty("loanOrigin");
			String loanCompress = PropertiesFactoryUtil.getProperties("META-INF/config.properties")
					.getProperty("loanCompress");
			String rootPath = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("rootPath");
			if (!multipartFile.isEmpty()) {
				originalName = multipartFile.getOriginalFilename();// ??????????????????+?????????
				String uuid = UUID.randomUUID().toString();
				String dateName = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
				int index = 0;
				index = originalName.lastIndexOf(".");
				String extendName = originalName.substring(index);// ???????????????
				String format = extendName.substring(1, extendName.length());// ????????????
				String startDatePath = dateName + uuid + extendName;
				String originFullPath = rootPath + loanOrigin + startDatePath;
				String compressFullPath = rootPath + loanCompress + startDatePath;
				try {
					File file = new File(originFullPath);
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					fos = new FileOutputStream(file);
					fos.write(multipartFile.getBytes());
					fos.flush();
					fos.close();
					addWatermark(originFullPath,"?????????",format);
					saveCompressFile(originFullPath, compressFullPath, 750, format);
				} catch (IOException e) {
					logger.error("??????????????????", e);
				}
				loanFileReqVo.setEditedBy(role);
				loanFileReqVo.setLoanCode(loanCode);
				loanFileReqVo.setTitle(fileName);
				loanFileReqVo.setFileUrl(startDatePath);// ??????????????????
				loanFileReqVo.setMark("??????????????????");
				loanExtendReqVoList.add(loanFileReqVo);
			}
		}
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanExtendReqVoList), ILoanFileService.class);
		TradeCommonResult<List<LoanFileResVo>> listTradeCommonResult = loanFileService
				.insertLoanFileList(loanExtendReqVoList);
		logger.info("??????{}??????:" + JSONObject.toJSONString(listTradeCommonResult), ILoanFileService.class);
		if (listTradeCommonResult.isSuccess())
			return Result.success(listTradeCommonResult.getBusinessObject());
		else
			return Result.error(listTradeCommonResult.getResultCodeMsg());
	}

	private void saveCompressFile(String originFullPath, String compressFullPath, int width, String format) {
		File originFile = new File(originFullPath);
		File compressFile = new File(compressFullPath);
		compressFile.getParentFile().mkdirs();// ????????????
		try {
			ImageSizer.resize(originFile, compressFile, width, format);
		} catch (Exception e) {
			logger.error("???????????????????????????????????????", e);
		}
	}
public  String filen() {
	String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", ""); 
	return classpath;
}
public static void addWatermark(String sourceImgPath, String waterMarkContent,String fileExt){
	try {
		Thumbnails.of(sourceImgPath).scale(1.0d).outputFormat(fileExt).toFile(sourceImgPath+"."+fileExt);
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	 Font font = new Font("??????", Font.PLAIN, 30);//?????????????????????

    Color markContentColor = Color.red;//????????????

    Integer degree = 45;//?????????????????????????????????

    float alpha = 0.3f;//?????????????????????

    OutputStream outImgStream = null;

    try{

        File srcImgFile = new File(sourceImgPath);//????????????

        java.awt.Image srcImg = ImageIO.read(srcImgFile);//?????????????????????

        int srcImgWidth = srcImg.getWidth(null);//??????????????????

        int srcImgHeight = srcImg.getHeight(null);//??????????????????

//?????????

        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bufImg.createGraphics();//????????????

        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);

        g.setColor(markContentColor); //??????????????????

        g.setFont(font); //????????????

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));//???????????????????????????

        if(null!= degree) {

            g.rotate(Math.toRadians(degree));//??????????????????

        }

        JLabel label = new JLabel(waterMarkContent);

        FontMetrics metrics = label.getFontMetrics(font);

        int width = metrics.stringWidth(label.getText());//??????????????????

        int mark = 2 ;
        int rowsNumber = srcImgHeight/width / mark ;// ???????????? ?????? ?????????????????? ??????> ???????????????(??????????????????????????????)

        int columnsNumber = srcImgWidth/width / mark ;//???????????? ?????? ?????????????????? ??????> ?????????????????????(??????????????????????????????)
        //??????????????????????????????????????????????????????????????????
        if(rowsNumber < 1){
            rowsNumber = 1;
        }

        if(columnsNumber < 1){
            columnsNumber = 1;
        }

        for(int j=0;j<rowsNumber;j++){

            for(int i=0;i<columnsNumber;i++){
                g.drawString(waterMarkContent, i*width*mark + j*width*mark, -i*width*mark + j*width*mark);//????????????,?????????????????????
            }
        }


        g.dispose();// ????????????
        // ????????????
        outImgStream = new FileOutputStream(sourceImgPath);

        ImageIO.write(bufImg, fileExt, outImgStream);

    } catch(Exception e) {

        e.printStackTrace();

        e.getMessage();

    } finally{

        try{

            if(outImgStream != null){

                outImgStream.flush();

                outImgStream.close();

            }

        } catch(Exception e) {

            e.printStackTrace();

            e.getMessage();

        }

    }

}

	/**
	 * ???????????????????????????
	 * @param loanCode
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{menu}/export/{loanCode}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Result export(@PathVariable @NotBlank String loanCode, HttpServletResponse response) {

		try {
			// ?????????????????????????????????
			InterestPlansReqVo interestPlansReqVo = new InterestPlansReqVo();
			interestPlansReqVo.setLoanCode(loanCode);
			List<InterestPlansStatus> statusIn = new ArrayList<InterestPlansStatus>();
			statusIn.add(InterestPlansStatus.REPAY_COMMEN);
			statusIn.add(InterestPlansStatus.REPAY_EARLY);
			statusIn.add(InterestPlansStatus.REPAY_OVERDUE);
			interestPlansReqVo.setStatusIn(statusIn);
			logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansReqVo), IInterestPlansService.class);

			TradeCommonResult<List<InterestPlansResVo>> interestPlans = interestPlansService
					.listInterestPlans(interestPlansReqVo);
			logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlans), IInterestPlansService.class);
			List<InterestExcelPlans> plans = new ArrayList<InterestExcelPlans>();
			for (InterestPlansResVo interestPlansResVo : interestPlans.getBusinessObject()) {
				InterestExcelPlans plan = new InterestExcelPlans();
				// ????????????
				logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansResVo), IUserInfoService.class);
				UserCommonResult<UserInfoResVo> userResult = userInfoService.getByRoleUserId(interestPlansResVo.getUserCode());
				logger.info("??????{}??????:" + JSONObject.toJSONString(userResult), IUserInfoService.class);
				UserInfoResVo user = userResult.getBusinessObject();
				logger.info("??????{}??????:" + JSONObject.toJSONString(interestPlansResVo), ILoanInvestorService.class);
				TradeCommonResult<LoanInvestorResVo> investResult = loanInvestorService
						.getById(interestPlansResVo.getInvestorCode());
				logger.info("??????{}??????:" + JSONObject.toJSONString(investResult), ILoanInvestorService.class);
				plan.setDiscount(interestPlansResVo.getEarlyRepayment().addTo(interestPlansResVo.getOverdueInterest()));// ??????
				plan.setPhase(interestPlansResVo.getPhase());// ????????????
				plan.setModifyTime(interestPlansResVo.getModifyTime());// ????????????
				plan.setInterest(interestPlansResVo.getInterestAmount());// ??????
				plan.setInvestAmount(interestPlansResVo.getPrincipalAmount());// ????????????
				plan.setPhone(SensitiveInfoUtils.mobilePhone(user.getRegisterMobile()));// ?????????
				plan.setPrincipalAmount(interestPlansResVo.getPrincipalAmount());// ??????
				plan.setRedMoneyAmount(investResult.getBusinessObject().getRedMoneyAmount());// ????????????
				plan.setRealPay(interestPlansResVo.getPrincipalAmount()
						.subtractFrom(investResult.getBusinessObject().getRedMoneyAmount()));// ????????????
				Money m1 = new Money();
				Money m2 = new Money();
				Money m3 = new Money();
				m1.setAmount(plan.getDiscount().getAmount());
				m2.setAmount(plan.getPrincipalAmount().getAmount());
				m3.setAmount(plan.getInterest().getAmount());
				plan.setTotal(m1.addTo(m2).addTo(m3));// ??????
				plan.setUserCode(interestPlansResVo.getInvestorCode());// ??????id
				plan.setUserName(SensitiveInfoUtils.chineseName(user.getRealName()));// ??????
				if ("REPAY_COMMEN".equals(interestPlansResVo.getStatus())) {
					plan.setStatus("?????????");
				}
				if ("REPAY_EARLY".equals(interestPlansResVo.getStatus())) {
					plan.setStatus("???????????????");
				}
				if ("REPAY_OVERDUE".equals(interestPlansResVo.getStatus())) {
					plan.setStatus("???????????????");
				}
				plans.add(plan);
			}
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String("settle.xls".getBytes("UTF-8"), "ISO-8859-1"));
			String[] headers;
			ExcelUtils<InterestExcelPlans> utils = new ExcelUtils<InterestExcelPlans>();
			headers = new String[] { "??????ID", "?????????", "?????????", "??????", "????????????", "????????????", "????????????", "????????????", "??????", "??????", "??????", "??????",
					"??????" };
			utils.exportExcel("??????????????????", headers, plans, response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
		} catch (Exception e) {
			logger.error("?????????????????????", e);
		}
		return Result.success("success");
	}

	/**
	 * ???????????????
	 * @param loanCode
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{menu}/exportInvestUser/{loanCode}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Result exportInvestUser(@PathVariable @NotBlank String loanCode, HttpServletResponse response) {
		LoanInvestUserForm loanInvestUserForm = new LoanInvestUserForm();
		LoanInvestorReqVo loanInvestorReqVo = new LoanInvestorReqVo();
		loanInvestorReqVo.setLoanCode(loanCode);
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanInvestorReqVo), ILoanInvestorService.class);
		TradeCommonResult<LoanInvestorStatisticsResVo> loanInvestorStatistics = loanInvestorService
				.loanInvestorStatistics(loanCode);
		logger.info("??????{}??????:" + JSONObject.toJSONString(loanInvestorStatistics), ILoanInvestorService.class);
		if (loanInvestorStatistics.isSuccess()) {
			loanInvestUserForm.setBiddingAmount(loanInvestorStatistics.getBusinessObject().getBiddingAmount());
			loanInvestUserForm.setCashAmount(loanInvestorStatistics.getBusinessObject().getCashAmount());
			loanInvestUserForm.setInvestCount(loanInvestorStatistics.getBusinessObject().getInvestCount());
			loanInvestUserForm.setLoanAmount(loanInvestorStatistics.getBusinessObject().getLoanAmount());
			loanInvestUserForm.setRedmoneyAmount(loanInvestorStatistics.getBusinessObject().getRedmoneyAmount());
			loanInvestorReqVo.setInvestorStatus(TradeOrderStatus.SUCCESS);
			logger.info("??????{}??????:" + JSONObject.toJSONString(loanInvestorReqVo), ILoanInvestorService.class);
			TradeCommonResult<List<LoanInvestorResVo>> result = loanInvestorService.listLoanInvestor(loanInvestorReqVo);
			logger.info("??????{}??????:" + JSONObject.toJSONString(result), ILoanInvestorService.class);
			if (result.isSuccess()) {
				List<InvestUserForm> list = BeanMapper.mapList(result.getBusinessObject(), InvestUserForm.class);
				List<InvestUserForm> investUserFormList = new ArrayList<InvestUserForm>();
				for (InvestUserForm investUserForm : list) {
					logger.info("??????{}??????:" + JSONObject.toJSONString(investUserForm), IUserInfoService.class);
					UserCommonResult<UserInfoResVo> resVoUserCommonResult = userInfoService
							.getByRoleUserId(investUserForm.getInvestorUserCode());
					logger.info("??????{}??????:" + JSONObject.toJSONString(resVoUserCommonResult), IUserInfoService.class);
					if (resVoUserCommonResult.isSuccess()) {
						investUserForm.setInvestorUserCode(resVoUserCommonResult.getBusinessObject().getId());
						investUserForm.setUserName(resVoUserCommonResult.getBusinessObject().getRealName());
						investUserForm.setUserPhone(resVoUserCommonResult.getBusinessObject().getRegisterMobile());
						investUserForm.setUserProperties(resVoUserCommonResult.getBusinessObject().getGroup());
						investUserFormList.add(investUserForm);
					}
				}
				loanInvestUserForm.setInvestUserForms(investUserFormList);
				List<InvestUserExcelPlans> plans = new ArrayList<InvestUserExcelPlans>();
				for (int i = 0; i < investUserFormList.size(); i++) {
					InvestUserExcelPlans plan = new InvestUserExcelPlans();
					plan.setInvestAmount(investUserFormList.get(i).getInvestAmount());
					plan.setInvestTime(investUserFormList.get(i).getInvestTime());
					plan.setRealName(SensitiveInfoUtils.chineseName(investUserFormList.get(i).getUserName()));
					plan.setRealPay(investUserFormList.get(i).getInvestAmount()
							.subtract(investUserFormList.get(i).getRedMoneyAmount()));
					plan.setRedMoneyAmount(investUserFormList.get(i).getRedMoneyAmount());
					plan.setRegisterMobile(SensitiveInfoUtils.mobilePhone(investUserFormList.get(i).getUserPhone()));
					plan.setUserProperties(investUserFormList.get(i).getUserProperties());
					plans.add(plan);
				}
				response.setContentType("application/octet-stream");
				try {
					response.setHeader("Content-Disposition",
							"attachment;filename=" + new String("settle.xls".getBytes("UTF-8"), "ISO-8859-1"));
				} catch (UnsupportedEncodingException e) {
					logger.error("????????????", e);
				}
				String[] headers;
				ExcelUtils<InvestUserExcelPlans> utils = new ExcelUtils<InvestUserExcelPlans>();
				headers = new String[] { "?????????", "??????", "????????????", "????????????", "????????????", "????????????", "????????????" };
				try {
					utils.exportExcel("??????????????????", headers, plans, response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
				} catch (IOException e) {
					logger.error("?????????????????????", e);
				}
				return Result.success(loanInvestUserForm);
			} else {
				return Result.error(result.getResultCodeMsg());
			}
		} else {
			return Result.error(loanInvestorStatistics.getResultCodeMsg());
		}

	}

}

package com.winsmoney.jajaying.boss.controller.trade;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.controller.BaseController;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.boss.request.TradeStreamApplyReq;
import com.winsmoney.jajaying.boss.request.TradeStreamSearch;
import com.winsmoney.jajaying.boss.response.TradeStreamRes;
import com.winsmoney.jajaying.deposit.service.IInfoService;
import com.winsmoney.jajaying.deposit.service.ITransRecordService;
import com.winsmoney.jajaying.deposit.service.enums.UserRole;
import com.winsmoney.jajaying.deposit.service.request.TransRecordReqVo;
import com.winsmoney.jajaying.deposit.service.request.info.TransFlowingReqVo;
import com.winsmoney.jajaying.deposit.service.response.CommonResVo;
import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;
import com.winsmoney.jajaying.deposit.service.response.TransRecordResVo;
import com.winsmoney.jajaying.deposit.service.response.info.TransFlowingResVo;
import com.winsmoney.jajaying.user.service.IUserRoleInfoService;
import com.winsmoney.jajaying.user.service.enums.UserRoleType;
import com.winsmoney.jajaying.user.service.request.UserRoleInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserRoleInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * ????????????
 *
 * @author howard he
 * @create 2018/8/29 09:55
 */
@Controller
@RequestMapping("/trade/stream")
public class TradeStreamController extends BaseController {

	@Autowired
	private IInfoService depositInfoService;
	@Autowired
	private ITransRecordService depositTransRecordService;
	@Autowired
	private IUserRoleInfoService userRoleInfoService;
	private WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(TradeStreamController.class);

	/**
	 * ????????????????????????
	 *
	 * @param search
	 *            ????????????
	 * @param pageNo
	 *            ????????? ??????0
	 * @param pageSize
	 *            ???????????? ??????10
	 * @return ??????????????????
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<TradeStreamRes> list(TradeStreamSearch search, int pageNo, int pageSize) {
		TransRecordReqVo reqVo = new TransRecordReqVo();
		reqVo.setBusiness("TRANS_FLOWING");
		PageInfo<TradeStreamRes> pageInfo = new PageInfo<>();
		try {
			DepositCommonResult<PageInfo<TransRecordResVo>> depositCommonResult = depositTransRecordService.list(reqVo,
					pageNo, pageSize);
			if (depositCommonResult == null || depositCommonResult.getBusinessObject() == null) {
				throw new RuntimeException("????????????????????? null");
			}
			PageInfo<TransRecordResVo> transRecordResVoPageInfo = depositCommonResult.getBusinessObject();
			pageInfo.setPageSize(pageSize);
			pageInfo.setPageNum(pageNo);
			pageInfo.setTotal(transRecordResVoPageInfo.getTotal());
			List<TradeStreamRes> list = Lists.newArrayList();
			for (TransRecordResVo transRecordResVo : transRecordResVoPageInfo.getList()) {
				TradeStreamRes res = new TradeStreamRes();
				res.setId(transRecordResVo.getId());
				res.setRequestId(transRecordResVo.getRequestId());
				res.setUserId(transRecordResVo.getUserId());
				res.setStartDate(transRecordResVo.getRequestTime());
				res.setEndDate(transRecordResVo.getResponseTime());
				res.setStatus(transRecordResVo.getTransStatus());
				res.setSequenceId(transRecordResVo.getSequenceId());
				res.setRequestTime(transRecordResVo.getRequestTime());
				res.setResponseTime(transRecordResVo.getResponseTime());
				res.setRemark(transRecordResVo.getRemark());
				list.add(res);
			}
			pageInfo.setList(list);
		} catch (Exception e) {
			logger.error("????????????????????????????????????", e);
		}
		return pageInfo;
	}

	/**
	 * ??????????????????
	 *
	 * @param req
	 *            ??????body
	 * @return ??????\??????
	 */
	@AduitLog(type = OperType.CREATE, content = "??????????????????")
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	@ResponseBody
	public Result apply(TradeStreamApplyReq req) {

		TransFlowingReqVo reqVo = new TransFlowingReqVo();
		// ????????????ID??????,????????????
		if (!StringUtil.isBlank(req.getSequenceIds())) {
			ArrayList<String> list = new ArrayList<>();
			// ???????????????????????????
			String[] array = req.getSequenceIds().split("[\\t \\n]+");
			for (String sequenceId : array) {
				list.add(sequenceId);
			}
			if(list.size()>100){
				return Result.error("?????????????????????100???");
			}else{
				reqVo.setSequenceIdList(list);
				reqVo.setUserId(req.getUserId());
				reqVo.setBeginTime(req.getStartDate());
				reqVo.setEndTime(req.getEndDate());
				reqVo.setBusinessType("RECHARGE,RELEASE,REPAY,WITHDRAW,MARKETING");
				reqVo.setRequestId(UUID.randomUUID().toString().replaceAll("-", ""));
				reqVo.setRequestTime(new Date());
			}
			try {
				DepositCommonResult<CommonResVo> depositCommonResult = depositInfoService.applyTransFlowing(reqVo);
				if (depositCommonResult == null) {
					throw new RuntimeException("?????????????????????????????????");
				}
				if (depositCommonResult.isFailure()) {
					logger.error(depositCommonResult.getResultCodeMsg());
					return Result.error("????????????????????????");
				}
				return Result.success(req);
			} catch (Exception e) {
				logger.error("apply error", e);
				return Result.error("????????????????????????");
			}
		// ????????????????????????ID
		} else {
			reqVo.setUserId(req.getUserId());
			reqVo.setBeginTime(req.getStartDate());
			reqVo.setEndTime(req.getEndDate());
			reqVo.setBusinessType("RECHARGE,RELEASE,REPAY,WITHDRAW,MARKETING");
			reqVo.setRequestId(UUID.randomUUID().toString().replaceAll("-", ""));
			reqVo.setRequestTime(new Date());
			// ??????userId????????????,????????????????????????
			UserRoleInfoReqVo userRoleInfoReqVo = new UserRoleInfoReqVo();
			if (StringUtil.isBlank(req.getUserId())) {
				userRoleInfoReqVo.setUserId(null);
				reqVo.setUserRole(null);
			} else {
				userRoleInfoReqVo.setUserId(req.getUserId());
				UserCommonResult<UserRoleInfoResVo> userCommonResult = userRoleInfoService.get(userRoleInfoReqVo);
				UserRoleType roleCode = userCommonResult.getBusinessObject().getRoleCode();
				if ("INVEST".equals(roleCode.toString())) {
					reqVo.setUserRole(UserRole.getEnum("INVESTOR"));
				} else if ("BORROW".equals(roleCode.toString())) {
					reqVo.setUserRole(UserRole.getEnum("BORROWERS"));
				} else if ("GUARANTEE".equals(roleCode.toString())) {
					reqVo.setUserRole(UserRole.getEnum("GUARANTEECORP"));
				} else {
					reqVo.setUserRole(null);
				}
			}

			try {
				DepositCommonResult<CommonResVo> depositCommonResult = depositInfoService.applyTransFlowing(reqVo);
				if (depositCommonResult == null) {
					throw new RuntimeException("?????????????????????????????????");
				}
				if (depositCommonResult.isFailure()) {
					logger.error(depositCommonResult.getResultCodeMsg());
					return Result.error("????????????????????????");
				}
				return Result.success(req);
			} catch (Exception e) {
				logger.error("apply error", e);
				return Result.error("????????????????????????");
			}
		}
	}

	/**
	 * ????????????
	 */
	@AduitLog(type = OperType.CREATE, content = "??????????????????")
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	@ResponseBody
	public Result downloadFile(String requestId) {
		TransFlowingReqVo reqVo = new TransFlowingReqVo();
		reqVo.setRequestId(requestId);
		try {
			DepositCommonResult<TransFlowingResVo> depositCommonResult = depositInfoService.downloadTransFlowing(reqVo);
			if (depositCommonResult.isFailure()) {
				return Result.error(depositCommonResult.getResultCodeMsg());
			}
			TransFlowingResVo transFlowingResVo = depositCommonResult.getBusinessObject();
			String url;
			if (transFlowingResVo == null || Strings.isNullOrEmpty(url = transFlowingResVo.getUrl())) {
				logger.error("??????????????????????????? {}", depositCommonResult);
				return Result.error("???????????????????????????");
			}
			return Result.success(url);
		} catch (Exception e) {
			logger.error("????????????????????????", e);
			return Result.error(e.getMessage());
		}

	}

}

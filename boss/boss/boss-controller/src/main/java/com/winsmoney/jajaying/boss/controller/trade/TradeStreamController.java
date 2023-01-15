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
 * 交易流水
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
	 * 查询交易流水列表
	 *
	 * @param search
	 *            查询条件
	 * @param pageNo
	 *            起始页 默认0
	 * @param pageSize
	 *            每页大小 默认10
	 * @return 交易流水列表
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
				throw new RuntimeException("调用接口时返回 null");
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
			logger.error("查询交易流水列表出现异常", e);
		}
		return pageInfo;
	}

	/**
	 * 交易流水申请
	 *
	 * @param req
	 *            请求body
	 * @return 成功\失败
	 */
	@AduitLog(type = OperType.CREATE, content = "交易流水申请")
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	@ResponseBody
	public Result apply(TradeStreamApplyReq req) {

		TransFlowingReqVo reqVo = new TransFlowingReqVo();
		// 若有流水ID输入,直接查询
		if (!StringUtil.isBlank(req.getSequenceIds())) {
			ArrayList<String> list = new ArrayList<>();
			// 根据空格或回车分割
			String[] array = req.getSequenceIds().split("[\\t \\n]+");
			for (String sequenceId : array) {
				list.add(sequenceId);
			}
			if(list.size()>100){
				return Result.error("交易流水号超出100条");
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
					throw new RuntimeException("调用接口返回数据有错误");
				}
				if (depositCommonResult.isFailure()) {
					logger.error(depositCommonResult.getResultCodeMsg());
					return Result.error("调用接口返回失败");
				}
				return Result.success(req);
			} catch (Exception e) {
				logger.error("apply error", e);
				return Result.error("调用接口出现异常");
			}
		// 若未输入交易流水ID
		} else {
			reqVo.setUserId(req.getUserId());
			reqVo.setBeginTime(req.getStartDate());
			reqVo.setEndTime(req.getEndDate());
			reqVo.setBusinessType("RECHARGE,RELEASE,REPAY,WITHDRAW,MARKETING");
			reqVo.setRequestId(UUID.randomUUID().toString().replaceAll("-", ""));
			reqVo.setRequestTime(new Date());
			// 根据userId查询角色,作为申请接口参数
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
					throw new RuntimeException("调用接口返回数据有错误");
				}
				if (depositCommonResult.isFailure()) {
					logger.error(depositCommonResult.getResultCodeMsg());
					return Result.error("调用接口返回失败");
				}
				return Result.success(req);
			} catch (Exception e) {
				logger.error("apply error", e);
				return Result.error("调用接口出现异常");
			}
		}
	}

	/**
	 * 下载文件
	 */
	@AduitLog(type = OperType.CREATE, content = "交易流水下载")
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
				logger.error("接口返回的数据为空 {}", depositCommonResult);
				return Result.error("接口返回的数据为空");
			}
			return Result.success(url);
		} catch (Exception e) {
			logger.error("调用下载接口失败", e);
			return Result.error(e.getMessage());
		}

	}

}

package com.winsmoney.jajaying.boss.controller.signet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.sign.SignConfigController;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.protocol.service.IContractSignatureSerivce;
import com.winsmoney.jajaying.protocol.service.IOrderProtocolService;
import com.winsmoney.jajaying.protocol.service.IProtocolTemplateService;
import com.winsmoney.jajaying.protocol.service.ISealImageRecordService;
import com.winsmoney.jajaying.protocol.service.ISealRecordService;
import com.winsmoney.jajaying.protocol.service.ISignatureBatchService;
import com.winsmoney.jajaying.protocol.service.IpaperLessService;
import com.winsmoney.jajaying.protocol.service.enums.TransStatus;
import com.winsmoney.jajaying.protocol.service.enums.UserType;
import com.winsmoney.jajaying.protocol.service.request.BaseReqVo;
import com.winsmoney.jajaying.protocol.service.request.OrderProtocolReqVo;
import com.winsmoney.jajaying.protocol.service.request.SealImageRecordReqVo;
import com.winsmoney.jajaying.protocol.service.request.SealImageReqVo;
import com.winsmoney.jajaying.protocol.service.request.SealRecordReqVo;
import com.winsmoney.jajaying.protocol.service.request.SealReqVo;
import com.winsmoney.jajaying.protocol.service.request.SignatureBatchReqVo;
import com.winsmoney.jajaying.protocol.service.request.SignatureRetryReqVo;
import com.winsmoney.jajaying.protocol.service.response.CommonResVo;
import com.winsmoney.jajaying.protocol.service.response.OrderProtocolResVo;
import com.winsmoney.jajaying.protocol.service.response.ProtocolCommonResult;
import com.winsmoney.jajaying.protocol.service.response.SealImageRecordResVo;
import com.winsmoney.jajaying.protocol.service.response.SealRecordResVo;
import com.winsmoney.jajaying.protocol.service.response.SignatureBatchResVo;
import com.winsmoney.jajaying.trade.service.IBorrowService;
import com.winsmoney.jajaying.trade.service.response.BorrowResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IEnterpriseUserService;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.request.EnterpriseUserReqVo;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.response.EnterpriseUserResVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;


@Controller
@RequestMapping("/signet")
public class ElectronicSealController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(SignConfigController.class);

	@Autowired
	private IpaperLessService paperLessService;
	@Autowired
	private ISealImageRecordService sealImageRecord;
	@Autowired
	private ISealRecordService sealRecord;
	@Autowired
	private IOrderProtocolService orderProtocol;
	@Autowired
	private ISignatureBatchService signatureBatch;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IEnterpriseUserService enterpriseUser;
	@Autowired
	private SequenceGenerator sequenceGenerator;
	@Autowired
	private IContractSignatureSerivce contractSignature;
	@Autowired
	private IProtocolTemplateService protocolTemplateService;
	@Autowired
	private IBorrowService borrowService;

	/**
	 * ??????????????????????????????
	 * 
	 * @param reqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/person_moulage_record/getSealImageRecord", method = RequestMethod.POST)
	@ResponseBody
	public Result getPersonSealImageRecord(SealImageRecordReqVo reqVo, int pageNo, int pageSize) {
		ProtocolCommonResult<PageInfo<SealImageRecordResVo>> result;
		if (StringUtils.isBlank(reqVo.getUserId())) {
			reqVo.setUserId(null);
		}
		try {
			reqVo.setUserType(UserType.PERSON);
			logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), ISealImageRecordService.class);
			result = sealImageRecord.list(reqVo, pageNo, pageSize);
			logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), ISealImageRecordService.class);
			if (result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			} else {
				return Result.error("????????????????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("????????????????????????");
		}
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param reqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/enterprise_moulage_record/getSealImageRecord", method = RequestMethod.POST)
	@ResponseBody
	public Result getEnterpriseSealImageRecord(SealImageRecordReqVo reqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(reqVo.getUserId())) {
			reqVo.setUserId(null);
		}
		reqVo.setUserType(UserType.ENTERPRISE);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), ISealImageRecordService.class);
		ProtocolCommonResult<PageInfo<SealImageRecordResVo>> result = sealImageRecord.list(reqVo, pageNo, pageSize);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), ISealImageRecordService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error("????????????????????????");
		}
	}

	/**
	 * ?????????????????? enterpriseUser userInfoService
	 * 
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/moulage_record/getSealImageInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result getSealImageInfo(SealImageRecordReqVo reqVo) {
		if (StringUtils.isBlank(reqVo.getUserId())) {
			reqVo.setUserId(null);
		}
		reqVo.setTransStatus(TransStatus.SUCCESS);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), ISealImageRecordService.class);
		ProtocolCommonResult<SealImageRecordResVo> result = sealImageRecord.get(reqVo);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), ISealImageRecordService.class);

		if (StringUtils.isNotBlank(result.getBusinessObject().getId())) {
			if (result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			} else {
				return Result.error("??????????????????????????????");
			}
		} else {
			// ?????????????????????,???userInfo
			SealImageRecordResVo sealImage = new SealImageRecordResVo();
//			UserInfoReqVo userInfoReq = new UserInfoReqVo();
//			userInfoReq.setId(reqVo.getUserId());
			UserCommonResult<UserInfoResVo> result2 = userInfoService.getByRoleUserId(reqVo.getUserId());
			if (result2.isSuccess()) {
				UserInfoResVo userInfoResVo = result2.getBusinessObject();
				if (StringUtils.isNotEmpty(userInfoResVo.getId())) {
					sealImage.setUserId(userInfoResVo.getId());
					// ??????????????????
					if (com.winsmoney.jajaying.user.service.enums.UserType.PERSON.equals(userInfoResVo.getUserType())) {
						sealImage.setUserType(UserType.PERSON);
						sealImage.setUserName(userInfoResVo.getRealName());
						sealImage.setCertNo(userInfoResVo.getCertNo());
						sealImage.setBankMobile(userInfoResVo.getRegisterMobile());

					}
					// ??????????????????
					//|| com.winsmoney.jajaying.user.service.enums.UserType.SYSTEM.equals(userInfoResVo.getUserType())
					else if (com.winsmoney.jajaying.user.service.enums.UserType.ENTERPRISE
							.equals(userInfoResVo.getUserType()) || com.winsmoney.jajaying.user.service.enums.UserType.SYSTEM
							.equals(userInfoResVo.getUserType())) {
						EnterpriseUserReqVo enterReq = new EnterpriseUserReqVo();
						enterReq.setUserId(reqVo.getUserId());
						UserCommonResult<EnterpriseUserResVo> result3 = enterpriseUser.getEnterpriseUser(enterReq);
						if (result3.isSuccess()) {
							sealImage.setUserType(UserType.ENTERPRISE);
							sealImage.setUserName(result3.getBusinessObject().getEnterpriseName());
							if (StringUtils.isBlank(result3.getBusinessObject().getUnifiedSocialCreditCode())){
								sealImage.setCertNo(result3.getBusinessObject().getOrganizingCode());
							}else {
								sealImage.setCertNo(result3.getBusinessObject().getUnifiedSocialCreditCode());
							}
							sealImage.setBankMobile(result3.getBusinessObject().getLegalPersonMobile());
						} else {
							return Result.error("????????????????????????");
						}
					}
					return Result.success(sealImage);
				} else {
					return Result.error("????????????????????????");
				}
			} else {
				return Result.error("????????????????????????");
			}

		}
	}

	/**
	 * ????????????
	 * 
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/moulage_record/sealImageGenerate", method = RequestMethod.POST)
	@ResponseBody
	public Result sealImageGenerate(SealImageReqVo reqVo) {

		String uuid = sequenceGenerator.getUUID();
		reqVo.setRequestId(uuid);
		reqVo.setRequestTime(new Date());
		reqVo.setIp("");

		ProtocolCommonResult<CommonResVo> result;
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), IpaperLessService.class);

		if (UserType.PERSON.equals(reqVo.getUserType())) {
			result = paperLessService.sealImageGenerate(reqVo);
		} else if (UserType.ENTERPRISE.equals(reqVo.getUserType())) {
			result = paperLessService.circleSealImageGenerate(reqVo);
		} else {
			result = null;
		}
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), IpaperLessService.class);
		if (result.isSuccess()) {
			return Result.success(result);
		} else {
			return Result.error("??????????????????");
		}
	}

	/**
	 * ????????????
	 * 
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/moulage_record/sealImageUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result sealImageUpdate(SealImageReqVo reqVo) {

		String uuid = sequenceGenerator.getUUID();
		reqVo.setRequestId(uuid);
		reqVo.setRequestTime(new Date());
		reqVo.setIp("");

		ProtocolCommonResult<CommonResVo> result;
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), IpaperLessService.class);
		result = paperLessService.sealImageUpdate(reqVo);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), IpaperLessService.class);
		if (result.isSuccess()) {
			return Result.success(result);
		} else {
			return Result.error("??????????????????");
		}
	}
	
	/**
	 * ??????&????????????
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/moulage_record/invalidateMoulage", method = RequestMethod.POST)
	@ResponseBody
	public Result invalidateMoulage(SealImageRecordReqVo reqVo) {
		
		reqVo.setTransStatus(TransStatus.INVALID);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), ISealImageRecordService .class);
		ProtocolCommonResult<Integer> result = sealImageRecord.updateByRequestId(reqVo);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), ISealImageRecordService .class);
		if (result.isSuccess()) {
			
			ProtocolCommonResult<SealImageRecordResVo> result3 = sealImageRecord.get(reqVo);
			SealRecordReqVo sealRecordReqVo = new SealRecordReqVo();
			sealRecordReqVo.setUserId(reqVo.getUserId());
			sealRecordReqVo.setSealImageRequestId(result3.getBusinessObject().getRequestId());
			sealRecordReqVo.setTransStatus(TransStatus.INVALID);
			ProtocolCommonResult<Integer> result2 = sealRecord.updateByRequestId(sealRecordReqVo);
			if(result2.isSuccess()){
				return Result.success(result2);
			}else{
				return Result.error("??????????????????");
			}
		} else {
			return Result.error("??????????????????");
		}
	}

	/**
	 * ????????????????????????
	 * 
	 * @param reqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/person_signet_record/getPersonSealRecord", method = RequestMethod.POST)
	@ResponseBody
	public Result getPersonSealRecord(SealRecordReqVo reqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(reqVo.getUserId())) {
			reqVo.setUserId(null);
		}
		reqVo.setUserType(UserType.PERSON);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), ISealRecordService.class);
		ProtocolCommonResult<PageInfo<SealRecordResVo>> result = sealRecord.list(reqVo, pageNo, pageSize);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), ISealRecordService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error("????????????????????????");
		}
	}

	/**
	 * ????????????????????????
	 * 
	 * @param reqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/enterprise_signet_record/getEnterpriseSealRecord", method = RequestMethod.POST)
	@ResponseBody
	public Result getEnterpriseSealRecord(SealRecordReqVo reqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(reqVo.getUserId())) {
			reqVo.setUserId(null);
		}
		reqVo.setUserType(UserType.ENTERPRISE);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), ISealRecordService.class);
		ProtocolCommonResult<PageInfo<SealRecordResVo>> result = sealRecord.list(reqVo, pageNo, pageSize);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), ISealRecordService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error("????????????????????????");
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/moulage_record/getSealInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result getSealInfo(SealRecordReqVo reqVo) {
		ProtocolCommonResult<SealRecordResVo> result;
		if (StringUtils.isBlank(reqVo.getRequestId())) {
			reqVo.setRequestId(null);
		}
		try {
			logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), ISealRecordService.class);
			result = sealRecord.get(reqVo);
			logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), ISealRecordService.class);
			if (result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			} else {
				return Result.error("????????????????????????");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("????????????????????????");
		}

	}
	
	/**
	 * ????????????
	 * 
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/moulage_record/autoMakeSeal", method = RequestMethod.POST)
	@ResponseBody
	public Result autoMakeSeal(SealReqVo reqVo) {

		UserCommonResult<UserInfoResVo> result2 = userInfoService.getByRoleUserId(reqVo.getUserId());
		if (result2.isSuccess()) {
			UserInfoResVo userInfoResVo = result2.getBusinessObject();
			if (StringUtils.isNotEmpty(userInfoResVo.getId())) {
				reqVo.setUserId(userInfoResVo.getId());
				// ??????????????????
				if (com.winsmoney.jajaying.user.service.enums.UserType.PERSON.equals(userInfoResVo.getUserType())) {
					reqVo.setUserName(userInfoResVo.getRealName());
					reqVo.setCertNo(userInfoResVo.getCertNo());
				}
				// ??????????????????
				//|| com.winsmoney.jajaying.user.service.enums.UserType.SYSTEM.equals(userInfoResVo.getUserType())
				else if (com.winsmoney.jajaying.user.service.enums.UserType.ENTERPRISE
						.equals(userInfoResVo.getUserType()) || com.winsmoney.jajaying.user.service.enums.UserType.SYSTEM
						.equals(userInfoResVo.getUserType())) {
					EnterpriseUserReqVo enterReq = new EnterpriseUserReqVo();
					enterReq.setUserId(reqVo.getUserId());
					UserCommonResult<EnterpriseUserResVo> result3 = enterpriseUser.getEnterpriseUser(enterReq);
					if (result3.isSuccess()) {
						reqVo.setUserName(result3.getBusinessObject().getEnterpriseName());
						if (StringUtils.isBlank(result3.getBusinessObject().getUnifiedSocialCreditCode())){
							reqVo.setCertNo(result3.getBusinessObject().getOrganizingCode());
						}else {
							reqVo.setCertNo(result3.getBusinessObject().getUnifiedSocialCreditCode());
						}
					}
				}
			}
		}
		String uuid = sequenceGenerator.getUUID();
		reqVo.setRequestId(uuid);
		reqVo.setRequestTime(new Date());
		reqVo.setIp("");

		ProtocolCommonResult<CommonResVo> result;
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), IpaperLessService.class);
		result = paperLessService.autoMakeSeal(reqVo);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), IpaperLessService.class);
		if (result.isSuccess()) {
			return Result.success(result);
		} else {
			return Result.error(result.getResultCodeMsg());
		}
	}
	
	/**
	 * ????????????
	 * 
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/moulage_record/sealUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result sealUpdate(SealReqVo reqVo) {

		String uuid = sequenceGenerator.getUUID();
		reqVo.setRequestId(uuid);
		reqVo.setRequestTime(new Date());
		reqVo.setIp("");

		ProtocolCommonResult<CommonResVo> result;
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), IpaperLessService.class);
		result = paperLessService.sealUpdate(reqVo);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), IpaperLessService.class);
		if (result.isSuccess()) {
			return Result.success(result);
		} else {
			return Result.error("??????????????????");
		}
	}
	
	/**
	 * ????????????
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/moulage_record/sealRevise", method = RequestMethod.POST)
	@ResponseBody
	public Result sealRevise(SealReqVo reqVo) {

		reqVo.setRequestTime(new Date());
		reqVo.setIp("");
		
		ProtocolCommonResult<CommonResVo> result;
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), IpaperLessService.class);
		result = paperLessService.sealInfo(reqVo);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), IpaperLessService.class);
		if (result.isSuccess()) {
			return Result.success(result);
		} else {
			return Result.error("??????????????????");
		}
	}

	/**
	 * ????????????????????????
	 * 
	 * @param reqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/contract_details_record/listOrderProtocol", method = RequestMethod.POST)
	@ResponseBody
	public Result listOrderProtocol(OrderProtocolReqVo reqVo, int pageNo, int pageSize) {
		if (StringUtils.isBlank(reqVo.getOrderId())) {
			reqVo.setOrderId(null);
		}
		ProtocolCommonResult<PageInfo<OrderProtocolResVo>> result;
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(reqVo), IOrderProtocolService.class);
		result = orderProtocol.listOrderProtocol(reqVo, pageNo, pageSize);
		logger.info("??????????????????{}??????:" + JSONObject.toJSONString(result), IOrderProtocolService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error("????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/contract_details_record/evidencePreserve", method = RequestMethod.POST)
	@ResponseBody
	public Result evidencePreserve(OrderProtocolReqVo reqVo) {
		logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(reqVo), IContractSignatureSerivce.class);
		ProtocolCommonResult<CommonResVo> result = paperLessService.evidencePreserve(reqVo);
		logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(result), IContractSignatureSerivce.class);
		if (result.isSuccess()) {
			return Result.success(result);
		} else {
			return Result.error("????????????????????????");
		}
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param reqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/contract_signing_batch_record/listSignatureBatch", method = RequestMethod.POST)
	@ResponseBody
	public Result listSignatureBatch(SignatureBatchReqVo reqVo, int pageNo, int pageSize) {
		ProtocolCommonResult<PageInfo<SignatureBatchResVo>> result;
		if (StringUtils.isBlank(reqVo.getBorrowerCode())) {
			reqVo.setBorrowerCode(null);
		}
		logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(reqVo), ISignatureBatchService.class);
		result = signatureBatch.list(reqVo, pageNo, pageSize);
		logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(result), ISignatureBatchService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error("????????????????????????????????????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/contract_signing_batch_record/signatureRetry", method = RequestMethod.POST)
	@ResponseBody
	public Result signatureRetry(SignatureRetryReqVo reqVo) {
		logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(reqVo), IContractSignatureSerivce.class);
		ProtocolCommonResult<Integer> result = contractSignature.signatureRetry(reqVo);
		logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(result), IContractSignatureSerivce.class);
		if (result.isSuccess()) {
			return Result.success(result);
		} else {
			return Result.error("????????????????????????");
		}
	}
	
	/**
	 * ????????????MOCK
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/contract_signing_batch_record/signatureMock", method = RequestMethod.POST)
	@ResponseBody
	public Result signatureMock(SignatureRetryReqVo reqVo) {
		logger.info("????????????MOCK??????{}??????:" + JSONObject.toJSONString(reqVo), IContractSignatureSerivce.class);
		ProtocolCommonResult<Integer> result = contractSignature.signatureMock(reqVo);
		logger.info("????????????MOCK??????{}??????:" + JSONObject.toJSONString(result), IContractSignatureSerivce.class);
		if (result.isSuccess()) {
			return Result.success(result);
		} else {
			return Result.error("????????????MOCK??????");
		}
	}
	
	/**
	 * ??????????????????
	 * @param reqVo
	 * @return
	 */
	@RequestMapping(value = "/contract_signing_batch_record/signatureCancel", method = RequestMethod.POST)
	@ResponseBody
	public Result signatureCancel(SignatureRetryReqVo reqVo) {
		logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(reqVo), IContractSignatureSerivce.class);
		ProtocolCommonResult<Integer> result = contractSignature.signatureCancel(reqVo);
		logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(result), IContractSignatureSerivce.class);
		if (result.isSuccess()) {
			return Result.success(result);
		} else {
			return Result.error("????????????????????????");
		}
	}

	/**
	 * ??????????????????
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/moulage_record/getUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result getUserInfo(String userId) {
			SealImageRecordResVo sealImage = new SealImageRecordResVo();
//			UserInfoReqVo userInfoReq = new UserInfoReqVo();
//			userInfoReq.setId(userId);
			UserCommonResult<UserInfoResVo> result = userInfoService.getByRoleUserId(userId);
			if (result.isSuccess()) {
				UserInfoResVo userInfoResVo = result.getBusinessObject();
				if (StringUtils.isNotEmpty(userInfoResVo.getId())) {
					sealImage.setUserId(userInfoResVo.getId());
					// ??????????????????
					if (com.winsmoney.jajaying.user.service.enums.UserType.PERSON.equals(userInfoResVo.getUserType())) {
						sealImage.setUserType(UserType.PERSON);
						sealImage.setUserName(userInfoResVo.getRealName());
						sealImage.setCertNo(userInfoResVo.getCertNo());
						sealImage.setBankMobile(userInfoResVo.getRegisterMobile());

					}
					// ??????????????????
					else if (com.winsmoney.jajaying.user.service.enums.UserType.ENTERPRISE
							.equals(userInfoResVo.getUserType()) || com.winsmoney.jajaying.user.service.enums.UserType.SYSTEM
							.equals(userInfoResVo.getUserType())) {
						EnterpriseUserReqVo enterReq = new EnterpriseUserReqVo();
						enterReq.setUserId(userId);
						UserCommonResult<EnterpriseUserResVo> result3 = enterpriseUser.getEnterpriseUser(enterReq);
						if (result3.isSuccess()) {
							sealImage.setUserType(UserType.ENTERPRISE);
							sealImage.setUserName(result3.getBusinessObject().getEnterpriseName());
							if (StringUtils.isBlank(result3.getBusinessObject().getUnifiedSocialCreditCode())){
								sealImage.setCertNo(result3.getBusinessObject().getOrganizingCode());
							}else {
								sealImage.setCertNo(result3.getBusinessObject().getUnifiedSocialCreditCode());
							}
							sealImage.setBankMobile(result3.getBusinessObject().getLegalPersonMobile());
						} else {
							return Result.error(result3.getResultCodeMsg());
						}
					}
					return Result.success(sealImage);
				} else {
					return Result.error("????????????????????????");
				}
			} else {
				return Result.error("????????????????????????");
			}
		}
	
	/**
	 * ??????????????????PDF
	 * @param requestId
	 * @return
	 */
	@RequestMapping(value = "/moulage_record/testGeneratePDF", method = RequestMethod.POST)
	@ResponseBody
	public Result testGeneratePDF(String requestId) {
		BaseReqVo baseReqVo = new BaseReqVo();
		baseReqVo.setRequestId(requestId);
		logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(baseReqVo), IProtocolTemplateService.class);
		ProtocolCommonResult<OrderProtocolResVo> result = protocolTemplateService.testGeneratePDF(baseReqVo);
		logger.info("????????????????????????{}??????:" + JSONObject.toJSONString(result), IProtocolTemplateService.class);
		if (result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		} else {
			return Result.error("????????????????????????");
		}
	}
	
	/**
	 * ??????borrowId????????????
	 * @param borrowId
	 * @return
	 */
	@RequestMapping(value = "/moulage/picture", method = RequestMethod.POST)
	@ResponseBody
	public Result getbaseInfo1(String borrowId) {
		
		try {
			List resultFinal = new ArrayList();
            logger.info("??????????????????{}??????:" + JSONObject.toJSONString(borrowId), IBorrowService.class);
            TradeCommonResult<BorrowResVo> result = borrowService.getBorrowById(borrowId);
            logger.info("??????????????????{}??????:" + JSONObject.toJSONString(borrowId), IBorrowService.class);
            //???????????????id??????????????????
			SealImageRecordReqVo reqVo = new SealImageRecordReqVo();
			reqVo.setTransStatus(TransStatus.SUCCESS);
			reqVo.setUserId(result.getBusinessObject().getBorrowUserCode());
			ProtocolCommonResult<SealImageRecordResVo> borrowerSealImageResVo = sealImageRecord.get(reqVo);
			//???????????????id??????????????????
			SealImageRecordReqVo reqVo1 = new SealImageRecordReqVo();
			reqVo1.setTransStatus(TransStatus.SUCCESS);
			reqVo1.setUserId(result.getBusinessObject().getGuaranteeUserId());
			ProtocolCommonResult<SealImageRecordResVo> guaranteeSealImageResVo = sealImageRecord.get(reqVo1);
			
			resultFinal.add(borrowerSealImageResVo.getBusinessObject());
			resultFinal.add(guaranteeSealImageResVo.getBusinessObject());
			return Result.success(resultFinal);
		} catch (Exception e) {
            logger.error("????????????????????????", e);
        }
		return Result.error("????????????????????????");
	}
	
}


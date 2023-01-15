package com.winsmoney.jajaying.boss.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.winsmoney.jajaying.user.service.enums.UserRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.pay.service.IPayService;
import com.winsmoney.jajaying.pay.service.request.UnlockSMSReqVo;
import com.winsmoney.jajaying.pay.service.response.PayCommonResult;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.IUserService;
import com.winsmoney.jajaying.user.service.IWxInfoService;
import com.winsmoney.jajaying.user.service.enums.UnlockType;
import com.winsmoney.jajaying.user.service.request.ChangeUserMobileReqVo;
import com.winsmoney.jajaying.user.service.request.UnLockUserReqVo;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.request.WxInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.jajaying.user.service.response.WxInfoResVo;

/**
 * 用户解锁、更换手机号
 *
 */
@Controller

public class UserUnlockController {
	@Autowired
	private IUserService userService;
	@Autowired
	private IPayService payservice;
	@Autowired
	private IWxInfoService wxInfoService; 
	@Autowired
	private IUserInfoService userInfoService;
	@RequestMapping(value = "/user/userunlock/smsUnBindCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> SmsUnlock(UnLockUserReqVo unLockUserReqVo) {
		Map<String, String> result = new HashMap<String, String>();
		unLockUserReqVo.setType(UnlockType.SMS);
		UserCommonResult<Boolean> userUnlockResult = userService.unLockUser(unLockUserReqVo);
		UnlockSMSReqVo unlockSMSReqVo = new UnlockSMSReqVo();
		unlockSMSReqVo.setMobile(unLockUserReqVo.getMobile());
		PayCommonResult<Boolean> payUnlockResult = payservice.unlockSMS(unlockSMSReqVo);
		if(userUnlockResult.isSuccess() && payUnlockResult.isSuccess()){
			result.put("result", "验证码解锁成功");
		}else{
			result.put("result", "验证码解锁失败");
		}
		return result;
	}

	@RequestMapping(value = "/user/userunlock/changeMobile", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新手机号")
	public Map<String, Object> changeMobile(ChangeUserMobileReqVo changeUserMobileReqVo, HttpServletRequest request) {
		Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
		changeUserMobileReqVo.setEditedBy(staffSession.getStaffName());
		Map<String, Object> result = new HashMap<String, Object>();
		UserCommonResult<Boolean> bl = userService.changeUserMobile(changeUserMobileReqVo);
		result.put("result", bl.getResultCodeMsg());
		return result;
	}
	
	
	@RequestMapping(value = "/user/weChatunbundling/weChatUnbundling", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "解绑微信")
	public String weChatUnbundling(String phone) {
//		UserInfoReqVo userInfoReqVo=new UserInfoReqVo();
//		userInfoReqVo.setRegisterMobile(phone);
		UserCommonResult<UserInfoResVo> userInfo = userInfoService.getUserInfoByName( phone , UserRoleType.INVEST.getCode() );
		String UserId = userInfo.getBusinessObject().getId();
		if(UserId==null){
			return "该手机未注册用户";
		}
		WxInfoReqVo request2=new WxInfoReqVo();
		request2.setUserId(UserId);
		UserCommonResult<WxInfoResVo> byId = wxInfoService.get(request2);
		String id = byId.getBusinessObject().getId();
		if(id==null){
			return "用户未绑定微信";
		}
		WxInfoReqVo request=new WxInfoReqVo();
		request.setId(id);
		request.setUserId("");
		UserCommonResult<Integer> update = wxInfoService.update(request);
			
		return update.getResultCodeMsg();
	}
	
	

}

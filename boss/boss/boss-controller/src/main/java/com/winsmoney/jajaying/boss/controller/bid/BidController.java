package com.winsmoney.jajaying.boss.controller.bid;

import com.winsmoney.jajaying.user.service.IUserRoleInfoService;
import com.winsmoney.jajaying.user.service.request.UserRoleInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserRoleInfoResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.trade.service.IAutoInvestSettingService;
import com.winsmoney.jajaying.trade.service.request.AutoInvestSettingReqVo;
import com.winsmoney.jajaying.trade.service.response.AutoInvestSettingResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;

@Controller
@RequestMapping("/automatic_bidding/automatic_user_list")
public class BidController {

	@Autowired
	private IAutoInvestSettingService autoInvestSettingService;
	
	@Autowired
	private IUserRoleInfoService userRoleInfoService;
		 
	
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<AutoInvestSettingResVo> list(AutoInvestSettingReqVo autoInvestRecordReqVo,UserInfoReqVo userInfoReqVo,int pageNo, int pageSize){
		TradeCommonResult<PageInfo<AutoInvestSettingResVo>> list = autoInvestSettingService.list(autoInvestRecordReqVo, pageNo, pageSize);
		for (int i = 0; i < list.getBusinessObject().getList().size(); i++) {
			UserRoleInfoReqVo userRoleInfoReqVo = new UserRoleInfoReqVo();
			userRoleInfoReqVo.setUserRoleId( list.getBusinessObject().getList().get(i).getUserId() );
			UserCommonResult<UserRoleInfoResVo> listUserInfo = userRoleInfoService.get( userRoleInfoReqVo ) ;
			if(listUserInfo.getBusinessObject().getAuthState() != null){
				if(listUserInfo.getBusinessObject().getAuthState().getCode().equals("BANKCARD")){
					list.getBusinessObject().getList().get(i).setStatus("ON");
				}else{
					autoInvestRecordReqVo.setId(list.getBusinessObject().getList().get(i).getId());
					list.getBusinessObject().getList().get(i).setStatus("OFF");
					autoInvestRecordReqVo.setStatus("OFF");
					TradeCommonResult<AutoInvestSettingResVo> update1 = autoInvestSettingService.update(autoInvestRecordReqVo);
				}
			}else{
				list.getBusinessObject().getList().get(i).setStatus("OFF");
				autoInvestRecordReqVo.setId(list.getBusinessObject().getList().get(i).getId());
				autoInvestRecordReqVo.setStatus("OFF");
				TradeCommonResult<AutoInvestSettingResVo> update2 = autoInvestSettingService.update(autoInvestRecordReqVo);
			}
			}	
		return list.getBusinessObject();
	}
}

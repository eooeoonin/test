package com.winsmoney.jajaying.boss.controller.bid;

import java.awt.List;
import java.util.ArrayList;
import java.util.Date;

import com.winsmoney.jajaying.user.service.enums.UserRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.controller.utils.BeanMapper;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.trade.service.IAutoInvestSettingService;
import com.winsmoney.jajaying.trade.service.enums.AutoInvestEnableStatus;
import com.winsmoney.jajaying.trade.service.request.AutoInvestSettingReqVo;
import com.winsmoney.jajaying.trade.service.response.AutoInvestSettingResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
/**
 * 黑名单
 * @author Moon
 *
 */
@Controller
@RequestMapping("/automatic_bidding/blacklist_list")
public class BlacklistController {

	@Autowired
	private IAutoInvestSettingService autoInvestSettingService;
	
	@Autowired
	private IUserInfoService userInfoService;
		 
	/**
	 * 黑名单列表
	 * @param autoInvestRecordReqVo
	 * @param userInfoReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ArrayList<BlacklistReqForm> list(int pageNo, int pageSize){
		AutoInvestSettingReqVo request2=new AutoInvestSettingReqVo();
		request2.setEnable(AutoInvestEnableStatus.OFF);//禁用
		TradeCommonResult<PageInfo<AutoInvestSettingResVo>> list = autoInvestSettingService.list (request2,pageNo,pageSize);
		ArrayList<BlacklistReqForm> list2 =new ArrayList<>();
		
		for (int i = 0; i <list.getBusinessObject().getSize();i++) {
			BlacklistReqForm blacklistReqForm=new BlacklistReqForm();
			
			blacklistReqForm.setPageNum(list.getBusinessObject().getPageNum());
			blacklistReqForm.setPages(list.getBusinessObject().getPages());
			blacklistReqForm.setPageSize(list.getBusinessObject().getPageSize());
			blacklistReqForm.setSize(list.getBusinessObject().getSize());
			blacklistReqForm.setTotal(list.getBusinessObject().getTotal());
			
			String userId = list.getBusinessObject().getList().get(i).getUserId();
			Date enableTime = list.getBusinessObject().getList().get(i).getEnableTime();//创建时间
//			UserInfoReqVo userInfoReqVo= new UserInfoReqVo();
//			userInfoReqVo.setId(userId);
			UserCommonResult<UserInfoResVo> userInfo = userInfoService.getByRoleUserId( userId );
			String registerMobile = userInfo.getBusinessObject().getRegisterMobile();//手机号
			
			
			blacklistReqForm.setBlackCreateTime(enableTime);
			blacklistReqForm.setRegisterMobile(registerMobile);
			
			list2.add(i, blacklistReqForm);
		}
		
/*		PageInfo<BlacklistReqForm> newPage = new PageInfo<BlacklistReqForm>();
		newPage = BeanMapper.map(page, PageInfo.class);
		newPage.setList(list2);
		
//		list.getBusinessObject().getPageSize();
//		int pageNum = list.getBusinessObject().getPageNum();
	*/
		
		return list2;
	}
	
	

	
	/**
	 * 添加黑名单
	 */
	@RequestMapping("/addBlacklist")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加黑名单")
	public String  add(String mobile){
//		UserInfoReqVo userInfoReqVo= new UserInfoReqVo();
//		userInfoReqVo.setRegisterMobile(mobile);
		UserCommonResult<UserInfoResVo> userInfo = userInfoService.getUserInfoByName(mobile, UserRoleType.INVEST.getCode());
		String UserId = userInfo.getBusinessObject().getId();
		
		if(UserId==null){
			return  "该手机号没有注册";
		}
		AutoInvestSettingReqVo request=new AutoInvestSettingReqVo();
		request.setUserId(UserId);
		TradeCommonResult<AutoInvestSettingResVo> tradeCommonResult = autoInvestSettingService.get(request);
		String autoId = tradeCommonResult.getBusinessObject().getId();
		
		AutoInvestSettingReqVo request2=new AutoInvestSettingReqVo();
		request2.setId(autoId);
		request2.setEnable(AutoInvestEnableStatus.OFF);//禁用
		request2.setEnableTime(new Date());
		TradeCommonResult<AutoInvestSettingResVo> update = autoInvestSettingService.update(request2);
		
		return update.getResultCodeMsg();
	}
	
}

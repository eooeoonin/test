package com.winsmoney.jajaying.boss.controller.basedata;


import java.util.List;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.ICardBinService;
import com.winsmoney.jajaying.basedata.service.request.CardBinReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.CardBinResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.msgcenter.service.IChannelService;
import com.winsmoney.jajaying.msgcenter.service.ISmsAgencyService;
import com.winsmoney.jajaying.msgcenter.service.request.ChannelReqVo;
import com.winsmoney.jajaying.msgcenter.service.request.SmsAgencyReqVo;
import com.winsmoney.jajaying.msgcenter.service.response.ChannelResVo;
import com.winsmoney.jajaying.msgcenter.service.response.MessageCommonResult;
import com.winsmoney.jajaying.msgcenter.service.response.SmsAgencyResVo;
/**
 * 短信通道管理 增删改查
 * @author Moon
 *
 */
@Controller
@RequestMapping("/basedata_mgt/sms_channel_list")
public class SMSChannelController {
	@Autowired
	private IChannelService   iChannelService ;
	 /**
     * 根据id查询 短信通道管理
     * @param id
     * @return CardBinResVo
     */
	@RequestMapping(value="/channel/ById")
	@ResponseBody
	public ChannelResVo getById(ChannelReqVo channelReqVo){
		 channelReqVo.setOpen(-1);
		 MessageCommonResult<ChannelResVo> channel = iChannelService.getChannel(channelReqVo);
		 ChannelResVo businessObject = channel.getBusinessObject();
		return  businessObject;
	}
	
	/**
     * 分页列表 短信通道管理
     * @param cardBinReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
	@RequestMapping(value="/channel/page")
	@ResponseBody
	public PageInfo<ChannelResVo> listChannel(ChannelReqVo channelReqVo, int pageNo, int pageSize){
		channelReqVo.setOpen(-1);
			
		MessageCommonResult<PageInfo<ChannelResVo>> list = iChannelService.listChannelPage(channelReqVo, pageNo, pageSize);
		PageInfo<ChannelResVo> businessObject = list.getBusinessObject();
		return businessObject;
	}
	 /**
     * 添加  短信通道管理
     * @param cardBinReqVo
     * @return Integer
     */		
	@RequestMapping(value="/channel/add")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加  短信通道管理")
	public Integer insertChannel(ChannelReqVo channelReqVo,HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		channelReqVo.setEditedBy(role);
				
		MessageCommonResult<Integer> insert = iChannelService.insertChannel(channelReqVo);
		
			 	return insert.getBusinessObject();
	}
	/**
     * 更新 短信通道管理
     * @param cardBinReqVo
     * @return
     */
	@RequestMapping(value="/channel/edit")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新  短信通道管理")
	public Integer updateChannel(ChannelReqVo channelReqVo,HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		channelReqVo.setEditedBy(role);
		MessageCommonResult<Integer> update = iChannelService.updateChannel(channelReqVo);
		return update.getBusinessObject();
	}	
	/**
     * 删除 短信通道管理
     * @param id
     * @return
     */	
	@RequestMapping(value="/channel/delete")
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除  短信通道管理")
	public Integer deleteChannel(String id){
		MessageCommonResult<Integer> delete = iChannelService.deleteChannel(id);
		return delete.getBusinessObject();
	}
}

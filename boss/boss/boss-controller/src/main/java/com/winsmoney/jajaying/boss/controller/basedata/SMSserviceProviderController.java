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
import com.winsmoney.jajaying.msgcenter.service.ISmsAgencyService;
import com.winsmoney.jajaying.msgcenter.service.request.SmsAgencyReqVo;
import com.winsmoney.jajaying.msgcenter.service.response.MessageCommonResult;
import com.winsmoney.jajaying.msgcenter.service.response.SmsAgencyResVo;
/**
 * 短信服务商管理 增删改查
 * @author Moon
 *
 */
@Controller
@RequestMapping("/basedata_mgt/sms_service_provider_list")
public class SMSserviceProviderController {
	@Autowired
	private ISmsAgencyService iSmsAgencyService;
	 /**
     * 根据id查询 短信提供商表
     * @param id
     * @return CardBinResVo
     */
	@RequestMapping(value="/Agency/ById")
	@ResponseBody
	public SmsAgencyResVo getById(@RequestParam("id")String id){
			MessageCommonResult<SmsAgencyResVo> byId = iSmsAgencyService.getById(id);
		SmsAgencyResVo businessObject = byId.getBusinessObject();
		
		return  businessObject;
	}
	
	/**
     * 分页列表 短信提供商表
     * @param cardBinReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
	@RequestMapping(value="/Agency/page")
	@ResponseBody
	public PageInfo<SmsAgencyResVo> listAgency(SmsAgencyReqVo smsAgencyReqVo, int pageNo, int pageSize){
			
		MessageCommonResult<PageInfo<SmsAgencyResVo>> list = iSmsAgencyService.list(smsAgencyReqVo, 1, 10);
		PageInfo<SmsAgencyResVo> businessObject = list.getBusinessObject();
		return businessObject;
	}
	 /**
     * 添加  短信提供商表
     * @param cardBinReqVo
     * @return Integer
     */		
	@RequestMapping(value="/Agency/add")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加  短信提供商表")
	public Integer insertAgency(SmsAgencyReqVo smsAgencyReqVo,HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		smsAgencyReqVo.setEditedBy(role);
				
		MessageCommonResult<Integer> insert = iSmsAgencyService.insert(smsAgencyReqVo);
		
			 	return insert.getBusinessObject();
	}
	/**
     * 更新 短信提供商表
     * @param cardBinReqVo
     * @return
     */
	@RequestMapping(value="/Agency/edit")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新  短信提供商表")
	public Integer updateAgency(SmsAgencyReqVo smsAgencyReqVo,HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		smsAgencyReqVo.setEditedBy(role);
		MessageCommonResult<Integer> update = iSmsAgencyService.update(smsAgencyReqVo);
		return update.getBusinessObject();
	}	
	/**
     * 删除 短信提供商表
     * @param id
     * @return
     */	
	@RequestMapping(value="/Agency/delete")
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除  短信提供商表")
	public Integer deleteAgency(String id){
		MessageCommonResult<Integer> delete = iSmsAgencyService.delete(id);
		return delete.getBusinessObject();
	}
}

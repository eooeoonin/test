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
/**
 * 卡bin增删改查
 * @author Moon
 *
 */
@Controller
@RequestMapping("/basedata_mgt/b_card_bin_list")
public class ICardBinController {
	@Autowired
	private ICardBinService iCardBinService;
	 /**
     * 单笔查询 卡bin信息 
     * @param id
     * @return CardBinResVo
     */
	@RequestMapping(value="/CardBin/one")
	@ResponseBody
	public String getById(@RequestParam("id")String id){
			BasedataCommonResult<CardBinResVo> byId = iCardBinService.getById(id);
			CardBinResVo businessObject = byId.getBusinessObject();
		//System.out.println(JSON.toJSONString(businessObject));
		return  JSON.toJSONString(businessObject);
	}
	 /**
     * 统计 卡bin信息
     * @param cardBinReqVo
     * @return Integer
     */
	@RequestMapping(value="/CardBin/list",method=RequestMethod.POST)
	@ResponseBody
	public Integer countCardBin(CardBinReqVo cardBinReqVo){
			BasedataCommonResult<Integer> countCardBin = iCardBinService.countCardBin(cardBinReqVo);
			//System.out.println(countCardBin);
			return countCardBin.getBusinessObject();
	}
	/**
     * 分页列表卡bin信息
     * @param cardBinReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
	@RequestMapping(value="/CardBin/page")
	@ResponseBody
	public String listCardBin(CardBinReqVo cardBinReqVo, Integer pageNo, Integer pageSize){
			
			BasedataCommonResult<PageInfo<CardBinResVo>> listCardBin = iCardBinService.listCardBin(cardBinReqVo,pageNo, pageSize);
			List<CardBinResVo> list = listCardBin.getBusinessObject().getList();
			
			return JSON.toJSONString(list);
	}
	 /**
     * 添加 卡bin信息
     * @param cardBinReqVo
     * @return Integer
     */		
	@RequestMapping(value="/CardBin/add")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加 卡bin信息")
	public BasedataCommonResult<Integer> insertCardBin(CardBinReqVo cardBinReqVo,HttpSession httpSession,HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		cardBinReqVo.setEditedBy(role);
				
		BasedataCommonResult<Integer> insertCardBin = iCardBinService.insertCardBin(cardBinReqVo);
		//System.out.println(insertCardBin);
			 	return insertCardBin;
	}
	/**
     * 更新 卡bin信息
     * @param cardBinReqVo
     * @return
     */
	@RequestMapping(value="/CardBin/edit")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新 卡bin信息")
	public BasedataCommonResult<Integer> updateCardBin(CardBinReqVo cardBinReqVo,HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		cardBinReqVo.setEditedBy(role);
			 BasedataCommonResult<Integer> insertCardBin = iCardBinService.updateCardBin(cardBinReqVo);
			// System.out.println(cardBinReqVo);
			 //System.out.println(JSON.toJSONString(cardBinReqVo));	
			 return insertCardBin;
	}	
	/**
     * 删除 卡bin信息
     * @param id
     * @return
     */	
	@RequestMapping(value="/CardBin/delete")
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除 卡bin信息")
	public BasedataCommonResult<Integer> deleteCardBin(String id){
			 BasedataCommonResult<Integer> insertCardBin = iCardBinService.deleteCardBin(id);
			// System.out.println(insertCardBin);	
			 	return insertCardBin;
	}
}

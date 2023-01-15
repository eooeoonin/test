package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.INoticeService;
import com.winsmoney.jajaying.basedata.service.request.NoticeReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.NoticeResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.msgcenter.service.ISpeInnerTemplateService;
import com.winsmoney.jajaying.msgcenter.service.enums.InnerMessageType;
import com.winsmoney.jajaying.msgcenter.service.request.SpeInnerTemplateReqVo;
import com.winsmoney.jajaying.msgcenter.service.response.MessageCommonResult;
import com.winsmoney.jajaying.msgcenter.service.response.SpeInnerTemplateResVo;

/**
 * 群发模板
 * @author Moon
 *
 */
@Controller
@RequestMapping("/basedata_mgt/masstemplate")
public class MasstemplateController {

	@Autowired
	private ISpeInnerTemplateService iSpeInnerTemplateService ;
	
	/**
	    * 根据id查询 
	    * @param noticeReqVo
	    * @return NoticeResVo
	    */
		@RequestMapping("/one")
		@ResponseBody
		public  SpeInnerTemplateResVo getById(String id){
			
			MessageCommonResult<SpeInnerTemplateResVo> byId = iSpeInnerTemplateService.getById(id);
			
			
			return byId.getBusinessObject();
		}
	
	/**
	 * 添加
	 */
	
	@RequestMapping("/add")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增群发模板")
	public MessageCommonResult<Integer> insert(SpeInnerTemplateReqVo speInnerTemplateReqVo, HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		
		speInnerTemplateReqVo.setEditedBy(role);
		speInnerTemplateReqVo.setType(InnerMessageType.SPCINNERTEMPLATE);//类型
		speInnerTemplateReqVo.setMerchant("P2P");//商户
		MessageCommonResult<Integer> insert = iSpeInnerTemplateService.insert(speInnerTemplateReqVo);
		
		return insert;
	}
	
	/**
	 * 更新
	 */
	
	@RequestMapping("/update")
	@ResponseBody	
	@AduitLog(type = OperType.UPDATE, content = "更新群发模板")
	public MessageCommonResult<Integer> update(SpeInnerTemplateReqVo speInnerTemplateReqVo, HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		
		speInnerTemplateReqVo.setEditedBy(role);
		speInnerTemplateReqVo.setType(InnerMessageType.SPCINNERTEMPLATE);//类型
		speInnerTemplateReqVo.setMerchant("P2P");//商户
		MessageCommonResult<Integer> update = iSpeInnerTemplateService.update(speInnerTemplateReqVo);
		
		return update;
	}
	
	/**
	 * 
	 * 列表显示
	 */
	@RequestMapping("/page")
	@ResponseBody
    public  PageInfo<SpeInnerTemplateResVo> list(SpeInnerTemplateReqVo speInnerTemplateReqVo, int pageNo, int pageSize){

		MessageCommonResult<PageInfo<SpeInnerTemplateResVo>> list = iSpeInnerTemplateService.list(speInnerTemplateReqVo, pageNo, pageSize);
		PageInfo<SpeInnerTemplateResVo> businessObject = list.getBusinessObject();
		
		return  businessObject;
	}
}

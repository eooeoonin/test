package com.winsmoney.jajaying.boss.controller.basedata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IProtocolService;
import com.winsmoney.jajaying.basedata.service.request.EventValueMapReqVo;
import com.winsmoney.jajaying.basedata.service.request.ProtocolReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.EventValueMapResVo;
import com.winsmoney.jajaying.basedata.service.response.ProtocolResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
/**
 * 
 * 协议列表
 * @author Moon
 *
 */
@Controller
@RequestMapping("/basedata_mgt/iprotol_list")
public class IprotocolController {
	
	@Autowired
	private IProtocolService iProtocolService;
	
	
	
	
	
	
	/**
	 * 分页列表展示
	 * @param protocolReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<ProtocolResVo> selectIProtocol(ProtocolReqVo protocolReqVo,int pageNo,int pageSize){
		 BasedataCommonResult<PageInfo<ProtocolResVo>> list = iProtocolService.list(protocolReqVo, pageNo, pageSize);
		 PageInfo<ProtocolResVo> eventvaluemapList = new PageInfo<ProtocolResVo>();
		 eventvaluemapList = list.getBusinessObject();
		return eventvaluemapList;
	}
	
	
	/**
	 * 添加
	 * @param protocolReqVo
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增协议")
	public Integer insertIsystemConfig(ProtocolReqVo protocolReqVo){
		
		
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");       
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">").     
             append("<head>")       
            .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")     
            .append("<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;}</style>")      
            .append("</head>")       
            .append("<body>");
        html.append(protocolReqVo.getTemplate());
        html.append("</body></html>");  
        protocolReqVo.setTemplate(html.toString());
		
		BasedataCommonResult<Integer> insert = iProtocolService.insert(protocolReqVo);
		return insert.getBusinessObject();
	}
	
	/**
	 * 删除
	 * @param sid
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除协议")
	public Integer deleteIsystemConfig(String pid){
		BasedataCommonResult<Integer> delete = iProtocolService.delete(pid);
	    return delete.getBusinessObject();
	}
	
	/**
	 * 回显
	 * @param id
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public ProtocolResVo selectIsystemConfig(String id){
		 BasedataCommonResult<ProtocolResVo> getById = iProtocolService.getById(id);
		return getById.getBusinessObject();
	}
	
	
	@RequestMapping("/update")
	@ResponseBody
	public Integer updateIsystemCongfig(ProtocolReqVo protocolReqVo){
		 BasedataCommonResult<Integer> update = iProtocolService.update(protocolReqVo);
		 update.getBusinessObject();
		return update.getBusinessObject();
	}
	
	
}

package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IErrorCodeMapService;
import com.winsmoney.jajaying.basedata.service.IErrorCodeService;
import com.winsmoney.jajaying.basedata.service.request.ErrorCodeMapReqVo;
import com.winsmoney.jajaying.basedata.service.request.ErrorCodeReqVo;
import com.winsmoney.jajaying.basedata.service.response.ErrorCodeMapResVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.ErrorCodeResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.Result;

/**
 * 错误码关系管理
 *
 */
@Controller
@RequestMapping(value="/basedata_mgt/errorcodemap_manage")
public class ErrorCodeMapManageController {
	
	@Autowired
	private IErrorCodeMapService  errorCodeServiceRemote;
	@Autowired
	private IErrorCodeService  errorCodeService;
	
	@RequestMapping(value="getErrorCodeBySelected", method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<ErrorCodeMapResVo> getErrorCodeBySelected(ErrorCodeMapReqVo errorCodeMapReqVo, String pageNo, String pageSize) throws Exception{
		if(StringUtils.isBlank(errorCodeMapReqVo.getSystemCode())){
			errorCodeMapReqVo.setSystemCode(null);
		}
		if(StringUtils.isBlank(errorCodeMapReqVo.getSourceCode())){
			errorCodeMapReqVo.setSourceCode(null);
		}
		if(StringUtils.isBlank(errorCodeMapReqVo.getTargetCode())){
			errorCodeMapReqVo.setTargetCode(null);
		}
		
		BasedataCommonResult<PageInfo<ErrorCodeMapResVo>> errorCodeMapResVo = errorCodeServiceRemote.listErrorCodeMap(errorCodeMapReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		
		PageInfo<ErrorCodeMapResVo> errorCodeList = new PageInfo<ErrorCodeMapResVo>();
		if(errorCodeMapResVo.isSuccess()) {
			errorCodeList  = errorCodeMapResVo.getBusinessObject();
		}
		return errorCodeList;  
	}
	
	
	@RequestMapping(value="getErrorCodeList", method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<ErrorCodeMapResVo> getErrorCodeList(String pageNo,String pageSize) throws Exception{	
		PageInfo<ErrorCodeMapResVo> errorCodeList = null;
		ErrorCodeMapReqVo errorCodeMapReqVo = new ErrorCodeMapReqVo();
		BasedataCommonResult<PageInfo<ErrorCodeMapResVo>> errorCodeMapResVo = errorCodeServiceRemote.listErrorCodeMap(errorCodeMapReqVo,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		if(errorCodeMapResVo.isSuccess()) {
			errorCodeList = errorCodeMapResVo.getBusinessObject();
		}
		return errorCodeList; 
	}
	
	@RequestMapping(value="getErrorCodeById", method=RequestMethod.POST)
	@ResponseBody
	public ErrorCodeMapResVo getErrorCodeById(String id) throws Exception{	
		ErrorCodeMapReqVo errorCodeMapReqVo = new ErrorCodeMapReqVo();
		errorCodeMapReqVo.setId(id);
		BasedataCommonResult<ErrorCodeMapResVo> errorCodeMapResVo = errorCodeServiceRemote.getErrorCodeMap(errorCodeMapReqVo);
		return errorCodeMapResVo.getBusinessObject(); 
	}
	
	@RequestMapping(value="updateErrorCode", method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新错误码")
	public Map<String, Object> updateErrorCode(ErrorCodeMapReqVo errorCodeMapReqVo,HttpServletRequest httpRequest) throws Exception{
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		errorCodeMapReqVo.setEditedBy(role);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		BasedataCommonResult<Integer> errorCodeMapResVo = errorCodeServiceRemote.updateErrorCodeMap(errorCodeMapReqVo);	
		resultMap.put("result", errorCodeMapResVo.getResultCodeMsg());  
		return resultMap;
	}
	
	
	@RequestMapping(value="addErrorCode", method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增错误码")
	public Result addErrorCode(ErrorCodeMapReqVo errorCodeMapReqVo,HttpServletRequest httpRequest) throws Exception {
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		errorCodeMapReqVo.setEditedBy(role);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BasedataCommonResult<Integer> errorCodeMapResVo = errorCodeServiceRemote
				.insertErrorCodeMap(errorCodeMapReqVo );
		if(errorCodeMapResVo.isSuccess()) {
			return Result.success(errorCodeMapResVo.getBusinessObject());
		}
		else
			return Result.error(errorCodeMapResVo.getResultCodeMsg());
	}
	
	
	@RequestMapping(value="errorCodeDelete", method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除错误码")
	public Map<String, Object> errorCodeDelete(String id) throws Exception{	
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BasedataCommonResult<Integer> errorCodeMapResVo = errorCodeServiceRemote.deleteErrorCodeMap(id);
		resultMap.put("result", errorCodeMapResVo.getResultCodeMsg());
		return resultMap; 
	}
		
	@RequestMapping(value="getErrorCode", method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<ErrorCodeResVo> getErrorCode(ErrorCodeReqVo errorCodeReqVo, String pageNo, String pageSize) throws Exception{	
		if(StringUtils.isBlank(errorCodeReqVo.getCode()))
			errorCodeReqVo.setCode(null);
		if(StringUtils.isBlank(errorCodeReqVo.getSystemCode()))
			errorCodeReqVo.setSystemCode(null);
		BasedataCommonResult<PageInfo<ErrorCodeResVo>> errorCodeResVo = errorCodeService.listErrorCode(errorCodeReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		return errorCodeResVo.getBusinessObject(); 
	}
}

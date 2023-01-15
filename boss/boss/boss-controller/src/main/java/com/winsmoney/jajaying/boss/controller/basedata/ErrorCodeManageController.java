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
import com.winsmoney.jajaying.basedata.service.IErrorCodeService;
import com.winsmoney.jajaying.basedata.service.request.ErrorCodeReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.ErrorCodeResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

/**
 * 错误码管理
 *
 */
@Controller
@RequestMapping(value = "/basedata_mgt/errorcode_manage")
public class ErrorCodeManageController {

	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ErrorCodeManageController.class);
	
	@Autowired
	private IErrorCodeService errorCodeService;

	@RequestMapping(value = "getErrorCodeBySelected", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<ErrorCodeResVo> getErrorCodeBySelected(
			ErrorCodeReqVo errorCodeReqVo, String pageNo, String pageSize)
			throws Exception {
		if (StringUtils.isBlank(errorCodeReqVo.getSystemCode())) {
			errorCodeReqVo.setSystemCode(null);
		}
		if (StringUtils.isBlank(errorCodeReqVo.getCode())) {
			errorCodeReqVo.setCode(null);
		}
		BasedataCommonResult<PageInfo<ErrorCodeResVo>> errorCodeResVo = errorCodeService
				.listErrorCode(errorCodeReqVo, Integer.parseInt(pageNo),
						Integer.parseInt(pageSize));

		PageInfo<ErrorCodeResVo> errorCodeList = new PageInfo<ErrorCodeResVo>();
		if (errorCodeResVo.isSuccess()) {
			errorCodeList = errorCodeResVo.getBusinessObject();
		}
		return errorCodeList;
	}
	
	@RequestMapping(value="addErrorCode", method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增错误码")
	public Map<String, Object> addErrorCode(ErrorCodeReqVo errorCodeReqVo,HttpServletRequest httpRequest) throws Exception {
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		errorCodeReqVo.setEditedBy(role);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();	
		BasedataCommonResult<Integer> errorCodeResVo = errorCodeService.insertErrorCode(errorCodeReqVo);
			resultMap.put("result", errorCodeResVo.getResultCodeMsg());
		return resultMap;
	}
	
	@RequestMapping(value="getErrorCodeById", method=RequestMethod.POST)
	@ResponseBody
	public ErrorCodeResVo getErrorCodeById(String id) throws Exception{
		
		ErrorCodeReqVo errorCodeReqVo = new ErrorCodeReqVo();
		errorCodeReqVo.setId(id);
		BasedataCommonResult<ErrorCodeResVo> errorCodeResVo = errorCodeService.getErrorCode(errorCodeReqVo);
		return errorCodeResVo.getBusinessObject(); 
	}

	@RequestMapping(value="errorCodeDelete", method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除错误码")
	public Map<String, Object> errorCodeDelete(String id) throws Exception{	
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BasedataCommonResult<Integer> errorCodeResVo = errorCodeService.deleteErrorCode(id);
		resultMap.put("result", errorCodeResVo.getResultCodeMsg());
		logger.info("/basedata_mgt/errorcode_manage/errorCodeDelete-错误码删除结果-{}", new String(errorCodeResVo.getResultCodeMsg()));
		return resultMap; 
	}
	
	@RequestMapping(value="updateErrorCode", method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新错误码")
	public Map<String, Object> updateErrorCode(ErrorCodeReqVo errorCodeReqVo,HttpServletRequest httpRequest) throws Exception{
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		errorCodeReqVo.setEditedBy(role);
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		BasedataCommonResult<Integer> errorCodeResVo = errorCodeService.updateErrorCode(errorCodeReqVo);
		resultMap.put("result",errorCodeResVo.getResultCodeMsg());  
		logger.info("/basedata_mgt/errorcode_manage/updateErrorCode-错误码编辑结果-{}", new String(errorCodeResVo.getResultCodeMsg()));
		return resultMap;
	}
}

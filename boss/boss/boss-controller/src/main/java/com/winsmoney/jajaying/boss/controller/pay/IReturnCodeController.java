package com.winsmoney.jajaying.boss.controller.pay;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.pay.service.IReturnCodeService;
import com.winsmoney.jajaying.pay.service.request.ReturnCodeReqVo;
import com.winsmoney.jajaying.pay.service.response.PayCommonResult;
import com.winsmoney.jajaying.pay.service.response.ReturnCodeResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
@Controller
@RequestMapping(value="/pay/returnCode")
public class IReturnCodeController {
	
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(IReturnCodeController.class);

	@Autowired
	private IReturnCodeService returnCode;
	 /**
     * 分页列表
     */	
	@RequestMapping(value="listReturnCode",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<ReturnCodeResVo> listReturnCode(ReturnCodeReqVo request, String pageNo, String pageSize){		
		if("".equals(request.getChannelCode())){
			request.setChannelCode(null);			
		}
		if("".equals(request.getChannelName())){
			request.setChannelName(null);
		}
		PayCommonResult<PageInfo<ReturnCodeResVo>> result = returnCode.list(request,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		return result.getBusinessObject();
	}
	/**
     * 添加
     */
	@RequestMapping(value="insertReturnCode",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加返回码")
	public Integer insertReturnCode(ReturnCodeReqVo request,HttpServletRequest httrequest){
		Staff staffSession =  (Staff)httrequest.getSession().getAttribute("adminInfo");
		request.setEditedBy(staffSession.getStaffName());
		logger.info("接口{}入参：" + JSONObject.toJSONString(request),IReturnCodeService.class);
		PayCommonResult<Integer> result = returnCode.insert(request);
		logger.info("接口{}出参：" + JSONObject.toJSONString(result),IReturnCodeService.class);
		return result.getBusinessObject();
	}
    /**
     * 更新
     */
	@RequestMapping(value="updateReturnCode",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新返回码")
	public Integer updateReturnCode(ReturnCodeReqVo request,HttpServletRequest httrequest){
		Staff staffSession =  (Staff)httrequest.getSession().getAttribute("adminInfo");
		request.setEditedBy(staffSession.getStaffName());
		PayCommonResult<Integer> result = returnCode.update(request);
		
		return result.getBusinessObject();
	}
    /**
     * 删除
     */
	@RequestMapping(value="deleteReturnCode",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除返回码")
	public Integer deleteReturnCode(String id){
		
		PayCommonResult<Integer> result = returnCode.delete(id);
		
		return result.getBusinessObject();
	}
    /**
     * 单笔查询
     */
	@RequestMapping(value="seletByIdReturnCode",method=RequestMethod.POST)
	@ResponseBody
	public ReturnCodeResVo seletByIdReturnCode(ReturnCodeReqVo request){
		
		PayCommonResult<ReturnCodeResVo> result = returnCode.get(request);
		
		return result.getBusinessObject();
	}
    

}

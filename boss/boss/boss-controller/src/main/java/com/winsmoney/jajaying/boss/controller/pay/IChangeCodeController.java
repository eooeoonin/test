package com.winsmoney.jajaying.boss.controller.pay;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.pay.service.IChangeCodeService;
import com.winsmoney.jajaying.pay.service.request.ChangeCodeReqVo;
import com.winsmoney.jajaying.pay.service.response.ChangeCodeResVo;
import com.winsmoney.jajaying.pay.service.response.PayCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

@Controller
@RequestMapping(value="/pay/changeCode")
public class IChangeCodeController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(IChangeCodeController.class);
	@Autowired
	private IChangeCodeService changeCode;
	 /**
     * 分页列表
     */	
	@RequestMapping(value="listChangeCode",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<ChangeCodeResVo> listReturnCode(ChangeCodeReqVo request, String pageNo, String pageSize){
		if("".equals(request.getChannelCode())){
			request.setChannelCode(null);
		}
		PayCommonResult<PageInfo<ChangeCodeResVo>> result = changeCode.list(request,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		return result.getBusinessObject();
	}
	/**
     * 添加
     */
	@RequestMapping(value="insertChangeCode",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加转换码")
	public Integer insertReturnCode(ChangeCodeReqVo request){
		logger.info("接口{}入参：" + JSONObject.toJSONString(request),IChangeCodeService.class);
		PayCommonResult<Integer> result = changeCode.insert(request);
		logger.info("接口{}出参：" + JSONObject.toJSONString(result),IChangeCodeService.class);
		return result.getBusinessObject();
	}
    /**
     * 更新
     */
	@RequestMapping(value="updateChangeCode",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新转换码")
	public Integer updateReturnCode(ChangeCodeReqVo request){
		PayCommonResult<Integer> result = changeCode.update(request);
		return result.getBusinessObject();
	}
    /**
     * 删除
     */
	@RequestMapping(value="deleteChangeCode",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除转换码")
	public Integer deleteReturnCode(String id){
		
		PayCommonResult<Integer> result = changeCode.delete(id);
		
		return result.getBusinessObject();
	}
    /**
     * 单笔查询
     */
	@RequestMapping(value="seletByIdChangeCode",method=RequestMethod.POST)
	@ResponseBody
	public ChangeCodeResVo seletByIdReturnCode(ChangeCodeReqVo request){
		
		PayCommonResult<ChangeCodeResVo> result = changeCode.get(request);
		
		return result.getBusinessObject();
	}
    
}

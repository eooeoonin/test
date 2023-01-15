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
import com.winsmoney.jajaying.pay.service.IChannelConfigService;
import com.winsmoney.jajaying.pay.service.request.ChannelConfigReqVo;
import com.winsmoney.jajaying.pay.service.response.ChannelConfigResVo;
import com.winsmoney.jajaying.pay.service.response.PayCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

@Controller
@RequestMapping(value="/pay/channelConfig")
public class IChannelConfigController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(IChannelConfigController.class);
	@Autowired
	private IChannelConfigService channelConfig;
	 /**
     * 分页列表
     */	
	@RequestMapping(value="listChannelConfig",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<ChannelConfigResVo> listChannelConfig(ChannelConfigReqVo request, String pageNo, String pageSize){
		PayCommonResult<PageInfo<ChannelConfigResVo>> result = channelConfig.list(request,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		return result.getBusinessObject();
	}
	/**
     * 添加
     */
	@RequestMapping(value="insertChannelConfig",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加渠道")
	public Integer insertChannelConfig(ChannelConfigReqVo request){
		logger.info("接口{}入参：" + JSONObject.toJSONString(request),IChannelConfigService.class);
		PayCommonResult<Integer> result = channelConfig.insert(request);
		logger.info("接口{}出参：" + JSONObject.toJSONString(result),IChannelConfigService.class);
		return result.getBusinessObject();
	}
    /**
     * 更新
     */
	@RequestMapping(value="updateChannelConfig",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新渠道")
	public Integer updateChannelConfig(ChannelConfigReqVo request){
		PayCommonResult<Integer> result = channelConfig.update(request);
		return result.getBusinessObject();
	}
    /**
     * 删除
     */
	@RequestMapping(value="deleteChannelConfig",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除渠道")
	public Integer deleteChannelConfig(String id){
		
		PayCommonResult<Integer> result = channelConfig.delete(id);
		
		return result.getBusinessObject();
	}
    /**
     * 单笔查询
     */
	@RequestMapping(value="seletByIdChannelConfig",method=RequestMethod.POST)
	@ResponseBody
	public ChannelConfigResVo seletByIdChannelConfig(ChannelConfigReqVo request){
		
		PayCommonResult<ChannelConfigResVo> result = channelConfig.get(request);
		
		return result.getBusinessObject();
	}

}

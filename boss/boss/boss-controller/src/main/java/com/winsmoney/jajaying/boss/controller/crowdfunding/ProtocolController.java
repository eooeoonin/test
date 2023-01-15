package com.winsmoney.jajaying.boss.controller.crowdfunding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.crowdfunding.service.IProtocolService;
import com.winsmoney.jajaying.crowdfunding.service.request.ProtocolReqVo;
import com.winsmoney.jajaying.crowdfunding.service.response.CrowdfundingCommonResult;
import com.winsmoney.jajaying.crowdfunding.service.response.ProtocolResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;


@Controller
@RequestMapping("/crowdfunding")
public class ProtocolController {
	private  static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ProtocolController.class);
	@Autowired
	private IProtocolService protocolService;
	
	@RequestMapping(value="/protocol/protocolList")
	@ResponseBody
	public Result protocolList(ProtocolReqVo protocolReqVo){
		protocolReqVo.setDeleted(0);
		CrowdfundingCommonResult<List<ProtocolResVo>> list = protocolService.list(protocolReqVo);
		if(list.isSuccess())
			return Result.success(list.getBusinessObject());
		else
			return Result.error(list.getResultCodeMsg());
	}
	
	@RequestMapping(value="/protocol/protocolAdd")
	@ResponseBody
	public Result protocolAdd(ProtocolReqVo protocolReqVo){
		CrowdfundingCommonResult<Integer>  result = protocolService.insert(protocolReqVo);
		if(result.isSuccess())
			return Result.success(result.getBusinessObject());
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	@RequestMapping(value="/protocol/getById")
	@ResponseBody
	public Result toAddPage(ProtocolReqVo protocolReqVo){
		
		CrowdfundingCommonResult<ProtocolResVo>  result = protocolService.get(protocolReqVo);
		if(result.isSuccess())
			return Result.success(result.getBusinessObject());
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	@RequestMapping(value="/protocol/protocolEdit")
	@ResponseBody
	public Result protocolEdit(ProtocolReqVo protocolReqVo){
		CrowdfundingCommonResult<Integer>  result = protocolService.update(protocolReqVo);
		if(result.isSuccess())
			return Result.success(result.getBusinessObject());
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	@RequestMapping(value="/protocol/protocolDelete")
	@ResponseBody
	public Result protocolDelete(String protocolId){
		
		CrowdfundingCommonResult<Integer>  result = protocolService.delete(protocolId);
		if(result.isSuccess())
			return Result.success("success");
		else
			return Result.error(result.getResultCodeMsg());
	}
}

    
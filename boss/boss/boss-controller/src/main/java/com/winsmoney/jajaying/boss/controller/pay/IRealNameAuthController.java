package com.winsmoney.jajaying.boss.controller.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.pay.service.IRealNameAuthService;
import com.winsmoney.jajaying.pay.service.request.RealNameAuthReqVo;
import com.winsmoney.jajaying.pay.service.response.PayCommonResult;
import com.winsmoney.jajaying.pay.service.response.RealNameAuthResVo;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;

@Controller
@RequestMapping(value="/pay/realNameAuth")
public class IRealNameAuthController {
	
	@Autowired
	private IRealNameAuthService realNameAuth;
	
	SensitiveInfoUtils sensitiveInfoUtils = new SensitiveInfoUtils();
	/**
     * 分页列表
     */
	@RequestMapping(value="listRealNameAuth",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<RealNameAuthResVo> listChannelConfig(RealNameAuthReqVo request, String pageNo, String pageSize){
		request.setCustom(null);
		request.setRequestTime(null);
		if("".equals(request.getRealName())){
			request.setRealName(null);			
		}
		
		PayCommonResult<PageInfo<RealNameAuthResVo>> result = realNameAuth.list(request,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		for(RealNameAuthResVo re : result.getBusinessObject().getList()) {
			if(re.getUsername() != null && !"".equals(re.getUsername()))
				re.setUsername(SensitiveInfoUtils.mobilePhone(re.getUsername()));
			if(re.getCertNumber() != null && !"".equals(re.getCertNumber()))
				re.setCertNumber(SensitiveInfoUtils.idCardNum(re.getCertNumber()));
		}
		return result.getBusinessObject();
	} 
	

}

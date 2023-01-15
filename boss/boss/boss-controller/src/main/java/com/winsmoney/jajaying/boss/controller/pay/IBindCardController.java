package com.winsmoney.jajaying.boss.controller.pay;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.pay.service.IBindCardService;
import com.winsmoney.jajaying.pay.service.request.BindCardReqVo;
import com.winsmoney.jajaying.pay.service.response.BindCardResVo;
import com.winsmoney.jajaying.pay.service.response.PayCommonResult;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;

@Controller
@RequestMapping(value="/pay/bindCard")
public class IBindCardController {
	
	@Autowired
	private IBindCardService bindCard;
	
	 /**
     * 分页列表
     */	
	@RequestMapping(value="listBindCard",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<BindCardResVo> listReturnCode(BindCardReqVo request, String pageNo, String pageSize){
		
		if(StringUtils.isBlank(request.getCertNumber())){
			request.setCertNumber(null);
 		}
 		
 		if(StringUtils.isBlank(request.getMobile())){
 			request.setMobile(null);
 		}
		PayCommonResult<PageInfo<BindCardResVo>> result = bindCard.list(request,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		for(BindCardResVo be : result.getBusinessObject().getList()) {
			if(be.getUsername() != null && !"".equals(be.getUsername())) 
				be.setUsername(SensitiveInfoUtils.mobilePhone(be.getUsername()));
			if(be.getMobile() != null && !"".equals(be.getMobile())) 
				be.setMobile(SensitiveInfoUtils.mobilePhone(be.getMobile()));
			if(be.getCardNo() != null && !"".equals(be.getCardNo())) 
				be.setCardNo(SensitiveInfoUtils.bankCard(be.getCardNo()));
			if(be.getCertNumber() != null && !"".equals(be.getCertNumber())) 
				be.setCertNumber(SensitiveInfoUtils.idCardNum(be.getCertNumber()));
		}
		return result.getBusinessObject();
	}

}

package com.winsmoney.jajaying.boss.controller.pay;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IBankInfoService;
import com.winsmoney.jajaying.basedata.service.request.BankInfoReqVo;
import com.winsmoney.jajaying.basedata.service.response.BankInfoResVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.pay.service.IBankAbilityService;
import com.winsmoney.jajaying.pay.service.request.BankAbilityReqVo;
import com.winsmoney.jajaying.pay.service.response.BankAbilityResVo;
import com.winsmoney.jajaying.pay.service.response.PayCommonResult;
/**
 * 银行卡能力
 *
 */
@Controller
@RequestMapping(value="/pay/bankability")
public class BankAbility {
	
	@Autowired
	private IBankAbilityService bankAbilityService;
	@Autowired
	private IBankInfoService bankInfoService;
	@RequestMapping(value="listBankAbility",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<BankAbilityResVo> listBankAbility(BankAbilityReqVo bankAbilityReqVo, String pageNo, String pageSize){
		if(bankAbilityReqVo.getMerchant() != null)
			if(StringUtils.isBlank(bankAbilityReqVo.getMerchant().getCode()))
				bankAbilityReqVo.setMerchant(null);
		if(StringUtils.isBlank(bankAbilityReqVo.getChannel()))
			bankAbilityReqVo.setChannel(null);
		if(bankAbilityReqVo.getBusiness() != null)
			if(StringUtils.isBlank(bankAbilityReqVo.getBusiness().getCode()))
				bankAbilityReqVo.setBusiness(null);
		if(bankAbilityReqVo.getCardType()!=null)
			if(StringUtils.isBlank(bankAbilityReqVo.getCardType().getCode()))
				bankAbilityReqVo.setCardType(null);
		if(StringUtils.isBlank(bankAbilityReqVo.getBankName()))
			bankAbilityReqVo.setBankName(null);
		if(StringUtils.isBlank(bankAbilityReqVo.getBankCode()))
			bankAbilityReqVo.setBankCode(null);
		PayCommonResult<PageInfo<BankAbilityResVo>> bankAbilityResVo = bankAbilityService.list(bankAbilityReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		return bankAbilityResVo.getBusinessObject();
	}
	
	@RequestMapping(value="addBankAbility",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加银行卡能力")
	public Result addBankAbility(BankAbilityReqVo bankAbilityReqVo, HttpServletRequest request){
		bankAbilityReqVo.setAvailable(true);
		PayCommonResult<Integer> bankAbilityResVo = bankAbilityService.insert(bankAbilityReqVo);
		if(bankAbilityResVo.isSuccess())
			return Result.success(bankAbilityResVo);
		else return Result.error("添加银行卡能力失败");
	}
	
	@RequestMapping(value="getBankAbilityById",method=RequestMethod.POST)
	@ResponseBody
	public BankAbilityResVo getBankAbilityById(String id){
		PayCommonResult<BankAbilityResVo> bankAbilityResVo = bankAbilityService.getById(id);
		return bankAbilityResVo.getBusinessObject();
	}
	
	@RequestMapping(value="updateBankAbility",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "编辑银行卡能力")
	public Result updateBankAbility(BankAbilityReqVo bankAbilityReqVo){
		PayCommonResult<Integer> bankAbilityResVo = bankAbilityService.update(bankAbilityReqVo);
		if(bankAbilityResVo.isSuccess())
			return Result.success(bankAbilityResVo);
		else return Result.error("编辑银行卡能力失败");
	}
	
	@RequestMapping(value="delBankAbility",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除银行卡能力")
	public Result delBankAbility(String id){
		PayCommonResult<Integer> bankAbilityResVo = bankAbilityService.delete(id);
		if(bankAbilityResVo.isSuccess())
			return Result.success(bankAbilityResVo);
		else return Result.error("删除银行卡能力失败");
	}
	
	
	@RequestMapping(value="getBankInfo",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<BankInfoResVo> getBankInfo(int pageNo ,int pageSize){
		BankInfoReqVo bankInfoReqVo = new BankInfoReqVo();
		BasedataCommonResult<PageInfo<BankInfoResVo>> BankInfoResVo = bankInfoService.listBankInfo(bankInfoReqVo, pageNo, pageSize);
		return BankInfoResVo.getBusinessObject();
	}
}

package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IBankInfoService;
import com.winsmoney.jajaying.basedata.service.request.BankInfoReqVo;
import com.winsmoney.jajaying.basedata.service.response.BankInfoResVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
/**
 * 
 * 银行信息
 * @author Moon
 *
 */

@Controller
@RequestMapping("/basedata_mgt/b_bank_info_list")
public class IBankInfoController {

	@Autowired
	private IBankInfoService iBankInfoService;
	
	/**
     * 单笔查询 银行信息
     * @param bankInfoReqVo
     * @return BankInfoResVo
     */
	
	@RequestMapping("/BankInfo/one")
	@ResponseBody
	public String getBankInfo(BankInfoReqVo bankInfoReqVo){
		
			BasedataCommonResult<BankInfoResVo> bankInfo = iBankInfoService.getBankInfo(bankInfoReqVo);
			//System.out.println(JSON.toJSONString(bankInfoReqVo));
			
		    return JSON.toJSONString(bankInfo.getBusinessObject());
	}
	/**
     * 分页列表银行信息
     * @param bankInfoReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
	@RequestMapping("/BankInfo/page")
	@ResponseBody
	public String listBankInfo(BankInfoReqVo bankInfoReqVo,Integer pageNo, Integer pageSize){
		
		BasedataCommonResult<PageInfo<BankInfoResVo>> listBankInfo = iBankInfoService.listBankInfo(bankInfoReqVo, pageNo,pageSize);
		
		PageInfo<BankInfoResVo> businessObject = listBankInfo.getBusinessObject();
		List<BankInfoResVo> list = businessObject.getList();
		//System.out.println(JSON.toJSONString(list));
		return JSON.toJSONString(list);
	}
	/**
     * 添加 银行信息
     * @param bankInfoReqVo
     * @return Integer
     */
	@RequestMapping("/BankInfo/add")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增银行信息")
	public BasedataCommonResult<Integer> insertBankInfo(BankInfoReqVo bankInfoReqVo){
		
		BasedataCommonResult<Integer> insertBankInfo = iBankInfoService.insertBankInfo(bankInfoReqVo);
		
		return  insertBankInfo;
	}
	   /**
     * 更新 银行信息
     * @param bankInfoReqVo
     * @return
     */
	@RequestMapping("/BankInfo/edit")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新银行信息")
	public BasedataCommonResult<Integer> updateBankInfo(BankInfoReqVo bankInfoReqVo){
		//System.out.println(JSON.toJSONString(bankInfoReqVo));
		BasedataCommonResult<Integer> updateBankInfo = iBankInfoService.updateBankInfo(bankInfoReqVo);
		
		return updateBankInfo;
	}
	
    /**
     * 删除 银行信息
     * @param id
     * @return
     */
	@RequestMapping("/BankInfo/delete")
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除银行信息")
	public  BasedataCommonResult<Integer> deleteBankInfo(String id){
		//System.out.println(id);
		BasedataCommonResult<Integer> deleteBankInfo = iBankInfoService.deleteBankInfo(id);
		//System.out.println(deleteBankInfo);
		return deleteBankInfo;
		}
}

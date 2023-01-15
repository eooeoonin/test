package com.winsmoney.jajaying.boss.controller.crowdfunding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.model.SettleModel;
import com.winsmoney.jajaying.boss.controller.model.SettleModel2;
import com.winsmoney.jajaying.boss.controller.utils.BeanMapper;
import com.winsmoney.jajaying.boss.controller.utils.ExcelUtils;
import com.winsmoney.jajaying.crowdfunding.service.IOrderInfoService;
import com.winsmoney.jajaying.crowdfunding.service.ISettleRecordService;
import com.winsmoney.jajaying.crowdfunding.service.request.OrderRecordReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.SettleRecordReqVo;
import com.winsmoney.jajaying.crowdfunding.service.response.CrowdfundingCommonResult;
import com.winsmoney.jajaying.crowdfunding.service.response.OrderRecordResVo;
import com.winsmoney.jajaying.crowdfunding.service.response.SettleRecordResVo;



@Controller
@RequestMapping("/crowdfunding/settlement")
public class SettleController {
	
	@Autowired
	private IOrderInfoService orderInfoService;
	@Autowired
	private ISettleRecordService settleRecordService;

	/**
	* list:清算列表查询. <br/>
	* Date: 2016年4月19日 下午12:09:27 <br/>
	* @author wangbinlei
	* @param orderRecordReqVo
	* @param pageVo
	* @param model
	* @return
	*/
	@ResponseBody
	@RequestMapping("/list")
	public Map list(OrderRecordReqVo orderRecordReqVo,SettleRecordReqVo settleRecordReqVo,int pageNo, int pageSize,String settleStatus){
		Map map = new HashMap();
		if(StringUtils.isBlank(orderRecordReqVo.getName())){
			orderRecordReqVo.setName(null);
		}if(StringUtils.isBlank(orderRecordReqVo.getPhone())){
			orderRecordReqVo.setPhone(null);
		}if(StringUtils.isBlank(orderRecordReqVo.getIdcard())){
			orderRecordReqVo.setIdcard(null);
		}if(StringUtils.isBlank(orderRecordReqVo.getSubProductName())){
			orderRecordReqVo.setSubProductName(null);
		}
		if(!"1".equals(settleStatus)){
			//查询未结算信息
			
			orderRecordReqVo.setDeleted(0);
			orderRecordReqVo.setRate("true");//只查询利率部位null数据
			orderRecordReqVo.setBuyHouseFlag("true");//只查询设置了购房标示的数据
			CrowdfundingCommonResult<PageInfo<OrderRecordResVo>> result = orderInfoService.selectSettleMsg(orderRecordReqVo, pageNo, pageSize);
			map.put("result", result.getBusinessObject());
		}else{
			//查询已结算信息
			settleRecordReqVo.setType(orderRecordReqVo.getType());
			settleRecordReqVo.setName(orderRecordReqVo.getName());
			settleRecordReqVo.setIdcard(orderRecordReqVo.getIdcard());
			CrowdfundingCommonResult<PageInfo<SettleRecordResVo>> commonResult=settleRecordService.settledPageList(settleRecordReqVo, pageNo, pageSize);
			map.put("result", commonResult.getBusinessObject());
		}

		return map;
	}
	
	/**
	* export:导出excel. <br/>
	* Date: 2016年4月19日 下午12:09:39 <br/>
	* @author wangbinlei
	* @param orderRecordReqVo
	* @param pageVo
	* @param model
	* @return
	*/
	@RequestMapping("/export")
	@ResponseBody
	public void export(OrderRecordReqVo orderRecordReqVo,Model model,HttpServletResponse response,String settleStatus){
		if(StringUtils.isBlank(orderRecordReqVo.getName())){
			orderRecordReqVo.setName(null);
		}if(StringUtils.isBlank(orderRecordReqVo.getPhone())){
			orderRecordReqVo.setPhone(null);
		}if(StringUtils.isBlank(orderRecordReqVo.getIdcard())){
			orderRecordReqVo.setIdcard(null);
		}if(StringUtils.isBlank(orderRecordReqVo.getSubProductName())){
			orderRecordReqVo.setSubProductName(null);
		}
		List<SettleModel> list=null;
		if(!"1".equals(settleStatus)) {		
			//默认查询倍筹信息
			if (null == orderRecordReqVo.getType()) {
				orderRecordReqVo.setType(2);
			}
			orderRecordReqVo.setDeleted(0);
			orderRecordReqVo.setRate("true");//只查询利率部位null数据
			orderRecordReqVo.setBuyHouseFlag("true");//只查询设置了购房标示的数据
			CrowdfundingCommonResult<PageInfo<OrderRecordResVo>> result = orderInfoService.selectSettleMsg(orderRecordReqVo, 1, 1000);
			List<OrderRecordResVo> vos = result.getBusinessObject().getList();
			try {
				list = BeanMapper.mapList2(vos, SettleModel.class);
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename="
						+ new String("settle.xls".getBytes("GBK"), "ISO-8859-1"));
				ExcelUtils<SettleModel> utils = new ExcelUtils<SettleModel>();
				String[] headers = {"项目名称","权益名称","用户ID","姓名","手机号","身份证号","购买时间","金额（元）","是否购房(使用)","利率","购房/不购房设置时间"};
				utils.exportExcel("清算数据",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//查询已结算信息
			SettleRecordReqVo settleRecordReqVo=new SettleRecordReqVo();
			settleRecordReqVo.setType(orderRecordReqVo.getType());
			settleRecordReqVo.setName(orderRecordReqVo.getName());
			settleRecordReqVo.setIdcard(orderRecordReqVo.getIdcard());
			CrowdfundingCommonResult<PageInfo<SettleRecordResVo>> commonResult=settleRecordService.settledPageList(settleRecordReqVo, 1, 1000);
			List<SettleRecordResVo> vos2 = commonResult.getBusinessObject().getList();
			try {
				List<SettleModel2> list2 = BeanMapper.mapList2(vos2, SettleModel2.class);
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename="
						+ new String("settle.xls".getBytes("GBK"), "ISO-8859-1"));
				ExcelUtils<SettleModel2> utils = new ExcelUtils<SettleModel2>();
				String[] headers = {"项目名称","权益名称","用户ID","姓名","手机号","身份证号","购买时间","金额（元）","是否购房(使用)","利率","利息"};
				utils.exportExcel("清算数据",headers,list2,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="/updateStatus")
	@ResponseBody
	public String updateStatus(String orderId){
		List<String> orderIds = new ArrayList<String>();
		orderIds.add(orderId);
		//批量修改是否买房状态
		CrowdfundingCommonResult<Integer> result= orderInfoService.updateStatus(orderIds, "");
		return result.getResultCode();
	}
	
	@RequestMapping(value="/settle")
	@ResponseBody
	public String settle(String[] orderIds){
		
		List<String> orderList = new ArrayList<String>();
		Collections.addAll(orderList, orderIds);
		//结算
		CrowdfundingCommonResult<Map<String, Object>> result= orderInfoService.settle(orderList);
		return JSONObject.toJSONString(result);
	}
}

    
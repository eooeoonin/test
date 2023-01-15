package com.winsmoney.jajaying.boss.controller.crowdfunding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.winsmoney.jajaying.boss.controller.utils.ExcelUtils;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.crowdfunding.service.IOrderInfoService;
import com.winsmoney.jajaying.crowdfunding.service.request.OrderInfoReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.OrderRecordReqVo;
import com.winsmoney.jajaying.crowdfunding.service.response.CrowdfundingCommonResult;
import com.winsmoney.jajaying.crowdfunding.service.response.OrderRecordResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;


@Controller
@RequestMapping("/crowdfunding")
public class ProxyInvestController {
	private  static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ProxyInvestController.class);
	@Autowired
	private IOrderInfoService orderInfoService;
	
	@RequestMapping(value="/proxyInvest/orderList", method = RequestMethod.POST)
	@ResponseBody
	public Result orderList(OrderRecordReqVo orderRecordReqVo,int pageNo, int pageSize){
		//默认查询全部
		if("0".equals(orderRecordReqVo.getStatus())){
			orderRecordReqVo.setStatus(null);
		}
		if(StringUtils.isEmpty(orderRecordReqVo.getPhone()))
			orderRecordReqVo.setPhone(null);
		if(StringUtils.isEmpty(orderRecordReqVo.getSubProductName()))
			orderRecordReqVo.setSubProductName(null);
		CrowdfundingCommonResult<PageInfo<OrderRecordResVo>> result = orderInfoService.orderList(orderRecordReqVo, pageNo, pageSize);
		for(OrderRecordResVo or : result.getBusinessObject().getList()) {
			if(or.getPhone() != null && !"".equals(or.getPhone()))
				or.setPhone(SensitiveInfoUtils.mobilePhone(or.getPhone()));
			if(or.getIdcard() != null && !"".equals(or.getIdcard()))
				or.setIdcard(SensitiveInfoUtils.idCardNum(or.getIdcard()));
		}
		if(result.isSuccess())
			return Result.success(result.getBusinessObject());
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	@RequestMapping(value="/proxyInvest/effectiveOrder" , method = RequestMethod.POST)
	@ResponseBody
	public Result effectiveOrder(String orderId){
		 
		OrderInfoReqVo orderInfoReqVo= new OrderInfoReqVo();
		orderInfoReqVo.setId(orderId);
		CrowdfundingCommonResult<Integer>  result= orderInfoService.effectiveOrder(orderInfoReqVo);
		if(result.isSuccess())
			return Result.success("success");
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	@RequestMapping(value="/proxyInvest/buy" , method = RequestMethod.POST)
	@ResponseBody
	public Result buy(Model model,String orderId){
		
		OrderInfoReqVo orderInfoReqVo= new OrderInfoReqVo();
		orderInfoReqVo.setId(orderId);
		CrowdfundingCommonResult<Integer> result = orderInfoService.proxyInvest(orderInfoReqVo);
		if(result.isSuccess())
			return Result.success("success");
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	
	@RequestMapping(value="/proxyInvest/export/{form}", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void export(@PathVariable @NotBlank String form,HttpServletResponse response){
		//默认查询全部
		OrderRecordReqVo orderRecordReqVo = new OrderRecordReqVo();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		try {
			JSONObject a = new JSONObject(form);
			String phone = a.getString("phone");
			String subProductName = a.getString("subProductName");
			String status = a.getString("status");
			String beginTime = a.getString("beginTime");
			String endTime = a.getString("endTime");
			if(!StringUtil.isEmpty(phone))
				orderRecordReqVo.setPhone(phone);
			if(!StringUtil.isEmpty(subProductName))
				orderRecordReqVo.setSubProductName(subProductName);
			if(!StringUtil.isEmpty(status) && !"0".equals(status))
				orderRecordReqVo.setStatus(status);
			if(!StringUtil.isEmpty(beginTime)) {
				String beginTimeDate  =  dateFormat.format(new Date(Long.valueOf(beginTime)));
				orderRecordReqVo.setBeginTime(dateFormat.parse(beginTimeDate));		
			}				
			if(!StringUtil.isEmpty(endTime)) {
				String endTimeDate  =  dateFormat.format(new Date(Long.valueOf(endTime)));
				orderRecordReqVo.setEndTime(dateFormat.parse(endTimeDate));
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		CrowdfundingCommonResult<PageInfo<OrderRecordResVo>> result = 
				orderInfoService.orderList(orderRecordReqVo, 1, 20000);
		List<OrderRecordResVo> vos2 = (List<OrderRecordResVo>) result.getBusinessObject().getList();
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("settle.xls".getBytes("GBK"), "ISO-8859-1"));
			String[] headers; 
			if("3".equals(orderRecordReqVo.getStatus())){
				ExcelUtils<EffectOrderModel> utils = new ExcelUtils<EffectOrderModel>();
				List<EffectOrderModel> list=  BeanMapper.mapList(vos2, EffectOrderModel.class);
				headers = new String[] {"购买时间","完成时间","用户名","姓名","身份证号","金额（元）","权益"};
				utils.exportExcel("已完成订单",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}else if("-4".equals(orderRecordReqVo.getStatus())){
				ExcelUtils<RefundOrderModel> utils = new ExcelUtils<RefundOrderModel>();
				List<RefundOrderModel> list=  BeanMapper.mapList(vos2, RefundOrderModel.class);
				headers = new String[] {"购买时间","退款时间","用户名","姓名","身份证号","金额（元）","权益"};
				utils.exportExcel("已退款订单",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}else if("2".equals(orderRecordReqVo.getStatus())){
				ExcelUtils<PayingOrderModel> utils = new ExcelUtils<PayingOrderModel>();
				List<PayingOrderModel> list=  BeanMapper.mapList(vos2, PayingOrderModel.class);
				headers = new String[] {"购买时间","用户名","姓名","身份证号","金额（元）","已支付金额（元）","权益"};
				utils.exportExcel("支付中订单",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}else if(null == orderRecordReqVo.getStatus() || "0".equals(orderRecordReqVo.getStatus())){
				ExcelUtils<AllOrderModel> utils = new ExcelUtils<AllOrderModel>();
				//List<AllOrderModel> list=  BeanMapper.mapList(vos2, AllOrderModel.class);
				List<AllOrderModel> list = new ArrayList<AllOrderModel>();
				for(OrderRecordResVo orderRecordResVo:vos2){
					AllOrderModel allOrderModel = BeanMapper.map(orderRecordResVo,AllOrderModel.class);
					if("-4".equals(orderRecordResVo.getStatus())){
						allOrderModel.setSettleTime(orderRecordResVo.getRefundTime());
					}
					list.add(allOrderModel);
				}
				headers = new String[] {"用户ID","状态","购买时间","清算时间","用户名","姓名","身份证号","金额（元）","权益"};
				utils.exportExcel("全部订单",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}else if("4".equals(orderRecordReqVo.getStatus())){
				ExcelUtils<SettleOrderModel> utils = new ExcelUtils<SettleOrderModel>();
				List<SettleOrderModel> list=  BeanMapper.mapList(vos2, SettleOrderModel.class);
				headers = new String[] {"购买时间","清算时间","用户名","姓名","身份证号","金额（元）","权益"};
				utils.exportExcel("已结算",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}else{
				String title = "";
				if("1".equals(orderRecordReqVo.getStatus()))
					title = "待支付";
				if("-1".equals(orderRecordReqVo.getStatus()))
					title = "已失效";
				ExcelUtils<NoPayOrderModel> utils = new ExcelUtils<NoPayOrderModel>();
				List<NoPayOrderModel> list=  BeanMapper.mapList(vos2, NoPayOrderModel.class);
				headers = new String[] {"购买时间","用户名","姓名","身份证号","金额（元）","权益"};
				utils.exportExcel(title,headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="/proxyInvest/export2", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void export2(OrderRecordReqVo orderRecordReqVo,HttpServletResponse response){
		//默认查询全部
		if("0".equals(orderRecordReqVo.getStatus())) {
			orderRecordReqVo.setStatus(null);
		}
		CrowdfundingCommonResult<PageInfo<OrderRecordResVo>> result = 
				orderInfoService.orderList(orderRecordReqVo, 1, 20000);
		List<OrderRecordResVo> vos2 = (List<OrderRecordResVo>) result.getBusinessObject().getList();
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("settle.xls".getBytes("GBK"), "ISO-8859-1"));
			String[] headers; 
			if("3".equals(orderRecordReqVo.getStatus())){
				ExcelUtils<EffectOrderModel> utils = new ExcelUtils<EffectOrderModel>();
				List<EffectOrderModel> list=  BeanMapper.mapList(vos2, EffectOrderModel.class);
				headers = new String[] {"购买时间","完成时间","用户名","姓名","身份证号","金额（元）","权益"};
				utils.exportExcel("已完成订单",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}else if("-4".equals(orderRecordReqVo.getStatus())){
				ExcelUtils<RefundOrderModel> utils = new ExcelUtils<RefundOrderModel>();
				List<RefundOrderModel> list=  BeanMapper.mapList(vos2, RefundOrderModel.class);
				headers = new String[] {"购买时间","退款时间","用户名","姓名","身份证号","金额（元）","权益"};
				utils.exportExcel("已退款订单",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}else if("2".equals(orderRecordReqVo.getStatus())){
				ExcelUtils<PayingOrderModel> utils = new ExcelUtils<PayingOrderModel>();
				List<PayingOrderModel> list=  BeanMapper.mapList(vos2, PayingOrderModel.class);
				headers = new String[] {"购买时间","用户名","姓名","身份证号","金额（元）","已支付金额（元）","权益"};
				utils.exportExcel("支付中订单",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}else if(null == orderRecordReqVo.getStatus() || "0".equals(orderRecordReqVo.getStatus())){
				ExcelUtils<AllOrderModel> utils = new ExcelUtils<AllOrderModel>();
				//List<AllOrderModel> list=  BeanMapper.mapList(vos2, AllOrderModel.class);
				List<AllOrderModel> list = new ArrayList<AllOrderModel>();
				for(OrderRecordResVo orderRecordResVo:vos2){
					AllOrderModel allOrderModel = BeanMapper.map(orderRecordResVo,AllOrderModel.class);
					if("-4".equals(orderRecordResVo.getStatus())){
						allOrderModel.setSettleTime(orderRecordResVo.getRefundTime());
					}
					list.add(allOrderModel);
				}
				headers = new String[] {"用户ID","状态","购买时间","清算时间","用户名","姓名","身份证号","金额（元）","权益"};
				utils.exportExcel("全部订单",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}else if("4".equals(orderRecordReqVo.getStatus())){
				ExcelUtils<SettleOrderModel> utils = new ExcelUtils<SettleOrderModel>();
				List<SettleOrderModel> list=  BeanMapper.mapList(vos2, SettleOrderModel.class);
				headers = new String[] {"购买时间","清算时间","用户名","姓名","身份证号","金额（元）","权益"};
				utils.exportExcel("已结算",headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}else{
				String title = "";
				if("1".equals(orderRecordReqVo.getStatus()))
					title = "待支付";
				if("-1".equals(orderRecordReqVo.getStatus()))
					title = "已失效";
				ExcelUtils<NoPayOrderModel> utils = new ExcelUtils<NoPayOrderModel>();
				List<NoPayOrderModel> list=  BeanMapper.mapList(vos2, NoPayOrderModel.class);
				headers = new String[] {"购买时间","用户名","姓名","身份证号","金额（元）","权益"};
				utils.exportExcel(title,headers,list,response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

    
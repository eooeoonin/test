package com.winsmoney.jajaying.boss.controller.user;



import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.controller.BaseController;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.trade.service.ILoanInvestorService;
import com.winsmoney.jajaying.trade.service.response.LoanInvestorStatisticsResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IBindBankCardService;
import com.winsmoney.jajaying.user.service.IUnbindCardApplyService;
import com.winsmoney.jajaying.user.service.request.BindBankCardReqVo;
import com.winsmoney.jajaying.user.service.request.UnbindCardApplyReqVo;
import com.winsmoney.jajaying.user.service.response.BindBankCardResVo;
import com.winsmoney.jajaying.user.service.response.UnbindCardApplyResVo;
import com.winsmoney.jajaying.user.service.response.UserAccountInfoResVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.Money;
import com.winsmoney.platform.framework.core.util.SensitiveInfoUtils;

/*
 * 解绑银行卡
 * 
 * */


@Controller
@RequestMapping("/unwrap")
public class IUserUnBandController extends BaseController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(IUserUnBandController.class);
	//列表展示
	@Autowired
	private IUnbindCardApplyService unbindCard;
	//取得账户余额
	//取得用户信息
	@Autowired
	private IBindBankCardService bindBankCard;
	//取得投资金额
	@Autowired
	private ILoanInvestorService loanInvestor;
	SensitiveInfoUtils sensitiveInfoUtils = new SensitiveInfoUtils();

	
	/**
     * 投资记录
     * @param loanInvestorReqVo
     * @return LoanInvestorResVo
     */
	@RequestMapping(value="userSelectloanid",method=RequestMethod.POST)
	@ResponseBody
	public LoanInvestorStatisticsResVo userSelectloanid(String investorUserCode){
		logger.info("接口{}入参：" + JSONObject.toJSONString(investorUserCode),ILoanInvestorService.class);
		TradeCommonResult<LoanInvestorStatisticsResVo> result = loanInvestor.userInvestorStatistics(investorUserCode);
		logger.info("接口{}出参：" + JSONObject.toJSONString(result),ILoanInvestorService.class);
		return result.getBusinessObject();
	}	
	/**
     * 单笔查询 用户绑定银行卡
     * @param bindBankCardReqVo
     * @return BindBankCardResVo
     */
	@RequestMapping(value="userSelectid",method=RequestMethod.POST)
	@ResponseBody
	public BindBankCardResVo userSelectid(BindBankCardReqVo bindBankCardReqVo){
		UserCommonResult<BindBankCardResVo> result = bindBankCard.getByRoleUserId(bindBankCardReqVo.getUserId());
//		if(result.getBusinessObject().getBankMobile() != null)
//			result.getBusinessObject().setBankMobile(sensitiveInfoUtils.mobilePhone(result.getBusinessObject().getBankMobile()));
//		if(result.getBusinessObject().getRealName() != null)
//			result.getBusinessObject().setRealName(sensitiveInfoUtils.chineseName(result.getBusinessObject().getRealName()));
//		if(result.getBusinessObject().getCertNo()!= null)
//			result.getBusinessObject().setCertNo(sensitiveInfoUtils.idCardNum(result.getBusinessObject().getCertNo()));
		if(result.getBusinessObject().getBankCardNo()!= null)
			result.getBusinessObject().setBankCardNo(sensitiveInfoUtils.bankCard(result.getBusinessObject().getBankCardNo()));
		return result.getBusinessObject();
	}
	 /**
     * 取得用户账户信息：余额
     * @param request
     * @return
     */
	@RequestMapping(value="userSelectyuer",method=RequestMethod.POST)
	@ResponseBody
	public Money userSelectyuer(UserAccountInfoResVo request){
		    return getBalance(request.getUserId()).getAvailableBalance();
	}
	
	/**
    *解绑银行卡列表展示
    * @param request
    * @return
    */
	@RequestMapping(value="unwrapSelectAll",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<UnbindCardApplyResVo> unwrapSelectAll(UnbindCardApplyReqVo unbindCardApplyReqVo,String pageNo, String pageSize)throws Exception{

		if(StringUtils.isBlank(unbindCardApplyReqVo.getRegisterMobile())){
			unbindCardApplyReqVo.setRegisterMobile(null);
		}
		if(StringUtils.isBlank(unbindCardApplyReqVo.getRealName())){
			unbindCardApplyReqVo.setRealName(null);
		}
		UserCommonResult<PageInfo<UnbindCardApplyResVo>> selectall = unbindCard.list(unbindCardApplyReqVo,(int)Double.parseDouble(pageNo),(int)Double.parseDouble(pageSize));
		PageInfo<UnbindCardApplyResVo> list = selectall.getBusinessObject();
		for(UnbindCardApplyResVo unbindCardApplyResVo:list.getList()) {
			if(unbindCardApplyResVo.getRegisterMobile() != null)
				unbindCardApplyResVo.setRegisterMobile(sensitiveInfoUtils.mobilePhone(unbindCardApplyResVo.getRegisterMobile()));
			if(unbindCardApplyResVo.getRealName() != null)
				unbindCardApplyResVo.setRealName(sensitiveInfoUtils.chineseName(unbindCardApplyResVo.getRealName()));
		}
		return list;
	}
	 /**
     * 解绑申请结果：0=申请中、1=解绑成功、2=解绑处理中、-1=解绑失败、 -2=解绑拒绝
     */
	 /**
     * 同意解绑申请
     * @param request
     * @return
     */
	@RequestMapping(value="successUnBank",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "同意解绑申请")
	public UserCommonResult<UnbindCardApplyResVo> successUnBank(UnbindCardApplyReqVo request,HttpServletRequest httpRequest){
		
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		request.setEditedBy(role);
		logger.info("接口{}入参：" + JSONObject.toJSONString(request),IUnbindCardApplyService.class);
		UserCommonResult<UnbindCardApplyResVo> result = unbindCard.unBindCardAgree(request);
		logger.info("接口{}出参：" + JSONObject.toJSONString(result),IUnbindCardApplyService.class);
		return result;
	}

    /**
     * 拒绝解绑申请
     * @param request
     * @return
     */
	@RequestMapping(value="refuseUnBank",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "拒绝解绑申请")
	public UserCommonResult<UnbindCardApplyResVo> refuseUnBank(UnbindCardApplyReqVo request,HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		request.setEditedBy(role);
		logger.info("接口{}入参：" + JSONObject.toJSONString(request),IUnbindCardApplyService.class);
		UserCommonResult<UnbindCardApplyResVo> result = unbindCard.unBindCardRefuse(request);
		logger.info("接口{}出参：" + JSONObject.toJSONString(result),IUnbindCardApplyService.class);
		return result;
	}
	
    
	/**
	  * 根据id查询 用户解绑银行卡申请
	  * @param unbindCardApplyReqVo
	  * @return UnbindCardApplyResVo
	  */
	@RequestMapping(value="UnBankselectByid",method=RequestMethod.POST)
	@ResponseBody
	public UserCommonResult<UnbindCardApplyResVo> UnBankselectByid(String id){
		UserCommonResult<UnbindCardApplyResVo> getbyid = unbindCard.getById(id);
	    return getbyid;
	}
	 /**
     * 单笔查询 用户解绑银行卡申请
     * @param unbindCardApplyReqVo
     * @return UnbindCardApplyResVo
     */
	@RequestMapping(value="UnBankselect",method=RequestMethod.POST)
	@ResponseBody
	public UnbindCardApplyResVo UnBankselect(UnbindCardApplyReqVo unbindCardApplyReqVo){
		 UserCommonResult<UnbindCardApplyResVo> result = unbindCard.get(unbindCardApplyReqVo);
//		 if(result.getBusinessObject().getRegisterMobile() != null)
//			result.getBusinessObject().setRegisterMobile(sensitiveInfoUtils.mobilePhone(result.getBusinessObject().getRegisterMobile()));
		 return result.getBusinessObject();
	}
	/**
	 * 获取当前用户
	 * 
	 * */
	@RequestMapping(value="getUnBankStaffName",method=RequestMethod.POST)
	@ResponseBody
	public String getUnBankStaffName(HttpServletRequest request){
		Staff staffSession =  (Staff)request.getSession().getAttribute("adminInfo");
		return staffSession.getStaffName();
	}

}

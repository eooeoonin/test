package com.winsmoney.jajaying.boss.controller.homepage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.jajaying.trade.service.IBorrowService;
import com.winsmoney.jajaying.trade.service.ILoanInvestorService;
import com.winsmoney.jajaying.trade.service.IPayRecordService;
import com.winsmoney.jajaying.trade.service.request.BorrowCountReqVo;
import com.winsmoney.jajaying.trade.service.response.BorrowCountResVo;
import com.winsmoney.jajaying.trade.service.response.LoanInvestorStatisticsResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;


@Controller
@RequestMapping("/main")
public class HomePage {
	
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(HomePage.class);
	
	@Autowired
	private IBorrowService borrowService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IPayRecordService payRecordService;
	
	@Autowired
	private ILoanInvestorService loanInvestorService;
	

	/**
	 * 投资笔数金额统计
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/investorStatistics")
	@ResponseBody
	public List<LoanInvestorStatisticsResVo> investorStatistics(Date startTime,Date endTime){
		TradeCommonResult<List<LoanInvestorStatisticsResVo>> investorStatistics = loanInvestorService.investorStatistics(startTime,endTime);
		return  investorStatistics.getBusinessObject();
	}
	
	/**
	 * 借款笔数金额统计
	 * @param borrowCountReqVo
	 * @return
	 */
	@RequestMapping("/borrowCountList")
	@ResponseBody
	public List<BorrowCountResVo> borrowCountList(BorrowCountReqVo borrowCountReqVo){
	    TradeCommonResult<List<BorrowCountResVo>> borrowCountList = borrowService.borrowCountList(borrowCountReqVo);
		return  borrowCountList.getBusinessObject();
	}
	
	
	
	
	/**
	 * 借款申请笔数
	 * 借款总额
	 * @param borrowCountReqVo
	 * @return
	 */
	@RequestMapping("/borrowAccount")
	@ResponseBody
	public BorrowCountResVo borrowAccount(BorrowCountReqVo borrowCountReqVo){
	    TradeCommonResult<BorrowCountResVo> borrowCount = borrowService.borrowCount(borrowCountReqVo);
	return  borrowCount.getBusinessObject();
	}
	
	/**
	 * 统计新增用户
	 * @return
	 */
	@RequestMapping("/countRegisterUser")
	@ResponseBody
	public Integer countRegisterUser(){
		UserCommonResult<Integer> countRegisterUser = userInfoService.countRegisterUser();
		if(countRegisterUser.isSuccess()){
			return countRegisterUser.getBusinessObject();
		}
		logger.debug("userInfoService接口异常");
	return 0; 
	}
	
	

	/**
	 * 统计登录用户
	 * @return
	 */
	@RequestMapping("/countLoginUser")
	@ResponseBody
	public Integer countLoginUser(){
		UserCommonResult<Integer> countLoginUser = userInfoService.countLoginUser();
		if(countLoginUser.isSuccess()){
			return countLoginUser.getBusinessObject();
		}
		logger.debug("userInfoService接口异常");
	return  0;
	}
	
	
	/**
	 * 统计提现笔数
	 * @return
	 */
	@RequestMapping("/countTodayWithdraw")
	@ResponseBody
	public Integer countTodayWithdraw(){
	    TradeCommonResult<Integer> countTodayWithdraw = payRecordService.countTodayWithdraw();
	    if(countTodayWithdraw.isSuccess()){
			return countTodayWithdraw.getBusinessObject();
		}
		logger.debug("payRecordService接口异常");
	return  0;
	}
	
	
}

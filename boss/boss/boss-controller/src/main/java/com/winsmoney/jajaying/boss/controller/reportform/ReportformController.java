package com.winsmoney.jajaying.boss.controller.reportform;

import com.winsmoney.jajaying.boss.controller.model.InvestorStatisticsExcelPlans;
import com.winsmoney.jajaying.redmoney.service.IRedMoneyQueryService;
import com.winsmoney.jajaying.redmoney.service.request.QueryTotalRegisterRedmoneyReqVo;
import com.winsmoney.jajaying.redmoney.service.response.QueryTotalRegisterRedmoneyResVo;
import com.winsmoney.jajaying.redmoney.service.response.RedmoneyCommonResult;
import com.winsmoney.jajaying.trade.service.ILoanInvestorService;
import com.winsmoney.jajaying.trade.service.response.InvestorStatisticsResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.jajaying.user.service.IUserStatisticsService;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.platform.framework.core.util.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 报表查询
 */
@Controller
@RequestMapping(value = "/reportform/reportform")
public class ReportformController {
    @Autowired
    private ILoanInvestorService loanInvestorService;

    @Autowired
    private IUserStatisticsService iUserStatisticsService;

    @Autowired
    private IRedMoneyQueryService redMoneyQueryService;

    @RequestMapping(value = "/select")
    @ResponseBody
    public InvestorStatisticsExcelPlans reportform(Date startTime, Date endTime, HttpServletResponse response) {
        InvestorStatisticsExcelPlans investorStatisticsExcelPlans = new InvestorStatisticsExcelPlans();
        //新增注册用户数
        UserCommonResult<Integer> addRegisteredUsers = iUserStatisticsService.countUser(startTime, endTime);
        investorStatisticsExcelPlans.setAddRegisteredUsers(addRegisteredUsers.getBusinessObject());

        //累计注册用户数
        UserCommonResult<Integer> cumulativeRegisteredUsers = iUserStatisticsService.countUser(null, null);
        investorStatisticsExcelPlans.setCumulativeRegisteredUsers(cumulativeRegisteredUsers.getBusinessObject());

        //新增绑卡用户数
        UserCommonResult<Integer> addBindUsers = iUserStatisticsService.countBindUser(startTime, endTime);
        investorStatisticsExcelPlans.setAddBindUsers(addBindUsers.getBusinessObject());

        //累计绑卡用户数
        UserCommonResult<Integer> cumulativeBindUsers = iUserStatisticsService.countBindUser(null, null);
        investorStatisticsExcelPlans.setCumulativeBindUsers(cumulativeBindUsers.getBusinessObject());

        //平均用户注册成本（发放的注册红包总金额/所得的用户数），
        QueryTotalRegisterRedmoneyReqVo queryTotalRegisterRedmoneyReqVo = new QueryTotalRegisterRedmoneyReqVo();
        queryTotalRegisterRedmoneyReqVo.setStartTime(startTime);
        queryTotalRegisterRedmoneyReqVo.setEndTime(endTime);
        RedmoneyCommonResult<QueryTotalRegisterRedmoneyResVo> queryTotalRegisterRedmoney = redMoneyQueryService.queryTotalRegisterRedmoney(queryTotalRegisterRedmoneyReqVo);
        Money totalRegisterRedmoney = queryTotalRegisterRedmoney.getBusinessObject().getTotalRegisterRedmoney();//发放的注册红包总金额
        if (addRegisteredUsers.getBusinessObject() == 0) {
            investorStatisticsExcelPlans.setAverageUserCost(new Money(0));
        } else {
            investorStatisticsExcelPlans.setAverageUserCost(totalRegisterRedmoney.divide(new BigDecimal(addRegisteredUsers.getBusinessObject()), 2));//平均用户注册成本（发放的注册红包总金额/所得的用户数）
        }
        TradeCommonResult<InvestorStatisticsResVo> investorStatisticsForBoss = loanInvestorService.investorStatisticsForBoss(startTime, endTime);
        investorStatisticsExcelPlans.setRepayAmount(investorStatisticsForBoss.getBusinessObject().getRepayAmount());//还款金额
        investorStatisticsExcelPlans.setInvestAmount(investorStatisticsForBoss.getBusinessObject().getInvestAmount());//投资金额
        investorStatisticsExcelPlans.setInvestCount(investorStatisticsForBoss.getBusinessObject().getInvestCount());//投资笔数
        investorStatisticsExcelPlans.setInvestNumber(investorStatisticsForBoss.getBusinessObject().getInvestNumber());//投资人数
        investorStatisticsExcelPlans.setMaxInvestAmount(investorStatisticsForBoss.getBusinessObject().getMaxInvestAmount());//最大单笔投资金额
        investorStatisticsExcelPlans.setAverageInvestAmount(investorStatisticsForBoss.getBusinessObject().getAverageInvestAmount());//平均投资金额
        investorStatisticsExcelPlans.setAveragePersonInvestAmount(investorStatisticsForBoss.getBusinessObject().getAveragePersonInvestAmount());//平均每人投资金额
        investorStatisticsExcelPlans.setTotalRedMoneyAmount(investorStatisticsForBoss.getBusinessObject().getTotalRedMoneyAmount());//红包使用金额
        investorStatisticsExcelPlans.setMaxPersonInvestAmount(investorStatisticsForBoss.getBusinessObject().getMaxPersonInvestAmount());//单人最大投资金额
        investorStatisticsExcelPlans.setAverageInvestCost(investorStatisticsForBoss.getBusinessObject().getAverageInvestCost());//平均用户投资成本
        investorStatisticsExcelPlans.setRechargeAmount(investorStatisticsForBoss.getBusinessObject().getRechargeAmount());//充值金额
        investorStatisticsExcelPlans.setWithdrawAmount(investorStatisticsForBoss.getBusinessObject().getWithdrawAmount());//提现金额

        return investorStatisticsExcelPlans;
    }
}

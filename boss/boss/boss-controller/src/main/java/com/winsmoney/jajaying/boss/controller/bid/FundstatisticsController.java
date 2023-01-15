package com.winsmoney.jajaying.boss.controller.bid;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.deposit.service.IInfoService;
import com.winsmoney.jajaying.deposit.service.request.BaseReqVo;
import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;
import com.winsmoney.jajaying.trade.service.IAutoInvestSettingService;
import com.winsmoney.jajaying.trade.service.enums.AutoInvestEnableStatus;
import com.winsmoney.jajaying.trade.service.enums.AutoInvestSettingStatus;
import com.winsmoney.jajaying.trade.service.request.AutoInvestSettingReqVo;
import com.winsmoney.jajaying.trade.service.response.AutoInvestSettingResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.Money;

@Controller
@RequestMapping("/automatic_bidding/fundstatistics")
public class FundstatisticsController {


    private static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(FundstatisticsController.class);

    @Autowired
    private IAutoInvestSettingService autoInvestSettingService;
    @Autowired
    private IInfoService infoService;
    @RequestMapping("/userInvestStatistics")
    @ResponseBody
    public List<AutoInvestSettingResVo> list(AutoInvestSettingReqVo autoInvestSettingReqVo) {
        int pages = 1;
        List userIdList = new ArrayList<>();
        TradeCommonResult<List<AutoInvestSettingResVo>> autoInvestSettingList = autoInvestSettingService.selectAutoInvestSettings();
        if (autoInvestSettingList.isFailure()) {
            logger.error("交易信息查询失败###errorMsg" + JSONObject.toJSONString(autoInvestSettingList.getBusinessObject()));
        }
        if (autoInvestSettingList.getBusinessObject().size() != 0) {
            if (!autoInvestSettingList.getBusinessObject().isEmpty()) {
                for (AutoInvestSettingResVo autoInvestSettingResVo : autoInvestSettingList.getBusinessObject()) {
                    Money totalAmount = new Money();
                    AutoInvestSettingReqVo condition = new AutoInvestSettingReqVo();
                    condition.setInvestTermMin(autoInvestSettingResVo.getInvestTermMin());
                    condition.setInvestTermMax(autoInvestSettingResVo.getInvestTermMax());
                    condition.setStatus(AutoInvestSettingStatus.ON.getCode());
                    condition.setEnable(AutoInvestEnableStatus.ON);
                    //查询已投资金额
                    for (int pageNo = 1; pageNo <= pages; pageNo++) {
                        TradeCommonResult<PageInfo<AutoInvestSettingResVo>> autoInvestSettingDetail = autoInvestSettingService.list(condition, pageNo, 1000);
                        if (!autoInvestSettingDetail.getBusinessObject().isIsLastPage()) {
                            pages++;
                        }

                        List<AutoInvestSettingResVo> list = autoInvestSettingDetail.getBusinessObject().getList();
                        for (int i = 0; i < list.size(); i++) {
                            userIdList.add(list.get(i).getUserId());
                            autoInvestSettingReqVo.setUserIds(userIdList);
                            //查询用户账户余额
                            
                            
                            BaseReqVo baseReqVo=new BaseReqVo();
                            baseReqVo.setUserId(list.get(i).getUserId());
                            DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> result = infoService.queryUserInfo(baseReqVo);
                            if(result.isFailure()) {
                            	totalAmount.addTo(new Money());
                            }else {
                            	if(result.getBusinessObject().getAvailableBalance() != null && result.getBusinessObject() != null) {
                            		totalAmount.addTo(result.getBusinessObject().getAvailableBalance());
                            	}else {
                            		totalAmount.addTo(new Money());
                            	}
                            }
                            
//                            AccountCommonResult<MainAccountInfoResVo> balanceAccountResult = queryAccountService.queryGateWayAccountInfo(list.get(i).getUserId());
//                            MainAccountInfoResVo accountInfo = balanceAccountResult.getBusinessObject();
//                            if (balanceAccountResult.isSuccess() && StringUtils.isNotBlank(accountInfo.getId())) {
//                                totalAmount.addTo(accountInfo.getBalance().subtract(accountInfo.getFreezeAmount()));
//                            }
                        }

                        autoInvestSettingReqVo.setStartTime(autoInvestSettingReqVo.getStartTime());
                        autoInvestSettingReqVo.setEndTime(autoInvestSettingReqVo.getEndTime());
                        TradeCommonResult<Money> fund = autoInvestSettingService.userFundStatic(autoInvestSettingReqVo);
                        totalAmount.addTo(fund.getBusinessObject());
                    }
                    autoInvestSettingResVo.setTotalAmount(totalAmount);
                }
            }
        }
        return autoInvestSettingList.getBusinessObject();
    }
}

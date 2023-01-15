package com.winsmoney.jajaying.boss.controller.trade;

import com.winsmoney.jajaying.trade.service.request.MoneyRecordReqVo;

/**
 * Created by chenkai1 on 2017/8/17.
 */
public class TradeBizReqForm extends MoneyRecordReqVo {
    private String registerMobile;

    public String getRegisterMobile() {
        return registerMobile;
    }

    public void setRegisterMobile(String registerMobile) {
        this.registerMobile = registerMobile;
    }
}

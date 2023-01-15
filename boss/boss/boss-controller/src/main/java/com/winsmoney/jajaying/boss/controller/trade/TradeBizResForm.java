package com.winsmoney.jajaying.boss.controller.trade;

import com.winsmoney.jajaying.trade.service.response.MoneyRecordResVo;

/**
 * Created by chenkai1 on 2017/8/17.
 */
public class TradeBizResForm extends MoneyRecordResVo {
    private String registerMobile;

    public String getRegisterMobile() {
        return registerMobile;
    }

    public void setRegisterMobile(String registerMobile) {
        this.registerMobile = registerMobile;
    }
}

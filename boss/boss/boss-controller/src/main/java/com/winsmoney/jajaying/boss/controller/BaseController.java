/**
* Project Name:mgt
* File Name:BaseController.java
* Package Name:com.winsmoney.jajaying.mgt.controller
* Date:2016年4月16日下午1:08:47
* Copyright (c) 2016, wangbinlei@jajaying.com All Rights Reserved.
*/

package com.winsmoney.jajaying.boss.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.winsmoney.jajaying.deposit.service.IInfoService;
import com.winsmoney.jajaying.deposit.service.request.BaseReqVo;
import com.winsmoney.jajaying.deposit.service.response.DepositCommonResult;

/**
* Description: TODO ADD Description. <br/>
* Date: 2016年4月16日 下午1:08:47 <br/>
* @author wangbinlei
* @since JDK 1.7
*/
@Controller
public class BaseController {
    @Autowired
    private IInfoService infoService;
    @InitBinder    
    public void initBinder(WebDataBinder binder) {    
 
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
        
        DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor startEditor = new CustomDateEditor(dayFormat, true);
        binder.registerCustomEditor(Date.class, "startDate", startEditor);

        CustomDateEditor endEditor = new CustomDateEditor(dayFormat, true);
        binder.registerCustomEditor(Date.class, "endDate", endEditor);
        
        DateFormat dayFormatebds = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor endDateStartEditor = new CustomDateEditor(dayFormatebds, true);
		binder.registerCustomEditor(Date.class, "endDateStart", endDateStartEditor);
		
		DateFormat dayFormatebde = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor endDateEndEditor = new CustomDateEditor(dayFormatebde, true);
		binder.registerCustomEditor(Date.class, "endDateEnd", endDateEndEditor);
		
		
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CustomDateEditor shipDateEditor = new CustomDateEditor(dateFormat2, true);
		binder.registerCustomEditor(Date.class, "openTime", shipDateEditor);

        DateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor startDateEditor = new CustomDateEditor(startDateFormat, true);
        binder.registerCustomEditor(Date.class, "activityStartTime", startDateEditor);

        DateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor endDateEditor = new CustomDateEditor(endDateFormat, true);
        binder.registerCustomEditor(Date.class, "activityEndTime", endDateEditor);

        DateFormat awardDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor awardDateEditor = new CustomDateEditor(awardDateFormat, true);
        binder.registerCustomEditor(Date.class, "awardTime", awardDateEditor);


    }
    
    public com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo getBalance(String userId) {
    	  BaseReqVo baseReqVo=new BaseReqVo();
          baseReqVo.setUserId(userId);
          DepositCommonResult<com.winsmoney.jajaying.deposit.service.response.info.UserInfoResVo> userResult = infoService.queryUserInfo(baseReqVo);
          return userResult.getBusinessObject();
    }
}

    
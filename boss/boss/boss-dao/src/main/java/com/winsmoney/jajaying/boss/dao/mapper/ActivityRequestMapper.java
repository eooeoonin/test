/**
 * Project:boss
 * File:ActivityRequest.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.mapper;

import com.winsmoney.platform.framework.core.model.BaseMapper;
import com.winsmoney.jajaying.boss.dao.po.ActivityRequestPo;

import java.math.BigDecimal;

/**
* Description: 活动请求 DAO接口 mapper
* date: 2017-11-23 02:43:12
* @author: CodeCreator
*/
public interface ActivityRequestMapper extends BaseMapper<ActivityRequestPo>
{

    BigDecimal getSum(String activityId);

}

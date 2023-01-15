/**
 * Project:boss
 * File:Activity.java
 * Date:2017-09-04
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.mapper;

import com.winsmoney.platform.framework.core.model.BaseMapper;
import com.winsmoney.jajaying.boss.dao.po.ActivityPo;

import java.util.Date;
import java.util.List;

/**
* Description: 活动表 DAO接口 mapper
* date: 2017-09-04 05:21:24
* @author: CodeCreator
*/
public interface ActivityMapper extends BaseMapper<ActivityPo>
{

    List<ActivityPo> activityList( Date tradeTime );
}

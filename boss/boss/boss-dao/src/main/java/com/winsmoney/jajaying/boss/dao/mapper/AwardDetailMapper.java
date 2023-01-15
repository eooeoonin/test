/**
 * Project:boss
 * File:AwardDetail.java
 * Date:2017-11-23
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.mapper;

import com.winsmoney.platform.framework.core.model.BaseMapper;
import com.winsmoney.jajaying.boss.dao.po.AwardDetailPo;

import java.util.List;

/**
* Description: 奖品详情(抽奖号码) DAO接口 mapper
* date: 2017-11-23 02:43:12
* @author: CodeCreator
*/
public interface AwardDetailMapper extends BaseMapper<AwardDetailPo>
{

    int deleteByActivityCode(String activityCode);

    List<AwardDetailPo> getSunShines(AwardDetailPo awardDetailPo);

    Integer getCount(String activityId);

    int updateUserIdNull(AwardDetailPo map);

    List<AwardDetailPo> listForMastor(AwardDetailPo param);

    List<String> listByActivityCode(String activityCode);
}

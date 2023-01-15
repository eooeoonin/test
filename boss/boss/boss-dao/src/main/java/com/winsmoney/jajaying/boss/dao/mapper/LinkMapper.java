/**
 * Project:boss
 * File:Link.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.dao.mapper;

import com.winsmoney.platform.framework.core.model.BaseMapper;
import com.winsmoney.jajaying.boss.dao.po.LinkPo;
/**
* Description: 链接表 DAO接口 mapper
* date: 2017-08-31 05:16:26
* @author: CodeCreator
*/
public interface LinkMapper extends BaseMapper<LinkPo>
{
	int deleteByAcode(String code);
}

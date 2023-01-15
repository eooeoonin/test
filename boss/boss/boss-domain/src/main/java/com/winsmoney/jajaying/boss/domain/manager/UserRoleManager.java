/**
* Project:boss
* File:UserRole.java
* Date:2016-08-03
* Copyright (c) 2016 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.UserRoleMapper;
import com.winsmoney.jajaying.boss.dao.po.UserRolePo;
import com.winsmoney.jajaying.boss.domain.model.UserRole;

/**
* Description:  管理器
* date: 2016-08-03 06:03:18
* @author: CodeCreator
*/
@Component
public class UserRoleManager extends BaseManager<UserRole,UserRoleMapper,UserRolePo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(UserRoleManager.class);


}



/**
* Project:boss
* File:UserRole.java
* Date:2016-08-03
* Copyright (c) 2016 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.UserRoleManager;
import com.winsmoney.jajaying.boss.domain.model.Menu;
import com.winsmoney.jajaying.boss.domain.model.Resource;
import com.winsmoney.jajaying.boss.domain.model.UserRole;
import com.winsmoney.jajaying.boss.domain.utils.GenerateRedisKeyUtils;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.redis.map.MapManager;
import com.winsmoney.framework.pagehelper.PageInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
* ClassName: userRole
* Description:  实现
* date: 2016-08-03 06:03:18
* @author: CodeCreator
*/
@Service
public class UserRoleDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(UserRoleDomain.class);

    @Autowired
    private UserRoleManager userRoleManager;
    @Autowired
    private MenuDomain menuDomain;
    @Autowired
    private ResourceDomain resourceDomain;

    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public UserRole getById(String id)
    {
        UserRole r=userRoleManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public UserRole getUserRole(UserRole condition)
    {
        UserRole result= userRoleManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int countUserRole(UserRole condition)
    {
        int result = userRoleManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<UserRole> listUserRole(UserRole condition)
    {
        List<UserRole> result = userRoleManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<UserRole> listUserRole(UserRole condition, int pageNo, int pageSize)
    {
        PageInfo<UserRole> result = userRoleManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insertUserRole(UserRole value)
    {
        int result = userRoleManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int updateUserRole(UserRole value)
    {
        int result = userRoleManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int deleteUserRole(String id)
    {
        int result = userRoleManager.delete(id);
        return result;
    }
    
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertRoleAndRescources(UserRole userRole) {
		int status = userRoleManager.insert(userRole);
		if (status > 0) {
			insertResourceBatch(userRole);
		}
		return status;
	}

	private void insertResourceBatch(UserRole userRole) {
		String permissionIds = userRole.getPermissionIds();
		String[] permissionArray = permissionIds.split(",");
		for (String menuId : permissionArray) {
			if (StringUtils.isNotBlank(menuId)) {
				Menu menu = menuDomain.getById(menuId);
				Resource resource = new Resource();
				resource.setMenuId(menuId);
				resource.setText(menu.getText());
				resource.setRoleId(userRole.getId());
				resource.setRemark(menu.getText());
				resourceDomain.insertResource(resource);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int updateUserRoleAndResources(UserRole userRole) {
		int result = userRoleManager.update(userRole);
		if(result > 0){
			resourceDomain.deleteResourcesByRoleId(userRole.getId());
			insertResourceBatch(userRole);
		}
		return result;
	}
	
	public UserRole getUserRoleByName(String roleName) {
		UserRole condition = new UserRole();
		condition.setRoleName(roleName);
		UserRole result= userRoleManager.get(condition);
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteUserRoleAndResources(String roleId) {
		int result = userRoleManager.delete(roleId);
		if(result > 0){
			resourceDomain.deleteResourcesByRoleId(roleId);
		}
		return result;
	}
}

/**
* Project:boss
* File:Menu.java
* Date:2016-08-03
* Copyright (c) 2016 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.domain.manager.MenuManager;
import com.winsmoney.jajaying.boss.domain.model.Menu;
import com.winsmoney.jajaying.boss.domain.model.MenuBo;
import com.winsmoney.jajaying.boss.domain.model.Resource;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;


/**
* ClassName: menu
* Description:  实现
* date: 2016-08-03 06:03:17
* @author: CodeCreator
*/
@Service
public class MenuDomain
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(MenuDomain.class);

    @Autowired
    private MenuManager menuManager;
    @Autowired
    private ResourceDomain resourceDomain;

    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public Menu getById(String id)
    {
        Menu r=menuManager.getById(id);
        return  r;
    }

    /**
     * 取得记录
     */
    public Menu getMenu(Menu condition)
    {
        Menu result= menuManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int countMenu(Menu condition)
    {
        int result = menuManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<Menu> listMenu(Menu condition)
    {
        List<Menu> result = menuManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<Menu> listMenu(Menu condition, int pageNo, int pageSize)
    {
        PageInfo<Menu> result = menuManager.list(condition,pageNo,pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insertMenu(Menu value)
    {
        int result = menuManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int updateMenu(Menu value)
    {
        int result = menuManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int deleteMenu(String id)
    {
        int result = menuManager.delete(id);
        return result;
    }

	/**
	 * 查询所有一级菜单
	 * @return
	 */
	public List<MenuBo> findAllOneLevelMenus() {
		return menuManager.findAllOneLevelMenus();
	}
	
	/**
	 * 根据父节点查询所有子节点
	 * @param parentId
	 * @return
	 */
	public List<MenuBo> findChildMenusByParentId(String parentId) {
		return menuManager.findChildMenusByParentId(parentId);
	}
	
	public List<MenuBo> findMenusByRoleId(String roleId) {
		List<MenuBo> lastMenuToShow = new ArrayList<MenuBo>();
		List<MenuBo> oneLevelMenus = findAllOneLevelMenus();
		List<Resource> resources = resourceDomain.findResourcesByRoleId(roleId);
		//根据该角色的资源比对menu表中的数据，将合格菜单和其子菜单挑选出来放在oneLevelMenus中
		for(MenuBo paremntMenu: oneLevelMenus){
			List<MenuBo> oneLevelChildList = findChildMenusByParentId(paremntMenu.getId());
			List<MenuBo> userMenuList = new ArrayList<MenuBo>();
			for(MenuBo menu: oneLevelChildList){
				for(Resource resource : resources){
					if(menu.getId().equals(resource.getMenuId())){
						userMenuList.add(menu);
						break;  //跳出resource循环，重新开始上一层 oneLevelChildList的比对
					}
				}
			}
			paremntMenu.setChildren(userMenuList);
		}
		
		//oneLevelMenus中没有子菜单的不显示
		for(MenuBo paremntMenu: oneLevelMenus){
			for(Resource resource : resources){
				if(!"#".equals(paremntMenu.getUrl()) && paremntMenu.getId().equals(resource.getMenuId())){
					lastMenuToShow.add(paremntMenu);
					break;
				}else if(!paremntMenu.getChildren().isEmpty()){
					lastMenuToShow.add(paremntMenu);
					break;
				}
				
			}
		}
		
		return lastMenuToShow;
	}

}

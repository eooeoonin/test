package com.winsmoney.jajaying.boss.controller.login;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.MenuDomain;
import com.winsmoney.jajaying.boss.domain.ResourceDomain;
import com.winsmoney.jajaying.boss.domain.StaffDomain;
import com.winsmoney.jajaying.boss.domain.UserRoleDomain;
import com.winsmoney.jajaying.boss.domain.model.Menu;
import com.winsmoney.jajaying.boss.domain.model.MenuBo;
import com.winsmoney.jajaying.boss.domain.model.Resource;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.model.UserRole;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;


/**
 *角色管理
 */
@Controller
@RequestMapping(value = "/permission/role")
public class AdminRoleController {

	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AdminRoleController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @Autowired
    private UserRoleDomain userRoleDomain;
    @Autowired
    private MenuDomain menuDomain;
    @Autowired
    private ResourceDomain resourceDomain;
    @Autowired
    private StaffDomain staffDomain;

    /**
     * 所有角色列表
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    public Result departments() {
        List<UserRole> listRoles = userRoleDomain.listUserRole(new UserRole());
        return Result.success(listRoles);
    }

    /**
     * 角色列表分页
     */
    @RequestMapping(value = "/rolesWithPage")
    @ResponseBody
    public Result departmentsWithPage(UserRole userRole, int pageNo, int pageSize) {
        PageInfo<UserRole> listDepartment = userRoleDomain.listUserRole(userRole, pageNo, pageSize);
        return Result.success(listDepartment);
    }


    /**
     * 编辑单个角色
     */
    @RequestMapping(value = "/get/{roleId}", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "编辑角色")
    public Result get(@PathVariable("roleId") String roleId) {
        UserRole department = userRoleDomain.getById(roleId);
        List<MenuBo> menus = menuDomain.findMenusByRoleId(roleId);
        department.setMenus(menus);//编辑的目标用户的权限
        return Result.success(department);
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/delete/{roleId}", method = RequestMethod.DELETE)
    @ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除角色")
    public Result delete(@PathVariable("roleId") String roleId) {
        Staff condition = new Staff();
        condition.setRoleId(roleId);
        List<Staff> listStaff = staffDomain.listStaff(condition);
        if (null != listStaff && listStaff.size() > 0 && null != listStaff.get(0).getId()) {
            return Result.error("此角色下还有员工，请先删除此角色下的所有员工");
        }

        int status = userRoleDomain.deleteUserRoleAndResources(roleId);
        if (status > 0) {
            return Result.success(SUCCESS);
        } else
            return Result.error(FAIL);
    }

    /**
     * 新增或更新角色
     *
     * @param userRole
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增角色")
    public Result addDepartment(UserRole userRole) {
        int status = 0;
        try {
            UserRole userRoleByName = userRoleDomain.getUserRoleByName(userRole.getRoleName());
            if (null != userRoleByName.getId() && !userRoleByName.getId().equals(userRole.getId())) {
                return Result.error("角色名已存在，不能重复添加或修改");
            }
            if (StringUtils.isNotEmpty(userRole.getId())) {
                status = userRoleDomain.updateUserRoleAndResources(userRole);
            } else {
                status = userRoleDomain.insertRoleAndRescources(userRole);
            }

        } catch (Exception e) {
        	logger.error("新增或更新角色失败", e);
            return Result.error("新增或更新角色失败");
        }
        if(status > 0){
        	 return Result.success(status);
        }else{
        	 return Result.error("新增或更新角色失败");
        }
    }


    /**
     * 展示当前登录用户的Menu菜单
     */
    @RequestMapping(value = "/getMenusLogined", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenus(HttpServletRequest request) {
        Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
        List<MenuBo> menus = new ArrayList<MenuBo>();
        if (null != staffSession) {
            String roleId = staffSession.getRoleId();
            menus = menuDomain.findMenusByRoleId(roleId);
        }
        return Result.success(menus);
    }
    /**
     * 展示当前登录用户的Menu菜单
     */
    @RequestMapping(value = "/getMenusLogineds", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuss(HttpServletRequest request) {
        Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
        List<MenuBo> menus = new ArrayList<MenuBo>();
        if (null != staffSession) {
            String roleId = staffSession.getRoleId();
            menus = menuDomain.findMenusByRoleId("1");
        }
        return Result.success(menus);
    }

    /**
     * 该用户的resourceId
     */
    @RequestMapping(value = "/resources/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public Result getResources(@PathVariable("roleId") String roleId) {
        List<Resource> permissions = resourceDomain.getPermissonByRoleId(roleId);
        return Result.success(permissions);
    }
    /**
     * 插入菜单MENU表
     */
	@RequestMapping(value = "/insertMenu", method = RequestMethod.POST)
	@ResponseBody
	public String insertMenu(Menu value){
		 menuDomain.insertMenu(value);
	        return value.getId();	
	}
	 /**
     * 插入菜单resource表
     */
	@RequestMapping(value = "/insertResource", method = RequestMethod.POST)
	@ResponseBody
    public int insertResource(Resource value,HttpServletRequest request){
		Staff staffSession =  (Staff)request.getSession().getAttribute("adminInfo");	
		value.setRoleId(staffSession.getRoleId());
        return resourceDomain.insertResource(value);
    }
	/**
	 * 查询所有一级菜单
	 * @return
	 */
	@RequestMapping(value = "/selectMenu", method = RequestMethod.POST)
	@ResponseBody
	public List<MenuBo> selectMenu() {
		List<MenuBo> Onemenu = new ArrayList<MenuBo>();
		Onemenu = menuDomain.findAllOneLevelMenus();
		return Onemenu;
	}
	
	/**
	 * 根据父节点查询所有子节点
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/selectMenuTwo", method = RequestMethod.POST)
	@ResponseBody
	public List<MenuBo> selectMenuTwo(String parentId) {
		List<MenuBo> Twomenu = new ArrayList<MenuBo>();
		Twomenu = menuDomain.findChildMenusByParentId(parentId);
		
		return Twomenu;
	}
	/**
     * 根据id取得记录
     */
	@RequestMapping(value = "/selectMenuEdit", method = RequestMethod.POST)
	@ResponseBody
    public Menu selectMenuEdit(String id){
		Menu r=	menuDomain.getById(id);     
        return  r;
    }
	/**
     * 更新menu表
     */
	@RequestMapping(value = "/OneMenuEdit", method = RequestMethod.POST)
	@ResponseBody
    public int OneMenuEdit(Menu value){
		int result = menuDomain.updateMenu(value);  
        return result;
    }
    
	
	 /**
     * 更新resource表
     */
	@RequestMapping(value = "/OneResourceEdit", method = RequestMethod.POST)
	@ResponseBody
	public int OneResourceEdit(Resource value){
		int result = resourceDomain.updateResource(value);
		return result;
	}
	/**
     * 删除menu表
     */
	@RequestMapping(value = "/OneMenudelete", method = RequestMethod.POST)
	@ResponseBody
	public  int OneMenudelete(String id){
		int result = menuDomain.deleteMenu(id);
		return result;
		
	}
	/**
     * 删除resource表
     */
	@RequestMapping(value = "/OneResourcedelete", method = RequestMethod.POST)
	@ResponseBody
	public int OneResourcedelete(String id){
		
		int result = resourceDomain.deleteResource(id);
		return result;
	}
}

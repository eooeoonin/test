package com.winsmoney.jajaying.boss.controller.login;

import java.util.List;

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
import com.winsmoney.jajaying.boss.domain.DepartmentDomain;
import com.winsmoney.jajaying.boss.domain.StaffDomain;
import com.winsmoney.jajaying.boss.domain.model.Department;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

/**
 *部门管理
 */
@Controller
@RequestMapping(value = "/permission/department")
public class AdminDepartmentController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AdminDepartmentController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	@Autowired
	private DepartmentDomain departmentDomain;
	@Autowired
	private StaffDomain staffDomain;
	
	
	
	/**
	 * 所有部门列表
	 */
	@RequestMapping(value = "/departments", method = RequestMethod.GET)
	@ResponseBody
	public Result departments(){
		List<Department> listDepartments = departmentDomain.listDepartment(new Department());
		return Result.success(listDepartments);
	}
	/**
	 * 部门列表分页
	 */
	@RequestMapping(value = "/departmentsWithPage")
	@ResponseBody
	public Result departmentsWithPage(Department department, int pageNo, int pageSize){
		try{
			PageInfo<Department> listDepartment = departmentDomain.listDepartment(department, pageNo, pageSize);
			return Result.success(listDepartment);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 取得单个部门
	 */
	@RequestMapping(value = "/get/{departmentId}", method = RequestMethod.GET)
	@ResponseBody
	public Result get(@PathVariable("departmentId") String departmentId){
		Department department = departmentDomain.getById(departmentId);
		return Result.success(department);
	}
	
	/**
	 * 删除部门
	 */
	@RequestMapping(value = "delete/{departmentId}", method = RequestMethod.DELETE)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除部门")
	public Result delete(@PathVariable("departmentId") String departmentId){
		Staff condition = new Staff();
		condition.setDepartmentId(departmentId);
		List<Staff> listStaff = staffDomain.listStaff(condition);
		if(null != listStaff && listStaff.size() > 0 && null != listStaff.get(0).getId()){
			return Result.error("此部门下还有员工，请先删除此部门下的所有员工");
		}
		
		int status = departmentDomain.deleteDepartment(departmentId);
		if(status > 0){
			return Result.success(SUCCESS);
		}else
			return Result.error(FAIL);
	}
	
	/**
	 * 新增或更新部门
	 * @param department
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增或更新部门")
	public Result addDepartment(Department department){
		int status = 0;
		try {
			Department departmentByName = departmentDomain.getDepartmentByName(department.getDepartmentName());
			if (null != departmentByName.getId() && !department.getId().equals(departmentByName.getId())) {
				return Result.error("该部门名称已经存在");
			}

			Department departmentByCode = departmentDomain.getDepartmentByCode(department.getDepartmentCode());
			if (null != departmentByCode.getId() && !departmentByCode.getId().equals(department.getId())) {
				return Result.error("该部门代码已经存在");
			}

			if (StringUtils.isNotEmpty(department.getId())) {
				status = departmentDomain.updateDepartment(department);
			} else
				status = departmentDomain.insertDepartment(department);
		} catch (Exception e) {
			logger.error("新增或更新部门失败", e);
			return Result.error(FAIL);
		}
		if (status > 0) {
			return Result.success(status);
		} else {
			return Result.error("新增或更新部门失败");
		}

	}
	
}

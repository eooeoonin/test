package com.winsmoney.jajaying.boss.controller.login;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.DepartmentDomain;
import com.winsmoney.jajaying.boss.domain.EmailDomain;
import com.winsmoney.jajaying.boss.domain.StaffDomain;
import com.winsmoney.jajaying.boss.domain.UserRoleDomain;
import com.winsmoney.jajaying.boss.domain.enums.StaffStatus;
import com.winsmoney.jajaying.boss.domain.model.Department;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.model.UserRole;
import com.winsmoney.jajaying.boss.domain.utils.MD5;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.LocalIPUtils;
import com.winsmoney.platform.framework.mq.producer.WinsMoneyMQProducer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 员工管理
 */
@Controller
@RequestMapping(value = "/permission/staff")
public class AdminStaffController {

    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AdminStaffController.class);

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @Autowired
    private StaffDomain staffDomain;
    @Autowired
    private DepartmentDomain departmentDomain;
    @Autowired
    private UserRoleDomain userRoleDomain;
    @Autowired
    private WinsMoneyMQProducer winsMoneyMQProducer;
    @Value("${boss_host:\"\"}")
    private String host;
    @Autowired
    private EmailDomain emailDomain;
    private String mailSubject = "【Boss】用户激活";

    /**
     * 员工列表分页
     */
    @RequestMapping(value = "/staffsWithPage")
    @ResponseBody
    public Result staffsWithPage(Staff staff, int pageNo, int pageSize) {
        PageInfo<Staff> listDepartment = staffDomain.listStaff(staff, pageNo, pageSize);
        List<Staff> staffList = listDepartment.getList();
        for (Staff bean : staffList) {
            Department department = departmentDomain.getById(bean.getDepartmentId());
            UserRole userRole = userRoleDomain.getById(bean.getRoleId());
            if (null != department) {
                bean.setDepartmentName(department.getDepartmentName());
            }
            if (null != userRole) {
                bean.setRoleName(userRole.getRoleName());
            }
        }

        return Result.success(listDepartment);
    }


    /**
     * 取得单个员工
     */
    @RequestMapping(value = "/get/{staffId}", method = RequestMethod.GET)
    @ResponseBody
    public Result get(@PathVariable("staffId") String staffId) {
        Staff staff = staffDomain.getById(staffId);
        return Result.success(staff);
    }

    /**
     * 停用员工
     */
    @RequestMapping(value = "/delete/{staffId}", method = RequestMethod.DELETE)
    @ResponseBody
	@AduitLog(type = OperType.DELETE, content = "停用员工")    public Result delete(@PathVariable("staffId") String staffId) {
        int status = 0;
        try {
            Staff staffByStaffName = staffDomain.getById( staffId );

            if (StringUtils.isNotEmpty(staffByStaffName.getId())) {
                staffByStaffName.setStatus(StaffStatus.DISABLED.toString());
                status = staffDomain.updateStaff(staffByStaffName);
            } else {
                return Result.error("该用户不存在");
            }
        } catch (Exception e) {
            logger.error("停用员工失败", e);
            return Result.error("停用员工失败");
        }
        if(status > 0){
            return Result.success("停用员工成功");
        }else{
            return Result.error("停用员工失败");
        }
    }

    /**
     * 新增或更新员工
     *
     * @param staff
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增或更新员工")
    public Result addStaff(Staff staff, HttpServletRequest request) {
        String[] a = staff.getEmail().split("@");
        String b = a[1];
        if (!"jajaying.com".equals(b)) {
            return Result.error("邮箱必须为jajaying.com");
        }
        int status = 0;
        try {
            Staff staffByStaffName = staffDomain.getStaffByStaffName(staff.getStaffName());
            if (null != staffByStaffName.getId() && !staff.getId().equals(staffByStaffName.getId())) {
                return Result.error("该员工名称已经存在");
            }
            Staff staffByIdCard = staffDomain.getStaffByIdCard(staff.getIdCard());
            if (null != staffByIdCard.getId() && !staff.getId().equals(staffByIdCard.getId())) {
                return Result.error("该员工身份证号已经存在");
            }

            if (StringUtils.isNotEmpty(staff.getId())) {
                status = staffDomain.updateStaff(staff);
            } else {
                //新增用户 状态初始（未激活） 初始密码（123456）
                staff.setStatus(StaffStatus.INIT.toString());
                staff.setPassword("123456");
                status = staffDomain.insertStaff(staff);
            }
        } catch (Exception e) {
            logger.error("新增或更新员工失败", e);
            return Result.error(FAIL);
        }
        if (status > 0) {
            Staff result = staffDomain.getStaffByStaffName(staff.getStaffName());
            if (StaffStatus.ACTIVATED.toString().equals(result.getStatus())){
            	return Result.success(status);
            } else {
            	//发送mail
            	sendEmail(result.getEmail(), result.getStaffName(), request);
            	return Result.success(status);
            }
        } else {
            return Result.error("新增或更新员工失败");
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public Result changePassword(String staffId, String newPassword) {
        try {
            Staff staffById = staffDomain.getById(staffId);
            staffById.setPassword(MD5.encodeByMd5AndSalt(newPassword));
            int status = staffDomain.updateStaff(staffById);
            if (status > 0) {
                return Result.success(status);
            }
        } catch (Exception e) {
            logger.error("修改员工密码出错", e);
        }
        return Result.error("修改员工密码出错");

    }

    /**
     * 激活用户
     * @param staff
     * @return
     */
    @RequestMapping(value = "/userActivated", method = RequestMethod.POST)
    @ResponseBody
    public Result userActivated(Staff staff){
        int status = 0;
        try {
            Staff staffByStaffName = staffDomain.getStaffByStaffName(staff.getStaffName());

            if (StringUtils.isNotEmpty(staffByStaffName.getId())&&!StaffStatus.DISABLED.toString().equals(staffByStaffName.getStatus())) {
                staff = BeanMapper.map(staffByStaffName, Staff.class);
                staff.setStatus(StaffStatus.ACTIVATED.toString());
                status = staffDomain.updateStaff(staff);
            } else {
                return Result.error("该用户不存在");
            }
        } catch (Exception e) {
            logger.error("激活失败", e);
            return Result.error("激活失败");
        }
        if(status > 0){
            return Result.success("激活成功");
        }else{
            return Result.error("激活失败");
        }
    }

    private void sendEmail(String to, String staffName, HttpServletRequest request) {
        String url = createUrl(staffName, request);
        emailDomain.send(new String[]{to}, mailSubject, template(staffName, url), true);
    }
    private String createUrl(String staffName, HttpServletRequest request) {
        if (this.host == null || this.host.isEmpty()) {
            this.host = LocalIPUtils.getIp4Single();
        }
        if (!host.startsWith("http://") && !host.startsWith("https://")) {
            host = "http://" + host + ":" + request.getServerPort();
        }
        String url = host + "/userActivated.html?staffName=" + staffName;
        return url;
    }

    private String template(String staffName, String url) {
        String message = "用户名：" + staffName + "，初始密码：123456，请激活你的用户，<a target=\"_blank\" href=\"" + url + "\">点击</a>";
        return message;
    }
    
    /**
     * 发送激活邮件
     * @param staff
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public String sendEmail(Staff staff, HttpServletRequest request) {
    	try {
    		Staff result = staffDomain.getStaffByStaffName(staff.getStaffName());
            //发送mail
            sendEmail(result.getEmail(), result.getStaffName(), request);
            return SUCCESS;
		} catch (Exception e) {
			return FAIL;
		}
}

}
package com.winsmoney.jajaying.boss.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.enums.StaffStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.jajaying.boss.domain.StaffDomain;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.MD5;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

/**
 *登录退出管理
 */
@Controller
public class AdminUserController {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AdminUserController.class);
    public static final String USERNAME = "adminInfo";

    @Autowired
    private StaffDomain staffDomain;

    private static final String SUCCESS = "success";
    private static final String CHANGEPSW = "change";
    private static final String FAIL = "fail";

    @RequestMapping(value = "/admin/login")
    @ResponseBody
	public Result login(Staff staff, HttpServletRequest request) {
		try {
			Staff staffReq = new Staff();
			staffReq.setStaffName(staff.getStaffName());
			staffReq.setPassword(MD5.encodeByMd5AndSalt(staff.getPassword()));
			Staff adminInfo = staffDomain.getStaff(staffReq);
			if (null != adminInfo && null != adminInfo.getId()) {
				//如果用户状态为未激活
				if("ACTIVATED".equals(adminInfo.getStatus())){
					if(MD5.encodeByMd5AndSalt("123456").equals(adminInfo.getPassword())){
						logger.info("boss系统登录成功: {}", adminInfo);
						request.getSession().setAttribute(USERNAME, adminInfo);
						return Result.success(CHANGEPSW);
					}else{
						logger.info("boss系统登录成功: {}", adminInfo);
						request.getSession().setAttribute(USERNAME, adminInfo);
						return Result.success(SUCCESS);
					}
				} else if("DISABLED".equals(adminInfo.getStatus())){
                    logger.info("boss系统登录失败: {}", adminInfo);
                    return Result.error("该用户已停用");
                } else {
					logger.info("boss系统登录失败: {}", adminInfo);
					return Result.error("该用户未激活");
				}
			} else {
				return Result.error("用户名或密码错误 ");
			}
		} catch (Exception e) {
			logger.error("系统异常", e);
			return Result.error("系统异常，登录失败 ");
		}
	}

    @RequestMapping(value = "/admin/logout")
    @ResponseBody
    @AduitLog(type = OperType.QUERY, content = "退出")
    public Result logout(HttpSession session) {
        try {
            Staff staff = (Staff) session.getAttribute(USERNAME);
            session.invalidate();
            logger.info("boss系统退出成功: {}", staff);
            return Result.success(SUCCESS);
        } catch (Exception e) {
            logger.error("退出登录失败", e);
            return Result.error("退出登录失败");
        }
    }

    @RequestMapping(value = "/admin/changePassword")
    @ResponseBody
    @AduitLog(type = OperType.UPDATE, content = "更改密码")
    public Result changePassword(HttpServletRequest request, String originPassword, String newPassword) {
        try {
            Staff staffSession = (Staff) request.getSession().getAttribute(USERNAME);

            String encodePassword = MD5.encodeByMd5AndSalt(originPassword);
            if (!encodePassword.equals(staffSession.getPassword())) {
                return Result.error("原密码错误");
            }
            Staff staff = staffDomain.getById(staffSession.getId());
            staff.setPassword(MD5.encodeByMd5AndSalt(newPassword));
            int status = staffDomain.updateStaff(staff);
            if (status > 0) {
                request.getSession().setAttribute(USERNAME, staff);
                return Result.success(SUCCESS);
            } else {
                return Result.error("修改密码失败");
            }

        } catch (Exception e) {
            logger.error("修改密码失败", e);
            return Result.error("修改密码失败");
        }
    }

    @RequestMapping(value = "/admin/getCurrentUser")
    @ResponseBody
    public Result getCurrentUser(HttpServletRequest request) {
        try {
            Staff staffSession = (Staff) request.getSession().getAttribute(USERNAME);
            return Result.success(staffSession);
        } catch (Exception e) {
            logger.error("取得当前登录用户信息出错", e);
            return Result.error("取得当前登录用户信息出错");
        }
    }
    
}

package com.winsmoney.jajaying.boss.controller;

import com.winsmoney.jajaying.boss.controller.login.AdminUserController;
import com.winsmoney.jajaying.boss.domain.OperLogDomain;
import com.winsmoney.jajaying.boss.domain.model.OperLog;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author howard he
 * @create 2018/7/10 15:57
 */

public class AuditLogInterceptor extends HandlerInterceptorAdapter {

    private WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(AuditLogInterceptor.class);
    @Autowired
    private OperLogDomain operLogDomain;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            createAduitLog(request, response, handlerMethod);
        }
    }

    private void createAduitLog(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        AduitLog annotation = handler.getMethod().getAnnotation(AduitLog.class);
        if (annotation == null) {
            return;
        }
        String code = annotation.code();
        if (code == null || code.isEmpty()) {
            code = handler.getMethod().getName();
        }
        Date curr = new Date();
        OperLog operLog = new OperLog();
        operLog.setCode(code);
        operLog.setContent(annotation.content());
        operLog.setInputData("");
        operLog.setOutputData("");
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        operLog.setOperAddress(ipAddress);
        Staff staff = (Staff) request.getSession().getAttribute(AdminUserController.USERNAME);
        if (staff != null) {
            operLog.setOperId(staff.getId());
            operLog.setOperName(staff.getRealName());
        }
        operLog.setOperTime(curr);
        operLog.setOperType(annotation.type());
        operLog.setCreateTime(curr);
        try {
            operLogDomain.create(operLog);
        } catch (Exception e) {
            logger.error("AuditLogInterceptor create log error, log: " + operLog, e);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}

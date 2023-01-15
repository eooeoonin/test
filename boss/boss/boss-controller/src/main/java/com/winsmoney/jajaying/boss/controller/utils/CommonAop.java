package com.winsmoney.jajaying.boss.controller.utils;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;


@Aspect    
@Component
public class CommonAop {

	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(CommonAop.class);
	
	@Before("execution (* com.winsmoney.jajaying.boss.controller..*.*(..))")
	 public void before(JoinPoint joinpoint){
		 HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();    
		 Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
		 String staffName = null;
		 if(staffSession != null)
			 staffName  = staffSession.getStaffName();
		 else
			 staffName = "未登录";
			 logger.info("username :" + staffName);
	 }
}

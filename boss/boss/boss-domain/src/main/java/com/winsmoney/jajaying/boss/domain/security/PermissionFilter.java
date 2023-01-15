//package com.winsmoney.jajaying.boss.domain.security;
//
//import java.util.Collection;
//import java.util.List;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.AccessControlFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.winsmoney.jajaying.boss.domain.ResourceDomain;
//import com.winsmoney.jajaying.boss.domain.model.Resource;
//
//@Component
//public class PermissionFilter extends AccessControlFilter {
//	private final static String LOGIN_URL = "/login.html";
//	private final static String NO_PERMISSION_URL = "/noPermission.html";
//
//	@Autowired
//	private ResourceDomain resourceDomain;
//	@Autowired
//	private SecurityRealm securityRealm;
//	@Override
//	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//		// 取到请求的uri ，进行权限判断
//		Subject subject = getSubject(request, response);
//		String uri = ((HttpServletRequest) request).getRequestURI();
//		boolean canAccessFlag = false;
////		if (isAjaxRequest(request)) {
////			return true;
////		}
//		if (StringUtils.isNotBlank(uri)) {
//			Collection<String> stringPermissions = securityRealm.doGetAuthorizationInfo(SecurityUtils.getSubject().getPrincipals()).getStringPermissions();
//			for (String permissionUrl : stringPermissions) {
//				if (!"#".equals(permissionUrl)) {
//					if (uri.startsWith(permissionUrl)) {
//						canAccessFlag = true;
//						break;
//					}
//				}
//			}
//		}
//
//		return canAccessFlag;
//	}
//
//	@Override
//	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//		Subject subject = getSubject(request, response);
//		if (null == subject.getPrincipal()) {// 表示没有登录，重定向到登录页面
//			saveRequest(request);
//			WebUtils.issueRedirect(request, response, LOGIN_URL);
//		} else {
//			WebUtils.issueRedirect(request, response, NO_PERMISSION_URL);
//		}
//		return false;
//	}
//
//	private boolean isAjaxRequest(ServletRequest request) {
//		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
//	}
//
//}

package com.winsmoney.jajaying.boss.domain.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.winsmoney.jajaying.boss.domain.MenuDomain;
import com.winsmoney.jajaying.boss.domain.ResourceDomain;
import com.winsmoney.jajaying.boss.domain.model.Menu;
import com.winsmoney.jajaying.boss.domain.model.Resource;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.web.util.HttpSpringUtils;

public class BossPermissionFilter implements Filter {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(BossPermissionFilter.class);
	private static final String USERNAME = "adminInfo";
	private final static String LOGIN_URL = "/login.html";
	private final static String NO_PERMISSION_URL = "/noPermission.html";

	@Autowired
	private ResourceDomain resourceDomain;
	@Autowired
	private MenuDomain menuDomain;


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		resourceDomain = (ResourceDomain) HttpSpringUtils.getBean("resourceDomain");
		menuDomain = (MenuDomain) HttpSpringUtils.getBean("menuDomain");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String uri = request.getRequestURI();
		if (("/").equals(uri) 
				|| uri.startsWith("/permission/department/departments")  
				|| uri.startsWith("/permission/role/roles")
				|| uri.startsWith("/ueditor/jsp/upload/image")  
				|| uri.startsWith("/project/p_project_info") 
				|| uri.startsWith("/project/p_invest_user_p")
				|| uri.startsWith("/project/p_loan_association_p")
				|| uri.startsWith("/project/p_repayment_info")
				|| uri.startsWith("/project/p_project_edit")
				|| uri.startsWith("/project/show")
				|| uri.startsWith("/favicon.ico")  
				|| uri.startsWith("/static")  
				|| uri.startsWith("/example")  
				|| uri.startsWith("/index.html")
				|| uri.startsWith("/login.html")
				|| uri.startsWith("/change_pwd.html")
				|| uri.startsWith("/userActivated.html")
				|| uri.startsWith("/userActivated.js")
				|| uri.startsWith("/permission/staff/userActivated")
				|| uri.startsWith("/admin/changePassword")
				|| uri.startsWith("/noPermission.html")
				|| uri.startsWith("/admin/login")
				|| uri.startsWith("/admin/logout")
				|| uri.startsWith("/admin/getCurrentUser")
				|| uri.startsWith("/permission/role/getMenusLogined")
				|| uri.startsWith("/boss/imageUpload")
				|| uri.startsWith("/ueditor/dispather")
				|| uri.startsWith("/borrow/getBorrow")
				|| uri.startsWith("/activity")
				|| uri.startsWith("/project/p_wait_list.html")
				|| uri.startsWith("/borrow/l_refuse_list_view.html")
				|| uri.startsWith("/platformManager/account/getPlatformAccounts")
				|| uri.startsWith("/platformManager/account/getPlatformAccount")
				|| uri.startsWith("/platformManager/withdraw")
				|| uri.startsWith("/platformManager/withdraw_info.html")
				|| uri.startsWith("/platformManager/account_Notify.html")
				|| uri.startsWith("/borrow/repayPlan_detail")
				|| uri.startsWith("/borrow/repayList")
				|| uri.startsWith("/borrow/l_add_loan")
				
//				|| uri.startsWith("/user/user_list/selectUserById")
				
				|| uri.startsWith("/signet/moulage")
				|| uri.startsWith("/signet/signet")
				|| uri.startsWith("/signet/enterprise")				) {
			chain.doFilter(request, response);
		} else {
			Staff staffSession = (Staff) request.getSession().getAttribute(USERNAME);

			if (null == staffSession) {
				sendRedirectIncludeAjax(request, response);
				return;
			} else {
				boolean canAccessFlag = false;
				String roleId = staffSession.getRoleId();
				if (StringUtils.isNotBlank(roleId)) {
					List<Resource> permissions = resourceDomain.getPermissonFlagByRoleId(roleId);
					for (Resource resource : permissions) {
						if (StringUtils.isNotBlank(resource.getMenuId())) {
							String permissionFlag = resource.getPermissionFlag();
							if (!"#".equals(permissionFlag) && uri.startsWith(permissionFlag)) {
								canAccessFlag = true;
								break;
							}
						}
					}
				} else {
					sendRedirectIncludeAjax(request, response);
					return;
				}
				if (canAccessFlag) {
					chain.doFilter(request, response);
				} else {
					response.sendRedirect(NO_PERMISSION_URL);
					return;
				}
			}
		}

	}

	private void sendRedirectIncludeAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
			response.setHeader("sessionStatus", "timeout");  
			return;
		} else {
			response.sendRedirect(LOGIN_URL);
			return;
		}
	}

	@Override
	public void destroy() {

	}

}

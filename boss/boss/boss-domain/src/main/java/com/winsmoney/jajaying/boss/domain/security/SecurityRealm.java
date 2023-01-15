//package com.winsmoney.jajaying.boss.domain.security;
//
//import java.util.List;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.winsmoney.jajaying.boss.domain.MenuDomain;
//import com.winsmoney.jajaying.boss.domain.ResourceDomain;
//import com.winsmoney.jajaying.boss.domain.StaffDomain;
//import com.winsmoney.jajaying.boss.domain.UserRoleDomain;
//import com.winsmoney.jajaying.boss.domain.model.Menu;
//import com.winsmoney.jajaying.boss.domain.model.Resource;
//import com.winsmoney.jajaying.boss.domain.model.Staff;
//import com.winsmoney.jajaying.boss.domain.model.UserRole;
//import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
//import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
//
//@Component("securityRealm")
//public class SecurityRealm extends AuthorizingRealm {
//	private final static WinsMoneyLogger LOG = WinsMoneyLoggerFactory.getLogger(AuthorizingRealm.class);
//
//	@Autowired
//	private StaffDomain staffDomain;
//	@Autowired
//	private UserRoleDomain userRoleDomain;
//	@Autowired
//	private ResourceDomain resourceDomain;
//	@Autowired
//	private MenuDomain menuDomain;
//
//	/**
//	 * 权限检查
//	 */
//	@Override
//	public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
//		String staffName = String.valueOf(super.getAvailablePrincipal(principals));
//		Staff staffReq = new Staff();
//		staffReq.setStaffName(staffName);
//		Staff staff = staffDomain.getStaff(staffReq);
//		UserRole UserRoleReq = new UserRole();
//		UserRoleReq.setId(staff.getRoleId());
//		UserRole userRole = userRoleDomain.getUserRole(UserRoleReq);
//		simpleAuthorInfo.addRole(userRole.getId());
//		List<Resource> permissions = resourceDomain.getPermissonByRoleId(userRole.getId());
//		for (Resource resource : permissions) {
//			if(StringUtils.isNotBlank(resource.getMenuId())){
//				Menu menu = menuDomain.getById(resource.getMenuId());
//				if(StringUtils.isNotBlank(menu.getPermissionFlag()) && !"#".equals(menu.getPermissionFlag()))
//				simpleAuthorInfo.addStringPermission(menu.getPermissionFlag());
//			}
//		}
//		LOG.info("roleId=" + simpleAuthorInfo.getRoles() + "permission=" + simpleAuthorInfo.getStringPermissions());
//		LOG.info(">>>>>>>>>>>>>>>>>simpleAuthorInfo=" + simpleAuthorInfo);
//		return simpleAuthorInfo;
//	}
//
//	/**
//	 * 登录验证
//	 */
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
//		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//		String loginName = token.getUsername();
//		String host = token.getHost();
//		LOG.info("userName=[{}],host=[{}]开始登录", loginName, host);
//		Staff staffReq = new Staff();
//		staffReq.setStaffName(loginName);
//		Staff staff = staffDomain.getStaff(staffReq);
//		if (null != staff && StringUtils.isEmpty(staff.getId())) {
//			throw new UnknownAccountException("该账号不存在");
//		}
//		ByteSource salt = ByteSource.Util.bytes(loginName);
//		// 记录登录日志
//		LOG.info("用户登录");
//		doGetAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
//		return new SimpleAuthenticationInfo(loginName, staff.getPassword(), salt, getName());
//	}
//
//}

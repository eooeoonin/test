package com.winsmoney.jajaying.boss.domain.security;

import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class PermissionConfig {
	private Set<String> staticSources;
	private Set<String> actionUrl;

	public Set<String> getStaticSources() {
		return staticSources;
	}

	public void setStaticSources(Set<String> staticSources) {
		this.staticSources = staticSources;
	}

	public Set<String> getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(Set<String> actionUrl) {
		this.actionUrl = actionUrl;
	}
	
	public boolean checkPermission(String requestUri){
		boolean canAccessFlag = false;
		for(String s : staticSources){
			if(s.endsWith("/**")){
				String strFlag = s.substring(0, s.length()-3);
				if(requestUri.startsWith(strFlag)){
					canAccessFlag = true;
				}
			}
		}
		
		
		return canAccessFlag;
		
	}

}

package com.winsmoney.jajaying.boss.domain.model.enums;

/**
 * 活动与平台对应关系
 * @author yangguolong
 *
 */
public enum ActivityPlatformType {
	/**
	 * pc网站
	 */
	PC("pc", "0"),
	;
	private String dbCode;
	private String htmlCode;
	
	ActivityPlatformType(String dbCode, String htmlCode) {
		this.dbCode = dbCode;
		this.htmlCode = htmlCode;
	}

	public String getDbCode() {
		return dbCode;
	}
	
	public String getHtmlCode() {
		return htmlCode;
	}

	public static ActivityPlatformType getType(String htmlCode) {
		for (ActivityPlatformType obj : ActivityPlatformType.class.getEnumConstants()) {
			if (obj.getHtmlCode().equals(htmlCode)) {
				return obj;
			}
		}
		return null;
	}
}

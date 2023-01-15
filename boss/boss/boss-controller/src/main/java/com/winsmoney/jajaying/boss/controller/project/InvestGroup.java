package com.winsmoney.jajaying.boss.controller.project;

import org.apache.commons.lang.StringUtils;

/**
 * Created by wei on 2016/8/19.
 */
public enum InvestGroup {
    ALL("ALL", "普通用户"),
    JINDI("JINDI", "内部员工"),
    VIP("VIP", "VIP用户"),
    OWNER("OWNER", "业主"),
    SL01("SL01", "小贷组"),
    TEST("TEST", "测试用户");

    private String code;
    private String name;

    InvestGroup(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static InvestGroup getType(String code) {
        for(InvestGroup obj : InvestGroup.class.getEnumConstants()){
            if( obj.getCode().equals( code )){
                return obj ;
            }
        }
        return null;
    }

    public static String investGroupName(String code) {
        String investGroupName = StringUtils.EMPTY;
        if(StringUtils.isBlank(code)){
            return investGroupName;
        }
        String[] investGroups = code.split(",");
        for(String investGroup:investGroups) {
            for (InvestGroup obj : InvestGroup.class.getEnumConstants()) {
                if (obj.getCode().equals(investGroup)) {
                    investGroupName += obj.getName()+",";
                }
            }
        }
        investGroupName = investGroupName.substring(0,investGroupName.length()-1);
        return investGroupName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

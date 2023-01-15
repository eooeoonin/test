package com.winsmoney.jajaying.boss.dao.enums;

/**
 * 操作类型
 */
public enum OperType {

    QUERY(0, "查询"), CREATE(1, "创建"), UPDATE(2, "更新"), DELETE(3, "删除");

    private int type;
    private String mark;

    OperType(int type, String mark) {
        this.type = type;
        this.mark = mark;
    }

    public int getType() {
        return type;
    }

    public String getMark() {
        return mark;
    }

    public static OperType convert(int type) {
        for (OperType operType : OperType.values()) {
            if (operType.type == type) {
                return operType;
            }
        }
        return null;
    }
}

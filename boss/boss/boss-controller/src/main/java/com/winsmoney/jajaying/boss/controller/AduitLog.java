package com.winsmoney.jajaying.boss.controller;

import com.winsmoney.jajaying.boss.dao.enums.OperType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AduitLog {

    /**
     * 操作类型
     * @return
     */
    OperType type() default OperType.CREATE;

    /**
     * code
     * @return
     */
    String code() default "";

    /**
     * content
     */
    String content() default "";
}

package com.winsmoney.jajaying.boss.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by weiqinlei on 2018/7/6.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  MarriageType {

    SINGLE("SINGLE","单身"),
    MARRIED("MARRIED","已婚"),
    DIVORCE("DIVORCE","离异"),
    DIEONE("DIEONE","丧偶"),
    ;
    private String code;
    private String message;
}

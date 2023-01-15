package com.winsmoney.jajaying.boss.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by weiqinlei on 2018/7/23.
 */
@Getter
@AllArgsConstructor
public enum CityListType {

	BeiJing("BeiJing","北京"),
	TianJin("TianJin","天津"),
    ;

    private String code;
    private String name;
}

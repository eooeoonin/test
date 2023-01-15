package com.winsmoney.jajaying.boss.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  IndustryType {

	FINANCE ("FINANCE","金融业"),
    CROWD("CROWD","建筑/房地产业"),
    INTERNET("INTERNET","互联网/通信业"),
    MANUFACTURE("MANUFACTURE","生产/制造业"),
    MEDIA("MEDIA","广告/媒体/策划业"),
    EDUCATE("EDUCATE","翻译/法律/教育业"),
    CHEMICAL("CHEMICAL","医疗/化工业"),
    SERVICE("SERVICE","服务业"),

    //20180902新增
    //批发和零售业；交通运输仓储和邮政业；租赁和商务服务业；科学研究和技术服务业；住宿和餐饮业；信息传输软件和信息服务业；教育；文化、体育和娱乐业
    WHOLESALE("WHOLESALE","批发和零售业"),
    TRANSPORTATION("TRANSPORTATION","交通运输仓储和邮政业"),
    LEASE("LEASE","租赁和商务服务业"),
    TECHNOLOGY("TECHNOLOGY","科学研究和技术服务业"),
    RESTAURANT("RESTAURANT","住宿和餐饮业"),
    INFORMATION("INFORMATION","信息传输软件和信息服务业"),
    EDUCATION("EDUCATION","教育"),
    OTHER("OTHER","其他"),
    ENTERTAINMENT("ENTERTAINMENT","文化、体育和娱乐业");
    private String code;
    private String message;
}

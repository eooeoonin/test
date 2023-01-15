package com.winsmoney.jajaying.boss.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  EntRelationType {

    LEGAL ("LEGAL","法人"),
    CONTROL("CONTROL","实际控制人"),
    OTHER("OTHER","其他");
    private String code;
    private String message;
}

package com.winsmoney.jajaying.boss.dao.po;

import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.platform.framework.core.model.BasePo;

import java.util.Date;

/**
 * 操作日志po
 *
 * @author howard he
 * @create 2018/7/9 16:26
 */
public class OperLogPo extends BasePo {

    /**
     * 操作人id
     */
    private String operId;
    /**
     * 操作人名称
     */
    private String operName;
    /**
     * 操作类型
     */
    private OperType operType;
    /**
     * 操作人的Ip地址
     */
    private String operAddress;
    /**
     * 操作的开发时间
     */
    private Date operTime;
    /**
     * 操作的内容
     */
    private String content;
    /**
     * 设置操作的code标识
     */
    private String code;
    /**
     * 请求信息
     */
    private String inputData;
    /**
     * 响应信息
     */
    private String outputData;

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public OperType getOperType() {
        return operType;
    }

    public void setOperType(OperType operType) {
        this.operType = operType;
    }

    public String getOperAddress() {
        return operAddress;
    }

    public void setOperAddress(String operAddress) {
        this.operAddress = operAddress;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getOutputData() {
        return outputData;
    }

    public void setOutputData(String outputData) {
        this.outputData = outputData;
    }
}

package com.winsmoney.jajaying.boss.domain.mq;


public enum MsgMQTopic
{
    CHECK("C14","CHECK","对账"),
    PAY_SHARE_PACKET_BOSS("C05","PAY_SHARE_PACKET_BOSS","BOSS发放分享红包"),
    AWARD_REQUEST("C16","AWARD_REQUEST","奖励请求"),
    ACTIVITY_REQUEST("C05","ACTIVITY_REQUEST","活动请求"),
    SENDEMAIL("P01","SEND_EMAIL","发送邮件"),
    ;
	

    private String topic;
    private String tag;
    private String message;

    private MsgMQTopic(String topic, String tag,String message)
    {
        this.topic = topic;
        this.tag = tag;
        this.message=message;
    }

    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}

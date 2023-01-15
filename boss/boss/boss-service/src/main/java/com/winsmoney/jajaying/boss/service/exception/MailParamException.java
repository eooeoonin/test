package com.winsmoney.jajaying.boss.service.exception;

/**
 * @author howard he
 * @create 2018/7/11 10:14
 */
public class MailParamException extends IllegalArgumentException {

    public MailParamException() {
        super();
    }

    public MailParamException(String s) {
        super(s);
    }

    public MailParamException(String message, Throwable cause) {
        super(message, cause);
    }
}

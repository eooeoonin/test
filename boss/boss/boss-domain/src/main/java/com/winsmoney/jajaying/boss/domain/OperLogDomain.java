package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.jajaying.boss.domain.manager.OperLogManager;
import com.winsmoney.jajaying.boss.domain.model.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author howard he
 * @create 2018/7/10 15:46
 */
@Component
public class OperLogDomain {

    @Autowired
    private OperLogManager operLogManager;

    /**
     * 创建日志
     * @param operLog
     */
    public void create(OperLog operLog) {
        operLogManager.insert(operLog);
    }
}

/**
* Project:boss
* File:Template.java
* Date:2017-09-04
* Copyright (c) 2017 jajaying.com All Rights Reserved.
*/
package com.winsmoney.jajaying.boss.domain.manager;

import java.util.List;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.springframework.stereotype.Component;

import com.winsmoney.jajaying.boss.dao.mapper.TemplateMapper;
import com.winsmoney.jajaying.boss.dao.po.TemplatePo;
import com.winsmoney.jajaying.boss.domain.model.Template;

/**
* Description: 模板表 管理器
* date: 2017-09-04 04:11:05
* @author: CodeCreator
*/
@Component
public class TemplateManager extends BaseManager<Template,TemplateMapper,TemplatePo>
{
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(TemplateManager.class);


}



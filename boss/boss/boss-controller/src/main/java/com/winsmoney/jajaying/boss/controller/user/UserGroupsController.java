package com.winsmoney.jajaying.boss.controller.user;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.ISystemConfigService;
import com.winsmoney.jajaying.basedata.service.request.SystemConfigReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.SystemConfigResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenkai1 on 2017/8/17.
 */

@RestController
public class UserGroupsController {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(UserGroupsController.class);

    @Autowired
    private ISystemConfigService systemConfigService;

    @RequestMapping(value = {"/user/group/list", "/user/user_list/getUserGroups"})
    public Result list(HttpServletRequest request) {
        SystemConfigReqVo systemConfigReqVo = new SystemConfigReqVo();
        systemConfigReqVo.setClassCode("userGroups");
        systemConfigReqVo.setUnit("0");
        // 前台请求时，只查出启用了的用户组,后台查询时，查询出所有的用户组
        if(request.getRequestURI().endsWith("user/user_list/getUserGroups")){
            systemConfigReqVo.setAvailable(1);
        }
        BasedataCommonResult<PageInfo<SystemConfigResVo>> systemConfigResult = null;
        try {
            systemConfigResult = systemConfigService.listSystemConfig(systemConfigReqVo, 1, 50);
            if (systemConfigResult.isSuccess()) {
                return Result.success(systemConfigResult.getBusinessObject());
            } else return Result.error("接口异常");
        } catch (Exception e) {
            logger.error("调用基础数据查询接口异常", e);
            return Result.error("调用基础数据查询接口异常");
        }
    }


    @RequestMapping(value = "/user/group/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable("id") String id) {
        BasedataCommonResult<Integer> basedataCommonResult = null;
        try {
            BasedataCommonResult<SystemConfigResVo> systemConfigResult = systemConfigService.getById(id);
            if (systemConfigResult.isSuccess()) {
                return Result.success(systemConfigResult.getBusinessObject());
            } else return Result.error("接口异常");
        } catch (Exception e) {
            logger.error("调用基础数据获取单条记录接口异常", e);
            return Result.error("调用基础数据获取单条记录接口异常");
        }
    }

    /**
     * 新增或更新组
     */
    @RequestMapping(value = "/user/group/add", method = RequestMethod.POST)
	@AduitLog(type = OperType.CREATE, content = "新增或更新组")
    public Result add(SystemConfigReqVo systemConfigReqVo) {
        BasedataCommonResult<Integer> basedataCommonResult = null;
        try {
            if (StringUtils.isNotEmpty(systemConfigReqVo.getId())) {
                basedataCommonResult = systemConfigService.updateSystemConfig(systemConfigReqVo);
            } else {
                systemConfigReqVo.setClassCode("userGroups");
                systemConfigReqVo.setClassDesc("用户组");
                basedataCommonResult = systemConfigService.insertSystemConfig(systemConfigReqVo);
            }
            if (basedataCommonResult.isSuccess()) {
                return Result.success(basedataCommonResult.getBusinessObject());
            } else return Result.error("接口异常");
        } catch (Exception e) {
            logger.error("调用基础数据新增或更新接口异常", e);
            return Result.error("调用基础数据新增或更新接口异常");
        }
    }
}

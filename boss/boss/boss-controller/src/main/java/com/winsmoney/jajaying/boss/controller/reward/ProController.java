package com.winsmoney.jajaying.boss.controller.reward;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.award.service.IProjectService;
import com.winsmoney.jajaying.award.service.request.ProjectReqVo;
import com.winsmoney.jajaying.award.service.response.AwardCommonResult;
import com.winsmoney.jajaying.award.service.response.ProjectResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 项目管理
 */
@Controller
@RequestMapping("/reward/project_list")
public class ProController {

    @Autowired
    private IProjectService projectService;  //项目

    @RequestMapping("/list")
    @ResponseBody
    public PageInfo<ProjectResVo> list(ProjectReqVo projectReqVo, int pageNo, int pageSize) {
        AwardCommonResult<PageInfo<ProjectResVo>> list = projectService.list(projectReqVo, pageNo, pageSize);
        return list.getBusinessObject();
    }


    @RequestMapping("/insert")
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加奖励项目")
    public Integer insert(ProjectReqVo projectReqVo) {
        AwardCommonResult<Integer> insert = projectService.insert(projectReqVo);
        return insert.getBusinessObject();
    }

    @RequestMapping("/getById")
    @ResponseBody
    public ProjectResVo getById(String id) {
        AwardCommonResult<ProjectResVo> getById = projectService.getById(id);
        return getById.getBusinessObject();
    }

    @RequestMapping("/update")
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新奖励项目")
    public Integer update(ProjectReqVo projectReqVo) {
        AwardCommonResult<Integer> update = projectService.update(projectReqVo);
        return update.getBusinessObject();
    }


}

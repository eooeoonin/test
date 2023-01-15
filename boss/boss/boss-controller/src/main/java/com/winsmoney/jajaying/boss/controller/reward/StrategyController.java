package com.winsmoney.jajaying.boss.controller.reward;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.award.service.IAwardStrategyService;
import com.winsmoney.jajaying.award.service.request.AwardStrategyReqVo;
import com.winsmoney.jajaying.award.service.response.AwardCommonResult;
import com.winsmoney.jajaying.award.service.response.AwardStrategyResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 策略管理
 */
@Controller
@RequestMapping("/reward/project_list")
public class StrategyController {

    @Autowired
    private IAwardStrategyService awardStrategyService;   // 策略列表


    @RequestMapping("/strateg/list")
    @ResponseBody
    public PageInfo<AwardStrategyResVo> list(AwardStrategyReqVo awardStrategyReqVo, int pageNo, int pageSize) {
        AwardCommonResult<PageInfo<AwardStrategyResVo>> list = awardStrategyService.list(awardStrategyReqVo, pageNo, pageSize);
        return list.getBusinessObject();
    }


    @RequestMapping("/strateg/insert")
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增奖励策略")
    public Integer insert(AwardStrategyReqVo awardStrategyReqVo) {
        AwardCommonResult<Integer> insert = awardStrategyService.insert(awardStrategyReqVo);
        return insert.getBusinessObject();
    }

    @RequestMapping("/strateg/getById")
    @ResponseBody
    public AwardStrategyResVo getById(String id) {
        AwardCommonResult<AwardStrategyResVo> getById = awardStrategyService.getById(id);
        return getById.getBusinessObject();
    }


    @RequestMapping("/strateg/update")
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新奖励策略")
    public Integer update(AwardStrategyReqVo awardStrategyReqVo) {
        AwardCommonResult<Integer> update = awardStrategyService.update(awardStrategyReqVo);
        return update.getBusinessObject();
    }


}

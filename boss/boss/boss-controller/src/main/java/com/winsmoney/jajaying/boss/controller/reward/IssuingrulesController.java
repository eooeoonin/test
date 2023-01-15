package com.winsmoney.jajaying.boss.controller.reward;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.award.service.IAwardPaySettingService;
import com.winsmoney.jajaying.award.service.IAwardPoolService;
import com.winsmoney.jajaying.award.service.request.AwardPaySettingReqVo;
import com.winsmoney.jajaying.award.service.response.AwardCommonResult;
import com.winsmoney.jajaying.award.service.response.AwardPaySettingResVo;
import com.winsmoney.jajaying.award.service.response.AwardPoolResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 策略发送规则
 */
@Controller
@RequestMapping("/reward/project_list")
public class IssuingrulesController {


    @Autowired
    private IAwardPaySettingService awardPaySettingService;   //发放

    @Autowired
    private IAwardPoolService awardPoolService;  //奖池列表


    @RequestMapping("/lssuingrules/list")
    @ResponseBody
    public PageInfo<AwardPaySettingResVo> list(AwardPaySettingReqVo awardPaySettingReqVo, int pageNo, int pageSize) {
        AwardCommonResult<PageInfo<AwardPaySettingResVo>> list = awardPaySettingService.list(awardPaySettingReqVo, pageNo, pageSize);
        for (int i = 0; i < list.getBusinessObject().getList().size(); i++) {
            String id = list.getBusinessObject().getList().get(i).getAwardPoolId();
            AwardCommonResult<AwardPoolResVo> AwardPoolResVoList = awardPoolService.getById(id);
            list.getBusinessObject().getList().get(i).setAwardPoolId(AwardPoolResVoList.getBusinessObject().getDescName());
        }
        return list.getBusinessObject();
    }


    @RequestMapping("/lssuingrules/insert")
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增奖励规则")
    public Integer insert(AwardPaySettingReqVo awardPaySettingReqVo) {
        AwardCommonResult<Integer> insert = awardPaySettingService.insert(awardPaySettingReqVo);
        return insert.getBusinessObject();
    }

    @RequestMapping("/lssuingrules/getById")
    @ResponseBody
    public AwardPaySettingResVo getById(String id) {
        AwardCommonResult<AwardPaySettingResVo> getById = awardPaySettingService.getById(id);
        return getById.getBusinessObject();
    }


    @RequestMapping("/lssuingrules/update")
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新奖励规则")
    public Integer update(AwardPaySettingReqVo awardPaySettingReqVo) {
        AwardCommonResult<Integer> update = awardPaySettingService.update(awardPaySettingReqVo);
        return update.getBusinessObject();
    }


}

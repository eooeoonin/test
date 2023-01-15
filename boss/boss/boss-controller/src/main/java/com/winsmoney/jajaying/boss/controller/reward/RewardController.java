package com.winsmoney.jajaying.boss.controller.reward;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.domain.AwardListLogDomain;
import com.winsmoney.jajaying.boss.domain.model.AwardListLog;
/**
 * 奖励记录
 */
@Controller
@RequestMapping("/reward/reward_list")
public class RewardController {

    @Autowired
    private AwardListLogDomain awardListLogDomain;//奖励

    @RequestMapping(value="/list")
    @ResponseBody
    public PageInfo<AwardListLog> list(AwardListLog request, int pageNo, int pageSize) {
        PageInfo<AwardListLog> list = awardListLogDomain.list(request, pageNo, pageSize);
        return list;
    }

}

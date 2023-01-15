package com.winsmoney.jajaying.boss.controller.reward;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Array;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.award.service.IAwardActRequestService;
import com.winsmoney.jajaying.award.service.enums.Beneficiary;
import com.winsmoney.jajaying.award.service.request.AwardActRequestReqVo;
import com.winsmoney.jajaying.award.service.response.AwardActRequestResVo;
import com.winsmoney.jajaying.award.service.response.AwardCommonResult;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.controller.model.BehaviorInfo;
import com.winsmoney.jajaying.boss.controller.model.ReturnBehavior;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.util.BeanMapper;

/**
 * 策略行为
 */
@Controller
@RequestMapping("/reward/project_list")
public class BehaviorController {

    @Autowired
    private IAwardActRequestService awardActRequestService;  //行为列表


    @RequestMapping("/behavior/list")
    @ResponseBody
    public PageInfo<AwardActRequestResVo> list(AwardActRequestReqVo awardActRequestReqVo, int pageNo, int pageSize) {
        awardActRequestReqVo.setIsParent(true);
        AwardCommonResult<PageInfo<AwardActRequestResVo>> list = awardActRequestService.list(awardActRequestReqVo, pageNo, pageSize);
        List<AwardActRequestResVo> l = list.getBusinessObject().getList();
        return list.getBusinessObject();
    }


    @RequestMapping("/behavior/insert")
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增策略")
    public Integer insert(ReturnBehavior awardActRequestReqVo) {
        if (StringUtils.isEmpty(awardActRequestReqVo.getActivityCode())) {
            awardActRequestReqVo.setActivityCode(null);
        }
        if (StringUtils.isEmpty(awardActRequestReqVo.getChannelCode())) {
            awardActRequestReqVo.setChannelCode(null);
        }
        String channelCode = awardActRequestReqVo.getChannelCode();
        String activityCode = awardActRequestReqVo.getActivityCode();
        if (StringUtils.isNotBlank(channelCode)) {
            List<String> channelCodes = Arrays.asList(channelCode.split(","));
            awardActRequestReqVo.setChannelCode(JSONObject.toJSONString(channelCodes));
        } else {
            awardActRequestReqVo.setChannelCode(null);
        }
        if (StringUtils.isEmpty(activityCode)) {
            awardActRequestReqVo.setActivityCode(null);
        }
        if(!awardActRequestReqVo.getSingle()){
        	awardActRequestReqVo.setBeneficiary(null);
        }
        AwardActRequestReqVo aarr = BeanMapper.map(awardActRequestReqVo, AwardActRequestReqVo.class);
        aarr.setIsParent(true);
        AwardCommonResult<AwardActRequestResVo> insert = awardActRequestService.createAwardActRequest(aarr);
        String parentId = insert.getBusinessObject().getId();
        if (!aarr.getSingle()) {
            List<BehaviorInfo> l = awardActRequestReqVo.getBehaviorInfo();
            for (BehaviorInfo b : l) {
                if (null != b && null != b.getZuida() && !"".equals(b.getZuida())) {
                    aarr.setId(null);
                    aarr.setMax(Long.parseLong(b.getZuida()));
                    aarr.setMin(Long.parseLong(b.getZuixiao()));
                    aarr.setBeneficiary(Beneficiary.getType(b.getShouyiren()));
                    aarr.setParentActId(parentId);
                    aarr.setIsParent(false);
                    awardActRequestService.createAwardActRequest(aarr);
                }
            }
        }

        return Result.success(insert).getResCode();
    }

    @RequestMapping("/behavior/getById")
    @ResponseBody
    public List getById(String id) {
        AwardCommonResult<AwardActRequestResVo> getById = awardActRequestService.getById(id);
        AwardActRequestReqVo aarr = new AwardActRequestReqVo();
        aarr.setParentActId(getById.getBusinessObject().getId());
        AwardCommonResult<PageInfo<AwardActRequestResVo>> a = awardActRequestService.list(aarr, 1, 1000);
        List l = new ArrayList();
        l.add(getById.getBusinessObject());
        l.add(a.getBusinessObject().getList());
        return l;
    }

    @RequestMapping("/behavior/getListById")
    @ResponseBody
    public PageInfo<AwardActRequestResVo> getListById(String id, int pageNo, int pageSize) {
        AwardActRequestReqVo aarr = new AwardActRequestReqVo();
        aarr.setParentActId(id);
        AwardCommonResult<PageInfo<AwardActRequestResVo>> list = awardActRequestService.list(aarr, pageNo, pageSize);
        return list.getBusinessObject();
    }

    @RequestMapping("/behavior/del")
    @ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除策略")
    public AwardCommonResult<Integer> del(String id) {
        AwardCommonResult<Integer> a = awardActRequestService.delete(id);
        awardActRequestService.deleteAwardActRequestByParentId(id);
        return a;
    }

    @RequestMapping("/behavior/update")
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新策略")
    public Integer update(ReturnBehavior awardActRequestReqVo) {
        if (StringUtils.isEmpty(awardActRequestReqVo.getActivityCode())) {
            awardActRequestReqVo.setActivityCode(StringUtils.EMPTY);
        }
        String channelCode = awardActRequestReqVo.getChannelCode();
        if (StringUtils.isNotBlank(channelCode) && !channelCode.startsWith("[")) {
            List<String> channelCodes = Arrays.asList(channelCode.split(","));
            awardActRequestReqVo.setChannelCode(JSONObject.toJSONString(channelCodes));
            awardActRequestReqVo.setActivityCode(StringUtils.EMPTY);
        }
        if (StringUtils.isBlank(channelCode)) {
            awardActRequestReqVo.setChannelCode(StringUtils.EMPTY);
        }
        if(!awardActRequestReqVo.getSingle()){
        	awardActRequestReqVo.setBeneficiary(null);
        }
        AwardActRequestReqVo aarr = BeanMapper.map(awardActRequestReqVo, AwardActRequestReqVo.class);
        AwardCommonResult<Integer> update = awardActRequestService.update(aarr);
        awardActRequestService.deleteAwardActRequestByParentId(awardActRequestReqVo.getId());
        List<BehaviorInfo> l = awardActRequestReqVo.getBehaviorInfo();
        for (BehaviorInfo b : l) {
            if (null != b.getZuida() && !"".equals(b.getZuida())) {
                aarr.setId(null);
                aarr.setMax(Long.parseLong(b.getZuida()));
                aarr.setMin(Long.parseLong(b.getZuixiao()));
                aarr.setBeneficiary(Beneficiary.getType(b.getShouyiren()));
                aarr.setParentActId(awardActRequestReqVo.getId());
                aarr.setIsParent(false);
                awardActRequestService.createAwardActRequest(aarr);
            }
        }
        return update.getBusinessObject();
    }

}

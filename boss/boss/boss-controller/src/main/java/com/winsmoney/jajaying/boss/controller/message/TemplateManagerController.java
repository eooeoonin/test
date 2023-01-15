package com.winsmoney.jajaying.boss.controller.message;

import java.util.ArrayList;
import java.util.List;

import com.winsmoney.jajaying.message.api.templatepara.ITemplateParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IEventConfigService;
import com.winsmoney.jajaying.basedata.service.request.EventConfigReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.EventConfigResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.message.api.response.Page;
import com.winsmoney.jajaying.message.api.template.ITemplateService;
import com.winsmoney.jajaying.message.api.template.request.TemplateReq;
import com.winsmoney.jajaying.message.api.template.response.TemplateRes;
import com.winsmoney.jajaying.message.api.templatepara.request.TemplateParaReq;
import com.winsmoney.jajaying.message.api.templatepara.response.TemplateParaRes;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

@Controller
@RequestMapping("/basedata_mgt/templateManager")
public class TemplateManagerController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(TemplateManagerController.class);
	@Autowired
	private IEventConfigService eventConfigService;
	@Autowired
	private ITemplateService templateService;
	@Autowired
	private ITemplateParaService templateParaService;
	
    @RequestMapping(value = "/getAllEven", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllLink() {
        try {
        	EventConfigReqVo ecfr = new EventConfigReqVo();
         	BasedataCommonResult<PageInfo<EventConfigResVo>> result = eventConfigService.list(ecfr, 1, 1000);
            return Result.success(result.getBusinessObject().getList());
        } catch (Exception e) {
        	logger.error("eventConfigService list 获取所有事件接口失败",e);
            e.printStackTrace();
        	return Result.error("接口异常");
        }
    }
    @RequestMapping(value = "/getAllPara", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllPara() {
        try {
        	TemplateParaReq t = new TemplateParaReq();
        	Page<TemplateParaRes> result = templateParaService.queryForList(t, 1, 1000);
        	return Result.success(result.getList());
        } catch (Exception e) {
        	logger.error("eventConfigService list 获取所有事件接口失败",e);
        	return Result.error("接口异常");
        }
    }
    
    
    @RequestMapping(value = "/addTemplate", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增活动模板")
    public Result addTemplate(@ModelAttribute TemplateReq templateReq) {
        try {
        	logger.info("接口{}入参:" + JSONObject.toJSONString(templateReq), ITemplateService.class);
        	int result = templateService.insert(templateReq);
        	logger.info("接口{}出参:" + JSONObject.toJSONString(result), ITemplateService.class);
        	return Result.success(result);
        } catch (Exception e) {
        	logger.error("addTemplate()  ----------",e);
        	return 	Result.error("error");
        }
    }
    @RequestMapping(value = "/delTemplate", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除活动模板")
    public Result delTemplate(int id) {
        try {
        	int result = templateService.delete(id);
        	return Result.success(result);
        } catch (Exception e) {
        	logger.error("addTemplate()  ----------",e);
        	return 	Result.error("error");
        }
    }
    @RequestMapping(value = "/editTemplate", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新活动模板")
    public Result editTemplate(@ModelAttribute TemplateReq templateReq) {
        try {
        	logger.info("接口{}入参:" + JSONObject.toJSONString(templateReq), ITemplateService.class);
        	int result = templateService.update(templateReq);
        	logger.info("接口{}出参:" + JSONObject.toJSONString(result), ITemplateService.class);
        	return Result.success(result);
        } catch (Exception e) {
        	logger.error("addTemplate()  ----------",e);
        	return 	Result.error("error");
        }
    }
    
    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    @ResponseBody
    public Result getById(int id) {
        try {
        	logger.info("接口{}入参:" + JSONObject.toJSONString(id), ITemplateService.class);
        	TemplateRes result = templateService.getById(id);
        	logger.info("接口{}出参:" + JSONObject.toJSONString(result), ITemplateService.class);
        	return Result.success(result);
        } catch (Exception e) {
        	logger.error("addTemplate()  ----------",e);
        	return 	Result.error("error");
        }
    }
    
    @RequestMapping(value = "/getTemplateList", method = RequestMethod.POST)
    @ResponseBody
    public Result getTemplateList(@ModelAttribute TemplateReq templateReq,Integer pageNo,Integer pageSize) {
        try {
        	logger.info("接口{}入参:" + JSONObject.toJSONString(templateReq), ITemplateService.class);
        	Page<TemplateRes> result = templateService.queryForList(templateReq, pageNo, pageSize);
        	logger.info("接口{}出参:" + JSONObject.toJSONString(result), ITemplateService.class);
        	List<TemplateRes> l  = result.getList();
        	for(TemplateRes o : l){
        		String evenCode = o.getEventCode();
        		EventConfigReqVo e = new EventConfigReqVo();
        		e.setCode(evenCode);
        		BasedataCommonResult<EventConfigResVo> ecrv = eventConfigService.get(e);
        		o.setEventCode(ecrv.getBusinessObject().getName());
        	}
        	System.out.println("");
        	return Result.success(result);
        } catch (Exception e) {
        	logger.error("addTemplate()  ----------",e);
        	return 	Result.error("error");
        }
    }
}

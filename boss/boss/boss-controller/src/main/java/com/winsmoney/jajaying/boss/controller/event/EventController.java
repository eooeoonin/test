package com.winsmoney.jajaying.boss.controller.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

/**
 * 事件管理
 * @author xiaxingxing1
 *
 */
@Controller
@RequestMapping("/basedata_mgt/event")
public class EventController {

	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(EventController.class);
	
	@Autowired
	private IEventConfigService eventConfigService;
	
	/**
	 * 事件分页列表
	 * @param eventConfigReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/eventlist", method = RequestMethod.POST)
    @ResponseBody
    public Result eventlist(EventConfigReqVo eventConfigReqVo,int pageNo, int pageSize) {
		if(null == eventConfigReqVo || eventConfigReqVo.equals("")){
			eventConfigReqVo = null;
		}
		BasedataCommonResult<PageInfo<EventConfigResVo>> result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(eventConfigReqVo));
			result = eventConfigService.list(eventConfigReqVo, pageNo, pageSize);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{EventController  eventlist  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 增加事件
	 * @param eventConfigReqVo
	 * @return
	 */
	@RequestMapping(value = "/eventInsert", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "增加事件")
    public Result eventInsert(EventConfigReqVo eventConfigReqVo) {
		if(null == eventConfigReqVo || eventConfigReqVo.equals("")){
			eventConfigReqVo = null;
		}
		BasedataCommonResult<Integer> result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(eventConfigReqVo));
			result = eventConfigService.insert(eventConfigReqVo);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{EventController  eventInsert  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 根据ID获取一条数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/eventById", method = RequestMethod.POST)
    @ResponseBody
    public Result eventById(String id) {
		if(null == id || id.equals("")){
			id = null;
		}
		BasedataCommonResult<EventConfigResVo> result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			result = eventConfigService.getById(id);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{EventController  eventById  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 编辑更新事件
	 * @param eventConfigReqVo
	 * @return
	 */
	@RequestMapping(value = "/eventUpdate", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新事件")
    public Result eventUpdate(EventConfigReqVo eventConfigReqVo) {
		if(null == eventConfigReqVo || eventConfigReqVo.equals("")){
			eventConfigReqVo = null;
		}
		BasedataCommonResult<Integer> result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(eventConfigReqVo));
			result = eventConfigService.update(eventConfigReqVo);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{EventController  eventUpdate  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	/**
	 * 根据ID删除一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/eventDelete", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除事件")
    public Result eventDelete(String id) {
		if(null == id || id.equals("")){
			id = null;
		}
		BasedataCommonResult<Integer> result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			result = eventConfigService.delete(id);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{EventController  eventDelete  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
}

package com.winsmoney.jajaying.boss.controller.event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.message.api.eventpropertymapping.IEventPropertyMappingService;
import com.winsmoney.jajaying.message.api.eventpropertymapping.request.EventPropertyMappingReq;
import com.winsmoney.jajaying.message.api.eventpropertymapping.response.EventPropertyMappingRes;
import com.winsmoney.jajaying.message.api.response.Page;
import com.winsmoney.jajaying.message.api.templatepara.ITemplateParaService;
import com.winsmoney.jajaying.message.api.templatepara.request.TemplateParaReq;
import com.winsmoney.jajaying.message.api.templatepara.response.TemplateParaRes;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

/**
 * 事件属性Mapping管理
 * @author xiaxingxing1
 *
 */
@Controller
@RequestMapping("/basedata_mgt/eventMapping")
public class EventMappingController {

	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(EventController.class);
	
	@Autowired
	private IEventPropertyMappingService eventPropertyMappingService;
	@Autowired
	private ITemplateParaService templateParaService;
	/**
	 *  获取分页数据
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getAllMapping", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllMapping(EventPropertyMappingReq entity) {
		if(null == entity || entity.equals("")){
			entity = null;
		}
		Page<EventPropertyMappingRes> result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(entity));
			result = eventPropertyMappingService.queryForList(entity, null, null);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{EventMappingController  getAllMapping  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 查询所有模板变量
	 * @return
	 */
	@RequestMapping(value = "/getAllPara", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllPara() {
		Page<TemplateParaRes> result  = null;
		try{
			result = templateParaService.queryForList(new TemplateParaReq(), null, null);
			return Result.success(result.getList());
		}catch (Exception e) {
			logger.error("{EventMappingController  getAllPara  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }

	
	/**
	 * 插入一条记录
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/addMapping", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增事件映射")
	 public Result addMapping(EventPropertyMappingReq entity) {
		if(null == entity || entity.equals("")){
			entity = null;
		}
		Integer result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(entity));
			result = eventPropertyMappingService.insert(entity);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{EventMappingController  addMapping  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 按照id查询一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getById", method = RequestMethod.POST)
	@ResponseBody
	 public Result getById(int id) {
		
		EventPropertyMappingRes result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			result = eventPropertyMappingService.getById(id);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{EventMappingController  getById  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 更新记录
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/editMapping", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新事件映射")
	 public Result editMapping(EventPropertyMappingReq entity) {
		if(null == entity || entity.equals("")){
			entity = null;
		}
		Integer result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(entity));
			result = eventPropertyMappingService.update(entity);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{EventMappingController  editMapping  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 根据id删除一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delMapping", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除事件映射")
	 public Result delMapping(int id) {
		
		Integer result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			result = eventPropertyMappingService.delete(id);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{EventMappingController  editMapping  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
}
	

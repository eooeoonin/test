package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IEventMapService;
import com.winsmoney.jajaying.basedata.service.IEventValueMapService;
import com.winsmoney.jajaying.basedata.service.IEventValueSupplyService;
import com.winsmoney.jajaying.basedata.service.request.EventMapReqVo;
import com.winsmoney.jajaying.basedata.service.request.EventValueMapReqVo;
import com.winsmoney.jajaying.basedata.service.request.EventValueSupplyReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.EventMapResVo;
import com.winsmoney.jajaying.basedata.service.response.EventValueMapResVo;
import com.winsmoney.jajaying.basedata.service.response.EventValueSupplyResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
/**
 * 
 * 事件管理，事件列表
 * @author Moon
 *
 */

@Controller
@RequestMapping("/basedata_mgt/event_list")
public class IeventController {
	
	@Autowired
	private IEventMapService eventMapService;
	
	@Autowired
	private IEventValueMapService eventValueMapService;
	@Autowired
	private IEventValueSupplyService  eventValueSupplyService ;
	
	/**
	 * 单笔查询
	 * @param eventValueMapReqVo
	 * @return
	 */
	@RequestMapping("/selectValueMap")
	@ResponseBody
	public String selectValueMap(EventValueMapReqVo eventValueMapReqVo){
		 BasedataCommonResult<EventValueMapResVo> get = eventValueMapService.get(eventValueMapReqVo);
		return JSON.toJSONString(get.getBusinessObject());
	}
	/**
	 * 查询子表1
	 * @param eventValueMapReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	
	@RequestMapping("/selectValueMapList")
	@ResponseBody
	public PageInfo<EventValueMapResVo> selectValueMapList(EventValueMapReqVo eventValueMapReqVo,int pageNo, int pageSize){
		 BasedataCommonResult<PageInfo<EventValueMapResVo>> list = eventValueMapService.list(eventValueMapReqVo, pageNo, pageSize);
		 PageInfo<EventValueMapResVo> eventvaluemapList = new PageInfo<EventValueMapResVo>();
		 eventvaluemapList = list.getBusinessObject();
		 return eventvaluemapList;
	}
	
	/**
	 * 查询子表2
	 * @param eventValueMapReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/selectValueMapSupplyList")
	@ResponseBody
	public PageInfo<EventValueSupplyResVo > list(EventValueSupplyReqVo eventValueSupplyReqVo,int pageNo, int pageSize){
		 BasedataCommonResult<PageInfo<EventValueSupplyResVo>> list = eventValueSupplyService.list(eventValueSupplyReqVo, pageNo, pageSize);
		 PageInfo<EventValueSupplyResVo> eventValueMapSupplyList = new PageInfo<EventValueSupplyResVo>();
		 eventValueMapSupplyList= list.getBusinessObject();
		 
		 return eventValueMapSupplyList;
		 
	}
	
	
	
	/**
	 * 删除次表1
	 * @param eid
	 * @return
	 */
	@RequestMapping("/deleteEventValueMap")
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除事件次表1")
	public String deleteEventValueMap(String id){
		//System.out.println(id); 
		 BasedataCommonResult<Integer> delete = eventValueMapService.delete(id);
	    return JSON.toJSONString(delete.getBusinessObject());
	}
	
	/**
	 * 删除次表2
	 * @param eid
	 * @return
	 */
	@RequestMapping("/deleteEventValueSupplyMap")
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除事件次表1")
	public String delete(String id){
		//System.out.println(id); 
		 BasedataCommonResult<Integer> delete = eventValueSupplyService.delete(id);
	    return JSON.toJSONString(delete.getBusinessObject());
	}
	

	
	/**
	 * 更新字表1
	 * @param eventValueMapReqVo
	 * @return
	 */
	@RequestMapping("/updateEventValueMap")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新事件字表1")
	public Integer updateEventValueMap(EventValueMapReqVo eventVlaueMapReqVo){
		BasedataCommonResult<Integer> update = eventValueMapService.update(eventVlaueMapReqVo);
		return update.getBusinessObject();
	}
	/**
	 * 更新字表2
	 * @param eventValueMapReqVo
	 * @return
	 */
	@RequestMapping("/updateEventValueSupplyMap")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新事件字表2")
	public Integer update(EventValueSupplyReqVo eventValueSupplyReqVo){
		BasedataCommonResult<Integer> update = eventValueSupplyService.update(eventValueSupplyReqVo);
		return update.getBusinessObject();
	}
	/**
	 * 添加子表1
	 * @param eventValueMapReqVo
	 * @return
	 */
	
	@RequestMapping("/insertEventValueMap")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加事件子表1")
	public Integer insertEventValueMap(EventValueMapReqVo eventValueMapReqVo){
 		BasedataCommonResult<Integer> insert = eventValueMapService.insert(eventValueMapReqVo);
		return insert.getBusinessObject();
	}
	/**
	 * 添加子表2
	 * @param eventValueMapReqVo
	 * @return
	 */
	
	@RequestMapping("/insertEventValueSupplyMapp")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加事件子表2")
	public Integer insert(EventValueSupplyReqVo eventValueSupplyReqVo){
 		BasedataCommonResult<Integer> insert = eventValueSupplyService.insert(eventValueSupplyReqVo);
		return insert.getBusinessObject();
	}
	
	
	/**
	 * 列表展示
	 * @param eventMapReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/selectEventMapList")
	@ResponseBody
	public PageInfo<EventMapResVo> selectEventMapList(EventMapReqVo eventMapReqVo,int pageNo, int pageSize)throws Exception{
		 BasedataCommonResult<PageInfo<EventMapResVo>> list = eventMapService.list(eventMapReqVo, pageNo, pageSize);
		 PageInfo<EventMapResVo> eventinfoList = new PageInfo<EventMapResVo>();
		 eventinfoList = list.getBusinessObject();
		return eventinfoList;
	}
	
	
	/**
	 * 添加
	 * @param eventMapReqVo
	 * @return
	 */
	@RequestMapping("/insertEventMap")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加事件")
	public Integer insertEventMap(EventMapReqVo eventMapReqVo){
		BasedataCommonResult<Integer> insert = eventMapService.insert(eventMapReqVo);
		return insert.getBusinessObject();
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteEventMap")
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除事件")
	public String deleteEventMap(String eid){
		//System.out.println(eid); 
		BasedataCommonResult<Integer> delete = eventMapService.delete(eid);
	    return JSON.toJSONString(delete.getBusinessObject());
	}
	/**
	 * 回显字表1
	 * @param EventValueMapReqVo
	 * @return
	 */
	@RequestMapping("/selectEventValueMapById")
	@ResponseBody
	public EventValueMapResVo selectEventValueMapById(EventValueMapReqVo eventVlaueMapReqVo){
		BasedataCommonResult<EventValueMapResVo> get = eventValueMapService.get(eventVlaueMapReqVo);
		return get.getBusinessObject();
	}
	/**
	 * 回显字表2
	 * @param EventValueMapReqVo
	 * @return
	 */
	@RequestMapping("/selectEventValueSupplyMapById")
	@ResponseBody
	public EventValueSupplyResVo getById(String id){
		 BasedataCommonResult<EventValueSupplyResVo> byId = eventValueSupplyService.getById(id);
		return byId.getBusinessObject();
	}
	
	/**
	 * 回显
	 * @param eventMapReqVo
	 * @return
	 */
	@RequestMapping("/selectEventMapById")
	@ResponseBody
	public EventMapResVo selectEventMapById(EventMapReqVo eventMapReqVo){
		BasedataCommonResult<EventMapResVo> get = eventMapService.get(eventMapReqVo);
		get.getBusinessObject();
		return get.getBusinessObject();
	}
	
	
	/**
	 * 更新父表
	 * @param eventMapReqVo
	 * @return
	 */
	@RequestMapping("/updateEventMap")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新事件")
	public Integer updateEventMap(EventMapReqVo eventMapReqVo){
		BasedataCommonResult<Integer> update = eventMapService.update(eventMapReqVo);
		update.getBusinessObject();
		return update.getBusinessObject();
	}
	
}

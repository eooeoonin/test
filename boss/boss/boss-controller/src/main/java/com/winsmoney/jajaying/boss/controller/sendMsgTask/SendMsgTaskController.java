package com.winsmoney.jajaying.boss.controller.sendMsgTask;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.message.api.response.Page;
import com.winsmoney.jajaying.message.api.response.SendTypeEnum;
import com.winsmoney.jajaying.message.api.task.config.ITaskConfigService;
import com.winsmoney.jajaying.message.api.task.config.request.TaskConfigRequest;
import com.winsmoney.jajaying.message.api.task.config.response.TaskConfigResponse;
import com.winsmoney.jajaying.message.api.task.config.response.TaskConfigStatusEnum;
import com.winsmoney.jajaying.message.api.template.ITemplateService;
import com.winsmoney.jajaying.message.api.template.request.TemplateReq;
import com.winsmoney.jajaying.message.api.template.response.TemplateRes;
import com.winsmoney.jajaying.user.service.IGroupService;
import com.winsmoney.jajaying.user.service.response.GroupResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
/**
 * 发送消息任务管理
 * @author xiaxingxing1
 *
 */
@Controller
@RequestMapping("/basedata_mgt/sendMsgTask")
public class SendMsgTaskController {

	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(SendMsgTaskController.class);
	
	@Autowired
	private ITaskConfigService taskConfigService;
	@Autowired
	private IGroupService groupService;
	@Autowired
	private ITemplateService templateService;
	
	/**
	 * 发送消息管理列表
	 * @param name     TaskConfigRequest
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/sendMsgTaskForList", method = RequestMethod.POST)
    @ResponseBody
    public Result sendMsgTaskForList( String name,  Integer pageIndex, Integer pageSize) {
		
		if(null == name || name.equals("")){
			name = null;
		}
		Page<TaskConfigResponse> result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(name));
			result = taskConfigService.list(name, pageIndex, pageSize);
			List<TaskConfigResponse> l = result.getList();
			for(TaskConfigResponse taskres : l){
				String id = taskres.getGroupId();
				GroupResVo g = groupService.get(id);
				taskres.setGroupId(g.getName());
			}
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{SendMsgTaskController  sendMsgTaskForList  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 删除一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/sendMsgTaskDelete", method = RequestMethod.POST)
    @ResponseBody
public Result sendMsgTaskDelete(int id) {
		Integer result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			result = taskConfigService.deleteById(id);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{SendMsgTaskController  sendMsgTaskDelete  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 获取一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/ById", method = RequestMethod.POST)
    @ResponseBody
    public Result ById(int id) {
		TaskConfigResponse result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			result = taskConfigService.getById(id);
			String groupId = result.getGroupId();
			GroupResVo vo = groupService.get(groupId);
			result.setGroupId(vo.getName());
			String tempName = null;
			Integer templateId = result.getTemplateId();
			if(null != templateId) {
				TemplateRes byId = templateService.getById(templateId);
				tempName = byId.getName();
			}
			HashMap<Object,Object> map = new HashMap<>();
			if(null == map || map.equals("")){
				map = null;
			}
			map.put("tempName", tempName);
			map.put("result", result);
			return Result.success( map);
		}catch (Exception e) {
			logger.error("{SendMsgTaskController  ById  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 更新
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新消息任务")
    public Result updateTask(int id, TaskConfigRequest taskConfigRequest,String sendTypeString) {
		Integer result  = null;
		try{
			if(sendTypeString == null || "".equals(sendTypeString)){
				return Result.error("接口调用失败");
			}else{
				taskConfigRequest.setSendType(SendTypeEnum.valueOf(sendTypeString));
			}
			if (StringUtils.isEmpty(taskConfigRequest.getName())) {
				taskConfigRequest.setName(null);
			}
			if (StringUtils.isEmpty(taskConfigRequest.getGroupId())) {
				taskConfigRequest.setGroupId(null);
			}
			if (StringUtils.isEmpty(taskConfigRequest.getMessageName())) {
				taskConfigRequest.setMessageName(null);
			}
			if (StringUtils.isEmpty(taskConfigRequest.getMessageContent())) {
				taskConfigRequest.setMessageContent(null);
			}
			if (null != taskConfigRequest.getTemplateId() && 0 != taskConfigRequest.getTemplateId()){
				taskConfigRequest.setMessageName(null);
			}
			if (StringUtils.isEmpty(taskConfigRequest.getWxTemplateId())) {
				taskConfigRequest.setWxTemplateId(null);
			}
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			result = taskConfigService.update(id, taskConfigRequest);
			return Result.success( result);
		}catch (Exception e) {
			logger.error("{SendMsgTaskController  updateTask  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 撤销任务
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/sendMsgTaskcardChe", method = RequestMethod.POST)
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "撤销消息任务")
    public Result sendMsgTaskcardChe(int id, String status) {
		Integer result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			result = taskConfigService.updateForStatus(id, TaskConfigStatusEnum.valueOf(status));
			return Result.success( result);
		}catch (Exception e) {
			logger.error("{SendMsgTaskController  sendMsgTaskcardChe  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 新建任务
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/createMsgTaskcardChe", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "新建消息任务")
    public Result createMsgTaskcardChe(TaskConfigRequest taskConfigRequest,String sendTypeString) {
		try{
			if(sendTypeString == null || "".equals(sendTypeString)){
				return Result.error("接口调用失败");
			}else{
				taskConfigRequest.setSendType(SendTypeEnum.valueOf(sendTypeString));
			}
			if (StringUtils.isEmpty(taskConfigRequest.getName())) {
				taskConfigRequest.setName(null);
			}
			if (StringUtils.isEmpty(taskConfigRequest.getGroupId())) {
				taskConfigRequest.setGroupId(null);
			}
			if (StringUtils.isEmpty(taskConfigRequest.getMessageName())) {
				taskConfigRequest.setMessageName(null);
			}
			if (StringUtils.isEmpty(taskConfigRequest.getMessageContent())) {
				taskConfigRequest.setMessageContent(null);
			}
			if (null != taskConfigRequest.getTemplateId() && 0 != taskConfigRequest.getTemplateId()){
				taskConfigRequest.setMessageName(null);
			}
			if (StringUtils.isEmpty(taskConfigRequest.getWxTemplateId())) {
				taskConfigRequest.setWxTemplateId(null);
			}
			logger.info("接口{}入参：" + JSONObject.toJSONString(taskConfigRequest));
			taskConfigService.insert(taskConfigRequest);
			return Result.success("success");
		}catch (Exception e) {
			logger.error("{SendMsgTaskController  sendMsgTaskcardChe  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	
	
	/**
	 * 获取所有的TAG
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getAllTagData", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllTagData() {
		try{
			PageInfo<GroupResVo> re  = groupService.list(null,1,1000);
			return Result.success(re.getList());
		}catch (Exception e) {
			logger.error("{SendMsgTaskController  sendMsgTaskcardChe  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	
	/**
	 * 获取所有的模板
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/getAllTemplate", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllTemplate() {
		try{
			Page<TemplateRes> re = templateService.queryForList(new TemplateReq(), null, null);
			return Result.success(re.getList());
		}catch (Exception e) {
			logger.error("{SendMsgTaskController  sendMsgTaskcardChe  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	

	
	
	
	/*@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
    @ResponseBody
    public Result updateTask(String id ,GroupReqVo groupReqVo){
		if(null == id || id.equals("")){
			id = null;
		}
		Integer	update  = null;
		try {
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			update = groupService.update(id, groupReqVo);
			return Result.success(update);
		} catch (Exception e) {
			logger.error("{SendMsgTaskController  updateTask  调用失败}", e);
			return Result.error("接口调用失败");
		}
	}
	
	@RequestMapping(value = "/getById", method = RequestMethod.POST)
    @ResponseBody
    public Result getById(String id){
		GroupResVo	Gid  = null;
		try {
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			Gid = groupService.get(id);
			return Result.success(Gid);
		} catch (Exception e) {
			logger.error("{SendMsgTaskController  getById  调用失败}", e);
			return Result.error("接口调用失败");
		}
	}
	
	@RequestMapping(value = "/addTemplate", method = RequestMethod.POST)
    @ResponseBody
	public Result addTemplate(GroupReqVo groupReqVo){
		Integer	insertCreate  = null;
		try {
			logger.info("接口{}入参：" + JSONObject.toJSONString(groupReqVo));
			insertCreate = groupService.create(groupReqVo);
			return Result.success(insertCreate);
		} catch (Exception e) {
			logger.error("{SendMsgTaskController  addTemplate  调用失败}", e);
			return Result.error("接口调用失败");
		}
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

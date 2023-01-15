package com.winsmoney.jajaying.boss.controller.basedata;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;




import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IMsgTemplateService;
import com.winsmoney.jajaying.basedata.service.request.MsgTemplateReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.MsgTemplateResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
/**
 * 系统消息
 * 
 * */
@Controller

@RequestMapping(value="/basedata_mgt/message")
public class MessageSysController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(MessageSysController.class);
	@Autowired
	private IMsgTemplateService message;
	
	
	/**
     * 分页列表消息模板
     * @param msgTemplateReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
		@RequestMapping(value="message_system")
		@ResponseBody
    	public List<MsgTemplateResVo> all(Integer pageNo,Integer pageSize){
			MsgTemplateReqVo msgTemplateReqVo = new MsgTemplateReqVo();
			
			BasedataCommonResult<PageInfo<MsgTemplateResVo>> list = message.listMsgTemplate(msgTemplateReqVo,pageNo,pageSize);
			
			list.getBusinessObject().getList();
			return list.getBusinessObject().getList();

	}
	/**
     * 单笔查询 消息模板
     * @param msgTemplateReqVo
     * @return MsgTemplateResVo
     */
	@RequestMapping(value="message_compile")
	
	@ResponseBody
	
	public MsgTemplateResVo iDmessage(String id){ 
		MsgTemplateReqVo msgTemplateReqVo = new MsgTemplateReqVo();
		msgTemplateReqVo.setId(id);
		logger.info("接口{}入参：" + JSONObject.toJSONString(msgTemplateReqVo),MessageSysController.class);
	    BasedataCommonResult<MsgTemplateResVo>  selectByid =  message.getMsgTemplate(msgTemplateReqVo);
	    logger.info("接口{}出参：" + JSONObject.toJSONString(selectByid),MessageSysController.class);
	    MsgTemplateResVo byid = selectByid.getBusinessObject();
		return byid;
	}
	/**
     * 
     *更新
     */
	@RequestMapping(value="message/edit")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新消息模板")
	public int updateMessage(MsgTemplateReqVo msgTemplateReqVo){
		logger.info("接口{}入参：" + JSONObject.toJSONString(msgTemplateReqVo),MessageSysController.class);
		BasedataCommonResult<Integer> saveMessage = message.updateMsgTemplate(msgTemplateReqVo);
		logger.info("接口{}出参：" + JSONObject.toJSONString(saveMessage),MessageSysController.class);
		return saveMessage.getBusinessObject();
	}
	
	
	

	//----------------条件查询$$分页--------------------------------------
	//@RequestMapping(value="getErrorCodeBySelected", method=RequestMethod.POST)
	
	@RequestMapping(value="messageSelectAll",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<MsgTemplateResVo> getMessageSelected(MsgTemplateReqVo msgTemplateResVo, String pageNo, String pageSize){
		if(StringUtils.isBlank(msgTemplateResVo.getSubject())){
			msgTemplateResVo.setSubject(null);
		}
		if(StringUtils.isBlank(msgTemplateResVo.getId())){
			msgTemplateResVo.setId(null);
		}
		if(StringUtils.isBlank(msgTemplateResVo.getType())){
			msgTemplateResVo.setType(null);
		}
		logger.info("接口{}入参：" + JSONObject.toJSONString(msgTemplateResVo),MessageSysController.class);
		BasedataCommonResult<PageInfo<MsgTemplateResVo>>  messageTemplate = message.listMsgTemplate(msgTemplateResVo, (int)Double.parseDouble(pageNo),(int)Double.parseDouble(pageSize));
		logger.info("接口{}出参：" + JSONObject.toJSONString(messageTemplate),MessageSysController.class);
		PageInfo<MsgTemplateResVo> messageList = messageTemplate.getBusinessObject();
		return messageList;
	}
	/**
     * 添加 消息模板
     * @param msgTemplateReqVo
     * @return Integer
     */
	@RequestMapping(value="messageInsert")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加消息模板")
	public Integer insertMsgTemplate(MsgTemplateReqVo msgTemplateReqVo){
		logger.info("接口{}入参：" + JSONObject.toJSONString(msgTemplateReqVo),MessageSysController.class);
		BasedataCommonResult<Integer> insertMessage = message.insertMsgTemplate(msgTemplateReqVo);
		logger.info("接口{}出参：" + JSONObject.toJSONString(insertMessage),MessageSysController.class);
		return insertMessage.getBusinessObject();
	}
	
	
	
	
}

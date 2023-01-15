package com.winsmoney.jajaying.boss.controller.basedata;



import java.util.ArrayList;
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
import com.winsmoney.jajaying.msgcenter.service.IMessageService;
import com.winsmoney.jajaying.msgcenter.service.enums.MessageType;
import com.winsmoney.jajaying.msgcenter.service.request.MessageReqVo;
import com.winsmoney.jajaying.msgcenter.service.request.ReceiverReqVo;
import com.winsmoney.jajaying.msgcenter.service.response.MessageCommonResult;
import com.winsmoney.jajaying.msgcenter.service.response.MessageResVo;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.IUserService;
import com.winsmoney.jajaying.user.service.enums.AuthState;
import com.winsmoney.jajaying.user.service.request.UserInfo1ReqVo;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.redis.core.RedisList;
import com.winsmoney.platform.framework.redis.support.ICache;
/**
 * 查询用户ID,并发短信
 * 
 * */
@Controller

@RequestMapping(value="/basedata_mgt/selectuserid")
public class SelectUseridController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(SelectUseridController.class);
	
	private final static String BATCH_SMS_KEY = "L02_BATCHSMS";
	@Autowired
	private IUserInfoService userInfoService;  
	
	@Autowired
	private IUserService userInfoService2;
	
	@Autowired
	private IMessageService messageService;
	@Autowired
	protected RedisList redis;
	@Autowired
	protected ICache<String> redisMagager;
//----------------条件查询$$分页--------------------------------------
	//@RequestMapping(value="getErrorCodeBySelected", method=RequestMethod.POST)
	
	@RequestMapping(value="select",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<UserInfoResVo> getMessageSelected(UserInfo1ReqVo userInfoReqVo, String pageNo, String pageSize){
		
		if(StringUtils.isBlank(userInfoReqVo.getProjectCode())){ //社区号
			userInfoReqVo.setProjectCode(null);
		}
		if(StringUtils.isBlank(userInfoReqVo.getRealName())){ //用户姓名
			userInfoReqVo.setRealName(null);
		}
		if(StringUtils.isBlank(userInfoReqVo.getRegisterMobile())){ //手机号
			userInfoReqVo.setRegisterMobile(null);
		}
		if(StringUtils.isBlank(userInfoReqVo.getId())){ //用户ID
			userInfoReqVo.setId(null);;
		}

		logger.info("接口{}入参：" + JSONObject.toJSONString(userInfoReqVo),SelectUseridController.class);
		
		
		UserCommonResult<PageInfo<UserInfoResVo>>  list = userInfoService.listUserInfoByProjectCode( userInfoReqVo,(int)Double.parseDouble(pageNo),(int)Double.parseDouble(pageSize));
	
		logger.info("接口{}出参：" + JSONObject.toJSONString(list),SelectUseridController.class);
		PageInfo<UserInfoResVo> messageList = list.getBusinessObject();
		return messageList;
	}	
	//单选增加
	@RequestMapping(value="insertone",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<UserInfoResVo> insertOne(String id){
		UserCommonResult<UserInfoResVo> byId = userInfoService.getByRoleUserId(id);
		
		redis.lpush("sendMessage", byId.getBusinessObject());
		
		return null;
	}
	//单选删除
	@RequestMapping(value="deleteone",method=RequestMethod.POST)
	@ResponseBody
	public String deleteOne(String id){
		 List<UserInfoResVo> lrange = redis.lrange("sendMessage", 0, 1000, UserInfoResVo.class);
		System.out.println(lrange);
		 for(int i = 0; i < lrange.size(); i++)  
	        {  
			  UserInfoResVo userInfoResVo = lrange.get(i); 
			  String id2 = userInfoResVo.getId();
			  if(id.equals(id2)){
				  System.out.println("1");
				  redis.lrem("sendMessage",1, lrange.get(i));
					return "成功";
			  }
	         
	        } 

		return null;
	}
	//全选增加
	@RequestMapping(value="insertall",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<UserInfoResVo> insertAll(String id){
		
		String[] split = id.split(",");
		for( int i=0;i<split.length;i++){
			UserCommonResult<UserInfoResVo> byId = userInfoService.getByRoleUserId(split[i]);
			
			redis.lpush("sendMessage", byId.getBusinessObject());
		}
		return null;
	}
	
	//全选删除
	@RequestMapping(value="deleteall",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<UserInfoResVo> deleteAll(String id){
		String[] split = id.split(",");
		for( int i=0;i<split.length;i++){
		redis.lpop("sendMessage", UserInfoResVo.class);
		}
		return null;
	}
	//页面加载初始删除
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<UserInfoResVo> delete(){
		redisMagager.delete("sendMessage");
		return null;
	}
	//从redis中读取数据 列表显示
	@RequestMapping(value="SMSlist",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<UserInfoResVo> List(int pageNo,int pageSize){
		List<UserInfoResVo> lrange2 = redis.lrange("sendMessage", 0, 1000, UserInfoResVo.class);
		int total = lrange2.size();//总条数
		List<UserInfoResVo> lrange = redis.lrange("sendMessage", pageNo*pageSize-pageSize, pageNo*pageSize-1, UserInfoResVo.class);
		List<UserInfoResVo> list =new ArrayList();
		PageInfo<UserInfoResVo> pageInfo=new PageInfo();
		for(int i = 0; i < lrange.size(); i++)  
        {  
			 UserInfoResVo userInfoResVo = lrange.get(i); 
			 list.add(userInfoResVo);
			 pageInfo.setList(list);
        }
	  
		pageInfo.setPageSize(pageSize);
		pageInfo.setTotal(total);//总条数
		Double total2=(double) total;
		double c=Math.ceil(total2/pageSize);
		pageInfo.setPages((int)c);//总页数
		pageInfo.setPageNum(pageNo);//当前页
		return pageInfo;
		
	}
	//根据用户Id发短信
	@RequestMapping(value="sndMsgById",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "根据用户Id发短信")
	public String SendMsgById(String content){
		List<UserInfoResVo> lrange = redis.lrange("sendMessage", 0, 1000, UserInfoResVo.class);
		MessageReqVo messageReqVo = new MessageReqVo();
		messageReqVo.setContent(content);
		messageReqVo.setMerchant("P2P");
		messageReqVo.setType(MessageType.SMS);
		messageReqVo.setKey(BATCH_SMS_KEY);
		List<ReceiverReqVo> receiveres = new ArrayList<>();
		if(lrange.size()<1000){
			
		for(int i = 0; i < lrange.size(); i++)  
        {  
			UserInfoResVo userInfoResVo = lrange.get(i); 
			String registerMobile = userInfoResVo.getRegisterMobile();
			
			ReceiverReqVo receiverReqVo = new ReceiverReqVo();
			receiverReqVo.setCellphone(registerMobile);
			receiveres.add(receiverReqVo);
			messageReqVo.setReceiveres(receiveres);
			MessageCommonResult<MessageResVo> sendMsgForBoss = messageService.sendMsgForBoss(messageReqVo);
        
        }
		 return "成功";
		}
		return null;
		
	}
	
	}
	
	
	


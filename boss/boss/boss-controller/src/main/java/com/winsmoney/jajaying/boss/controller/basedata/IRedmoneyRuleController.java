package com.winsmoney.jajaying.boss.controller.basedata;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IRedmoneyRuleService;
import com.winsmoney.jajaying.basedata.service.request.RedmoneyRuleReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.RedmoneyRuleResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;




@Controller
@RequestMapping("/basedata_mgt/redmoneyrules")
public class IRedmoneyRuleController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(IRedmoneyRuleController.class);
	@Autowired
	private IRedmoneyRuleService redmoneyRuleService;
	
	/**
     * 分页列表红包规则
     * @param redmoneyRuleReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
	@RequestMapping(value = "/redmoneyRulelist",method=RequestMethod.POST)
	@ResponseBody
	public  PageInfo<RedmoneyRuleResVo> redmoneyRulelist(RedmoneyRuleReqVo redmoneyRuleReqVo,int pageNo, int pageSize){
		logger.info("接口{}入参：" + JSONObject.toJSONString(redmoneyRuleReqVo),IRedmoneyRuleService.class);
		BasedataCommonResult<PageInfo<RedmoneyRuleResVo>> result = redmoneyRuleService.list(redmoneyRuleReqVo, pageNo, pageSize);
		logger.info("接口{}出参：" + JSONObject.toJSONString(result),IRedmoneyRuleService.class);
		PageInfo<RedmoneyRuleResVo> list = result.getBusinessObject();
		
		return list;
	}
	
	  /**
	    * 根据id查询 红包规则
	    * @param id
	    * @return RedmoneyRuleResVo
	    */
	@RequestMapping(value = "/redmoneyRuleselect",method=RequestMethod.POST)
	@ResponseBody
	public RedmoneyRuleResVo redmoneyRuleselect(String id){
		logger.info("接口{}入参：" + JSONObject.toJSONString(id),IRedmoneyRuleService.class);
		BasedataCommonResult<RedmoneyRuleResVo> result = redmoneyRuleService.getById(id);
		logger.info("接口{}出参：" + JSONObject.toJSONString(result),IRedmoneyRuleService.class);
		return result.getBusinessObject();
	}
	
	/**
     * 更新 红包规则
     * @param redmoneyRuleReqVo
     * @return
     */
	@RequestMapping(value = "/redrileedit",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新 红包规则")
	public Integer redmoneyRuleedit(RedmoneyRuleReqVo redmoneyRuleReqVo,HttpServletRequest request){
		
		Staff staffSession =  (Staff)request.getSession().getAttribute("adminInfo");
		redmoneyRuleReqVo.setEditedBy(staffSession.getStaffName());
		logger.info("接口{}入参：" + JSONObject.toJSONString(redmoneyRuleReqVo),IRedmoneyRuleService.class);
		 BasedataCommonResult<Integer> result = redmoneyRuleService.update(redmoneyRuleReqVo);
		 logger.info("接口{}出参：" + JSONObject.toJSONString(result),IRedmoneyRuleService.class);
		 return result.getBusinessObject();
	}
   
	/**
     * 删除 红包规则
     * @param id
     * @return
     */
	@RequestMapping(value = "/redmoneyRuledelete",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除 红包规则")
	public Integer redmoneyRuledelete (String id){
		logger.info("接口{}入参：" + JSONObject.toJSONString(id),IRedmoneyRuleService.class);
		BasedataCommonResult<Integer> result = redmoneyRuleService.delete(id);
		logger.info("接口{}出参：" + JSONObject.toJSONString(result),IRedmoneyRuleService.class);
		return result.getBusinessObject();
	}
    
	 /**
     * 添加 红包规则
     * @param redmoneyRuleReqVo
     * @return Integer
     */
	@RequestMapping(value = "/redmoneyRuleadd",method=RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加 红包规则")
	public BasedataCommonResult<Integer> redmoneyRuleadd (RedmoneyRuleReqVo redmoneyRuleReqVo,HttpServletRequest request){
		Staff staffSession =  (Staff)request.getSession().getAttribute("adminInfo");
		redmoneyRuleReqVo.setEditedBy(staffSession.getStaffName());
		logger.info("接口{}入参：" + JSONObject.toJSONString(redmoneyRuleReqVo),IRedmoneyRuleService.class);
		BasedataCommonResult<Integer> result = redmoneyRuleService.insert(redmoneyRuleReqVo);
		logger.info("接口{}出参：" + JSONObject.toJSONString(result),IRedmoneyRuleService.class);
		return result;
	}
    
	

}

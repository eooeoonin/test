package com.winsmoney.jajaying.boss.controller.templatePare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.message.api.response.Page;
import com.winsmoney.jajaying.message.api.templatepara.ITemplateParaService;
import com.winsmoney.jajaying.message.api.templatepara.request.TemplateParaReq;
import com.winsmoney.jajaying.message.api.templatepara.response.TemplateParaRes;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
/**
 * 模板变量
 * @author xiaxingxing1
 *
 */
@Controller
@RequestMapping("/basedata_mgt/TemplatePara")
public class TemplatePareController {

	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(TemplatePareController.class);
	
	@Autowired
	private ITemplateParaService templateParaService;
	
	/**
	 * 变量列表
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/templateParaForList", method = RequestMethod.POST)
    @ResponseBody
	 public Result templateParaForList(TemplateParaReq entity,Integer pageIndex, Integer pageSize) {
			if(null == entity || entity.equals("")){
				entity = null;
			}
			Page<TemplateParaRes> result  = null;
			try{
				logger.info("接口{}入参：" + JSONObject.toJSONString(entity));
				result = templateParaService.queryForList(entity, pageIndex, pageSize);
				return Result.success(result);
			}catch (Exception e) {
 				logger.error("{TemplatePareController  templateParaForList  调用失败}", e);
				return Result.error("接口调用失败");
			}
	    }
	
	/**
	 * 新增一条变量
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/templateParaInsert", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "新增模板变量")
    public Result templateParaInsert(TemplateParaReq entity) {
		if(null == entity || entity.equals("")){
			entity = null;
		}
		Integer result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(entity));
			result = templateParaService.insert(entity);
			return Result.success(result);
		}catch (Exception e) {
				logger.error("{TemplatePareController  templateParaInsert  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 按照ID查询一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/templateById", method = RequestMethod.POST)
    @ResponseBody
    public Result templateById(int id) {
		
		TemplateParaRes result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			result = templateParaService.getById(id);
			return Result.success(result);
		}catch (Exception e) {
				logger.error("{TemplatePareController  templateById  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 编辑一条变量
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/templateEdit", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.UPDATE, content = "更新模板变量")
    public Result templateEdit(TemplateParaReq entity) {
		if(entity == null || entity.equals("")){
			entity = null;
		}
		Integer result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(entity));
			result = templateParaService.update(entity);
			return Result.success(result);
		}catch (Exception e) {
				logger.error("{TemplatePareController  templateEdit  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
	 * 根据ID删除一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/templateParaDelete", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.DELETE, content = "删除模板变量")
    public Result templateParaDelete(int id) {
		
		Integer result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			result = templateParaService.delete(id);
			return Result.success(result);
		}catch (Exception e) {
				logger.error("{TemplatePareController  templateParaDelete  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
}

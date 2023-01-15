package com.winsmoney.jajaying.boss.controller.sign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.ISignConfigService;
import com.winsmoney.jajaying.basedata.service.request.SignConfigReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.SignConfigResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

@Controller
@RequestMapping("/sign/config")
public class SignConfigController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(SignConfigController.class);
	@Autowired
	private ISignConfigService signConfigService;
	@RequestMapping("/getAll")
 	@ResponseBody
 	public Result getAll(SignConfigReqVo signConfigReqVo, int pageNo, int pageSize){
		try {
			BasedataCommonResult<PageInfo<SignConfigResVo>> result = signConfigService.list(signConfigReqVo, pageNo, pageSize);
			if(result.isSuccess()) {
				return Result.success(result.getBusinessObject());
			}else {
				return Result.error("调用失败");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
 	}
	@RequestMapping("/upLine")
 	@ResponseBody
    @AduitLog(type = OperType.UPDATE, content = "更新签到配置")
 	public Result upLine(SignConfigReqVo signConfigReqVo){
		BasedataCommonResult<Integer> result = signConfigService.update(signConfigReqVo);
		if(result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		}else {
			return Result.error("调用失败");
		}
 	}
	@RequestMapping("/downLine")
 	@ResponseBody
 	public Result downLine(SignConfigReqVo signConfigReqVo){
		BasedataCommonResult<Integer> result = signConfigService.update(signConfigReqVo);
		if(result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		}else {
			return Result.error("调用失败");
		}
 	}
	@RequestMapping("/del")
 	@ResponseBody
    @AduitLog(type = OperType.DELETE, content = "删除签到配置")
 	public Result del(String id){
		BasedataCommonResult<Integer> result = signConfigService.delete(id);
		if(result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		}else {
			return Result.error("调用失败");
		}
 	}
	@RequestMapping("/add")
 	@ResponseBody
    @AduitLog(type = OperType.CREATE, content = "添加签到配置")
 	public Result add(SignConfigReqVo signConfigReqVo){
		signConfigReqVo.setStatus("0");
		BasedataCommonResult<Integer> result = signConfigService.insert(signConfigReqVo);
		if(result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		}else {
			return Result.error("调用失败");
		}
 	}
	@RequestMapping("/edit")
 	@ResponseBody
    @AduitLog(type = OperType.UPDATE, content = "更新签到配置")
 	public Result edit(SignConfigReqVo signConfigReqVo){
		BasedataCommonResult<Integer> result = signConfigService.update(signConfigReqVo);
		if(result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		}else {
			return Result.error("调用失败");
		}
 	}
	@RequestMapping("/getById")
 	@ResponseBody
 	public Result getById(String id){
		BasedataCommonResult<SignConfigResVo> result = signConfigService.getById(id);
		if(result.isSuccess()) {
			return Result.success(result.getBusinessObject());
		}else {
			return Result.error("调用失败");
		}
 	}
}

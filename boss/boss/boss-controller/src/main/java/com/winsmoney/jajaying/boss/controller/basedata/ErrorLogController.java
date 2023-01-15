package com.winsmoney.jajaying.boss.controller.basedata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IErrorLogService;
import com.winsmoney.jajaying.basedata.service.request.ErrorLogReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.ErrorLogResVo;
import com.winsmoney.jajaying.boss.domain.utils.Result;

@Controller
@RequestMapping("/basedata_mgt/errorlog")
public class ErrorLogController {
	
	@Autowired
	private IErrorLogService errorLogService;
	
	@RequestMapping("/errorLogList")
	@ResponseBody
	public Result errorLogList(ErrorLogReqVo errorLogReqVo, int pageNo, int pageSize){
		BasedataCommonResult<PageInfo<ErrorLogResVo>> errorLogResVo = errorLogService.list(errorLogReqVo, pageNo, pageSize);
		if(errorLogResVo.isSuccess())
			return Result.success(errorLogResVo.getBusinessObject());
		else
			return Result.error(errorLogResVo.getResultCodeMsg());
	}
}

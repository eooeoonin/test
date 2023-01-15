package com.winsmoney.jajaying.boss.controller.basedata;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.ISystemConfigService;
import com.winsmoney.jajaying.basedata.service.request.SystemConfigReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.SystemConfigResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
/**
 * 
 * 系统列表
 * @author Moon
 *
 */
@Controller
@RequestMapping("/basedata_mgt/isystemconfig_list")
public class IsystemConfigController {
	
	@Autowired
	private ISystemConfigService iSystemConfigService;
	
	
	
	/**
	 * 列表展示
	 * @param systemConfigReqVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getSystemConfig")
	@ResponseBody
	public PageInfo<SystemConfigResVo> selectIsystemConfig(SystemConfigReqVo systemConfigReqVo,int pageNo,int pageSize){
		if(StringUtils.isBlank(systemConfigReqVo.getSystemCode())){
			systemConfigReqVo.setSystemCode(null);
		}

		
		BasedataCommonResult<PageInfo<SystemConfigResVo>> listSystemConfig = iSystemConfigService.listSystemConfig(systemConfigReqVo, pageNo, pageSize);
		PageInfo<SystemConfigResVo> eventvaluemapList = new PageInfo<SystemConfigResVo>();
		eventvaluemapList = listSystemConfig.getBusinessObject();
		return eventvaluemapList;
	}


	/**
	 * 添加
	 * @param systemConfigReqVo
	 */
	@RequestMapping("/insertSystemConfig")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加 系统信息")
	public Integer insertIsystemConfig(SystemConfigReqVo systemConfigReqVo){
		systemConfigReqVo.setAvailable(1);
		BasedataCommonResult<Integer> insertSystemConfig = iSystemConfigService.insertSystemConfig(systemConfigReqVo);
		return insertSystemConfig.getBusinessObject();
	}
	
	/**
	 * 刪除
	 * @param sid
	 */
	@RequestMapping("/deleteIsystemConfig")
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除 系统信息")
	public String deleteIsystemConfig(String sid){
		//System.out.println(sid); 
		BasedataCommonResult<Integer> deleteSystemConfig = iSystemConfigService.deleteSystemConfig(sid);
	    return JSON.toJSONString(deleteSystemConfig.getBusinessObject());
	}
	
		/**
	     * 单笔查询 系统配置(回显)
	     * @param id
	     * @return SystemConfigResVo
	     */
		@RequestMapping("/selectIsystemConfig")
		@ResponseBody
		public  SystemConfigResVo getById(String id){
			BasedataCommonResult<SystemConfigResVo> byId = iSystemConfigService.getById(id);
			byId.getBusinessObject();
			return byId.getBusinessObject();			
			
		}
	

	/**
	 * 修改
	 * @param systemConfigReqVo
	 */
	@RequestMapping("/updateIsystemConfig")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新 系统信息")
	public String updateIsystemCongfig(SystemConfigReqVo systemConfigReqVo){
		BasedataCommonResult<Integer> updateSystemConfig = iSystemConfigService.updateSystemConfig(systemConfigReqVo);
		updateSystemConfig.getBusinessObject();
		return JSON.toJSONString(updateSystemConfig.getBusinessObject());
	}
	
	
}

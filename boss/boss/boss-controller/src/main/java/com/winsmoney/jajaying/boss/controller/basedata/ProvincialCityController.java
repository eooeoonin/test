package com.winsmoney.jajaying.boss.controller.basedata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IRegionService;
import com.winsmoney.jajaying.basedata.service.request.RegionReqVo;
import com.winsmoney.jajaying.basedata.service.request.SystemConfigReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.RegionResVo;
import com.winsmoney.jajaying.basedata.service.response.SystemConfigResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;

@Controller
@RequestMapping("/basedata_mgt/provincialcity_list")
public class ProvincialCityController {

	@Autowired
	private IRegionService regionService;
	
	
	@RequestMapping("/getSystemConfig")
	@ResponseBody
	public PageInfo<RegionResVo> selectIsystemConfig(RegionReqVo regionReqVo,int pageNo, int pageSize){
//		if(StringUtils.isBlank(systemConfigReqVo.getSystemCode())){
//			systemConfigReqVo.setSystemCode(null);
//		}
		BasedataCommonResult<PageInfo<RegionResVo>> list =regionService.list(regionReqVo,pageNo,pageSize);
		PageInfo<RegionResVo> eventvaluemapList = new PageInfo<RegionResVo>();
		eventvaluemapList = list.getBusinessObject();
		return eventvaluemapList;
	}
	
	/**
     * 单笔查询 系统配置(回显)
     * @param id
     * @return SystemConfigResVo
     */
	@RequestMapping("/selectProvincialcity")
	@ResponseBody
	public  RegionResVo getById(String id){
		BasedataCommonResult<RegionResVo> byId = regionService.getById(id);
		byId.getBusinessObject();
		return byId.getBusinessObject();			
		
	}


	/**
	 * 修改
	 * @param systemConfigReqVo
	 */
	@RequestMapping("/updateProvincialcity")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新系统配置")
	public String updateIsystemCongfig(RegionReqVo regionReqVo){
		BasedataCommonResult<Integer> updateSystemConfig = regionService.update(regionReqVo);
		updateSystemConfig.getBusinessObject();
		return JSON.toJSONString(updateSystemConfig.getBusinessObject());
	}
	
	
	
	
	
}

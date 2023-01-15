package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IBannerService;
import com.winsmoney.jajaying.basedata.service.IHolidayService;
import com.winsmoney.jajaying.basedata.service.request.BannerReqVo;
import com.winsmoney.jajaying.basedata.service.request.HolidayReqVo;
import com.winsmoney.jajaying.basedata.service.request.SystemConfigReqVo;
import com.winsmoney.jajaying.basedata.service.response.BannerResVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.HolidayResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;

/**
 * 节假日管理接口
 * @author Moon
 *
 */
@Controller
@RequestMapping("/basedata_mgt/vacation_list")
public class VacationController {

	
	@Autowired
	private IHolidayService holidayService;
	
	  /**
	    * 根据id查询 
	    * @param id
	    * @return HolidayResVo
	    */
	@RequestMapping("/byId")
	@ResponseBody
	public    HolidayResVo getById(String id){
		BasedataCommonResult<HolidayResVo> byId = holidayService.getById(id);
			return byId.getBusinessObject();
		
	}
	

    /**
     * 分页列表
     *
     * @param bannerReqVo 查询条件
     * @param pageNo      当前页码
     * @param pageSize    每页条数
     * @return
     */
	@RequestMapping("/list")
	@ResponseBody
   public  PageInfo<HolidayResVo> list(HolidayReqVo holidayReqVo, Integer pageNo, Integer pageSize){
		
	
		BasedataCommonResult<PageInfo<HolidayResVo>> list = holidayService.list(holidayReqVo, pageNo, pageSize);
			 PageInfo<HolidayResVo> businessObject = list.getBusinessObject();
			 return businessObject;
		}

	/**
     * 添加或者新增
     *
     * @param bannerReqVo
     * @return Integer
     */
	@RequestMapping("/add")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加节假日")
	public BasedataCommonResult<Integer> insert(HolidayReqVo holidayReqVo, HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		BasedataCommonResult<Integer> status ;
		holidayReqVo.setEditedBy(role);
	
		if(StringUtils.isNotEmpty(holidayReqVo.getId())){
			
			status = holidayService.update(holidayReqVo);
		}else{
			status = holidayService.insert(holidayReqVo);
		}

	
		return status;
	}

    /**
     * 删除
     *
     * @param id
     * @return
     */
	@RequestMapping("/delete")
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "删除节假日")
	public BasedataCommonResult<Integer> delete(String id){
		BasedataCommonResult<Integer> delete = holidayService.delete(id);
	
		return delete;
		
	}
    /**
     * 将库中的节假日值初始化到redis中
     *
     * @param id
     * @return
     */
	@RequestMapping("/redis")
	@ResponseBody
	public BasedataCommonResult<Integer> initHolidaysToRedis(){
		BasedataCommonResult<Integer> initHolidaysToRedis = holidayService.initHolidaysToRedis();
	
		return initHolidaysToRedis;
		
	}


}

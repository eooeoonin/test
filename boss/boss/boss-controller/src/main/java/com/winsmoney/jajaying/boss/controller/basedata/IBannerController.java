package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IBannerService;
import com.winsmoney.jajaying.basedata.service.ISystemConfigService;
import com.winsmoney.jajaying.basedata.service.request.BannerReqVo;
import com.winsmoney.jajaying.basedata.service.request.SystemConfigReqVo;
import com.winsmoney.jajaying.basedata.service.response.BannerResVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
/**
 * 
 * APP维护
 * @author Moon
 *
 */
@Controller
@RequestMapping("/web_mgt/w_app_banner_list_123")
public class IBannerController {

	@Autowired
	private IBannerService bannerService;
	@Autowired  
    private HttpServletRequest request; 
	@Autowired
	private ISystemConfigService iSystemConfigService;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return BannerResVo
	 */
	@RequestMapping("/banner/one")
	@ResponseBody
	public String getById(String id) {
		BasedataCommonResult<BannerResVo> byId = bannerService.getById(id);
		//System.out.println(id);
		BannerResVo businessObject = byId.getBusinessObject();
		//System.out.println(JSON.toJSONString(businessObject));
		return JSON.toJSONString(businessObject);
	}
		/**
		 * 单笔查询
		 * 
		 * @param bannerReqVo
		 * @return BannerResVo
		 */
	@RequestMapping("/banner/cond")
	@ResponseBody
	public BasedataCommonResult<BannerResVo> get(BannerReqVo bannerReqVo){
		BasedataCommonResult<BannerResVo> basedataCommonResult = bannerService.get(bannerReqVo);
		//System.out.println(basedataCommonResult);
		return basedataCommonResult;
	}

    /**
     * 分页列表
     *
     * @param bannerReqVo 查询条件
     * @param pageNo      当前页码
     * @param pageSize    每页条数
     * @return
     */
	@RequestMapping("/banner/page")
	@ResponseBody
   public  String list(BannerReqVo bannerReqVo, Integer pageNo, Integer pageSize){
		bannerReqVo.setType("3");
		BasedataCommonResult<PageInfo<BannerResVo>> list = bannerService.listBanner(bannerReqVo, 1, 10);
			PageInfo<BannerResVo> businessObject = list.getBusinessObject();
			List<BannerResVo> list2 = businessObject.getList();
		//System.out.println(businessObject);
		return JSON.toJSONString(list2);
		}

	/**
     * 添加
     *
     * @param bannerReqVo
     * @return Integer
     */
	@RequestMapping("/banner/add")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增banner")
	public BasedataCommonResult<BannerResVo> insert(BannerReqVo bannerReqVo,HttpServletRequest httpRequest){

		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		bannerReqVo.setEditedBy(role);
		
		SystemConfigReqVo systemConfigReqVo=new SystemConfigReqVo();
//		systemConfigReqVo.setCodeKey("下载URL");
//	//通过通用iSystemConfigService获得前缀
//	BasedataCommonResult<SystemConfigResVo> systemConfig = iSystemConfigService.getSystemConfig(systemConfigReqVo);
//		String httpUrl = systemConfig.getBusinessObject().getCodeValue();
		
		 
		
//        String filePath =httpUrl+bannerReqVo.getPicture(); //下載url
    
       
        bannerReqVo.setPicture(bannerReqVo.getPicture());
		BasedataCommonResult<BannerResVo> insert = bannerService.insert(bannerReqVo);


	
		return insert;
	}

    /**
     * 删除
     *
     * @param id
     * @return
     */
	@RequestMapping("/banner/delete")
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除banner")
	public BasedataCommonResult<Integer> delete(String id){
		BasedataCommonResult<Integer> delete = bannerService.delete(id);
		//System.out.println(delete);
		return delete;
		
	}
	/**
     * 修改
     * @param bannerReqVo
     * @return
     */
	@RequestMapping("/banner/update")
	@ResponseBody	
	@AduitLog(type = OperType.UPDATE, content = "更新banner")
	public BasedataCommonResult<Integer>  updateCardBin(BannerReqVo bannerReqVo,HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		bannerReqVo.setEditedBy(role);
			bannerReqVo.setType("3");

			BasedataCommonResult<Integer> update = bannerService.update(bannerReqVo);
			
				//System.out.println(update);
			return update;
	}


}


package com.winsmoney.jajaying.boss.controller.crowdfunding;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IBannerService;
import com.winsmoney.jajaying.basedata.service.ISystemConfigService;
import com.winsmoney.jajaying.basedata.service.request.BannerReqVo;
import com.winsmoney.jajaying.basedata.service.request.SystemConfigReqVo;
import com.winsmoney.jajaying.basedata.service.response.BannerResVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.crowdfunding.service.IAppBannerService;
import com.winsmoney.jajaying.crowdfunding.service.enums.FileType;
import com.winsmoney.jajaying.crowdfunding.service.request.AppBannerReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.UploadFileReqVo;
import com.winsmoney.jajaying.crowdfunding.service.response.AppBannerResVo;
import com.winsmoney.jajaying.crowdfunding.service.response.CrowdfundingCommonResult;
/**
 * 
 * 众筹管理APP维护
 * @author Moon
 *
 */
@Controller
@RequestMapping("/crowdfunding/crowdfunding_banner_list")
public class AppBannerController {
		@Autowired
		private IAppBannerService appBannerService;
		@Autowired  
	    private HttpServletRequest request; 
		/**
		 * 单笔查询
		 * 
		 * @param id
		 * @return BannerResVo
		 */
		@RequestMapping("/banner/one")
		@ResponseBody
		public AppBannerResVo getById(String id) {
			AppBannerReqVo bannerReqVo=new AppBannerReqVo();
			bannerReqVo.setId(id);
			CrowdfundingCommonResult<AppBannerResVo> byId = appBannerService.selectBanner(bannerReqVo);
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
		@RequestMapping("/banner/page")
		@ResponseBody
		public PageInfo<AppBannerResVo> selectBannerPageList(AppBannerReqVo bannerReqVo,int pageNo,int pageSize){
			
			CrowdfundingCommonResult<com.github.pagehelper.PageInfo<AppBannerResVo>> list = appBannerService.selectBannerPageList(bannerReqVo, 1, 10);
				PageInfo<AppBannerResVo> businessObject = list.getBusinessObject();
				return businessObject;
			
			
			}

		/**
	     * 添加
	     *
	     * @param bannerReqVo
	     * @return Integer
	     */
		@RequestMapping("/banner/add")
		@ResponseBody
		public CrowdfundingCommonResult<Integer> insertBanner(AppBannerReqVo bannerReqVo,String picture){
			
			UploadFileReqVo uploadFile = new UploadFileReqVo();
			uploadFile.setPath(picture);
			uploadFile.setType(FileType.BANNER);

			bannerReqVo.setUploadFile(uploadFile);
			CrowdfundingCommonResult<Integer> insert = appBannerService.insertBanner(bannerReqVo);


		
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
		 public CrowdfundingCommonResult<Integer> deleteBanner(String id){
			CrowdfundingCommonResult<Integer> delete = appBannerService.deleteBanner(id);
	
			return delete;
			
		}
		/**
	     * 修改
	     * @param bannerReqVo
	     * @return
	     */
		@RequestMapping("/banner/update")
		@ResponseBody	
		public CrowdfundingCommonResult<Integer> updateBanner(AppBannerReqVo bannerReqVo,String picture){
			UploadFileReqVo uploadFile = new UploadFileReqVo();
			uploadFile.setPath(picture);
			uploadFile.setType(FileType.BANNER);
			bannerReqVo.setUploadFile(uploadFile);
				CrowdfundingCommonResult<Integer> update = appBannerService.updateBanner(bannerReqVo);
			
				return update;
		}

}

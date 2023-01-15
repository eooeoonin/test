package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IBannerService;
import com.winsmoney.jajaying.basedata.service.ISystemConfigService;
import com.winsmoney.jajaying.basedata.service.request.BannerReqVo;
import com.winsmoney.jajaying.basedata.service.response.BannerResVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.util.BeanMapper;
/**
 * 
 * APP维护
 * @author Moon
 *
 */
@Controller
@RequestMapping("/web_mgt/w_app_banner_list")
public class AppIBannerController {

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
	public Result list(BannerReqVo bannerReqVo, Integer pageNo, Integer pageSize) {
		try {
			bannerReqVo.setType("3");
			BasedataCommonResult<PageInfo<BannerResVo>> list = bannerService.listBanner(bannerReqVo, pageNo, pageSize);
			PageInfo<BannerResVo> businessObject = list.getBusinessObject();
			// System.out.println(businessObject);
			return Result.success(businessObject);
		} catch (Exception e) {
			return Result.error("error");
		}
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
	public BasedataCommonResult<BannerResVo> insertWithOrder(BannerReqVo bannerReqVo,HttpServletRequest httpRequest){

		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		bannerReqVo.setEditedBy(role);
		bannerReqVo.setType("3");
		bannerReqVo.setIsShow(0);
		bannerReqVo.setAvailable(1);
        bannerReqVo.setPicture(bannerReqVo.getPicture());
		BasedataCommonResult<BannerResVo> insert = bannerService.insertWithOrder(bannerReqVo);

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
	public Integer delete(String id){
		BasedataCommonResult<Integer> delete = bannerService.delete(id);
		//System.out.println(delete);
		return delete.getBusinessObject();
		
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
	
	/**
	 * 下调
	 * @param bannerReqVo
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping("/banner/orderDown")
	@ResponseBody	
	public Integer orderDown(BannerReqVo bannerReqVo,HttpServletRequest httpRequest) {
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		bannerReqVo.setEditedBy(role);
		bannerReqVo.setType("3");
		BasedataCommonResult<Integer> update = bannerService.orderDown(bannerReqVo);
		
		return update.getBusinessObject();
	}

	/**
	 * 上调
	 * @param bannerReqVo
	 * @param httpRequest
	 * @return
	 */
	@RequestMapping("/banner/orderUp")
	@ResponseBody	
	public Integer orderUp(BannerReqVo bannerReqVo,HttpServletRequest httpRequest) {
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		bannerReqVo.setEditedBy(role);
		bannerReqVo.setType("3");
		BasedataCommonResult<Integer> update = bannerService.orderUp(bannerReqVo);
		
		return update.getBusinessObject();
	}
	
	@RequestMapping(value = "/banner/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeStatus(BannerReqVo bannerReqVo, HttpServletRequest httpRequest) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 根据id获取
			BannerResVo bannerResVo = bannerService.getById(bannerReqVo.getId()).getBusinessObject();
			bannerReqVo = BeanMapper.map(bannerResVo, BannerReqVo.class);
			if (bannerResVo.getIsShow()== null || bannerResVo.getIsShow() == 1) {
				bannerReqVo.setIsShow(0); // 下线
			} else {
				bannerReqVo.setIsShow(1); // 上线
			}
			
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			String role = staffSession.getStaffName();
			bannerReqVo.setEditedBy(role);
			bannerReqVo.setType("3");
			BasedataCommonResult<Integer> updateResult = bannerService.update(bannerReqVo);
			if (updateResult.getBusinessObject() > 0) {
				result.put("flag", 1);
			} else {
				result.put("flag", 0);
			}
		} catch (Exception e) {
//			logger.error("新闻修改状态异常：" + e.getMessage(), e);
			result.put("result", e.getMessage());
		}

		return result;
	}

}


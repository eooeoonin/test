package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.Arrays;
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
import com.winsmoney.jajaying.basedata.service.INoticeService;
import com.winsmoney.jajaying.basedata.service.enums.Platform;
import com.winsmoney.jajaying.basedata.service.request.NoticeReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.NoticeResVo;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.platform.framework.core.util.BeanMapper;
/*
 * 公告信息
 */
@Controller
@RequestMapping("/basedata_mgt/app_notice_list")
public class IAppNoticeController {

	@Autowired
	private INoticeService iNoticeService ;
	
	
    /**
    * 根据id查询 
    * @param noticeReqVo
    * @return NoticeResVo
    */
	@RequestMapping("/notice/one")
	@ResponseBody
	public  String getById(String id){
		
		BasedataCommonResult<NoticeResVo> byId = iNoticeService.getById(id);
		NoticeResVo businessObject = byId.getBusinessObject();
		//System.out.println(JSON.toJSONString(businessObject));
		
		return JSON.toJSONString(businessObject);
	}
	
	/**
     * 单笔查询 
     * @param noticeReqVo
     * @return NoticeResVo
     */
	@RequestMapping("/notice/cond")
	@ResponseBody
	 public BasedataCommonResult<NoticeResVo> get(NoticeReqVo noticeReqVo){
		
		BasedataCommonResult<NoticeResVo> basedataCommonResult = iNoticeService.get(noticeReqVo);
		//System.out.println(basedataCommonResult);
		return basedataCommonResult;
	}
	

    /**
     * 分页列表
     * @param noticeReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
//	@RequestMapping("/notice/page")
//	@ResponseBody
//    public  String list(NoticeReqVo noticeReqVo,Integer pageNo, Integer pageSize){
//		
//		BasedataCommonResult<PageInfo<NoticeResVo>> list = iNoticeService.list(noticeReqVo, 1, 9);
//		PageInfo<NoticeResVo> businessObject = list.getBusinessObject();
//		List<NoticeResVo> list2 = businessObject.getList();
//		return JSON.toJSONString(list2);
//	}
	@RequestMapping("/notice/page")
	@ResponseBody
	public  PageInfo<NoticeResVo> listByPlatforms(String platform, String availableTimeBefore, 
				String availableTimeAfter, String available, Integer pageNo, Integer pageSize){
    	Map<String, Object> params = new HashMap<>();
		if (platform == null || platform.length() == 0) {
			params.put("platforms", Arrays.asList(
					new String[] {Platform.APP.getCode(), Platform.H5.getCode()}));
		} else {
			params.put("platforms", Arrays.asList(platform));
		}
		params.put("availableTimeBefore", availableTimeBefore);
		params.put("availableTimeAfter", availableTimeAfter);
		params.put("available", available);
		BasedataCommonResult<PageInfo<NoticeResVo>> list = iNoticeService.listByPlatforms(params, pageNo, pageSize);
		return list.getBusinessObject();
//		PageInfo<NoticeResVo> businessObject = list.getBusinessObject();
//		List<NoticeResVo> list2 = businessObject.getList();
//		return JSON.toJSONString(list2);
	}
	
	 /**
     * 添加 
     * @param noticeReqVo
     * @return Integer
     */
	@RequestMapping("/notice/add")
	@ResponseBody
	public BasedataCommonResult<Integer> insert(NoticeReqVo noticeReqVo, HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		
		noticeReqVo.setEditedBy(role);
		noticeReqVo.setAvailable(0); // 默认为关闭
		BasedataCommonResult<Integer> insert = iNoticeService.insert(noticeReqVo);
		
		return insert;
	}
	/**
     * 更新 
     * @param noticeReqVo
     * @return
     */
	@RequestMapping("/notice/update")
	@ResponseBody	
	public BasedataCommonResult<Integer>  update(NoticeReqVo noticeReqVo, HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		
		noticeReqVo.setEditedBy(role);
		
		BasedataCommonResult<Integer> update = iNoticeService.update(noticeReqVo);
		
		return update;
	}
    /**
     * 删除 
     * @param id
     * @return
     */
	@RequestMapping("/notice/delete")
	@ResponseBody	
	public Integer delete(String id){
		BasedataCommonResult<Integer> delete = iNoticeService.delete(id);
		return delete.getBusinessObject();
	}
	
	@RequestMapping(value = "/notice/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeStatus(NoticeReqVo noticeReqVo, HttpServletRequest httpRequest) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 根据id获取
			NoticeResVo noticeResVo = iNoticeService.getById(noticeReqVo.getId()).getBusinessObject();
			noticeReqVo = BeanMapper.map(noticeResVo, NoticeReqVo.class);
			if (noticeResVo.getAvailable()== null || noticeResVo.getAvailable() == 1) {
				noticeReqVo.setAvailable(0); // 下线
			} else {
				noticeReqVo.setAvailable(1); // 上线
			}
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			String role = staffSession.getStaffName();
			noticeReqVo.setEditedBy(role);
			BasedataCommonResult<Integer> updateResult = iNoticeService.update(noticeReqVo);
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

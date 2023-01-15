package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.INoticeService;
import com.winsmoney.jajaying.basedata.service.request.NoticeReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.NoticeResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
/*
 * 公告信息
 */
@Controller
@RequestMapping("/basedata_mgt/notice_list")
public class INoticeController {

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
	@RequestMapping("/notice/page")
	@ResponseBody
    public  String list(NoticeReqVo noticeReqVo,Integer pageNo, Integer pageSize){
		
		BasedataCommonResult<PageInfo<NoticeResVo>> list = iNoticeService.list(noticeReqVo, 1, 9);
		PageInfo<NoticeResVo> businessObject = list.getBusinessObject();
		List<NoticeResVo> list2 = businessObject.getList();
		return JSON.toJSONString(list2);
	}
	
	 /**
     * 添加 
     * @param noticeReqVo
     * @return Integer
     */
	@RequestMapping("/notice/add")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增公告")
	public BasedataCommonResult<Integer> insert(NoticeReqVo noticeReqVo, HttpServletRequest httpRequest){
		Staff staffSession = (Staff)  httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		
		noticeReqVo.setEditedBy(role);
		
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
	@AduitLog(type = OperType.UPDATE, content = "更新公告")
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
	@AduitLog(type = OperType.DELETE, content = "删除公告")
	public BasedataCommonResult<Integer> delete(String id){
		BasedataCommonResult<Integer> delete = iNoticeService.delete(id);
		return delete;
	}
}

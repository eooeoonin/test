package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.msgcenter.service.IInnerMessageService;
import com.winsmoney.jajaying.msgcenter.service.request.InnerMessageReqVo;
import com.winsmoney.jajaying.msgcenter.service.response.InnerMessageResVo;
import com.winsmoney.jajaying.msgcenter.service.response.MessageCommonResult;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
/**
 * 
 * @author Moon
 *
 */
@Controller
@RequestMapping("/basedata_mgt/innermessage_list")
public class InnerMessagController {
	
	@Autowired
	private IInnerMessageService iInnerMessageService;
	@Autowired
	private IUserInfoService userInfoService;

	/**
	 * 分页列表
	 * @param innerMessageReqVo 查询条件
	 * @param pageNo 当前页码
	 * @param pageSize 每页条数
	 * @return
	 */
	@RequestMapping("/innermessage/page")
	@ResponseBody
	public PageInfo<InnerMessageResVo> listInnerMessagePage(InnerMessageReqVo innerMessageReqVo, int pageNo, int pageSize) {
		try {
			MessageCommonResult<PageInfo<InnerMessageResVo>> listInnerMessagePage = iInnerMessageService.listInnerMessagePage(innerMessageReqVo, pageNo, pageSize);
//		List<InnerMessageResVo> list = listInnerMessagePage.getBusinessObject().getList();
			PageInfo<InnerMessageResVo> businessObject = listInnerMessagePage.getBusinessObject();
			return businessObject;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 插入接口
	 */

/**
 * 添加
 * @param request
 * @return
 */
@RequestMapping("/innermessage/insert")
@ResponseBody
@AduitLog(type = OperType.CREATE, content = "新增站内信")
public Boolean insertInnerMessage(InnerMessageReqVo innerMessageReqVo, HttpServletRequest httpRequest){
	Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
	String staffName = staffSession.getStaffName();
	innerMessageReqVo.setEditedBy(staffName);
	innerMessageReqVo.setMerchant("P2P");
	innerMessageReqVo.setStatus("0");
	String userId=innerMessageReqVo.getUserId();
	String[] split = userId.split(";");
	List list2=new ArrayList<>();
	for (String lsit : split) {
		list2.add(lsit);
	}
	//查询用户接口
	Boolean suc = null;
	UserCommonResult<List<UserInfoResVo>> listByIds = userInfoService.listByIds(list2);
	List<UserInfoResVo> businessObject = listByIds.getBusinessObject();
	for (int i = 0; i < businessObject.size(); i++) {
		String id = businessObject.get(i).getId();
		innerMessageReqVo.setUserId(id);
		MessageCommonResult<Integer> insertInnerMessage = iInnerMessageService.insertInnerMessage(innerMessageReqVo);
		suc = insertInnerMessage.isSuccess();
	}
	return suc;
	

	
}
}
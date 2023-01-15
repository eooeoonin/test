package com.winsmoney.jajaying.boss.controller.basedata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IReviewService;
import com.winsmoney.jajaying.basedata.service.request.ReviewReqVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.basedata.service.response.ReviewResVo;
/**
 * 意见信息
 */
@Controller
@RequestMapping("/basedata_mgt/review_info_list")
public class IReviewController {
	
	
	@Autowired
	private IReviewService iReviewService;
	 /**
     * 分页列表
     * @param reviewReqVo 查询条件
     * @param pageNo 当前页码
     * @param pageSize 每页条数
     * @return
     */
	@RequestMapping("/ireview/page")
	@ResponseBody
	public PageInfo<ReviewResVo> list(ReviewReqVo reviewReqVo,Integer pageNo, Integer pageSize){
		
		BasedataCommonResult<PageInfo<ReviewResVo>> list = iReviewService.list(reviewReqVo, pageNo, pageSize);
	//	System.out.println(list.getBusinessObject());
		return list.getBusinessObject();
	}

}

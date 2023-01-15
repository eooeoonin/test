package com.winsmoney.jajaying.boss.controller.activity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.activity.service.IChannelService;
import com.winsmoney.jajaying.activity.service.IChannelTypeService;
import com.winsmoney.jajaying.activity.service.ITemplateService;
import com.winsmoney.jajaying.activity.service.request.ChannelReqVo;
import com.winsmoney.jajaying.activity.service.request.ChannelTypeReqVo;
import com.winsmoney.jajaying.activity.service.request.TemplateReqVo;
import com.winsmoney.jajaying.activity.service.response.ActivityCommonResult;
import com.winsmoney.jajaying.activity.service.response.ChannelResVo;
import com.winsmoney.jajaying.activity.service.response.ChannelTypeResVo;
import com.winsmoney.jajaying.activity.service.response.TemplateResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Channel;
import com.winsmoney.jajaying.boss.domain.model.ChannelType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.model.Template;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.util.BeanMapper;

@Controller
@RequestMapping("/activity")
public class ChannelAndTeplateController {
	@Autowired 
	private IChannelService channelService;	
	@Autowired 
	private IChannelTypeService channelTypeService;
	@Autowired
	private ITemplateService templateService;
	@RequestMapping(value="/channelManage/getAll", method = RequestMethod.POST)
	@ResponseBody
	public Result getChannelList(Channel channel,int pageNo, int pageSize) {		
		try {
			if ("".equals(channel.getName())) {
				channel.setName(null);
			}
//			PageInfo<Channel> pChannel = channelDomain.list(channel, pageNo, pageSize);
			ActivityCommonResult<PageInfo<ChannelResVo>> list = channelService.list(BeanMapper.map(channel, ChannelReqVo.class), pageNo, pageSize);
			PageInfo<ChannelResVo> pChannel = list.getBusinessObject();
			return Result.success(pChannel);
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/channelManage/getAllData", method = RequestMethod.POST)
	@ResponseBody
	public Result getAllData(Channel channel) {		
		try {
			if ("".equals(channel.getName())) {
				channel.setName(null);
			}
//			List<Channel> pChannel = channelDomain.list(channel);
			ActivityCommonResult<List<ChannelResVo>> list = channelService.list(BeanMapper.map(channel, ChannelReqVo.class));
			List<ChannelResVo> pChannel = list.getBusinessObject();
			return Result.success(pChannel);
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/channelManage/getById", method = RequestMethod.POST)
	@ResponseBody
	public Result getById(String id) {		
		try {
//			Channel c = channelDomain.getById(id);
			ActivityCommonResult<ChannelResVo> result = channelService.getById(id);
			ChannelResVo c = result.getBusinessObject();
			return Result.success(c);
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/channelManage/addChannel", method = RequestMethod.POST)
	@ResponseBody
    @AduitLog(type = OperType.CREATE, content = "新增渠道")
	public Result addChannel(Channel channel,HttpServletRequest httpRequest) {		
		try {
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			channel.setCreatedBy(staffSession.getId());
			channel.setEditedBy(staffSession.getRealName());
//			channelDomain.insert(channel);
			ActivityCommonResult<Integer> insert = channelService.insert(BeanMapper.map(channel, ChannelReqVo.class));
			if (insert.isFailure()) {
				throw new RuntimeException(insert.getResultCodeMsg());
			}
			return Result.success("保存成功");
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/channelManage/editChannel", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新渠道")
	public Result editChannel(Channel channel,HttpServletRequest httpRequest) {		
		try {
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			channel.setEditedBy(staffSession.getRealName());
//			channelDomain.update(channel);
			ActivityCommonResult<Integer> update = channelService.update(BeanMapper.map(channel, ChannelReqVo.class));
			if (update.isFailure()) {
				throw new RuntimeException(update.getResultCodeMsg());
			}
			return Result.success("修改成功");
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/channelManage/delChannel", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除渠道")
	public Result delChannel(Channel channel) {		
		try {
//			channelDomain.delete(channel.getId());
			ActivityCommonResult<Integer> delete = channelService.delete(channel.getId());
			if (delete.isFailure()) {
				throw new RuntimeException(delete.getResultCodeMsg());
			}
			return Result.success("删除成功");
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/channelManage/addChannelType", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增渠道类型")
	public Result addChannelType(ChannelType channelType,HttpServletRequest httpRequest) {		
		try {
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			channelType.setCreatedBy(staffSession.getId());
			channelType.setEditedBy(staffSession.getRealName());
//			channelTypeDomain.insert(channelType);
			ActivityCommonResult<Integer> insert = channelTypeService.insert(BeanMapper.map(channelType, ChannelTypeReqVo.class));
			if (insert.isFailure()) {
				throw new RuntimeException(insert.getResultCodeMsg());
			}
			return Result.success("保存成功");
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/channelManage/getAllChannelType", method = RequestMethod.POST)
	@ResponseBody
	public Result getChannelTypeList() {		
		try {
//			List<ChannelType> lc = channelTypeDomain.list(new ChannelType());
			ActivityCommonResult<List<ChannelTypeResVo>> list = channelTypeService.list(new ChannelTypeReqVo());
			List<ChannelTypeResVo> lc = list.getBusinessObject();
			return Result.success(lc);
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/template/addtemplate", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增活动模板")
	public Result addtemplate(Template template,HttpServletRequest httpRequest) {		
		try {
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			template.setCreatedBy(staffSession.getId());
			template.setEditedBy(staffSession.getRealName());
			template.setStatus("0");
//			templateDomain.insert(template);
			ActivityCommonResult<Integer> insert = templateService.insert(BeanMapper.map(template, TemplateReqVo.class));
			if (insert.isFailure()) {
				throw new RuntimeException(insert.getResultCodeMsg());
			}
			return Result.success("保存成功");
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/template/getAlltemplate", method = RequestMethod.POST)
	@ResponseBody
	public Result getAlltemplate(int pageNo, int pageSize) {		
		try {
//			PageInfo<Template> pt = templateDomain.list(new Template(),pageNo,pageSize);
			ActivityCommonResult<PageInfo<TemplateResVo>> list = templateService.list(new TemplateReqVo(),pageNo,pageSize);
			PageInfo<TemplateResVo> pt = list.getBusinessObject();
			return Result.success(pt);
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/template/getAlltemplateByType", method = RequestMethod.POST)
	@ResponseBody
	public Result getAlltemplateByType(String type) {		
		try {
			Template t = new Template();
			t.setType(type);
//			List<Template> pt = templateDomain.list(t);
			ActivityCommonResult<List<TemplateResVo>> list = templateService.list(BeanMapper.map(t, TemplateReqVo.class));
			List<TemplateResVo> pt = list.getBusinessObject();
			return Result.success(pt);
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/template/getTemplateById", method = RequestMethod.POST)
	@ResponseBody
	public Result getTemplateById(String id) {		
		try {
//			Template t = templateDomain.getById(id);
			ActivityCommonResult<TemplateResVo> result = templateService.getById(id);
			TemplateResVo t = result.getBusinessObject();
			return Result.success(t);
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/template/updateTemplate", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新活动模板")
	public Result updateTemplate(Template t,HttpServletRequest httpRequest) {		
		try {
			Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
			t.setEditedBy(staffSession.getRealName());
//			templateDomain.update(t);
			ActivityCommonResult<Integer> update = templateService.update(BeanMapper.map(t, TemplateReqVo.class));
			if (update.isFailure()) {
				throw new RuntimeException(update.getResultCodeMsg());
			}
			return Result.success("修改成功");
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
	@RequestMapping(value="/template/delTemplate", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除活动模板")
	public Result delTemplate(String id) {		
		try {
//			templateDomain.delete(id);
			ActivityCommonResult<Integer> delete = templateService.delete(id);
			if (delete.isFailure()) {
				throw new RuntimeException(delete.getResultCodeMsg());
			}
			return Result.success("删除成功");
		} catch (Exception e) {
			return Result.error("接口异常");
		}
	}
}


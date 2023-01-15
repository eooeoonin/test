package com.winsmoney.jajaying.boss.controller.tag;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.user.service.IGroupService;
import com.winsmoney.jajaying.user.service.request.GroupReqVo;
import com.winsmoney.jajaying.user.service.response.GroupResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
/**
 * TAG管理
 * @author xiaxingxing1
 *
 */
@Controller
@RequestMapping("/basedata_mgt/TAG")
public class TagController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(TagController.class);
	@Autowired
	private IGroupService groupService;
	
	/**
     * 群组列表
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
	@RequestMapping(value = "/listData", method = RequestMethod.POST)
    @ResponseBody
    public Result listData(String name,int pageNo, int pageSize) {
		if(null == name || name.equals("")){
			name = null;
		}
		PageInfo<GroupResVo> result  = null;
		try{
			logger.info("接口{}入参：" + JSONObject.toJSONString(name));
			result = groupService.list(name, pageNo, pageSize);
			return Result.success(result);
		}catch (Exception e) {
			logger.error("{TagController  listData  调用失败}", e);
			return Result.error("接口调用失败");
		}
    }
	
	/**
     * 创建群组
     *
     * @param groupReqVo
     * @return
     */
	@RequestMapping(value="TAGInsert",method = RequestMethod.POST)
	@ResponseBody
    @AduitLog(type = OperType.CREATE, content = "创建群组")
	public Result TAGInsert(GroupReqVo groupReqVo){
		Integer	insertCreate  = null;
		try {
			logger.info("接口{}入参：" + JSONObject.toJSONString(groupReqVo));
			if(null == groupReqVo.getContent() || "".equals(groupReqVo.getContent())) {
				groupReqVo.setContent("全部用户");
			}
			insertCreate = groupService.create(groupReqVo);
			return Result.success(insertCreate);
		} catch (Exception e) {
			logger.error("{TagController  TAGInsert  调用失败}", e);
			return Result.error("接口调用失败");
		}
	}
	/**
	 * 根据ID获取一条数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value="ById",method = RequestMethod.POST)
	@ResponseBody
	public Result ById(String id){
		GroupResVo	Gid  = null;
		try {
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			Gid = groupService.get(id);
			return Result.success(Gid);
		} catch (Exception e) {
			logger.error("{TagController  ById  调用失败}", e);
			return Result.error("接口调用失败");
		}
	}
	
	/**
	 * 更新TAG管理
	 * @param id
	 * @return
	 */
	@RequestMapping(value="edit",method = RequestMethod.POST)
	@ResponseBody
    @AduitLog(type = OperType.UPDATE, content = "更新群组")
	public Result TAGEdit(String id ,GroupReqVo groupReqVo){
		if(null == id || id.equals("")){
			id = null;
		}
		Integer	update  = null;
		try {
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			if(null == groupReqVo.getContent() || "".equals(groupReqVo.getContent())) {
				groupReqVo.setContent("全部用户");
			}
			update = groupService.update(id, groupReqVo);
			return Result.success(update);
		} catch (Exception e) {
			logger.error("{TagController  edit  调用失败}", e);
			return Result.error("接口调用失败");
		}
	}
	
	/**
	 * 根据ID删除一条记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete",method = RequestMethod.POST)
	@ResponseBody
    @AduitLog(type = OperType.DELETE, content = "删除群组")
	public Result delete(String id){
		if(null == id || id.equals("")){
			id = null;
		}
		Integer	delete  = null;
		try {
			logger.info("接口{}入参：" + JSONObject.toJSONString(id));
			delete = groupService.delete(id);
			return Result.success(delete);
		} catch (Exception e) {
			logger.error("{TagController  delete  调用失败}", e);
			return Result.error("接口调用失败");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

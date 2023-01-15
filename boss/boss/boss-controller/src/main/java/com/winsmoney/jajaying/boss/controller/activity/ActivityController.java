package com.winsmoney.jajaying.boss.controller.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.activity.service.IActivityRequestService;
import com.winsmoney.jajaying.activity.service.IActivityService;
import com.winsmoney.jajaying.activity.service.IAwardDetailLogService;
import com.winsmoney.jajaying.activity.service.IAwardDetailService;
import com.winsmoney.jajaying.activity.service.IAwardRuleService;
import com.winsmoney.jajaying.activity.service.IAwardSignRuleService;
import com.winsmoney.jajaying.activity.service.IChannelService;
import com.winsmoney.jajaying.activity.service.IChannelTypeService;
import com.winsmoney.jajaying.activity.service.ILinkService;
import com.winsmoney.jajaying.activity.service.ITemplateService;
import com.winsmoney.jajaying.activity.service.request.ActivityReqVo;
import com.winsmoney.jajaying.activity.service.request.AwardDetailLogReqVo;
import com.winsmoney.jajaying.activity.service.request.AwardDetailReqVo;
import com.winsmoney.jajaying.activity.service.request.AwardRuleReqVo;
import com.winsmoney.jajaying.activity.service.request.AwardSignRuleReqVo;
import com.winsmoney.jajaying.activity.service.request.LinkReqVo;
import com.winsmoney.jajaying.activity.service.response.ActivityCommonResult;
import com.winsmoney.jajaying.activity.service.response.ActivityResVo;
import com.winsmoney.jajaying.activity.service.response.AwardDetailResVo;
import com.winsmoney.jajaying.activity.service.response.AwardRuleResVo;
import com.winsmoney.jajaying.activity.service.response.AwardSignRuleResVo;
import com.winsmoney.jajaying.activity.service.response.ChannelResVo;
import com.winsmoney.jajaying.activity.service.response.LinkResVo;
import com.winsmoney.jajaying.activity.service.response.TemplateResVo;
import com.winsmoney.jajaying.award.service.IProjectService;
import com.winsmoney.jajaying.award.service.enums.ActionCode;
import com.winsmoney.jajaying.award.service.request.ProjectReqVo;
import com.winsmoney.jajaying.award.service.response.AwardCommonResult;
import com.winsmoney.jajaying.award.service.response.ProjectResVo;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.controller.BaseController;
import com.winsmoney.jajaying.boss.controller.enums.ActivityPlatformType;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.enums.AwardStatus;
import com.winsmoney.jajaying.boss.domain.model.Activity;
import com.winsmoney.jajaying.boss.domain.model.AwardDetail;
import com.winsmoney.jajaying.boss.domain.model.AwardDetailLog;
import com.winsmoney.jajaying.boss.domain.model.AwardRule;
import com.winsmoney.jajaying.boss.domain.model.Link;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.mq.BossMQSender;
import com.winsmoney.jajaying.boss.domain.mq.MsgMQTopic;
import com.winsmoney.jajaying.boss.domain.strategy.TemplateStrateay;
import com.winsmoney.jajaying.boss.domain.utils.PropertiesFactoryUtil;
import com.winsmoney.jajaying.boss.domain.utils.QRUtil;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.Money;
import com.winsmoney.platform.framework.redis.map.MapManager;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private IChannelService channelService;
    @Autowired
    private IChannelTypeService channelTypeService;
    @Autowired
    private ITemplateService templateService;
    @Autowired
    private IAwardSignRuleService awardSignRuleService;
    @Autowired
    private TemplateStrateay templateStrateay;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IAwardDetailService awardDetailService;
    @Autowired
    private IActivityRequestService activityRequestService;
    @Autowired
    private IAwardRuleService awardRuleService;
    @Autowired
    private ILinkService linkService;
    @Autowired
    private MapManager mapManager;
    @Autowired
    private IProjectService projectService;  //项目
    @Autowired
    private BossMQSender bossMQSender;
    @Autowired
    private IAwardDetailLogService awardDetailLogService;
    @Autowired
    private TransactionTemplate transactionTemplateMaster;

    @RequestMapping(value = "/activityManager/addActivity", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "新增活动")
    public Result addChannel(MultipartFile file, Activity activity, HttpServletRequest httpRequest) {
        try {
            String systemCode = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("systemCode");
            Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
            activity.setCreatedBy(staffSession.getId());
            activity.setEditedBy(staffSession.getRealName());
            activity.setStatus("0");
            activity.setStartTime( activity.getActivityStartTime() );
            activity.setEndTime( activity.getActivityEndTime() );
//            activityDomain.insert(activity);
            ActivityPlatformType platform = ActivityPlatformType.getType(httpRequest.getParameter("pcIsShow"));
            ActivityCommonResult<ActivityResVo> addActivity = activityService.insertActiveAndSaveIsShowPlatform(BeanMapper.map(activity, ActivityReqVo.class), platform.getDbCode());
//            ActivityCommonResult<ActivityResVo> addActivity = activityService.addActivity(BeanMapper.map(activity, ActivityReqVo.class));
            if (addActivity.isFailure()) {
            	return Result.error("接口异常");
            }
//            Activity reslutActivity = activityDomain.getById(activity.getId());
            ActivityResVo addActivityResult = addActivity.getBusinessObject();
            if (null == addActivityResult.getId()) {
            	return Result.error("接口异常");
            }
            ActivityCommonResult<ActivityResVo> activityResult = activityService.getById(addActivityResult.getId());
            ActivityResVo reslutActivity = activityResult.getBusinessObject();
            mapManager.set(systemCode + ":ACTIVITY:" + reslutActivity.getCode(), JSONObject.toJSONString(reslutActivity));
            int code = reslutActivity.getCode();
//            Template template = templateDomain.getById(activity.getTemplateId());
            ActivityCommonResult<TemplateResVo> result = templateService.getById(activity.getTemplateId());
            TemplateResVo template = result.getBusinessObject();
            templateStrateay.handlerTemplate(file, template.getFileName(), code, true);
            
            delActivityListToRedisByPlatform(systemCode, ActivityPlatformType.PC);
            
            return Result.success(reslutActivity);
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }

    @RequestMapping(value = "/activityManager/editActivity", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.UPDATE, content = "更新活动")
    public Result editActivity(MultipartFile file, Activity activity, HttpServletRequest httpRequest) {
        try {
            String systemCode = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("systemCode");
            Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
            activity.setEditedBy(staffSession.getRealName());
            activity.setStartTime( activity.getActivityStartTime() );
            activity.setEndTime( activity.getActivityEndTime() );
//            activityDomain.editActivity(activity);
//            activityService.editActivity(BeanMapper.map(activity, ActivityReqVo.class));
            ActivityPlatformType platformType = ActivityPlatformType.getType(httpRequest.getParameter("pcIsShow"));
            String platform = platformType == null ? null : platformType.getDbCode();
            activityService.updateAndSaveIsShowPlatform(BeanMapper.map(activity, ActivityReqVo.class), platform);
//            Activity reslutActivity = activityDomain.getById(activity.getId());
            ActivityCommonResult<ActivityResVo> activityResult = activityService.getById(activity.getId());
            ActivityResVo reslutActivity = activityResult.getBusinessObject();
            mapManager.set(systemCode + ":ACTIVITY:" + reslutActivity.getCode(), JSONObject.toJSONString(reslutActivity));
            int code = reslutActivity.getCode();
//            Template template = templateDomain.getById(activity.getTemplateId());
            ActivityCommonResult<TemplateResVo> result = templateService.getById(activity.getTemplateId());
            TemplateResVo template = result.getBusinessObject();
            if (!file.isEmpty()) {
            	templateStrateay.handlerTemplate(file, template.getFileName(), code, false);
            }
            
            delActivityListToRedisByPlatform(systemCode, ActivityPlatformType.PC);
            
            return Result.success(reslutActivity);
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }

    @RequestMapping(value = "/activityManager/updateStatusActivity", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.UPDATE, content = "更新活动状态")
    public Result updateStatusActivity(Activity activity, HttpServletRequest httpRequest) {
        try {
        	String systemCode = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("systemCode");
            Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
            activity.setEditedBy(staffSession.getRealName());
//            activityDomain.update(activity);
            ActivityCommonResult<Integer> updateResult = activityService.update(BeanMapper.map(activity, ActivityReqVo.class));
            if (updateResult.isFailure()) {
            	throw new RuntimeException(updateResult.getResultCodeMsg());
            }
//            Activity reslutActivity = activityDomain.getById(activity.getId());
            ActivityCommonResult<ActivityResVo> activityResult = activityService.getById(activity.getId());
            ActivityResVo reslutActivity = activityResult.getBusinessObject();
            mapManager.set(systemCode + ":ACTIVITY:" + reslutActivity.getCode(), JSONObject.toJSONString(reslutActivity));
           
            delActivityListToRedisByPlatform(systemCode, ActivityPlatformType.PC);
            
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }

    @RequestMapping(value = "/activityManager/delActivity", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.DELETE, content = "删除活动")
    public Result delActivity(String id) {
        try {
            String systemCode = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("systemCode");
//            Activity a = activityDomain.getById(id);
            ActivityCommonResult<ActivityResVo> activityResult = activityService.getById(id);
            ActivityResVo a = activityResult.getBusinessObject();
            mapManager.delete(systemCode + ":ACTIVITY:" + a.getCode());
//            activityDomain.delete(id);
//            ActivityCommonResult<Integer> result = activityService.delete(id);
            ActivityCommonResult<Integer> result = activityService.deleteAndResetSaveIsShowPlatform(id);
            if (result.isFailure()) {
            	throw new RuntimeException(result.getResultCodeMsg());
            }
//            linkDomain.deleteByAcode(a.getCode().toString());
            ActivityCommonResult<Integer> deleteByAcode = linkService.deleteByAcode(a.getCode().toString());
            if (deleteByAcode.isFailure()) {
            	throw new RuntimeException(deleteByAcode.getResultCodeMsg());
            }
            
            delActivityListToRedisByPlatform(systemCode, ActivityPlatformType.PC);
            
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }

	private void delActivityListToRedisByPlatform(String systemCode, ActivityPlatformType platformType) {
		mapManager.delete(systemCode + ":ACTIVITY:" + ActivityPlatformType.PC + "_platform_list");
	}
    
    @RequestMapping(value = "/activityManager/getAllLink", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllLink(Link link, int pageNo, int pageSize) {
        try {
        	if ("".equals(link.getChannelCode())) {
        		link.setChannelCode(null);
			}
//        	PageInfo<Link> pl = linkDomain.list(link, pageNo, pageSize);
        	ActivityCommonResult<PageInfo<LinkResVo>> list = linkService.list(BeanMapper.map(link, LinkReqVo.class), pageNo, pageSize);
            PageInfo<LinkResVo> pl = list.getBusinessObject();
            return Result.success(pl);
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }
    @RequestMapping(value = "/activityManager/getAllSignRule", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllSignRule(String id) {
        try {
        	AwardSignRuleReqVo a = new AwardSignRuleReqVo(); 
        	a.setActivityId(id);
        	ActivityCommonResult<List<AwardSignRuleResVo>> l = awardSignRuleService.list(a);
        	if(l.isSuccess()) {
        		return Result.success(l.getBusinessObject());
        	}else {
        		return Result.error("获取数据失败");
        	}
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }
    @RequestMapping(value = "/activityManager/addSignRule", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "新增签到规则")
    public Result addSignRule(AwardSignRuleReqVo awardSignRuleReqVo,HttpServletRequest httpRequest) {
        try {
        	 Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
        	 awardSignRuleReqVo.setEditedBy(staffSession.getRealName());
        	 ActivityCommonResult<Integer> l = awardSignRuleService.insert(awardSignRuleReqVo);
        	if(l.isSuccess()) {
        		return Result.success("success");
        	}else {
        		return Result.error("error");
        	}
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }
    @RequestMapping(value = "/activityManager/editSignRule", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.UPDATE, content = "更新签到规则")
    public Result editSignRule(AwardSignRuleReqVo awardSignRuleReqVo,HttpServletRequest httpRequest) {
        try {
        	Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
        	awardSignRuleReqVo.setEditedBy(staffSession.getRealName());
        	ActivityCommonResult<Integer> l = awardSignRuleService.update(awardSignRuleReqVo);
        	if(l.isSuccess()) {
        		return Result.success("success");
        	}else {
        		return Result.error("error");
        	}
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }
    @RequestMapping(value = "/activityManager/delSignRule", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.DELETE, content = "删除签到规则")
    public Result delSignRule(String id) {
        try {
        	ActivityCommonResult<Integer> l = awardSignRuleService.delete(id);
        	if(l.isSuccess()) {
        		return Result.success("success");
        	}else {
        		return Result.error("error");
        	}
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }
    @RequestMapping(value = "/activityManager/getBySignRuleId", method = RequestMethod.POST)
    @ResponseBody
    public Result getBySignRuleId(String id) {
        try {
        	ActivityCommonResult<AwardSignRuleResVo> l = awardSignRuleService.getById(id);
        	if(l.isSuccess()) {
        		return Result.success(l.getBusinessObject());
        	}else {
        		return Result.error("error");
        	}
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }
    @RequestMapping(value = "/activityManager/getAllProject", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllProject() {
        AwardCommonResult<PageInfo<ProjectResVo>> list = projectService.list(new ProjectReqVo(), 1, 1000);
        return Result.success(list.getBusinessObject().getList());
    }
    @RequestMapping(value = "/activityManager/delLink", method = RequestMethod.POST)
    @ResponseBody
    public Result delLink(String id) {
        try {
//        	linkDomain.delete(id);
            ActivityCommonResult<Integer> delete = linkService.delete(id);
            if (delete.isFailure()) {
            	throw new RuntimeException(delete.getResultCodeMsg());
            }
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }

    @RequestMapping(value = "/activityManager/getAllActivity", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllActivity(Activity activity, int pageNo, int pageSize) {
        try {
            if ("".equals(activity.getName())) {
                activity.setName(null);
            }
            List l = new ArrayList();
//            PageInfo<Activity> pa = activityDomain.list(activity, pageNo, pageSize);
            ActivityCommonResult<PageInfo<ActivityResVo>> list = activityService.list(BeanMapper.map(activity, ActivityReqVo.class), pageNo, pageSize);
            PageInfo<ActivityResVo> pa = list.getBusinessObject();
            for (ActivityResVo a : pa.getList()) {
//            	Template template = templateDomain.getById(a.getTemplateId());
                ActivityCommonResult<TemplateResVo> result = templateService.getById(a.getTemplateId());
                TemplateResVo template = result.getBusinessObject();
            	if (null != template) {
                    l.add(template);
                } else {
                    template.setName("无");
                    template.setType("无");
                    l.add(template);
                }
            }
            List result = new ArrayList();
            result.add(l);
            result.add(pa);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }

    @RequestMapping(value = "/activityManager/getActivityById", method = RequestMethod.POST)
    @ResponseBody
    public Result getActivityById(String id) {
        try {
//            Activity activity = new Activity();
            List l = new ArrayList();
//            activity = activityDomain.getById(id);
            ActivityCommonResult<ActivityResVo> activityResult = activityService.getById(id);
            ActivityResVo activity = activityResult.getBusinessObject();
            l.add(activity);
//            Template template = templateDomain.getById(activity.getTemplateId());
            ActivityCommonResult<TemplateResVo> result = templateService.getById(activity.getTemplateId());
            TemplateResVo template = result.getBusinessObject();
            if (null != template) {
                l.add(template);
            } else {
                template.setName("无");
                template.setType("无");
                l.add(template);
            }
            ActivityCommonResult<List<String>> showPlatformList = activityService.getShowPlatformList(id);
            List<String> platforms = showPlatformList.getBusinessObject();
        	HashMap<ActivityPlatformType, String> isPlatformShowMap = new HashMap<ActivityPlatformType, String>();
        	for (ActivityPlatformType obj : ActivityPlatformType.class.getEnumConstants()) {
    			if (platforms.contains(obj.getDbCode())) {
    				isPlatformShowMap.put(obj, "0");
    			} else {
    				isPlatformShowMap.put(obj, "1");
    			}
    		}
            l.add(isPlatformShowMap);
            return Result.success(l);
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }

    @RequestMapping(value = "/activityManage/createLink", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "新增活动链接")
    public Result createLink(String[] ids, String activityId, String linkUrl, HttpServletRequest httpRequest) {
        try {
            Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
//            Activity activity = activityDomain.getById(activityId);
            ActivityCommonResult<ActivityResVo> activityResult = activityService.getById(activityId);
            ActivityResVo activity = activityResult.getBusinessObject();
//            linkDomain.deleteByAcode(activity.getCode().toString());
            ActivityCommonResult<Integer> deleteByAcode = linkService.deleteByAcode(activity.getCode().toString());
            if (deleteByAcode.isFailure()) {
            	throw new RuntimeException(deleteByAcode.getResultCodeMsg());
            }
            for (String channelId : ids) {
                Link link = new Link();
//                Channel channel = channelDomain.getById(channelId);
                ActivityCommonResult<ChannelResVo> result = channelService.getById(channelId);
                ChannelResVo channel = result.getBusinessObject();
                String qrUrl = linkUrl + activity.getCode() + "/idx.html?cd=" + channel.getCode();
                link.setAActivityCode(activity.getCode().toString());
                link.setActivityName(activity.getName());
                link.setChannelCode(channel.getCode());
                link.setChannelName(channel.getName());
                link.setCreatedBy(staffSession.getId());
                link.setEditedBy(staffSession.getRealName());
                link.setLinkName(qrUrl);
//                linkDomain.insert(link);
                ActivityCommonResult<Integer> insert = linkService.insert(BeanMapper.map(link, LinkReqVo.class));
                if (insert.isFailure()) {
                	throw new RuntimeException(insert.getResultCodeMsg());
                }
                QRUtil.saveQRCode(qrUrl, activity.getCode().toString(), channel.getCode());
            }
            return Result.success(activity.getCode());
        } catch (Exception e) {
            return Result.error("接口异常");
        }
    }

    @RequestMapping(value = "/activityManager/template/download")
    @ResponseBody
    public ResponseEntity<byte[]> download(String templateId) {
        try {
//            Template template = templateDomain.getById(templateId);
            ActivityCommonResult<TemplateResVo> result = templateService.getById(templateId);
            TemplateResVo template = result.getBusinessObject();
            String path = template.getFileName();
            String marketRootPath = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("marketRootPath");
            String zipFileName = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("marketZipFileName");
            File file = new File(marketRootPath + File.separator + path + File.separator + zipFileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", zipFileName);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("下载文件异常");
            return null;
        }
    }

    @RequestMapping(value = "/activityManager/qrCode/download")
    @ResponseBody
    public ResponseEntity<byte[]> downloadQrCode(String[] ids) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "qrCode.zip");
        String marketRootPath = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("marketTargetPath");
        List<String> files = new ArrayList<String>();
        try {
            for (String id : ids) {
//            	Link link = linkDomain.getById(id);
            	ActivityCommonResult<LinkResVo> result = linkService.getById(id);
            	LinkResVo link = result.getBusinessObject();
                if (null != link && StringUtils.isNotBlank(link.getId())) {
                    String path = (marketRootPath + File.separator + link.getAActivityCode() + "/static/qrcode/" + link.getChannelCode() + ".png");
                    files.add(path);
                }
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(bos);
            for (String fileName : files) {
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(zipEntry);
                FileInputStream inputStream = new FileInputStream(fileName);
                IOUtils.copy(inputStream, zipOutputStream);
                inputStream.close();
            }
            zipOutputStream.close();
            return new ResponseEntity<byte[]>(bos.toByteArray(), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("下载文件异常");
            return null;
        }
    }


    @RequestMapping(value = "/activityManager/getAllRule", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllRule(AwardRule awardRule, int pageNo, int pageSize) {
        try {
            if ( StringUtils.isBlank( awardRule.getActivityId() )) {
                return Result.error( "活动不能为空" );
            }
//            Activity activity = activityDomain.getById( awardRule.getActivityId() );
            ActivityCommonResult<ActivityResVo> activityResult = activityService.getById(awardRule.getActivityId());
            ActivityResVo activity = activityResult.getBusinessObject();
            List result = new ArrayList();
//            PageInfo<AwardRule> pa = awardRuleDomain.list(awardRule, pageNo, pageSize);
            ActivityCommonResult<PageInfo<AwardRuleResVo>> list = awardRuleService.list(BeanMapper.map(awardRule, AwardRuleReqVo.class), pageNo, pageSize);
            PageInfo<AwardRuleResVo> pa = list.getBusinessObject();
            result.add( activity );
            result.add( pa );
            return Result.success(result);
        } catch (Exception e) {
            logger.error( " 异常 " , e );
            return Result.error("接口异常");
        }
    }

    @RequestMapping(value = "/activityManager/delRule", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.DELETE, content = "删除采集信息")
    public Result delRule(AwardRule awardRule) {
        try {
            if ( StringUtils.isBlank( awardRule.getId())) {
                return Result.error( "参数不能为空" );
            }
//            int result = awardRuleDomain.delete( awardRule.getId());
            ActivityCommonResult<Integer> delete = awardRuleService.delete( awardRule.getId());
            if (delete.isFailure()) {
            	throw new RuntimeException(delete.getResultCodeMsg());
            }
            int result = delete.getBusinessObject();
            return Result.success(result);
        } catch (Exception e) {
            logger.error( " 异常 " , e );
            return Result.error("接口异常");
        }
    }

    @RequestMapping(value = "/activityManager/saveRule", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "新增或更新采集信息")
    public Result saveRule(AwardRule awardRule) {
        try {
            if ( StringUtils.isBlank( awardRule.getId())) {
//            	awardRuleDomain.insert(awardRule);
                ActivityCommonResult<Integer> insert = awardRuleService.insert(BeanMapper.map(awardRule, AwardRuleReqVo.class));
                if (insert.isFailure()) {
                	throw new RuntimeException(insert.getResultCodeMsg());
                }
            }else{
//            	awardRuleDomain.update( awardRule );
                ActivityCommonResult<Integer> update = awardRuleService.update(BeanMapper.map(awardRule, AwardRuleReqVo.class));
                if (update.isFailure()) {
                	throw new RuntimeException(update.getResultCodeMsg());
                }
            }
            return Result.success( awardRule );
        } catch (Exception e) {
            logger.error( " 异常 " , e );
            return Result.error("接口异常");
        }
    }@RequestMapping(value = "/activityManager/activityStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Result activityStatistics(String activityId) {
        try {
            if ( StringUtils.isBlank( activityId )) {
                logger.info( " 活动编号不能为空 " );
                return Result.error("活动编号不能为空");
            }else{
//                Activity activity = activityDomain.getById( activityId );
                ActivityCommonResult<ActivityResVo> activityResult = activityService.getById(activityId);
                ActivityResVo activity = activityResult.getBusinessObject();
//                Integer personTime = awardDetailDomain.getCount( String.valueOf(  activity.getCode()  )); //活动人数
                Integer personTime = awardDetailService.getCount( String.valueOf(  activity.getCode()  )).getBusinessObject(); //活动人数
//                Money titleAmount = activityRequestDomain.getSum(  String.valueOf(  activity.getCode()  ) );
                ActivityCommonResult<Money> sum = activityRequestService.getSum(  String.valueOf(  activity.getCode()  ) );
                Money titleAmount = sum.getBusinessObject();
                Map<String,Object> map = new HashMap<>();
                map.put( "personTime" , personTime );
                map.put( "titleAmount" , titleAmount );
                return Result.success( map );
            }
        } catch (Exception e) {
            logger.error( " 异常 " , e );
            return Result.error("接口异常");
        }
    }

    @RequestMapping(value = "/activityManager/award", method = RequestMethod.POST)
    @ResponseBody
    @AduitLog(type = OperType.CREATE, content = "新增或更新活动规则")
    public Result award(AwardRule awardRule) {
        final  AwardRule rule = awardRule ;
       return transactionTemplateMaster.execute(new TransactionCallback<Result>() {
            @Override
            public Result doInTransaction(TransactionStatus transactionStatus) {
                try {
                    if ( StringUtils.isBlank( rule.getId())) {
//                    	awardRuleDomain.insert(rule);
                        ActivityCommonResult<Integer> insert = awardRuleService.insert(BeanMapper.map(rule, AwardRuleReqVo.class));
                        if (insert.isFailure()) {
                        	throw new RuntimeException(insert.getResultCodeMsg());
                        }
                    }else{
//                        awardRuleDomain.update( rule ); //中奖规则
                    	ActivityCommonResult<Integer> update = awardRuleService.update(BeanMapper.map(rule, AwardRuleReqVo.class));
                    	if (update.isFailure()) {
                    		throw new RuntimeException(update.getResultCodeMsg());
                    	}
                    }
                    Map<String,Object> map= new HashMap<String,Object>();
//                    Activity activity = activityDomain.getById( rule.getActivityId() );
                    ActivityCommonResult<ActivityResVo> activityResult = activityService.getById(rule.getActivityId());
                    ActivityResVo activity = activityResult.getBusinessObject();

//                    List<AwardDetail> list = new ArrayList<>();
                    List<AwardDetailResVo> list = new ArrayList<>();
                    if( ! rule.getSunShines() ) { // 是否阳光普照 筛选不同中奖号
                        AwardDetail awardDetail = new AwardDetail();
                        awardDetail.setActivityId(  rule.getActivityId() );
                        awardDetail.setCode( rule.getStandardAmount() ); //中奖号码
//                        list = awardDetailDomain.list(awardDetail); //中奖号码查询
                        awardDetailService.list(BeanMapper.map(awardDetail, AwardDetailReqVo.class)); //中奖号码查询
//                        list = 
                    }else{
                        AwardDetail awardDetail = new AwardDetail();
                        awardDetail.setActivityId(  rule.getActivityId() );
                        awardDetail.setAwardStatus( AwardStatus.USE.name() );
//                        list = awardDetailDomain.getSunShines(awardDetail); //中奖号码查询
                        ActivityCommonResult<List<AwardDetailResVo>> result = awardDetailService.getSunShines(BeanMapper.map(awardDetail, AwardDetailReqVo.class)); //中奖号码查询
                        list = result.getBusinessObject();
                    }
                    for( AwardDetailResVo detail : list ){
                        if( StringUtils.isNotBlank( detail.getUserId())) {
                            AwardDetailLog awardDetailLog = new AwardDetailLog();
                            awardDetailLog.setUserId(detail.getUserId());
                            awardDetailLog.setActivityId(activity.getId());
                            awardDetailLog.setActivityCode(detail.getCode());
                            awardDetailLog.setAwardCode(rule.getStandardAmount());
                            awardDetailLog.setProjectId(activity.getRule1());
                            awardDetailLog.setAwardLevel(rule.getName()); //中奖名称
                            detail.setAwardStatus(AwardStatus.AWARD.name()); //以奖励
//                            awardDetailLogDomain.insert(awardDetailLog); //记录奖励日志
                            ActivityCommonResult<Integer> insert = awardDetailLogService.insert(BeanMapper.map(awardDetailLog, AwardDetailLogReqVo.class)); //记录奖励日志
                            if (insert.isFailure()) {
                            	throw new RuntimeException(insert.getResultCodeMsg());
                            }
                            map.put("userId", awardDetailLog.getUserId()); //用户单号
                            map.put("requestId", awardDetailLog.getId()); //请求单号
                            map.put("projectId", activity.getRule1()); //奖励项目编号
                            map.put("actCode", ActionCode.WINNING.getCode()); //中奖行为
                            map.put("TRANSVALUE", rule.getStandardAmount()); //中奖行为值
                            boolean result = bossMQSender.sendMQ(MsgMQTopic.AWARD_REQUEST, map);
                            if (!result) {
                                return Result.error("下发奖品MQ异常");
                            } else {
//                            	awardDetailDomain.update(detail); //更新状态以奖励
                                ActivityCommonResult<Integer> update = awardDetailService.update(BeanMapper.map(detail, AwardDetailReqVo.class)); //更新状态以奖励
                                if (update.isFailure()) {
                                	throw new RuntimeException(update.getResultCodeMsg());
                                }
                            }
                        }
                    }
                    return Result.success( rule );
                }catch (RuntimeException e){
                    logger.error( " 异常 " , e );
                    transactionStatus.setRollbackOnly();
                    return Result.error("接口异常");
                }catch (Exception e) {
                    logger.error( " 异常 " , e );
                    transactionStatus.setRollbackOnly();
                    return Result.error("接口异常");
                }
            }
        });

    }
}

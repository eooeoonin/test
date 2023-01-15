package com.winsmoney.jajaying.boss.controller.reward;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.controller.utils.BeanMapper;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.*;
import com.winsmoney.jajaying.boss.domain.model.AwardListLog;
import com.winsmoney.jajaying.boss.domain.model.GrantAwardLog;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.framework.pagehelper.StringUtil;
import com.winsmoney.jajaying.award.service.IAwardExecService;
import com.winsmoney.jajaying.award.service.IAwardPoolService;
import com.winsmoney.jajaying.award.service.IProjectService;
import com.winsmoney.jajaying.award.service.request.AwardGrantReqVo;
import com.winsmoney.jajaying.award.service.request.AwardGrantsReqVo;
import com.winsmoney.jajaying.award.service.request.AwardPoolReqVo;
import com.winsmoney.jajaying.award.service.request.GrantReqVo;
import com.winsmoney.jajaying.award.service.request.ProjectReqVo;
import com.winsmoney.jajaying.award.service.response.AwardCommonResult;
import com.winsmoney.jajaying.award.service.response.AwardGrantResVo;
import com.winsmoney.jajaying.award.service.response.AwardPoolResVo;
import com.winsmoney.jajaying.award.service.response.GrantResVo;
import com.winsmoney.jajaying.award.service.response.ProjectResVo;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;

/**
 * 项目管理
 */
@Controller
@RequestMapping("/reward/grant_reward")
public class GrantRewardController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(GrantRewardController.class);
	@Autowired
	private IProjectService projectService;  //项目 
    @Autowired
    private IAwardPoolService awardPoolService;  //奖池列表
    @Autowired
    private IAwardExecService awardExecService;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Autowired
    private GrantAwardLogDomain GrantAwardLogDomain;
    @Autowired
    private AwardListLogDomain awardListLogDomain;
    @RequestMapping("/getAllProject")
    @ResponseBody
    public Result list() {
    	try {
    		ProjectReqVo projectReqVo = new ProjectReqVo();
    		AwardCommonResult<PageInfo<ProjectResVo>> list = projectService.list(projectReqVo, 1, 1000);
    		if(list.isSuccess()) {
    			return Result.success(list.getBusinessObject().getList());
    		}else{
    			return Result.error("接口调用异常");
    		}
    	}catch (Exception e) {
    		logger.error("GrantRewardController  list >>>>>>>>>>>>>>>> ",e);
    		return Result.error("接口调用异常");
		}
    }
    @RequestMapping("/getAllJackpot")
    @ResponseBody
    public Result getAllJackpot(String id) {
    	try {
    		AwardPoolReqVo awardPoolReqVo = new AwardPoolReqVo();
    		if(StringUtil.isEmpty(id)) {
    			return Result.error("参数错误");
    		}
    		awardPoolReqVo.setProjectId(id);
    		AwardCommonResult<PageInfo<AwardPoolResVo>> list = awardPoolService.list(awardPoolReqVo, 1, 1000);
    		if(list.isSuccess()) {
    			return Result.success(list.getBusinessObject().getList());
    		}else{
    			return Result.error("接口调用异常");
    		}
    	}catch (Exception e) {
    		logger.error("GrantRewardController  getAllJackpot >>>>>>>>>>>>>>>> ",e);
    		return Result.error("接口调用异常");
		}
    }
    @RequestMapping("/grantsAward")
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "新增奖励")
    public Result grantsAward(String awardProject,String grantType,String[] userIds,String projectId,String[] jackpotId,Long[] jackpotnum,String TAG) {
		try {
			if(projectId == null || projectId.equals("0") || projectId.equals("")) {
				return Result.error("无效的项目");
			}
			for(int i = 0; i < jackpotId.length ; i++) {
				if(jackpotId[i] == null || jackpotId[i].toString().equals("0") || jackpotId[i].toString().equals("")) {
					return Result.error("无效的奖池");
				}
			}
			if(StringUtil.isEmpty(TAG)) {
				HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();    
				Staff staffSession = (Staff) request.getSession().getAttribute("adminInfo");
				String staffName = null;
				if(staffSession != null) {
					staffName  = staffSession.getStaffName();
				}else {
					logger.info("grantsAward  ======== > username :" + staffName);
					return Result.error("当前登录用户信息异常");
				}
	    		AwardListLog all = new AwardListLog();
	    		all.setAwardAmount(userIds.length);
	    		all.setAwardProjectId(projectId);
	    		all.setCreatedBy(staffName);
	    		all.setGrantType(grantType);
	    		all.setAwardProject(awardProject);
	    		awardListLogDomain.insert(all);
				AwardGrantsReqVo awardGrantsReqVo = new AwardGrantsReqVo();
		    	List<AwardGrantReqVo> lage = new ArrayList<AwardGrantReqVo>();
		    	List<GrantAwardLog> lgal = new ArrayList<GrantAwardLog>();
		    	for(int j = 0; j < userIds.length ; j++) {
		    		if(userIds[j] != null && !userIds[j].toString().equals("")) {
		    			List<GrantReqVo> lgr = new ArrayList<GrantReqVo>();
		    			for(int i = 0; i < jackpotId.length ; i++) {
		    				GrantReqVo grantReqVo = new GrantReqVo();
		    				String id = sequenceGenerator.getUUID();
			    			grantReqVo.setAwardGrantId(id);
			    			grantReqVo.setProjectId(projectId);
			    			grantReqVo.setPoolId(jackpotId[i]);
			    			grantReqVo.setGrantNum(jackpotnum[i]);
			    			lgr.add(grantReqVo);
				    		GrantAwardLog g = new GrantAwardLog();
				    		g.setAwardPoolId(jackpotId[i]);
				    		g.setAwardPoolNum(jackpotnum[i].intValue());
				    		g.setId(id);
				    		g.setProjectId(projectId);
				    		g.setUserId(userIds[j]);
				    		g.setStatus("0");
				    		g.setCreatedBy(staffName);
				    		lgal.add(g);
		    			}
		    			AwardGrantReqVo awardGrantReqVo = new AwardGrantReqVo();
		    			awardGrantReqVo.setUserId(userIds[j]);
		    			awardGrantReqVo.setGrants(lgr);
		    			lage.add(awardGrantReqVo);
		    		}
		    	}
		    	GrantAwardLogDomain.insertList(lgal);
		    	awardGrantsReqVo.setGrants(lage);
		    	AwardCommonResult<List<AwardGrantResVo>> result = awardExecService.grant(awardGrantsReqVo);
		    	for(AwardGrantResVo agrv : result.getBusinessObject()) {
		    		String re = null;
		    		if(agrv.getResult()) {
		    			re = "1";
		    		}else {
		    			re = "2";
		    		}
		    		for(GrantResVo grv : agrv.getGrants()) {
		    			GrantAwardLog g = new GrantAwardLog(); 
		    			String id = grv.getAwardGrantId();
		    			g.setId(id);
		    			g.setStatus(re);
		    			GrantAwardLogDomain.update(g);
		    		}
		    	}
		    	return Result.success("发放成功");
			}else {
				return Result.success("暂时不支持TAG");
			}
			
		}catch (Exception e) {
			logger.error("grantsAward------------------>", e);
			return Result.error("系统异常");
		}
    }
}

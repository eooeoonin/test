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
@RequestMapping("/reward/grant_detail")
public class GrantDetailController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(GrantDetailController.class);
    @Autowired
    private IAwardPoolService awardPoolService;  //奖池列表
    @Autowired
    private IAwardExecService awardExecService;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Autowired
    private GrantAwardLogDomain GrantAwardLogDomain;
    @RequestMapping("/getAllData")
    @ResponseBody
    public Result list(GrantAwardLog grantAwardLog,int pageNo,int pageSize) {
    	try {
    		PageInfo<GrantAwardLog> result = GrantAwardLogDomain.list(grantAwardLog, pageNo, pageSize);
    		return Result.success(result);
    	}catch (Exception e) {
    		logger.error("GrantDetailController  list >>>>>>>>>>>>>>>> ",e);
    		return Result.error("接口调用异常");
		}
    }
    @RequestMapping("/regrant")
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "发放奖励")
    public Result regrant(GrantAwardLog grantAwardLog) {
		try {
			AwardGrantsReqVo awardGrantsReqVo = new AwardGrantsReqVo();
	    	List<GrantReqVo> lgr = new ArrayList<GrantReqVo>();
	    	List<AwardGrantReqVo> lage = new ArrayList<AwardGrantReqVo>();
	    	GrantReqVo grv = new GrantReqVo();
	    	grv.setAwardGrantId(grantAwardLog.getId());
	    	grv.setGrantNum(new Long((long)grantAwardLog.getAwardPoolNum()));
	    	grv.setPoolId(grantAwardLog.getAwardPoolId());
	    	grv.setProjectId(grantAwardLog.getProjectId());
	    	lgr.add(grv);
	        AwardGrantReqVo awardGrantReqVo = new AwardGrantReqVo();
	        awardGrantReqVo.setUserId(grantAwardLog.getUserId());
	        awardGrantReqVo.setGrants(lgr);
	        lage.add(awardGrantReqVo);
	        awardGrantsReqVo.setGrants(lage);
	    	AwardCommonResult<List<AwardGrantResVo>> result = awardExecService.grant(awardGrantsReqVo);
	    	for(AwardGrantResVo agrv : result.getBusinessObject()) {
	    		String re = null;
	    		if(agrv.getResult()) {
	    			re = "1";
	    		}else {
	    			return Result.error("发放失败");
	    		}
	    		for(GrantResVo grv1 : agrv.getGrants()) {
	    			GrantAwardLog g = new GrantAwardLog(); 
	    			String id = grv1.getAwardGrantId();
	    			g.setId(id);
	    			g.setStatus(re);
	    			GrantAwardLogDomain.update(g);
	    		}
	    	}
	    	return Result.success("发放成功");
		}catch (Exception e) {
			logger.info("regrant------------------>",e);
			return Result.error("接口异常");
		}
    }
}

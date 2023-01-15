package com.winsmoney.jajaying.boss.controller.basedata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.basedata.service.IBannerService;
import com.winsmoney.jajaying.basedata.service.ISystemConfigService;
import com.winsmoney.jajaying.basedata.service.request.BannerReqVo;
import com.winsmoney.jajaying.basedata.service.response.BannerResVo;
import com.winsmoney.jajaying.basedata.service.response.BasedataCommonResult;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.model.Staff;
import com.winsmoney.jajaying.boss.domain.utils.BannerResForm;
import com.winsmoney.jajaying.boss.domain.utils.EntityObject;
import com.winsmoney.jajaying.boss.domain.utils.PropertiesFactoryUtil;
import com.winsmoney.jajaying.boss.domain.utils.VelocityGenerator;
import com.winsmoney.jajaying.boss.domain.utils.HtmlParser;
import com.winsmoney.jajaying.trade.service.request.LoanReqVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;

/**
 * 热点新闻
 *
 */
@Controller
@RequestMapping("/web_mgt/w_app_news_list_123")
public class NewsController {

	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(NewsController.class);

	@Autowired
	private IBannerService bannerService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ISystemConfigService iSystemConfigService;

	@RequestMapping(value = "/newsList", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<BannerResVo> newsList(BannerReqVo bannerReqVo, String pageNo, String pageSize) {
		bannerReqVo.setType("6");
		try {
			BasedataCommonResult<PageInfo<BannerResVo>> BannerResVoList = bannerService.list(bannerReqVo, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			return BannerResVoList.getBusinessObject();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/newsDelete", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除热点新闻")
	public Integer newsDelete(String id) {
		BasedataCommonResult<Integer> BannerResVoList = bannerService.delete(id);
		return BannerResVoList.getBusinessObject();

	}

	@RequestMapping(value = "/getNewsById", method = RequestMethod.POST)
	@ResponseBody
	public BannerResForm getNewsById(BannerReqVo bannerReqVo, String bizeCode) {
		BasedataCommonResult<BannerResVo> basedataCommonResult = bannerService.get(bannerReqVo);
		BannerResForm bannerResForm = BeanMapper.map(basedataCommonResult.getBusinessObject(), BannerResForm.class);
		String fileName = bannerReqVo.getId() + ".html";
		String value = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty(bizeCode.split("/")[1]);
		String LocalhostUrl = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("rootPath");
		String filePath = LocalhostUrl + value;
		HtmlParser htmlParser = new HtmlParser();
		String content = htmlParser.parseHtml(filePath+"/a_" +fileName);
		/*HtmlParser htmlParser = new HtmlParser();
		String content = htmlParser.parseHtmlUrl(newsUrl);*/
		logger.info("新闻内容：{}",content);
		bannerResForm.setContent(content);
		return bannerResForm;
	}

	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新热点新闻")
	public Map<String, String> publish(BannerReqVo bannerReqVo, String bizCode, String content, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();
		bannerReqVo.setEditedBy(role);
		bannerReqVo.setType("6");
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			BasedataCommonResult<Integer> result = bannerService.update(bannerReqVo);

			String fileName = bannerReqVo.getId() + ".html";
			String value = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty(bizCode.split("/")[1]);
			String LocalhostUrl = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("rootPath");
			String filePath = LocalhostUrl + value;
			VelocityGenerator velocityGenerator = new VelocityGenerator();
			velocityGenerator.init();
			EntityObject entityObject = new EntityObject();
			entityObject.setBody(content);
			entityObject.setTitle(bannerReqVo.getTitile());

			velocityGenerator.buildFileByTemplate(entityObject, fileName, filePath);
			if (result.isSuccess()) {
				resultMap.put("result", "success");
			}

			else
				resultMap.put("result", result.getResultCodeMsg());
		} catch (IOException e) {
			logger.error("编辑新闻异常：" + e.getMessage(), e);
			resultMap.put("result", e.getMessage());
		}
		return resultMap;

	}

	@RequestMapping(value = "/addPublish", method = RequestMethod.POST)
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加热点新闻")
	public Map<String, Object> addPublish(BannerReqVo bannerReqVo, String bizeCode, String content, HttpServletRequest httpRequest) {
		Staff staffSession = (Staff) httpRequest.getSession().getAttribute("adminInfo");
		String role = staffSession.getStaffName();

		bannerReqVo.setEditedBy(role);

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			bannerReqVo.setType("6");
			bannerReqVo.setAvailable(1);
			bannerReqVo.setIsShow(1);
			
			BasedataCommonResult<BannerResVo> resultBannerResVo = bannerService.insert(bannerReqVo);

			String fileName = resultBannerResVo.getBusinessObject().getId() + ".html";
			String value = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty(bizeCode.split("/")[1]);
			String LocalhostUrl = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("rootPath");
			String filePath = LocalhostUrl + value;				
			VelocityGenerator velocityGenerator = new VelocityGenerator();
			velocityGenerator.init();
			EntityObject entityObject = new EntityObject();
			entityObject.setBody(content);
			entityObject.setTitle(bannerReqVo.getTitile());

			velocityGenerator.buildFileByTemplate(entityObject, fileName, filePath);

			if (resultBannerResVo.isSuccess()) {
				result.put("result", "success");
				result.put("id", resultBannerResVo.getBusinessObject().getId());
			}

			else
				result.put("result", resultBannerResVo.getResultCodeMsg());
		} catch (IOException e) {
			logger.error("新闻发布异常：" + e.getMessage(), e);
			result.put("result", e.getMessage());
		}

		return result;
	}

}
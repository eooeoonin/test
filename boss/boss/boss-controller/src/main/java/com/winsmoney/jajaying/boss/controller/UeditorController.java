package com.winsmoney.jajaying.boss.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winsmoney.jajaying.boss.baidu.ueditor.MyActionEnter;
import com.winsmoney.jajaying.boss.domain.utils.PropertiesFactoryUtil;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;


@Controller
@RequestMapping("/ueditor")
public class UeditorController {
	private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(UeditorController.class);
	@RequestMapping("/dispather")
	public void config(HttpServletRequest request, HttpServletResponse response) {
//		ProductContext productContext = ProductContextHolder.getProductContext();
//		String url = productContext.getUrl();
//		String rootPath = request.getRealPath("/");
		try {
			String rootPath=PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("rootPath");
			response.setHeader("Content-Type", "text/html");
			String exec = new MyActionEnter(request, request.getInputStream(), rootPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("ueditor上传图片失败", e);
		}

	}

}

package com.winsmoney.jajaying.boss.domain.utils;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;

/**
* Created by sqq on 2016/11/21.
*/
public class HtmlParser {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(VelocityGenerator.class);
   
	public String parseHtml(String path){
		String result = "";
		File input = new File(path);
		try {
			Document doc = Jsoup.parse(input, "UTF-8");
			Element serviceTxt = doc.select(".tpl_body").first();
			result = serviceTxt.html();
		} catch (IOException e) {
			logger.error("文件读取失败", e);
		}
		return result;
	}
	
	public String parseHtmlUrl(String url) {
		String result = "";
		try {
			Document doc = Jsoup.connect(url).get();
			Element serviceTxt = doc.select("div.service_txt").first();
			logger.info("serviceTxt：{}",serviceTxt.toString());
			result = serviceTxt.html();
		} catch (IOException e) {
			logger.error("文件读取失败", e);
		}
		return result;
	}
	
	public static void main(String args[]) {
		HtmlParser HtmlParser = new HtmlParser();
		HtmlParser.parseHtmlUrl("https://dev-static.jajaying.com/html/a_20161123P05300000000000000000006.html");
	}
	
}
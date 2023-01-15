package com.winsmoney.jajaying.boss.controller.basedata;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.winsmoney.jajaying.basedata.service.ISystemConfigService;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.PropertiesFactoryUtil;

/**
 * 
 * 图片上传
 * 
 * @author Moon
 * 
 */
@Controller
@RequestMapping("/boss")
public class UploadController {

	@Autowired
	private ISystemConfigService iSystemConfigService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

	@RequestMapping("/imageUpload")
	@ResponseBody
	@AduitLog(type = OperType.CREATE, content = "图片上传")
	public String imageUpload(String bizeCode, MultipartFile file) {
		Map<String, String> map = new HashMap<>();
		if (file == null) {
			LOGGER.error("文件名为空");
			map.put("fileName", "");
			return JSON.toJSONString(map);
		}
		String UrlName = saveFile(bizeCode, file);
		map.put("fileName", UrlName);
		return JSON.toJSONString(map);
	}

	private String saveFile(String bizeCode, MultipartFile file) {
		String nginxMapFlag = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty(bizeCode);
		String rootPath = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("rootPath");

		String originalName = null;
		String startDatePath = null;
		try {
			if (!file.isEmpty()) {
				originalName = file.getOriginalFilename();// 获得原文件名+扩展名
			}
			String uuid = UUID.randomUUID().toString();
			String dateName = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
			if (null != originalName && !originalName.isEmpty()) {
				int index = originalName.lastIndexOf(".");
				String extendName = originalName.substring(index);// 获取后缀名
				startDatePath = dateName + uuid + extendName;
				String originFullPath = rootPath + nginxMapFlag +startDatePath;
				File originFile = new File(originFullPath);
				originFile.getParentFile().mkdirs();// 创建目录
				file.transferTo(new File(originFullPath));
			}
		} catch (Exception e) {
			LOGGER.error("上传失败", e);

		}
		return startDatePath;
	}

}

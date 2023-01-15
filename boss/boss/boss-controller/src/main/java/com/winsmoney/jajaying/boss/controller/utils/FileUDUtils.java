/**
* Project Name:mgt
* File Name:FileUtils.java
* Package Name:com.winsmoney.jajaying.mgt.utils
* Date:2016年4月14日下午7:47:07
* Copyright (c) 2016, wangbinlei@jajaying.com All Rights Reserved.
*/

package com.winsmoney.jajaying.boss.controller.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

/**
* Description: TODO ADD Description. <br/>
* Date: 2016年4月14日 下午7:47:07 <br/>
* @author wangbinlei
* @since JDK 1.7
*/
public class FileUDUtils {

	
	/**
	* uploadFile:上传文件. <br/>
	* Date: 2016年4月14日 下午7:48:07 <br/>
	* @author wangbinlei
	* @param descPath
	* @return
	*/
	public static void uploadFile(String filePath,MultipartFile file){
		// 文件保存路径  
		try {  
			File saveFile = new File(filePath);
			if(!saveFile.exists()){
				saveFile.getParentFile().mkdirs();
			}
            // 转存文件  
            file.transferTo(saveFile);  
        } catch (Exception e) {  
            e.printStackTrace();
            
        }
		
	}
	
	
	/**
	* createFilePath:文件名称生成. <br/>
	* Date: 2016年4月14日 下午7:49:26 <br/>
	* @author wangbinlei
	* @return
	*/
	private static String createFilePath(String fileType){
		String fileName = UUID.randomUUID().toString();
		fileName = fileName+"."+fileType;
		return fileName;
	}
}

    
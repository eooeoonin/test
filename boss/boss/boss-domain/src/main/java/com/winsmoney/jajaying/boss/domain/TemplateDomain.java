/**
 * Project:boss
 * File:Template.java
 * Date:2017-08-31
 * Copyright (c) 2017 jajaying.com All Rights Reserved.
 */
package com.winsmoney.jajaying.boss.domain;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.domain.manager.TemplateManager;
import com.winsmoney.jajaying.boss.domain.model.Template;
import com.winsmoney.jajaying.boss.domain.utils.PropertiesFactoryUtil;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.redis.api.LockManager;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.locks.Lock;


/**
 * ClassName: template
 * Description: 模板表 实现
 * date: 2017-08-31 05:16:26
 *
 * @author: CodeCreator
 */
@Service
public class TemplateDomain {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(TemplateDomain.class);

    @Autowired
    private TemplateManager templateManager;
    @Resource(name = "lockManager")
    LockManager lockManager;

    //通用//////////////////////////////////

    /**
     * 根据id取得记录
     */
    public Template getById(String id) {
        Template r = templateManager.getById(id);
        return r;
    }

    /**
     * 取得记录
     */
    public Template get(Template condition) {
        Template result = templateManager.get(condition);
        return result;
    }

    /**
     * 统计
     */
    public int count(Template condition) {
        int result = templateManager.count(condition);
        return result;
    }

    /**
     * 列表
     */
    public List<Template> list(Template condition) {
        List<Template> result = templateManager.list(condition);
        return result;
    }

    /**
     * 分页列表
     */
    public PageInfo<Template> list(Template condition, int pageNo, int pageSize) {
        PageInfo<Template> result = templateManager.list(condition, pageNo, pageSize);
        return result;
    }

    /**
     * 插入
     */
    public int insert(Template value) {
        int result = templateManager.insert(value);
        return result;
    }

    /**
     * 更新
     */
    public int update(Template value) {
        int result = templateManager.update(value);
        return result;
    }

    /**
     * 删除
     */
    public int delete(String id) {
        int result = templateManager.delete(id);
        return result;
    }


    /**
     * 模板文件替换，一共3步
     * 1. 将原所模板文件(根据库中配置的模板名称查找)拷贝到 /var/mkt_tpl/活动文件夹如000001/ 下
     * 2. 将上传的img.zip拷贝到/var/mkt_tpl/活动文件夹如000001/static 下
     * 3. 将img.zip解压到/var/mkt_tpl/活动文件夹如000001/static 下，
     */
    public void handlerTemplate(MultipartFile file, String templateName, int code, boolean isAdd) {
        String marketRootPath = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("marketRootPath");
        String marketTargetPath = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("marketTargetPath");
        String zipFileName = PropertiesFactoryUtil.getProperties("META-INF/config.properties").getProperty("marketZipFileName");
        Lock lock = lockManager.createLock("L02_TEMPLATE_COPY");
        try {
            boolean lockFirst = lock.tryLock();
            if(lockFirst){
                validateZipName(file, templateName, String.valueOf(code), zipFileName);
                String originPath = marketRootPath + File.separator + templateName; // /var/mkt_dist/package_tpl
                String targetPath = marketTargetPath + File.separator + code + File.separator; // /var/mkt_tpl/123/
                if(isAdd){
                    //如果是新增，则拷贝原文件
                    copyOriginTemplate(originPath, targetPath);
                }
                //zip文件所在的目录
                String zipFilePath = targetPath + "static";
                String zipFilePathWithName = zipFilePath + File.separator + zipFileName;
                copyZipFile(file, zipFilePathWithName);
                unZip(zipFilePathWithName, zipFilePath);
            }
        } catch (IOException e) {
            logger.error("模板替换出错", e);
        }finally {
            lock.unlock();
        }
    }

    private void validateZipName(MultipartFile file, String templateName, String code, String zipFileName) {
        String originalName = file.getOriginalFilename();
        if (!zipFileName.equalsIgnoreCase(originalName)) {
            throw new RuntimeException("只能上传img.zip名称的文件");
        }
        if (StringUtils.isBlank(templateName)) {
            throw new RuntimeException("模板名称为空");
        }
        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("活动号为空");
        }
    }

    private void copyOriginTemplate(String originPath, String targetPath) throws IOException {
        File originFile = new File(originPath);
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            targetFile.mkdir();
        }
        FileUtils.copyDirectory(originFile, targetFile);
    }

    private void copyZipFile(MultipartFile file, String targetPath) throws IOException {
        File uploadZipFile = new File(targetPath);
        File parentFile = uploadZipFile.getParentFile();
        if (null != parentFile && !parentFile.exists()) {
            parentFile.mkdir();
        }
        file.transferTo(uploadZipFile);
    }

    private void unZip(String zipFilePathWithName, String targetDir) throws IOException {
        File zipfile = new File(zipFilePathWithName);
        if (zipfile.exists()) {
            targetDir = targetDir + File.separator;
            FileUtils.forceMkdir(new File(targetDir));
            try (ZipFile zf = new ZipFile(zipfile, "GBK")) {
                Enumeration zipArchiveEntrys = zf.getEntries();
                while (zipArchiveEntrys.hasMoreElements()) {
                    ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
                    if (zipArchiveEntry.isDirectory()) {
                        FileUtils.forceMkdir(new File(targetDir + zipArchiveEntry.getName() + File.separator));
                    } else {
                        IOUtils.copy(zf.getInputStream(zipArchiveEntry), FileUtils.openOutputStream(new File(targetDir + zipArchiveEntry.getName())));
                    }
                }
            }
            zipfile.delete();
        } else {
            throw new IOException("指定的解压文件不存在" + zipFilePathWithName);
        }
    }


}

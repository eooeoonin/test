package com.winsmoney.jajaying.boss.domain.utils;

import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by jk on 2016/8/24.
 */
public class VelocityGenerator {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(VelocityGenerator.class);

    protected VelocityEngine ve;

    /**
     * 准备velocity引擎
     */
    public void init() {
        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
    }

    /**
     * @param entity
     * @param fileName /var/html/ xxx.html
     * @throws IOException
     */
    public void buildFileByTemplate(EntityObject entity, String fileName, String filePath) throws IOException {
        //添加数据
        VelocityContext ctx = new VelocityContext();
        ctx.put("entity", entity);
        //取得模板文件
        Template domainModelTemplateA = ve.getTemplate("template/a_news.vm", "utf-8"); 
        Template domainModelTemplateOt = ve.getTemplate("template/ot_news.vm", "utf-8");
        Template domainModelTemplatePc = ve.getTemplate("template/pc_news.vm", "utf-8");

        String outputPath = filePath + File.separator + "a_" + fileName;
        //渲染并保存文件
        render(domainModelTemplateA, ctx, outputPath);
        outputPath = filePath + File.separator + "ot_" + fileName;
        render(domainModelTemplateOt, ctx, outputPath);
        outputPath = filePath + File.separator + "pc_" + fileName;
        render(domainModelTemplatePc, ctx, outputPath);
    }
    
    public void buildPcNewsByTemplate(EntityObject entity, String fileName, String filePath) throws IOException {
    	//添加数据
    	VelocityContext ctx = new VelocityContext();
    	ctx.put("entity", entity);
    	//取得模板文件
    	Template domainModelTemplatePc = ve.getTemplate("template/pc_news.vm", "utf-8");
    	
    	String outputPath = filePath + File.separator + "pc_" + fileName;
    	render(domainModelTemplatePc, ctx, outputPath);
    }


    /**
     * 渲染
     *
     * @param template
     * @param ctx
     * @param file
     */
    protected void render(Template template, VelocityContext ctx, String file) {
        File out = new File(file);
        FileWriter writer = null;
        try {
            //如果路径不存在，创建路径
            out.getParentFile().mkdirs();
            writer = new FileWriter(out);
            //渲染
            template.merge(ctx, writer);
            writer.flush();

        } catch (IOException e) {
            logger.error("文件生成失败", e);
        } finally {
            try {
            	if(writer != null){
                	writer.close();
            	}
            } catch (IOException e) {
                logger.error("文件流关闭失败", e);
            }
        }
    }

}

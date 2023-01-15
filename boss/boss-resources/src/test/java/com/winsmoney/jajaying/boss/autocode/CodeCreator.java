package com.winsmoney.jajaying.boss.autocode;

import com.winsmoney.autocode.domain.enums.CodeTemplate;
import com.winsmoney.autocode.service.impl.AutoCodeService;
import com.winsmoney.autocode.service.request.CreateCodeRequest;
import org.junit.Test;

/**
 * Created by guoxuechong on 2016/3/22.
 */
public class CodeCreator
{
    /**
     * 生成公共代码
     */
    //  @Test
    public void testCreatCommonCode() throws Exception
    {
        CreateCodeRequest request = new CreateCodeRequest();
        request.initConfigXml("/mybatis/autocode-config.xml");   //配置文件
        //配置
        request.setProjectName("boss"); //项目名称
        request.setTargetRootPath("..\\"); //代码保存路径：默认为本项目内：../

        //设置需要生成的代码文件
        request.getCodeTemplates().add(CodeTemplate.BaseManager);
        request.getCodeTemplates().add(CodeTemplate.RuntimeException);
        request.getCodeTemplates().add(CodeTemplate.InnerErrorCode);
        request.getCodeTemplates().add(CodeTemplate.ServiceCommonResult);
        request.getCodeTemplates().add(CodeTemplate.CommonServiceInterface);
        request.getCodeTemplates().add(CodeTemplate.CommonService);
        request.getCodeTemplates().add(CodeTemplate.BaseService);
        AutoCodeService service = new AutoCodeService();
        service.createCommonCode(request);
    }

    /**
     * 根据数据库表生成代码文件，请先修改配置文件
     */
    @Test
    public void testCreatCodeFromTable() throws Exception
    {
        CreateCodeRequest request = new CreateCodeRequest();
        request.initConfigXml("/mybatis/autocode-config.xml");   //配置文件
        //配置
        request.setProjectName("boss"); //项目名称
        request.setTargetRootPath("../"); //代码保存路径：默认为本项目内：../
        request.getDbConfig().setSchemas("boss"); //使用数据库名称
        request.getTables().clear();
        //添加数据表，可以添加多个
        request.addTable("t_activity");
//        request.addTable("t_award_rule");
//        request.addTable("t_award_detail_log");
//        request.addTable("t_activity_extend");
//        request.addTable("t_award_detail");
//        request.addTable("t_activity_request");
//        request.addTable("t_channel");
//        request.addTable("t_channel_type");
//        request.addTable("t_department");
//        request.addTable("t_import_waybill");
//        request.addTable("t_link");
//        request.addTable("t_menu");
//        request.addTable("t_redmoney_info");
//        request.addTable("t_resource");
//        request.addTable("t_staff");
//        request.addTable("t_template");
//        request.addTable("t_tmp_user");
//        request.addTable("t_user_role");
//        request.addTable("test");

        //设置需要生成的代码文件
//        request.getCodeTemplates().add(CodeTemplate.DaoPo);
//        request.getCodeTemplates().add(CodeTemplate.DaoMapper);
//        request.getCodeTemplates().add(CodeTemplate.DomainModel);
//        request.getCodeTemplates().add(CodeTemplate.DomainManager);
//        request.getCodeTemplates().add(CodeTemplate.DomainImpl);
//        request.getCodeTemplates().add(CodeTemplate.ServiceRequest);
//        request.getCodeTemplates().add(CodeTemplate.ServiceResponse);
//        request.getCodeTemplates().add(CodeTemplate.ServiceInterface);
//        request.getCodeTemplates().add(CodeTemplate.ServiceImpl);
        request.getCodeTemplates().add(CodeTemplate.ResourceXML);
//        request.getCodeTemplates().add(CodeTemplate.ServiceImplXML);
//        request.getCodeTemplates().add(CodeTemplate.ServiceTest);

        AutoCodeService service = new AutoCodeService();
        service.createCodeFromTable(request);
    }
}

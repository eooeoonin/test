package com.winsmoney.jajaying.boss.controller.crowdfunding;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.crowdfunding.service.ICompanyInfoService;
import com.winsmoney.jajaying.crowdfunding.service.IEstateProjectService;
import com.winsmoney.jajaying.crowdfunding.service.IEstateUserService;
import com.winsmoney.jajaying.crowdfunding.service.IProductInfoService;
import com.winsmoney.jajaying.crowdfunding.service.enums.FileType;
import com.winsmoney.jajaying.crowdfunding.service.request.CompanyInfoReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.EstateProjectReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.EstateUserReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.ProductInfoReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.UploadFileReqVo;
import com.winsmoney.jajaying.crowdfunding.service.response.CompanyInfoResVo;
import com.winsmoney.jajaying.crowdfunding.service.response.CrowdfundingCommonResult;
import com.winsmoney.jajaying.crowdfunding.service.response.EstateProjectResVo;
import com.winsmoney.jajaying.crowdfunding.service.response.EstateUserResVo;
import com.winsmoney.jajaying.crowdfunding.service.response.ProductInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;


@Controller
@RequestMapping("/crowdfunding")
public class MerchantController {
	private  static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(MerchantController.class);
	@Autowired
	private ICompanyInfoService companyInfoService; 
	@Autowired
	private IEstateProjectService estateProjectService;
	@Autowired
	private IEstateUserService estateUserService;
	@Autowired
	private IProductInfoService productInfoService;
	
	@Value(value="${crowdfunding.project.managerSwitch}")
	private String managerSwitch;
	/**
	* merchantList:商户列表. <br/>
	*/
	@RequestMapping(value="/merchant/merchantList" , method = RequestMethod.POST)
	@ResponseBody
	public Result merchantList(CompanyInfoReqVo companyInfoReqVo,int pageNo, int pageSize){
		CrowdfundingCommonResult<PageInfo<CompanyInfoResVo>> result =  companyInfoService.selectCompanyInfoPageList(companyInfoReqVo, pageNo, pageSize);
		if(result.isSuccess())
			return Result.success(result.getBusinessObject());
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	@RequestMapping(value="/merchant/add" , method = RequestMethod.POST)
	@ResponseBody
	public Result add(CompanyInfoReqVo companyInfoReqVo, String busFile, String orgFile, String taxFile, String proFile){
		try {
			
			List<UploadFileReqVo> list = new ArrayList<UploadFileReqVo>();		
			list.add(setFile(FileType.BUSINESS_LICENSE,busFile));
			list.add(setFile(FileType.ORG_NO,orgFile));
			list.add(setFile(FileType.TAX_REG,taxFile));
			list.add(setFile(FileType.PROTOCOL,proFile));
			companyInfoReqVo.setFiles(list);
			CrowdfundingCommonResult<Integer>  result = companyInfoService.insertCompanyInfo(companyInfoReqVo);
			if(result.isSuccess())
				return Result.success("success");
			else
				return Result.error(result.getResultCodeMsg());
		}catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}
	
	@RequestMapping(value="/merchant/getById" , method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Result toEditPage(String companyId){
		CrowdfundingCommonResult<CompanyInfoResVo> result = companyInfoService.selectCompanyInfo(companyId);
		if(result.isSuccess())
			return Result.success(result.getBusinessObject());
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	@RequestMapping(value="/merchant/edit" , method = RequestMethod.POST)
	@ResponseBody
	public Result edit(CompanyInfoReqVo companyInfoReqVo, String busFile, String orgFile, String taxFile, String proFile){
		
		List<UploadFileReqVo> list = new ArrayList<UploadFileReqVo>();		
		list.add(setFile(FileType.BUSINESS_LICENSE,busFile));
		list.add(setFile(FileType.ORG_NO,orgFile));
		list.add(setFile(FileType.TAX_REG,taxFile));
		list.add(setFile(FileType.PROTOCOL,proFile));
		companyInfoReqVo.setFiles(list);
		CrowdfundingCommonResult<Integer>  result = companyInfoService.update(companyInfoReqVo);
		if(result.isSuccess())
			return Result.success("success");
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	@RequestMapping(value = "/merchant/projectAdd", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Result projectAdd(EstateProjectReqVo estateProjectReqVo){
		CrowdfundingCommonResult<Integer>  result = estateProjectService.insertEstateProject(estateProjectReqVo);
		if(result.isSuccess())
			return Result.success("success");
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	
	@RequestMapping(value = "/merchant/projectList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Result projectList(String companyId, int pageNo, int pageSize){
		
		EstateProjectReqVo estateProjectReqVo = new EstateProjectReqVo();
		estateProjectReqVo.setCompanyId(companyId);
		logger.info("接口{}入参:" + JSONObject.toJSONString(estateProjectReqVo),IEstateProjectService.class);
		CrowdfundingCommonResult<PageInfo<EstateProjectResVo>> result =  estateProjectService.selectEstateProjectPageList(estateProjectReqVo, pageNo, pageSize);
		logger.info("接口{}出参:" + JSONObject.toJSONString(result),IEstateProjectService.class);
		if(result.isSuccess())
			return Result.success(result.getBusinessObject());
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	
	@RequestMapping(value = "/merchant/projectGetById", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Result projectGetById(String projectId){

		CrowdfundingCommonResult<EstateProjectResVo> result=estateProjectService.selectEstateProject(projectId);
		EstateProjectResVo estateProjectResVo=result.getBusinessObject();
		EstateProjectResForm estateProjectResForm = BeanMapper.map(estateProjectResVo, EstateProjectResForm.class);

		CrowdfundingCommonResult<CompanyInfoResVo> commonResult=companyInfoService.selectCompanyInfo(result.getBusinessObject().getCompanyId());
		estateProjectResForm.setCompanyName(commonResult.getBusinessObject().getName());

		//查询产品信息
		ProductInfoReqVo reqVo=new ProductInfoReqVo();
		reqVo.setEstateId(estateProjectResVo.getId());
		CrowdfundingCommonResult<List<ProductInfoResVo>> crowdCommonResult=productInfoService.selectProductInfoList(reqVo);
		List<ProductInfoResVo> list=crowdCommonResult.getBusinessObject();
		String editFlag="1";
		if(CollectionUtils.isNotEmpty(list)){
			for(ProductInfoResVo productInfo:list){
				if(!productInfo.getStatus().equals("0")){
					editFlag="0";
					break;
				}
			}
		}
		estateProjectResForm.setEditFlag(editFlag);
		return Result.success(estateProjectResForm);
				
	}
	
	@RequestMapping(value = "/merchant/projectEdit", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Result projectEdit(EstateProjectReqVo estateProjectReqVo){
		estateProjectReqVo.getEstateUser().setProjectId(estateProjectReqVo.getId());
		estateProjectReqVo.getProLocationInfo().setProjectId(estateProjectReqVo.getId());
		CrowdfundingCommonResult<Integer>  result = estateProjectService.updateEstateProject(estateProjectReqVo);
		if(result.isSuccess())
			return Result.success("success");
		else
			return Result.error(result.getResultCodeMsg());
	}
	
	/**
	 * 查询管理员是否存在
	 * @param model
	 * @param EstateProjectReqVo
	 * @return
	 */
	@RequestMapping(value = "/merchant/queryManager",method = RequestMethod.POST)
	@ResponseBody
	public String queryManager(EstateProjectReqVo EstateProjectReqVo ){

		//管理员重复开关 true可重复  false不可重复
		if("true".endsWith(managerSwitch)){
			//管理员可管理多个项目
			return "success";
		}

		EstateUserReqVo estateUserReqVo=new EstateUserReqVo();
		estateUserReqVo.setProjectId(EstateProjectReqVo.getId());
		estateUserReqVo.setUsername(EstateProjectReqVo.getEstateUser().getUsername());
		CrowdfundingCommonResult<EstateUserResVo> commonResult=estateUserService.selectEstateUser(estateUserReqVo);
		EstateUserResVo estateUserResVo=commonResult.getBusinessObject();
		if(StringUtils.isNotEmpty(estateUserResVo.getUsername())&&StringUtils.isEmpty(EstateProjectReqVo.getId())){
			return "error";
		}
		return "success";
	}
	
	
	private UploadFileReqVo setFile(FileType type,String file){
		UploadFileReqVo uploadFile = new UploadFileReqVo();
		uploadFile.setName(file.split("/")[file.split("/").length-1]);
		if(StringUtils.isNotEmpty(file)){
			uploadFile.setPath(file);
		}
		uploadFile.setType(type);
		return uploadFile;
	}
	
}

    
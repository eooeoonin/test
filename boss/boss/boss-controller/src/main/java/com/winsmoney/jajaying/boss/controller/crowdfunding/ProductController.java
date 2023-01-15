package com.winsmoney.jajaying.boss.controller.crowdfunding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.model.SubProductInfoResForm;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.crowdfunding.service.IEstateProjectService;
import com.winsmoney.jajaying.crowdfunding.service.IProductInfoService;
import com.winsmoney.jajaying.crowdfunding.service.IProtocolService;
import com.winsmoney.jajaying.crowdfunding.service.ISubProductInfoService;
import com.winsmoney.jajaying.crowdfunding.service.enums.FileType;
import com.winsmoney.jajaying.crowdfunding.service.request.EstateProjectReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.ProductInfoReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.ProtocolReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.SubProductInfoReqVo;
import com.winsmoney.jajaying.crowdfunding.service.request.UploadFileReqVo;
import com.winsmoney.jajaying.crowdfunding.service.response.CrowdfundingCommonResult;
import com.winsmoney.jajaying.crowdfunding.service.response.EstateProjectResVo;
import com.winsmoney.jajaying.crowdfunding.service.response.ProductInfoResVo;
import com.winsmoney.jajaying.crowdfunding.service.response.ProtocolResVo;
import com.winsmoney.jajaying.crowdfunding.service.response.SubProductInfoResVo;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.DateUtil;

/**
 * 众筹产品管理
 * 
 * @author ChenKai
 * @date 2017年1月18日
 */
@Controller
@RequestMapping("/crowdfunding/product")
public class ProductController {
	private final static WinsMoneyLogger LOG = WinsMoneyLoggerFactory.getLogger(ProductController.class);

	@Autowired
	private IProductInfoService productInfoService;
	@Autowired
	private IEstateProjectService estateProjectService;
	@Autowired
	private IProtocolService crowdProtocolService;
	@Autowired
	private ISubProductInfoService subProductInfoService;

	@RequestMapping(value = "/listWithPage", method = RequestMethod.POST)
	@ResponseBody
	public Result listWithPage(ProductInfoReqVo productInfoReqVo, int pageNo, int pageSize) {
		try {
			CrowdfundingCommonResult<PageInfo<ProductInfoResVo>> result = productInfoService.selectProductInfoPageList(productInfoReqVo, pageNo, pageSize);
			if (result.isSuccess()) {
				return Result.success(result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			LOG.error("取得众筹产品分页列表异常", e);
		}
		return Result.error("取得众筹产品分页列表异常");
	}

	/**
	 * 下架众筹产品
	 */
	@RequestMapping(value = "/unSale", method = RequestMethod.POST)
	@ResponseBody
	public Result unSale(String productId) {
		try {
			ProductInfoReqVo productInfoReqVo = new ProductInfoReqVo();
			productInfoReqVo.setId(productId);
			productInfoReqVo.setStatus("2");
			CrowdfundingCommonResult<Integer> result = productInfoService.undercarriage(productInfoReqVo);
			if (result.isSuccess()) {
				return Result.success(result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			LOG.error("下架众筹产品异常", e);
		}
		return Result.error("下架众筹产品失败");
	}

	/**
	 * 查询项目信息
	 */
	@RequestMapping(value = "/projectList", method = RequestMethod.POST)
	@ResponseBody
	public Result projectList() {
		try {
			EstateProjectReqVo estateProjectReqVo = new EstateProjectReqVo();
			CrowdfundingCommonResult<PageInfo<EstateProjectResVo>> result = estateProjectService.selectEstateProjectPageList(estateProjectReqVo, 0, 1000);
			if (result.isSuccess()) {
				return Result.success(result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			LOG.error("查询项目信息异常", e);
		}
		return Result.error("查询项目信息失败");
	}

	/**
	 * 新增或编辑产品
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Result add(ProductInfoReqVo productInfoReqVo, String listFile, String bannerFile, HttpServletRequest request) {
		try {
			CrowdfundingCommonResult<Integer> result = new CrowdfundingCommonResult<Integer>(null, null, 0);
			setFileNameAndOrder(productInfoReqVo, listFile, bannerFile);
			if (StringUtils.isNotBlank(productInfoReqVo.getId())) {
				result = productInfoService.updateProductInfo(productInfoReqVo);
			} else {
				result = productInfoService.insertProductInfo(productInfoReqVo);
			}
			if (result.isSuccess()) {
				return Result.success(result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			LOG.error("新增众筹产品异常", e);
		}
		return Result.error("新增众筹产品失败");
	}

	/**
	 * 取得单个产品详细信息
	 */
	@RequestMapping(value = "/getProduct")
	@ResponseBody
	public Result getProduct(String productId) {
		try {
			CrowdfundingCommonResult<ProductInfoResVo> result = productInfoService.selectProductInfo(productId);
			if (result.isSuccess()) {
				return Result.success(result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			LOG.error("取得单个产品详细信息异常", e);
		}
		return Result.error("取得单个产品详细信息失败");
	}

	/**
	 * 查询协议信息
	 */
	@RequestMapping(value = "/protocolList", method = RequestMethod.POST)
	@ResponseBody
	public Result protocolList() {
		try {
			ProtocolReqVo protocolReqVo = new ProtocolReqVo();
			protocolReqVo.setDeleted(0);
			CrowdfundingCommonResult<List<ProtocolResVo>> result = crowdProtocolService.list(protocolReqVo);
			if (result.isSuccess()) {
				return Result.success(result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			LOG.error("查询协议信息异常", e);
		}
		return Result.error("查询协议信息失败");
	}

	/**
	 * 新增或编辑权益
	 */
	@RequestMapping(value = "/addSub")
	@ResponseBody
	public Result addSub(SubProductInfoReqVo subproductInfoReqVo) {
		try {
			CrowdfundingCommonResult<Integer> result = new CrowdfundingCommonResult<Integer>(null, null, 0);
			Date dateEnd = DateUtil.getDayEnd(subproductInfoReqVo.getEndTime());
			subproductInfoReqVo.setEndTime(dateEnd);
			if (StringUtils.isNotBlank(subproductInfoReqVo.getId())) {
				result = subProductInfoService.updateSubProjectInfo(subproductInfoReqVo);
			} else {
				result = subProductInfoService.insertSubProjectInfo(subproductInfoReqVo);
			}
			if (result.isSuccess()) {
				return Result.success(result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			LOG.error("新增或编辑权益异常", e);
		}
		return Result.error("新增或编辑权益失败");
	}

	/**
	 * 下架权益
	 */
	@RequestMapping(value = "/subProjectUnSale", method = RequestMethod.POST)
	@ResponseBody
	public Result subProjectUnSale(String subProjectId) {
		try {

			SubProductInfoReqVo subproductInfoReqVo = new SubProductInfoReqVo();
			subproductInfoReqVo.setId(subProjectId);
			subproductInfoReqVo.setStatus("0");
			CrowdfundingCommonResult<Integer> result = subProductInfoService.updateSubProjectInfo(subproductInfoReqVo);
			if (result.isSuccess()) {
				return Result.success(result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			LOG.error("下架产品权益异常", e);
		}
		return Result.error("下架产品权益失败");
	}

	/**
	 * 给权益设置结算参数
	 */
	@RequestMapping(value = "/setSettle", method = RequestMethod.POST)
	@ResponseBody
	public Result setSettle(SubProductInfoReqVo subproductInfoReqVo) {
		try {
			CrowdfundingCommonResult<Integer> result = subProductInfoService.updateSubProjectInfo(subproductInfoReqVo);
			if (result.isSuccess()) {
				return Result.success(result);
			} else
				return Result.error(result.getResultCodeMsg());
		} catch (Exception e) {
			LOG.error("给权益设置结算参数异常", e);
		}
		return Result.error("给权益设置结算参数失败");
	}

	/**
	 * 取得单个权益信息
	 */
	@RequestMapping(value = "/getSubProduct")
	@ResponseBody
	public Result getSubProduct(String subProductId) {
		try {
			SubProductInfoResForm result = new SubProductInfoResForm();
			CrowdfundingCommonResult<SubProductInfoResVo> resultVo = subProductInfoService.selectSubProjectInfo(subProductId);
			if (resultVo.isSuccess()) {
				result = BeanMapper.map(resultVo.getBusinessObject(), SubProductInfoResForm.class);
				Date startTime = result.getStartTime();
				if (startTime.compareTo(new Date()) > 0) {
					result.setEditFlag("1");
				}
				return Result.success(result);
			} else
				return Result.error(resultVo.getResultCodeMsg());
		} catch (Exception e) {
			LOG.error("取得单个权益详细信息异常", e);
		}
		return Result.error("取得单个权益详细信息失败");
	}

	private void setFileNameAndOrder(ProductInfoReqVo productInfoReqVo, String listFile, String bannerFile) {
		Date dateEnd = DateUtil.getDayEnd(productInfoReqVo.getEndTime());
		productInfoReqVo.setEndTime(dateEnd);
		productInfoReqVo.setStatus("0");
		List<UploadFileReqVo> files = productInfoReqVo.getFiles();
		List<UploadFileReqVo> allFiles = new ArrayList<UploadFileReqVo>();
		for (int i = 1; i <= files.size(); i++) {
			UploadFileReqVo uploadFileReqVo = files.get(i - 1);
			if (StringUtils.isNotBlank(uploadFileReqVo.getName())) {
				uploadFileReqVo.setType(FileType.PRODUCT_DSP);
				uploadFileReqVo.setOrderId(String.valueOf(i));
				uploadFileReqVo.setPath(uploadFileReqVo.getName());
				allFiles.add(uploadFileReqVo);
			}
		}
		UploadFileReqVo listFileReq = constructUploadFileReqVo(listFile, FileType.PRODUCT_LIST);
		UploadFileReqVo bannerFileReq = constructUploadFileReqVo(bannerFile, FileType.PRODUCT_BANNER);
		allFiles.add(listFileReq);
		allFiles.add(bannerFileReq);
		productInfoReqVo.setFiles(allFiles);
	}

	private UploadFileReqVo constructUploadFileReqVo(String fileName, FileType fileType) {
		UploadFileReqVo listFileReq = new UploadFileReqVo();
		listFileReq.setType(fileType);
		listFileReq.setName(fileName);
		listFileReq.setPath(fileName);
		return listFileReq;
	}

}

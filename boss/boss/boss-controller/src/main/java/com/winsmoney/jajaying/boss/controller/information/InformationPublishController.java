package com.winsmoney.jajaying.boss.controller.information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.controller.BaseController;
import com.winsmoney.jajaying.boss.controller.model.OperationStatisticsExcelInfo;
import com.winsmoney.jajaying.boss.controller.utils.ExcelUtils;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.trade.service.IOperationStatisticsService;
import com.winsmoney.jajaying.trade.service.request.OperationStatisticsReqVo;
import com.winsmoney.jajaying.trade.service.response.OperationStatisticsResVo;
import com.winsmoney.jajaying.trade.service.response.TradeCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.DateUtil;
import com.winsmoney.platform.framework.core.util.Money;

/**
 * Created by wei on 2016/8/15.
 */
@Controller
@RequestMapping("/information")
public class InformationPublishController extends BaseController {
    private static final WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(InformationPublishController.class);

    @Autowired
    private IOperationStatisticsService operationStatisticsService;

    /**
     * 运营统计信息列表
     */
    @RequestMapping(value = "/operation_statistic_list", method = {RequestMethod.POST})
    @ResponseBody
    public Result getOperationStatistic(OperationStatisticsReqVo operationStatisticsReqVo, int pageNo, int pageSize) {
        TradeCommonResult<PageInfo<OperationStatisticsResVo>> result = operationStatisticsService.list(operationStatisticsReqVo, pageNo, pageSize);
        if (result.isSuccess()) {
            PageInfo<OperationStatisticsResVo> pageInfo = result.getBusinessObject();
            pageInfo.setList(BeanMapper.mapList(pageInfo.getList(), OperationStatisticsResVo.class));
            return Result.success(pageInfo);
        } else {
            return Result.error(result.getResultCodeMsg());
        }
    }
    
    /**
     * 修改运营统计信息状态
     */
    @RequestMapping(value = "/operation_statistic_changeStatus", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> changeStatus(OperationStatisticsReqVo operationStatisticsReqVo) {
		Map<String, Object> result = new HashMap<String, Object>();

    	operationStatisticsReqVo.setMark("0");
    	TradeCommonResult<OperationStatisticsResVo> updateResult = operationStatisticsService.update(operationStatisticsReqVo);
    	if (updateResult.isSuccess()) {
			result.put("flag", 1);
			TradeCommonResult<OperationStatisticsResVo> info = operationStatisticsService.getLastInfo(operationStatisticsReqVo);
			result.put("updateTime", info.getBusinessObject().getModifyTime());
		} else {
			result.put("flag", 0);
		}
    	
    	return result;
    }

    /**
     * 导出投资人投资记录
     *
     * @param loanId
     * @param phase
     * @param response
     */
    @RequestMapping(value = "/operation_statistic_export")
    @ResponseBody
    public Result export(OperationStatisticsReqVo operationStatisticsReqVo, HttpServletResponse response) {

        try {
            // 查询运营统计信息列表
        	TradeCommonResult<PageInfo<OperationStatisticsResVo>> result = operationStatisticsService.list(operationStatisticsReqVo, 1, Integer.MAX_VALUE);

            logger.info("接口{}出参:" + JSONObject.toJSONString(result), OperationStatisticsResVo.class);
            List<OperationStatisticsExcelInfo> infos = new ArrayList<OperationStatisticsExcelInfo>();
            for (OperationStatisticsResVo resVo : result.getBusinessObject().getList()) {
            	OperationStatisticsExcelInfo info = new OperationStatisticsExcelInfo();
            	
            	info.setInfoMonth(resVo.getInfoMonth());//统计月份
				info.setHistoryAmount(resVo.getHistoryAmount().divide(10000).toString());//累计借贷金额
				info.setHistoryTrade(resVo.getHistoryTrade());//累计借贷笔数
				info.setBorrowBalance(resVo.getBorrowBalance().divide(10000).toString());//借贷余额
				info.setCurrentBorrowTrade(resVo.getCurrentBorrowTrade());//借贷余额笔数
				info.setHistoryLender(resVo.getHistoryLender());//累计出借人数量
				info.setHistoryBorrowerNum(resVo.getHistoryBorrowerNum());//累计借款人数量
				info.setCurrentLender(resVo.getCurrentLender());//当期出借人数量
				info.setCurrentBorrowerNum(resVo.getCurrentBorrowerNum());//当期借款人数量
				if (resVo.getHistoryAllamount().equals(new Money())) {
					info.setHistoryTop10amountPercent("0.00%");//前十大借款人待还金额占比%
					info.setHistoryTop1amountPercent("0.00%");//最大单一借款人待还金额占比%
				} else {
					info.setHistoryTop10amountPercent(resVo.getHistoryTop10amount().multiply(100).divide(resVo.getHistoryAllamount().getAmount()) + "%");//前十大借款人待还金额占比%
					info.setHistoryTop1amountPercent(resVo.getHistoryTop1amount().multiply(100).divide(resVo.getHistoryAllamount().getAmount()) + "%");//最大单一借款人待还金额占比%
				}
                infos.add(info);
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String("运营披露数据统计表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            String[] headers;
            ExcelUtils<OperationStatisticsExcelInfo> utils = new ExcelUtils<OperationStatisticsExcelInfo>();
            headers = new String[]{"统计月份", "累计借贷金额（万元）", "累计借贷笔数", "借贷余额（万元）", "借贷余额笔数", "累计出借人数量", "累计借款人数量", "当期出借人数量", "当期借款人数量", "前十大借款人待还金额占比%", "最大单一借款人待还金额占比%"};
            utils.exportExcel("运营披露数据统计表", headers, infos, response.getOutputStream(), "YYYY-MM-dd HH:mm:ss");
        } catch (Exception e) {
            logger.error("导出投资人失败", e);
        }
        return Result.success("success");
    }

}

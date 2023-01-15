package com.winsmoney.jajaying.boss.controller.user;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.jajaying.retail.service.IMainOrdersService;
import com.winsmoney.jajaying.retail.service.enums.OrderStatusType;
import com.winsmoney.jajaying.retail.service.request.MainOrdersReqVo;
import com.winsmoney.jajaying.retail.service.response.MainOrdersResVo;
import com.winsmoney.jajaying.retail.service.response.RetailCommonResult;
import com.winsmoney.jajaying.retailgoods.service.IExpressCabinetService;
import com.winsmoney.jajaying.retailgoods.service.IGoodsAppraiseService;
import com.winsmoney.jajaying.retailgoods.service.request.ExpressCabinetReqVo;
import com.winsmoney.jajaying.retailgoods.service.response.ExpressCabinetResVo;
import com.winsmoney.jajaying.retailgoods.service.response.GoodsAppraiseResVo;
import com.winsmoney.jajaying.retailgoods.service.response.RetailgoodsCommonResult;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.runtime.CommonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 生鲜用户信息
 * Created by chenkai1 on 2017/9/24.
 */

@RestController
public class FreshUserController {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(FreshUserController.class);
    @Autowired
    private IMainOrdersService mainOrdersService;
    @Autowired
    private IExpressCabinetService expressCabinetService;
    @Autowired
    private IGoodsAppraiseService goodsAppraiseService;

    /**
     * 生鲜主订单消费记录
     */
    @RequestMapping("/user/user_list/selectFreshOrderRecord")
    public Result selectFreshOrderRecord(MainOrdersReqVo mainOrdersReqVo, int pageNo, int pageSize) {
        try {
            RetailCommonResult<PageInfo<MainOrdersResVo>> orderResult = mainOrdersService.list(mainOrdersReqVo, pageNo, pageSize);
            PageInfo<MainOrdersResVo> pageInfo = orderResult.getBusinessObject();
            List<MainOrdersResVo> list = pageInfo.getList();
            for (MainOrdersResVo mainOrdersResVo : list) {
                ExpressCabinetReqVo expressCabinetReqVo = new ExpressCabinetReqVo();
                expressCabinetReqVo.setCabinetCode(mainOrdersResVo.getCabinetCode());
                RetailgoodsCommonResult<ExpressCabinetResVo> expressCabinetResult = expressCabinetService.get(expressCabinetReqVo);
                if (expressCabinetResult.isSuccess() && null != expressCabinetResult && StringUtils.isNotBlank(expressCabinetResult.getBusinessObject().getId())) {
                    mainOrdersResVo.setCabinetName(expressCabinetResult.getBusinessObject().getAddress());
                }
            }
            pageInfo.setList(list);
            if (orderResult.isSuccess()) {
                return Result.success(pageInfo);
            } else return Result.error("查询生鲜消费记录异常");
        } catch (Exception e) {
            logger.error("查询生鲜消费记录接口异常", e);
            return Result.error("查询生鲜消费记录接口异常");
        }
    }

    @RequestMapping("/user/user_list/selectFreshAppraiseRecord")
    public Result selectFreshAppraiseRecord(String userId, int pageNo, int pageSize) {
        try {
            CommonResult<PageInfo<GoodsAppraiseResVo>> appraiseResult = goodsAppraiseService.mainOrderAppraise(userId, pageNo, pageSize);
            if (appraiseResult.isSuccess()) {
                PageInfo<GoodsAppraiseResVo> businessObject = appraiseResult.getBusinessObject();
                return Result.success(businessObject);
            } else return Result.error("查询生鲜评价记录异常");
        } catch (Exception e) {
            logger.error("查询生鲜评价记录接口异常", e);
            return Result.error("查询生鲜评价记录接口异常");
        }
    }

}

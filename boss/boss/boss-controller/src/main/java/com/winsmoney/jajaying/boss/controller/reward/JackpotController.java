package com.winsmoney.jajaying.boss.controller.reward;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.award.service.*;
import com.winsmoney.jajaying.award.service.enums.AwardType;
import com.winsmoney.jajaying.award.service.enums.PassStatus;
import com.winsmoney.jajaying.award.service.enums.SeneCode;
import com.winsmoney.jajaying.award.service.request.*;
import com.winsmoney.jajaying.award.service.response.*;
import com.winsmoney.jajaying.boss.controller.AduitLog;
import com.winsmoney.jajaying.boss.dao.enums.OperType;
import com.winsmoney.jajaying.boss.domain.utils.Result;
import com.winsmoney.platform.framework.core.log.WinsMoneyLogger;
import com.winsmoney.platform.framework.core.log.WinsMoneyLoggerFactory;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import com.winsmoney.platform.framework.core.util.Money;
import com.winsmoney.platform.framework.uuid.SequenceGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 奖池管理
 */
@Controller
@RequestMapping("/reward/project_list")
public class JackpotController {
    private final static WinsMoneyLogger logger = WinsMoneyLoggerFactory.getLogger(JackpotController.class);

    @Autowired
    private IAwardPoolService awardPoolService;  //奖池列表

    @Autowired
    private ILotteryChangesService lotteryChangesService;

    @Autowired
    private ILotteryInfoService lotteryInfoService;

    @Autowired
    private IPoolMsgService poolMsgService;

    @Autowired
    private IVoucherInfoService voucherInfoService;
    @Autowired
    private IPassPoolService passPoolService;
    @Autowired
    private IPassInfoService passInfoService;
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @RequestMapping("/jackpot/listNews")
    @ResponseBody
    public PageInfo<PoolMsgResVo> list(PoolMsgReqVo poolMsgReqVo, int pageNo, int pageSize) {
        AwardCommonResult<PageInfo<PoolMsgResVo>> list = poolMsgService.list(poolMsgReqVo, pageNo, pageSize);
        return list.getBusinessObject();
    }


    @RequestMapping("/jackpot/insertNews")
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加奖池信息")
    public Integer insertNews(PoolMsgReqVo poolMsgReqVo) {
        AwardCommonResult<Integer> insert = poolMsgService.insert(poolMsgReqVo);
        return insert.getBusinessObject();
    }

    @RequestMapping("/jackpot/deleteNews")
    @ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除奖池信息")
    public Integer deleteNews(String id) {
        AwardCommonResult<Integer> deleteNews = poolMsgService.delete(id);
        return deleteNews.getBusinessObject();
    }


    @RequestMapping("/jackpot/list")
    @ResponseBody
    public PageInfo<AwardPoolResVo> list(AwardPoolReqVo awardPoolReqVo, int pageNo, int pageSize) {
        AwardCommonResult<PageInfo<AwardPoolResVo>> list = awardPoolService.list(awardPoolReqVo, pageNo, pageSize);
        return list.getBusinessObject();
    }


    @RequestMapping("/jackpot/insert")
    @ResponseBody
	@AduitLog(type = OperType.CREATE, content = "添加奖池")
    public Result insert(@RequestParam(required = false) CommonsMultipartFile file, AwardForm awardForm) {
        Map<String, Object> validateResult = validatePassFile(file, awardForm);
        boolean flag = (boolean) validateResult.get("flag");
        if (!flag) {
            return Result.error("卡密条数据与奖品个数不相同，请重新修改");
        }
        try {
            AwardPoolReqVo awardPoolReqVo = BeanMapper.map(awardForm, AwardPoolReqVo.class);
            handlerLotteryInfo(awardPoolReqVo);
            if (awardPoolReqVo.getType() == AwardType.VOUCHEROFFLINE || awardPoolReqVo.getType() == AwardType.VOUCHER || awardPoolReqVo.getType() == AwardType.VOUCHERH5) {
                awardPoolReqVo.setLotteryChangesReqVos(null);
                awardPoolReqVo.setLotteryInfoReqVo(null);
            }
            if (awardPoolReqVo.getType() == AwardType.VOUCHER && SeneCode.INTEREST == awardPoolReqVo.getVoucherInfoReqVo().getSeneCode()) {
                Money value = awardPoolReqVo.getValue().divide(new BigDecimal(100));    //加息券时将金额除以100
                awardPoolReqVo.setValue(value);
            }

            awardPoolReqVo.setUsedNumber(0L);
            awardPoolReqVo.setPayNumber(0L);
            awardPoolReqVo.setId(null);
            AwardCommonResult<AwardPoolResVo> insertAwardPoll = awardPoolService.createAwardPool(awardPoolReqVo);
            if (null != file && !file.isEmpty()) {
                String uuid = sequenceGenerator.getUUID();
                PassPoolReqVo passPoolReqVo = new PassPoolReqVo();
                passPoolReqVo.setId(uuid);
                passPoolReqVo.setPoolId(insertAwardPoll.getBusinessObject().getId());
                passPoolReqVo.setChannel("P2P");
                AwardCommonResult<Integer> insert = passPoolService.insert(passPoolReqVo);
                String[] passInfos = (String[]) validateResult.get("data");
                for (String passInfo : passInfos) {
                    PassInfoReqVo passInfoReqVo = new PassInfoReqVo();
                    passInfoReqVo.setPassPoolId(uuid);
                    passInfoReqVo.setPass(passInfo);
                    passInfoReqVo.setPassStatus(PassStatus.UNUSE.getCode());
                    passInfoService.insert(passInfoReqVo);
                }
            }
            //保存短信push配置
            List<PoolMsgReqVo> poolMsgReqVos = awardForm.getPoolMsgReqVos();
            for (PoolMsgReqVo msgReqVo : poolMsgReqVos) {
                if (StringUtils.isNotBlank(msgReqVo.getMsgKey()) && StringUtils.isNotBlank(msgReqVo.getMsgType())) {
                    msgReqVo.setPoolId(insertAwardPoll.getBusinessObject().getId());
                    AwardCommonResult<Integer> insert = poolMsgService.insert(msgReqVo);
                }
            }
            return Result.success("新增奖品成功");
        } catch (Exception e) {
            return Result.error("新增奖品失败");
        }

    }

    private Map<String, Object> validatePassFile(CommonsMultipartFile file, AwardForm awardForm) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("flag", true);
        if (null != file && !file.isEmpty()) {
            String[] strings = readFile(file);
            result.put("data", strings);
            Long number = awardForm.getNumber();
            if (null != number && strings.length != number) {
                result.put("flag", false);
            }
        }
        return result;
    }

    private String[] readFile(CommonsMultipartFile file) {
        try {
            String poolInfos = IOUtils.toString(file.getInputStream(), Charset.defaultCharset());
            String result = poolInfos.replaceAll("\\s*|\t|\r|\n", "");
            String[] split = result.split(",");
            return split;
        } catch (IOException e) {
            logger.error("上传文件失败", e);
        }
        return new String[0];
    }

    @RequestMapping("/jackpot/getById")
    @ResponseBody
    public List getById(String id) {
        AwardCommonResult<AwardPoolResVo> awardResult = awardPoolService.getById(id);
        AwardPoolResVo awardPoolRes = awardResult.getBusinessObject();
        //取得外链地址
        LotteryInfoReqVo lotteryInfoReqVo = new LotteryInfoReqVo();
        lotteryInfoReqVo.setPoolId(id);
        AwardCommonResult<LotteryInfoResVo> lotteryInfoRes = lotteryInfoService.get(lotteryInfoReqVo);
        LotteryInfoReqVo lotteryInfo = BeanMapper.map(lotteryInfoRes.getBusinessObject(), LotteryInfoReqVo.class);
        awardPoolRes.setLotteryInfoReqVo(lotteryInfo);
        // 取得线下代金券信息
        VoucherInfoReqVo voucherInfoResVo = new VoucherInfoReqVo();
        voucherInfoResVo.setPoolId(id);
        AwardCommonResult<VoucherInfoResVo> voucherInfoRes = voucherInfoService.get(voucherInfoResVo);
        VoucherInfoReqVo voucherInfo = BeanMapper.map(voucherInfoRes.getBusinessObject(), VoucherInfoReqVo.class);
        awardPoolRes.setVoucherInfoReqVo(voucherInfo);

        if (AwardType.VOUCHER.getCode().equalsIgnoreCase(awardPoolRes.getType())  && SeneCode.INTEREST == awardPoolRes.getVoucherInfoReqVo().getSeneCode()) {
            Money value = awardPoolRes.getValue().multiply(new BigDecimal(100));    //加息券回显时将金额乘以100
            awardPoolRes.setValue(value);
        }

        List list = new ArrayList<>();
        list.add(awardPoolRes);
        PoolMsgReqVo poolMsgReqVo = new PoolMsgReqVo();
        poolMsgReqVo.setPoolId(id);
        AwardCommonResult<PageInfo<PoolMsgResVo>> msgResult = poolMsgService.list(poolMsgReqVo, 1, 1000);
        list.add(msgResult.getBusinessObject());
        return list;
    }


    //抽奖概率

    //外链地址
    @RequestMapping("/jackpot/getId")
    @ResponseBody
    public LotteryInfoResVo getId(String poolId) {
        LotteryInfoReqVo lotteryInfoReqVo = new LotteryInfoReqVo();
        lotteryInfoReqVo.setPoolId(poolId);
        AwardCommonResult<LotteryInfoResVo> getId = lotteryInfoService.get(lotteryInfoReqVo);
        return getId.getBusinessObject();
    }

    @RequestMapping("/jackpot/bylotteryInfoId")
    @ResponseBody
    public PageInfo<LotteryChangesResVo> lotteryChangesServicelist(String poolId, int pageNo, int pageSize) {
        LotteryChangesReqVo lotteryChangesReqVo = new LotteryChangesReqVo();
        lotteryChangesReqVo.setPoolId(poolId);
        AwardCommonResult<PageInfo<LotteryChangesResVo>> byId = lotteryChangesService.list(lotteryChangesReqVo, pageNo, pageSize);
        return byId.getBusinessObject();
    }


    @RequestMapping("/jackpot/getVoch")
    @ResponseBody
    public VoucherInfoResVo getVoch(String poolId, int pageNo, int pageSize) {
        VoucherInfoReqVo voucherInfoResVo = new VoucherInfoReqVo();
        voucherInfoResVo.setPoolId(poolId);
        AwardCommonResult<VoucherInfoResVo> getId = voucherInfoService.get(voucherInfoResVo);
        return getId.getBusinessObject();
    }


    @RequestMapping("/jackpot/update")
    @ResponseBody
	@AduitLog(type = OperType.UPDATE, content = "更新奖池")
    public Integer update(AwardForm awardForm) {
        AwardPoolReqVo awardPoolReqVo = BeanMapper.map(awardForm, AwardPoolReqVo.class);
        int status = 0;
        handlerLotteryInfo(awardPoolReqVo);

        if (awardPoolReqVo.getType() == AwardType.VOUCHER && SeneCode.INTEREST == awardPoolReqVo.getVoucherInfoReqVo().getSeneCode()) {
            Money value = awardPoolReqVo.getValue().divide(new BigDecimal(100));    //加息券时将金额除以100
            awardPoolReqVo.setValue(value);
        }

        AwardCommonResult<AwardPoolResVo> result = awardPoolService.modifyAwardPool(awardPoolReqVo);
        String poolId = awardPoolReqVo.getId();
        //保存短信push配置
        poolMsgService.deletePoolMsgBypoolId(poolId);
        List<PoolMsgReqVo> poolMsgReqVos = awardForm.getPoolMsgReqVos();
        for (PoolMsgReqVo msgReqVo : poolMsgReqVos) {
            if (StringUtils.isNotBlank(msgReqVo.getMsgKey()) && StringUtils.isNotBlank(msgReqVo.getMsgType())) {
                msgReqVo.setPoolId(poolId);
                AwardCommonResult<Integer> insert = poolMsgService.insert(msgReqVo);
            }
        }
        if (result.isSuccess()) {
            status = 1;
        }
        return status;
    }

    private void handlerLotteryInfo(AwardPoolReqVo awardPoolReqVo) {
        if (awardPoolReqVo.getType() == AwardType.LOTTERY) {
            List<LotteryChangesReqVo> lotteryChangesReqVos = awardPoolReqVo.getLotteryChangesReqVos();
            Iterator<LotteryChangesReqVo> iterator = lotteryChangesReqVos.iterator();
            while (iterator.hasNext()) {
                LotteryChangesReqVo next = iterator.next();
                if (StringUtils.isBlank(next.getActCode())) {
                    iterator.remove();
                }
            }
            awardPoolReqVo.setVoucherInfoReqVo(null);
        }
    }

    @RequestMapping("/jackpot/deleteJackpot")
    @ResponseBody
	@AduitLog(type = OperType.DELETE, content = "删除奖池")
    public Integer delete(String poolId) {
        int status = 0;
        AwardPoolReqVo awardPoolReqVo = new AwardPoolReqVo();
        awardPoolReqVo.setId(poolId);
        AwardCommonResult<AwardPoolResVo> deleteAwardPool = awardPoolService.deleteAwardPool(awardPoolReqVo);
        if (deleteAwardPool.isSuccess()) {
            status = 1;
        }
        return status;
    }


}

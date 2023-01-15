package com.winsmoney.jajaying.boss.domain.strategy;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.msgcenter.service.ISmsAgencyService;
import com.winsmoney.jajaying.msgcenter.service.enums.MessageType;
import com.winsmoney.jajaying.msgcenter.service.request.MessageReqVo;
import com.winsmoney.jajaying.msgcenter.service.request.ReceiverReqVo;
import com.winsmoney.jajaying.msgcenter.service.request.SmsAgencyReqVo;
import com.winsmoney.jajaying.msgcenter.service.response.MessageCommonResult;
import com.winsmoney.jajaying.msgcenter.service.response.SmsAgencyResVo;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenkai1 on 2017/8/17.
 */
@Component
public class SendBatchMsgStrateay {
    private final static String BATCH_SMS_KEY = "L02_BATCHSMS";

    @Autowired
    private ISmsAgencyService smsAgencyService;

    public int getMaxPhones() {
        SmsAgencyReqVo condition = new SmsAgencyReqVo();
        condition.setOpen(1);
        MessageCommonResult<PageInfo<SmsAgencyResVo>> agencys = smsAgencyService.list(condition, 1, 10);
        if (!agencys.isSuccess()) {
            throw new RuntimeException("取得短信服务商所能发送最大的条数异常");
        }
        List<SmsAgencyResVo> list = agencys.getBusinessObject().getList();
        if (null != list && list.size() >= 1) {
            SmsAgencyResVo smsAgencyResVo = list.get(0);
            return smsAgencyResVo.getMaxPhones();
        } else
            throw new RuntimeException("取得短信服务商所能发送最大的条数异常");
    }


    public List<List<String>> splitList(List<String> target, int size) {
        List<List<String>> listArr = new ArrayList<List<String>>();
        // 获取被拆分的数组个数
        int arrSize = target.size() % size == 0 ? target.size() / size : target.size() / size + 1;
        for (int i = 0; i < arrSize; i++) {
            List<String> sub = new ArrayList<String>();
            // 把指定索引数据放入到list中
            for (int j = i * size; j <= size * (i + 1) - 1; j++) {
                if (j <= target.size() - 1) {
                    sub.add(target.get(j));
                }
            }
            listArr.add(sub);
        }
        return listArr;
    }

    public MessageReqVo constructMobile(List<UserInfoResVo> list, String content) {
        MessageReqVo messageReqVo = new MessageReqVo();
        messageReqVo.setContent(content);
        messageReqVo.setMerchant("P2P");
        messageReqVo.setType(MessageType.SMS);
        messageReqVo.setKey(BATCH_SMS_KEY);
        List<ReceiverReqVo> receiveres = new ArrayList<ReceiverReqVo>();
        for (UserInfoResVo userInfoResVo : list) {
            ReceiverReqVo receiverReqVo = new ReceiverReqVo();
            receiverReqVo.setCellphone(userInfoResVo.getRegisterMobile());
            receiveres.add(receiverReqVo);
        }
        messageReqVo.setReceiveres(receiveres);
        return messageReqVo;
    }
}

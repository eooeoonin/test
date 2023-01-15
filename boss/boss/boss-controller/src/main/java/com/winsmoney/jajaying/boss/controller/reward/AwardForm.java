package com.winsmoney.jajaying.boss.controller.reward;

import com.winsmoney.jajaying.award.service.request.AwardPoolReqVo;
import com.winsmoney.jajaying.award.service.request.PoolMsgReqVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by chenkai1 on 2017/9/14.
 */
@Data
@NoArgsConstructor
public class AwardForm extends AwardPoolReqVo {
    List<PoolMsgReqVo> poolMsgReqVos;

}

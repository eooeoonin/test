package com.winsmoney.jajaying.boss.controller.model;

import com.winsmoney.jajaying.award.service.enums.AwardType;
import com.winsmoney.jajaying.award.service.enums.SeneCode;
import com.winsmoney.platform.framework.core.util.Money;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by fengwei on 2017/12/28.
 */
@Data
@NoArgsConstructor
public class UserAward implements Serializable {
    /**
     * 主键
     */
    private String id ;

    /**
     * 用户编号
     */
    private String userId ;

    /**
     * 请求单号
     */
    private String requestId ;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     *  收货描述
     */
    private String acceptDesc;

    /**
     * 奖励类型
     */
    private AwardType type ;

    /**
     * 奖品名称
     */
    private String name ;
    /**
     * 奖品价值
     */
    private Money value;
    /**
     * 名称描述
     */
    private String descName;
    /**
     * 价值描述
     */
    private String descValue;
    /**
     * 可用开始时间
     */
    private Date startDate ;

    /**
     * 可用结束时间
     */
    private Date endDate ;

    /**
     * 使用时间
     */
    private Date useDate ;

    /**
     * 状态
     */
    private String status ;

    /**
     * 创建时间
     */
    private Date createTime ;

    /**
     * 修改时间
     */
    private Date modifyTime ;

    /**
     * 备注
     */
    private String remark ;

    /**
     * 地址信息
     */
    private String userAddress ;

    /**
     * 项目编号
     */
    private String projectId ;

    /**
     * 操作者
     */
    private String editedBy ;

    /**
     * 领取人手机号
     */
    private String userPhone ;
    /**
     * 微信昵称
     */
    private String nickName ;

    /**
     * 领取人姓名
     */
    private String userName ;

    /**
     * 奖池编号
     */
    private String poolId;
    /**
     * 业务编码
     */
    private SeneCode seneCode;
    /**
     * 密码值
     */
    private String passValue ;
    /**
     * 密码链接地址
     */
    private String passLink;
    /**
     * 渠道编号
     */
    private String channelCode;
    /**
     * 活动编号
     */
    private String activityCode;
    /**
     * 使用场景下限
     */
    private BigDecimal useLimitMin;
    /**
     * 使用场景上限
     */
    private BigDecimal useLimitMax;
    /**
     * 使用场景天数下限
     * 加息券
     */
    private Integer useDayLimitMin;
    /**
     * 使用场景天数上限
     */
    private Integer useDayLimitMax;
    /**
     * 使用限额描述
     */
    private String useLimitDesc;
    /**
     * 查询业务特殊参数集合
     * useLimitValue 使用价值
     * useDayLimitValue 使用天数价值 加息券使用
     * seneCodes List<SeneCode> 使用场景集合
     * types  List<AwardType> 使用奖品类型
     */
    private Map<String,Object> params;
}

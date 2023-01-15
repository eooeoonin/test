package com.winsmoney.jajaying.boss.controller.reward;

import com.winsmoney.framework.pagehelper.PageInfo;
import com.winsmoney.jajaying.award.service.IUserAwardService;
import com.winsmoney.jajaying.award.service.request.UserAwardReqVo;
import com.winsmoney.jajaying.award.service.response.AwardCommonResult;
import com.winsmoney.jajaying.award.service.response.UserAwardResVo;
import com.winsmoney.jajaying.boss.controller.model.UserAward;
import com.winsmoney.jajaying.user.service.IUserInfoService;
import com.winsmoney.jajaying.user.service.enums.UserRoleType;
import com.winsmoney.jajaying.user.service.request.UserInfoReqVo;
import com.winsmoney.jajaying.user.service.response.UserCommonResult;
import com.winsmoney.jajaying.user.service.response.UserInfoResVo;
import com.winsmoney.platform.framework.core.util.BeanMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/reward/cardcoupons_list")
public class CardcouponsController {

    @Autowired
    private IUserAwardService userAwardService;
    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping("/list")
    @ResponseBody
    public PageInfo<UserAwardResVo> list(UserAward awardReqVo, int pageNo, int pageSize) {
        if (StringUtils.isBlank(awardReqVo.getUserPhone())) {
            awardReqVo.setUserPhone(null);
        }
        if (StringUtils.isBlank(awardReqVo.getProjectId())) {
            awardReqVo.setProjectId(null);
        }
        if (StringUtils.isBlank(awardReqVo.getStatus())) {
            awardReqVo.setStatus(null);
        }
        if (StringUtils.isBlank(awardReqVo.getNickName())) {
            awardReqVo.setNickName(null);
        }
        if (StringUtils.isNotBlank(awardReqVo.getNickName())) {
            UserInfoReqVo userInfoReqVo = new UserInfoReqVo();
            userInfoReqVo.setNickName( awardReqVo.getNickName() );
            UserCommonResult<UserInfoResVo> userInfo = userInfoService.getUserInfoByName(  awardReqVo.getNickName() , UserRoleType.INVEST.getCode() );
            if( userInfo.isSuccess() ){
                UserInfoResVo userInfoResVo = userInfo.getBusinessObject() ;
                if( StringUtils.isNotBlank( userInfoResVo.getId())){
                    awardReqVo.setUserId( userInfoResVo.getRoleUserId() );
                }else{
                    return new PageInfo<UserAwardResVo>();
                }
            }else{
                return new PageInfo<UserAwardResVo>();
            }
        }

        UserAwardReqVo userAwardReqVo = BeanMapper.map( awardReqVo , UserAwardReqVo.class );
        AwardCommonResult<PageInfo<UserAwardResVo>> list = userAwardService.list(userAwardReqVo, pageNo, pageSize);
        for (UserAwardResVo ua : list.getBusinessObject().getList()) {
            if (!"".equals(ua.getUserPhone()) && null != ua.getUserPhone()) {
                ua.setUserPhone(ua.getUserPhone().substring(0, 3) + "****" + ua.getUserPhone().substring(7));
            } else {
                ua.setUserPhone("----");
            }
        }
        return list.getBusinessObject();
    }


}

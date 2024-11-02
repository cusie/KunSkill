package top.cusie.service.user.converter;


import top.cusie.api.model.vo.user.UserInfoSaveReq;
import top.cusie.api.model.vo.user.UserRelationReq;
import top.cusie.api.model.vo.user.UserSaveReq;
import top.cusie.service.user.repository.entity.UserDO;
import top.cusie.service.user.repository.entity.UserInfoDO;
import top.cusie.service.user.repository.entity.UserRelationDO;

/**
 * 用户转换
 *
 * @author louzai
 * @date 2022-07-20
 */
public class UserConverter {

    public static UserDO toDO(UserSaveReq req) {
        if (req == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        userDO.setId(req.getUserId());
        userDO.setThirdAccountId(req.getThirdAccountId());
        userDO.setLoginType(req.getLoginType());
        return userDO;
    }

    public static UserInfoDO toDO(UserInfoSaveReq req) {
        if (req == null) {
            return null;
        }
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setUserId(req.getUserId());
        userInfoDO.setUserName(req.getUserName());
        userInfoDO.setPhoto(req.getPhoto());
        userInfoDO.setPosition(req.getPosition());
        userInfoDO.setCompany(req.getCompany());
        userInfoDO.setProfile(req.getProfile());
        return userInfoDO;
    }

    public static UserRelationDO toDO(UserRelationReq req) {
        if (req == null) {
            return null;
        }
        UserRelationDO userRelationDO = new UserRelationDO();
        userRelationDO.setId(req.getUserRelationId());
        userRelationDO.setUserId(req.getUserId());
        userRelationDO.setFollowUserId(req.getFollowUserId());
        userRelationDO.setFollowState(req.getFollowState());
        return userRelationDO;
    }
}

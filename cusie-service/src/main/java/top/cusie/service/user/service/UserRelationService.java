package top.cusie.service.user.service;

import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.user.UserRelationReq;
import top.cusie.service.comment.dto.UserFollowListDTO;


/**
 * 用户关系Service接口
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface UserRelationService {

    /**
     * 我关注的用户
     *
     * @param userId
     * @param pageParam
     * @return
     */
    UserFollowListDTO getUserFollowList(Long userId, PageParam pageParam);


    /**
     * 关注我的粉丝
     *
     * @param userId
     * @param pageParam
     * @return
     */
    UserFollowListDTO getUserFansList(Long userId, PageParam pageParam);


    /**
     * 保存用户关系
     * @param req
     * @throws Exception
     */
    void saveUserRelation(UserRelationReq req) throws Exception;
}

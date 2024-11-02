package top.cusie.service.user.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.cusie.api.model.vo.PageParam;
import top.cusie.service.comment.dto.UserFollowDTO;
import top.cusie.service.user.repository.entity.UserRelationDO;

import java.util.List;

/**
 * 用户关系mapper接口
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface UserRelationMapper extends BaseMapper<UserRelationDO> {

    /**
     * 我关注的用户
     * @param followUserId
     * @param pageParam
     * @return
     */
    List<UserFollowDTO> queryUserFollowList(@Param("followUserId") Long followUserId, @Param("pageParam") PageParam pageParam);

    /**
     * 关注我的粉丝
     * @param userId
     * @param pageParam
     * @return
     */
    List<UserFollowDTO> queryUserFansList(@Param("userId") Long userId, @Param("pageParam") PageParam pageParam);

    /**
     * 我关注的用户总数
     * @param followUserId
     * @param pageParam
     * @return
     */
    Integer queryUserFollowCount(@Param("followUserId") Long followUserId, @Param("pageParam") PageParam pageParam);

    /**
     * 关注我的粉丝总数
     * @param userId
     * @param pageParam
     * @return
     */
    Integer queryUserFansCount(@Param("userId") Long userId, @Param("pageParam") PageParam pageParam);
}
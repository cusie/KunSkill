package top.cusie.service.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.cusie.api.model.vo.PageParam;
import top.cusie.service.user.repository.entity.UserRelationDO;

/**
 * 用户关系Service接口
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface UserRelationService {

    /**
     * 获取关注用户列表
     * @param userId
     * @return
     */
    IPage<UserRelationDO> getUserRelationListByUserId(Integer userId, PageParam pageParam);

    /**
     * 获取被关注用户列表
     * @param followUserId
     * @return
     */
    IPage<UserRelationDO> getUserRelationListByFollowUserId(Integer followUserId, PageParam pageParam);

    /**
     * 删除用户关系
     * @param id
     */
    void deleteUserRelationById(Long id);
}

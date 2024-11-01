package top.cusie.service.user.service;


import top.cusie.service.user.repository.entity.UserDO;
import top.cusie.service.user.repository.entity.UserInfoDO;

/**
 * 用户Service接口
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface UserService {

    /**
     * 更新用户
     * @param userDTO
     */
    void updateUser(UserDO userDTO);

    /**
     * 删除用户
     * @param userInfoId
     */
    void deleteUser(Long userInfoId);

    /**
     * 更新用户信息
     * @param userInfoDTO
     */
    void updateUserInfo(UserInfoDO userInfoDTO);

    /**
     * 删除用户信息
     * @param userId
     */
    void deleteUserInfo(Long userId);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    UserInfoDO getUserInfoByUserId(Long userId);
}

package top.cusie.service.user.service;


import top.cusie.api.model.vo.user.UserInfoSaveReq;
import top.cusie.api.model.vo.user.UserSaveReq;
import top.cusie.service.user.dto.UserHomeDTO;
import top.cusie.service.user.repository.entity.UserInfoDO;

/**
 * 用户Service接口
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface UserService {

    /**
     * 保存用户
     * @param req
     * @throws Exception
     */
    void saveUser(UserSaveReq req);

    /**
     * 保存用户详情
     * @param req
     */
    void saveUserInfo(UserInfoSaveReq req);

    /**
     * 查询用户详情信息
     * @param userId
     * @return
     */
    UserInfoDO getUserInfoByUserId(Long userId);


    /**
     * 查询用户主页信息
     * @param userId
     * @return
     * @throws Exception
     */
    UserHomeDTO getUserHomeDTO(Long userId);
}

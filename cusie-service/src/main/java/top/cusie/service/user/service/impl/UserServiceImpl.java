package top.cusie.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import org.springframework.stereotype.Service;
import top.cusie.api.model.enums.YesOrNoEnum;
import top.cusie.service.user.service.UserService;
import top.cusie.service.user.repository.entity.UserDO;
import top.cusie.service.user.repository.entity.UserInfoDO;
import top.cusie.service.user.repository.mapper.UserInfoMapper;
import top.cusie.service.user.repository.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * 用户Service
 *
 * @author louzai
 * @date 2022-07-20
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public void updateUser(UserDO userDTO) {
        UserDO updateUser = userMapper.selectById(userDTO.getId());
        if (updateUser != null) {
            userMapper.updateById(userDTO);
        }
    }

    @Override
    public void deleteUser(Long userInfoId) {
        UserDO updateUser = userMapper.selectById(userInfoId);
        if (updateUser != null) {
            updateUser.setDeleted(YesOrNoEnum.YES.getCode());
            userMapper.updateById(updateUser);
        }
    }

    @Override
    public void updateUserInfo(UserInfoDO userInfoDTO) {
        UserInfoDO updateUserInfo = userInfoMapper.selectById(userInfoDTO.getId());
        if (updateUserInfo != null) {
            userInfoMapper.updateById(userInfoDTO);
        }
    }

    @Override
    public void deleteUserInfo(Long userId) {
        UserInfoDO updateUserInfo = userInfoMapper.selectById(userId);
        if (updateUserInfo != null) {
            updateUserInfo.setDeleted(YesOrNoEnum.YES.getCode());
            userInfoMapper.updateById(updateUserInfo);
        }
    }

    @Override
    public UserInfoDO getUserInfoByUserId(Long userId) {
        LambdaQueryWrapper<UserInfoDO> query = Wrappers.lambdaQuery();
        query.eq(UserInfoDO::getUserId, userId)
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode());
        return userInfoMapper.selectOne(query);
    }

}

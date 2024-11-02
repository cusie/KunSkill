package top.cusie.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import org.springframework.stereotype.Service;
import top.cusie.api.model.enums.PushStatusEnum;
import top.cusie.api.model.enums.YesOrNoEnum;
import top.cusie.api.model.vo.user.UserInfoSaveReq;
import top.cusie.api.model.vo.user.UserSaveReq;
import top.cusie.service.article.repository.entity.ArticleDO;
import top.cusie.service.article.repository.mapper.ArticleMapper;
import top.cusie.service.user.converter.UserConverter;
import top.cusie.service.user.dto.ArticleFootCountDTO;
import top.cusie.service.user.dto.UserHomeDTO;
import top.cusie.service.user.repository.mapper.UserRelationMapper;
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

    @Resource
    private UserRelationMapper userRelationMapper;

    @Resource
    private ArticleMapper articleMapper;


    @Resource
    private UserFootServiceImpl userFootService;

    @Override
    public void saveUser(UserSaveReq req) {
        if (req.getUserId() == null || req.getUserId() == 0) {
            UserDO user = UserConverter.toDO(req);
            userMapper.insert(user);
            req.setUserId(user.getId());
            return;
        }

        UserDO userDO = userMapper.selectById(req.getUserId());
        if (userDO == null) {
            throw new IllegalArgumentException("未查询到该用户");
        }
        userMapper.updateById(UserConverter.toDO(req));
    }

    @Override
    public void saveUserInfo(UserInfoSaveReq req) {
        UserInfoDO userInfoDO = getUserInfoByUserId(req.getUserId());
        if (userInfoDO == null) {
            userInfoMapper.insert(UserConverter.toDO(req));
            return;
        }

        UserInfoDO updateUserInfoDO = UserConverter.toDO(req);
        updateUserInfoDO.setId(userInfoDO.getId());
        userInfoMapper.updateById(updateUserInfoDO);
    }

    @Override
    public UserInfoDO getUserInfoByUserId(Long userId) {
        LambdaQueryWrapper<UserInfoDO> query = Wrappers.lambdaQuery();
        query.eq(UserInfoDO::getUserId, userId)
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode());
        return userInfoMapper.selectOne(query);
    }

    @Override
    public UserHomeDTO getUserHomeDTO(Long userId) {

        UserInfoDO userInfoDO = getUserInfoByUserId(userId);
        if (userInfoDO == null) {
            throw new IllegalArgumentException("用户不存在!");
        }

        // 获取关注数、粉丝数
        Integer followCount = userRelationMapper.queryUserFollowCount(userId, null);
        Integer fansCount = userRelationMapper.queryUserFansCount(userId, null);

        // 获取文章相关统计
        ArticleFootCountDTO articleFootCountDTO = userFootService.queryArticleCountByUserId(userId);

        // 获取发布文章总数
        LambdaQueryWrapper<ArticleDO> articleQuery = Wrappers.lambdaQuery();
        articleQuery.eq(ArticleDO::getUserId, userId)
                .eq(ArticleDO::getStatus, PushStatusEnum.ONLINE.getCode())
                .eq(ArticleDO::getDeleted, YesOrNoEnum.NO.getCode());
        Long articleCount = Long.valueOf(articleMapper.selectCount(articleQuery));

        UserHomeDTO userHomeDTO = new UserHomeDTO();
        userHomeDTO.setUserId(userInfoDO.getUserId());
        userHomeDTO.setRole("normal");
        userHomeDTO.setUserName(userInfoDO.getUserName());
        userHomeDTO.setPhoto(userInfoDO.getPhoto());
        userHomeDTO.setProfile(userInfoDO.getProfile());
        userHomeDTO.setFollowCount(followCount);
        userHomeDTO.setFansCount(fansCount);
        if (articleFootCountDTO != null) {
            userHomeDTO.setPraiseCount(articleFootCountDTO.getPraiseCount());
            userHomeDTO.setReadCount(articleFootCountDTO.getReadCount());
            userHomeDTO.setCollectionCount(articleFootCountDTO.getCollectionCount());
        } else {
            userHomeDTO.setPraiseCount(0);
            userHomeDTO.setReadCount(0);
            userHomeDTO.setCollectionCount(0);
        }
        userHomeDTO.setArticleCount(articleCount.intValue());
        return userHomeDTO;
    }

}

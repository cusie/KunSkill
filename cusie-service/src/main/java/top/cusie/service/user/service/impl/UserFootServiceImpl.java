package top.cusie.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import top.cusie.api.model.enums.CollectionStatEnum;
import top.cusie.api.model.enums.CommentStatEnum;
import top.cusie.api.model.enums.PraiseStatEnum;
import top.cusie.api.model.enums.ReadStatEnum;
import top.cusie.service.user.repository.entity.UserFootDO;
import top.cusie.service.user.repository.mapper.UserFootMapper;
import top.cusie.service.user.service.UserFootService;

import javax.annotation.Resource;

/**
 * 用户足迹Service
 *
 * @author louzai
 * @date 2022-07-20
 */
@Service
public class UserFootServiceImpl implements UserFootService {

    @Resource
    private UserFootMapper userFootMapper;

    @Override
    public Integer queryCollentionCount(Long documentId) {
        LambdaQueryWrapper<UserFootDO> query = Wrappers.lambdaQuery();
        query.eq(UserFootDO::getDoucumentId, documentId)
                .eq(UserFootDO::getCollectionStat, CollectionStatEnum.COLLECTION.getCode());
        return userFootMapper.selectCount(query);
    }

    @Override
    public Integer queryReadCount(Long documentId) {
        LambdaQueryWrapper<UserFootDO> query = Wrappers.lambdaQuery();
        query.eq(UserFootDO::getDoucumentId, documentId)
                .eq(UserFootDO::getReadStat, ReadStatEnum.READ.getCode());
        return userFootMapper.selectCount(query);
    }

    @Override
    public Integer queryCommentCount(Long documentId) {
        LambdaQueryWrapper<UserFootDO> query = Wrappers.lambdaQuery();
        query.eq(UserFootDO::getDoucumentId, documentId)
                .eq(UserFootDO::getCommentStat, CommentStatEnum.COMMENT.getCode());
        return userFootMapper.selectCount(query);
    }

    @Override
    public Integer queryPraiseCount(Long documentId) {
        LambdaQueryWrapper<UserFootDO> query = Wrappers.lambdaQuery();
        query.eq(UserFootDO::getDoucumentId, documentId)
                .eq(UserFootDO::getPraiseStat, PraiseStatEnum.PRAISE.getCode());
        return userFootMapper.selectCount(query);
    }

    @Override
    public Integer operateCollectionFoot(Long documentId, Long userId, CollectionStatEnum statEnum) {
        LambdaQueryWrapper<UserFootDO> query = Wrappers.lambdaQuery();
        query.eq(UserFootDO::getDoucumentId, documentId)
                .eq(UserFootDO::getUserId, userId);
        UserFootDO userFootDTO = userFootMapper.selectOne(query);
        userFootDTO.setCollectionStat(statEnum.getCode());
        return userFootMapper.update(userFootDTO, query);
    }

    @Override
    public Integer operateCommentFoot(Long documentId, Long userId, CommentStatEnum statEnum) {
        LambdaQueryWrapper<UserFootDO> query = Wrappers.lambdaQuery();
        query.eq(UserFootDO::getDoucumentId, documentId)
                .eq(UserFootDO::getUserId, userId);
        UserFootDO userFootDTO = userFootMapper.selectOne(query);
        userFootDTO.setCommentStat(statEnum.getCode());
        return userFootMapper.update(userFootDTO, query);
    }

    @Override
    public Integer operatePraiseFoot(Long documentId, Long userId, PraiseStatEnum statEnum) {
        LambdaQueryWrapper<UserFootDO> query = Wrappers.lambdaQuery();
        query.eq(UserFootDO::getDoucumentId, documentId)
                .eq(UserFootDO::getUserId, userId);
        UserFootDO userFootDTO = userFootMapper.selectOne(query);
        userFootDTO.setPraiseStat(statEnum.getCode());
        return userFootMapper.update(userFootDTO, query);
    }
}

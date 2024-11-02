package top.cusie.service.user.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.user.UserRelationReq;
import top.cusie.service.comment.dto.UserFollowDTO;
import top.cusie.service.comment.dto.UserFollowListDTO;
import top.cusie.service.user.converter.UserConverter;
import top.cusie.service.user.repository.entity.UserRelationDO;
import top.cusie.service.user.repository.mapper.UserRelationMapper;
import top.cusie.service.user.service.UserRelationService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户关系Service
 *
 * @author louzai
 * @date 2022-07-20
 */
@Service
public class UserRelationServiceImpl implements UserRelationService {

    @Resource
    private UserRelationMapper userRelationMapper;

    @Override
    public UserFollowListDTO getUserFollowList(Long userId, PageParam pageParam) {

        UserFollowListDTO userFollowListDTO = new UserFollowListDTO();
        List<UserFollowDTO> userRelationList = userRelationMapper.queryUserFollowList(userId, pageParam);
        if (userRelationList.isEmpty())  {
            return userFollowListDTO;
        }

        Boolean isMore = (userRelationList.size() == pageParam.getPageSize()) ? Boolean.TRUE : Boolean.FALSE;

        userFollowListDTO.setUserFollowList(userRelationList);
        userFollowListDTO.setIsMore(isMore);
        return userFollowListDTO;
    }

    @Override
    public UserFollowListDTO getUserFansList(Long userId, PageParam pageParam) {

        UserFollowListDTO userFollowListDTO = new UserFollowListDTO();
        List<UserFollowDTO> userRelationList = userRelationMapper.queryUserFansList(userId, pageParam);
        if (userRelationList.isEmpty())  {
            return userFollowListDTO;
        }

        Boolean isMore = (userRelationList.size() == pageParam.getPageSize()) ? Boolean.TRUE : Boolean.FALSE;

        userFollowListDTO.setUserFollowList(userRelationList);
        userFollowListDTO.setIsMore(isMore);
        return userFollowListDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRelation(UserRelationReq req) throws Exception {
        if (req.getUserRelationId() == null || req.getUserRelationId() == 0) {
            userRelationMapper.insert(UserConverter.toDO(req));
            return;
        }

        UserRelationDO userRelationDO = userRelationMapper.selectById(req.getUserRelationId());
        if (userRelationDO == null) {
            throw new Exception("未查询到该用户关系");
        }
        userRelationMapper.updateById(UserConverter.toDO(req));
    }
}

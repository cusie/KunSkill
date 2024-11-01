package top.cusie.service.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import top.cusie.api.model.enums.PraiseStatEnum;
import top.cusie.api.model.enums.YesOrNoEnum;
import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.comment.CommentReq;
import top.cusie.service.comment.repository.entity.CommentDO;
import top.cusie.service.comment.repository.mapper.CommentMapper;
import top.cusie.service.comment.service.CommentService;
import top.cusie.service.comment.converter.CommentConverter;
import top.cusie.service.comment.dto.CommentTreeDTO;
import top.cusie.service.user.repository.entity.UserFootDO;
import top.cusie.service.user.repository.entity.UserInfoDO;
import top.cusie.service.user.repository.mapper.UserFootMapper;
import top.cusie.service.user.repository.mapper.UserInfoMapper;

import javax.annotation.Resource;
import java.util.*;

/**
 * 评论Service
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CommentConverter commentConverter;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserFootMapper userFootMapper;

    @Override
    public Map<Long, CommentTreeDTO> getCommentList(Long articleId, PageParam pageSearchReq) {

        // 1. 获取当前分页的评论
        PageParam pageParam = PageParam.newPageInstance(pageSearchReq.getPageNum(), pageSearchReq.getPageSize());
        List<CommentDO> commentFirstLevelList = getCommentList(pageParam, 0L);
        if (commentFirstLevelList.isEmpty()) {
            return new HashMap<>();
        }
        Map<Long, CommentDO> commentFirstLevelMap = new HashMap<>();
        commentFirstLevelList.forEach(commentDO -> commentFirstLevelMap.put(commentDO.getId(), commentDO));

        // 2. 获取所有评论，并过滤 1 级评论，且不在分页中的数据
        pageParam = PageParam.newPageInstance(1L, Long.MAX_VALUE);
        List<CommentDO> commentAllList = getCommentList(pageParam, null);
        if (commentAllList.isEmpty()) {
            return new HashMap<>();
        }

        List<CommentDO> commentBasicList = new ArrayList<>();
        for (CommentDO commentDO : commentAllList) {
            if (commentDO.getParentCommentId() == 0 && !commentFirstLevelMap.containsKey(commentDO.getId())) {
                continue;
            }
            commentBasicList.add(commentDO);
        }

        // 3. 组建一棵树
        Map<Long, CommentTreeDTO> deptTreeMap = new HashMap<>();
        commentBasicList.forEach(commentDO -> deptTreeMap.put(commentDO.getId(), commentConverter.toDTO(commentDO)));
        Set<Long> commentIdSet = new HashSet<>(deptTreeMap.keySet());
        Map<Long, CommentTreeDTO> commentTreeMap = getCommentTree(deptTreeMap, commentIdSet, 0L);

        // 4. 填充每一个节点的信息
        fillCommentTree(commentTreeMap);
        return commentTreeMap;
    }

    @Override
    public void saveComment(CommentReq commentReq) throws Exception {
        if (commentReq.getCommentId() == null || commentReq.getCommentId() == 0) {
            commentMapper.insert(commentConverter.toDo(commentReq));
            return;
        }

        CommentDO commentDO = commentMapper.selectById(commentReq.getCommentId());
        if (commentDO == null) {
            throw new Exception("未查询到该评论");
        }
        commentMapper.updateById(commentConverter.toDo(commentReq));
    }

    @Override
    public void deleteComment(Long commentId) throws Exception {
        CommentDO commentDO = commentMapper.selectById(commentId);
        if (commentDO == null) {
            throw new Exception("未查询到该评论");
        }
        commentMapper.deleteById(commentId);
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    private UserInfoDO getUserInfoByUserId(Long userId) {
        LambdaQueryWrapper<UserInfoDO> query = Wrappers.lambdaQuery();
        query.eq(UserInfoDO::getUserId, userId)
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode());
        return userInfoMapper.selectOne(query);
    }

    /**
     * 获取评论点赞数量
     *
     * @param documentId
     * @return
     */
    public Long queryPraiseCount(Long documentId) {
        LambdaQueryWrapper<UserFootDO> query = Wrappers.lambdaQuery();
        query.eq(UserFootDO::getDoucumentId, documentId)
                .eq(UserFootDO::getPraiseStat, PraiseStatEnum.PRAISE.getCode());
        return Long.valueOf(userFootMapper.selectCount(query));
    }

    /**
     * 获取评论列表
     *
     * @param pageParam
     * @param parentCommentId
     * @return
     */
    private List<CommentDO> getCommentList(PageParam pageParam, Long parentCommentId) {
        QueryWrapper<CommentDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(parentCommentId != null, CommentDO::getParentCommentId, parentCommentId)
                .last(PageParam.getLimitSql(pageParam))
                .orderByDesc(CommentDO::getId);
        return commentMapper.selectList(queryWrapper);
    }

    /**
     * 将评论构建成一棵树
     * 功能说明：将评论列表，构建成一颗树，如果列表中数据有多余的，可以直接剔除
     *
     * @param commentMap    需要构件树的评论列表
     * @param notDeleteSet  未删除标记
     * @param rootCommentId 根节点的ID
     * @return 一棵树
     */
    private Map<Long, CommentTreeDTO> getCommentTree(Map<Long, CommentTreeDTO> commentMap, Set<Long> notDeleteSet, Long rootCommentId) {
        Map<Long, CommentTreeDTO> commentTree = new HashMap<>();
        for (Map.Entry<Long, CommentTreeDTO> entry : commentMap.entrySet()) {
            Long commentId = entry.getKey();
            CommentTreeDTO commentInfo = entry.getValue();
            // 如果已经删除，直接跳过
            if (!notDeleteSet.contains(commentId)) {
                continue;
            }
            // 找到根节点的一个孩子，获取这个孩子节点的树
            if (commentInfo.getParentCommentId().equals(rootCommentId)) {
                // 删除已经处理过的节点
                notDeleteSet.remove(commentId);
                // 构造该节点的孩子节点树
                Map<Long, CommentTreeDTO> childrenCommentTree = this.getCommentTree(commentMap, notDeleteSet, commentId);
                if (childrenCommentTree.size() > 0) {
                    commentInfo.setCommentChilds(childrenCommentTree);
                }
                // 添加一颗子树
                commentTree.put(commentId, commentInfo);
            }
        }
        return commentTree;
    }

    /**
     * 填充评论的基础数据
     *
     * @param commentTreeMap
     */
    private void fillCommentTree(Map<Long, CommentTreeDTO> commentTreeMap) {
        for (Map.Entry<Long, CommentTreeDTO> entry : commentTreeMap.entrySet()) {
            CommentTreeDTO commentInfo = entry.getValue();
            UserInfoDO userInfoDO = getUserInfoByUserId(commentInfo.getUserId());
            if (userInfoDO == null) {
                // 如果用户注销，给一个默认的用户
                commentInfo.setUserName("默认用户"); // TODO: 后续再优化
                commentInfo.setUserPhoto("");
                commentInfo.setCommentCount(0);
                commentInfo.setCommentChilds(new HashMap<>());
                continue;
            }
            commentInfo.setUserName(userInfoDO.getUserName());
            commentInfo.setUserPhoto(userInfoDO.getPhoto());
            commentInfo.setCommentCount(commentInfo.getCommentChilds().size());

            Long praistCount = queryPraiseCount(userInfoDO.getId());
            commentInfo.setPraiseCount(praistCount.intValue());
            fillCommentTree(commentInfo.getCommentChilds());
        }
    }
}

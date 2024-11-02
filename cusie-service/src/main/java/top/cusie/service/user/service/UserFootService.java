package top.cusie.service.user.service;


import top.cusie.api.model.enums.OperateTypeEnum;
import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.comment.CommentSaveReq;
import top.cusie.service.article.repository.entity.ArticleDO;
import top.cusie.service.comment.repository.entity.CommentDO;
import top.cusie.service.user.dto.ArticleFootCountDTO;

import java.util.List;

/**
 * 用户足迹Service接口
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface UserFootService {

    /**
     * 保存文章计数
     * @param articleId
     * @param userId
     * @param operateTypeEnum
     * @return
     */
    ArticleFootCountDTO saveArticleFoot(Long articleId, Long userId, OperateTypeEnum operateTypeEnum);

    /**
     * 根据文章ID查询文章计数
     *
     * @param articleId
     * @return
     */
    ArticleFootCountDTO queryArticleCountByArticleId(Long articleId);


    /**
     * 根据用户ID查询文章计数
     *
     * @param userId
     * @return
     */
    ArticleFootCountDTO queryArticleCountByUserId(Long userId);

    /**
     * 获取评论点赞数量
     *
     * @param commentId
     * @return
     */
    Long queryCommentPraiseCount(Long commentId);

    /**
     * 查询已读文章列表
     *
     * @param userId
     * @param pageParam
     * @return
     */
    List<ArticleDO> queryReadArticleList(Long userId, PageParam pageParam);

    /**
     * 查询收藏文章列表
     *
     * @param userId
     * @param pageParam
     * @return
     */
    List<ArticleDO> queryCollectionArticleList(Long userId, PageParam pageParam);

    /**
     * 保存评论足迹
     *
     * @param commentSaveReq  保存评论入参
     * @param commentId  评论ID
     * @param articleUserId  发版文章的用户ID
     */
    void saveCommentFoot(CommentSaveReq commentSaveReq, Long commentId, Long articleUserId);

    /**
     * 删除评论足迹
     *
     * @param commentDO
     * @throws Exception
     */
    void deleteCommentFoot(CommentDO commentDO) throws Exception;
}

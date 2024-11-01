package top.cusie.service.user.service;


import top.cusie.api.model.enums.CollectionStatEnum;
import top.cusie.api.model.enums.CommentStatEnum;
import top.cusie.api.model.enums.PraiseStatEnum;

/**
 * 用户足迹Service接口
 *
 * @author Cusie
 * @date 2024/10/31
 */
public interface UserFootService {

    /**
     * 文章收藏数
     *
     * @param documentId
     * @return
     */
    Integer queryCollentionCount(Long documentId);

    /**
     * 文章阅读数
     *
     * @param documentId
     * @return
     */
    Integer queryReadCount(Long documentId);

    /**
     * 文章评论数
     *
     * @param documentId
     * @return
     */
    Integer queryCommentCount(Long documentId);

    /**
     * 文章点赞数
     *
     * @param documentId
     * @return
     */
    Integer queryPraiseCount(Long documentId);

    /**
     * 收藏/取消收藏足迹
     * @param documentId
     * @param userId
     * @return
     */
    Integer operateCollectionFoot(Long documentId, Long userId, CollectionStatEnum statEnum);

    /**
     * 评论/删除评论足迹
     * @param documentId
     * @param userId
     * @return
     */
    Integer operateCommentFoot(Long documentId, Long userId, CommentStatEnum statEnum);

    /**
     * 点赞/取消点赞足迹
     * @param documentId
     * @param userId
     * @return
     */
    Integer operatePraiseFoot(Long documentId, Long userId, PraiseStatEnum statEnum);
}

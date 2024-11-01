package top.cusie.api.model.vo.comment;

import lombok.Data;

/**
 * 评论列表入参
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Data
public class CommentReq {

    /**
     * 评论ID
     */
    private Long commentId;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 父评论ID
     */
    private Long parentCommentId;
}

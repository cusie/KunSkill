package top.cusie.service.comment.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.cusie.api.model.entity.BaseDO;

/**
 * 评论表
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("comment")
public class CommentDO extends BaseDO {
    private static final long serialVersionUID = 1L;

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
    private String content;

    /**
     * 父评论ID
     */
    private Long parentCommentId;
}

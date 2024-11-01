package top.cusie.service.user.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.cusie.api.model.entity.BaseDO;

/**
 * 用户足迹表
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_foot")
public class UserFootDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 文档ID（文章/评论）
     */
    private Long doucumentId;

    /**
     * 文档类型：1-文章，2-评论
     */
    private Integer doucumentType;

    /**
     * 收藏状态: 0-未收藏，1-已收藏，2-取消收藏
     */
    private Integer collectionStat;

    /**
     * 阅读状态: 0-未读，1-已读
     */
    private Integer readStat;

    /**
     * 评论状态: 0-未评论，1-已评论，2-删除评论
     */
    private Integer commentStat;

    /**
     * 点赞状态: 0-未点赞，1-已点赞，2-取消点赞
     */
    private Integer praiseStat;
}

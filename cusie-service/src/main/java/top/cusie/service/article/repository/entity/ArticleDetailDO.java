package top.cusie.service.article.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.cusie.api.model.entity.BaseDO;

/**
 * 文章详情
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article_detail")
public class ArticleDetailDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 文章内容
     */
    private String content;

    private Integer deleted;
}

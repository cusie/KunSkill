package top.cusie.service.article.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.cusie.api.model.entity.BaseDO;

/**
 * 文章标签映射表
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article_tag")
public class ArticleTagDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 标签id
     */
    private Long tagId;

    private Integer deleted;
}

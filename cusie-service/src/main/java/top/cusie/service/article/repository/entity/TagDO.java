package top.cusie.service.article.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.cusie.api.model.entity.BaseDO;

/**
 * 标签管理表
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tag")
public class TagDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签类型：1-系统标签，2-自定义标签
     */
    private Integer tagType;

    /**
     * 类目ID
     */
    private Long categoryId;

    /**
     * 状态：0-未发布，1-已发布
     */
    private Integer status;

    private Integer deleted;
}

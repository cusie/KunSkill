package top.cusie.service.user.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.cusie.api.model.entity.BaseDO;

/**
 * 用户关系表
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_relation")
public class UserRelationDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 关注用户ID
     */
    private Long followUserId;

    private Integer deleted;
}

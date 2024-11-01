package top.cusie.service.user.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.cusie.api.model.entity.BaseDO;

/**
 * 用户登录表
 *
 * @author Cusie
 * @date 2024/10/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class UserDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 第三方用户ID
     */
    private String thirdAccountId;

    /**
     * 登录方式: 0-微信登录，1-账号密码登录
     */
    private Integer loginType;

    private Integer deleted;
}

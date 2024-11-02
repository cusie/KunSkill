package top.cusie.api.model.vo.user;

import lombok.Data;

/**
 * 用户入参
 *
 * @author Cusie
 * @date 2024/11/1
 */
@Data
public class UserSaveReq {
    /**
     * 主键ID
     */
    private Long userId;

    /**
     * 第三方用户ID
     */
    private String thirdAccountId;

    /**
     * 登录方式: 0-微信登录，1-账号密码登录
     */
    private Integer loginType;
}

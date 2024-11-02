package top.cusie.service.comment.dto;

import lombok.Data;

/**
 * 关注用户
 *
 * @author Cusie
 * @date 2024/11/2
 */
@Data
public class UserFollowDTO {

    /**
     * 关系ID
     */
    private Long userRelationId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户图像
     */
    private String photo;

    /**
     * 个人简介
     */
    private String profile;
}

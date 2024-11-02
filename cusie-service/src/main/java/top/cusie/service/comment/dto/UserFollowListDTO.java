package top.cusie.service.comment.dto;

import lombok.Data;

import java.util.List;

/**
 * 关注用户
 *
 * @author Cusie
 * @date 2024/11/2
 */
@Data
public class UserFollowListDTO {
    /**
     * 用户列表
     */
    List<UserFollowDTO> userFollowList;

    /**
     * 是否有更多
     */
    private Boolean isMore;
}

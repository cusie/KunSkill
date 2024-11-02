package top.cusie.test.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.cusie.api.model.enums.FollowStateEnum;
import top.cusie.api.model.vo.PageParam;
import top.cusie.api.model.vo.user.UserRelationReq;
import top.cusie.service.comment.dto.UserFollowListDTO;
import top.cusie.service.user.service.UserRelationService;
import top.cusie.service.user.service.UserService;
import top.cusie.test.BasicTest;

/**
 * @author YiHui
 * @date 2022/7/20
 */
@Slf4j
public class UserRelationDaoTest extends BasicTest {

    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private UserService userService;

    @Test
    public void saveUserRelation() throws Exception {

        UserRelationReq req1 = new UserRelationReq();
        req1.setUserId(1L);
        req1.setFollowUserId(2L);
        req1.setFollowState(FollowStateEnum.FOLLOW.getCode());
        userRelationService.saveUserRelation(req1);

        UserRelationReq req2 = new UserRelationReq();
        req2.setUserId(1L);
        req2.setFollowUserId(3L);
        req2.setFollowState(FollowStateEnum.FOLLOW.getCode());
        userRelationService.saveUserRelation(req2);

        UserRelationReq req3 = new UserRelationReq();
        req3.setUserId(2L);
        req3.setFollowUserId(1L);
        req3.setFollowState(FollowStateEnum.FOLLOW.getCode());
        userRelationService.saveUserRelation(req3);
    }

    @Test
    public void testCancelUserRelation() throws Exception {
        UserRelationReq req = new UserRelationReq();
        req.setUserRelationId(7L);
        req.setFollowState(FollowStateEnum.CANCEL_FOLLOW.getCode());
        userRelationService.saveUserRelation(req);
    }

    @Test
    public void testUserRelation() {
        UserFollowListDTO userFollowListDTO = userRelationService.getUserFollowList(1L, PageParam.newPageInstance(1L, 10L));
        log.info("query userFollowDTOS: {}", userFollowListDTO);

        UserFollowListDTO userFansListDTO = userRelationService.getUserFansList(1L, PageParam.newPageInstance(1L, 10L));
        log.info("query userFansList: {}", userFansListDTO);
    }
}

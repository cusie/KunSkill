package top.cusie.test.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.cusie.service.user.dto.UserHomeDTO;
import top.cusie.service.user.service.UserService;
import top.cusie.test.BasicTest;

/**
 * @author YiHui
 * @date 2022/7/20
 */
@Slf4j
public class UserDaoTest extends BasicTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUserHome() throws Exception {
        UserHomeDTO userHomeDTO = userService.getUserHomeDTO(1L);
        log.info("query userPageDTO: {}", userHomeDTO);
    }
}

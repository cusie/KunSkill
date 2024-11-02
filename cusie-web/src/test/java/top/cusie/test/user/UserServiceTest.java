package top.cusie.test.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.cusie.api.model.vo.user.UserInfoSaveReq;
import top.cusie.api.model.vo.user.UserSaveReq;
import top.cusie.service.user.service.UserService;
import top.cusie.test.BasicTest;

import java.util.UUID;

/**
 * @author YiHui
 * @date 2022/8/6
 */
public class UserServiceTest extends BasicTest {

    @Autowired
    private UserService userService;

    /**
     * 注册一个用户
     */
    @Test
    public void testRegister() {
        UserSaveReq req = new UserSaveReq();
        req.setThirdAccountId(UUID.randomUUID().toString());
        req.setLoginType(0);
        userService.saveUser(req);
        long userId = req.getUserId();

        UserInfoSaveReq save = new UserInfoSaveReq();
        save.setUserId(userId);
        save.setUserName("cusie");
        save.setPhoto("https://spring.hhui.top/spring-blog/css/images/avatar.jpg");
        save.setCompany("xm");
        save.setPosition("java");
        save.setProfile("码农");
        userService.saveUserInfo(save);
    }

}

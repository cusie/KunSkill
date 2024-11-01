package top.cusie.web.hook.interceptor;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.cusie.web.config.GlobalViewConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author Cusie
 * @date 2024/10/31
 */

@Slf4j
@Component
/**
 * 全局视图拦截器，用于在请求处理前后执行自定义逻辑
 * 主要功能包括向模型中添加全局视图数据，如站点信息、用户登录状态和用户信息等
 */
public class GlobalViewInterceptor implements AsyncHandlerInterceptor {
    @Resource
    private GlobalViewConfig globalViewConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 预处理请求，返回true表示请求继续执行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 重定向请求不需要添加
        if (!ObjectUtils.isEmpty(modelAndView)) {
            // 向模型中添加全局视图数据
            modelAndView.getModel().put("siteInfo", globalViewConfig);
            modelAndView.getModel().put("isLogin", true);
            // 构建用户信息并添加到模型中
            UserInfo userInfo = new UserInfo().setUid(1L).setUname("cusie")
                    .setAvatar("https://jsd.onmicrosoft.cn/gh/cusie/image/unnamed.jpg")
                    .setNewMsgList(Arrays.asList(new UserMsg().setMsgId(100L).setMsgType(1).setMsg("模拟通知消息")));
            modelAndView.getModel().put("user", userInfo);
        }
    }

    /**
     * 用户信息类，包含用户的基本信息和新消息列表
     */
    @Data
    @Accessors(chain = true)
    private static class UserInfo {
        private Long uid;
        private String uname;
        private String avatar;
        private String role;
        private List<UserMsg> newMsgList;
    }

    /**
     * 用户消息类，表示用户接收到的消息
     */
    @Data
    @Accessors(chain = true)
    private static class UserMsg {
        private long msgId;
        private int msgType;
        private String msg;
    }
}


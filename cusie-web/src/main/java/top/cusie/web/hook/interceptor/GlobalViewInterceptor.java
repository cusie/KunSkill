package top.cusie.web.hook.interceptor;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.cusie.api.model.context.ReqInfoContext;
import top.cusie.service.user.dto.UserHomeDTO;
import top.cusie.service.user.service.UserService;
import top.cusie.web.config.GlobalViewConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 注入全局的配置信息：
 * - thymleaf 站点信息，基本信息，在这里注入
 *
 * @author Cusie
 * @date 2024/10/31
 */

@Slf4j
@Component
public class GlobalViewInterceptor implements AsyncHandlerInterceptor {
    @Resource
    private GlobalViewConfig globalViewConfig;
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 重定向请求不需要添加
        if (!ObjectUtils.isEmpty(modelAndView)) {
            modelAndView.getModel().put("siteInfo", globalViewConfig);
            Long userId = ReqInfoContext.getReqInfo().getUserId();
            if (userId == null) {
                return;
            }

            // 用户信息
            UserHomeDTO user = userService.getUserHomeDTO(userId);
            if (user != null) {
                modelAndView.getModel().put("isLogin", true);
                modelAndView.getModel().put("user", user);
            } else {
                modelAndView.getModel().put("isLogin", false);
            }

            // 消息数 fixme 消息信息改由消息模块处理
            modelAndView.getModel().put("msgs", Arrays.asList(new UserMsg().setMsgId(100L).setMsgType(1).setMsg("模拟通知消息")));
        }
    }

    @Data
    @Accessors(chain = true)
    private static class UserMsg {
        private long msgId;
        private int msgType;
        private String msg;
    }
}


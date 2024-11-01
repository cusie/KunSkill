package top.cusie.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.cusie.web.hook.interceptor.GlobalViewInterceptor;

import javax.annotation.Resource;

/**
 * 入口
 *
 * @author Cusie
 * @date 2024/10/30
 */
@ServletComponentScan
@SpringBootApplication
public class QuickSkillApplication implements WebMvcConfigurer {

    // 使用Resource注解注入GlobalViewInterceptor实例
    @Resource
    private GlobalViewInterceptor globalViewInterceptor;

    /**
     * 配置拦截器
     *
     * 此方法用于向Spring MVC的InterceptorRegistry中添加拦截器
     * 它重写了父类中的方法，以便于自定义拦截逻辑
     *
     * @param registry InterceptorRegistry实例，用于注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册全局视图拦截器，应用于所有请求路径
        registry.addInterceptor(globalViewInterceptor).addPathPatterns("/**");
    }

    public static void main(String[] args) {
        SpringApplication.run(QuickSkillApplication.class, args);
    }
}

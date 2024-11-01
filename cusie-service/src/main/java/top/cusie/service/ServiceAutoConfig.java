package top.cusie.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Cusie
 * @date 2024/10/30
 */
@Configuration
@ComponentScan("top.cusie.service")
@MapperScan(basePackages = {
        "top.cusie.service.article.repository.mapper",
        "top.cusie.service.user.repository.mapper",
        "top.cusie.service.comment.repository.mapper",})
public class ServiceAutoConfig {
}

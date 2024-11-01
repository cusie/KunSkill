package top.cusie.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Cusie
 * @date 2024/10/30
 */
@Data
@ConfigurationProperties(prefix = "view.site")
@Component
public class GlobalViewConfig {

    private String cdnImgStyle;

    private String websiteRecord;

    private Integer pageSize;

    private String websiteName;

    private String websiteLogoUrl;

    private String websiteFaviconIconUrl;

    private String contactMeWxQrCode;

    private String contactMeTitle;

}

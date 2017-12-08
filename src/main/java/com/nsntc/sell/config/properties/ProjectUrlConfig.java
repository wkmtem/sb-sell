package com.nsntc.sell.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Class Name: ProjectUrlConfig
 * Package: com.nsntc.sell.config.properties
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/8 下午10:58
 * Version: 1.0
 */
@Data
@Component
@PropertySource(value = { "classpath:properties/projectUrl.properties" }, ignoreResourceNotFound = true)
public class ProjectUrlConfig {

    /**
     * 微信公众平台授权url
     */
    @Value("${wechat.mpAuthorize.url}")
    public String wechatMpAuthorize;

    /**
     * 微信开放平台授权url
     */
    @Value("${wechat.openAuthorize.url}")
    public String wechatOpenAuthorize;

    /**
     * 本项目系统
     */
    @Value("${project.base.url}")
    public String baseUrl;
}

package com.nsntc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class Name: OtherConfig
 * Package: com.nsntc.sell.config
 * Description: 读取yml属性
 * @author wkm
 * Create DateTime: 2017/12/8 上午12:58
 * Version: 1.0
 */
@Component
@Data
@ConfigurationProperties(prefix = "other")
public class OtherConfig {

    private Integer aaa;
    private Integer bbb;
    private String ccc;

}

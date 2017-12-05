package com.nsntc.sell.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class Name: OtherProperties
 * Package: com.nsntc.sell.config.properties
 * Description: 读取yml属性
 * @author wkm
 * Create DateTime: 2017/12/4 下午7:14
 * Version: 1.0
 */
@Component
@Data
@ConfigurationProperties(prefix = "other")
public class OtherProperties {

    private Integer aaa;
    private Integer bbb;
    private String ccc;

}

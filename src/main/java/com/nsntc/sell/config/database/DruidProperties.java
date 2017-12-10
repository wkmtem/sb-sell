package com.nsntc.sell.config.database;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/** 此绑定方式，底层调用Setter方法 */
@Data
@Component
@SpringBootConfiguration
/** ignoreUnknownFields = false 忽略未知字段: false则抛异常 */
@ConfigurationProperties(prefix = "druid", ignoreUnknownFields = false)
/** 只可加载proprties文件 */
@PropertySource(value = { "classpath:properties/druid.properties" }, encoding = "UTF-8")
public class DruidProperties {

    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private Boolean testWhileIdle;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private Boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private String connectionProperties;
    private Boolean removeAbandoned;
    private int removeAbandonedTimeout;
}

package com.nsntc.sell.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Class Name: DruidDBConfig
 * Package: com.nsntc.sell.config.database
 * Description: 由于Druid暂时不在Spring Bootz中的直接支持，故需要进行配置信息的定制
 * @author wkm
 * Create DateTime: 2017/12/1 下午10:16
 * Version: 1.0
 */
@SpringBootConfiguration
/** 只可加载proprties文件 */
@PropertySource(value = { "classpath:properties/druid.properties" }, ignoreResourceNotFound = true)
@Slf4j
public class DruidDBConfig {

    /** 获取对象属性 */
    //@Value("#{druidDBConfig.driverClassName}")

    /** yml配置 */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    /** properties配置 */
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.maxWait}")
    private int maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;
    @Value("${spring.datasource.removeAbandoned}")
    private boolean removeAbandoned;
    @Value("${spring.datasource.removeAbandonedTimeout}")
    private int removeAbandonedTimeout;

    /** Spring容器管理: name即bean的id, 默认方法名; 指定, 方法名被忽略。bean的名称和别名也可以通过value指定 */
    @Bean(name = "dataSource")
    /** 在相同的DataSource中，首先使用被标注@Primary的DataSource,这里定义的DataSource将覆盖其他来源的DataSource */
    @Primary
    public DataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setUrl(this.dbUrl);
        druidDataSource.setUsername(this.username);
        druidDataSource.setPassword(this.password);
        druidDataSource.setDriverClassName(this.driverClassName);

        /** configuration */
        druidDataSource.setInitialSize(this.initialSize);
        druidDataSource.setMinIdle(this.minIdle);
        druidDataSource.setMaxActive(this.maxActive);
        druidDataSource.setMaxWait(this.maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(this.validationQuery);
        druidDataSource.setTestWhileIdle(this.testWhileIdle);
        druidDataSource.setTestOnBorrow(this.testOnBorrow);
        druidDataSource.setTestOnReturn(this.testOnReturn);
        druidDataSource.setPoolPreparedStatements(this.poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize);
        druidDataSource.setRemoveAbandoned(this.removeAbandoned);
        druidDataSource.setRemoveAbandonedTimeout(this.removeAbandonedTimeout);
        try {
            druidDataSource.setFilters(this.filters);
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
        druidDataSource.setConnectionProperties(this.connectionProperties);
        return druidDataSource;
    }
}
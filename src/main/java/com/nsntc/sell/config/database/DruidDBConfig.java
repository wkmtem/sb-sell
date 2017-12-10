package com.nsntc.sell.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

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
@Slf4j
public class DruidDBConfig {

    @Autowired
    private DruidProperties druidProperties;

    /** Spring容器管理: name即bean的id, 默认方法名; 指定, 方法名被忽略。bean的名称和别名也可以通过value指定 */
    @Bean(name = "dataSource")
    /** 在相同的DataSource中，首先使用被标注@Primary的DataSource,这里定义的DataSource将覆盖其他来源的DataSource */
    @Primary
    public DataSource dataSource(){

        DruidDataSource druidDataSource = new DruidDataSource();

        /** database info */
        druidDataSource.setUrl(this.druidProperties.getDbUrl());
        druidDataSource.setUsername(this.druidProperties.getUsername());
        druidDataSource.setPassword(this.druidProperties.getPassword());
        druidDataSource.setDriverClassName(this.druidProperties.getDriverClassName());

        /** configuration */
        druidDataSource.setInitialSize(this.druidProperties.getInitialSize());
        druidDataSource.setMinIdle(this.druidProperties.getMinIdle());
        druidDataSource.setMaxActive(this.druidProperties.getMaxActive());
        druidDataSource.setMaxWait(this.druidProperties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(this.druidProperties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(this.druidProperties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(this.druidProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(this.druidProperties.getTestWhileIdle());
        druidDataSource.setTestOnBorrow(this.druidProperties.getTestOnBorrow());
        druidDataSource.setTestOnReturn(this.druidProperties.getTestOnReturn());
        druidDataSource.setPoolPreparedStatements(this.druidProperties.getPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(this.druidProperties.getMaxPoolPreparedStatementPerConnectionSize());
        druidDataSource.setRemoveAbandoned(this.druidProperties.getRemoveAbandoned());
        druidDataSource.setRemoveAbandonedTimeout(this.druidProperties.getRemoveAbandonedTimeout());
        try {
            druidDataSource.setFilters(this.druidProperties.getFilters());
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
        druidDataSource.setConnectionProperties(this.druidProperties.getConnectionProperties());
        return druidDataSource;
    }
}
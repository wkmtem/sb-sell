//package com.nsntc.sell.config.database;
//
//import com.jolbox.bonecp.BoneCPDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.PropertySource;
//
//import javax.sql.DataSource;
//
///**
// * Class Name: BoneCPConfig
// * Package: com.nsntc.sell.config
// * Description: boneCP连接池
// * @author wkm
// * Create DateTime: 2017/12/2 上午12:46
// * Version: 1.0
// */
//@SpringBootConfiguration
///** 只可加载proprties文件 */
//@PropertySource(value = { "classpath:properties/boneCP.properties" }, ignoreResourceNotFound = true)
//public class BoneCPConfig {
//
//    @Value("${boneCP.driverClass}")
//    private String driverClass;
//    @Value("${boneCP.username}")
//    private String username;
//    @Value("${boneCP.password}")
//    private String password;
//    @Value("${boneCP.url}")
//    private String url;
//
//    @Value("${boneCP.idleConnectionTestPeriodInMinutes}")
//    private String idleConnectionTestPeriodInMinutes;
//    @Value("${boneCP.idleMaxAgeInMinutes}")
//    private String idleMaxAgeInMinutes;
//    @Value("${boneCP.maxConnectionsPerPartition}")
//    private String maxConnectionsPerPartition;
//    @Value("${boneCP.minConnectionsPerPartition}")
//    private String minConnectionsPerPartition;
//
//    /** Spring容器管理, 未指定name, 则是方法名; 指定, 方法名被忽略。bean的名称和别名也可以通过value指定 */
//    @Bean(name = "dataSource", destroyMethod = "close") /** 销毁方法参数:"close" */
//    /** 在相同的DataSource中，首先使用被标注@Primary的DataSource,这里定义的DataSource将覆盖其他来源的DataSource */
//    @Primary
//    public DataSource dataSource() {
//        BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
//        boneCPDataSource.setDriverClass(driverClass);
//        boneCPDataSource.setUsername(username);
//        boneCPDataSource.setPassword(password);
//        boneCPDataSource.setJdbcUrl(url);
//
//        /** 检查数据库连接池中空闲连接的间隔时间, 单位:分钟, 默认:240, 取消:0 */
//        boneCPDataSource.setIdleConnectionTestPeriodInMinutes(Integer.parseInt(idleConnectionTestPeriodInMinutes));
//         /** 连接池中未使用的连接最大存活时间, 单位:分钟, 默认:60, 永远存活:0 */
//        boneCPDataSource.setIdleMaxAgeInMinutes(Integer.parseInt(idleMaxAgeInMinutes));
//         /** 每个分区最大的连接数, 判断依据: 请求并发数 */
//        boneCPDataSource.setMaxConnectionsPerPartition(Integer.parseInt(maxConnectionsPerPartition));
//         /** 每个分区最小的连接数, 判断依据: 请求并发数 */
//        boneCPDataSource.setMinConnectionsPerPartition(Integer.parseInt(minConnectionsPerPartition));
//        return boneCPDataSource;
//    }
//}

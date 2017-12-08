package com.nsntc.sell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Class Name: SbSellApplication
 * Package: com.nsntc.sell.config
 * Description: spring boot 入口函数
 * @author wkm
 * Create DateTime: 2017/12/2 上午12:59
 * Version: 1.0
 */
/** 复合注解: 包含@ComponentScan(扫描@Component,@Controller,@Service,@Repository), @SpringBootConfiguration, @EnableAutoConfiguration */
@SpringBootApplication(scanBasePackages = "com.nsntc.sell") /** 扫描@SpringBootApplication注解标记类包下及其子包的类,可指定可排除某个class */
/** 扫描@WebServlet */
@ServletComponentScan
/** 扫描mapper路径 */
@MapperScan(basePackages = "com.nsntc.sell.repository.mapper")
public class SbSellApplication {
/** 用于package war, 在外部的tomcat中运行(springbooot在根目录下自动生成org.springframework.boot.loader包(启动类)) */
//public class SbSellApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

        /** 运行的应用必须包含@SpringBootApplication注解 */
        SpringApplication application = new SpringApplication(SbSellApplication.class);
        /**
         * Banner.Mode.OFF: 关闭
         * Banner.Mode.CONSOLE: 控制台输出，默认方式
         * Banner.Mode.LOG: 日志输出方式
         */
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);
	}

    /**
     * Method Name: configure
     * Description: 设置外部tomcat运行的启动类(入口), 即本类
     * Create DateTime: 2017/12/8 下午6:04
     * @param builder
     * @return
     */
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(SbSellApplication.class);
//    }
}

package com.nsntc.sell;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Class Name: SbSellApplication
 * Package: com.nsntc.sell.config
 * Description: spring boot 入口函数
 * @author wkm
 * Create DateTime: 2017/12/2 上午12:59
 * Version: 1.0
 */
@SpringBootApplication
@ServletComponentScan
public class SbSellApplication {

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
}

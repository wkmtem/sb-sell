package com.nsntc.sell.config.wechat;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Class Name: WechatMpConfig
 * Package: com.nsntc.sell.config.wechat
 * Description: 微信公众号配置
 * @author wkm
 * Create DateTime: 2017/12/4 下午9:02
 * Version: 1.0
 */
/** 被复合注解@SpringBootConfiguration中的@ComponentScan纳入spring容器中 */
//@Component
/** 将当前类内声明的@Bean方法的实例纳入srping容器中, 且实例名就是方法名 */
@SpringBootConfiguration
public class WechatMpConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    /**
     * Method Name: wxMpService
     * Description: 第三方sdk定义通过WxMpService构造参数
     * Create DateTime: 2017/12/8 上午12:18
     * @return
     */
    /** 返回值是一个类的实例,并声明这个返回值(返回一个对象)是spring上下文环境中的一个bean */
    @Bean(name = "wxMpService") /** 即bean的id */
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(this.wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        /** 基于内存的微信配置provider，在实际生产环境中应将这些配置持久化 */
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(this.wechatAccountConfig.getMpAppId());
        wxMpConfigStorage.setSecret(this.wechatAccountConfig.getMpAppSecret());
        return wxMpConfigStorage;
    }
}
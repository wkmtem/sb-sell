package com.nsntc.sell.config.wechat;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Class Name: WechatOpenConfig
 * Package: com.nsntc.sell.config.wechat
 * Description: 微信开放平台配置
 * @author wkm
 * Create DateTime: 2017/12/8 上午12:09
 * Version: 1.0
 */
@Component
public class WechatOpenConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    /**
     * Method Name: wxOpenService
     * Description: 第三方sdk定义通过WxMpService构造参数
     * Create DateTime: 2017/12/8 上午12:13
     * @return
     */
    @Bean
    public WxMpService wxOpenService() {
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(this.wxOpenConfigStorage());
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage wxOpenConfigStorage() {
        /** 基于内存的微信配置provider，在实际生产环境中应将这些配置持久化 */
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(this.wechatAccountConfig.getOpenAppId());
        wxMpInMemoryConfigStorage.setSecret(this.wechatAccountConfig.getOpenAppSecret());
        return wxMpInMemoryConfigStorage;
    }
}

package com.nsntc.sell.config.wechat;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
@Component
public class WechatMpConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    /**
     * Method Name: wxMpService
     * Description: 第三方sdk定义通过WxMpService构造参数
     * Create DateTime: 2017/12/8 上午12:18
     * @return
     */
    @Bean
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

package com.nsntc.sell.config.wechat;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Class Name: WechatPayConfig
 * Package: com.nsntc.sell.config.wechat
 * Description: 微信支付配置
 * @author wkm
 * Create DateTime: 2017/12/5 下午8:27
 * Version: 1.0
 */
@Component
public class WechatPayConfig {

    @Autowired
    private WechatAccountConfig accountConfig;

    @Bean
    public BestPayServiceImpl bestPayService() {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }

    /**
     * Method Name: wxPayH5Config
     * Description: 公众号支付
     * Create DateTime: 2017/12/5 下午8:42
     * @return
     */
    @Bean
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        /** 公众平台id */
        wxPayH5Config.setAppId(accountConfig.getMpAppId());
        /** 公众平台密钥 */
        wxPayH5Config.setAppSecret(accountConfig.getMpAppSecret());
        /** 商户号 */
        wxPayH5Config.setMchId(accountConfig.getMchId());
        /** 商户密钥 */
        wxPayH5Config.setMchKey(accountConfig.getMchKey());
        /** 商户证书路径 */
        wxPayH5Config.setKeyPath(accountConfig.getKeyPath());
        /** 微信支付异步通知地址 */
        wxPayH5Config.setNotifyUrl(accountConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}

package com.nsntc.sell.config.wechat;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Class Name: WechatAccountConfig
 * Package: com.nsntc.sell.config.wechat
 * Description: 微信公众号信息
 * @author wkm
 * Create DateTime: 2017/12/4 下午9:21
 * Version: 1.0
 */
@Data
@Component
/** 只可加载proprties文件 */
@PropertySource(value = { "classpath:properties/wechat.properties" }, ignoreResourceNotFound = true)
public class WechatAccountConfig {

    /**
     * 公众平台id
     */
    @Value("${wechat.mpAppId}")
    private String mpAppId;

    /**
     * 公众平台密钥
     */
    @Value("${wechat.mpAppSecret}")
    private String mpAppSecret;

    /**
     * 开放平台id
     */
    @Value("${wechat.openAppId}")
    private String openAppId;

    /**
     * 开放平台密钥
     */
    @Value("${wechat.openAppSecret}")
    private String openAppSecret;

    /**
     * 商户号
     */
    @Value("${wechat.mchId}")
    private String mchId;

    /**
     * 商户密钥
     */
    @Value("${wechat.mchKey}")
    private String mchKey;

    /**
     * 商户证书路径
     */
    @Value("${wechat.keyPath}")
    private String keyPath;

    /**
     * 微信支付异步通知地址
     */
    @Value("${wechat.notifyUrl}")
    private String notifyUrl;

    /**
     * 微信模版id
     */
    private Map<String, String> templateId;
}

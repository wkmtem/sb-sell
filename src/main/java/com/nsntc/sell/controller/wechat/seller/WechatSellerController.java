package com.nsntc.sell.controller.wechat.seller;

import com.nsntc.sell.config.other.ProjectUrlConfig;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * Class Name: WechatSellerController
 * Package: com.nsntc.sell.controller.wechat.seller
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/7 下午7:33
 * Version: 1.0
 */
@Controller
@RequestMapping("wechat")
@Slf4j
public class WechatSellerController {

    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * Method Name: qrAuthorize
     * Description: 扫码登录
     * Create DateTime: 2017/12/4 下午10:19
     * @param returnUrl
     * @return
     */
    @GetMapping("qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
        try {
            returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionCustom(HttpResultEnum.URL_ENCODER_ERROR);
        }
        String url = this.projectUrlConfig.getWechatOpenAuthorize() + "/sell/wechat/qrWxUserInfo";
        String redirectUrl = this.wxOpenService.buildQrConnectUrl(
                /** 网页应用目前仅填写snsap_login */
                url, WxConsts.QrConnectScope.SNSAPI_LOGIN, returnUrl);
        return "redirect:" + redirectUrl;
    }

    /**
     * Method Name: qrWxUserInfo
     * Description: 重定向跳转地址，授权并获取微信用户openid
     * Create DateTime: 2017/12/5 下午7:53
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping("qrWxUserInfo")
    public String qrWxUserInfo(@RequestParam("code") String code,
                               @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = this.wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new ExceptionCustom(HttpResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        log.info("wxMpOAuth2AccessToken={}", wxMpOAuth2AccessToken);
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openId;
    }
}

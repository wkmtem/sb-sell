package com.nsntc.sell.controller.wechat;

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
 * Class Name: WechatController
 * Package: com.nsntc.sell.controller.wechat
 * Description: 
 * @author wkm
 * Create DateTime: 2017/12/4 下午8:58
 * Version: 1.0
 */
@Controller
@RequestMapping("wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * Method Name: authorize
     * Description: 构造网页授权url，重定向给用户点击
     * Create DateTime: 2017/12/4 下午9:36
     * @param returnUrl
     * @return
     */
    @GetMapping("authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        try {
            returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionCustom(HttpResultEnum.URL_ENCODER_ERROR);
        }
        /** 构造网页授权重定向url: userInfo */
        String url = this.projectUrlConfig.getWechatMpAuthorize() + "/sell/wechat/wxUserInfo";
        String redirectUrl = this.wxMpService.oauth2buildAuthorizationUrl(
                /**
                 * SNSAPI_BASE: 仅openid无授权弹窗
                 * SNSAPI_USERINFO: openid及用户昵称等基本信息
                 */
                url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, returnUrl);
        return "redirect:" + redirectUrl;
    }

    /**
     * Method Name: wxUserInfo
     * Description: 重定向跳转地址，授权并获取微信用户openid
     * Create DateTime: 2017/12/4 下午9:41
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping("wxUserInfo")
    public String wxUserInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            /** 获取access token */
            wxMpOAuth2AccessToken = this.wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new ExceptionCustom(HttpResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        /** 携带openid重定向至应用url, openid由前端保存至cookie中 */
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openId;
    }

    /**
     * Method Name: qrAuthorize
     * Description: 扫码授权
     * Create DateTime: 2017/12/4 下午10:19
     * @param returnUrl
     * @return
     */
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
        String url = this.projectUrlConfig.getWechatOpenAuthorize() + "/sell/wechat/qrWxUserInfo";
        String redirectUrl = this.wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
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
    @GetMapping("/qrWxUserInfo")
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

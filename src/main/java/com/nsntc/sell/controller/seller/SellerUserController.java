package com.nsntc.sell.controller.seller;

import com.nsntc.sell.config.properties.ProjectUrlConfig;
import com.nsntc.sell.constant.CookieConstant;
import com.nsntc.sell.constant.RedisConstant;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.pojo.po.SellerInfo;
import com.nsntc.sell.service.seller.ISellerService;
import com.nsntc.sell.util.CookieUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Class Name: SellerUserController
 * Package: com.nsntc.sell.controller.seller
 * Description: 卖家用户登录登出
 * @author wkm
 * Create DateTime: 2017/12/8 上午1:11
 * Version: 1.0
 */
@Controller
@RequestMapping("seller")
public class SellerUserController {

    @Autowired
    private ISellerService sellerService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * Method Name: login
     * Description: 登录
     * Create DateTime: 2017/12/8 上午1:15
     * @param openid
     * @param response
     * @param map
     * @return
     */
    @GetMapping("login")
    public ModelAndView login(@RequestParam("openid") String openid, HttpServletResponse response,
                              Map<String, Object> map) {

        /** 匹配数据库openid */
        SellerInfo sellerInfo = this.sellerService.findSellerInfoByOpenid(openid);
        if (null == sellerInfo) {
            map.put("msg", HttpResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        /** 清除redis中token */
        this.delKeyFromRedis(sellerInfo.getToken());

        /** 设置token至redis */
        String token = DigestUtils.sha1Hex(System.currentTimeMillis() + openid);
        String sha1_token = DigestUtils.sha1Hex(token);
        Integer expire = RedisConstant.EXPIRE;
        this.stringRedisTemplate.opsForValue().set(
                /** redis中的key需加密 */
                String.format(RedisConstant.TOKEN_PREFIX, sha1_token),
                openid,
                expire,
                TimeUnit.SECONDS);

        /** 设置未加密token至数据库，用于单一登录 */
        sellerInfo.setToken(token);
        this.sellerService.save(sellerInfo);

        /** 设置未加密token至cookie */
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        return new ModelAndView("redirect:" + this.projectUrlConfig.getBaseUrl() + "/sell/seller/order/list");

    }

    /**
     * Method Name: logout
     * Description: 登出
     * Create DateTime: 2017/12/8 上午1:15
     * @param request
     * @param response
     * @param map
     * @return
     */
    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response,
                       Map<String, Object> map) {
        /** 从cookie里查询 */
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (null != cookie) {
            /** 清除redis */
            this.delKeyFromRedis(cookie.getValue());

            /** 清除cookie */
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }
        map.put("msg", HttpResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * Method Name: delKeyFromRedis
     * Description: 删除redis中的key
     * Create DateTime: 2017/12/8 上午3:45
     * @param token
     */
    private void delKeyFromRedis(String token) {
        this.stringRedisTemplate.opsForValue().getOperations()
                .delete(String.format(RedisConstant.TOKEN_PREFIX, DigestUtils.sha1Hex(token)));
    }
}

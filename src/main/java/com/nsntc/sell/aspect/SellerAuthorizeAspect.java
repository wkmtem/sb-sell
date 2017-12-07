package com.nsntc.sell.aspect;

import com.nsntc.sell.constant.CookieConstant;
import com.nsntc.sell.constant.RedisConstant;
import com.nsntc.sell.exception.SellerAuthorizeException;
import com.nsntc.sell.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Class Name: SellerAuthorizeAspect
 * Package: com.nsntc.sell.aspect
 * Description: 卖家授权认证切面
 * @author wkm
 * Create DateTime: 2017/12/8 上午3:59
 * Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(public * com.nsntc.sell.controller.seller.*.*(..))" +
              "&& !execution(public * com.nsntc.sell.controller.seller.SellerUserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        /** 获取cookie */
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (null == cookie) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        /** 验证token */
        String tokenValue = this.stringRedisTemplate.opsForValue()
                .get(String.format(RedisConstant.TOKEN_PREFIX, DigestUtils.sha1Hex(cookie.getValue())));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}

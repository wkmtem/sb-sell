package com.nsntc.sell.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Class Name: CookieUtil
 * Package: com.nsntc.sell.util
 * Description: cookie工具类
 * @author wkm
 * Create DateTime: 2017/12/8 上午2:35
 * Version: 1.0
 */
public class CookieUtil {

    /**
     * Method Name: set
     * Description: 设置cookie
     * Create DateTime: 2017/12/8 上午2:35
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * Method Name: get
     * Description: 获取cookie
     * Create DateTime: 2017/12/8 上午2:36
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request,
                           String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        }
        return null;
    }

    /**
     * Method Name: readCookieMap
     * Description: 将cookie封装成Map
     * Create DateTime: 2017/12/8 上午2:36
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>(16);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}

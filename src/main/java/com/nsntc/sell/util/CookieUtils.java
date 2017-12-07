package com.nsntc.sell.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Name: CookieUtils
 * Package: com.nsntc.sell.util
 * Description: Cookie工具类
 * @author wkm
 * Create DateTime: 2017/12/8 上午2:39
 * Version: 1.0
 */
@Slf4j
public final class CookieUtils {

	/**
	 * Method Name: getCookieValue
	 * Description: 获取Cookie值(不解码)
	 * Create DateTime: 2017/12/8 上午2:40
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		return getCookieValue(request, cookieName, false);
	}

	/**
	 * Method Name: getCookieValue
	 * Description: 获取Cookie值(是否解码)
	 * Create DateTime: 2017/12/8 上午2:41
	 * @param request
	 * @param cookieName
	 * @param isDecoder
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList == null || cookieName == null){
			return null;			
		}
		String retValue = null;
		try {
			for (int i = 0; i < cookieList.length; i++) {
				if (cookieList[i].getName().equals(cookieName)) {
					if (isDecoder) {
						retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
					} else {
						retValue = cookieList[i].getValue();
					}
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Cookie Decode Error.", e);
		}
		return retValue;
	}

	/**
	 * Method Name: getCookieValue
	 * Description: 获取Cookie值(自定义解码字符集)
	 * Create DateTime: 2017/12/8 上午2:42
	 * @param request
	 * @param cookieName
	 * @param encodeString
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList == null || cookieName == null){
			return null;			
		}
		String retValue = null;
		try {
			for (int i = 0; i < cookieList.length; i++) {
				if (cookieList[i].getName().equals(cookieName)) {
					retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("Cookie Decode Error.", e);
		}
		return retValue;
	}

	/**
	 * Method Name: setCookie
	 * Description: 设置Cookie值(不编码)，不设置生效时间(默认浏览器关闭即失效)
	 * Create DateTime: 2017/12/8 上午2:42
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue) {
		setCookie(request, response, cookieName, cookieValue, -1);
	}

	/**
	 * Method Name: setCookie
	 * Description: 设置Cookie值(不编码)，在指定时间内生效
	 * Create DateTime: 2017/12/8 上午2:44
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param cookieMaxage
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, 
			String cookieName, String cookieValue, int cookieMaxage) {
		setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
	}

	/**
	 * Method Name: setCookie
	 * Description: 设置Cookie值(是否编码)，不设置生效时间(默认浏览器关闭即失效)
	 * Create DateTime: 2017/12/8 上午2:44
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param isEncode
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, 
			String cookieName, String cookieValue, boolean isEncode) {
		setCookie(request, response, cookieName, cookieValue, -1, isEncode);
	}

	/**
	 * Method Name: setCookie
	 * Description: 设置Cookie值(是否编码)，在指定时间内生效
	 * Create DateTime: 2017/12/8 上午2:45
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param cookieMaxage
	 * @param isEncode
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, 
			String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
		doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
	}

	/**
	 * Method Name: setCookie
	 * Description: 设置Cookie值(自定义编码字符集)，在指定时间内生效
	 * Create DateTime: 2017/12/8 上午2:46
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param cookieMaxage
	 * @param encodeString
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, 
			String cookieName, String cookieValue, int cookieMaxage, String encodeString) {
		doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
	}

	/**
	 * Method Name: deleteCookie
	 * Description: 删除Cookie，保留cookie域名
	 * Create DateTime: 2017/12/8 上午2:47
	 * @param request
	 * @param response
	 * @param cookieName
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, 
			String cookieName) {
		doSetCookie(request, response, cookieName, "", -1, false);
	}

	/**
	 * Method Name: doSetCookie
	 * Description: 设置Cookie值(是否编码)，在指定时间(秒)内生效
	 * Create DateTime: 2017/12/8 上午2:47
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param cookieMaxage
	 * @param isEncode
	 */
	private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, 
			String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else if (isEncode) {
				cookieValue = URLEncoder.encode(cookieValue, "utf-8");
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0) {
				cookie.setMaxAge(cookieMaxage);
			}
			if (null != request) {
				/** 设置域名的cookie */
				cookie.setDomain(getDomainName(request));
			}
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			log.error("Cookie Encode Error.", e);
		}
	}

	/**
	 * Method Name: doSetCookie
	 * Description: 设置Cookie值(自定义编码字符集)，在指定时间(秒)内生效
	 * Create DateTime: 2017/12/8 上午2:49
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param cookieMaxage
	 * @param encodeString
	 */
	private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, 
			String cookieName, String cookieValue, int cookieMaxage, String encodeString) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else {
				cookieValue = URLEncoder.encode(cookieValue, encodeString);
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0) {
				cookie.setMaxAge(cookieMaxage);
			}
			if (null != request) {
				/** 设置域名的cookie */
				cookie.setDomain(getDomainName(request));
			}
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			log.error("Cookie Encode Error.", e);
		}
	}

	/**
	 * Method Name: getDomainName
	 * Description: 获取cookie域名(若通过nginx反向代理，则需在nginx代理头信息中配置proxy_set_header Host $host;)
	 * Create DateTime: 2017/12/8 上午2:50
	 * @param request
	 * @return
	 */
	private static final String getDomainName(HttpServletRequest request) {
		String domainName = null;

		String serverName = request.getRequestURL().toString();
		if (serverName == null || serverName.equals("")) {
			domainName = "";
		} else {
			serverName = serverName.toLowerCase();
			serverName = serverName.substring(serverName.indexOf("//") + 2);
			final int end = serverName.indexOf("/");
			serverName = serverName.substring(0, end);
			final String[] domains = serverName.split("\\.");
			int len = domains.length;
			if (len > 3) {
				// www.xxx.com.cn
				domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
			} else if (len <= 3 && len > 1) {
				// xxx.com or xxx.cn
				domainName = "." + domains[len - 2] + "." + domains[len - 1];
			} else {
				domainName = serverName;
			}
		}
		if (domainName != null && domainName.indexOf(":") > 0) {
			String[] ary = domainName.split("\\:");
			domainName = ary[0];
		}
		return domainName;
	}
}

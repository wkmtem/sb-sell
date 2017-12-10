package com.nsntc.sell.servlet.druid;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;
import com.nsntc.sell.constant.DruidConstant;
import org.springframework.beans.factory.annotation.Value;

/**
 * Class Name: DruidStatViewServlet
 * Package: com.nsntc.sell.servlet.druid
 * Description: Druid Monitor, 相当于在web.xml中声明了一个servlet, 访问地址 http://ip:port/druid/index.html
 * <servlet>
 *   <servlet-name>DruidStatView</servlet-name>
 *   <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
 * </servlet>
 * <servlet-mapping>
 *   <servlet-name>DruidStatView</servlet-name>
 *   <url-pattern>/druid/*</url-pattern>
 * </servlet-mapping>
 * @author wkm
 * Create DateTime: 2017/12/1 下午10:56
 * Version: 1.0
 */
@WebServlet(urlPatterns = DruidConstant.MONITOR_URLPATTERNS,
            initParams = {@WebInitParam(name = "allow", value = DruidConstant.MONITOR_ALLOW),/** IP白名单(没有配置或者为空,则允许所有访问) */
                          @WebInitParam(name = "deny", value = DruidConstant.MONITOR_DENY),/** IP黑名单(存在共同时,deny优先于allow) */
                          @WebInitParam(name = "loginUsername", value = DruidConstant.MONITOR_LOGINUSERNAME),/** 用户名 */
                          @WebInitParam(name = "loginPassword", value = DruidConstant.MONITOR_LOGINPASSWORD),/** 密码 */
                          @WebInitParam(name = "resetEnable", value = DruidConstant.MONITOR_RESETENABLE)})/** 禁用HTML页面上的'Reset All'功能 */
public class DruidStatViewServlet extends StatViewServlet {
    private static final long serialVersionUID = -2699972071445249526L;
}

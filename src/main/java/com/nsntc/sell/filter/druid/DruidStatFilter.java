package com.nsntc.sell.filter.druid;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;
import com.nsntc.sell.constant.DruidConstant;

/**
 * Class Name: DruidStatFilter
 * Package: com.nsntc.sell.filter
 * Description: Druid Monitor, 相当于在web.xml中声明了一个filter
 * <filter>
 *   <filter-name>DruidWebStatFilter</filter-name>
 *   <filter-class>com.alibaba.druid.support.http.WebStatFilter</filter-class>
 *   <init-param>
 *     <param-name>exclusions</param-name>
 *     <param-value>*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*</param-value>
 *   </init-param>
 * </filter>
 * <filter-mapping>
 *   <filter-name>DruidWebStatFilter</filter-name>
 *   <url-pattern>/*</url-pattern>
 * </filter-mapping>
 * @author wkm
 * Create DateTime: 2017/12/1 下午10:46
 * Version: 1.0
 */
@WebFilter(filterName = DruidConstant.MONITOR_FILTER_FILTERNAME,
           urlPatterns = DruidConstant.MONITOR_FILTER_URLPATTERNS,
           initParams = {@WebInitParam(name = DruidConstant.MONITOR_FILTER_INITPARAMS_NAME,
                                       value = DruidConstant.MONITOR_FILTER_INITPARAMS_VALUE)})/** 忽略资源 */
public class DruidStatFilter extends WebStatFilter {
}

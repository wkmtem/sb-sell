package com.nsntc.sell.constant;

/**
 * Class Name: DruidConstant
 * Package: com.nsntc.sell.constant
 * Description: 德鲁伊常量
 * @author wkm
 * Create DateTime: 2017/12/10 下午6:57
 * Version: 1.0
 */
public interface DruidConstant {

    String MONITOR_URLPATTERNS = "/druid/*";

    /** IP白名单(没有配置或者为空,则允许所有访问) */
    String MONITOR_ALLOW = "";

    /** IP黑名单(存在共同时,deny优先于allow) */
    String MONITOR_DENY = "";

    /** 用户名 */
    String MONITOR_LOGINUSERNAME = "administrator";

    /** 密码 */
    String MONITOR_LOGINPASSWORD = "omega";

    /** 禁用HTML页面上的'Reset All'功能 */
    String MONITOR_RESETENABLE = "false";

    String MONITOR_FILTER_FILTERNAME = "druidWebStatFilter";
    String MONITOR_FILTER_URLPATTERNS = "/*";

    /** 忽略资源 */
    String MONITOR_FILTER_INITPARAMS_NAME = "exclusions";
    String MONITOR_FILTER_INITPARAMS_VALUE = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*";

}

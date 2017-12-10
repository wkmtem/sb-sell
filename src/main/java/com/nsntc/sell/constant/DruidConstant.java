package com.nsntc.sell.constant;

/**
 * Class Name: DruidConstant
 * Package: com.nsntc.interview.constant
 * Description: 德鲁伊常量
 * @author wkm
 * Create DateTime: 2017/12/10 下午5:13
 * Version: 1.0
 */
public interface DruidConstant {

    /** servlet */
    String MONITOR_URLPATTERNS = "/druid/*";
    /** 白名单 */
    String MONITOR_ALLOW = "";
    /** 黑名单 */
    String MONITOR_DENY = "";
    String MONITOR_LOGINUSERNAME = "administrator";
    String MONITOR_LOGINPASSWORD = "omega";
    /** 禁用HTML页面上的'Reset All'功能 */
    String MONITOR_RESETENABLE = "false";

    /** filter */
    String MONITOR_FILTER_FILTERNAME = "druidWebStatFilter";
    String MONITOR_FILTER_URLPATTERNS = "/*";
    /** 忽略资源 */
    String MONITOR_FILTER_INITPARAMS_NAME = "exclusions";
    String MONITOR_FILTER_INITPARAMS_VALUE = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*";

}

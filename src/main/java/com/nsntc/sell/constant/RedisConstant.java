package com.nsntc.sell.constant;

/**
 * Class Name: RedisConstant
 * Package: com.nsntc.sell.constant
 * Description: 采用接口(Interface)的中变量默认为static final的特性
 * @author wkm
 * Create DateTime: 2017/12/8 上午2:32
 * Version: 1.0
 */
public interface RedisConstant {

    /** token前缀 */
    String TOKEN_PREFIX = "token_%s";

    /** 2小时 */
    Integer EXPIRE = 7200;
}

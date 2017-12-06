package com.nsntc.sell.util;


import com.nsntc.sell.enums.CodeEnum;

/**
 * Class Name: EnumUtil
 * Package: com.nsntc.sell.util
 * Description: 枚举工具类
 * @author wkm
 * Create DateTime: 2017/12/6 下午10:37
 * Version: 1.0
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}

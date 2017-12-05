package com.nsntc.sell.util;

import java.util.Random;

/**
 * Class Name: KeyUtil
 * Package: com.nsntc.sell.util
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/3 下午4:17
 * Version: 1.0
 */
public class KeyUtil {

    /**
     * Method Name: genUniqueKey
     * Description: 生成唯一的主键(同步锁), 格式: 毫秒值 + 随机数
     * Create DateTime: 2017/12/3 下午4:18
     * @return
     */
    public static synchronized String genUniqueKey() {
        /** bound 0-899999随机数 */
        Integer number = new Random().nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}

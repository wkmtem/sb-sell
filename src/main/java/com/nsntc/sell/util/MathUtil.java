package com.nsntc.sell.util;

/**
 * Class Name: MathUtil
 * Package: com.nsntc.sell.util
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/5 下午11:17
 * Version: 1.0
 */
public class MathUtil {

    /** 精度 */
    private static final Double MONEY_RANGE = 0.01;

    /**
     * Method Name: equals
     * Description: 比较2个金额是否相等
     * Create DateTime: 2017/12/5 下午11:17
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1, Double d2) {
        /** 返回数字的绝对值 */
        Double result = Math.abs(d1 - d2);
        if (result < MONEY_RANGE) {
            return true;
        }
        return false;
    }
}

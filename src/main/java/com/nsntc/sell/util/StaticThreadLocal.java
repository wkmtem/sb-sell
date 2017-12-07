package com.nsntc.sell.util;

/**
 * Class Name: StaticThreadLocal
 * Package: com.nsntc.sell.util
 * Description: 并不是一个Thread，而是Thread的局部变量
 * @author wkm
 * Create DateTime: 2017/12/8 上午3:22
 * Version: 1.0
 */
public class StaticThreadLocal {

    /**
     *  Map: key = 线程对象, value = 对应线程的变量副本
     */
    private static final ThreadLocal<Object> LOCAL = new ThreadLocal<>();

    /**
     * Method Name: initialValue
     * Description: 延迟调用，第1次调用get()时执行，且仅执行1次。调用set()时，不会调用该方法
     * Create DateTime: 2017/12/8 上午3:22
     * @return
     */
    protected Object initialValue() {
        return null;
    }

    /**
     * Method Name: set
     * Description:
     * Create DateTime: 2017/12/8 上午3:23
     * @param obj
     */
    public static void set(Object obj) {
        LOCAL.set(obj);
    }

    /**
     * Method Name: get
     * Description:
     * Create DateTime: 2017/12/8 上午3:23
     * @return
     */
    public static Object get() {
        return LOCAL.get();
    }

    /**
     * Method Name: remove
     * Description: 显式调用，减少内存的占用
     * Create DateTime: 2017/12/8 上午3:23
     */
    public static void remove() {
        LOCAL.remove();
    }
}

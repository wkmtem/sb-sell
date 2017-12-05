package com.nsntc.sell.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Objects;

/**
 * Class Name: GsonUtil
 * Package: com.nsntc.sell.util
 * Description: json工具类
 * @author wkm
 * Create DateTime: 2017/12/5 下午8:56
 * Version: 1.0
 */
public class GsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static GsonBuilder gsonBuilder = new GsonBuilder();

    public GsonUtil() {
    }

    /**
     * Method Name: toJson
     * Description: json序列化
     * Create DateTime: 2017/12/5 下午8:59
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create().toJson(obj);
    }

    /**
     * Method Name: toObject
     * Description: json反序列化
     * Create DateTime: 2017/12/5 下午8:59
     * @param json
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        Objects.requireNonNull(json, "json is null.");
        Objects.requireNonNull(valueType, "value type is null.");

        try {
            return mapper.readValue(json, valueType);
        } catch (IOException var3) {
            throw new IllegalStateException("fail to convert [" + json + "] to [" + valueType + "].", var3);
        }
    }
}

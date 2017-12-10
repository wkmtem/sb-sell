package com.nsntc.sell.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Class Name: JsonUtil
 * Package: com.nsntc.commons.util.json
 * Description: JSON工具类
 * @author wkm
 * Create DateTime: 2017/11/22 上午12:40
 * Version: 1.0
 */
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Method Name: objectToJson
     * Description: 对象序列化为json
     * Create DateTime: 2017/11/22 上午12:40
     * @param obj
     * @return
     */
    public static String objectToJson(Object obj) {
        try {
            String string = MAPPER.writeValueAsString(obj);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method Name: jsonToObject
     * Description: json反序列化为对象
     * Create DateTime: 2017/11/22 上午12:41
     * @param jsonData
     * @param beanClazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String jsonData, Class<T> beanClazz) {
        try {
            T t = MAPPER.readValue(jsonData, beanClazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method Name: jsonToObjectList
     * Description: json反序列化为集合
     * Create DateTime: 2017/11/22 上午12:41
     * @param jsonData
     * @param beanClazz
     * @param <T>
     * @return
     */
    public static <T>List<T> jsonToObjectList(String jsonData, Class<T> beanClazz) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanClazz);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
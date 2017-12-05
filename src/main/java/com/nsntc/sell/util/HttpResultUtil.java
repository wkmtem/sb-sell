package com.nsntc.sell.util;


import com.nsntc.sell.bean.HttpResult;
import org.springframework.http.HttpStatus;

/**
 * Class Name: HttpResultUtil
 * Package: com.nsntc.sell.util
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/2 下午5:57
 * Version: 1.0
 */
public class HttpResultUtil {

    public static HttpResult success() {
        return success(null);
    }

    public static HttpResult success(Object data) {
        return success(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static HttpResult success(Integer code, String msg) {

        return success(code, msg, null);
    }

    public static HttpResult success(Integer code, String msg, Object data) {

        return setProperties(code, msg, data);
    }

    public static HttpResult error(Integer code, String msg) {
        return setProperties(code, msg, null);
    }

    private static HttpResult setProperties(Integer code, String msg, Object data){
        HttpResult<Object> result = new HttpResult<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}

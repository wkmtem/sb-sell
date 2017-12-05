package com.nsntc.sell.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Class Name: HttpResult
 * Package: com.nsntc.sell.bean
 * Description: http response obj
 * @author wkm
 * Create DateTime: 2017/12/2 下午5:52
 * Version: 1.0
 */
@Data
public class HttpResult<T> {
    
    private Integer code;
    private String msg;
    private T data;
}

package com.nsntc.sell.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Class Name: HttpResult
 * Package: com.nsntc.sell.bean
 * Description: http response obj
 * @author wkm
 * Create DateTime: 2017/12/2 下午5:52
 * Version: 1.0
 */
@Data
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = -2618856908360126376L;

    private Integer code;
    private String msg;
    private T data;
}

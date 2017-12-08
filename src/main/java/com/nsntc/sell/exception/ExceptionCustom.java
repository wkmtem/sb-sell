package com.nsntc.sell.exception;

import com.nsntc.sell.enums.HttpResultEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Class Name: ExceptionCustom
 * Package: com.nsntc.sell.exception
 * Description: 自定义异常类: spring事务对RuntimeException执行回滚
 * @author wkm
 * Create DateTime: 2017/12/2 下午5:54
 * Version: 1.0
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class ExceptionCustom extends RuntimeException{

    private Integer code;

    public ExceptionCustom(HttpResultEnum httpResultEnum) {
        super(httpResultEnum.getMessage());
        this.code = httpResultEnum.getCode();
    }

    public ExceptionCustom(Integer code, String message) {
        super(message);
        this.code = code;
    }
}

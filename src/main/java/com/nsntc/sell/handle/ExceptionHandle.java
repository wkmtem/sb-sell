package com.nsntc.sell.handle;

import com.nsntc.sell.bean.HttpResult;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.util.HttpResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Class Name: ExceptionHandle
 * Package: com.nsntc.interview.handle
 * Description: 全局统一异常处理
 * @author wkm
 * Create DateTime: 2017/11/30 下午10:38
 * Version: 1.0
 */
/** 处理全局异常 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    /** 声明捕获的异常类 */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public HttpResult UnifiedExceptionHandle (Exception e) {
        if (e instanceof ExceptionCustom) {
            return HttpResultUtil.error(((ExceptionCustom) e).getCode(), e.getMessage());
        }
        log.error("[ INTERNAL SERVER ERROR ] {}", e);
        /** 500 */
        return HttpResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}

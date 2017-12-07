package com.nsntc.sell.handler;

import com.nsntc.sell.bean.HttpResult;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.util.HttpResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class MessageNotReadableExceptionHandler {

    /**
     * Method Name: handleHttpMessageNotReadableException
     * Description: JSON convert exception
     * Create DateTime: 2017/12/7 下午6:17
     * @param e
     * @return
     */
    /** 声明捕获的异常类 */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public HttpResult handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error(HttpResultEnum.JSON_CONVERT_FAILURE.getMessage());
        return HttpResultUtil.error(HttpResultEnum.JSON_CONVERT_FAILURE.getCode(), HttpResultEnum.JSON_CONVERT_FAILURE.getMessage());
    }
}

package com.nsntc.sell.handle;

import com.nsntc.sell.bean.HttpResult;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.util.HttpResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
            log.error("[ ERROR CODE ] {}, [ ERROR MESSAGE ] {}", ((ExceptionCustom) e).getCode(), e.getMessage());
            return HttpResultUtil.error(((ExceptionCustom) e).getCode(), e.getMessage());
        }

        if (e instanceof HttpMessageNotReadableException) {
            log.error(HttpResultEnum.JSON_CONVERT_FAILURE.getMessage());
            return HttpResultUtil.error(HttpResultEnum.JSON_CONVERT_FAILURE.getCode(), HttpResultEnum.JSON_CONVERT_FAILURE.getMessage());
        }

        /** 500 */
        log.error("[ INTERNAL SERVER ERROR ] {}", e);
        return HttpResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /**
     * Method Name: handleMethodArgumentNotValidException
     * Description: valid exception (MethodArgumentNotValidException Since: spring 3.1.0.RELEASE)
     * Create DateTime: 2017/12/7 下午6:11
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public HttpResult handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        String errorMesssage = "| ";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + "| ";
        }
        log.error("[ INVALID REQUEST ] {}", bindingResult.getFieldError().getDefaultMessage());
        return HttpResultUtil.error(HttpResultEnum.PARAM_ERROR.getCode(), errorMesssage);
    }

    /**
     * Method Name: handleHttpMessageNotReadableException
     * Description: JSON convert exception
     * Create DateTime: 2017/12/7 下午6:17
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public HttpResult handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error(HttpResultEnum.JSON_CONVERT_FAILURE.getMessage());
        return HttpResultUtil.error(HttpResultEnum.JSON_CONVERT_FAILURE.getCode(), HttpResultEnum.JSON_CONVERT_FAILURE.getMessage());
    }
}

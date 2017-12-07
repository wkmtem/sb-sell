package com.nsntc.sell.handler;

import com.nsntc.sell.bean.HttpResult;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.util.HttpResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class MethodArgumentNotValidExceptionHandler {

    /**
     * Method Name: handleMethodArgumentNotValidException
     * Description: valid exception (MethodArgumentNotValidException Since: spring 3.1.0.RELEASE)
     * Create DateTime: 2017/12/7 下午6:11
     * @param ex
     * @return
     */
    /** 声明捕获的异常类 */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
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
}

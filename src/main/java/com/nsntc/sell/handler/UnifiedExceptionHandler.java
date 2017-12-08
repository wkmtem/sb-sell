package com.nsntc.sell.handler;

import com.nsntc.sell.bean.HttpResult;
import com.nsntc.sell.config.properties.ProjectUrlConfig;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.exception.ResponseBankException;
import com.nsntc.sell.exception.SellerAuthorizeException;
import com.nsntc.sell.util.HttpResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

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
public class UnifiedExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * Method Name: handlerAuthorizeException
     * Description: 捕获卖家端授权异常: SellerAuthorizeException
     * Create DateTime: 2017/12/8 下午11:06
     * @return
     */
    @ExceptionHandler(value = SellerAuthorizeException.class)
    /** 403 禁止访问 */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAuthorizeException() {
        log.error("【登录超时】");
        return new ModelAndView("redirect:"
                .concat(this.projectUrlConfig.getWechatOpenAuthorize())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(this.projectUrlConfig.getBaseUrl())
                .concat("/sell/seller/login"));
    }

    /**
     * Method Name: handleExceptionCustom
     * Description: 捕获自定义异常: ExceptionCustom
     * Create DateTime: 2017/12/8 下午11:08
     * @param e
     * @return
     */
    @ExceptionHandler(value = ExceptionCustom.class)
    @ResponseBody
    public HttpResult handleExceptionCustom (ExceptionCustom e) {
        log.error("[ ERROR CODE ] {}, [ ERROR MESSAGE ] {}", e.getCode(), e.getMessage());
        return HttpResultUtil.error(e.getCode(), e.getMessage());
    }

    /**
     * Method Name: handleMethodArgumentNotValidException
     * Description: 捕获验证异常: MethodArgumentNotValidException (Since: spring 3.1.0.RELEASE)
     * Create DateTime: 2017/12/7 下午6:11
     * @param e
     * @return
     */
    /** 声明捕获的异常类 */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public HttpResult handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        String errorMesssage = "| ";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + "| ";
        }
        log.error("[ INVALID REQUEST ] {}", bindingResult.getFieldError().getDefaultMessage());
        return HttpResultUtil.error(HttpResultEnum.PARAM_ERROR.getCode(), errorMesssage);
    }

    /**
     * Method Name: handleHttpMessageNotReadableException
     * Description: 捕获JSON转换异常: HttpMessageNotReadableException
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

    /**
     * Method Name: handleResponseBankException
     * Description: 银行专用异常捕获
     * Create DateTime: 2017/12/8 下午11:41
     * @return
     */
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleResponseBankException() {
    }

    /**
     * Method Name: handleException
     * Description: 捕获其它异常: Exception
     * Create DateTime: 2017/12/8 下午11:54
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public HttpResult handleException(Exception e) {
        log.error("系统异常{}", e);
        return HttpResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}

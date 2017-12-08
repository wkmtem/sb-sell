package com.nsntc.sell.handler;

import com.nsntc.sell.config.properties.ProjectUrlConfig;
import com.nsntc.sell.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class Name: SellExceptionHandler
 * Package: com.nsntc.sell.handle
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/8 上午4:40
 * Version: 1.0
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /** 声明捕获的异常类 */
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"
        .concat(this.projectUrlConfig.getWechatOpenAuthorize())
        .concat("/sell/wechat/qrAuthorize")
        .concat("?returnUrl=")
        .concat(this.projectUrlConfig.getBaseUrl())
        .concat("/sell/seller/login"));
    }
}

package com.nsntc.sell.handler;

import com.nsntc.commons.bean.Result;
import com.nsntc.commons.enums.ResultEnum;
import com.nsntc.commons.exception.ApplicationException;
import com.nsntc.commons.utils.FieldValidatorTransferUtil;
import com.nsntc.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Name: ExceptionResolver
 * Package: com.nsntc.interview.resolver
 * Description: 全局统一异常处理
 * @author wkm
 * Create DateTime: 2017/12/10 上午2:34
 * Version: 1.0
 */
@ControllerAdvice
@Slf4j
public class ExceptionResolver {

    /**
     * Method Name: handleConstraintViolationException
     * Description: 接口入参验证单字段异常
     * Create DateTime: 2017/12/10 下午9:07
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> map = FieldValidatorTransferUtil.extractPropertyAndMessage(e);
        log.error("[参数错误] >>>>>> {}", map.toString());
        StringBuilder sb = new StringBuilder();
        String key = "";
        String value = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            key = entry.getKey();
            key = StringUtils.substring(key, StringUtils.lastIndexOf(key, ".") + 1);
            value = entry.getValue();
            sb.append(key + ":" + value + "; ");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.lastIndexOf("; "));
        }
        return ResultUtil.error(ResultEnum.PARAM_ERROR.getCode(), sb.toString());
    }

    /**
     * Method Name: handleBindException
     * Description: 接口入参验证对象异常
     * Create DateTime: 2017/12/10 上午12:29
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result handleBindException (
            BindException e) {
        List<FieldError> errorList = e.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        String field = null;
        String message = null;
        String value = null;
        Map<String, String> map = new HashMap<>(16);
        for (FieldError fieldError : errorList) {
            field = fieldError.getField();
            message = fieldError.getDefaultMessage();
            value = map.get(field);
            if (StringUtils.isEmpty(value)) {
                map.put(field, message);
            } else {
                map.put(field, value + ", " + message);
            }
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            field = entry.getKey();
            value = entry.getValue();
            sb.append(field + ":" + value + "; ");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.lastIndexOf("; "));
        }
        log.error("[参数错误] >>>>>> {{}}", sb.toString());
        return ResultUtil.error(ResultEnum.PARAM_ERROR.getCode(), sb.toString());
    }

    /**
     * Method Name: handleApplicationException
     * Description: 自定义异常
     * Create DateTime: 2017/12/10 上午12:28
     * @param e
     * @return
     */
    @ExceptionHandler(value = ApplicationException.class)
    @ResponseBody
    public Result handleApplicationException (ApplicationException e) {
        log.error("[错误代码] >>>>>> {{}}, [错误信息] >>>>>> {}", e.getCode(), e.getMessage());
        return ResultUtil.error(e.getCode(), e.getMessage());
    }

    /**
     * Method Name: handleHttpMessageNotReadableException
     * Description: JSON转换异常
     * Create DateTime: 2017/12/10 上午12:30
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public Result handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error("[JSON转换错误] >>>>>> {{}}", e);
        return ResultUtil.error(ResultEnum.JSON_CONVERT_FAILURE);
    }

    /**
     * Method Name: handleException
     * Description: Exception
     * Create DateTime: 2017/12/10 上午12:31
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        log.error("[系统错误] >>>>>> {{}}", e);
        return ResultUtil.error(ResultEnum.SYSTEM_ERROR);
    }
}

package com.nsntc.sell.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidateUtil {

    /**
     * 线程安全的
     */
    private static Validator validator;

    /**
     * 校验器，就是hibernate的validator
     */
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * 获取未通过校验的错误信息字符串
     * 通过try捕获异常e.getMessage()，获取未通过校验的错误信息
     * @param t 带校验注解的bean对象
     * @param <T>
     * @throws Exception
     */
    public static <T> void validate(T t) throws Exception {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (constraintViolations.size() > 0) {
            String validateError = "";
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                validateError += constraintViolation.getMessage() + ";";
            }
            throw new Exception(validateError);
        }
    }
}

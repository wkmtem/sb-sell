package com.nsntc.sell.enums;

import lombok.Getter;

/**
 * Class Name: PayStatusEnum
 * Package: com.nsntc.sell.enums
 * Description: 支付状态枚举
 * @author wkm
 * Create DateTime: 2017/12/3 下午3:02
 * Version: 1.0
 */
@Getter
public enum PayStatusEnum implements CodeEnum {
    WAIT(0, "未支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

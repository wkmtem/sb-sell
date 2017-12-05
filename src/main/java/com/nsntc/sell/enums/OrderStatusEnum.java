package com.nsntc.sell.enums;

import lombok.Getter;

/**
 * Class Name: OrderStatusEnum
 * Package: com.nsntc.sell.enums
 * Description: 订单状态枚举
 * @author wkm
 * Create DateTime: 2017/12/3 下午2:57
 * Version: 1.0
 */
@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完成"),
    CANCEL(2, "取消"),
    ;

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

package com.nsntc.sell.enums;

import lombok.Getter;

/**
 * Class Name: ProductStatusEnum
 * Package: com.nsntc.sell.enums
 * Description: 商品状态枚举
 * @author wkm
 * Create DateTime: 2017/12/2 下午8:59
 * Version: 1.0
 */
@Getter
public enum ProductStatusEnum {

    UP(0, "上架商品"),
    DOWN(1, "下架商品"),
    ;

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

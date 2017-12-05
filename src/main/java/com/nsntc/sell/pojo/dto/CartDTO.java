package com.nsntc.sell.pojo.dto;

import lombok.Data;

/**
 * Class Name: CartDTO
 * Package: com.nsntc.sell.pojo.dto
 * Description: 购物车
 * @author wkm
 * Create DateTime: 2017/12/4 上午1:41
 * Version: 1.0
 */
@Data
public class CartDTO {

    /** 商品id */
    private String productId;

    /** 数量 */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}

package com.nsntc.sell.form;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * Class Name: ProductForm
 * Package: com.nsntc.sell.form
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/7 下午5:58
 * Version: 1.0
 */
@Data
public class ProductForm {

    @NotEmpty(message = "商品ID必填")
    private String productId;

    /** 名称 */
    @NotEmpty(message = "商品名称必填")
    private String productName;

    /** 单价 */
    @NotEmpty(message = "商品单价必填")
    private BigDecimal productPrice;

    /** 库存 */
    @NotEmpty(message = "商品库存必填")
    private Integer productStock;

    /** 描述 */
    private String productDescription;

    /** 小图 */
    @NotEmpty(message = "商品图片必填")
    private String productIcon;

    /** 类目编号 */
    @NotEmpty(message = "商品类目编号必填")
    private Integer categoryType;
}

package com.nsntc.sell.pojo.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Class Name: ProductInfo
 * Package: com.nsntc.sell.pojo
 * Description: 商品
 * @author wkm
 * Create DateTime: 2017/12/2 下午7:58
 * Version: 1.0
 */
@Data
@Entity(name = "product_info")
public class ProductInfo {

    @Id
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDesc;
    private String productIcon;
    private Integer productStatus;
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;
}

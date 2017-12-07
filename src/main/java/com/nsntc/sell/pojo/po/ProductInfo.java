package com.nsntc.sell.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nsntc.sell.enums.ProductStatusEnum;
import com.nsntc.sell.util.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicUpdate
@Entity(name = "product_info")
public class ProductInfo {

    @Id
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDesc;
    private String productIcon;
    private Integer productStatus = ProductStatusEnum.DOWN.getCode();
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;

    /** 根据int值返回枚举类型message */
    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }
}

package com.nsntc.sell.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Class Name: ProductInfo
 * Package: com.nsntc.sell.pojo
 * Description: 商品VO
 * @author wkm
 * Create DateTime: 2017/12/2 下午7:58
 * Version: 1.0
 */
@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private String productId;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("price")
    private BigDecimal productPrice;
    @JsonProperty("description")
    private String productDesc;
    @JsonProperty("icon")
    private String productIcon;
    @JsonProperty("createTime")
    private Date createTime;
    @JsonProperty("updateTime")
    private Date updateTime;
}

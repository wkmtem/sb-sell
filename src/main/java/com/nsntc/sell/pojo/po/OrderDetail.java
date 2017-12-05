package com.nsntc.sell.pojo.po;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Class Name: OrderDetail
 * Package: com.nsntc.sell.pojo.po
 * Description: 订单详情
 * @author wkm
 * Create DateTime: 2017/12/3 下午3:08
 * Version: 1.0
 */
@Data
@DynamicUpdate
@Entity(name = "order_detail")
public class OrderDetail {

    @Id
    private String detailId;
    private String orderId;
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private String productIcon;
    private Date createTime;
    private Date updateTime;
}

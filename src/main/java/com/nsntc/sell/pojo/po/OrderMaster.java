package com.nsntc.sell.pojo.po;

import com.nsntc.sell.enums.OrderStatusEnum;
import com.nsntc.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Class Name: OrderMaster
 * Package: com.nsntc.sell.pojo.po
 * Description: 订单
 * @author wkm
 * Create DateTime: 2017/12/3 下午2:37
 * Version: 1.0
 */
@Data
@DynamicUpdate
@Entity(name = "order_master")
public class OrderMaster {

    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    private Date createTime;
    private Date updateTime;
}

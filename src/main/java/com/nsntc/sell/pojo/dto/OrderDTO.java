package com.nsntc.sell.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nsntc.sell.enums.OrderStatusEnum;
import com.nsntc.sell.enums.PayStatusEnum;
import com.nsntc.sell.pojo.po.OrderDetail;
import com.nsntc.sell.util.EnumUtil;
import com.nsntc.sell.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Class Name: OrderDTO
 * Package: com.nsntc.sell.pojo.dto
 * Description: 
 * @author wkm
 * Create DateTime: 2017/12/3 下午3:41
 * Version: 1.0
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) /** 已过时 */
//@JsonInclude(JsonInclude.Include.NON_NULL) /** json序列化不为null的属性, 通过yml全局配置 */
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 创建时间 自定义扩展注解 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间 自定义扩展注解 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    /** 根据int值返回枚举类型message */
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}

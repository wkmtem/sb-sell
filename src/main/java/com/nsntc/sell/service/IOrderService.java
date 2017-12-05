package com.nsntc.sell.service;

import com.nsntc.sell.pojo.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Class Name: IOrderService
 * Package: com.nsntc.sell.service
 * Description: 订单service
 * @author wkm
 * Create DateTime: 2017/12/3 下午3:36
 * Version: 1.0
 */
public interface IOrderService {

    /** 创建订单 */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单 */
    OrderDTO findOne(String orderId);

    /** 买家: 查询订单列表 */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /** 取消订单、退款 */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单 */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单 */
    OrderDTO paid(OrderDTO orderDTO);

    /** 卖家: 查询订单列表(分页) */
    Page<OrderDTO> findList(Pageable pageable);

}

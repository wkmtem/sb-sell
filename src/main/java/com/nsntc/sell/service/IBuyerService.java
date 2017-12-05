package com.nsntc.sell.service;

import com.nsntc.sell.pojo.dto.OrderDTO;

/**
 * Class Name: IBuyerService
 * Package: com.nsntc.sell.service
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/4 下午6:32
 * Version: 1.0
 */
public interface IBuyerService {

    /** 查询一个订单 */
    OrderDTO findOrderOne(String openid, String orderId);

    /** 取消订单 */
    OrderDTO cancelOrder(String openid, String orderId);
}

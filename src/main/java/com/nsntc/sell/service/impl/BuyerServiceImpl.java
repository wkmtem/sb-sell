package com.nsntc.sell.service.impl;

import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.pojo.dto.OrderDTO;
import com.nsntc.sell.service.IBuyerService;
import com.nsntc.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class Name: BuyerServiceImpl
 * Package: com.nsntc.sell.service.impl
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/4 下午6:33
 * Version: 1.0
 */
@Service
@Slf4j
public class BuyerServiceImpl implements IBuyerService {

    @Autowired
    private IOrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return this.checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = this.checkOrderOwner(openid, orderId);
        if (null == orderDTO) {
            log.error("【取消订单】查不到该订单, orderId={}", orderId);
            throw new ExceptionCustom(HttpResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = this.orderService.findOne(orderId);
        if (null == orderDTO) {
            return null;
        }
        /** 判断是否是自己的订单 */
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new ExceptionCustom(HttpResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}

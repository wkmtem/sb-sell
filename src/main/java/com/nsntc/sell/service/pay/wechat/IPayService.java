package com.nsntc.sell.service.pay.wechat;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.nsntc.sell.pojo.dto.OrderDTO;

/**
 * Class Name: IPayService
 * Package: com.nsntc.sell.service.pay
 * Description: 微信支付
 * @author wkm
 * Create DateTime: 2017/12/5 下午8:07
 * Version: 1.0
 */
public interface IPayService {

    /** 微信统一下单API, 创建预付单 */
    PayResponse create(OrderDTO orderDTO);

    /** 取消订单时，退款(需证书支持) */
    RefundResponse refund(OrderDTO orderDTO);

    /** 微信支付异步通知(回调) */
    void notify(String notifyData);
}

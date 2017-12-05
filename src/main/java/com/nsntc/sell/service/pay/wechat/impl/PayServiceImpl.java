package com.nsntc.sell.service.pay.wechat.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.pojo.dto.OrderDTO;
import com.nsntc.sell.service.IOrderService;
import com.nsntc.sell.service.pay.wechat.IPayService;
import com.nsntc.sell.util.GsonUtil;
import com.nsntc.sell.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class Name: PayServiceImpl
 * Package: com.nsntc.sell.service.pay.wechat.impl
 * Description: 微信支付
 * @author wkm
 * Create DateTime: 2017/12/5 下午10:19
 * Version: 1.0
 */
@Service
@Slf4j
public class PayServiceImpl implements IPayService {

    private static final String ORDER_NAME = "微信点餐订单";

    /** WechatPayConfig的bean */
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private IOrderService orderService;

    /**
     * Method Name: create
     * Description: 微信统一下单API, 创建预付单
     * Create DateTime: 2017/12/5 下午8:11
     * @param orderDTO
     * @return
     */
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        /** 买家openid */
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        /** 订单总金额 */
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        /** 订单号 */
        payRequest.setOrderId(orderDTO.getOrderId());
        /** 订单名称 */
        payRequest.setOrderName(ORDER_NAME);
        /** 支付类型: 微信公众账号支付 */
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付, request={}", GsonUtil.toJson(payRequest));

        /** 返回packAge:prepay_id=预付单信息 */
        PayResponse payResponse = this.bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付, response={}", GsonUtil.toJson(payResponse));
        return payResponse;
    }

    /**
     * Method Name: refund
     * Description: 取消订单时，退款(需证书支持)
     * Create DateTime: 2017/12/5 下午8:11
     * @param orderDTO
     * @return
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        /** 构造入参 */
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request={}", GsonUtil.toJson(refundRequest));

        /** outRefundNo退款流水号 */
        RefundResponse refundResponse = this.bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}", GsonUtil.toJson(refundResponse));
        return refundResponse;
    }

    /**
     * Method Name: notify
     * Description: 微信支付异步通知(回调)
     * Create DateTime: 2017/12/5 下午11:37
     * @param notifyData
     */
    @Override
    public void notify(String notifyData) {

        /** 验证签名和验证支付的状态 */
        /** transaction_id == outTradeNo 微信交易流水号 */
        PayResponse payResponse = this.bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知, payResponse={}", GsonUtil.toJson(payResponse));

        /** 查询订单 */
        OrderDTO orderDTO = this.orderService.findOne(payResponse.getOrderId());

        /** 判断订单是否存在 */
        if (null == orderDTO) {
            log.error("【微信支付】异步通知, 订单不存在, orderId={}", payResponse.getOrderId());
            throw new ExceptionCustom(HttpResultEnum.ORDER_NOT_EXIST);
        }
        /** 判断金额是否一致 */
        if (!MathUtil.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue())) {
            log.error("【微信支付】异步通知, 订单金额不一致, orderId={}, 微信通知金额={}, 系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new ExceptionCustom(HttpResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        /** 验证支付人(下单人 == 支付人)，此处不做校验 */

        /** 修改订单的支付状态 */
        this.orderService.paid(orderDTO);
    }
}

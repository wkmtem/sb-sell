package com.nsntc.sell.service.push.wechat;

import com.nsntc.sell.pojo.dto.OrderDTO;

/**
 * Class Name: IWechatPushMessageService
 * Package: com.nsntc.sell.service.push.wechat
 * Description: 微信消息推送
 * @author wkm
 * Create DateTime: 2017/12/8 下午8:10
 * Version: 1.0
 */
public interface IWechatPushMessageService {

    /**
     * Method Name: orderStatus
     * Description: 订单状态变更消息
     * Create DateTime: 2017/12/8 下午8:11
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}

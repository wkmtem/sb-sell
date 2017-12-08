package com.nsntc.sell.service.push.wechat.impl;

import com.nsntc.sell.config.wechat.WechatAccountConfig;
import com.nsntc.sell.pojo.dto.OrderDTO;
import com.nsntc.sell.service.push.wechat.IWechatPushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Class Name: WechatPushMessageServiceImpl
 * Package: com.nsntc.sell.service.push.wechat.impl
 * Description: 微信消息推送
 *
 * @author wkm
 * Create DateTime: 2017/12/8 下午8:12
 * Version: 1.0
 */
@Service
@Slf4j
public class WechatPushMessageServiceImpl implements IWechatPushMessageService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {

        /** 推送数据 */
        List<WxMpTemplateData> data = Arrays.asList(
                /** String name, String value, String color */
                /** 相对于模版项显示的值(模版项名称固定) */
                new WxMpTemplateData("first", "模版标题"),
                new WxMpTemplateData("keyword1", "根据模版显示项目对应值"),
                new WxMpTemplateData("keyword2", "根据模版显示项目对应值"),
                new WxMpTemplateData("keyword3", "根据模版显示项目对应值" + orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4", "根据模版显示项目对应值" + orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5", "根据模版显示项目对应值￥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("remark", "模版结尾")
        );

        WxMpTemplateMessage.WxMpTemplateMessageBuilder templateMessageBuilder = WxMpTemplateMessage.builder();
        /** 消息模版id */
        templateMessageBuilder.templateId(this.wechatAccountConfig.getTempIdOrderStatus());
        /** 公众号的买家openid */
        templateMessageBuilder.toUser(orderDTO.getBuyerOpenid());
//        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
//        templateMessage.setTemplateId(this.wechatAccountConfig.getTemplateId().get("orderStatus"));
//        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        templateMessageBuilder.data(data);
        WxMpTemplateMessage templateMessage = templateMessageBuilder.build();
        //templateMessage.setData(data);
        /** 避免推送失败, 造成service回滚 */
        try {
            this.wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模版消息】发送失败, {}", e);
        }
    }
}

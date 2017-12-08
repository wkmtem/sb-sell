package com.nsntc.sell.service.impl;

import com.nsntc.sell.pojo.dto.OrderDTO;
import com.nsntc.sell.service.system.IOrderService;
import com.nsntc.sell.service.push.wechat.impl.WechatPushMessageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WeChatPushMessageServiceImplTest {

    @Autowired
    private WechatPushMessageServiceImpl pushMessageService;
    @Autowired
    private IOrderService orderService;

    @Test
    public void orderStatus() throws Exception {

        OrderDTO orderDTO = orderService.findOne("1499097346204378750");
        pushMessageService.orderStatus(orderDTO);
    }

}
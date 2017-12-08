package com.nsntc.sell.controller.pay;

import com.lly835.bestpay.model.PayResponse;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.pojo.dto.OrderDTO;
import com.nsntc.sell.service.system.IOrderService;
import com.nsntc.sell.service.pay.wechat.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Class Name: WechatPayController
 * Package: com.nsntc.sell.controller.pay
 * Description: 微信支付
 * @author wkm
 * Create DateTime: 2017/12/5 下午7:55
 * Version: 1.0
 */
@Controller
@RequestMapping("wechatPay")
public class WechatPayController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPayService payService;

    /**
     * Method Name: create
     * Description: 创建微信支付, 跳转微信内支付的freemarker填充预付单信息
     * Create DateTime: 2017/12/5 下午7:59
     * @param orderId
     * @param returnUrl
     * @param map
     * @return
     */
    @GetMapping("create")
    public ModelAndView create(@RequestParam(value = "orderId", required = true) String orderId,
                               @RequestParam(value = "returnUrl", required = true) String returnUrl,
                               Map<String, Object> map) {
        /** 查询订单 */
        OrderDTO orderDTO = this.orderService.findOne(orderId);
        if (null == orderDTO) {
            throw new ExceptionCustom(HttpResultEnum.ORDER_NOT_EXIST);
        }

        /** 向微信发起支付 */
        PayResponse payResponse = this.payService.create(orderDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        /** templates下完整路径 */
        return new ModelAndView("pay/wechat/create", map);
    }

    /**
     * Method Name: notify
     * Description: 微信异步通知(回调)，修改订单状态
     * Create DateTime: 2017/12/5 下午8:01
     * @param notifyData
     * @return
     */
    @PostMapping("notify")
    public ModelAndView notify(@RequestBody String notifyData) { /** 微信传递参数格式: xml  */

        this.payService.notify(notifyData);
        /** 借助freemarker模版引擎，返回给微信处理结果 */
        return new ModelAndView("pay/wechat/success");
    }
}

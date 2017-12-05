package com.nsntc.sell.controller;

import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.pojo.dto.OrderDTO;
import com.nsntc.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Class Name: SellerOrderController
 * Package: com.nsntc.sell.controller
 * Description: 卖家端订单
 * @author wkm
 * Create DateTime: 2017/12/6 上午1:12
 * Version: 1.0
 */
@Controller
@RequestMapping("seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * Method Name: list
     * Description: 订单列表
     * Create DateTime: 2017/12/6 上午1:12
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             /** 未定义defaultValue: required自动为true, 定义了defaultValue: required自动为false */
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = this.orderService.findList(request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
//        orderDTOPage.getTotalPages()
        return new ModelAndView("order/list", map);
    }

    /**
     * Method Name: cancel
     * Description: 取消订单
     * Create DateTime: 2017/12/6 上午1:14
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId", required = true) String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = this.orderService.findOne(orderId);
            this.orderService.cancel(orderDTO);
        } catch (ExceptionCustom e) {
            log.error("【卖家端取消订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", HttpResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }

    /**
     * Method Name: detail
     * Description: 订单详情
     * Create DateTime: 2017/12/6 上午1:16
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("detail")
    public ModelAndView detail(@RequestParam(value = "orderId", required = true) String orderId,
                               Map<String, Object> map) {
        OrderDTO orderDTO = null;
        try {
            orderDTO = this.orderService.findOne(orderId);
        }catch (ExceptionCustom e) {
            log.error("【卖家端查询订单详情】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    /**
     * Method Name: finished
     * Description: 完结订单
     * Create DateTime: 2017/12/6 上午1:16
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("finish")
    public ModelAndView finished(@RequestParam(value = "orderId", required = true) String orderId,
                                 Map<String, Object> map) {
        try {
            OrderDTO orderDTO = this.orderService.findOne(orderId);
            this.orderService.finish(orderDTO);
        } catch (ExceptionCustom e) {
            log.error("【卖家端完结订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", HttpResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }
}

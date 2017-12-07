package com.nsntc.sell.controller.buyer;

import com.nsntc.sell.bean.HttpResult;
import com.nsntc.sell.converter.OrderForm2OrderDTOConverter;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.form.OrderForm;
import com.nsntc.sell.pojo.dto.OrderDTO;
import com.nsntc.sell.service.buyer.IBuyerService;
import com.nsntc.sell.service.IOrderService;
import com.nsntc.sell.util.HttpResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Name: BuyerOrderController
 * Package: com.nsntc.sell.controller
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/4 下午4:56
 * Version: 1.0
 */
@RestController
@RequestMapping("buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IBuyerService buyerService;

    /**
     * Method Name: create
     * Description: 创建订单
     * Create DateTime: 2017/12/4 下午4:57
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("create")
    public HttpResult<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new ExceptionCustom(HttpResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new ExceptionCustom(HttpResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = this.orderService.create(orderDTO);
        return HttpResultUtil.success(new HashMap<String, String>(1).put("orderId", createResult.getOrderId()));
    }

    /**
     * Method Name: list
     * Description: 订单列表
     * Create DateTime: 2017/12/4 下午4:58
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public HttpResult<List<OrderDTO>> list(@RequestParam(value = "openid", required = true) String openid,
                                           @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                           @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new ExceptionCustom(HttpResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = this.orderService.findList(openid, pageRequest);
        return HttpResultUtil.success(orderDTOPage.getContent());
    }


    /**
     * Method Name: detail
     * Description: 订单详情
     * Create DateTime: 2017/12/4 下午4:58
     * @param openid
     * @param orderId
     * @return
     */
    @GetMapping("detail")
    public HttpResult<OrderDTO> detail(@RequestParam("openid") String openid,
                                       @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = this.buyerService.findOrderOne(openid, orderId);
        return HttpResultUtil.success(orderDTO);
    }

    /**
     * Method Name: cancel
     * Description: 取消订单
     * Create DateTime: 2017/12/4 下午4:58
     * @param openid
     * @param orderId
     * @return
     */
    @PostMapping("cancel")
    public HttpResult cancel(@RequestParam("openid") String openid,
                             @RequestParam("orderId") String orderId) {
        this.buyerService.cancelOrder(openid, orderId);
        return HttpResultUtil.success();
    }

    /** springboot对delete封装，无须传递_method=METHOD */
}
package com.nsntc.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.form.OrderForm;
import com.nsntc.sell.pojo.dto.OrderDTO;
import com.nsntc.sell.pojo.po.OrderDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: OrderForm2OrderDTOConverter
 * Package: com.nsntc.sell.converter
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/4 下午5:12
 * Version: 1.0
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = null;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {}.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new ExceptionCustom(HttpResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}

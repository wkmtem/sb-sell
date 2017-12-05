package com.nsntc.sell.converter;

import com.nsntc.sell.pojo.dto.OrderDTO;
import com.nsntc.sell.pojo.po.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Name: OrderMaster2OrderDTOConverter
 * Package: com.nsntc.sell.converter
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/4 下午3:10
 * Version: 1.0
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream()
                .map(e -> convert(e))
                .collect(Collectors.toList());
    }
}

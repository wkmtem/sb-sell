package com.nsntc.sell.repository;

import com.nsntc.sell.pojo.po.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Class Name: OrderDetailRepository
 * Package: com.nsntc.sell.repository
 * Description: 订单详情dao
 * @author wkm
 * Create DateTime: 2017/12/3 下午3:20
 * Version: 1.0
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * Method Name: findByOrderId
     * Description: 根据订单id获取订单详情集合
     * Create DateTime: 2017/12/3 下午3:22
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}

package com.nsntc.sell.repository;

import com.nsntc.sell.pojo.po.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class Name: OrderMasterRepository
 * Package: com.nsntc.sell.repository
 * Description: 订单dao
 * @author wkm
 * Create DateTime: 2017/12/3 下午3:16
 * Version: 1.0
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String>{

    /**
     * Method Name: findByBuyerOpenid
     * Description: 根据用户微信openid分页获取订单
     * Create DateTime: 2017/12/3 下午3:18
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}

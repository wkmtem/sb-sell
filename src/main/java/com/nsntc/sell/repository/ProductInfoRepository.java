package com.nsntc.sell.repository;

import com.nsntc.sell.pojo.po.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Class Name: ProductInfoRepository
 * Package: com.nsntc.sell.repository
 * Description: 
 * @author wkm
 * Create DateTime: 2017/12/2 下午8:09
 * Version: 1.0
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /**
     * Method Name: findByProductStatus
     * Description: 根据状态获取商品集合
     * Create DateTime: 2017/12/2 下午8:11
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}

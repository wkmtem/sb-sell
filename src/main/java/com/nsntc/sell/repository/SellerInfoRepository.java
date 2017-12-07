package com.nsntc.sell.repository;

import com.nsntc.sell.pojo.po.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class Name: SellerInfoRepository
 * Package: com.nsntc.sell.repository
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/7 下午11:50
 * Version: 1.0
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    /**
     * Method Name: findByOpenid
     * Description:
     * Create DateTime: 2017/12/7 下午11:50
     * @param openid
     * @return
     */
    SellerInfo findByOpenid(String openid);
}

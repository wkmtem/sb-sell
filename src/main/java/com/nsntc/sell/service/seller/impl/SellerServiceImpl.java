package com.nsntc.sell.service.seller.impl;

import com.nsntc.sell.pojo.po.SellerInfo;
import com.nsntc.sell.repository.SellerInfoRepository;
import com.nsntc.sell.service.seller.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class Name: SellerServiceImpl
 * Package: com.nsntc.sell.service.seller.impl
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/7 下午11:54
 * Version: 1.0
 */
@Service
public class SellerServiceImpl implements ISellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    /**
     * Method Name: findSellerInfoByOpenid
     * Description: 通过openid查询卖家端信息
     * Create DateTime: 2017/12/7 下午11:55
     * @param openid
     * @return
     */
    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return this.sellerInfoRepository.findByOpenid(openid);
    }

    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
        return this.sellerInfoRepository.save(sellerInfo);
    }
}

package com.nsntc.sell.service.seller;

import com.nsntc.sell.pojo.po.SellerInfo;

/**
 * 卖家端
 * Created by 廖师兄
 * 2017-07-29 23:14
 */
public interface ISellerService {

    /**
     * Method Name: findSellerInfoByOpenid
     * Description: 通过openid查询卖家端信息
     * Create DateTime: 2017/12/7 下午11:53
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);

    SellerInfo save(SellerInfo sellerInfo);
}

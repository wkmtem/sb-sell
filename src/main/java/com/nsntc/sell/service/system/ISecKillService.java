package com.nsntc.sell.service.system;

/**
 * Class Name: ISeckillService
 * Package: com.nsntc.sell.service.system
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/9 上午2:14
 * Version: 1.0
 */
public interface ISecKillService {

    /**
     * Method Name: querySecKillProductInfo
     * Description: 查询秒杀活动特价商品信息
     * Create DateTime: 2017/12/9 上午2:16
     * @param productId
     * @return
     */
    String querySecKillProductInfo(String productId);

    /**
     * Method Name: orderProductMockDiffUser
     * Description:
     * Create DateTime: 2017/12/9 上午2:18
     * @param productId
     */
    void orderProductMockDiffUser(String productId);
}


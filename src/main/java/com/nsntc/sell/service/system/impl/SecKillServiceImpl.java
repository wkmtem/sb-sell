package com.nsntc.sell.service.system.impl;

import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.component.redis.RedisLock;
import com.nsntc.sell.service.system.ISecKillService;
import com.nsntc.sell.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillServiceImpl implements ISecKillService {

    @Autowired
    private RedisLock redisLock;

    /** 超时时间: 10s */
    private static final long TIMEOUT = 10* 1000;

    /**
     * 特价商品，限量100000份
     */
    /** 商品信息表 */
    static Map<String, Integer> products;
    /** 库存表 */
    static Map<String, Integer> stock;
    /** 秒杀成功订单 */
    static Map<String, Object> orders;

    static {
        products = new HashMap<>(16);
        stock = new HashMap<>(100000);
        orders = new HashMap<>(100000);
        products.put("123456", 100000);
        stock.put("123456", 100000);
    }

    private String queryMap(String productId) {
        return "特价活动, xxx商品特价，限量份" + products.get(productId)
                + "还剩: " + stock.get(productId) + "份"
                + "已成功下单数量: " + orders.size() + "单";
    }

    @Override
    public String querySecKillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    /**
     * Method Name: orderProductMockDiffUser
     * Description: 方法上添加了synchronized，解决了每次只有1个线程访问该方法，虽然解决了超卖问题，但是效率十分低下
     *              无法做到细粒度控制
     *              只适合单点的情况，服务器集群不适合
     *              解决: redis分布锁(SETNX: SET if Not eXists)
     *                   将key设置值为value:
     *                      如果key不存在，这种情况下等同于SET命令，返回1: key被设置了
     *                      当key存在时，什么也不做，返回0: key没有被设置
     *                   通过!SETNX与GETSET命令结合使用，达到秒杀效果
     * Create DateTime: 2017/12/9 上午2:50
     * @param productId
     */
    @Override
    public void orderProductMockDiffUser(String productId) {

        String time = String.valueOf(System.currentTimeMillis() + TIMEOUT);
        /** redis 加锁不成功 */
        if (!this.redisLock.lock(productId, time)) {
            throw new ExceptionCustom(-2, "哎呦喂～～运气不好，换个姿势再来一次～～");
        }
        /** 下单流程 */
        this.doMockDiffUser(productId);
        /** redis 解锁 */
        this.redisLock.unlock(productId, time);
    }

    /**
     * Method Name: doMockDiffUser
     * Description: 下单流程
     * Create DateTime: 2017/12/9 上午4:06
     * @param productId
     */
    private void doMockDiffUser(String productId) {
        /** 查询库存，0为活动结束 */
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new ExceptionCustom(-1, "活动结束啦！");
        }
        /** 下单 模拟不同用户的openid */
        orders.put(KeyUtil.genUniqueKey(), productId);
        /** 减库存 */
        stockNum --;
        try {
            Thread.sleep(100);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        /** 数量设置回去 */
        stock.put(productId, stockNum);
    }
}

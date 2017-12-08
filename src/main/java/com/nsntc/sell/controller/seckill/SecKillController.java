package com.nsntc.sell.controller.seckill;

import com.nsntc.sell.service.system.ISecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("skill")
@Slf4j
public class SecKillController {

    @Autowired
    private ISecKillService secKillService;

    /**
     * Method Name: query
     * Description: 查询秒杀活动特价商品信息
     * Create DateTime: 2017/12/9 上午2:08
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("query/{productId}")
    public String query(@PathVariable String productId) throws Exception {
        return this.secKillService.querySecKillProductInfo(productId);
    }

    /**
     * Method Name: skill
     * Description: 没有抢到获得"哎呦喂,xxx", 抢到则返回剩余库存量
     * Create DateTime: 2017/12/9 上午2:11
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("order/{productId}")
    public String skill(@PathVariable String productId) throws Exception {
        log.info("@skill request, productId={}", productId);
        this.secKillService.orderProductMockDiffUser(productId);
        return this.secKillService.querySecKillProductInfo(productId);
    }
}

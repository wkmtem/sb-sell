package com.nsntc.sell.repository;

import com.nsntc.sell.pojo.po.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void findByProductStatusTest() throws Exception {
        List<ProductInfo> byProductStatus = this.productInfoRepository.findByProductStatus(0);
        Assert.assertEquals(0, byProductStatus.size());
    }

}
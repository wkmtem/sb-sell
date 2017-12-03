package com.nsntc.sell.service.impl;

import com.nsntc.sell.enums.ProductStatusEnum;
import com.nsntc.sell.pojo.po.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOneTest() throws Exception {
        ProductInfo one = this.productService.findOne("1234");
        Assert.assertEquals("1234", one.getProductId());
    }

    @Test
    public void findUpAllTest() throws Exception {
        List<ProductInfo> upAll = this.productService.findUpAll();
        Assert.assertNotEquals(0, upAll.size());
    }

    @Test
    public void findAllTest() throws Exception {
        /** page 从0开始 */
        PageRequest pageRequest = new PageRequest(0, 20);
        Page<ProductInfo> all = this.productService.findAll(pageRequest);
        List<ProductInfo> content = all.getContent();
        if(!CollectionUtils.isEmpty(content)) {
            for(ProductInfo p : content){
                System.out.println(p.getProductName());
            }
        }
        Assert.assertNotEquals(0, all.getContent().size());
    }

    @Test
    //@Transactional
    public void saveTest() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1234");
        productInfo.setProductName("手机");
        productInfo.setProductPrice(new BigDecimal(1999.98));
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setProductStock(20);
        productInfo.setCategoryType(10);
        productInfo = this.productService.save(productInfo);
        Assert.assertNotNull(productInfo);
    }

}
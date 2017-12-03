package com.nsntc.sell.repository;

import com.nsntc.sell.pojo.po.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = productCategoryRepository.findOne(1);
        Assert.assertEquals("热销榜", productCategory.getCategoryName());
        Assert.assertNotNull(productCategory);
        Assert.assertNotEquals(null, productCategory); /** 不期望值, 实际值*/
    }

    @Test
    /** entirely rollback */
    @Transactional
    public void saveTest() {
        ProductCategory productCategory = productCategoryRepository.findOne(2);
        productCategory.setCategoryName("双十二专区");
        productCategory.setCategoryType(10);
        productCategoryRepository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(2, 3, 4);
        List<ProductCategory> byCategoryTypeIn = productCategoryRepository.findByCategoryTypeIn(list);
        for(ProductCategory p : byCategoryTypeIn){
            System.out.println(p.getCategoryName());
        }
        Assert.assertNotEquals(0, byCategoryTypeIn.size()); /** 不期望值, 实际值*/
    }

}
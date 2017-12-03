package com.nsntc.sell.service.impl;

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
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOneTest() throws Exception {
        ProductCategory one = this.categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), one.getCategoryId());

    }

    @Test
    public void findAllTest() throws Exception {
        List<ProductCategory> all = this.categoryService.findAll();
        Assert.assertNotEquals(0, all.size());

    }

    @Test
    public void findByCategoryTypeInTest() throws Exception {
        List<ProductCategory> list = this.categoryService.findByCategoryTypeIn(Arrays.asList(2, 3, 4));
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    @Transactional
    public void saveTest() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(11);
        productCategory.setCategoryName("测试");
        productCategory = this.categoryService.save(productCategory);
        Assert.assertNotNull(productCategory);
    }

}
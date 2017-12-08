package com.nsntc.sell.service.system.impl;

import com.nsntc.sell.pojo.po.ProductCategory;
import com.nsntc.sell.repository.ProductCategoryRepository;
import com.nsntc.sell.service.system.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class Name: CategoryServiceImpl
 * Package: com.nsntc.sell.service.system.impl
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/8 下午11:01
 * Version: 1.0
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    @Override
    public ProductCategory findOne(Integer categoryId) {
        return this.productCategoryRepository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return this.productCategoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return this.productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return this.productCategoryRepository.save(productCategory);
    }
}

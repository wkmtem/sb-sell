package com.nsntc.sell.service.system;

import com.nsntc.sell.pojo.po.ProductCategory;

import java.util.List;

/**
 * Class Name: ICategoryService
 * Package: com.nsntc.sell.service.system
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/8 下午11:00
 * Version: 1.0
 */
public interface ICategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}

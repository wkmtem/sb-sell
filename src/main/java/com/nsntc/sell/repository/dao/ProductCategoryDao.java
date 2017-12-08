package com.nsntc.sell.repository.dao;

import com.nsntc.sell.repository.mapper.ProductCategoryRepositoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class ProductCategoryDao {

    @Autowired
    private ProductCategoryRepositoryMapper productCategoryRepositoryMapper;

    public Integer insertByMap(Map<String, Object> map) {
        return this.productCategoryRepositoryMapper.insertByMap(map);
    }
}

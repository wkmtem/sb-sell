package com.nsntc.sell.service.impl;

import com.nsntc.sell.enums.ProductStatusEnum;
import com.nsntc.sell.pojo.po.ProductInfo;
import com.nsntc.sell.repository.ProductInfoRepository;
import com.nsntc.sell.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class Name: ProductServiceImpl
 * Package: com.nsntc.sell.service.impl
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/2 下午8:53
 * Version: 1.0
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return this.productInfoRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return this.productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return this.productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return this.productInfoRepository.save(productInfo);
    }
}

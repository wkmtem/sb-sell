package com.nsntc.sell.service.impl;

import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.enums.ProductStatusEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.pojo.dto.CartDTO;
import com.nsntc.sell.pojo.po.ProductInfo;
import com.nsntc.sell.repository.ProductInfoRepository;
import com.nsntc.sell.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseStock(List<CartDTO> cartDTOList) {

        ProductInfo productInfo = null;
        for (CartDTO cartDTO: cartDTOList) {
            productInfo = this.productInfoRepository.findOne(cartDTO.getProductId());
            if (null == productInfo) {
                throw new ExceptionCustom(HttpResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            this.productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseStock(List<CartDTO> cartDTOList) {

        ProductInfo productInfo = null;
        for (CartDTO cartDTO: cartDTOList) {
            productInfo = this.productInfoRepository.findOne(cartDTO.getProductId());
            if (null == productInfo) {
                throw new ExceptionCustom(HttpResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new ExceptionCustom(HttpResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            this.productInfoRepository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {

        ProductInfo productInfo = this.productInfoRepository.findOne(productId);
        if (null == productInfo) {
            throw new ExceptionCustom(HttpResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new ExceptionCustom(HttpResultEnum.PRODUCT_STATUS_ERROR);
        }
        /** 更新 */
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return this.productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = this.productInfoRepository.findOne(productId);
        if (null == productInfo) {
            throw new ExceptionCustom(HttpResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new ExceptionCustom(HttpResultEnum.PRODUCT_STATUS_ERROR);
        }
        /** 更新 */
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return this.productInfoRepository.save(productInfo);
    }
}

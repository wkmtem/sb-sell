package com.nsntc.sell.service.system.impl;

import com.nsntc.sell.enums.HttpResultEnum;
import com.nsntc.sell.enums.ProductStatusEnum;
import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.pojo.dto.CartDTO;
import com.nsntc.sell.pojo.po.ProductInfo;
import com.nsntc.sell.repository.ProductInfoRepository;
import com.nsntc.sell.service.system.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class Name: ProductServiceImpl
 * Package: com.nsntc.sell.service.system.impl
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/8 下午11:01
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

    /**
     * Method Name: onSale
     * Description: 上架
     * Create DateTime: 2017/12/7 下午4:32
     * @param productId
     * @return
     */
    @Override
    public ProductInfo onSale(String productId) {
        return this.onOrOffSale(productId, ProductStatusEnum.UP.getCode());
    }

    /**
     * Method Name: offSale
     * Description: 下架
     * Create DateTime: 2017/12/7 下午4:32
     * @param productId
     * @return
     */
    @Override
    public ProductInfo offSale(String productId) {
        return this.onOrOffSale(productId, ProductStatusEnum.DOWN.getCode());
    }

    /**
     * Method Name: onOrOffSale
     * Description: 上下架操作
     * Create DateTime: 2017/12/7 下午4:46
     * @param productId
     * @param type
     * @return
     */
    private ProductInfo onOrOffSale(String productId, Integer type) {
        ProductInfo productInfo = this.productInfoRepository.findOne(productId);
        if (null == productInfo) {
            throw new ExceptionCustom(HttpResultEnum.PRODUCT_NOT_EXIST);
        }
        /** 上架 */
        if (type.equals(ProductStatusEnum.UP.getCode())) {
            if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
                throw new ExceptionCustom(HttpResultEnum.PRODUCT_STATUS_ERROR);
            }
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        /** 下架*/
        } else {
            if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
                throw new ExceptionCustom(HttpResultEnum.PRODUCT_STATUS_ERROR);
            }
            productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        }
        /** 更新 */
        return this.productInfoRepository.save(productInfo);
    }
}

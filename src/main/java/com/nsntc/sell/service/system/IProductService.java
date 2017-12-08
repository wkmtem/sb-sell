package com.nsntc.sell.service.system;

import com.nsntc.sell.pojo.dto.CartDTO;
import com.nsntc.sell.pojo.po.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Class Name: IProductService
 * Package: com.nsntc.sell.service.system
 * Description: 商品
 * @author wkm
 * Create DateTime: 2017/12/8 下午11:00
 * Version: 1.0
 */
public interface IProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    /** 加库存 */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减库存 */
    void decreaseStock(List<CartDTO> cartDTOList);

    /** 上架 */
    ProductInfo onSale(String productId);

    /** 下架 */
    ProductInfo offSale(String productId);
}

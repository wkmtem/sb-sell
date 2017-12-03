package com.nsntc.sell.repository;

import com.nsntc.sell.pojo.po.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Class Name: ProductCategoryRepository
 * Package: com.nsntc.sell.repository
 * Description: 类目dao接口
 * @author wkm
 * Create DateTime: 2017/12/2 下午3:17
 * Version: 1.0
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    /**
     * Method Name: findByCategoryTypeIn
     * Description: 根据类目id集合获取类目集合
     * Create DateTime: 2017/12/2 下午4:56
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}

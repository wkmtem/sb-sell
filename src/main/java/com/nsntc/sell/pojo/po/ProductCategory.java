package com.nsntc.sell.pojo.po;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Class Name: ProductCategory
 * Package: com.nsntc.sell.pojo
 * Description: 类目
 * @author wkm
 * Create DateTime: 2017/12/2 下午3:07
 * Version: 1.0
 */
//@Table /** IllegalArgumentException: Not a managed type */
@Data
@DynamicUpdate
@Entity(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue
    private Integer categoryId;
    private String categoryName;
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;

    public ProductCategory() {}

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}

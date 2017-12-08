package com.nsntc.sell.repository.mapper;

import com.nsntc.sell.pojo.po.ProductCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

/**
 * Class Name: ProductCategoryRepositoryMapper
 * Package: com.nsntc.sell.repository.mapper
 * Description: 
 * @author wkm
 * Create DateTime: 2017/12/9 上午12:32
 * Version: 1.0
 */
public interface ProductCategoryRepositoryMapper {

    @Results(id = "BaseResultMap", value = {
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_name", property = "categoryName"),
            @Result(column = "category_type", property = "categoryType"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })

    @Insert("insert into product_category(category_name, category_type) values (#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
    Integer insertByMap(Map<String, Object> map);

    @Insert("insert into product_category(category_name, category_type) values (#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
    Integer insertByObject(ProductCategory productCategory);

    @Select("select * from product_category where category_type = #{categoryType}")
    @ResultMap(value = "BaseResultMap")
    ProductCategory findByCategoryType(Integer categoryType);

    @Select("select * from product_category where category_Name = #{categoryName}")
    @ResultMap(value = "BaseResultMap")
    List<ProductCategory> findByCategoryName(String categoryName);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    Integer updateByCategoryType(@Param(value = "categoryName")String categoryName,
                                 @Param(value = "categoryType")Integer categoryType);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    Integer updateByObject(ProductCategory productCategory);

    @Delete("delete from product_category where category_type = #{categoryType}")
    Integer deleteByCategoryType(Integer categoryType);
}

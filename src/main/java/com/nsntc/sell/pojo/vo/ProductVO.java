package com.nsntc.sell.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nsntc.sell.pojo.vo.ProductInfoVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Class Name: ProductVO
 * Package: com.nsntc.sell.vo
 * Description: 返回商品VO
 *
 * @author wkm
 * Create DateTime: 2017/12/2 下午11:23
 * Version: 1.0
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 9075636262073931671L;

    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}

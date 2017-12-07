package com.nsntc.sell.form;

import lombok.Data;

/**
 * Class Name: CategoryForm
 * Package: com.nsntc.sell.form
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/7 下午6:53
 * Version: 1.0
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /** 类目名称 */
    private String categoryName;

    /** 类目编号 */
    private Integer categoryType;
}

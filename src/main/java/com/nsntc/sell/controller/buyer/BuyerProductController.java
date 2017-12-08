package com.nsntc.sell.controller.buyer;

import com.nsntc.sell.bean.HttpResult;
import com.nsntc.sell.pojo.po.ProductCategory;
import com.nsntc.sell.pojo.po.ProductInfo;
import com.nsntc.sell.service.system.ICategoryService;
import com.nsntc.sell.service.system.IProductService;
import com.nsntc.sell.util.HttpResultUtil;
import com.nsntc.sell.pojo.vo.ProductInfoVO;
import com.nsntc.sell.pojo.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Name: BuyerProductController
 * Package: com.nsntc.sell.controller
 * Description: 买家
 * @author wkm
 * Create DateTime: 2017/12/2 下午10:39
 * Version: 1.0
 */
@RestController
@RequestMapping("buyer/product")
public class BuyerProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    /**
     * Method Name: list
     * Description: 获取上架商品集合
     * Create DateTime: 2017/12/2 下午11:43
     * @return
     */
    @GetMapping(value = "list")
    public ResponseEntity<HttpResult> list() {

        List<ProductInfoVO> productInfoVOList = null;
        ProductInfoVO productInfoVO = null;
        List<ProductVO> productVOList = null;
        ProductVO productVO = null;

        try {
            /** 上架商品 */
            List<ProductInfo> productInfoList = this.productService.findUpAll();
            if (!CollectionUtils.isEmpty(productInfoList)) {
                productVOList = new ArrayList<>();

                /** 所有类目 lambda表达式 */
                List<Integer> categoryTypeList = productInfoList
                        .stream()
                        .map(e -> e.getCategoryType())
                        .collect(Collectors.toList());
                List<ProductCategory> productCategoryList = this.categoryService.findByCategoryTypeIn(categoryTypeList);

                /** 组装 */
                for (ProductCategory c : productCategoryList) {
                    productVO = new ProductVO();
                    productVO.setCategoryName(c.getCategoryName());
                    productVO.setCategoryType(c.getCategoryType());

                    productInfoVOList = new ArrayList<>();
                    for (ProductInfo p : productInfoList) {
                        if (p.getCategoryType().equals(c.getCategoryType())) {
                            productInfoVO = new ProductInfoVO();
                            BeanUtils.copyProperties(p, productInfoVO);
                            productInfoVOList.add(productInfoVO);
                        }
                    }
                    productVO.setProductInfoVOList(productInfoVOList);
                    productVOList.add(productVO);
                }
            }
            return ResponseEntity.ok(HttpResultUtil.success(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    productInfoVOList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /** 500 */
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

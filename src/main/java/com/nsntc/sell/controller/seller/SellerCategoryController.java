package com.nsntc.sell.controller.seller;

import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.form.CategoryForm;
import com.nsntc.sell.pojo.po.ProductCategory;
import com.nsntc.sell.service.ICategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Class Name: SellerCategoryController
 * Package: com.nsntc.sell.controller.seller
 * Description: 卖家类目
 * @author wkm
 * Create DateTime: 2017/12/7 下午6:54
 * Version: 1.0
 */
@Controller
@RequestMapping("seller/category")
public class SellerCategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * Method Name: list
     * Description: 类目列表
     * Create DateTime: 2017/12/7 下午6:55
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = this.categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * Method Name: index
     * Description: 展示
     * Create DateTime: 2017/12/7 下午6:55
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (null != categoryId) {
            ProductCategory productCategory = this.categoryService.findOne(categoryId);
            map.put("category", productCategory);
        }
        return new ModelAndView("category/index", map);
    }

    /**
     * Method Name: save
     * Description: 保存/更新
     * Create DateTime: 2017/12/7 下午6:56
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("save")
    public ModelAndView save(@Valid CategoryForm form, BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (null != form.getCategoryId()) {
                productCategory = this.categoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            this.categoryService.save(productCategory);
        } catch (ExceptionCustom e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}

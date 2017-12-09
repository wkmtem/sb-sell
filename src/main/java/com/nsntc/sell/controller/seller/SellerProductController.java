package com.nsntc.sell.controller.seller;

import com.nsntc.sell.exception.ExceptionCustom;
import com.nsntc.sell.form.ProductForm;
import com.nsntc.sell.pojo.po.ProductCategory;
import com.nsntc.sell.pojo.po.ProductInfo;
import com.nsntc.sell.service.system.ICategoryService;
import com.nsntc.sell.service.system.IProductService;
import com.nsntc.sell.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
 * Class Name: SellerProductController
 * Package: com.nsntc.sell.controller.seller
 * Description: 卖家端商品
 * @author wkm
 * Create DateTime: 2017/12/7 下午3:50
 * Version: 1.0
 */
@Controller
@RequestMapping("seller/product")
@CacheConfig(cacheNames = {"product"})
public class SellerProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    /**
     * Method Name: list
     * Description: 商品列表
     * Create DateTime: 2017/12/7 下午3:52
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = this.productService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    /**
     * Method Name: onSale
     * Description: 商品上架
     * Create DateTime: 2017/12/7 下午3:53
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            this.productService.onSale(productId);
        } catch (ExceptionCustom e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * Method Name: offSale
     * Description: 商品下架
     * Create DateTime: 2017/12/7 下午3:54
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            this.productService.offSale(productId);
        } catch (ExceptionCustom e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * Method Name: index
     * Description: 商品详情模版
     * Create DateTime: 2017/12/7 下午3:54
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                      Map<String, Object> map) {
        if (StringUtils.isNotEmpty(productId)) {
            ProductInfo productInfo = this.productService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        /** 所有类目 */
        List<ProductCategory> categoryList = this.categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("product/index", map);
    }

    /**
     * Method Name: save
     * Description: 保存/更新
     * Create DateTime: 2017/12/7 下午3:56
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("save")
    /** 缓存的最终返回对象与cacheNames指定的对象不同(相同时,可使用该注解)，且ModelAndView不能序列化 */
    //@CachePut(cacheNames = {"product"}, key = "123")
    /** 此方法完成后，清除缓存 */
    @CacheEvict(key = "123")
    public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            /** 更新 */
            if (StringUtils.isNotEmpty(form.getProductId())) {
                productInfo = this.productService.findOne(form.getProductId());
            /** 新增 */
            } else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            this.productService.save(productInfo);
        } catch (ExceptionCustom e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}

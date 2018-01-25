package com.astro.web.frontend;

import com.astro.dto.ProductExcution;
import com.astro.entity.Product;
import com.astro.entity.ProductCategory;
import com.astro.entity.Shop;
import com.astro.service.ProductCategoryService;
import com.astro.service.ProductService;
import com.astro.service.ShopService;
import com.astro.util.HttpServletRequestUtil;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by astro on 2018/1/25.
 */
@Controller
@RequestMapping("/frontend")
@Slf4j
public class ShopDetailController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/listshopdetailpageinfo")
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1) {
            shop = shopService.queryShopById(shopId);
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            map.put("shop", shop);
            map.put("productCategoryList", productCategoryList);
            map.put("success", true);
        } else {
            map.put("success", false);
            map.put("errMsg", "empty shopId");
        }
        return map;
    }


    //分页 查询出该shop下的商品
    @GetMapping("/listproductsbyshop")
    @ResponseBody
    private Map<String, Object> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
            try {
                ProductExcution pe = productService.getProductList(productCondition, pageIndex, pageSize);
                map.put("success",true);
                map.put("productList",pe.getProductList());
                map.put("count",pe.getCount());
            }catch (Exception e){
                map.put("success",false);
                map.put("errMsg",e.toString());
                log.info("-------------errrrr:"+e.toString());
                return map;
            }

        }else {
            map.put("success",false);
            map.put("errMsg","empty pageIndex pageSize");
        }
        return map;
    }

    private Product compactProductCondition4Search(long shopId, long productCategoryId, String procuctName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId!=-1){
            ProductCategory pc=new ProductCategory();
            pc.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(pc);
        }
        if (procuctName!=null){
            productCondition.setProductName(procuctName);
        }
        productCondition.setEnableStatus(1);
        return productCondition;
    }

}

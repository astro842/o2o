package com.astro.web.shopadmin;

import com.astro.dto.ProductExcution;
import com.astro.entity.Product;
import com.astro.entity.ProductCategory;
import com.astro.entity.Shop;
import com.astro.service.ProductService;
import com.astro.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by astro on 2018/1/9.
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getproductlistbyshop")
    @ResponseBody
    private Map<String,Object> getProductListByShop(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        Shop currentShop= (Shop) request.getSession().getAttribute("currentShop");
        if ((pageIndex >-1) && (pageSize >-1) && (currentShop != null) && (currentShop.getShopId()!=null)){
           long productCageoryId = HttpServletRequestUtil.getLong(request,"productCategoryId");
           String productName = HttpServletRequestUtil.getString(request,"productName");
           Product productCondition = compactProductCondition(currentShop.getShopId(),productCageoryId,productName);

            ProductExcution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            map.put("success",true);
            map.put("count",pe.getCount());
            map.put("productList",pe.getProductList());
        }else {
            map.put("success",false);
            map.put("errMsg","empty pageSize or pageIndex or shopId");
        }
        return map;
    }

    private Product compactProductCondition(long shopId,long productCategoryId,String productName){
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1l){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null){
            productCondition.setProductName(productName);
        }
        return productCondition;
    }
}

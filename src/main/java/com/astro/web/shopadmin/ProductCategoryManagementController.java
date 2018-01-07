package com.astro.web.shopadmin;

import com.astro.dto.ProductCagegoryExecution;
import com.astro.dto.Result;
import com.astro.entity.ProductCategory;
import com.astro.entity.Shop;
import com.astro.enums.ProductCategoryStateEnum;
import com.astro.exceptions.ProductCategoryOperationException;
import com.astro.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by astro on 2018/1/4.
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/getproductcategorylist")
    @ResponseBody
    public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
         //To be remove
        Shop shop = new Shop();
        shop.setShopId(5L);
        request.getSession().setAttribute("currentShop",shop);

        Shop currentShop =(Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> productCategoryList = null;
       if (currentShop != null && currentShop.getShopId()>0) {
           productCategoryList = productCategoryService.getProductCategoryList(currentShop.getShopId());
           return new Result<List<ProductCategory>>(true, productCategoryList);
       }else {
           ProductCategoryStateEnum ps =ProductCategoryStateEnum.INNER_ERROR;
           return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());
       }


    }

    @PostMapping("/addproductcategorys")
    @ResponseBody
    public Map<String,Object> addProductCagegorys(@RequestBody List<ProductCategory> productCategoryList,
                                                  HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc : productCategoryList){
            pc.setShopId(currentShop.getShopId());
        }
        if (productCategoryList != null && productCategoryList.size() >0){
           try {
               ProductCagegoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
               if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                   map.put("success",true);
               }else {
                   map.put("success",false);
                   map.put("errMsg",pe.getStateInfo());
               }
           }catch (ProductCategoryOperationException e){
               map.put("success",false);
               map.put("errMsg",e.toString());
               return map;
           }
        }else {
            map.put("success",false);
            map.put("errMsg","请至少输入一个商品类别");
        }
             return map;
    }

    @PostMapping("/removeproductcategory")
    @ResponseBody
    public Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        if (productCategoryId !=null && productCategoryId>0){
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCagegoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    map.put("success",true);
                }else {
                    map.put("success",false);
                    map.put("errMsg",pe.getStateInfo());
                }
            }catch (ProductCategoryOperationException e){
                map.put("success",false);
                map.put("errMsg",e.toString());
                return map;
            }
        }else {
            map.put("success",false);
            map.put("errMsg","请至少选择一个商品类别");
        }
         return map;
    }
}

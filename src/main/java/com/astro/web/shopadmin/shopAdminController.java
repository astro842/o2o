package com.astro.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by astro on 2017/12/28.
 */
@Controller
@RequestMapping(value = "shopadmin",method = RequestMethod.GET)
public class shopAdminController {

    //url返回的都是html页面 然后通过对应的js获取后台数据


    @RequestMapping("/shopoperation")
    public String shopOperation(){
        return "shop/shopOperation";
    }
    //商铺列表页面
    @RequestMapping("/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }
    //商铺管理页面
    @RequestMapping("/shopmanagement")
    public String shopManagement(){return "shop/shopmanage";
    }
    //商品类别管理页面
    @GetMapping("/productcategorymanagement")
    public String productCategoryManage(){
        return "shop/productcategorymanage";
    }
    //商品添加 编辑页面
    @RequestMapping("/productoperation")
    public String productOperation(){
        return "shop/productoperation";
    }
}

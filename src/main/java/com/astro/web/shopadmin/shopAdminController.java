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


    @RequestMapping("/shopoperation")
    public String shopOperation(){
        return "shop/shopOperation";
    }

    @RequestMapping("/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }

    @RequestMapping("/shopmanagement")
    public String shopManagement(){return "shop/shopmanage";
    }

    @GetMapping("/productcategorymanagement")
    public String productCategoryManage(){
        return "shop/productcategorymanage";
    }
}

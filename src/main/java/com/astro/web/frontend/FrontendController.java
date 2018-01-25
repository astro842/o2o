package com.astro.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by astro on 2018/1/16.
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {


    @GetMapping("/shopList")
    public String shopList(){
        return "frontend/shoplist";
    }

    @GetMapping("/shopdetail")
    public String shopDetail(){
        return "frontend/shopdetail";
    }

    @GetMapping("/index")
    public String index(){
        return "frontend/index";
    }

}

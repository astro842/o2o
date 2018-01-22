package com.astro.web.frontend;

import com.astro.dao.HeadLineDao;
import com.astro.entity.HeadLine;
import com.astro.entity.ShopCategory;
import com.astro.service.HeadLineService;
import com.astro.service.ShopCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by astro on 2018/1/15.
 */
@Controller
@RequestMapping("/frontend")
@Slf4j
public class MainPageController {

    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    @GetMapping("/listmainpageinfo")
    @ResponseBody
    public Map<String,Object> listMainPageInfo(){
        Map<String,Object> map=new HashMap<>();
        List<ShopCategory> shopCategoryList =new ArrayList<>();
        try {
            //商店类别 一级
             shopCategoryList = shopCategoryService.getShopCategoruList(null);
             map.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.toString());
            return map;
        }

        List<HeadLine> headLineList =new ArrayList<>();
        try {
            HeadLine headLine =new HeadLine();
            headLine.setEnableStatus(1);
            headLineList=  headLineService.getHeadLineList(headLine);
            log.info("headLineList:"+headLineList);
            map.put("headLineList",headLineList);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.toString());
            return map;
        }
        map.put("success",true);
        return map;
    }
}

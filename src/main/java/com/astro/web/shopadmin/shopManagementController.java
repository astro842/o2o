package com.astro.web.shopadmin;

import com.astro.dto.ShopExecution;
import com.astro.entity.Area;
import com.astro.entity.PersonInfo;
import com.astro.entity.Shop;
import com.astro.entity.ShopCategory;
import com.astro.enums.ShopStateEnum;
import com.astro.service.AreaService;
import com.astro.service.ShopCategoryService;
import com.astro.service.ShopService;
import com.astro.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by astro on 2017/12/27.
 */
@Controller
@RequestMapping("/shopadmin")
@Slf4j
public class shopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopCategoryService shopCategoryService;


    @GetMapping("/getshopmanagementinfo")
    @ResponseBody
    public Map<String,Object> getShopManagementInfo(HttpServletRequest request){

        Map<String,Object> map = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if (shopId <= 0){
            Object currentShop = request.getSession().getAttribute("currentShop");
            if (currentShop == null){
                map.put("redirect",true);
                map.put("url","/shopadmin/shoplist");
            }else {
                Shop shop = (Shop) currentShop;
                map.put("redirect",false);
                map.put("shopId",((Shop) currentShop).getShopId());
            }
        }else {
            Shop shop = new Shop();
            shop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",shop);
            map.put("redirect",false);
        }
          return map;
    }



    @GetMapping("/getshoplist")
    @ResponseBody
    public Map<String ,Object> getShopList(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //TODO
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("小明");
        request.getSession().setAttribute("user",user);
        user = (PersonInfo)request.getSession().getAttribute("user");
        try {
           Shop shopCondition = new Shop();
           shopCondition.setOwner(user);
           ShopExecution se = shopService.getShopList(shopCondition,1,100 );
            map.put("shopList",se.getShopList());
            map.put("user",user);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
            log.debug("没有shop？？？？？？");
        }
        return map;
    }


    @GetMapping("/getshopbyid")
    @ResponseBody
    public Map<String,Object> getShopById(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if (shopId >= -1){
            try {
                Shop shop = shopService.queryShopById(shopId);
                List<Area> areaList = areaService.getAreaList();
                map.put("shop",shop);
                map.put("areaList",areaList);
                map.put("success",true);
            }catch (Exception e){
                map.put("success",false);
                map.put("errMsg",e.getMessage());
            }
        }else {
            map.put("success",false);
            map.put("errMsg","emptyShop");
        }
        return map;
    }

    @GetMapping("/getshopinitinfo")
    @ResponseBody
    public Map<String,Object> getShopInitInfo(){
        Map<String,Object> map = new HashMap<>();
        List<ShopCategory>shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();

        try {
            areaList = areaService.getAreaList();
            shopCategoryList = shopCategoryService.getShopCategoruList(new ShopCategory());

            log.info("areaList:"+areaList);
            log.info("shopCategoryList:"+shopCategoryList);

            map.put("shopCategoryList",shopCategoryList);
            map.put("areaList",areaList);
            map.put("success",true);

        }catch (Exception e){
           map.put("success",false);
           map.put("errMsg",e.getMessage());
        }

        return map;
    }

    @PostMapping("/modifyshop")
    @ResponseBody
    public Map<String,Object> modifyShop(HttpServletRequest request){

          Map<String , Object> map =new HashMap<>();
         //1.接收并转化相应的参数
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        //jackson
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;

        try {
            shop = mapper.readValue(shopStr,Shop.class);
        } catch (IOException e) {
            map.put("success",false);
            map.put("errMsg",e.getMessage());
            log.error("------------------errMsg={}",e.getMessage());
            return map;
        }

        //接收上传的图片
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        if (resolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        //2.修改店铺
        if(shop != null && shop.getShopId() !=null){
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                if (shopImg == null){
                    se = shopService.modifyShop(shop,null,null);
                }else {
                    se = shopService.modifyShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                }
                if (se.getState() == ShopStateEnum.SUCCESS.getState()){
                    map.put("success",true);
                }else{
                    map.put("success",false);
                    map.put("errMsg",se.getState());
                    log.error("------------------errMsg={}",se.getState());
                }
            } catch (IOException e) {
                map.put("success",false);
                map.put("errMsg",e.getMessage());
                log.error("------------------errMsg={}",e.getMessage());
            }

            return map;

        }else {
            map.put("success",false);
            map.put("errMsg","请输入商铺信息");
            log.error("------------------errMsg={}-----请输入商铺信息");
            return map;
        }

        //3.返回结果
    }

    @PostMapping("/registershop")
    @ResponseBody
    public Map<String,Object> registerShop(HttpServletRequest request){

        Map<String , Object> map =new HashMap<>();
        //1.接收并转化相应的参数
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        //jackson
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;

        try {
            shop = mapper.readValue(shopStr,Shop.class);
        } catch (IOException e) {
            map.put("success",false);
            map.put("errMsg",e.getMessage());
            log.error("------------------errMsg={}",e.getMessage());
            return map;
        }

        //接收上传的图片
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        if (resolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else {
            map.put("success",false);
            map.put("errMsg","上传图片不能为空");
            log.error("------------------errMsg={}---上传图片不能为空");
            return map;
        }
        //2.注册店铺
        if(shop != null && shopImg !=null){
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                if (se.getState() == ShopStateEnum.CHECK.getState()){
                    map.put("success",true);
                }else{
                    map.put("success",false);
                    map.put("errMsg",se.getState());
                    log.error("------------------errMsg={}",se.getState());
                }
            } catch (IOException e) {
                map.put("success",false);
                map.put("errMsg",e.getMessage());
                log.error("------------------errMsg={}",e.getMessage());
            }

            return map;

        }else {
            map.put("success",false);
            map.put("errMsg","请输入商铺信息");
            log.error("------------------errMsg={}-----请输入商铺id");
            return map;
        }

        //3.返回结果
    }

//    private static void inputStream2File(InputStream is , File file){
//
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while ((bytesRead = is.read(buffer))!= -1){
//              os.write(buffer,0,bytesRead);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
//    }
}

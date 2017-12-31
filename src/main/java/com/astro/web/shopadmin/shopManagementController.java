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
import com.astro.util.ImageUtil;
import com.astro.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.rmi.MarshalledObject;
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


    @GetMapping("/getShopInitInfo")
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

    @PostMapping("/registerShop")
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
            log.error("------------------errMsg={}-----请输入商铺信息");
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

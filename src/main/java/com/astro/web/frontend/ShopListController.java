package com.astro.web.frontend;

import com.astro.dto.ShopExecution;
import com.astro.entity.Area;
import com.astro.entity.Shop;
import com.astro.entity.ShopCategory;
import com.astro.service.AreaService;
import com.astro.service.ShopCategoryService;
import com.astro.service.ShopService;
import com.astro.util.HttpServletRequestUtil;
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
 * Created by astro on 2018/1/20.
 */
@Controller
@RequestMapping("/frontend")
@Slf4j
public class ShopListController {

    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;

    //返回商品类别页的shopCatory列表（二级或者一级） 区域信息列表
    @GetMapping("/listshopspageinfo")
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            //如果parentId存在 ， 则取出shopCategory下的二级shopCategoryId
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoruList(shopCategoryCondition);
            } catch (Exception e) {
                map.put("success", false);
                map.put("errMsg", e.toString());
            }
        } else {
            try {
                // 如果parentId 不存在 ， 则取出shopCategory下的一级shopCategoryId
                shopCategoryList = shopCategoryService.getShopCategoruList(null);
            } catch (Exception e) {
                map.put("success", false);
                map.put("errMsg", e.toString());
            }
        }
        map.put("shopCategoryList", shopCategoryList);
        //返回区域信息
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();
            map.put("success", true);
            map.put("areaList", areaList);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
        }
        return map;

    }


    //查询条件下的 shopList
    @GetMapping("/listshops")
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex > -1 && pageSize > -1) {
            long parentId = HttpServletRequestUtil.getLong(request,"parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request,"shopCategoryId");
            int areaId = HttpServletRequestUtil.getInt(request,"areaId");
            //模糊查询shopnaem
            String shopName = HttpServletRequestUtil.getString(request,"shopName");
            //组合之后的查询条件
            Shop shopCondition = compactShopCondition4Search(parentId,shopCategoryId,areaId,shopName);
            //执行查询
            ShopExecution se =shopService.getShopList(shopCondition,pageIndex,pageSize);
            map.put("success",true);
            map.put("shopList",se.getShopList());
            map.put("count",se.getCount());
        }else {
            map.put("success",false);
            map.put("errMsg","1:something empty");
        }
        return map;
    }

    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition=new Shop();
        shopCondition.setEnableStatus(1);
        if (parentId!=-1){
            ShopCategory shopCategory=new ShopCategory();
            ShopCategory parent =new ShopCategory();
            parent.setShopCategoryId(parentId);
            shopCategory.setParent(parent);

            shopCondition.setShopCategory(shopCategory);
        }
        if (shopCategoryId!=-1){
            ShopCategory shopCategory=new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1){
            Area area=new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName!=null){
            shopCondition.setShopName(shopName);
        }
        return shopCondition;
    }


}

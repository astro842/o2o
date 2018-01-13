package com.astro.web.shopadmin;

import com.astro.dto.ImageHolder;
import com.astro.dto.ProductExcution;
import com.astro.entity.Product;
import com.astro.entity.ProductCategory;
import com.astro.entity.ProductImg;
import com.astro.entity.Shop;
import com.astro.enums.ProductStateEnum;
import com.astro.service.ProductService;
import com.astro.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by astro on 2018/1/9.
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    private static final int IMAGEMAXCOUNT = 6;

    @Autowired
    private ProductService productService;


    @PostMapping("/modifyProduct")
    @ResponseBody
    public Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //用Jackson接收前端传来的信息
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail=null;
        List<ImageHolder> productImgList =new ArrayList<>();
        //MultipartHttpServletRequest multipartrequest = null;
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        //若request中有文件流 这取出
        try {
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartrequest = (MultipartHttpServletRequest) request;
                //取出缩略图
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartrequest.getFile("thumbnail");
                if (thumbnailFile!=null) {
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                }
                //取出详情图
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImageFile = (CommonsMultipartFile) multipartrequest.getFile("productImg" + i);
                    if (productImageFile != null) {
                        ImageHolder imageHolder = new ImageHolder(productImageFile.getOriginalFilename(), productImageFile.getInputStream());
                        productImgList.add(imageHolder);
                    } else {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }
        try {
            String productStr = HttpServletRequestUtil.getString(request,"productStr");
            product=mapper.readValue(productStr,Product.class);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.toString());
            return map;
        }
        //更新
        if (product !=null){
            try {
                Shop currentShop =(Shop) request.getSession().getAttribute("currentShop");
                Shop shop =new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExcution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if (pe.getState()==ProductStateEnum.SUCCESS.getState()){
                    map.put("success",true);
                }
            }catch (Exception e){
                map.put("success",false);
                map.put("errMsg",e.toString());
                return map;
            }
        }else {
            map.put("success",false);
            map.put("errMsg","请输入商品信息");
        }


        return map;
    }

    @PostMapping("/addproduct")
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartrequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            if (multipartResolver.isMultipart(request)) {
                multipartrequest = (MultipartHttpServletRequest) request;
                //取出缩略图
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartrequest.getFile("thumbnail");
                if (thumbnailFile!=null) {
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                }
                //取出详情图
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImageFile = (CommonsMultipartFile) multipartrequest.getFile("productImg" + i);
                    if (productImageFile != null) {
                        ImageHolder imageHolder = new ImageHolder(productImageFile.getOriginalFilename(), productImageFile.getInputStream());
                        productImgList.add(imageHolder);
                    } else {
                        break;
                    }
                }
            } else {
                map.put("success", false);
                map.put("errMsg", "上传图片不能为空");
                return map;
            }
        } catch (IOException e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }
        try {
            //把productStr 通过JackSon转化为 product类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.toString());
            return map;
        }
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                ProductExcution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    map.put("success", true);
                } else {
                    map.put("success", false);
                    map.put("errMsg", pe.getStateInfo());

                }
            } catch (Exception e) {
                map.put("success", false);
                map.put("errMsg", e.toString());
                return map;
            }
        } else {
            map.put("success", false);
            map.put("errMsg", "请输入完整的商品信息");

        }
        return map;

    }

    @GetMapping("/getproductlistbyshop")
    @ResponseBody
    private Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            long productCageoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCageoryId, productName);

            ProductExcution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            map.put("success", true);
            map.put("count", pe.getCount());
            map.put("productList", pe.getProductList());
        } else {
            map.put("success", false);
            map.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return map;
    }

    private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1l) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }
}

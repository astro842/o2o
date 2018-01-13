package com.astro.service.impl;

import com.astro.dao.ProductDao;
import com.astro.dao.ProductImgDao;
import com.astro.dto.ImageHolder;
import com.astro.dto.ProductExcution;
import com.astro.entity.Product;
import com.astro.entity.ProductImg;
import com.astro.enums.ProductStateEnum;
import com.astro.exceptions.ProductOperationException;
import com.astro.service.ProductService;
import com.astro.util.ImageUtil;
import com.astro.util.PageCalculator;
import com.astro.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by astro on 2018/1/9.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;


    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    //如果要修改图片就先删除原来的所有图片在上传
    @Override
    public ProductExcution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException, IOException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            if (thumbnail != null) {
                Product tempProdcut = productDao.queryProductById(product.getProductId());
                if (tempProdcut.getImgAddr() != null) {
                    //删除缩略图
                    ImageUtil.deleteFilePath(tempProdcut.getImgAddr());
                }
                //添加更新后的缩略图
                addThumbnail(product,thumbnail);
            }
            //先删除原有的详情图 在上传
            if (productImgHolderList!=null && productImgHolderList.size()>0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgHolderList);
            }
            //更新信息
            try {
                int i = productDao.updateProduct(product);
                if (i<=0){
                    throw  new ProductOperationException("更新商品失败1");
                }
                return new ProductExcution(product,ProductStateEnum.SUCCESS);
            }catch (Exception e){
                throw  new ProductOperationException("更新商品失败2"+e.toString());
            }

        }else {
            return new ProductExcution(ProductStateEnum.EMPTY);
        }

    }

    private void deleteProductImgList(Long productId) {
        List<ProductImg> productImgList = productImgDao.quertProductImgList(productId);
        //删除详情图路径及文件
        for (ProductImg productImg:productImgList){
            ImageUtil.deleteFilePath(productImg.getImgAddr());
        }
        //删除数据库的详情图信息
        productImgDao.deleteProductImgByProductImg(productId);
    }


    @Override
    @Transactional
    public ProductExcution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException, IOException {

        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            //商品缩略图
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            //添加到  tb_product
            try {
                int effNum = productDao.insertProduct(product);
                if (effNum < 0) {
                    throw new ProductOperationException("商品创建失败1");
                }
            } catch (Exception e) {
                throw new ProductOperationException("商品创建失败2" + e.getMessage());
            }
            //商品详情图
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                addProductImgList(product, productImgHolderList);
            }

            return new ProductExcution(product, ProductStateEnum.SUCCESS);
        } else {
            return new ProductExcution(ProductStateEnum.EMPTY);
        }
    }


    @Override
    public ProductExcution getProductList(Product productCondition, int pageIndex, int pageSize) {

        int rowIndex = PageCalculator.caltulateRowIdex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExcution pe = new ProductExcution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }


    //添加商品缩略图
    private void addThumbnail(Product product, ImageHolder thumbnail) throws IOException {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumBnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    //添加商品多张详情图
    private void addProductImgList(Product product, List<ImageHolder> imageHolderList) throws IOException {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        //遍历图片并insert到tb_product_img
        for (ImageHolder imageHolder : imageHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(imageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        if (productImgList.size() > 0) {
            try {
                int i = productImgDao.batchInsertProductImg(productImgList);
                if (i <= 0) {
                    throw new ProductOperationException("商品详情图片上传失败1");
                }
            } catch (Exception e) {
                throw new ProductOperationException("商品详情图片上传失败2:" + e.toString());
            }
        }

    }

}

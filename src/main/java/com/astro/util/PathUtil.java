package com.astro.util;

import java.io.File;

/**
 * Created by astro on 2017/12/24.
 */
public class PathUtil {

    private static  String separator = System.getProperty("file.separator");

    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")){
            basePath = "E:/upload/o2o";
        }else {
            basePath = "";
        }
        //basePath = basePath.replace("\\",separator);
        return basePath;
    }

    public static String getShopImagePath(Long shopId) {
        String imagePath = "/upload/shop/" + shopId + "/";
        return imagePath.replace("/", separator);
        //return imagePath;
    }

}

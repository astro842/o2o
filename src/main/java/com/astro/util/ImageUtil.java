package com.astro.util;

import com.astro.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by astro on 2017/12/24.
 */
public class ImageUtil {

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static File transferCommonsMutipartFileToFile(CommonsMultipartFile cFile){
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;

    }

    public static String generateThumBnail(ImageHolder thumbnail, String targetAddr) throws IOException {
          String realFileName = getRandomFileName();
          String extension =  getFileExtension(thumbnail.getImageName());
          makeDirPath(targetAddr);
          String relativeAddr = targetAddr + realFileName + extension;
          logger.info("relativeAddr={}",relativeAddr);
          String lastAddr = PathUtil.getImgBasePath() + relativeAddr;
          File dest = new File(lastAddr);
        logger.info("dest={}",dest);
          try {
              Thumbnails.of(thumbnail.getImage()).size(200,200).watermark(Positions.BOTTOM_RIGHT,
                      ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
                      .outputQuality(0.8f).toFile(dest);
          }catch (IOException e){
                logger.error(e.toString());
               throw new RuntimeException(e.toString());
          }
          return relativeAddr;
    }

    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) throws IOException {
        String realFileName = getRandomFileName();
        String extension =  getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.info("relativeAddr={}",relativeAddr);
        String lastAddr = PathUtil.getImgBasePath() + relativeAddr;
        File dest = new File(lastAddr);
        logger.info("dest={}",dest);
        try {
            Thumbnails.of(thumbnail.getImage()).size(337,640).watermark(Positions.BOTTOM_RIGHT,
                    ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            logger.error(e.toString());
            throw new RuntimeException(e.toString());
        }
        return relativeAddr;
    }

    //创建涉及的路径  \8\2017122417293270963.jpg
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }

    }
    //获取扩展名
    private static String getFileExtension(String fileName) {

        return fileName.substring(fileName.lastIndexOf("."));
    }
    //生成文件名
    public static String getRandomFileName() {
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;

    }


    public static void main(String[] args) throws IOException {

        String addr = "E:/upload/o2o/p1New.jpg";
        Thumbnails.of(new File("E:/upload/o2o/p1.jpg")).size(200,200).watermark(Positions.BOTTOM_RIGHT,
                ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f).outputQuality(0.8f).toFile(addr);
        //System.out.println(System.getProperty("file.separator"));

    }

    public static void  deleteFilePath(String storePath){
        File filtPath = new File(PathUtil.getImgBasePath()+storePath);
        if (filtPath.exists()){
            if (filtPath.isDirectory()){
                File files[] =filtPath.listFiles();
                for (int i= 0;i<files.length;i++){
                    files[i].delete();
                }
            }
            filtPath.delete();
        }
    }
}

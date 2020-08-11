package org.zxy.flea.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.zxy.flea.consts.FleaConst;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.List;

@Slf4j
public class ImageUtil {

    public static String saveImage(String prefix, String imageId, String imageInfo){

        if (imageInfo == null)
            return null;

        int index = imageInfo.indexOf("base64,");
        if (index == -1) {
            return null;
        }

        imageInfo = imageInfo.substring(index + 7);

        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imageInfo);

            //生成jpeg图片
            String imgFilePath = FleaConst.IMAGE_DIR + prefix + imageId + ".jpg";

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();

            // 缩放图片
            resizeImage(imgFilePath, imgFilePath);
            return prefix + imageId + ".jpg";
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static void resizeImage(String srcPath, String destPath) {
        try {
            InputStream is = new FileInputStream(srcPath);
            Thumbnails.of(is).forceSize(100, 100).toFile(destPath);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteImage(String imagePath) {
        try{
            File file = new File(imagePath);
            file.delete();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static void deleteImage(List<String> imagePathList) {
        try {
            for (String imagePath: imagePathList) {
                File file = new File(imagePath);
                file.delete();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

}

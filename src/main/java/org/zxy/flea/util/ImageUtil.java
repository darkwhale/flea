package org.zxy.flea.util;

import lombok.extern.slf4j.Slf4j;
import org.zxy.flea.consts.FleaConst;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

@Slf4j
public class ImageUtil {

    public static String saveImage(String prefix, String userId, String imageInfo) {

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
            String imgFilePath = FleaConst.IMAGE_DIR + prefix + userId + ".jpg";
            log.info("imagePath: {}", imgFilePath);

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return prefix + userId + ".jpg";
        }
        catch (Exception e)
        {
            return null;
        }
    }
}

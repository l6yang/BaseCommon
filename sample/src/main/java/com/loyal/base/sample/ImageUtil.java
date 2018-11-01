package com.loyal.base.sample;

import android.graphics.Bitmap;

import com.loyal.base.util.IOUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ImageUtil {
    public static String saveToFile(File jpgFile, InputStream inputStream) {
        if (inputStream == null)
            return "";
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] bytes = new byte[1024];
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            while ((len = inputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            FileOutputStream outputStream = new FileOutputStream(jpgFile);
            outputStream.write(bos.toByteArray());
            IOUtil.closeStream(outputStream);
            return jpgFile.getPath();
        } catch (Exception e) {
            return "";
        } finally {
            IOUtil.closeStream(inputStream);
        }
    }

    public static String saveBitmap(String imgPath, Bitmap bitmap) {
        try {
            File jpgFile = new File(imgPath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            int options = 100;
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            while (baos.toByteArray().length / 1024 > 80) {
                // 重置baos即清空baos
                baos.reset();
                // 每次都减少10
                options -= 10;
                // 这里压缩options%，把压缩后的数据存放到baos中
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }
            FileOutputStream outputStream = new FileOutputStream(jpgFile);
            outputStream.write(baos.toByteArray());
            IOUtil.closeStream(outputStream);
            return jpgFile.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

package com.loyal.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    public static boolean deleteFile(String filePath) {
        return deleteFile(new File(filePath));
    }

    public static boolean deleteFile(String dirPath, String fileName) {
        return deleteFile(new File(dirPath, fileName));
    }

    /**
     * @param file Eg:"F:\ss\新建文本文档.txt"
     * @return 删除文件是否成功
     */
    public static boolean deleteFile(File file) {
        try {
            return file.exists() && file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createDirs(String dirPath) {
        return createDirs(new File(dirPath));
    }

    public static boolean createDirs(File file) {
        try {
            return !file.exists() && file.mkdirs();
        } catch (Exception e) {
            return false;
        }
    }

    public static void writeFile(InputStream in, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 128];
            int len;
            while ((len = in.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeStream(fos);
            IOUtil.closeStream(in);
        }
    }
}

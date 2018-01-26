package com.loyal.base.sample;

import android.os.Environment;

import com.loyal.base.util.IOUtil;
import com.loyal.base.util.ResUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//  @ 文件系统工具类
public class FileUtil {

    // sd卡路径
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final String MAIN_PATH = SD_PATH + "/com.loyal.base/";
    // 图片文件夹
    public static final String imagePath = MAIN_PATH + "image/";

    private static String[] paths = new String[]{imagePath};

    private static void createMainDir() {
        for (String path : paths) {
            File file = new File(path);
            createDirs(file);
        }
    }

    // 创建文件夹是否成功
    public static void createFileSys() {
        try {
            deleteEmptyDir(new File(imagePath));
            createMainDir();
            for (String path : paths) {
                createDirs(path);
            }
        } catch (Exception e) {
            //
        }
    }

    public static void deleteEmptyDirs() {
        File file = new File(MAIN_PATH);
        deleteEmptyDir(file);
        deleteFileWithDir(new File(MAIN_PATH + "Camera_Image"));
        deleteFileWithDir(new File(MAIN_PATH + "config"));
        deleteFileWithDir(new File(MAIN_PATH + "IpConfig"));
        deleteFileWithDir(new File(MAIN_PATH + "Camera_Image"));
        deleteFileWithDir(new File(MAIN_PATH + "Camera_Image/Thumbnail/"));
    }

    // 保存IP文件到本地 这里只需要把文件名传进去就行，不管保存的类型
    public static void createFiles(String path, String fileName, String content) {
        try {
            String[] Files = new String[]{fileName};
            for (String f : Files) {
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(new File(path) + "/" + f);
                    fos.write(content.getBytes());
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            //
        }
    }

    // 将保存在文件的数据读载到所需要的控件中
    public static String getFileContent(String path, String fileName) {
        try {
            return getFileContent(new File(path, fileName));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getFileContent(File file) {
        try {
            String content = "";
            if (!file.exists()) {
                return content;
            } else {
                FileInputStream fis = new FileInputStream(file);
                return ResUtil.getStrFromRes(fis);
            }
        } catch (Exception e) {
            return "";
        }
    }

    //@param extension 后缀名 如".jpg"
    public static String createFileName(String name, String extension) {
        // 查看是否带"."
        try {
            if (!extension.startsWith("."))
                extension = "." + extension;
            return name + extension;
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean createDirs(String path) {
        try {
            return createDirs(new File(path));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean createDirs(File file) {
        try {
            return !file.exists() && file.mkdirs();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除文件及所在文件夹
     */
    public static void deleteFileWithDir(File file) {
        if (file.exists()) { //指定文件是否存在
            if (file.isFile()) { //该路径名表示的文件是否是一个标准文件
                deleteFile(file); //删除该文件
            } else if (file.isDirectory()) { //该路径名表示的文件是否是一个目录（文件夹）
                File[] files = file.listFiles(); //列出当前文件夹下的所有文件
                for (File f : files) {
                    deleteFileWithDir(f); //递归删除
                    //Log.d("fileName", f.getName()); //打印文件名
                }
            }
            deleteFile(file);
            //删除文件夹（song,art,lyric）
        }
    }

    public static boolean deleteEmptyDir(File file) {
        try {
            if (file.isDirectory()) {
                File[] childFiles = file.listFiles();
                if (childFiles == null || childFiles.length == 0) {
                    return file.delete();
                }
                for (File f : childFiles) {
                    deleteEmptyDir(f);
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param file Eg:"F:\ss\新建文本文档.txt"
     * @ 删除文件
     */
    public static boolean deleteFile(File file) {
        try {
            return file.exists() && file.delete();
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
            fos.close();
            in.close();
        } catch (IOException e) {
            //
        } finally {
            IOUtil.closeStream(fos);
            IOUtil.closeStream(in);
        }
    }
}

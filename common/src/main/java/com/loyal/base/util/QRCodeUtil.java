package com.loyal.base.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

public class QRCodeUtil {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static Hashtable<EncodeHintType, Object> hints;

    static {
        hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设置空白边距的宽度
        hints.put(EncodeHintType.MARGIN, 1); //default is 4
    }

    /**
     * @param content 二维码内容
     * @param qrSize  图片尺寸
     * @return 二维码图片
     */
    public static Bitmap createQrImage(String content, int qrSize) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrSize, qrSize, hints);
            int[] pixels = new int[qrSize * qrSize];
            for (int y = 0; y < qrSize; y++) {
                for (int x = 0; x < qrSize; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * qrSize + x] = BLACK;
                    } else {
                        pixels[y * qrSize + x] = WHITE;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(qrSize, qrSize, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, qrSize, 0, 0, qrSize, qrSize);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param content 一维码内容
     *                默认width=500，height=200
     * @see #createBarCode(String)
     */
    public static Bitmap createBarCode(String content) {
        return createBarCode(content, 500, 200);
    }

    /**
     * 注：目前生成内容为中文的话将直接报错，要修改底层jar包的内容
     *
     * @param content 将要生成一维码的内容
     * @return 一维码bitmap
     */
    public static Bitmap createBarCode(String content, int barWidth, int barHeight) {
        try {
            // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
            BitMatrix matrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.CODE_128, barWidth, barHeight);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            // 通过像素数组生成bitmap,具体参考api
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }
}

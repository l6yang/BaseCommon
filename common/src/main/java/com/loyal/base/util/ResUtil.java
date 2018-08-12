package com.loyal.base.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class ResUtil {
    /**
     * @param variableName video
     * @param c            mipmap.class
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * @param fileName "json/index.json"
     */
    public static InputStream openAssetFile(Context context, String fileName) {
        try {
            return context.getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStrFromRes(InputStream is) {
        if (is == null)
            return "";
        BufferedReader bufferedReader = null;
        try {
            StringBuilder buffer = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            IOUtil.closeStream(bufferedReader);
            IOUtil.closeStream(is);
        }
    }

    /**
     * {@link #openAssetFile(Context, String)}
     */
    public static String getStrFromRes(Context context, String fileName) {
        InputStream is = openAssetFile(context, fileName);
        if (is == null)
            return "";
        return getStrFromRes(is);
    }
}

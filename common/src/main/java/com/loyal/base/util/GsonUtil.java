package com.loyal.base.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.loyal.base.beans.PatternBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ikidou.reflect.TypeBuilder;

public class GsonUtil {
    private static Gson gson = new Gson();

    /**
     * {@link #json2BeanObject(String, Class, Class)}
     */
    public static <T> PatternBean<List<T>> json2BeanArray(String json, Class<? extends PatternBean> rowClass, Class<T> clazz) {
        Type type = TypeBuilder
                .newInstance(rowClass)
                .beginSubType(List.class)
                .addTypeParam(clazz)
                .endSubType()
                .build();
        return new Gson().fromJson(json, type);
    }

    /**
     * 泛型
     *
     * @param clazz GsonBean中的泛型
     */
    public static <T> PatternBean<T> json2BeanObject(String json, Class<? extends PatternBean> rowClass, Class<T> clazz) {
        Type type = TypeBuilder
                .newInstance(rowClass)
                .addTypeParam(clazz)
                .build();
        return gson.fromJson(json, type);
    }

    //----------------------------------
    public static <T> T json2Bean(String json, Class<T> tClass) {
        try {
            return gson.fromJson(json, tClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static @NonNull
    <T> List<T> json2BeanList(String json, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        try {
            if (TextUtils.isEmpty(json)) {
                list.clear();
                return list;
            }
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add((T) gson.fromJson(elem, tClass));
            }
            return list;
        } catch (Exception e) {
            list.clear();
            return list;
        }
    }

    public static @NonNull
    String bean2Json(Object object) {
        if (null == object)
            return "{}";
        return gson.toJson(object);
    }

    public static @NonNull
    String list2Json(Object object) {
        if (null == object)
            return "[]";
        return gson.toJson(object);
    }

    public static <T> List<T> jsonFile2BeanList(Context context, String resName, Class<T> tClass) {
        if (TextUtils.isEmpty(resName))
            return new ArrayList<>();
        String json = ResUtil.getStrFromRes(context, resName);
        return json2BeanList(json, tClass);
    }

    public static <T> T jsonFile2Bean(Context context, String resName, Class<T> tClass) {
        if (TextUtils.isEmpty(resName))
            return null;
        String json = ResUtil.getStrFromRes(context, resName);
        return TextUtils.isEmpty(resName) ? null : json2Bean(json, tClass);
    }

    /**
     * @param isFile true：json格式的文件名
     *               false：标准的json格式字符串
     * @param json   isFile=true 如："test.json";
     *               isFile=false 如：{"name":"张三"};
     */
    public static @NonNull<T> List<T> json2BeanList(Context context, String json, Class<T> t, boolean isFile) {
        List<T> list = new ArrayList<>();
        if (TextUtils.isEmpty(json)) {
            list.clear();
            return list;
        }
        if (isFile)
            list = jsonFile2BeanList(context, json, t);
        else
            list = json2BeanList(json, t);
        return list;
    }
}
package com.loyal.base.util;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loyal.base.beans.GsonBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ikidou.reflect.TypeBuilder;

public class GsonUtil {
    private static Gson gson = new Gson();

    /**
     * {@link #json2BeanObject(String, Class, Class)}
     */
    public static <T> GsonBean<List<T>> json2BeanArray(String json, Class<? extends GsonBean> rowClass, Class<T> clazz) {
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
    public static <T> GsonBean<T> json2BeanObject(String json, Class<? extends GsonBean> rowClass, Class<T> clazz) {
        Type type = TypeBuilder
                .newInstance(rowClass)
                .addTypeParam(clazz)
                .build();
        return gson.fromJson(json, type);
    }

    //----------------------------------
    public static <T> T json2Bean(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    public static <T> List<T> json2BeanList(String json, Class<T> tClass) {
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

    public static String bean2Json(Object object) {
        if (null == object)
            return "{}";
        return gson.toJson(object);
    }

    public static String list2Json(Object object) {
        if (null == object)
            return "[]";
        return gson.toJson(object);
    }

    public static <T> List<T> resJson2BeanList(Context context, String resName, Class<T> tClass) {
        if (TextUtils.isEmpty(resName))
            return new ArrayList<>();
        String json = ResUtil.getStrFromRes(context, resName);
        return json2BeanList(json, tClass);
    }

    public static <T> T resJson2Bean(Context context, String resName, Class<T> tClass) {
        if (TextUtils.isEmpty(resName))
            return null;
        String json = ResUtil.getStrFromRes(context, resName);
        return TextUtils.isEmpty(resName) ? null : json2Bean(json, tClass);
    }

    /**
     * @param fromRes 是否Assets目录下的json文件
     *                true：表示param json是Assets目录下的json文件名 ex："test.json"
     *                false：表示param json是json字符串 ex：{"name":"张三"}
     * @param json    json文件或者Assets下的文件名
     */
    public static <T> List<T> json2BeanList(Context context, String json, Class<T> t, boolean fromRes) {
        List<T> list = new ArrayList<>();
        if (TextUtils.isEmpty(json)) {
            list.clear();
            return list;
        }
        if (fromRes)
            list = resJson2BeanList(context, json, t);
        else
            list = json2BeanList(json, t);
        return list;
    }
}
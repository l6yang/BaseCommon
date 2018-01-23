package com.loyal.base.util;

import java.lang.reflect.Method;

public class ObjectUtil {
    /**
     * @param tClass     xxx.class
     * @param methodName methodName
     * @return 此类该方法的返回值
     */
    public static Object getMethodValue(Object tClass, String methodName) {
        if (null == tClass)
            return null;
        try {
            Method method = tClass.getClass().getMethod(methodName);
            return method.invoke(tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public static Object setMethodParam(Class<?> tClass, String methodName) {
        if (null == tClass)
            return null;
        try {
            //Class<?>... paramClass
            //Object... params
            Method method = tClass.getClass().getMethod(methodName, paramClass);
            return method.invoke(tClass, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/
}

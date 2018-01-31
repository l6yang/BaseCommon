package com.loyal.base.sample.java;

import com.loyal.base.sample.beans.TestBean;
import com.loyal.base.util.GsonUtil;

import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        final String json1 = "{" +
                "\"message\": \"北京\",\n" +
                "\"code\": \"010\"\n" +
                "}";
        final String json2 = "{" +
                "\"message\": \"北京\",\n" +
                "\"code\": 2\n" +
                "}";
        TestBean<String> testMain1 = (TestBean<String>) GsonUtil.json2BeanObject(json1, TestBean.class, String.class);
        System.out.println(testMain1.getCode());
        System.out.println(testMain1.getMessage());
        System.out.println(json2);
        TestBean<String> testMain2 = (TestBean<String>) GsonUtil.json2BeanObject(json2, TestBean.class, String.class);
        System.out.println(testMain2.getCode());
        System.out.println(testMain2.getMessage());
        final String json3 = "{" +"\"message\": \"测试\",\n" + "\"code\":[\n" +
                "{" +
                "\"cityName\": \"北京\",\n" +
                "\"cityCode\": \"010\"\n" +
                "},\n" +
                "{\n" +
                "\"cityName\": \"上海\",\n" +
                "\"cityCode\": \"021\"\n" +
                "}]}";
        System.out.println(json3);
        TestBean<List<TestBean.CityBean>> testBean3 = (TestBean<List<TestBean.CityBean>>) GsonUtil.json2BeanArray(json3, TestBean.class, TestBean.CityBean.class);
        System.out.println(testBean3.getCode().size());
    }
}

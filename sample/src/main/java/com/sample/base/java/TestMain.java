package com.sample.base.java;

import com.sample.base.beans.TestBean;
import com.loyal.kit.GsonUtil;
import com.loyal.kit.OutUtil;

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
        OutUtil.println(testMain1.getCode());
        OutUtil.println(testMain1.getMessage());
        OutUtil.println(json2);
        TestBean<String> testMain2 = (TestBean<String>) GsonUtil.json2BeanObject(json2, TestBean.class, String.class);
        OutUtil.println(testMain2.getCode());
        OutUtil.println(testMain2.getMessage());
        final String json3 = "{" +"\"message\": \"测试\",\n" + "\"code\":[\n" +
                "{" +
                "\"cityName\": \"北京\",\n" +
                "\"cityCode\": \"010\"\n" +
                "},\n" +
                "{\n" +
                "\"cityName\": \"上海\",\n" +
                "\"cityCode\": \"021\"\n" +
                "}]}";
        OutUtil.println(json3);
        TestBean<List<TestBean.CityBean>> testBean3 = (TestBean<List<TestBean.CityBean>>) GsonUtil.json2BeanArray(json3, TestBean.class, TestBean.CityBean.class);
        OutUtil.println(testBean3.getCode().size());
    }
}

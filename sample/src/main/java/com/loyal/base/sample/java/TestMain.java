package com.loyal.base.sample.java;

import com.loyal.base.sample.beans.SpinBean;
import com.loyal.base.util.ObjectUtil;

public class TestMain {
    public static void main(String[] args) {
        SpinBean spinBean = new SpinBean();
        //spinBean.setKey("666");
        spinBean.setValue("11");
        spinBean.setBmmc("bmmc");
        spinBean.setDm("dm");
        String value = (String) ObjectUtil.getMethodValue(spinBean, "getBmmc");
        System.out.println("getValue::" + value);
    }
}

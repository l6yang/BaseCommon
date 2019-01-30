package com.sample.base.beans;

import com.loyal.kit.PatternBean;

public class TestBean<T> extends PatternBean {
    private T code;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setCode(T code) {
        this.code = code;
    }

    public T getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class CityBean {
        private String cityName;
        private String cityCode;

        public String getCityCode() {
            return cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }
}

package com.loyal.base.gps;

import android.location.Address;
import android.util.SparseArray;

public class GpsLocBean {
    private double latitude;//纬度
    private double longitude;//经度
    private String province;//省市
    private String cityName;//城市
    private String address;//详细地址
    private String countryName;//国家:中国
    private String countryCode;//国家代码:CN
    private Address description;//
    private SparseArray<String> nearArray;//附近的地址
    private boolean hasLatitude;//是否有纬度
    private boolean hasLongitude;//是否有经度
    private String area;//所在区:雁塔区
    private String road;//当前路段:科技路

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Address getDescription() {
        return description;
    }

    public void setDescription(Address description) {
        this.description = description;
    }

    public SparseArray<String> getNearArray() {
        return nearArray;
    }

    public void setHasLatitude(boolean hasLatitude) {
        this.hasLatitude = hasLatitude;
    }

    public void setHasLongitude(boolean hasLongitude) {
        this.hasLongitude = hasLongitude;
    }

    public boolean hasLatitude() {
        return hasLatitude;
    }

    public boolean hasLongitude() {
        return hasLongitude;
    }

    public void setNearArray(SparseArray<String> nearArray) {
        this.nearArray = nearArray;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }
}

package com.loyal.base.impl;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;

public interface IContacts {

    class Str {
        public static final String TIME_ALL = "yyyy-MM-dd HH:mm:ss";
        public static final String TIME_WEEK = "yyyy-MM-dd EEEE";
        public static final String TIME_YEAR_MONTH_DAY = "yyyy-MM-dd";
        public static final String HOURS_MIN = "HH:mm";
        public static final String MONTH_DAY_HOUR_MIN = "MM-dd HH:mm";
        public static final String YEAR_MONTH = "yyyy-MM";

        public static String replaceNull(CharSequence sequence) {
            return TextUtils.isEmpty(sequence) ? "" : sequence.toString().trim();
        }

        public static String encodeStr2Utf(String str) {
            if (TextUtils.isEmpty(str))
                return "";
            try {
                return URLEncoder.encode(str, "utf-8");
            } catch (Exception e) {
                return str;
            }
        }

        public static String decodeStr2Utf(String str) {
            if (TextUtils.isEmpty(str))
                return "";
            try {
                return URLDecoder.decode(str, "utf-8");
            } catch (Exception e) {
                return str;
            }
        }
    }

    class PerMission {
        //日历
        public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
        public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
        //相机
        public static final String CAMERA = Manifest.permission.CAMERA;
        //联系人
        public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
        public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
        public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
        //定位
        public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
        //录音
        public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
        //读取手机状态
        public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
        //存储
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
        public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        public static final int storagePermission = 300;
        public static final int cameraPermission = 301;
        public static final int locationPermission = 302;
        public static final int phoneStatePermission = 303;
        public static final int calendarPermission = 304;
        public static final int contactsPermission = 305;
    }

    enum TYPE {
        NONE, LEFT, RIGHT
    }
}
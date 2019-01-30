package com.loyal.base.impl;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.IntDef;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLDecoder;
import java.net.URLEncoder;

public interface IBaseContacts {

    final class BaseStr {

        public static final String defaultIpAdd = "192.168.0.1";
        public static final String defaultPort = "9080";
        public static final String http = "http";
        public static final String https = "https";
        public static final String nameSpace = "command";
        public static final String baseUrl = String.format("%s://%s:%s/%s/", http, defaultIpAdd, defaultPort, nameSpace);

        public static String replaceNull(CharSequence sequence) {
            return TextUtils.isEmpty(sequence) ? "" : sequence.toString().trim();
        }

        public static String encodeStr2Utf(String str) {
            return encodeString(str, "utf-8");
        }

        public static String encodeString(String str, String enc) {
            if (TextUtils.isEmpty(str))
                return "";
            try {
                return URLEncoder.encode(str, enc);
            } catch (Exception e) {
                return str;
            }
        }

        public static String decodeStr2Utf(String str) {
            return decodeString(str, "utf-8");
        }

        public static String decodeString(String str, String enc) {
            if (TextUtils.isEmpty(str))
                return "";
            try {
                return URLDecoder.decode(str, enc);
            } catch (Exception e) {
                return str;
            }
        }
    }

    final class PerMission {
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

    final class TypeImpl {
        public static final int DOUBLE = 0x00000000;
        public static final int CANCEL = 0x00000002;
        public static final int NEXT = 0x00000004;

        @IntDef({DOUBLE, CANCEL, NEXT})
        @Retention(RetentionPolicy.SOURCE)
        public @interface source {
        }
    }

    final class StatusBarImpl {
        public static final int NONE = 0x00000000;
        public static final int ImmerBar = 0x00000002;
        public static final int StateBar = 0x00000004;

        @IntDef({NONE, ImmerBar, StateBar})
        @Retention(RetentionPolicy.SOURCE)
        public @interface source {
        }
    }
}
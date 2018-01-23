package com.loyal.base.impl;

import android.text.TextUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;

public interface Contacts {

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

    enum TYPE {
        NONE, LEFT, RIGHT
    }
}
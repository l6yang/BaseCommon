package com.loyal.base.util;

import android.support.annotation.IntRange;
import android.text.TextUtils;

import com.loyal.base.impl.IBaseContacts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil implements IBaseContacts {
    public static String getWeek() {
        return replaceTime(setFormat(BaseStr.TIME_WEEK).format(new Date()));
    }

    public static SimpleDateFormat setFormat(String format) {
        return new SimpleDateFormat(format, Locale.CHINA);
    }

    public static String getDate() {
        return replaceTime(setFormat(BaseStr.TIME_YMD).format(new Date()));
    }

    public static String getDate(String format) {
        return replaceTime(setFormat(format).format(new Date()));
    }

    public static String getDate(Date date, String format) {
        return replaceTime(setFormat(format).format(date));
    }

    public static String getDateTime() {
        return replaceTime(setFormat(BaseStr.TIME_ALL).format(new Date()));
    }

    public static String getDateTime(String format) {
        return replaceTime(setFormat(format).format(new Date()));
    }

    public static String getDateTime(Date date, String format) {
        return replaceTime(setFormat(format).format(date));
    }

    public static String getTime() {
        return replaceTime(setFormat(BaseStr.TIME_HM).format(new Date()));
    }

    public static String getTime(String format) {
        return replaceTime(setFormat(format).format(new Date()));
    }

    public static String getTime(Date date, String format) {
        return replaceTime(setFormat(format).format(date));
    }

    public static boolean afterDate(String startTime, String endTime, String format) {
        try {
            SimpleDateFormat sdf = setFormat(format);
            if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime))
                return true;
            if (TextUtils.equals(startTime, endTime))
                return true;
            Date start = sdf.parse(startTime);
            Date end = sdf.parse(endTime);
            return end.after(start);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param startTime 开始时间
     * @param endTime   结束时间
     *                  如：2018-07-01,2018-07-02,相差1天
     * @return 两个时间段相隔几天
     */
    public static float daysDifference(String startTime, String endTime) {
        return daysDifference(startTime, endTime, BaseStr.TIME_YMD);
    }

    public static float daysDifference(String startTime, String endTime, String format) {
        return daysDifference(startTime, endTime, format, 24);
    }

    /**
     * @param hour 12小时制还是24小时制
     */
    public static float daysDifference(String startTime, String endTime, String format, @IntRange(from = 1, to = 24) int hour) {
        return daysDifference(startTime, endTime, format, hour, false);
    }

    /**
     * @param hour         12小时制还是24小时制
     * @param addStartTime 是否算上起始时间
     */
    public static float daysDifference(String startTime, String endTime, String format, @IntRange(from = 1, to = 24) int hour, boolean addStartTime) {
        try {
            SimpleDateFormat sdf = setFormat(format);
            if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime))
                return -1;
            if (TextUtils.equals(startTime, endTime))
                return 0;
            Date start = sdf.parse(replaceTime(startTime));
            Date end = sdf.parse(replaceTime(endTime));
            long span = (end.getTime() - start.getTime()) / 1000;
            float day = (float) span / (hour * 3600);
            return day >= 0 ? (addStartTime ? day + 1 : day) : -1;
            //DecimalFormat df = new DecimalFormat("#.00");
            //return df.format(days);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 两个时间段相差几个小时
     */
    public static float hourDifference(String startTime, String endTime) {
        try {
            SimpleDateFormat sdf = setFormat(BaseStr.TIME_ALL);
            if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime))
                return -1;
            if (TextUtils.equals(startTime, endTime))
                return 0;
            Date start = sdf.parse(replaceTime(startTime));
            Date end = sdf.parse(replaceTime(endTime));
            long span = (end.getTime() - start.getTime()) / 1000;
            float hour = (float) span / (3600);
            hour = (float) Math.round(hour * 100) / 100;
            return hour >= 0 ? hour : -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * format时间格式转换为long
     *
     * @param time 默认格式为yyyy-MM-dd HH:mm:ss
     */
    public static long time2LongDate(String time) {
        return time2LongDate(time, BaseStr.TIME_ALL);
    }

    /**
     * @param time   必须和所传的时间格式相匹配
     * @param format 默认yyyy-MM-dd HH:mm:ss
     */
    public static long time2LongDate(String time, String format) {
        try {
            if (TextUtils.isEmpty(time))
                return 0;
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE);
            Date start = sdf.parse(time);
            return start.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String replaceTime(String str) {
        return str.toLowerCase().replace("上午", "").replace("下午", "").replace("am", "").replace("pm", "");
    }

    public static String subEndTime(CharSequence timeSequence) {
        String subTime = BaseStr.replaceNull(timeSequence).trim();
        return TextUtils.isEmpty(subTime) ? "" : subTime.
                replace("00:00:00", "").replace(".0", "").trim();
    }
}

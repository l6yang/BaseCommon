package com.loyal.base.util;

import android.util.Log;

public class OutUtil {
    public static boolean DEBUG = true;

    public static void print() {
        print("");
    }

    public static void print(String tag, Object object) {
        print(tag + "#:#" + object);
    }

    public static void print(String printStr) {
        if (DEBUG) System.out.print(printStr);
    }

    public static void println() {
        println("");
    }

    public static void println(String tag, Object object) {
        println(tag + "#:#" + object);
    }

    public static void println(Object printStr) {
        if (DEBUG) System.out.println(printStr);
    }

    public static void d(String tag, String msg) {
        if (DEBUG) Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable throwable) {
        if (DEBUG) Log.d(tag, msg, throwable);
    }

    public static void e(String tag, String msg) {
        if (DEBUG) Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (DEBUG) Log.e(tag, msg, throwable);
    }

    public static void i(String tag, String msg) {
        if (DEBUG) Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable throwable) {
        if (DEBUG) Log.i(tag, msg, throwable);
    }

    public static void v(String tag, String msg) {
        if (DEBUG) Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable throwable) {
        if (DEBUG) Log.v(tag, msg, throwable);
    }

    public static void w(String tag, String msg) {
        if (DEBUG) Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable throwable) {
        if (DEBUG) Log.w(tag, msg, throwable);
    }
}

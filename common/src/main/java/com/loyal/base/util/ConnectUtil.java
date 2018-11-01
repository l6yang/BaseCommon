package com.loyal.base.util;

import android.text.TextUtils;

import com.loyal.base.impl.IBaseContacts;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ConnectUtil implements IBaseContacts {

    public static String getError(Throwable throwable) {
        if (null == throwable)
            return "未知错误";
        if (throwable instanceof ConnectException)
            return "连接服务器失败";
        else if (throwable instanceof SocketTimeoutException)
            return "连接超时";
        else if (throwable instanceof UnknownHostException)
            return "网络未连接或者当前网络地址未被识别";
        else if (throwable instanceof SocketException)
            return "网络服务出现问题";
        else return throwable.getMessage();
    }

    /**
     * @param ipAdd 默认端口9080
     *              1、（ip地址中包含端口号）192.168.1.15:9081
     *              2、（ip地址中不含端口号，需要加上默认端口9080）192.168.1.15
     * @return 1、192.168.1.15:9081
     * 2、192.168.1.15:9080
     */
    public static String getIpAddressByDefaultIp(String ipAdd) {
        return getIpAddressByDefaultIp(ipAdd, BaseStr.defaultPort);
    }

    /**
     * 默认端口192.168.0.1
     */
    public static String getIpAddressByDefaultIp(String ipAdd, String port) {
        return getIpAddress(ipAdd, BaseStr.defaultIpAdd, port);
    }

    /**
     * @param ipAdd 默认端口9080
     *              1、（ip地址中包含端口号）192.168.1.15:9081
     *              2、（ip地址中不含端口号，需要加上默认端口9080）192.168.1.15
     * @return 1、192.168.1.15:9081
     * 2、192.168.1.15:9080
     */
    public static String getIpAddressByDefaultPort(String ipAdd) {
        return getIpAddressByDefaultPort(ipAdd, BaseStr.defaultPort);
    }

    /**
     * 默认端口9080
     */
    public static String getIpAddressByDefaultPort(String ipAdd, String defaultPort) {
        return getIpAddress(ipAdd, BaseStr.defaultIpAdd, defaultPort);
    }

    /**
     * @param ipAdd 默认端口9080
     *              1、（ip地址中包含端口号）192.168.0.1:9081
     *              2、（ip地址中不含端口号，需要加上默认端口9080）192.168.0.1
     * @return 1、192.168.0.1:9081
     * 2、192.168.0.1:9080
     */
    public static String getIpAddress(String ipAdd, String defaultIp, String defaultPort) {
        String address;
        if (TextUtils.isEmpty(ipAdd)) {
            address = defaultIp + ":" + defaultPort;
            return address;
        }
        try {
            if (ipAdd.contains(":")) {
                address = ipAdd;
            } else {
                address = ipAdd + ":" + defaultPort;
            }
        } catch (Exception e) {
            e.printStackTrace();
            address = ipAdd;
        }
        return address;
    }

    /**
     * @see #getBaseUrl(String, String, String, String)
     */
    public static String getBaseUrl(String ipAdd, String nameSpace) {
        return getBaseUrl(BaseStr.http, ipAdd, nameSpace);
    }

    /**
     * @see #getBaseUrl(String, String, String, String)
     */
    public static String getBaseUrl(String http, String ipAdd, String nameSpace) {
        return getBaseUrl(http, ipAdd, BaseStr.defaultPort, nameSpace);
    }

    /**
     * @param http      http或者https
     * @param ipAdd     不一定非要是IP地址，baidu.com 也可以
     * @param port      端口号
     * @param nameSpace test
     * @return http://192.168.0.155:9080/test/ 必须以"/"结尾
     */
    public static String getBaseUrl(String http, String ipAdd, String port, String nameSpace) {
        String url = getIpAddressByDefaultPort(ipAdd, port);
        return String.format("%s://%s/%s/", http, url, nameSpace);
    }
}

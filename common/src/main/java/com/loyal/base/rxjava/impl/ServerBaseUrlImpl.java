package com.loyal.base.rxjava.impl;

public interface ServerBaseUrlImpl {
    String httpOrHttps();//http 或者https

    String serverNameSpace();//访问路径

    /**
     * @param clientIp 客户端配置的IP地址
     * @return http://ip地址:端口/项目访问地址;https://ip地址:端口/项目访问地址
     *
     */
    String baseUrl(String clientIp);
}

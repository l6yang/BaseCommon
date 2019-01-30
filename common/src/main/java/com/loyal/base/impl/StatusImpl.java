package com.loyal.base.impl;

public interface StatusImpl {

    boolean isFullScreen();//全屏状态栏透明/普通模式沉浸式

    void statusBar(@IBaseContacts.StatusBarImpl.source int barStatus);//沉浸式状态栏
}

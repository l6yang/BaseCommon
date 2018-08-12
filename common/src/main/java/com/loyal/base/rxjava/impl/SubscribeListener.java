package com.loyal.base.rxjava.impl;

public interface SubscribeListener<T> {
    void onResult(int what, Object tag, T result);

    void onError(int what, Object tag, Throwable e);
}
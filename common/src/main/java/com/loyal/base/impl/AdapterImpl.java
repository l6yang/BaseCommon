package com.loyal.base.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public interface AdapterImpl<T> extends IBaseContacts {
    /*刷新列表*/
    void notifyList(List<T> arrList);

    /*空数据刷新列表*/
    void notifyList();

    /*赋值，不刷新列表*/
    void changedList(List<T> arrList);

    Context getContext();

    Activity getActivity();

    @NonNull
    List<T> getArrayList();

    Intent getIntent();

    T getItem(int position);

    @NonNull
    String replaceNull(CharSequence sequence);

    @NonNull
    String subEndTime(CharSequence time);

    View getConvertView(@LayoutRes int resId, ViewGroup parent);
}

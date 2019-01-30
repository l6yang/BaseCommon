package com.loyal.base.adapter;

import android.content.Context;

import com.loyal.base.impl.AdapterImpl;

import java.util.List;

public abstract class ABasicBindListAdapter<T> extends ABasicAdapter<T> implements AdapterImpl<T> {

    public ABasicBindListAdapter(Context context) {
        super(context);
    }

    public ABasicBindListAdapter(Context context, List<T> arrList) {
        super(context, arrList);
    }

    public ABasicBindListAdapter(Context context, String json, Class<T> t) {
        super(context, json, t);
    }

    public ABasicBindListAdapter(Context context, String json, Class<T> t, boolean isFile) {
        super(context, json, t, isFile);
    }

    public abstract int variableId();
}

package com.loyal.base.adapter;

import android.content.Context;

import com.loyal.base.impl.AdapterImpl;

import java.util.List;

public abstract class ABasicBindRecycleAdapter<T,VH extends ABasicRecyclerViewHolder> extends ABasicRecycleAdapter<T,VH> implements AdapterImpl<T> {

    public ABasicBindRecycleAdapter(Context context) {
        super(context);
    }

    public ABasicBindRecycleAdapter(Context context, List<T> arrList) {
        super(context, arrList);
    }

    public ABasicBindRecycleAdapter(Context context, String json, Class<T> t) {
        super(context, json, t);
    }

    public ABasicBindRecycleAdapter(Context context, String json, Class<T> t, boolean isFile) {
        super(context, json, t, isFile);
    }

    public abstract int variableId();

}

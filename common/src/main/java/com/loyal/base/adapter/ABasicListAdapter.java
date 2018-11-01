package com.loyal.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class ABasicListAdapter<T, V extends ABasicListViewHolder> extends ABasicAdapter<T> {

    public ABasicListAdapter(Context context) {
        super(context);
    }

    public ABasicListAdapter(Context context, List<T> arrList) {
        super(context, arrList);
    }

    public ABasicListAdapter(Context context, String json, Class<T> t) {
        super(context, json, t);
    }

    public ABasicListAdapter(Context context, String json, Class<T> t, boolean isFile) {
        super(context, json, t, isFile);
    }

    @Override
    public View getConvertView(int resId, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(resId, parent, false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V holder;
        if (convertView == null) {
            convertView = getConvertView(adapterLayout(), parent);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else holder = (V) convertView.getTag();
        onViewHolder(holder, position);
        return convertView;
    }

    public abstract V createViewHolder(View view);

    public abstract void onViewHolder(V holder, int position);

}

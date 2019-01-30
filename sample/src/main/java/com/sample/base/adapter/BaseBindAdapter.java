package com.sample.base.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loyal.base.adapter.ABasicBindListAdapter;

import java.util.List;

public  class BaseBindAdapter<T, B extends ViewDataBinding> extends ABasicBindListAdapter<T> {
    protected B binding;

    public BaseBindAdapter(Context context) {
        super(context);
    }

    public BaseBindAdapter(Context context, List<T> arrList) {
        super(context, arrList);
    }

    public BaseBindAdapter(Context context, String json, Class<T> t) {
        super(context, json, t);
    }

    public BaseBindAdapter(Context context, String json, Class<T> t, boolean isFile) {
        super(context, json, t, isFile);
    }

    @Override
    public int adapterLayout() {
        return 0;
    }

    @Override
    public int variableId() {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //创建一个dataBinding
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), adapterLayout(), parent, false);
            //获取convertView
            convertView = binding.getRoot();
        } else {
            //去除convertView中banding的dataBinding
            binding = DataBindingUtil.getBinding(convertView);
        }
        T t = getItem(position);
        binding.setVariable(variableId(), t);
        onHoldView(position, t);
        return convertView;
    }

    public void onHoldView(int position, T t) {
    }
}

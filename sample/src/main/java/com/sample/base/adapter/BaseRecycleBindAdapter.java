package com.sample.base.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.loyal.base.adapter.ABasicBindRecycleAdapter;

import java.util.List;

public abstract class BaseRecycleBindAdapter<T, B extends ViewDataBinding> extends ABasicBindRecycleAdapter<T, RecycleBindViewHolder<B>> {

    public BaseRecycleBindAdapter(Context context) {
        super(context);
    }

    public BaseRecycleBindAdapter(Context context, List<T> arrList) {
        super(context, arrList);
    }

    public BaseRecycleBindAdapter(Context context, String json, Class<T> t) {
        super(context, json, t);
    }

    public BaseRecycleBindAdapter(Context context, String json, Class<T> t, boolean isFile) {
        super(context, json, t, isFile);
    }

    @NonNull
    @Override
    public RecycleBindViewHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        B binding = DataBindingUtil.inflate(inflater, adapterLayout(), parent, false);
        RecycleBindViewHolder<B> holder = new RecycleBindViewHolder<>(binding.getRoot().getRootView());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleBindViewHolder<B> viewHolder, int position) {
        B binding = viewHolder.getBinding();
        T text = getItem(position);
        System.out.println("text----" + text);
        binding.setVariable(variableId(), getItem(position));
        binding.executePendingBindings();
    }
}

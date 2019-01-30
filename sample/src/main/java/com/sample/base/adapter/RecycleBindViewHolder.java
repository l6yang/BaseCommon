package com.sample.base.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.View;

import com.loyal.base.adapter.ABasicRecyclerViewHolder;
import com.sample.base.impl.RecycleBindImpl;

import butterknife.ButterKnife;

public class RecycleBindViewHolder<B extends ViewDataBinding> extends ABasicRecyclerViewHolder implements RecycleBindImpl<B> {
    private B binding;

    @Override
    public void bindView(View view) {
        ButterKnife.bind(itemView);
    }

    public RecycleBindViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setBinding(B binding) {
        this.binding = binding;
    }

    @Override
    public B getBinding() {
        return binding;
    }
}

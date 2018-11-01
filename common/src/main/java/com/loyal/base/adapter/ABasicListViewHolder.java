package com.loyal.base.adapter;

import android.view.View;

public abstract class ABasicListViewHolder {
    private View itemView;

    public abstract void bindView(View view);

    public ABasicListViewHolder(View view) {
        this.itemView = view;
        bindView(view);
    }

    public View getView() {
        return itemView;
    }
}
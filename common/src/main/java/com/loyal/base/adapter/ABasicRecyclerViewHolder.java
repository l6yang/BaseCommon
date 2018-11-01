package com.loyal.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ABasicRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public abstract void bindView(View view);

    public ABasicRecyclerViewHolder(View itemView) {
        this(itemView, null, null);
    }

    public ABasicRecyclerViewHolder(View itemView, OnItemClickListener itemClickListener) {
        this(itemView, itemClickListener, null);
    }

    public ABasicRecyclerViewHolder(View itemView, OnItemClickListener itemClickListener, OnItemLongClickListener itemLongClickListener) {
        super(itemView);
        bindView(itemView);
        mOnItemClickListener = itemClickListener;
        itemView.setOnClickListener(this);
        mItemLongClickListener = itemLongClickListener;
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return mItemLongClickListener == null || mItemLongClickListener.onItemLongClick(v, getLayoutPosition());
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
}

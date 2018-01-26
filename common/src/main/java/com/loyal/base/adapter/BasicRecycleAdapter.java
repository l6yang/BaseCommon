package com.loyal.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loyal.base.impl.Contacts;
import com.loyal.base.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicRecycleAdapter<VH extends BasicViewHolder, T> extends RecyclerView.Adapter<VH> implements Contacts {
    private int mCount = 0;
    private LayoutInflater mInflater;
    protected BasicViewHolder.OnItemClickListener mOnItemClickListener;
    protected BasicViewHolder.OnItemLongClickListener mOnItemLongClickListener;
    private List<T> arrayList;
    private Context context;

    public BasicRecycleAdapter(Context context) {
        this(context, null);
    }

    public BasicRecycleAdapter(Context context, List<T> arrList) {
        this.context = context;
        if (null == arrList)
            arrList = new ArrayList<>();
        this.arrayList = arrList;
        mInflater = LayoutInflater.from(context);
    }

    public void changedList(List<T> arrList) {
        if (null == arrList)
            arrList = new ArrayList<>();
        this.arrayList = arrList;
    }

    public void refreshList(List<T> arrList) {
        changedList(arrList);
        notifyDataSetChanged();
    }

    public void refreshList() {
        refreshList(null);
    }

    public List<T> getArrList() {
        return arrayList;
    }

    protected abstract @LayoutRes
    int adapterLayout();

    protected View getConvertView(ViewGroup parent) {
        return mInflater.inflate(adapterLayout(), parent, false);
    }

    public void setCount(int count) {
        mCount = count;
    }

    public Context getContext() {
        return context;
    }

    public Activity getActivity() {
        return (Activity) context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        try {
            return arrayList.get(position);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public void setOnItemClickListener(BasicViewHolder.OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    public boolean setOnItemLongClickListener(BasicViewHolder.OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
        return false;
    }

    public String replaceNull(CharSequence sequence) {
        return Str.replaceNull(sequence);
    }

    public String subEndTime(CharSequence sequence) {
        return TimeUtil.subEndTime(sequence);
    }
}

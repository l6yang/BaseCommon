package com.loyal.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loyal.base.impl.IBaseContacts;
import com.loyal.kit.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 适用于分页加载
 * 继承此Adapter，需要设置{@link ABasicPullRecycleAdapter#setCount(int)}
 * 在Adapter里面设置或在外面设置都一样
 */
public abstract class ABasicPullRecycleAdapter<T, VH extends ABasicRecyclerViewHolder> extends RecyclerView.Adapter<VH> implements IBaseContacts {
    private int mCount = 0;
    private LayoutInflater mInflater;
    protected ABasicRecyclerViewHolder.OnItemClickListener mOnItemClickListener;
    protected ABasicRecyclerViewHolder.OnItemLongClickListener mOnItemLongClickListener;
    private List<T> arrayList;
    private Context context;

    public ABasicPullRecycleAdapter(Context context) {
        this(context, null);
    }

    public ABasicPullRecycleAdapter(Context context, List<T> arrList) {
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

    public abstract @LayoutRes
    int adapterLayout();

    public View getConvertView(ViewGroup parent) {
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

    public void setOnItemClickListener(ABasicRecyclerViewHolder.OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    public boolean setOnItemLongClickListener(ABasicRecyclerViewHolder.OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
        return false;
    }

    public String replaceNull(CharSequence sequence) {
        return BaseStr.replaceNull(sequence);
    }

    public String subEndTime(CharSequence sequence) {
        return TimeUtil.subEndTime(sequence);
    }
}

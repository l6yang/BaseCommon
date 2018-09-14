package com.loyal.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loyal.base.impl.IBaseContacts;
import com.loyal.base.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class ABasicRecycleAdapter<T, VH extends ABasicViewHolder> extends RecyclerView.Adapter<VH> implements IBaseContacts {
    private LayoutInflater mInflater;
    protected ABasicViewHolder.OnItemClickListener mOnItemClickListener;
    protected ABasicViewHolder.OnItemLongClickListener mOnItemLongClickListener;
    private List<T> arrayList;
    private Context context;

    public ABasicRecycleAdapter(Context context) {
        this(context, null);
    }

    public ABasicRecycleAdapter(Context context, List<T> arrList) {
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

    public Context getContext() {
        return context;
    }

    public Activity getActivity() {
        return (Activity) context;
    }

    public Intent getIntent() {
        Activity activity = getActivity();
        return null == activity ? null : activity.getIntent();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        return null == arrayList ? null : arrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return null == arrayList ? 0 : arrayList.size();
    }

    public void setOnItemClickListener(ABasicViewHolder.OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    public boolean setOnItemLongClickListener(ABasicViewHolder.OnItemLongClickListener listener) {
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

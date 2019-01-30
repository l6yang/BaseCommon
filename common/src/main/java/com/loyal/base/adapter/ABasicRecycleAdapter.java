package com.loyal.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loyal.base.impl.AdapterImpl;
import com.loyal.kit.GsonUtil;
import com.loyal.kit.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class ABasicRecycleAdapter<T, VH extends ABasicRecyclerViewHolder> extends RecyclerView.Adapter<VH> implements AdapterImpl<T> {
    protected ABasicRecyclerViewHolder.OnItemClickListener mOnItemClickListener;
    protected ABasicRecyclerViewHolder.OnItemLongClickListener mOnItemLongClickListener;
    private List<T> arrayList;
    private Context context;

    public ABasicRecycleAdapter(Context context) {
        this(context, null);
    }

    public ABasicRecycleAdapter(Context context, List<T> arrList) {
        this.context = context;
        changedList(arrList);
    }

    /**
     * @param context Context
     * @param t       class
     * @param json    如："test.json";
     *                默认 json格式的文件
     */
    public ABasicRecycleAdapter(Context context, String json, Class<T> t) {
        this(context, json, t, true);
    }

    /**
     * @param context Context
     * @param isFile  true：json格式的文件名
     *                false：标准的json格式字符串
     * @param t       class
     * @param json    isFile=true 如："test.json";
     *                isFile=false 如：{"name":"张三"};
     */
    public ABasicRecycleAdapter(Context context, String json, Class<T> t, boolean isFile) {
        this(context, GsonUtil.json2BeanList(context, json, t, isFile));
    }

    @Override
    public void changedList(List<T> arrList) {
        if (null == arrList)
            arrList = new ArrayList<>();
        this.arrayList = arrList;
    }

    @Override
    public void notifyList() {
        notifyList(null);
    }

    @Override
    public void notifyList(List<T> arrList) {
        changedList(arrList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public List<T> getArrayList() {
        return arrayList;
    }

    public abstract @LayoutRes
    int adapterLayout();

    public View getConvertView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(adapterLayout(), parent, false);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Activity getActivity() {
        return (Activity) context;
    }

    @Override
    public Intent getIntent() {
        Activity activity = getActivity();
        return null == activity ? null : activity.getIntent();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public T getItem(int position) {
        return null == arrayList ? null : arrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return null == arrayList ? 0 : arrayList.size();
    }

    public void setOnItemClickListener(ABasicRecyclerViewHolder.OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    public boolean setOnItemLongClickListener(ABasicRecyclerViewHolder.OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
        return false;
    }

    @NonNull
    public String replaceNull(CharSequence sequence) {
        return BaseStr.replaceNull(sequence);
    }

    @NonNull
    public String subEndTime(CharSequence sequence) {
        return TimeUtil.subEndTime(sequence);
    }
}

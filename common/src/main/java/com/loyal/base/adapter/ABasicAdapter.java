package com.loyal.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.BaseAdapter;

import com.loyal.base.impl.AdapterImpl;
import com.loyal.base.util.GsonUtil;
import com.loyal.base.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class ABasicAdapter<T> extends BaseAdapter implements AdapterImpl<T> {
    private List<T> arrList;
    private Context mContext;

    public ABasicAdapter(Context context) {
        this(context, null);
    }

    public ABasicAdapter(Context context, List<T> arrList) {
        this.mContext = context;
        changedList(arrList);
    }

    /**
     * @param context Context
     * @param t       class
     * @param json    如："test.json";
     *                默认 json格式的文件
     */
    public ABasicAdapter(Context context, String json, Class<T> t) {
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
    public ABasicAdapter(Context context, String json, Class<T> t, boolean isFile) {
        this(context, GsonUtil.json2BeanList(context, json, t, isFile));
    }

    @Override
    public void notifyList(List<T> arrList) {
        changedList(arrList);
        notifyDataSetChanged();
    }

    @Override
    public void notifyList() {
        notifyList(null);
    }

    @Override
    public void changedList(List<T> arrList) {
        if (null == arrList)
            arrList = new ArrayList<>();
        this.arrList = arrList;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public Activity getActivity() {
        return (Activity) mContext;
    }

    @Override
    public Intent getIntent() {
        return null == getActivity() ? null : getActivity().getIntent();
    }

    @Override
    public T getItem(int position) {
        return arrList == null ? null : arrList.get(position);
    }

    @Override
    public int getCount() {
        return arrList == null ? 0 : arrList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public List<T> getArrayList() {
        return null == arrList ? new ArrayList<T>() : arrList;
    }

    @NonNull
    @Override
    public String replaceNull(CharSequence sequence) {
        return BaseStr.replaceNull(sequence);
    }

    @NonNull
    @Override
    public String subEndTime(CharSequence time) {
        return TimeUtil.subEndTime(time);
    }

    protected abstract
    @LayoutRes
    int adapterLayout();
}

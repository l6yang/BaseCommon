package com.loyal.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.loyal.base.impl.IBaseContacts;
import com.loyal.base.util.GsonUtil;
import com.loyal.base.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class ABasicListAdapter<T, V extends ABasicListAdapter.ViewHolder> extends BaseAdapter implements IBaseContacts {
    private final LayoutInflater inflater;
    private List<T> arrList;
    private Context mContext;

    public ABasicListAdapter(Context context) {
        this(context, null);
    }

    public ABasicListAdapter(Context context, List<T> arrList) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        if (null == arrList)
            arrList = new ArrayList<>();
        this.arrList = arrList;
    }

    /**
     * @param context Context
     * @param isFile  true：json格式的文件名
     *                false：标准的json格式字符串
     * @param t       class
     * @param json    isFile=true 如："test.json";
     *                isFile=false 如：{"name":"张三"};
     */
    public ABasicListAdapter(Context context, String json, Class<T> t, boolean isFile) {
        inflater = LayoutInflater.from(context);
        this.arrList = GsonUtil.json2BeanList(context, json, t, isFile);
    }

    public void notifyList(List<T> arrList) {
        changedList(arrList);
        notifyDataSetChanged();
    }

    public void notifyList() {
        notifyList(null);
    }

    public void changedList(List<T> arrList) {
        if (null == arrList)
            arrList = new ArrayList<>();
        this.arrList = arrList;
    }

    public Context getContext() {
        return mContext;
    }

    public Activity getActivity() {
        return (Activity) mContext;
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

    public List<T> getArrList() {
        return arrList;
    }

    protected String replaceNull(CharSequence sequence) {
        return BaseStr.replaceNull(sequence);
    }

    protected String subEndTime(String t) {
        return TimeUtil.subEndTime(t);
    }

    private View getConvertView(@LayoutRes int resId, ViewGroup parent) {
        return inflater.inflate(resId, parent, false);
    }

    protected abstract
    @LayoutRes
    int adapterLayout();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V holder;
        if (convertView == null) {
            convertView = getConvertView(adapterLayout(), parent);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        } else holder = (V) convertView.getTag();
        onViewHolder(holder, position);
        return convertView;
    }

    public abstract V createViewHolder(View view);

    public abstract void onViewHolder(V holder, int position);

    public abstract class ViewHolder {
        private View itemView;

        public abstract void bindViews(View view);

        public ViewHolder(View view) {
            bindViews(view);
            this.itemView = view;
        }

        public View getView() {
            return itemView;
        }
    }
}

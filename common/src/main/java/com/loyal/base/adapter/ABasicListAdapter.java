package com.loyal.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.loyal.base.impl.IContacts;
import com.loyal.base.util.GsonUtil;
import com.loyal.base.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class ABasicListAdapter<T, V extends ABasicListAdapter.ViewHolder> extends BaseAdapter implements IContacts {
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
     * @param fromRes 是否Assets目录下的json文件
     *                true：表示param json是Assets目录下的json文件名 ex："test.json"
     *                false：表示param json是json字符串 ex：{"name":"张三"}
     * @param json    json文件或者Assets下的文件名
     */
    public ABasicListAdapter(Context context, String json, Class<T> t, boolean fromRes) {
        inflater = LayoutInflater.from(context);
        this.arrList = GsonUtil.json2BeanList(context, json, t, fromRes);
    }

    public void refreshList(List<T> arrList) {
        changedList(arrList);
        notifyDataSetChanged();
    }

    public void refreshList() {
        refreshList(null);
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
        return Str.replaceNull(sequence);
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

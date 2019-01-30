package com.sample.base.adapter;

import android.content.Context;

import com.sample.base.BR;
import com.sample.base.R;
import com.sample.base.databinding.ActivityRecycleBinding;

import java.util.List;

public class Adapter2222 extends BaseRecycleBindAdapter<String, ActivityRecycleBinding> {
    public Adapter2222(Context context, List<String> arrList) {
        super(context, arrList);
    }

    @Override
    public int getItemCount() {
        int count=super.getItemCount();
        System.out.println("count##"+count);
        return super.getItemCount();
    }

    @Override
    public int variableId() {
        return BR.itemStr;
    }

    @Override
    public int adapterLayout() {
        return R.layout.item_bind;
    }
}

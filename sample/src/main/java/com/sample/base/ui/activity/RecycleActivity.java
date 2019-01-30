package com.sample.base.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.sample.base.R;
import com.sample.base.adapter.Adapter2222;
import com.sample.base.base.ABaseActivity;
import com.sample.base.databinding.ActivityRecycleBinding;

import java.util.Arrays;

public class RecycleActivity extends ABaseActivity {
    private ActivityRecycleBinding binding;

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_recycle;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, actLayoutRes());
        System.out.println("null==binding:"+(null==binding));
    }

    @Override
    public void afterOnCreate() {
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView1.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView2.setLayoutManager(new LinearLayoutManager(this));
        String[] arr1 = new String[]{"1", "3", "5", "7", "9"};
        binding.setAdapter1(new Adapter2222(this, Arrays.asList(arr1)));
        String[] arr2 = new String[]{"2", "4", "6", "8", "10"};
        binding.setAdapter2(new Adapter2222(this, Arrays.asList(arr2)));
    }
}

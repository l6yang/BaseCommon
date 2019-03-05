package com.sample.base.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loyal.base.ui.fragment.ABasicBindFragment;
import com.loyal.kit.TimeUtil;
import com.sample.base.R;
import com.sample.base.databinding.FragmentBindingBinding;

public class BindingFragment extends ABasicBindFragment {
    private FragmentBindingBinding binding;

    public BindingFragment() {
    }

    public static BindingFragment newInstance() {
        BindingFragment fragment = new BindingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int fragLayoutRes() {
        return R.layout.fragment_binding;
    }

    @Override
    public void afterOnCreate() {
        binding.setVersion(TimeUtil.getDateTime());
    }

    @NonNull
    @Override
    public View inflaterView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, fragLayoutRes(), container, false);
        return binding.getRoot();
    }

    @Override
    public void bindViews(View view) {

    }

    @Override
    public void unbind() {

    }
}

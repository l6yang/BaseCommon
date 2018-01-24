package com.loyal.base.sample.ui.activity;

import android.widget.TextView;

import com.loyal.base.sample.R;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void afterOnCreate() {
        textView.setText("Just from MainActivity");
    }

    @Override
    public boolean isTransStatus() {
        return false;
    }
}

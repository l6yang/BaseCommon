package com.loyal.base.sample;

import android.widget.TextView;

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

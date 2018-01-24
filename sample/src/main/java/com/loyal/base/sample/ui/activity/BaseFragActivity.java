package com.loyal.base.sample.ui.activity;

import com.loyal.base.ui.activity.BasicFragActivity;

import butterknife.ButterKnife;

public abstract class BaseFragActivity extends BasicFragActivity {
    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    public boolean isTransStatus() {
        return false;
    }
}

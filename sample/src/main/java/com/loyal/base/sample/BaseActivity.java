package com.loyal.base.sample;

import com.loyal.base.ui.BasicActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends BasicActivity {
    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }
}

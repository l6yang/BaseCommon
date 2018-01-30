package com.loyal.base.sample.ui.activity;

import com.loyal.base.ui.activity.ABasicFragActivity;

import butterknife.ButterKnife;

public abstract class ABaseFragActivity extends ABasicFragActivity {
    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    public boolean isTransStatus() {
        return false;
    }
}

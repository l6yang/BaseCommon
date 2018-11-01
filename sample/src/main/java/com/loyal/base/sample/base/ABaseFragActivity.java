package com.loyal.base.sample.base;

import com.loyal.base.ui.activity.ABasicFragActivity;

import butterknife.ButterKnife;

public abstract class ABaseFragActivity extends ABasicFragActivity {
    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }
}
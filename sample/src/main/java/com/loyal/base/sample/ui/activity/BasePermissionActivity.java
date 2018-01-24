package com.loyal.base.sample.ui.activity;

import com.loyal.base.ui.activity.BasicPerMissionActivity;

import butterknife.ButterKnife;

public abstract class BasePermissionActivity extends BasicPerMissionActivity {
    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    public boolean isTransStatus() {
        return false;
    }
}

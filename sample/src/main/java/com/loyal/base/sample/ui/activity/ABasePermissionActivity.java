package com.loyal.base.sample.ui.activity;

import com.loyal.base.ui.activity.ABasicPerMissionActivity;

import butterknife.ButterKnife;

public abstract class ABasePermissionActivity extends ABasicPerMissionActivity {
    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }

    @Override
    public boolean isTransStatus() {
        return false;
    }
}

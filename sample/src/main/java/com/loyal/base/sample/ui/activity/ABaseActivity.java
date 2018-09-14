package com.loyal.base.sample.ui.activity;

import com.loyal.base.ui.activity.ABasicFragActivity;
import com.loyal.base.ui.activity.ABasicPerMissionActivity;

import butterknife.ButterKnife;

public abstract class ABaseActivity extends ABasicFragActivity {
    @Override
    public void bindViews() {
        ButterKnife.bind(this);
    }
}

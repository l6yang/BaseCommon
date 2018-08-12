package com.loyal.base.ui.activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

/**
 * 仅限于DrawerLayout+NavigationView+Toolbar
 */
public abstract class ABasicDrawerActivity extends ABasicPerMissionActivity {
    public abstract @NonNull
    Toolbar toolbar();

    public abstract @NonNull
    NavigationView navigationView();

    @Override
    public void statusBar(int barStatus) {
        super.statusBar(barStatus);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            mImmersionBar.transparentStatusBar().titleBar(toolbar()).init();
            navigationView().setFitsSystemWindows(false);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setElevation(0);
            }
            mImmersionBar.titleBar(toolbar()).init();
            toolbar().setFitsSystemWindows(true);
        }
    }
}

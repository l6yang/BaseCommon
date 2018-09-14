package com.loyal.base.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.loyal.base.impl.IFullScreenImpl;

public abstract class ABasicFullScreenActivity extends ABasicFragActivity implements IFullScreenImpl {
    @Override
    public boolean isFullScreen() {
        return true;
    }

    @Override
    public void hideActionBar() {
        if (null != getActionBar())
            getActionBar().hide();
        if (null != getSupportActionBar())
            getSupportActionBar().hide();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hideActionBar();
    }
}

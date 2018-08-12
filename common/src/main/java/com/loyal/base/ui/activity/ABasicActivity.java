package com.loyal.base.ui.activity;

public abstract class ABasicActivity extends ABasicBindActivity {

    @Override
    public void setViewByLayoutRes() {
        setContentView(actLayoutRes());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }
}

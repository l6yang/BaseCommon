package com.loyal.base.ui.activity;

public abstract class ABasicActivity extends ABasicBindActivity {

    @Override
    public void setContentView() {
        setContentView(actLayoutRes());
    }
}

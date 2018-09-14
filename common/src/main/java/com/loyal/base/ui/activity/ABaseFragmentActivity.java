package com.loyal.base.ui.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * 此类适用于使用FrameLayout的xml布局
 */
public abstract class ABaseFragmentActivity extends ABasicFragActivity {

    public abstract void initViews();

    public abstract void initFragment();

    public abstract void setSelectView(View view);

    public abstract @IdRes
    int frameLayoutId();

    private Fragment mCurrentFragment;

    @Override
    public void afterOnCreate() {
        initViews();
        initFragment();
    }

    public void showFragment(Fragment fragment, String tag) {
        if (fragment == mCurrentFragment)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            if (!fragment.isAdded())
                transaction.hide(mCurrentFragment).add(frameLayoutId(), fragment, tag).commit();
            else
                transaction.hide(mCurrentFragment).show(fragment).commit();
        } else
            transaction.add(frameLayoutId(), fragment, tag).disallowAddToBackStack().commit();
        mCurrentFragment = fragment;
    }
}

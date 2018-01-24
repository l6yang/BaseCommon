package com.loyal.base.sample.ui.fragment;

import android.view.View;

import com.loyal.base.ui.fragment.BasicPerMissionFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends BasicPerMissionFragment {
    private Unbinder unbinder;

    @Override
    public void bindViews(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder)
            unbinder.unbind();
    }
}

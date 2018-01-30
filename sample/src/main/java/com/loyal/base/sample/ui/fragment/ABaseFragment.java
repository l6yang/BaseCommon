package com.loyal.base.sample.ui.fragment;

import android.view.View;

import com.loyal.base.ui.fragment.ABasicPerMissionFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class ABaseFragment extends ABasicPerMissionFragment {
    private Unbinder unbinder;

    @Override
    public void bindViews(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void unbind() {
        if (null != unbinder)
            unbinder.unbind();
    }
}

package com.loyal.base.ui.activity;

import android.content.Context;

import com.loyal.base.impl.IFrag2ActListener;
import com.loyal.base.ui.fragment.ABasicFragment;

/**
 * {@link ABasicFragment#onAttach(Context)}
 */
public abstract class ABasicFragActivity extends ABasicActivity implements IFrag2ActListener {
    @Override
    public void onFrag2Act(String uri) {

    }
}

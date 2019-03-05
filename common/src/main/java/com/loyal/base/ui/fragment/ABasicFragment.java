package com.loyal.base.ui.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loyal.base.ui.activity.ABasicFragActivity;

/**
 * {@link ABasicFragActivity#onFrag2Act(String, Object...)}
 */
public abstract class ABasicFragment extends ABasicBindFragment    {

    @NonNull
    @Override
    public View inflaterView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(fragLayoutRes(), container, false);
    }
}

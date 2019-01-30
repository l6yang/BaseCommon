package com.sample.base.impl;

import android.databinding.ViewDataBinding;

public interface RecycleBindImpl<B extends ViewDataBinding> {
    void setBinding(B binding);

    B getBinding();
}

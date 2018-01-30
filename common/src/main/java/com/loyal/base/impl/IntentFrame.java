package com.loyal.base.impl;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

public final class IntentFrame implements IContacts {
    public interface ActivityFrame extends IContacts {
        void startActivityByAct(@Nullable Class<?> tClass);

        void startActivityForResultByAct(@Nullable Class<?> tClass, @IntRange(from = 2) int reqCode);

        void startServiceByAct(@Nullable Class<?> tClass);
    }

    public interface FragmentFrame extends IContacts {
        void startActivityByFrag(@Nullable Class<?> tClass);

        void startActivityForResultByFrag(@Nullable Class<?> tClass, @IntRange(from = 2) int reqCode);

        void startServiceByFrag(@Nullable Class<?> tClass);
    }
}
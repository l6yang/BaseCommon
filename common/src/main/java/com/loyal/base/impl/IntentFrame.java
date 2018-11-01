package com.loyal.base.impl;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

public final class IntentFrame implements IBaseContacts {
    public interface ActFrame extends IBaseContacts {
        void startActivityByAct(@Nullable String className);

        void startActivityForResultByAct(@Nullable String className, @IntRange(from = 2) int reqCode);

        void startServiceByAct(@Nullable String className);

        void startActivityByAct(@Nullable Class<?> tClass);

        void startActivityForResultByAct(@Nullable Class<?> tClass, @IntRange(from = 2) int reqCode);

        void startServiceByAct(@Nullable Class<?> tClass);
    }

    public interface FragFrame extends IBaseContacts {
        void startActivityByFrag(@Nullable String className);

        void startActivityForResultByFrag(@Nullable String className, @IntRange(from = 2) int reqCode);

        void startServiceByFrag(@Nullable String className);

        void startActivityByFrag(@Nullable Class<?> tClass);

        void startActivityForResultByFrag(@Nullable Class<?> tClass, @IntRange(from = 2) int reqCode);

        void startServiceByFrag(@Nullable Class<?> tClass);
    }
}
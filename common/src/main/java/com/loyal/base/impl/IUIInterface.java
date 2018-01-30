package com.loyal.base.impl;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Spinner;

public interface IUIInterface extends IContacts{
    String subEndTime(@NonNull CharSequence timeSequence);

    String getSpinSelectStr(Spinner spinner, @NonNull String methodName);

    String encodeStr2Utf(@NonNull String string);

    String decodeStr2Utf(@NonNull String string);

    void showToast(@NonNull String text);

    void showToast(@StringRes int resId);

    void showErrorToast(@StringRes int resId, Throwable e);

    void showErrorToast(@NonNull String text, Throwable e);

    void showErrorDialog(@NonNull String text, boolean finish);

    void showErrorDialog(@NonNull String text);

    void showErrorDialog(@NonNull String text, Throwable e, boolean finish);

    void showErrorDialog(@NonNull String text, Throwable e);

    void showDialog(@NonNull String text);

    void showDialog(@NonNull String text, boolean finish);

    String replaceNull(CharSequence sequence);

}

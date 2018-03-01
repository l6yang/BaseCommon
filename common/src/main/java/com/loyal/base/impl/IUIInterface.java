package com.loyal.base.impl;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Spinner;

public interface IUIInterface extends IContacts{
    String subEndTime(@NonNull CharSequence timeSequence);

    String getSpinSelectStr(Spinner spinner, @NonNull String methodName);

    String encodeStr2Utf(@NonNull String string);

    String decodeStr2Utf(@NonNull String string);

    void showToast(@NonNull CharSequence sequence);

    void showToast(@StringRes int resId);

    void showErrorToast(@StringRes int resId, Throwable e);

    void showErrorToast(@NonNull CharSequence sequence, Throwable e);

    void showErrorDialog(@NonNull CharSequence sequence, boolean finish);

    void showErrorDialog(@NonNull CharSequence sequence);

    void showErrorDialog(@NonNull CharSequence sequence, Throwable e, boolean finish);

    void showErrorDialog(@NonNull CharSequence sequence, Throwable e);

    void showDialog(@NonNull CharSequence sequence);

    void showDialog(@NonNull CharSequence sequence, boolean finish);

    String replaceNull(CharSequence sequence);

}

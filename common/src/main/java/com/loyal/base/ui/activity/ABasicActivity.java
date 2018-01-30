package com.loyal.base.ui.activity;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Spinner;

import com.loyal.base.impl.IntentFrame;
import com.loyal.base.impl.IUIInterface;
import com.loyal.base.util.ConnectUtil;
import com.loyal.base.util.IntentUtil;
import com.loyal.base.util.ObjectUtil;
import com.loyal.base.util.StateBarUtil;
import com.loyal.base.util.TimeUtil;
import com.loyal.base.util.ToastUtil;

public abstract class ABasicActivity extends AppCompatActivity implements IntentFrame.ActivityFrame, IUIInterface {

    protected abstract
    @LayoutRes
    int actLayoutRes();

    public abstract void afterOnCreate();

    public abstract boolean isTransStatus();

    public abstract void bindViews();

    protected IntentUtil builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(actLayoutRes());
        bindViews();
        StateBarUtil.setTranslucentStatus(this, isTransStatus());//沉浸式状态栏
        afterOnCreate();
        hasIntentParams(false);
    }

    /***/
    public void hasIntentParams(boolean hasParam) {
        builder = null;
        if (hasParam)
            builder = new IntentUtil(this, getIntent());
        else builder = new IntentUtil(this);
    }

    @Override
    public void startActivityByAct(@Nullable Class<?> tClass) {
        builder.startActivityByAct(tClass);
    }

    @Override
    public void startActivityForResultByAct(@Nullable Class<?> tClass, @IntRange(from = 2) int reqCode) {
        builder.startActivityForResultByAct(tClass, reqCode);
    }

    @Override
    public void startServiceByAct(@Nullable Class<?> tClass) {
        builder.startServiceByAct(tClass);
    }

    @Override
    public void showToast(@NonNull String text) {
        ToastUtil.showToast(this, text);
    }

    @Override
    public void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showErrorToast(int resId, Throwable e) {
        showErrorToast(getString(resId), e);
    }

    @Override
    public void showErrorToast(@NonNull String text, Throwable e) {
        String error = null == e ? "" : ConnectUtil.getError(e);
        showToast(replaceNull(text) + (TextUtils.isEmpty(error) ? "" : "\n" + error));
    }

    @Override
    public void showDialog(@NonNull String text) {
        showDialog(text, false);
    }

    @Override
    public void showDialog(@NonNull String text, boolean finish) {
        ToastUtil.showDialog(this, replaceNull(text), finish);
    }

    @Override
    public String replaceNull(CharSequence sequence) {
        return Str.replaceNull(sequence);
    }

    @Override
    public String subEndTime(@NonNull CharSequence timeSequence) {
        return TimeUtil.subEndTime(timeSequence);
    }

    @Override
    public String encodeStr2Utf(@NonNull String string) {
        return Str.encodeStr2Utf(string);
    }

    @Override
    public String decodeStr2Utf(@NonNull String string) {
        return Str.decodeStr2Utf(string);
    }

    @Override
    public String getSpinSelectStr(Spinner spinner, @NonNull String methodName) {
        return (String) ObjectUtil.getMethodValue(spinner.getSelectedItem(), methodName);
    }

    @Override
    public void showErrorDialog(@NonNull String text) {
        showErrorDialog(text, false);
    }

    @Override
    public void showErrorDialog(@NonNull String text, boolean finish) {
        showErrorDialog(text, null, finish);
    }

    @Override
    public void showErrorDialog(@NonNull String text, Throwable e) {
        showErrorDialog(text, e, false);
    }

    @Override
    public void showErrorDialog(@NonNull String text, Throwable e, boolean finish) {
        String error = null == e ? "" : ConnectUtil.getError(e);
        showDialog(replaceNull(text) + (TextUtils.isEmpty(error) ? "" : "\n" + error), finish);
    }
}

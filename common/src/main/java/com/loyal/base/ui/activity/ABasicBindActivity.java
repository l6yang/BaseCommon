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
import android.widget.Toast;

import com.loyal.base.impl.IUiCommandImpl;
import com.loyal.base.impl.IntentFrame;
import com.loyal.base.util.ConnectUtil;
import com.loyal.base.util.IntentUtil;
import com.loyal.base.util.ObjectUtil;
import com.loyal.base.util.StateBarUtil;
import com.loyal.base.util.TimeUtil;
import com.loyal.base.util.ToastUtil;

/**
 * this Activity just use to MvvM（DataBindingUtil.setContentView(Activity activity, int layoutId)）
 * <p>
 * others Activity do not extends this.
 *
 * @see ABasicActivity
 * </p>
 * @since 2018年3月1日11:44:19
 */
public abstract class ABasicBindActivity extends AppCompatActivity implements IntentFrame.ActFrame, IUiCommandImpl {
    protected abstract
    @LayoutRes
    int actLayoutRes();

    public abstract void afterOnCreate();

    public abstract boolean isTransStatus();

    public abstract void setViewByLayoutRes();
    public abstract void bindViews();

    protected IntentUtil intentBuilder;
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewByLayoutRes();
        bindViews();
        StateBarUtil.setTranslucentStatus(this, isTransStatus());//沉浸式状态栏
        hasIntentParams(false);
        afterOnCreate();
    }

    public void hasIntentParams(boolean hasParam) {
        intentBuilder = null;
        if (hasParam)
            intentBuilder = new IntentUtil(this, getIntent());
        else intentBuilder = new IntentUtil(this);
    }

    @Override
    public void startActivityByAct(@Nullable Class<?> tClass) {
        intentBuilder.startActivityByAct(tClass);
    }

    @Override
    public void startActivityForResultByAct(@Nullable Class<?> tClass, @IntRange(from = 2) int reqCode) {
        intentBuilder.startActivityForResultByAct(tClass, reqCode);
    }

    @Override
    public void startServiceByAct(@Nullable Class<?> tClass) {
        intentBuilder.startServiceByAct(tClass);
    }

    @Override
    public void showToast(@NonNull CharSequence sequence) {
        initToast(sequence);
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
    public void showErrorToast(@NonNull CharSequence sequence, Throwable e) {
        String error = null == e ? "" : ConnectUtil.getError(e);
        showToast(replaceNull(sequence) + (TextUtils.isEmpty(error) ? "" : "\n" + error));
    }

    @Override
    public void showDialog(@NonNull CharSequence sequence) {
        showDialog(sequence, false);
    }

    @Override
    public void showDialog(@NonNull CharSequence sequence, boolean finish) {
        ToastUtil.showDialog(this, replaceNull(sequence), finish);
    }

    @Override
    public String replaceNull(CharSequence sequence) {
        return BaseStr.replaceNull(sequence);
    }

    @Override
    public String subEndTime(@NonNull CharSequence timeSequence) {
        return TimeUtil.subEndTime(timeSequence);
    }

    @Override
    public String encodeStr2Utf(@NonNull String string) {
        return BaseStr.encodeStr2Utf(string);
    }

    @Override
    public String decodeStr2Utf(@NonNull String string) {
        return BaseStr.decodeStr2Utf(string);
    }

    @Override
    public String getSpinSelectStr(Spinner spinner, @NonNull String methodName) {
        return (String) ObjectUtil.getMethodValue(spinner.getSelectedItem(), methodName);
    }

    @Override
    public void showErrorDialog(@NonNull CharSequence sequence) {
        showErrorDialog(sequence, false);
    }

    @Override
    public void showErrorDialog(@NonNull CharSequence sequence, boolean finish) {
        showErrorDialog(sequence, null, finish);
    }

    @Override
    public void showErrorDialog(@NonNull CharSequence sequence, Throwable e) {
        showErrorDialog(sequence, e, false);
    }

    @Override
    public void showErrorDialog(@NonNull CharSequence sequence, Throwable e, boolean finish) {
        String error = null == e ? "" : ConnectUtil.getError(e);
        showDialog(replaceNull(sequence) + (TextUtils.isEmpty(error) ? "" : "\n" + error), finish);
    }

    private void initToast(CharSequence sequence) {
        if (toast == null)
            toast = Toast.makeText(this, sequence, Toast.LENGTH_SHORT);
        else {
            toast.setText(sequence);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != toast)
            toast.cancel();
    }
}

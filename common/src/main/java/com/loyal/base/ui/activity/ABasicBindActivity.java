package com.loyal.base.ui.activity;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;
import com.loyal.base.impl.CommandViewClickListener;
import com.loyal.base.impl.IUiCommandImpl;
import com.loyal.base.impl.IntentFrame;
import com.loyal.base.impl.StatusImpl;
import com.loyal.base.util.ConnectUtil;
import com.loyal.base.util.IntentBuilder;
import com.loyal.base.util.ObjectUtil;
import com.loyal.base.util.StateBarUtil;
import com.loyal.base.util.TimeUtil;
import com.loyal.base.widget.CommandDialog;

/**
 * this Activity just use to MvvM（DataBindingUtil.setContentView(Activity activity, int layoutId)）
 * <p>
 * others Activity do not extends this.
 *
 * @see ABasicActivity
 * </p>
 * @since 2018年3月1日11:44:19
 */
public abstract class ABasicBindActivity extends AppCompatActivity implements IntentFrame.ActFrame, IUiCommandImpl, StatusImpl {
    protected abstract
    @LayoutRes
    int actLayoutRes();

    public abstract void afterOnCreate();

    public abstract void setViewByLayoutRes();

    public abstract void bindViews();

    protected IntentBuilder intentBuilder;
    private Toast toast;
    protected ImmersionBar mImmersionBar;
    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";
    private CommandDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewByLayoutRes();
        bindViews();
        initImmersiveBar();
        statusBar(StatusBarImpl.ImmerBar);
        hasIntentParams(false);
        afterOnCreate();
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public boolean isImmersiveBar() {
        return true;
    }

    public void initImmersiveBar() {
        if (isImmersiveBar()) {
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.init();
        }
    }

    public void statusBar(@StatusBarImpl.source int barStatus) {
        switch (barStatus) {
            case StatusBarImpl.StateBar:
                StateBarUtil.setTranslucentStatus(this, isFullScreen());//沉浸式状态栏
                break;
            case StatusBarImpl.ImmerBar:
                if (OSUtils.isEMUI3_1()) {
                    //第一种
                    getContentResolver().registerContentObserver(Settings.System.getUriFor(NAVIGATIONBAR_IS_MIN), true, mNavigationStatusObserver);
                    //第二种,禁止对导航栏的设置
                    //mImmersionBar.navigationBarEnable(false).init();
                }
                break;
            case StatusBarImpl.NONE:
            default:
                break;
        }
    }

    public void hasIntentParams(boolean hasParam) {
        intentBuilder = null;
        if (hasParam)
            intentBuilder = new IntentBuilder(this, getIntent());
        else intentBuilder = new IntentBuilder(this);
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
        showCompatDialog(sequence, finish);
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

    private void initCompatDialog() {
        dialogBuilder = new CommandDialog.Builder(this);
        dialogBuilder.setOutsideCancel(false);
    }

    private void showCompatDialog(CharSequence content, final boolean isFinish) {
        if (null != dialogBuilder && dialogBuilder.isShowing())
            dialogBuilder.dismiss();
        initCompatDialog();
        dialogBuilder.setOutsideCancel(!isFinish);
        dialogBuilder.setContent(content);
        dialogBuilder.showWhichBtn(isFinish ? TypeImpl.NEXT : TypeImpl.CANCEL).setBtnText("确 定");
        dialogBuilder.setClickListener(new CommandViewClickListener() {
            @Override
            public void onViewClick(CommandDialog dialog, View view, Object tag) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                if (isFinish) {
                    finish();
                }
            }
        });
        dialogBuilder.show();
    }

    public void dismissCompatDialog() {
        if (null != dialogBuilder && dialogBuilder.isShowing())
            dialogBuilder.dismiss();
    }

    @Override
    public void finish() {
        dismissCompatDialog();
        super.finish();
    }

    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                    NAVIGATIONBAR_IS_MIN, 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
                if (null != mImmersionBar)
                    mImmersionBar.transparentNavigationBar().init();
            } else {
                //导航键显示了
                if (null != mImmersionBar)
                    mImmersionBar.navigationBarColor(android.R.color.black) //隐藏前导航栏的颜色
                            .fullScreen(false)
                            .init();
            }
        }
    };

    @Override
    protected void onPause() {
        if (null != toast)
            toast.cancel();
        dismissCompatDialog();
        super.onPause();
    }
}

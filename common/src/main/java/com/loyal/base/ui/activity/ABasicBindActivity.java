package com.loyal.base.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;
import com.loyal.base.impl.CommandViewClickListener;
import com.loyal.base.impl.IUICommandImpl;
import com.loyal.base.impl.StatusImpl;
import com.loyal.base.impl.ToolBarBackListener;
import com.loyal.base.widget.CommandDialog;
import com.loyal.kit.ConnectUtil;
import com.loyal.kit.IntentBuilder;
import com.loyal.kit.ObjectUtil;
import com.loyal.kit.StateBarUtil;
import com.loyal.kit.TimeUtil;
import com.loyal.kit.ToastUtil;
import com.loyal.kit.impl.IntentFrame;

/**
 * this Activity just use to MvvM（DataBindingUtil.setContentView(Activity activity, int layoutId)）
 * <p>
 * others Activity do not extends this.
 *
 * @see ABasicActivity
 * </p>
 * @since 2018年3月1日11:44:19
 */
public abstract class ABasicBindActivity extends AppCompatActivity implements IntentFrame.ActFrame, IUICommandImpl, StatusImpl {
    protected abstract
    @LayoutRes
    int actLayoutRes();

    public abstract void afterOnCreate();

    public abstract void setViewByLayoutRes();

    public abstract void bindViews();

    protected IntentBuilder intentBuilder;
    private ToastUtil toastUtil;
    protected ImmersionBar mImmersionBar;
    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";
    public CommandDialog.Builder dialogBuilder;
    private ActionBar actionBar;
    private ToolBarBackListener backListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewByLayoutRes();
        toastUtil = new ToastUtil(this);
        bindViews();
        statusBar(StatusBarImpl.StateBar);
        hasIntentParams(false);
        afterOnCreate();
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void statusBar(@StatusBarImpl.source int barStatus) {
        switch (barStatus) {
            case StatusBarImpl.StateBar:
                StateBarUtil.setTranslucentStatus(this, isFullScreen());//沉浸式状态栏
                break;
            case StatusBarImpl.ImmerBar:
                initImmersiveBar();
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

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        setActionBack(true);
    }

    public void hideActionBar(boolean hide) {
        if (hide) {
            if (null != actionBar)
                actionBar.hide();
        }
    }

    public void setActionBack(boolean showHomeAsUp) {
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
            actionBar.setHomeButtonEnabled(showHomeAsUp);
        }
    }

    public void setToolbarBackListener(ToolBarBackListener listener) {
        backListener = listener;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (null != backListener)
                    backListener.onBack();
                else finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initImmersiveBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    public void hasIntentParams(boolean hasParam) {
        intentBuilder = null;
        if (hasParam)
            intentBuilder = new IntentBuilder(this, getIntent());
        else intentBuilder = new IntentBuilder(this);
    }

    @Override
    public void startActivityByAct(@Nullable String className) {
        intentBuilder.startActivityByAct(className);
    }

    @Override
    public void startActivityForResultByAct(@Nullable String className, int reqCode) {
        intentBuilder.startActivityForResultByAct(className, reqCode);
    }

    @Override
    public void startServiceByAct(@Nullable String className) {
        intentBuilder.startServiceByAct(className);
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
    public void hideKeyBoard(@NonNull View view) {
        toastUtil.hideKeyBoard(view);
    }

    @Override
    public void showErrorDialog(@NonNull CharSequence sequence, Throwable e, boolean finish) {
        String error = null == e ? "" : ConnectUtil.getError(e);
        showDialog(replaceNull(sequence) + (TextUtils.isEmpty(error) ? "" : "\n" + error), finish);
    }

    private void initToast(final CharSequence sequence) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toastUtil.show(sequence);
            }
        });
    }

    public void initCompatDialog() {
        initCompatDialog(this);
    }

    public void initCompatDialog(Context context) {
        dialogBuilder = new CommandDialog.Builder(context);
        dialogBuilder.setOutsideCancel(false);
    }

    private void showCompatDialog(CharSequence content, final boolean isFinish) {
        dismissCompatDialog();
        initCompatDialog();
        dialogBuilder.setOutsideCancel(!isFinish);
        dialogBuilder.setContent(content);
        dialogBuilder.showSingleBtn(isFinish ? TypeImpl.NEXT : TypeImpl.CANCEL, "确 定");
        dialogBuilder.setClickListener(new CommandViewClickListener() {
            @Override
            public void onViewClick(DialogInterface dialog, View view, Object tag) {
                if (dialog != null)
                    dialog.dismiss();
                if (isFinish) {
                    finish();
                }
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialogBuilder.show();
            }
        });
    }

    public void dismissCompatDialog() {
        if (null != dialogBuilder && dialogBuilder.isShowing()) {
            dialogBuilder.dismiss();
            dialogBuilder = null;
        }
    }

    /**
     * 权限申请
     */
    public void showPermissionNextDialog(CharSequence content, CommandViewClickListener nextListener, boolean outsideCancel) {
        showPermissionDialog(content, TypeImpl.NEXT, new String[]{"确 定"}, nextListener, outsideCancel);
    }

    public void showPermissionDialog(CharSequence content, @TypeImpl.source int btnType, String[] btnTexts,
                                     CommandViewClickListener clickListener, boolean outsideCancel) {
        dismissCompatDialog();
        initCompatDialog();
        dialogBuilder.setOutsideCancel(outsideCancel);
        dialogBuilder.setContent(content);
        dialogBuilder.showButton(btnType, btnTexts).setClickListener(clickListener);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialogBuilder.show();
            }
        });
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
        if (null != toastUtil)
            toastUtil.cancel();
        dismissCompatDialog();
        super.onPause();
    }
}

package com.loyal.base.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.loyal.base.impl.CommandViewClickListener;
import com.loyal.base.impl.IFrag2ActListener;
import com.loyal.base.impl.IUICommandImpl;
import com.loyal.kit.IntentBuilder;
import com.loyal.base.ui.activity.ABasicFragActivity;
import com.loyal.base.widget.CommandDialog;
import com.loyal.kit.ConnectUtil;
import com.loyal.kit.ObjectUtil;
import com.loyal.kit.TimeUtil;
import com.loyal.kit.ToastUtil;
import com.loyal.kit.impl.IntentFrame;

/**
 * {@link ABasicFragActivity#onFrag2Act(String, Object...)}
 */
public abstract class ABasicFragment extends Fragment implements IntentFrame.FragFrame, IUICommandImpl {
    private IFrag2ActListener mListener;

    public abstract
    @LayoutRes
    int fragLayoutRes();

    public abstract void afterOnCreate();

    public abstract void bindViews(View view);

    public abstract void unbind();

    protected IntentBuilder intentBuilder;
    private ToastUtil toastUtil;
    public CommandDialog.Builder dialogBuilder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFrag2ActListener) {
            mListener = (IFrag2ActListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IFrag2ActListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(fragLayoutRes(), container, false);
        toastUtil = new ToastUtil(view.getContext());
        bindViews(view);
        hasIntentParams(false);
        afterOnCreate();
        return view;
    }

    public void onFrag2Act(String tag, Object... objectParam) {
        if (null != mListener) {
            mListener.onFrag2Act(tag, objectParam);
        }
    }

    public void hasIntentParams(boolean hasParam) {
        intentBuilder = null;
        if (hasParam)
            intentBuilder = new IntentBuilder(this, getIntent());
        else intentBuilder = new IntentBuilder(this);
    }

    @Override
    public void startActivityByFrag(@Nullable String className) {
        intentBuilder.startActivityByFrag(className);
    }

    @Override
    public void startActivityForResultByFrag(@Nullable String className, int reqCode) {
        intentBuilder.startActivityForResultByFrag(className, reqCode);
    }

    @Override
    public void startServiceByFrag(@Nullable String className) {
        intentBuilder.startServiceByFrag(className);
    }

    @Override
    public void startActivityByFrag(@Nullable Class<?> tClass) {
        intentBuilder.startActivityByFrag(tClass);
    }

    @Override
    public void startActivityForResultByFrag(@Nullable Class<?> tClass, @IntRange(from = 2) int reqCode) {
        intentBuilder.startActivityForResultByFrag(tClass, reqCode);
    }

    @Override
    public void startServiceByFrag(@Nullable Class<?> tClass) {
        intentBuilder.startServiceByFrag(tClass);
    }

    @Override
    public void showToast(@NonNull final CharSequence sequence) {
        FragmentActivity activity = getActivity();
        if (activity != null) activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != toastUtil)
                    toastUtil.show(sequence);
            }
        });

    }

    @Override
    public void showErrorToast(int resId, Throwable e) {
        showErrorToast(getString(resId), e);
    }

    @Override
    public void showErrorToast(@NonNull CharSequence sequence, Throwable e) {
        String error = ConnectUtil.getError(e);
        String toastStr = replaceNull(sequence) + (TextUtils.isEmpty(error) ? "" : "\n" + error);
        showToast(toastStr);
    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showDialog(@NonNull CharSequence sequence) {
        showDialog(sequence, false);
    }

    @Override
    public void showDialog(@NonNull CharSequence sequence, boolean finish) {
        Context context = getContext();
        if (null != context)
            showCompatDialog(replaceNull(sequence), finish);
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
    public void showErrorDialog(@NonNull CharSequence sequence, Throwable e, boolean finish) {
        String error = ConnectUtil.getError(e);
        String str = replaceNull(sequence) + (TextUtils.isEmpty(error) ? "" : "\n" + error);
        showDialog(replaceNull(str), finish);
    }

    @Override
    public void showErrorDialog(@NonNull CharSequence sequence, Throwable e) {
        showErrorDialog(sequence, e, false);
    }

    @Override
    public void showErrorDialog(@NonNull CharSequence sequence, boolean finish) {
        showDialog(replaceNull(sequence), finish);
    }

    @Override
    public void hideKeyBoard(@NonNull View view) {
        if (null != toastUtil)
            toastUtil.hideKeyBoard(view);
    }

    public Intent getIntent() {
        FragmentActivity activity = getActivity();
        if (null != activity)
            return activity.getIntent();
        return null;
    }

    public void finish() {
        FragmentActivity activity = getActivity();
        if (null != activity)
            activity.finish();
    }

    public void initCompatDialog() {
        initCompatDialog(getContext());
    }

    public void initCompatDialog(Context context) {
        if (null == context)
            return;
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
        FragmentActivity activity = getActivity();
        if (null != activity)
            activity.runOnUiThread(new Runnable() {
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

    @Override
    public void onPause() {
        dismissCompatDialog();
        if (null != toastUtil)
            toastUtil.cancel();
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind();
    }
}

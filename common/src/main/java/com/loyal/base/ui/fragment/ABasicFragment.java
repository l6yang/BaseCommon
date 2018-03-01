package com.loyal.base.ui.fragment;

import android.content.Context;
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

import com.loyal.base.impl.IFrag2ActListener;
import com.loyal.base.impl.IUIInterface;
import com.loyal.base.impl.IntentFrame;
import com.loyal.base.ui.activity.ABasicFragActivity;
import com.loyal.base.util.ConnectUtil;
import com.loyal.base.util.IntentUtil;
import com.loyal.base.util.ObjectUtil;
import com.loyal.base.util.TimeUtil;
import com.loyal.base.util.ToastUtil;

/**
 * {@link ABasicFragActivity#onFrag2Act(String, Object...)}
 */
public abstract class ABasicFragment extends Fragment implements IntentFrame.FragmentFrame, IUIInterface {
    private IFrag2ActListener mListener;

    public abstract
    @LayoutRes
    int fragLayoutRes();

    public abstract void afterOnCreate();

    public abstract void bindViews(View view);

    public abstract void unbind();

    protected IntentUtil builder;

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
        bindViews(view);
        afterOnCreate();
        hasIntentParams(false);
        return view;
    }

    public void onFrag2Act(String tag, Object... objectParam) {
        if (null != mListener) {
            mListener.onFrag2Act(tag, objectParam);
        }
    }

    public void hasIntentParams(boolean hasParam) {
        builder = null;
        if (hasParam)
            builder = new IntentUtil(this, getIntent());
        else builder = new IntentUtil(this);
    }

    @Override
    public void startActivityByFrag(@Nullable Class<?> tClass) {
        builder.startActivityByFrag(tClass);
    }

    @Override
    public void startActivityForResultByFrag(@Nullable Class<?> tClass, @IntRange(from = 2) int reqCode) {
        builder.startActivityForResultByFrag(tClass, reqCode);
    }

    @Override
    public void startServiceByFrag(@Nullable Class<?> tClass) {
        builder.startServiceByFrag(tClass);
    }

    @Override
    public void showToast(@NonNull CharSequence sequence) {
        Context context = getActivity();
        if (null != context)
            ToastUtil.showToast(context, sequence);
    }

    @Override
    public void showErrorToast(int resId, Throwable e) {

    }

    @Override
    public void showErrorToast(@NonNull CharSequence sequence, Throwable e) {

    }

    @Override
    public void showToast(int resId) {
        onFrag2Act("showToast", resId);
        Context context = getActivity();
        if (null != context)
            ToastUtil.showToast(context, context.getString(resId));
    }

    @Override
    public void showDialog(@NonNull CharSequence sequence) {
        showDialog(sequence, false);
    }

    @Override
    public void showDialog(@NonNull CharSequence sequence, boolean finish) {
        Context context = getActivity();
        if (null != context)
            ToastUtil.showDialog(context, replaceNull(sequence), finish);
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

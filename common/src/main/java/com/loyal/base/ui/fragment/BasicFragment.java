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

import com.loyal.base.impl.Contacts;
import com.loyal.base.impl.Frag2ActListener;
import com.loyal.base.impl.IntentFrame;
import com.loyal.base.impl.UIInterface;
import com.loyal.base.ui.activity.BasicFragActivity;
import com.loyal.base.util.ConnectUtil;
import com.loyal.base.util.IntentUtil;
import com.loyal.base.util.ObjectUtil;
import com.loyal.base.util.TimeUtil;
import com.loyal.base.util.ToastUtil;

/**
 * {@link BasicFragActivity#onFrag2Act(String)}
 */
public abstract class BasicFragment extends Fragment implements IntentFrame.FragmentFrame, UIInterface, Contacts {
    private Frag2ActListener mListener;

    public abstract
    @LayoutRes
    int fragLayoutRes();

    public abstract void afterOnCreate();

    public abstract void bindViews(View view);

    protected IntentUtil builder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Frag2ActListener) {
            mListener = (Frag2ActListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Frag2ActListener");
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

    public void onFrag2Act(String uri) {
        if (null != mListener) {
            mListener.onFrag2Act(uri);
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
    public void showToast(@NonNull String text) {
        Context context = getActivity();
        if (null != context)
            ToastUtil.showToast(context, text);
    }

    @Override
    public void showErrorToast(int resId, Throwable e) {

    }

    @Override
    public void showErrorToast(@NonNull String text, Throwable e) {

    }

    @Override
    public void showToast(int resId) {
        Context context = getActivity();
        if (null != context)
            ToastUtil.showToast(context, context.getString(resId));
    }

    @Override
    public void showDialog(@NonNull String text) {
        showDialog(text, false);
    }

    @Override
    public void showDialog(@NonNull String text, boolean finish) {
        Context context = getActivity();
        if (null != context)
            ToastUtil.showDialog(context, replaceNull(text), finish);
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
    public void showErrorDialog(@NonNull String text, Throwable e, boolean finish) {
        String error = ConnectUtil.getError(e);
        String str = replaceNull(text) + (TextUtils.isEmpty(error) ? "" : "\n" + error);
        showDialog(replaceNull(str), finish);
    }

    @Override
    public void showErrorDialog(@NonNull String text, Throwable e) {
        showErrorDialog(text, e, false);
    }

    @Override
    public void showErrorDialog(@NonNull String text, boolean finish) {
        showDialog(replaceNull(text), finish);
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
}

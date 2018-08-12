package com.loyal.base.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.loyal.base.impl.CommandViewClickListener;
import com.loyal.base.impl.IBaseContacts;
import com.loyal.base.widget.CommandDialog;

public class ToastUtil implements IBaseContacts {
    private static Toast toast = null;

    public static void showToast(@NonNull Context context, @NonNull CharSequence sequence) {
        if (toast == null)
            toast = Toast.makeText(context, sequence, Toast.LENGTH_SHORT);
        else {
            toast.setText(sequence);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void showToast(@NonNull Context context, @StringRes int resId) {
        showToast(context, context.getString(resId));
    }

    public static void showDialog(final Activity context, final CharSequence content, final boolean isFinish) {
        CommandDialog.Builder builder = new CommandDialog.Builder(context);
        builder.setContent(content).setOutsideCancel(false).setOutsideCancel(false);
        builder.setBottomBtnType(isFinish ? TypeImpl.RIGHT : TypeImpl.LEFT).setBtnText(new String[]{"确定"}).setClickListener(new CommandViewClickListener() {
            @Override
            public void onViewClick(CommandDialog dialog, View view, Object tag) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                if (isFinish) {
                    if (context != null)
                        context.finish();
                }
            }
        });
        builder.create().show();
    }

    public static void hideInput(@NonNull Context context, @NonNull IBinder token) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null)
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

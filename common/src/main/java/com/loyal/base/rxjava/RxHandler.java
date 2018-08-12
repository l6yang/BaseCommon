package com.loyal.base.rxjava;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import com.loyal.base.rxjava.impl.ProgressCancelListener;

public class RxHandler extends Handler implements DialogInterface.OnCancelListener {
    private ProgressDialog progressDialog;
    private Context context;
    private ProgressCancelListener listener;

    RxHandler(Context context, ProgressCancelListener cancelListener) {
        this.context = context;
        initDialog();
        this.listener = cancelListener;
    }

    private void initDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setOnCancelListener(this);
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }

    private void setMessage(CharSequence message) {
        if (progressDialog != null)
            progressDialog.setMessage(message);
    }

    private void setCancelable(boolean flag) {
        if (progressDialog != null)
            progressDialog.setCancelable(flag);
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private void showDialog() {
        if (null != progressDialog && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if (listener != null)
            listener.onCancelProgress();
    }

    public static final class Builder {
        private Context mContext;
        private ProgressCancelListener listener;
        private RxHandler handler;

        public Builder(Context context, ProgressCancelListener cancelListener) {
            this.mContext = context;
            this.listener = cancelListener;
            handler = new RxHandler(mContext, listener);
        }

        public ProgressDialog getProgressDialog() {
            if (null != handler)
                return handler.progressDialog;
            else return null;
        }

        public Builder setMessage(CharSequence sequence) {
            handler.setMessage(sequence);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            handler.setCancelable(cancelable);
            return this;
        }

        public void show() {
            if (null != handler)
                handler.showDialog();
        }

        public void dismiss() {
            if (null != handler)
                handler.dismissDialog();
        }
    }
}

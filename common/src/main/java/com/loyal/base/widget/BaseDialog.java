package com.loyal.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loyal.base.R;
import com.loyal.base.impl.Contacts;

public class BaseDialog extends Dialog implements Contacts {

    private BaseDialog(@NonNull Context context) {
        this(context, R.style.DialogTheme);
    }

    private BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Object objTag;

    public Object getTag() {
        return objTag;
    }

    private void setTag(Object objTag) {
        this.objTag = objTag;
    }

    public static class Builder implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (null != clickListener)
                clickListener.onClick(baseDialog, v, objectTag);
        }

        private Context mContext;
        private CharSequence sequenceTitle = "温馨提示", sequenceContent, sequenceOk, sequenceCancel;
        private DialogClickListener clickListener;
        private BaseDialog baseDialog;
        private TYPE type = TYPE.NONE;
        private TextView textTitle, textContent;
        private View layoutOk, layoutCancel;
        private Button btnOk, btnCancel;
        private boolean cancelable = false, outsideCancelable = false;
        private Object objectTag;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Context getContext() {
            return mContext;
        }

        public Builder setTitle(@StringRes int strId) {
            setTitle(mContext.getString(strId));
            return this;
        }

        public Builder setTitle(CharSequence sequence) {
            this.sequenceTitle = sequence;
            return this;
        }

        public Builder setTag(Object objectTag) {
            this.objectTag = objectTag;
            return this;
        }

        public Builder setContent(@StringRes int strId) {
            setTitle(mContext.getString(strId));
            return this;
        }

        public Builder setContent(CharSequence sequence) {
            this.sequenceContent = sequence;
            return this;
        }

        /**
         * use it must after {@link #show()} and return not null
         */
        public TextView getContentView() {
            return textContent;
        }

        /**
         * use it must after {@link #show()} and return not null
         */
        public TextView getTitleView() {
            return textTitle;
        }

        /**
         * use it must after {@link #show()} and return not null
         */
        public Button getCancelButton() {
            return btnCancel;
        }

        /**
         * use it must after {@link #show()} and return not null
         */
        public Button getOkButton() {
            return btnOk;
        }

        public Builder setBtnText(@Size(min = 1, max = 2) @NonNull String[] sequence) {
            switch (type) {
                case LEFT:
                    setLeftBtnText(sequence[0]);
                    break;
                case RIGHT:
                    setRightBtnText(sequence[0]);
                    break;
                case NONE:
                    setLeftBtnText(sequence[0]);
                    setRightBtnText(sequence.length >= 2 ? sequence[1] : "");
                    break;
            }
            return this;
        }

        public Builder setLeftBtnText(CharSequence sequence) {
            this.sequenceCancel = sequence;
            return this;
        }

        public Builder setRightBtnText(CharSequence sequence) {
            this.sequenceOk = sequence;
            return this;
        }

        public Builder setRightBtnText(@StringRes int strId) {
            setTitle(mContext.getString(strId));
            return this;
        }

        public Builder setLeftBtnText(@StringRes int strId) {
            setTitle(mContext.getString(strId));
            return this;
        }

        public Builder setClickListener(DialogClickListener listener) {
            this.clickListener = listener;
            return this;
        }

        public DialogClickListener getListener() {
            return clickListener;
        }

        public Builder setBottomBtnType(TYPE type) {
            this.type = type;
            return this;
        }

        public TYPE getType() {
            return type;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setOutsideCancel(boolean outsideCancelable) {
            this.outsideCancelable = outsideCancelable;
            return this;
        }

        public BaseDialog create() {
            baseDialog = new BaseDialog(mContext);
            baseDialog.setContentView(R.layout.dialog_layout);
            baseDialog.setCancelable(cancelable);
            baseDialog.setCanceledOnTouchOutside(outsideCancelable);
            baseDialog.setTag(objectTag);
            initDialogView();
            switch (type) {
                case NONE:
                    if (null != layoutOk)
                        layoutOk.setVisibility(View.VISIBLE);
                    if (null != layoutCancel)
                        layoutCancel.setVisibility(View.VISIBLE);
                    break;
                case LEFT:
                    if (null != layoutOk)
                        layoutOk.setVisibility(View.GONE);
                    if (null != layoutCancel)
                        layoutCancel.setVisibility(View.VISIBLE);
                    break;
                case RIGHT:
                    if (null != layoutOk)
                        layoutOk.setVisibility(View.VISIBLE);
                    if (null != layoutCancel)
                        layoutCancel.setVisibility(View.GONE);
                    break;
            }
            return baseDialog;
        }

        public void show() {
            BaseDialog dialog = create();
            dialog.show();
        }

        private void initDialogView() {
            textTitle = baseDialog.findViewById(R.id.text_title);
            textContent = baseDialog.findViewById(R.id.text_content);
            layoutOk = baseDialog.findViewById(R.id.dialog_layout_ok);
            layoutCancel = baseDialog.findViewById(R.id.dialog_layout_cancel);
            btnOk = baseDialog.findViewById(R.id.dialog_btn_ok);
            btnCancel = baseDialog.findViewById(R.id.dialog_btn_cancel);
            textTitle.setText(replaceNull(sequenceTitle));
            textContent.setText(replaceNull(sequenceContent));
            btnOk.setText(sequenceOk);
            btnCancel.setText(replaceNull(sequenceCancel));
            btnOk.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
        }

        private String replaceNull(CharSequence sequence) {
            return Str.replaceNull(sequence);
        }
    }

    public interface DialogClickListener {
        void onClick(BaseDialog dialog, View view, Object tag);
    }
}

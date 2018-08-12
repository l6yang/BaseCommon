package com.loyal.base.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loyal.base.R;
import com.loyal.base.impl.CommandViewClickListener;
import com.loyal.base.impl.IBaseContacts;

public class CommandDialog extends AppCompatDialog implements IBaseContacts {

    private CommandDialog(@NonNull Context context) {
        this(context, R.style.dialogTheme);
    }

    private CommandDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
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
                clickListener.onViewClick(baseDialog, v, objectTag);
        }

        private Context mContext;
        private CharSequence sequenceTitle = "温馨提示", sequenceContent, sequenceOk, sequenceCancel;
        private CommandViewClickListener clickListener;
        private CommandDialog baseDialog;
        private String statusType = TypeImpl.NONE;
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
            switch (statusType) {
                case TypeImpl.LEFT:
                    setLeftBtnText(sequence[0]);
                    break;
                case TypeImpl.RIGHT:
                    setRightBtnText(sequence[0]);
                    break;
                case TypeImpl.NONE:
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

        public Builder setClickListener(CommandViewClickListener listener) {
            this.clickListener = listener;
            return this;
        }

        public CommandViewClickListener getListener() {
            return clickListener;
        }

        public Builder setBottomBtnType(@TypeImpl.source String type) {
            this.statusType = type;
            return this;
        }

        public String getType() {
            return statusType;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setOutsideCancel(boolean outsideCancelable) {
            this.outsideCancelable = outsideCancelable;
            return this;
        }

        public CommandDialog create() {
            baseDialog = new CommandDialog(mContext);
            baseDialog.setContentView(R.layout.dialog_command);
            baseDialog.setCancelable(cancelable);
            baseDialog.setCanceledOnTouchOutside(outsideCancelable);
            baseDialog.setTag(objectTag);
            initDialogView();
            switch (statusType) {
                case TypeImpl.NONE:
                    if (null != layoutOk)
                        layoutOk.setVisibility(View.VISIBLE);
                    if (null != layoutCancel)
                        layoutCancel.setVisibility(View.VISIBLE);
                    break;
                case TypeImpl.LEFT:
                    if (null != layoutOk)
                        layoutOk.setVisibility(View.GONE);
                    if (null != layoutCancel)
                        layoutCancel.setVisibility(View.VISIBLE);
                    break;
                case TypeImpl.RIGHT:
                    if (null != layoutOk)
                        layoutOk.setVisibility(View.VISIBLE);
                    if (null != layoutCancel)
                        layoutCancel.setVisibility(View.GONE);
                    break;
            }
            return baseDialog;
        }

        public void show() {
            create();
            if (null != baseDialog)
                baseDialog.show();
        }

        private void initDialogView() {
            textTitle = baseDialog.findViewById(R.id.text_cmd_title);
            textContent = baseDialog.findViewById(R.id.text_cmd_content);
            layoutOk = baseDialog.findViewById(R.id.layout_cmd_next);
            layoutCancel = baseDialog.findViewById(R.id.layout_cmd_cancel);
            btnOk = baseDialog.findViewById(R.id.btn_cmd_next);
            btnCancel = baseDialog.findViewById(R.id.btn_cmd_cancel);
            textTitle.setText(replaceNull(sequenceTitle));
            textContent.setText(replaceNull(sequenceContent));
            btnOk.setText(sequenceOk);
            btnCancel.setText(replaceNull(sequenceCancel));
            btnOk.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
        }

        private String replaceNull(CharSequence sequence) {
            return BaseStr.replaceNull(sequence);
        }

        public boolean isShowing() {
            return baseDialog != null && baseDialog.isShowing();
        }

        public void dismiss() {
            if (null != baseDialog)
                baseDialog.dismiss();
        }
    }
}

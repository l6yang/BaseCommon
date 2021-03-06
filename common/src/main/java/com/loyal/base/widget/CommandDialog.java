package com.loyal.base.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.loyal.base.R;
import com.loyal.base.impl.CommandViewClickListener;
import com.loyal.base.impl.IBaseContacts;

public class CommandDialog extends AppCompatDialog implements IBaseContacts {

    private CommandDialog(@NonNull Context context) {
        this(context, R.style.DialogTheme);
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
        private CharSequence sequenceTitle = "温馨提示", sequenceContent, sequenceNext = "确 定", sequenceCancel = "取 消";
        private CommandViewClickListener clickListener;
        private CommandDialog baseDialog;
        private int statusType = TypeImpl.DOUBLE;
        private TextView textTitle, textContent;
        private View layoutNext, layoutCancel;
        private TextView btnNext, btnCancel;
        private boolean cancelable = false, outsideCancelable = false, showInAsServices = false;
        private Object objectTag;
        private OnCancelListener onCancelListener;
        private OnDismissListener onDismissListener;

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
            return setContent(mContext.getString(strId));
        }

        public Builder setContent(CharSequence sequence) {
            this.sequenceContent = sequence;
            return this;
        }

        /**
         * @param asInService 是否运行于后台，如果在后台Service中使用弹出对话框时，需要加此权限
         *                    uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"
         */
        public Builder showInServices(boolean asInService) {
            this.showInAsServices = asInService;
            return this;
        }

        /**
         * use it must after {@link #show()} or return null
         */
        public TextView contentView() {
            return textContent;
        }

        /**
         * use it must after {@link #show()} or return null
         */
        public TextView titleView() {
            return textTitle;
        }

        /**
         * use it must after {@link #show()} or return null
         */
        public View cancelButton() {
            return btnCancel;
        }

        /**
         * use it must after {@link #show()} or return null
         */
        public View nextButton() {
            return btnNext;
        }

        /**
         * use it before with {@link #showWhichBtn(int)}
         *
         * @param sequence nextText or cancelText
         */
        public Builder showSingleBtn(@NonNull String sequence) {
            switch (statusType) {
                case TypeImpl.CANCEL:
                    setCancelBtnText(sequence);
                    break;
                case TypeImpl.NEXT:
                    setNextBtnText(sequence);
                    break;
                case TypeImpl.DOUBLE:
                    break;
            }
            return this;
        }

        /**
         * show single Button
         *
         * @param whichBtn {@link com.loyal.base.impl.IBaseContacts.TypeImpl#CANCEL}
         *                 or
         *                 {@link com.loyal.base.impl.IBaseContacts.TypeImpl#NEXT}
         * @param sequence nextText or cancelText
         */
        public Builder showSingleBtn(@TypeImpl.source int whichBtn, @NonNull String sequence) {
            switch (whichBtn) {
                case TypeImpl.CANCEL:
                    setCancelBtnText(sequence);
                    showWhichBtn(whichBtn);
                    break;
                case TypeImpl.NEXT:
                    setNextBtnText(sequence);
                    showWhichBtn(whichBtn);
                    break;
                case TypeImpl.DOUBLE:
                    break;
            }
            return this;
        }

        public Builder showDoubleBtn(@Size(min = 1, max = 2) String[] btnText) {
            int length = null == btnText ? 0 : btnText.length;
            switch (length) {
                case 1:
                    setCancelBtnText(btnText[0]);
                    break;
                case 2:
                    setCancelBtnText(btnText[0]);
                    setNextBtnText(btnText[1]);
                    break;
            }
            return showWhichBtn(TypeImpl.DOUBLE);
        }

        public Builder showButton(@TypeImpl.source int whichBtn, @Size(min = 1, max = 2) String[] btnText) {
            switch (whichBtn) {
                case TypeImpl.CANCEL:
                    setCancelBtnText(btnText[0]);
                    break;
                case TypeImpl.NEXT:
                    setNextBtnText(btnText[0]);
                    break;
                case TypeImpl.DOUBLE:
                    setCancelBtnText(btnText[0]);
                    setNextBtnText(btnText[1]);
                    break;
            }
            return showWhichBtn(whichBtn);
        }

        public Builder showWhichBtn(@TypeImpl.source int whichBtn) {
            this.statusType = whichBtn;
            return this;
        }

        public void setOnCancelListener(@Nullable OnCancelListener listener) {
            this.onCancelListener = listener;
        }

        public void setOnDismissListener(@Nullable OnDismissListener listener) {
            this.onDismissListener = listener;
        }

        public Builder setCancelBtnText(@StringRes int strId) {
            return setCancelBtnText(mContext.getString(strId));
        }

        public Builder setCancelBtnText(CharSequence sequence) {
            sequenceCancel = sequence;
            return showWhichBtn(TypeImpl.CANCEL);
        }

        public Builder setNextBtnText(@StringRes int strId) {
            return setNextBtnText(mContext.getString(strId));
        }

        public Builder setNextBtnText(CharSequence sequence) {
            sequenceNext = sequence;
            return showWhichBtn(TypeImpl.NEXT);
        }

        public void setClickListener(CommandViewClickListener listener) {
            this.clickListener = listener;
        }

        public CommandViewClickListener getListener() {
            return clickListener;
        }

        public int getType() {
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
            baseDialog.setOnCancelListener(onCancelListener);
            baseDialog.setOnDismissListener(onDismissListener);
            if (showInAsServices) {
                Window window = baseDialog.getWindow();
                if (null != window)
                    window.setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
            }
            initDialogView();
            switch (statusType) {
                case TypeImpl.DOUBLE:
                    if (null != layoutNext)
                        layoutNext.setVisibility(View.VISIBLE);
                    if (null != layoutCancel)
                        layoutCancel.setVisibility(View.VISIBLE);
                    break;
                case TypeImpl.CANCEL:
                    if (null != layoutNext)
                        layoutNext.setVisibility(View.GONE);
                    if (null != layoutCancel)
                        layoutCancel.setVisibility(View.VISIBLE);
                    break;
                case TypeImpl.NEXT:
                    if (null != layoutNext)
                        layoutNext.setVisibility(View.VISIBLE);
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
            layoutNext = baseDialog.findViewById(R.id.layout_cmd_next);
            layoutCancel = baseDialog.findViewById(R.id.layout_cmd_cancel);
            btnNext = baseDialog.findViewById(R.id.btn_cmd_next);
            btnCancel = baseDialog.findViewById(R.id.btn_cmd_cancel);
            textTitle.setText(replaceNull(sequenceTitle));
            textContent.setText(replaceNull(sequenceContent));
            btnNext.setText(sequenceNext);
            btnCancel.setText(replaceNull(sequenceCancel));
            btnNext.setOnClickListener(this);
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

        public void cancel() {
            if (null != baseDialog)
                baseDialog.cancel();
        }
    }
}

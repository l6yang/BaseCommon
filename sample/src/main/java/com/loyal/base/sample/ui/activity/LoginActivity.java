package com.loyal.base.sample.ui.activity;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loyal.base.rxjava.RxUtil;
import com.loyal.base.rxjava.impl.RxSubscriberListener;
import com.loyal.base.sample.R;
import com.loyal.base.sample.base.ABaseActivity;
import com.loyal.base.sample.rxjava.RxServerSubscriber;

import butterknife.BindView;

public class LoginActivity extends ABaseActivity implements RxSubscriberListener<String> {

    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void afterOnCreate() {

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        RxServerSubscriber<String> subscriber = new RxServerSubscriber<>(this, "192.168.0.106:8080");
        subscriber.setDialogMessage("登录中...").showProgressDialog(true).setSubscribeListener(this);
        RxUtil.rxExecuted(subscriber.login(email, password), subscriber);
    }

    @Override
    public void onResult(int what, Object tag, String result) {
        try {
            showDialog("登录结果\n" + result);
        } catch (Exception e) {
            onError(what, tag, e);
        }
    }

    @Override
    public void onError(int what, Object tag, Throwable e) {
        showErrorDialog("登录失败", e);
    }
}


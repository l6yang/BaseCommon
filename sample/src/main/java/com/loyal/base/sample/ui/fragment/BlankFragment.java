package com.loyal.base.sample.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.loyal.base.impl.OnSinglePermissionListener;
import com.loyal.base.sample.R;
import com.loyal.base.sample.ui.activity.Test2Activity;
import com.loyal.base.util.OutUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class BlankFragment extends ABaseFragment implements OnSinglePermissionListener {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    @BindView(R.id.message)
    TextView textView;
    @BindView(R.id.click)
    TextView click;

    public BlankFragment() {
    }

    public static BlankFragment newInstance(String param1) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public int fragLayoutRes() {
        return R.layout.fragment_blank;
    }

    @Override
    public void afterOnCreate() {
        textView.setText(mParam1);
        textView.setTag(mParam1);
    }

    @OnClick({R.id.message, R.id.click})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message:
                String tag = (String) v.getTag();
                if (TextUtils.equals("memory", tag)) {
                    singlePermission(PerMission.storagePermission, this,  PerMission.WRITE_EXTERNAL_STORAGE);
                } else if (TextUtils.equals("location", tag)) {
                    singlePermission(PerMission.locationPermission, this, PerMission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
                } else if (TextUtils.equals("phone", tag)) {
                    singlePermission(5, this, Manifest.permission.READ_PHONE_STATE);
                } else {
                    //使用这个的话TestFragActivity需要继承与BasicPermissionActivity
                    onFrag2Act("camera");
                }
                break;
            case R.id.click:
                startActivityByFrag(Test2Activity.class);
                break;
        }
    }

    @Override
    public void onSinglePermission(int reqCode, boolean successful) {
        switch (reqCode) {
            case 5:
                OutUtil.println(successful ? "存储权限获取成功" : "存储权限获取失败");
                break;
            case 6:
                OutUtil.println(successful ? "定位权限获取成功" : "定位权限获取失败");
                break;
            case 7:
                OutUtil.println(successful ? "读取手机权限获取成功" : "读取手机权限获取失败");
                break;
        }
    }
}

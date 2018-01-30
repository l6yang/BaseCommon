package com.loyal.base.sample.ui.activity;

import android.Manifest;
import android.view.View;
import android.widget.TextView;

import com.loyal.base.sample.FileUtil;
import com.loyal.base.sample.R;
import com.loyal.base.ui.activity.ABasicPerMissionActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends ABaseActivity implements ABasicPerMissionActivity.onItemPermissionListener {
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void afterOnCreate() {
        textView.setText("Just from MainActivity");
        requestPermission(100, this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onItemPermissionResult(int reqCode, boolean successful) {
        if (successful) {
            FileUtil.createFileSys();
        }
    }

    @OnClick({R.id.textView})
    public void onClick(View view) {
        FileUtil.deleteFileWithDir(new File(FileUtil.imagePath));
        FileUtil.createDirs(new File(FileUtil.imagePath));
    }
}

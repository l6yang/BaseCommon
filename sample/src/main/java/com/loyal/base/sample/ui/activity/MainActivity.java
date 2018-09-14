package com.loyal.base.sample.ui.activity;

import android.Manifest;
import android.view.View;
import android.widget.TextView;

import com.loyal.base.impl.OnSinglePermissionListener;
import com.loyal.base.sample.FileUtil;
import com.loyal.base.sample.R;
import com.loyal.base.util.TimeUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends ABaseActivity implements OnSinglePermissionListener, View.OnClickListener {
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void afterOnCreate() {
        textView.setText("Just from MainActivity");
        textView.setOnClickListener(this);
        singlePermission(PerMission.storagePermission, this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onSinglePermission(int reqCode, boolean successful) {
        if (successful) {
            FileUtil.createFileSys();
        }
    }

    @OnClick({R.id.textView})
    public void onClick(View view) {
        FileUtil.deleteFileWithDir(new File(FileUtil.imagePath));
        FileUtil.createDirs(new File(FileUtil.imagePath));
        /*CommandDialog.Builder builder = new CommandDialog.Builder(this);
        builder.setContent("后悔不及").setContent(Html.fromHtml("<font color='blue'>错过</font>"));
        builder.setCancelBtnText(Html.fromHtml("<font color='red'>你好吗？</font>"));
        builder.setClickListener(new CommandViewClickListener() {
            @Override
            public void onViewClick(CommandDialog dialog, View view, Object tag) {
                dialog.dismiss();
            }
        });
        builder.create().show();
       builder.setCancelBtnText(Html.fromHtml("<font color='#336699' size='24'>你好...</font>"));
        builder.contentView().setText(Html.fromHtml("<font color='yellow' size='24'>你好</font>"));
    */
        float days = TimeUtil.daysDifference("2018-07-28 09:00:00", "2018-07-28 17:23:35", BaseStr.TIME_ALL, 8, false);
        float hour = TimeUtil.hourDifference("2018-07-28 16:00:00", "2018-07-28 16:59:59");
        System.out.println(days);
        System.out.println(hour);
    }
}

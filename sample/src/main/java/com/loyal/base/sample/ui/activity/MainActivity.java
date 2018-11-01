package com.loyal.base.sample.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loyal.base.gps.GpsLocBean;
import com.loyal.base.gps.GpsLocation;
import com.loyal.base.impl.OnMultiplePermissionsListener;
import com.loyal.base.impl.OnSinglePermissionListener;
import com.loyal.base.sample.FileUtil;
import com.loyal.base.sample.R;
import com.loyal.base.sample.adapter.ListAdapter;
import com.loyal.base.sample.adapter.RecycleAdapter;
import com.loyal.base.sample.rxjava.RxProgressSubscribe;
import com.loyal.base.util.GsonUtil;
import com.loyal.base.util.OutUtil;
import com.loyal.base.util.TimeUtil;

import java.io.File;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission;

public class MainActivity extends ABaseActivity implements OnSinglePermissionListener, View.OnClickListener, OnMultiplePermissionsListener, GpsLocation.LocationListener {
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void afterOnCreate() {
        textView.setText("Just from MainActivity");
        textView.setOnClickListener(this);
        //singlePermission(PerMission.storagePermission, this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        multiplePermissions(this, permission.READ_PHONE_STATE, permission.CAMERA, permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] array = new String[]{"1", "2", "3", "4", "5"};
        recyclerView.setAdapter(new RecycleAdapter(this, Arrays.asList(array)));
        listView.setAdapter(new ListAdapter(this,Arrays.asList(array)));
        RxProgressSubscribe<String> subscribe=new RxProgressSubscribe<>(this,"192.168.0.110");
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
        OutUtil.println(days);
        OutUtil.println(hour);
    }

    @Override
    public void onMultiplePermissions(@NonNull String permissionName, boolean successful, boolean shouldShow) {
        OutUtil.println(permissionName + "#成功#" + successful + "#下次提醒#" + shouldShow);
        switch (permissionName) {
            case permission.ACCESS_COARSE_LOCATION:
                GpsLocation gpsLocation = GpsLocation.getInstance(this);
                gpsLocation.setLocationListener(this);
                gpsLocation.start();
                break;
        }
    }

    @Override
    public void onLocation(GpsLocBean gpsLocBean) {
        OutUtil.println(GsonUtil.bean2Json(gpsLocBean));
    }
}

package com.sample.base.ui.activity;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loyal.base.gps.GpsLocBean;
import com.loyal.base.gps.GpsLocation;
import com.loyal.base.impl.CommandViewClickListener;
import com.loyal.base.widget.CommandDialog;
import com.sample.base.FileUtil;
import com.sample.base.R;
import com.sample.base.adapter.ListAdapter;
import com.sample.base.adapter.RecycleAdapter;
import com.sample.base.base.ABaseActivity;
import com.sample.base.rxjava.RxJavaTaskSubscriber;
import com.sample.base.rxjava.RxProgressSubscriber;
import com.loyal.rx.RxUtil;
import com.loyal.rx.impl.MultiplePermissionsListener;
import com.loyal.rx.impl.RxSubscriberListener;
import com.loyal.rx.impl.SinglePermissionListener;
import com.loyal.kit.GsonUtil;
import com.loyal.kit.OutUtil;
import com.loyal.kit.TimeUtil;

import java.io.File;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission;

public class MainActivity extends ABaseActivity implements SinglePermissionListener, View.OnClickListener, MultiplePermissionsListener, GpsLocation.LocationListener, RxSubscriberListener<String> {
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
        singlePermission(PerMission.storagePermission, this, PerMission.WRITE_EXTERNAL_STORAGE, PerMission.READ_EXTERNAL_STORAGE);
        multiplePermissions(this, PerMission.READ_PHONE_STATE, PerMission.CAMERA, PerMission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] array = new String[]{"1", "2", "3", "4", "5"};
        recyclerView.setAdapter(new RecycleAdapter(this, Arrays.asList(array)));
        listView.setAdapter(new ListAdapter(this, Arrays.asList(array)));
    }

    @Override
    public void onSinglePermission(int reqCode, boolean successful) {
        if (successful) {
            FileUtil.createFileSys();
        }
    }

    @OnClick({R.id.textView, R.id.downLoad, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView:
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
                float days = TimeUtil.daysDifference("2018-07-28 09:00:00", "2018-07-28 17:23:35", TimeUtil.TIME_ALL, 8, false);
                float hour = TimeUtil.hourDifference("2018-07-28 16:00:00", "2018-07-28 16:59:59");
                OutUtil.println(days);
                OutUtil.println(hour);

                RxProgressSubscriber<String> subscribe = new RxProgressSubscriber<>(this, "192.168.0.110");
                subscribe.showProgressDialog(true).setDialogMessage("登录中...");
                subscribe.setSubscribeListener(this);
                RxUtil.rxExecute(subscribe.doLogin("{\"account\":\"loyal\",\"password\":\"111111\"}"), subscribe);
                break;
            case R.id.downLoad:
                /*RxProgressSubscriber<ResponseBody> subscriber = new RxProgressSubscriber<>(this);
                String url = "http://192.168.0.110:8080/mwm/action.do?method=doShowIconByIO&account=loyal";
                subscriber.setSubscribeListener(new RxSubscriberListener<ResponseBody>() {

                    @Override
                    public void onResult(int what, Object tag, ResponseBody responseBody) {
                        try {
                            File iconFile = new File(FileUtil.imagePath, "icon_" + tag + ".jpg");
                            FileUtil.deleteFile(iconFile);
                            String save = ImageUtil.saveToFile(iconFile, responseBody.byteStream());
                            OutUtil.println(TextUtils.isEmpty(save) ? "下载失败" : "保存成功");
                        } catch (Exception e) {
                            OutUtil.println("imageService::Error--" + e);
                            onError(what, tag, e);
                        }
                    }

                    @Override
                    public void onError(int what, Object tag, Throwable e) {
                        System.out.println("downLoad" + e);
                    }
                });
                RxUtil.rxExecutedByIO(subscriber.downloadImage(url), subscriber);*/
                shodDialogForTest();
                break;
            case R.id.save:
                BitmapDrawable bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.pic));
                RxJavaTaskSubscriber<String> subscriber = new RxJavaTaskSubscriber<>(this);
                subscriber.showProgressDialog(true).setDialogMessage("照片压缩中...");
                subscriber.setSubscribeListener(new RxSubscriberListener<String>() {

                    @Override
                    public void onResult(int what, Object tag, String result) {
                        showToast(TextUtils.isEmpty(result) ? "失败" : "成功");
                    }

                    @Override
                    public void onError(int what, Object tag, Throwable e) {
                        showErrorDialog("压缩失败", e);
                    }
                });
                RxUtil.rxExecuteAndCompute(subscriber.saveBmp(FileUtil.imagePath + "pic.jpg", bitmap.getBitmap()), subscriber);
                break;
        }
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

    private void shodDialogForTest() {
        CommandDialog.Builder dialogBuilder = new CommandDialog.Builder(this);
        dialogBuilder.setOutsideCancel(true);
        dialogBuilder.setContent("测试");
        dialogBuilder.showSingleBtn(TypeImpl.NEXT, "确 定")
                .setClickListener(new CommandViewClickListener() {
                    @Override
                    public void onViewClick(DialogInterface dialog, View view, Object tag) {
                        System.out.println("onViewClick");
                        dialog.dismiss();
                    }
                });
        dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                System.out.println("onCancel");
            }
        });
        dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                System.out.println("onDismiss");
            }
        });
        dialogBuilder.show();
    }

    @Override
    public void onResult(int what, Object tag, String result) {
        try {
            OutUtil.println("login", result);
        } catch (Exception e) {
            e.printStackTrace();
            onError(what, tag, e);
        }
    }

    @Override
    public void onError(int what, Object tag, Throwable e) {
        showErrorDialog("Error", e);
    }
}

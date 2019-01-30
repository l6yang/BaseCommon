package com.sample.base.ui.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MenuItem;

import com.loyal.base.adapter.BasePagerAdapter;
import com.sample.base.R;
import com.sample.base.base.ABaseActivity;
import com.sample.base.ui.fragment.BlankFragment;
import com.loyal.rx.impl.SinglePermissionListener;
import com.loyal.kit.OutUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Test2Activity extends ABaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, SinglePermissionListener {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int actLayoutRes() {
        return R.layout.activity_test;
    }

    @Override
    public void afterOnCreate() {
        fragments.add(BlankFragment.newInstance("test1"));
        fragments.add(BlankFragment.newInstance("test2"));
        fragments.add(BlankFragment.newInstance("camera"));
        viewPager.setAdapter(new BasePagerAdapter(getSupportFragmentManager(), fragments));
        navigation.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_dashboard:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_notifications:
                viewPager.setCurrentItem(2);
                return true;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                navigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_dashboard);
                break;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_notifications);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onFrag2Act(String tag, Object... objectParam) {
        super.onFrag2Act(tag, objectParam);
        if (TextUtils.equals("camera", tag)) {
            singlePermission(9, this, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onSinglePermission(int reqCode, boolean successful) {
        OutUtil.println(successful ? "请求相机权限成功" : "请求相机权限失败");
    }
}

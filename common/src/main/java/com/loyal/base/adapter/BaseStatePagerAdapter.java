package com.loyal.base.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 适用于多页面的ViewPager
 */
public class BaseStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();

    public BaseStatePagerAdapter(FragmentManager fm) {
        this(fm, null);
    }

    public BaseStatePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        if (null == fragments)
            fragments = new ArrayList<>();
        this.fragmentList = fragments;
    }

    public void notifyList(List<Fragment> arrList) {
        changedList(arrList);
        notifyDataSetChanged();
    }

    public void notifyList() {
        notifyList(null);
    }

    public void changedList(List<Fragment> fragments) {
        if (null == fragments)
            fragments = new ArrayList<>();
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return null == fragmentList ? null : fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return null == fragmentList ? 0 : fragmentList.size();
    }

    //notifyDataSetChanged不起作用，必须加上这句
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

}

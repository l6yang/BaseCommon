package com.loyal.base.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public ViewAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return null == fragments ? 0 : fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return null == fragments ? null : fragments.get(position);
    }
}

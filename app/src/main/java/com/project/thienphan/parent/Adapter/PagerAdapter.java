package com.project.thienphan.parent.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.project.thienphan.parent.Fragment.NotifyFragment;
import com.project.thienphan.parent.Fragment.ResultFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case 0:
                fragment = new NotifyFragment();
                break;
            case 1:
                fragment = new ResultFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Nhận xét";
                break;
            case 1:
                title = "Điểm thi";
                break;
        }
        return title;
    }
}

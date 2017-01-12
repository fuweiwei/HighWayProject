package com.ty.highway.highwaysystem.support.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ty.highway.highwaysystem.ui.fragment.basic.BaseFragment;

import java.util.List;

/**
 * Created by fuweiwei on 2015/9/9.
 */
public class TabFragmentAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    public TabFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public BaseFragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}

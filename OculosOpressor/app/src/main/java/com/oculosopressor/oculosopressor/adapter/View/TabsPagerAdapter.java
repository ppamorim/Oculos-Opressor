package com.oculosopressor.oculosopressor.adapter.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.List;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public List<Fragment> mFragments;
    private List<String> mTagFragment;

    public TabsPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments, List<String> fragmentTag) {
        super(fragmentManager);
        mFragments = fragments;
        mTagFragment = fragmentTag;
    }

    public TabsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTagFragment.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position,  Object object) {
        if (position >= getCount()) {
            FragmentManager manager = ((Fragment) object).getFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove((Fragment) object);
            trans.commitAllowingStateLoss();
        }
    }
}

package com.ualr.idlegame.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabFragmentPager extends FragmentStatePagerAdapter {

    int tabCount;

    public TabFragmentPager (FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem (int position) {
        switch (position) {
            case 0:
                return new RecruitTabFragment();
            case 1:
                return new ArmyTabFragment();
            case 2:
                return new UpgradesTabFragment();
            case 3:
                return new MapTabFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

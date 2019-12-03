package com.ualr.idlegame.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabFragmentPager extends FragmentStatePagerAdapter {

    int tabCount;
    Fragment active = null;

    public TabFragmentPager (FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem (int position) {
        switch (position) {
            case 0:
                active = new RecruitTabFragment();
                break;
            case 1:
                active = new ArmyTabFragment();
                break;
            case 2:
                active = new UpgradesTabFragment();
                break;
            case 3:
                active = new MapTabFragment();
                break;

            default:
                active =  null;
        };

        return active;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public Fragment getActive () {
        return active;
    }
}

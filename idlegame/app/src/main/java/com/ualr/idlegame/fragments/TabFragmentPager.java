package com.ualr.idlegame.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ualr.idlegame.fragments.tabs.ArmyTabFragment;
import com.ualr.idlegame.fragments.tabs.MapTabFragment;
import com.ualr.idlegame.fragments.tabs.RecruitTabFragment;
import com.ualr.idlegame.fragments.tabs.UpgradesTabFragment;

public class TabFragmentPager extends FragmentStatePagerAdapter {

    int tabCount;
    Fragment active = null;

    public TabFragmentPager (FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem (int position) {
        setActive(position);
        return active;
    }

    public void setActive (int position) {
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
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public Fragment getActive () {
        return active;
    }
}

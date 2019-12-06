package com.ualr.idlegame.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.fragments.tabs.ArmyTabFragment;
import com.ualr.idlegame.fragments.tabs.MapTabFragment;
import com.ualr.idlegame.fragments.tabs.RecruitTabFragment;
import com.ualr.idlegame.fragments.tabs.UpgradesTabFragment;

import java.util.HashMap;
import java.util.Map;

public class TabFragmentPager extends FragmentStatePagerAdapter {
    Fragment active = null;
    Map<Integer, Fragment> fragmentMap = new HashMap<>();

    public TabFragmentPager (FragmentManager fm) {
        super(fm);

        fragmentMap.put(0, new RecruitTabFragment());
        fragmentMap.put(1, new ArmyTabFragment());
        fragmentMap.put(2, new UpgradesTabFragment());
        fragmentMap.put(3, new MapTabFragment());
    }

    @Override
    public Fragment getItem (int position) {
        return fragmentMap.get(position);
    }

    public void setActive (int position) {
        // deactivate every fragment
        for (Map.Entry<Integer, Fragment> entry : fragmentMap.entrySet()) {
            ((TabFragment) entry.getValue()).deactivate();
        }

        // active the newly selected fragment
        active = fragmentMap.get(position);
        ((TabFragment) active).activate();
    }

    @Override
    public int getCount() {
        return fragmentMap.size();
    }

    public Fragment getActive () {
        return active;
    }

    public Object[] getFragments () {
        return fragmentMap.values().toArray();
    }
}

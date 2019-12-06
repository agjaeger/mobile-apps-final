package com.ualr.idlegame.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.interfaces.TabFragment;

public class UpgradesTabFragment extends Fragment implements TabFragment {
    private boolean mActive = false;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_upgrades_fragment, container, false);
    }

    @Override
    public void onTick () {

    }

    @Override
    public boolean isActive () {
        return mActive;
    }

    @Override
    public void activate () {
        mActive = true;
    }

    @Override
    public void deactivate () {
        mActive = false;
    }
}

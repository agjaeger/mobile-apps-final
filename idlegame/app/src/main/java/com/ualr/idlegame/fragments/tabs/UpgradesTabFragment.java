package com.ualr.idlegame.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

public class UpgradesTabFragment extends Fragment implements TabFragment {
    private UpgradesTabFragment.UpgradesTabFragmentViewHolder viewHolder = null;
    private AppDataViewModel viewModel = null;
    private boolean mActive = false;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tab_upgrades_fragment, container, false);
        viewHolder = new UpgradesTabFragment.UpgradesTabFragmentViewHolder(view);
        return view;
        //return inflater.inflate(R.layout.tab_upgrades_fragment, container, false);
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


    public class UpgradesTabFragmentViewHolder {

        public UpgradesTabFragmentViewHolder(View view){
        }

    }

}

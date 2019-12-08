package com.ualr.idlegame.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.UpgradeRowFragment;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UpgradesTabFragment extends Fragment implements TabFragment {
    private UpgradesTabFragment.UpgradesTabFragmentViewHolder viewHolder = null;
    private AppDataViewModel viewModel = null;
    private boolean mActive = false;
    private List<String> upgrades = new ArrayList<>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tab_upgrades_fragment, container, false);
        viewHolder = new UpgradesTabFragment.UpgradesTabFragmentViewHolder(view);

        // get App Data View Model
        viewModel = ViewModelProviders.of(getActivity()).get(AppDataViewModel.class);

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

    public void addUpgrades (String upgrade) { upgrades.add(upgrade); }

    public List<String> getUpgrades () {return upgrades;}

    public int getUpgradeSize () {return upgrades.size();}


    public class UpgradesTabFragmentViewHolder {
        private LinearLayout linearLayout;

        private List<UpgradeRowFragment> upgradeRowFragments = new ArrayList();


        public UpgradesTabFragmentViewHolder(View view){
            linearLayout = view.findViewById(R.id.upgrade_linear_layout);

            FragmentTransaction ft = getFragmentManager().beginTransaction();

           // ft.replace(R.id.upgrade_header_placeholder, new ActionHeaderFragment(), getRandomKey());

            upgradeRowFragments.add(constructUpgradeRowFragment("100 Coins", "Double-Time", "x2"));
            ft.add(linearLayout.getId(), upgradeRowFragments.get(upgradeRowFragments.size() - 1), "Upgrade 1");

            upgradeRowFragments.get(upgradeRowFragments.size() - 1).setOnUpgradeListener(new UpgradeRowFragment.OnUpgradeListener() {
                @Override
                public void onUpgrade(String upgrade) {
                    addUpgrades(upgrade);
                }
            });

            upgradeRowFragments.add(constructUpgradeRowFragment("100000 Coins", "bonus2", "x2"));
            ft.add(linearLayout.getId(), upgradeRowFragments.get(upgradeRowFragments.size() - 1), "Upgrade 2");

            ft.commit();
        }

        private UpgradeRowFragment constructUpgradeRowFragment (String costLabel, String typeLabel, String valueLabel) {
            Bundle bundle = new Bundle();
            bundle.putString("costLabel", costLabel);
            bundle.putString("typeLabel", typeLabel);
            bundle.putString("valueLabel", valueLabel);

            UpgradeRowFragment arf = new UpgradeRowFragment();
            arf.setArguments(bundle);

            return arf;
        }

        private String getRandomKey () {
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            return new String(array, Charset.forName("UTF-8"));
        }

    }

}

package com.ualr.idlegame.fragments.tabs;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.fragments.rows.ActionRowFragment;
import com.ualr.idlegame.fragments.rows.PurchaseableActionRowFragment;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UpgradesTabFragment extends Fragment implements TabFragment {
    private Context context = null;
    private Resources resources = null;

    private UpgradesTabFragment.UpgradesTabFragmentViewHolder viewHolder = null;
    private AppDataViewModel viewModel = null;

    private boolean mActive = false;

    private List<String> upgrades = new ArrayList<>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        resources = context.getResources();

        // inflate the view
        View view =  inflater.inflate(R.layout.tab_upgrades_fragment, container, false);
        viewHolder = new UpgradesTabFragment.UpgradesTabFragmentViewHolder(view);

        // get App Data View Model
        viewModel = ViewModelProviders.of(getActivity()).get(AppDataViewModel.class);

        return view;
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

        private List<PurchaseableActionRowFragment> upgradeRowFragments = new ArrayList();


        public UpgradesTabFragmentViewHolder(View view){
            linearLayout = view.findViewById(R.id.upgrade_linear_layout);

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.replace(
                R.id.upgrade_header_placeholder,
                constructActionHeaderFragment(resources.getStringArray(R.array.upgrades_header)),
                getRandomKey()
            );

            // Generate Action Row Fragments
            int upgradesNumRows = resources.getInteger(R.integer.upgrades_num_rows);
            for (int rowIdx = 0; rowIdx < upgradesNumRows; rowIdx++) {
                int rowAndroidID = resources.getIdentifier(
                        "upgrades_row_" + rowIdx,
                        "array", context.getPackageName()
                );


                PurchaseableActionRowFragment arf = constructActionRowFragment(resources.getStringArray(rowAndroidID));
                arf.onPurchaseListener = new PurchaseableActionRowFragment.OnPurchaseListener() {
                    @Override
                    public void onPurchase (String name) {
                        addUpgrades(name);
                    }
                };

                upgradeRowFragments.add(arf);
                ft.add(linearLayout.getId(), arf, getRandomKey());
            }

            ft.commit();
        }

        private ActionRowFragment constructActionHeaderFragment (String[] headerInfo) {
            Bundle bundle = new Bundle();
            bundle.putString("leftLabel", headerInfo[resources.getInteger(R.integer.upgrades_header_left_idx)]);
            bundle.putString("centerLabel", headerInfo[resources.getInteger(R.integer.upgrades_header_center_idx)]);
            bundle.putString("rightLabel", "");

            ActionRowFragment ahf = new ActionRowFragment();
            ahf.setArguments(bundle);

            return ahf;
        }

        private PurchaseableActionRowFragment constructActionRowFragment (String[] rowInfo) {
            String actionCost = rowInfo[resources.getInteger(R.integer.army_row_cost_idx)];
            String actionTitle = rowInfo[resources.getInteger(R.integer.army_row_title_idx)];
            String actionReward = "";

            // Bundle up the data
            Bundle bundle = new Bundle();
            bundle.putString("leftLabel", actionCost);
            bundle.putString("centerLabel", actionTitle);
            bundle.putString("rightLabel", actionReward);

            PurchaseableActionRowFragment arf = new PurchaseableActionRowFragment();
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

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
import com.ualr.idlegame.fragments.rows.ActionRowFragment;
import com.ualr.idlegame.fragments.interfaces.OnProgressViewHolder;
import com.ualr.idlegame.fragments.interfaces.ProgressViewHolder;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.fragments.rows.UnlockableActionRowFragment;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ArmyTabFragment extends Fragment implements TabFragment {
    private Context context = null;
    private Resources resources = null;

    private boolean mActive = false;
    private ArmyTabFragmentViewHolder viewHolder;
    private AppDataViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        resources = context.getResources();

        View view = inflater.inflate(R.layout.tab_army_fragment, container, false);
        viewHolder = new ArmyTabFragmentViewHolder(view);
        // setup callback for when the progress bar completes.
        viewHolder.onProgressViewHolderListener = new OnProgressViewHolder() {
            @Override
            public void onComplete (String[] resources, int increment) {
                //System.out.println("PROGRESS COMPLETE");
                viewModel.incrementResource("power", increment);
            }
        };

        // get App Data View Model
        viewModel = ViewModelProviders.of(getActivity()).get(AppDataViewModel.class);

        return view;
    }

    @Override
    public void onTick () {
        if (viewHolder != null) {
            viewHolder.incrementProgress();
        }
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

    public void tryUnlockAll (Integer totalPowerEarned) {
        if (viewHolder != null) {
            for (UnlockableActionRowFragment arf : viewHolder.actionRowFragments) {
                if (totalPowerEarned >= arf.getCost()) {
                    arf.enablePurchaseButton();
                }
            }
        }
    }

    public class ArmyTabFragmentViewHolder implements ProgressViewHolder {
        private LinearLayout linearLayout;
        public List<UnlockableActionRowFragment> actionRowFragments = new ArrayList();
        public OnProgressViewHolder onProgressViewHolderListener;


        public ArmyTabFragmentViewHolder (View view) {
            linearLayout = view.findViewById(R.id.army_linear_layout);

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.replace(
                R.id.army_header_placeholder,
                constructActionHeaderFragment(resources.getStringArray(R.array.army_header)),
                getRandomKey()
            );

            // Generate Action Row Fragments
            int armyNumRows = resources.getInteger(R.integer.army_num_rows);
            for (int rowIdx = 0; rowIdx < armyNumRows; rowIdx++) {
                int rowAndroidID = resources.getIdentifier(
                        "army_row_" + rowIdx,
                        "array", context.getPackageName()
                );

                UnlockableActionRowFragment arf = constructActionRowFragment(resources.getStringArray(rowAndroidID));

                arf.setOnPowerUpListener(new UnlockableActionRowFragment.OnPowerUpListener(){
                    @Override
                    public void onPowerUp (){
                        System.out.println("Hit");
                    };
                });


                actionRowFragments.add(arf);

                ft.add(linearLayout.getId(), arf, getRandomKey());
            }

            ft.commit();
        }

        @Override
        public void incrementProgress () {
            for (UnlockableActionRowFragment arf  : actionRowFragments) {
                if (arf.unlocked()) {
                    arf.incrementProgressBar();
                }
            }
        }

        private UnlockableActionRowFragment constructActionRowFragment (String[] rowInfo) {
            String actionCost = rowInfo[resources.getInteger(R.integer.army_row_cost_idx)];
            String actionTitle = rowInfo[resources.getInteger(R.integer.army_row_title_idx)];
            String actionReward = "+" + rowInfo[resources.getInteger(R.integer.army_row_money_earned_idx)];

            // Bundle up the data
            Bundle bundle = new Bundle();
            bundle.putString("leftLabel", actionCost);
            bundle.putString("centerLabel", actionTitle);
            bundle.putString("rightLabel", actionReward);

            UnlockableActionRowFragment arf = new UnlockableActionRowFragment();
            arf.setArguments(bundle);

            // setup increment amount
            arf.setIncrement(Integer.parseInt(actionReward));
            arf.setResource("money");
            arf.setCost(Integer.parseInt(actionCost));

            // setup callback for when the progress bar completes.
            arf.onProgressViewHolder = new OnProgressViewHolder() {
                @Override
                public void onComplete (String[] resources, int increment) {
                    for (String resource : resources) {
                        viewModel.incrementResource(resource, increment);
                    }
                }
            };

            return arf;
        }

        private ActionRowFragment constructActionHeaderFragment (String[] headerInfo) {
            Bundle bundle = new Bundle();
            bundle.putString("leftLabel", headerInfo[resources.getInteger(R.integer.army_header_left_idx)]);
            bundle.putString("centerLabel", headerInfo[resources.getInteger(R.integer.army_header_center_idx)]);
            bundle.putString("rightLabel", headerInfo[resources.getInteger(R.integer.army_header_right_idx)]);

            ActionRowFragment ahf = new ActionRowFragment();
            ahf.setArguments(bundle);

            return ahf;
        }

        private String getRandomKey () {
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            return new String(array, Charset.forName("UTF-8"));
        }
    }
}

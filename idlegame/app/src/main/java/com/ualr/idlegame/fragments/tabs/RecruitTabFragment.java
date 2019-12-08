package com.ualr.idlegame.fragments.tabs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.ActionHeaderFragment;
import com.ualr.idlegame.fragments.ActionRowFragment;
import com.ualr.idlegame.fragments.interfaces.OnProgressViewHolder;
import com.ualr.idlegame.fragments.interfaces.ProgressViewHolder;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecruitTabFragment extends Fragment implements TabFragment {
    private RecruitTabFragmentViewHolder viewHolder = null;
    private AppDataViewModel viewModel = null;
    private Context context = null;
    private Resources resources = null;

    private boolean mActive = false;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        resources = context.getResources();

        // inflate and populate view
        View view =  inflater.inflate(R.layout.tab_recruit_fragment, container, false);
        viewHolder = new RecruitTabFragmentViewHolder(view);

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

    public class RecruitTabFragmentViewHolder implements ProgressViewHolder {
        private LinearLayout linearLayout;

        private List<ActionRowFragment> actionRowFragments = new ArrayList();
        public OnProgressViewHolder onProgressViewHolderListener;


        public RecruitTabFragmentViewHolder (View view) {
            linearLayout = view.findViewById(R.id.linear_layout);

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            // Generate Action Header Fragment
            ft.replace(R.id.header_placeholder, constructActionHeaderFragment(), getRandomKey());

            // Generate Action Row Fragments
            int recruitNumRows = resources.getInteger(R.integer.recruit_num_rows);
            for (int rowIdx = 0; rowIdx < recruitNumRows; rowIdx++) {
                int rowAndroidID = resources.getIdentifier(
                        "recruit_row_" + rowIdx,
                        "array", context.getPackageName()
                );

                ActionRowFragment arf = constructActionRowFragment(resources.getStringArray(rowAndroidID));
                actionRowFragments.add(arf);
                ft.add(linearLayout.getId(), arf, getRandomKey());
            }

            ft.commit();
        }

        @Override
        public void incrementProgress () {
            for (ActionRowFragment arf  : actionRowFragments) {
                if (arf.purchased()) {
                    arf.incrementProgressBar();
                }
            }
        }

        private ActionRowFragment constructActionRowFragment (String[] rowInfo) {
            String actionCost = rowInfo[resources.getInteger(R.integer.recruit_row_cost_idx)];
            String actionTitle = rowInfo[resources.getInteger(R.integer.recruit_row_title_idx)];
            String actionReward = "+" + rowInfo[resources.getInteger(R.integer.recruit_row_earned_power_value_idx)];
            String actionUnit = rowInfo[resources.getInteger(R.integer.recruit_row_earned_unit_string_idx)];

            // Bundle up the data
            Bundle bundle = new Bundle();
            bundle.putString("costLabel", actionCost);
            bundle.putString("typeLabel", actionTitle);
            bundle.putString("valueLabel", actionReward);

            ActionRowFragment arf = new ActionRowFragment();
            arf.setArguments(bundle);

            // setup increment amount
            arf.setIncrement(Integer.parseInt(actionReward));
            arf.setResource("power," + actionUnit);

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

        private ActionHeaderFragment constructActionHeaderFragment () {
            ActionHeaderFragment ahf = new ActionHeaderFragment();

            ahf.setLeftTextView("Money Req");
            ahf.setCenterTextView("Action");
            ahf.setRightTextView("Power Gained");

            return ahf;
        }

        private String getRandomKey () {
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            return new String(array, Charset.forName("UTF-8"));
        }
    }
}

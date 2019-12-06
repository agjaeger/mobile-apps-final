package com.ualr.idlegame.fragments.tabs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.ActionRowFragment;
import com.ualr.idlegame.fragments.TabFragment;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecruitTabFragment extends Fragment implements TabFragment {
    RecruitTabFragmentViewHolder viewHolder = null;
    AppDataViewModel viewModel = null;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate and populate view
        View view =  inflater.inflate(R.layout.tab_recruit_fragment, container, false);
        viewHolder = new RecruitTabFragmentViewHolder(view);

        // get App Data View Model
        viewModel = ViewModelProviders.of(getActivity()).get(AppDataViewModel.class);

        return view;
    }

    @Override
    public void onTick () {

    }

    public void incrementProgressBars () {
        if (viewHolder != null) {
            viewHolder.incrementProgressBars();
        }
    }

    public class RecruitTabFragmentViewHolder {
        private LinearLayout linearLayout;

        private List<ActionRowFragment> actionRowFragments = new ArrayList();

        public RecruitTabFragmentViewHolder (View view) {
            linearLayout = view.findViewById(R.id.linear_layout);

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            actionRowFragments.add(new ActionRowFragment("I am Frag 1"));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), "someTag1");

            actionRowFragments.add(new ActionRowFragment("I am Frag 2"));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), "someTag2");

            ft.commit();
        }

        public void incrementProgressBars () {
            for (ActionRowFragment arf  : actionRowFragments) {
                int originalValue = arf.getProgressBarValue();
                arf.setProgressBarValue((originalValue + 1) % 100);
            }
        }
    }
}

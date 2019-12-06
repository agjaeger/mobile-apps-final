package com.ualr.idlegame.fragments.tabs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.ActionRowFragment;
import com.ualr.idlegame.fragments.interfaces.OnProgressViewHolder;
import com.ualr.idlegame.fragments.interfaces.ProgressViewHolder;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
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

        // setup callback for when the progress bar completes.
        viewHolder.onProgressViewHolderListener = new OnProgressViewHolder() {
            @Override
            public void onComplete() {

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

    public class RecruitTabFragmentViewHolder implements ProgressViewHolder {
        private LinearLayout linearLayout;

        private List<ActionRowFragment> actionRowFragments = new ArrayList();
        public OnProgressViewHolder onProgressViewHolderListener;


        public RecruitTabFragmentViewHolder (View view) {
            linearLayout = view.findViewById(R.id.linear_layout);

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            actionRowFragments.add(new ActionRowFragment("I am Frag 1"));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), "someTag1");

            actionRowFragments.add(new ActionRowFragment("I am Frag 2"));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), "someTag2");

            ft.commit();
        }

        @Override
        public void incrementProgress () {
            for (ActionRowFragment arf  : actionRowFragments) {
                int nextValue = arf.getProgressBarValue() + 1;

                if (nextValue >= 100) {
                    onProgressViewHolderListener.onComplete();
                    arf.setProgressBarValue(0);
                } else {
                    arf.setProgressBarValue(nextValue);
                }
            }
        }
    }
}

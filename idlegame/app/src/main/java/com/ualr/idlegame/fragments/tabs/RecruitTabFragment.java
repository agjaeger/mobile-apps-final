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
    private boolean mActive = false;

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


            ft.replace(R.id.header_placeholder, constructActionHeaderFragment(), getRandomKey());

            // Newspaper
            actionRowFragments.add(constructActionRowFragment("0", "Newspaper Ad", "+1", 1, "footmen"));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), getRandomKey());

            // Town Crier
            actionRowFragments.add(constructActionRowFragment("40", "Town Crier", "+10", 10, "minutemen"));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), getRandomKey());

            // Propaganda
            actionRowFragments.add(constructActionRowFragment("250", "Spread Propaganda", "+100", 100, "artillery"));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), getRandomKey());

            // Celebrity
            actionRowFragments.add(constructActionRowFragment("1500", "Celebrity Endorsement", "+1000", 1000, "calvery"));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), getRandomKey());

            // Dragon balls
            actionRowFragments.add(constructActionRowFragment("80000", "Gather Dragon Balls", "+9001", 9001, "kakarot"));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), getRandomKey());

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

        private ActionRowFragment constructActionRowFragment (String costLabel, String typeLabel, String valueLabel, Integer pincrement, String unit) {
            Bundle bundle = new Bundle();
            bundle.putString("costLabel", costLabel);
            bundle.putString("typeLabel", typeLabel);
            bundle.putString("valueLabel", valueLabel);

            ActionRowFragment arf = new ActionRowFragment();
            arf.setArguments(bundle);

            // setup increment amount
            arf.setIncrement(pincrement);
            arf.setResource("power," + unit);

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

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


public class ArmyTabFragment extends Fragment implements TabFragment {
    private boolean mActive = false;
    private ArmyTabFragmentViewHolder viewHolder;
    private AppDataViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_army_fragment, container, false);
        viewHolder = new ArmyTabFragmentViewHolder(view);
        // setup callback for when the progress bar completes.
        viewHolder.onProgressViewHolderListener = new OnProgressViewHolder() {
            @Override
            public void onComplete (int increment) {
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

    public class ArmyTabFragmentViewHolder implements ProgressViewHolder {
        private LinearLayout linearLayout;

        private List<ActionRowFragment> actionRowFragments = new ArrayList();
        public OnProgressViewHolder onProgressViewHolderListener;


        public ArmyTabFragmentViewHolder (View view) {
            linearLayout = view.findViewById(R.id.army_linear_layout);

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.replace(R.id.army_header_placeholder, constructActionHeaderFragment(), getRandomKey());

            // Newspaper
            actionRowFragments.add(constructActionRowFragment("0", "Dig Ditches", "+1", 1));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), "test");

            // Town Crier
            actionRowFragments.add(constructActionRowFragment("40", "Fortify Town", "+10", 10));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), getRandomKey());

            // Propaganda
            actionRowFragments.add(constructActionRowFragment("250", "Blow Their Socks Off", "+100", 100));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), getRandomKey());

            // Celebrity
            actionRowFragments.add(constructActionRowFragment("1500", "Illegal Horse Racing", "+1000", 1000));
            ft.add(linearLayout.getId(), actionRowFragments.get(actionRowFragments.size() - 1), getRandomKey());

            // Dragon balls
            actionRowFragments.add(constructActionRowFragment("80000", "Fight Vegeta", "+9001", 9001));
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

        private ActionRowFragment constructActionRowFragment (String costLabel, String typeLabel, String valueLabel, Integer pincrement) {
            Bundle bundle = new Bundle();
            bundle.putString("costLabel", costLabel);
            bundle.putString("typeLabel", typeLabel);
            bundle.putString("valueLabel", valueLabel);

            ActionRowFragment arf = new ActionRowFragment();
            arf.setArguments(bundle);

            // setup callback for when the progress bar completes.
            arf.setIncrement(pincrement);

            arf.onProgressViewHolder = new OnProgressViewHolder() {
                @Override
                public void onComplete (int increment) {
                    viewModel.incrementResource("money", increment);
                }
            };


            return arf;
        }

        private ActionHeaderFragment constructActionHeaderFragment () {
            ActionHeaderFragment ahf = new ActionHeaderFragment();

            ahf.setLeftTextView("Power Req");
            ahf.setCenterTextView("Action");
            ahf.setRightTextView("Money Gained");

            return ahf;
        }

        private String getRandomKey () {
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            return new String(array, Charset.forName("UTF-8"));
        }
    }
}

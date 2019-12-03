package com.ualr.idlegame.fragments.tabs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.ActionRowFragment;

import java.util.ArrayList;
import java.util.List;

public class RecruitTabFragment extends Fragment {

    RecruitTabFragmentViewHolder viewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tab_recruit_fragment, container, false);
        RecruitTabFragmentViewHolder viewHolder = new RecruitTabFragmentViewHolder(view);

        return view;
    }



    public class RecruitTabFragmentViewHolder {
        private LinearLayout linearLayout;

        private List<ActionRowFragment> actionRowFragments = new ArrayList();

        public RecruitTabFragmentViewHolder (View view) {
            linearLayout = view.findViewById(R.id.linear_layout);

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.add(linearLayout.getId(), new ActionRowFragment("I am Frag 1"), "someTag1");
            ft.add(linearLayout.getId(), new ActionRowFragment("Ttest"), "someTag2");

            ft.commit();
        }

    }
}

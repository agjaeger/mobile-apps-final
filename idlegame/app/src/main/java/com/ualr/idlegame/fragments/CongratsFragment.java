package com.ualr.idlegame.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ualr.idlegame.R;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

public class CongratsFragment extends Fragment {
    private Context context = null;
    private Resources resources = null;

    private CongratsFragmentViewHolder viewHolder = null;
    private AppDataViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.gamewon_fragment, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(AppDataViewModel.class);
        viewHolder = new CongratsFragmentViewHolder(view);

        return view;
    }


    private class CongratsFragmentViewHolder {
        CongratsFragmentViewHolder (View view) {
            View constraintLayout = view.findViewById(R.id.bgConstraintLayout);

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Test");
                    getActivity().onBackPressed();
                }
            });
        }
    }
}

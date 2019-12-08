package com.ualr.idlegame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ualr.idlegame.R;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

public class ResourcesPaneFragment extends Fragment {
    ResourcesPaneFragmentViewHolder viewHolder = null;
    AppDataViewModel viewModel = null;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate and populate view
        View view =  inflater.inflate(R.layout.resourcepane_fragment, container, false);
        viewHolder = new ResourcesPaneFragmentViewHolder(view);

        // get App Data View Model
        viewModel = ViewModelProviders.of(getActivity()).get(AppDataViewModel.class);

        return view;
    }

    public void update () {
        viewHolder.powerValue.setText(viewModel.getResourceValue("power").toString());
        viewHolder.moneyValue.setText(viewModel.getResourceValue("money").toString());

        viewHolder.footmenValue.setText(viewModel.getResourceValue("footmen").toString());
        viewHolder.minutemenValue.setText(viewModel.getResourceValue("minutemen").toString());
        viewHolder.artilleryValue.setText(viewModel.getResourceValue("artillery").toString());
        viewHolder.cavalryValue.setText(viewModel.getResourceValue("cavalry").toString());
        viewHolder.kakarotValue.setText(viewModel.getResourceValue("kakarot").toString());
    }

    private class ResourcesPaneFragmentViewHolder {

        public TextView powerValue;
        public TextView moneyValue;

        public TextView footmenValue;
        public TextView minutemenValue;
        public TextView artilleryValue;
        public TextView cavalryValue;
        public TextView kakarotValue;


        public ResourcesPaneFragmentViewHolder (View view) {
            powerValue = view.findViewById(R.id.power_value);
            moneyValue = view.findViewById(R.id.money_value);

            footmenValue = view.findViewById(R.id.footmen_value);
            minutemenValue = view.findViewById(R.id.minutemen_value);
            artilleryValue = view.findViewById(R.id.artillery_value);
            cavalryValue = view.findViewById(R.id.cavalry_value);
            kakarotValue = view.findViewById(R.id.kakarot_value);
        }
    }
}

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
        viewHolder.setPowerValue(viewModel.getResourceValue("power").toString());
    }

    public class ResourcesPaneFragmentViewHolder {

        TextView powerValue;

        public ResourcesPaneFragmentViewHolder (View view) {
            powerValue = view.findViewById(R.id.power_value);
        }

        public void setPowerValue (String value) {
            powerValue.setText(value);
        }
    }
}

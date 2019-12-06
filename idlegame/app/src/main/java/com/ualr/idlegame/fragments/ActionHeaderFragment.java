package com.ualr.idlegame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ualr.idlegame.R;

public class ActionHeaderFragment extends Fragment {
    private ActionHeaderFragmentViewHolder viewHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actionheader_fragment, container, false);
        viewHolder = new ActionHeaderFragmentViewHolder(view);

        return view;
    }


    public class ActionHeaderFragmentViewHolder {
        public ActionHeaderFragmentViewHolder (View view) {

        }
    }
}

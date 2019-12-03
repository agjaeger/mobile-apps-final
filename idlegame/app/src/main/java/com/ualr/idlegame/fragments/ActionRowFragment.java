package com.ualr.idlegame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.ualr.idlegame.R;

import java.util.Random;


public class ActionRowFragment extends Fragment {
    private ActionRowFragmentViewHolder viewHolder;

    public ActionRowFragment (String text) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actionrow_fragment, container, false);
        viewHolder = new ActionRowFragmentViewHolder(view);
        return view;
    }

    public void setProgressBarValue (int value) {
        if (viewHolder != null) {
            viewHolder.setProgressBar(value);
        }
    }

    public int getProgressBarValue () {
        if (viewHolder != null) {
            return viewHolder.getProgressBar();
        }

        return 0;
    }

    public class ActionRowFragmentViewHolder {
        private ProgressBar progressBar;

        public ActionRowFragmentViewHolder (View view) {
            progressBar = view.findViewById(R.id.progress_bar);

            Random r = new Random();
            int min = 10;
            int max = 90;

            progressBar.setProgress(r.nextInt(max - min + 1) + min);
        }

        public void setProgressBar (int progress) {
            progressBar.setProgress(progress);
        }

        public int getProgressBar () {
            return progressBar.getProgress();
        }
    }
}

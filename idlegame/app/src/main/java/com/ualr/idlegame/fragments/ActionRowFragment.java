package com.ualr.idlegame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ualr.idlegame.R;

import java.util.Map;
import java.util.Random;


public class ActionRowFragment extends Fragment {
    private ActionRowFragmentViewHolder viewHolder;

    public ActionRowFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actionrow_fragment, container, false);
        viewHolder = new ActionRowFragmentViewHolder(view);

        viewHolder.setCostTextLabel(getArguments().getString("costLabel"));
        viewHolder.setTypeTextLabel(getArguments().getString("typeLabel"));
        viewHolder.setGainedTextLabel(getArguments().getString("valueLabel"));

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
        private TextView costTextLabel;
        private TextView typeTextLabel;
        private TextView gainedTextLabel;

        private ProgressBar progressBar;
        private Button purchaseButton;

        private boolean purchased;

        public ActionRowFragmentViewHolder (View view) {
            costTextLabel = view.findViewById(R.id.required_gold_value);
            typeTextLabel = view.findViewById(R.id.units_recruited_type);
            gainedTextLabel = view.findViewById(R.id.units_recruited_value);

            progressBar = view.findViewById(R.id.progress_bar);
            purchaseButton = view.findViewById(R.id.purchase_btn);
            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    purchased = true;

                    setProgressBar(0);
                    hidePurchaseButton();
                    showProgressBar();
                }
            });

            // setup initial state
            purchased = false;
            //disablePurchaseButton();
            hideProgressBar();
        }

        public void setCostTextLabel (String textLabel) { costTextLabel.setText(textLabel); }
        public void setTypeTextLabel (String textLabel) { typeTextLabel.setText(textLabel); }
        public void setGainedTextLabel (String textLabel) { gainedTextLabel.setText(textLabel); }

        public void setProgressBar (int progress) { progressBar.setProgress(progress); }
        public int getProgressBar () {
            return progressBar.getProgress();
        }

        public void enablePurchaseButton () {
            purchaseButton.setEnabled(true);
        }

        public void disablePurchaseButton () {
            purchaseButton.setEnabled(false);
        }

        public Boolean getPurchased () {
            return purchased;
        }

        private void hideProgressBar () { progressBar.setVisibility(View.INVISIBLE); }
        private void showProgressBar () { progressBar.setVisibility(View.VISIBLE); }

        private void hidePurchaseButton () { purchaseButton.setVisibility(View.INVISIBLE); }
        private void showPurchaseButton () { purchaseButton.setVisibility(View.VISIBLE); }
    }
}

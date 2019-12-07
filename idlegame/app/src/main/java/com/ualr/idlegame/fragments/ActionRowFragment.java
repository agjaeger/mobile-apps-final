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
import com.ualr.idlegame.fragments.interfaces.OnProgressViewHolder;


public class ActionRowFragment extends Fragment {
    public OnProgressViewHolder onProgressViewHolder;

    private ActionRowFragmentViewHolder viewHolder;
    private int increment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actionrow_fragment, container, false);
        viewHolder = new ActionRowFragmentViewHolder(view);

        viewHolder.setCostTextLabel(getArguments().getString("costLabel"));
        viewHolder.setTypeTextLabel(getArguments().getString("typeLabel"));
        viewHolder.setGainedTextLabel(getArguments().getString("valueLabel"));

        return view;
    }

    public void incrementProgressBar () {
        int nextValue = viewHolder.getProgressBar() + 1;
        if (nextValue >= 100) {
            onProgressViewHolder.onComplete(increment);
            viewHolder.setProgressBar(0);
        } else {
            viewHolder.setProgressBar(nextValue);
        }
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

    public Boolean purchased () {
        if (viewHolder != null) {
            return viewHolder.getPurchased();
        }

        return false;
    }

    public void setIncrement (int p_increment) {
        increment = p_increment;
    }

    public class ActionRowFragmentViewHolder {
        private TextView costTextLabel;
        private TextView typeTextLabel;
        private TextView gainedTextLabel;

        private ProgressBar progressBar;
        private Button purchaseButton;

        private boolean purchased;

        public ActionRowFragmentViewHolder (View view) {
            costTextLabel = view.findViewById(R.id.leftTextView);
            typeTextLabel = view.findViewById(R.id.centerTextView);
            gainedTextLabel = view.findViewById(R.id.rightTextView);

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

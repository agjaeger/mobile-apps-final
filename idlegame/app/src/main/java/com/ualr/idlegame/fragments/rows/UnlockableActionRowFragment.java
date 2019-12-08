package com.ualr.idlegame.fragments.rows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.interfaces.OnProgressViewHolder;

public class UnlockableActionRowFragment extends ActionRowFragment {

    public UnlockableActionRowFragment.OnPowerUpListener mOnPowerUpListener;

    public interface OnPowerUpListener{
        void onPowerUp();
    }

    public void setOnPowerUpListener(UnlockableActionRowFragment.OnPowerUpListener powerUpListener){
        mOnPowerUpListener = powerUpListener;
    }

    public OnProgressViewHolder onProgressViewHolder;

    private UnlockableActionRowFragmentViewHolder viewHolder;

    private int increment;
    private String resource;
    private boolean unlocked;
    private int m_cost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actionrow_fragment, container, false);
        viewHolder = new UnlockableActionRowFragmentViewHolder(view);

        super.populateView(view, getArguments());
        return view;
    }

    public void incrementProgressBar () {
        int nextValue = viewHolder.progressBar.getProgress() + 1;

        if (nextValue >= viewHolder.progressBar.getMax()) {
            onProgressViewHolder.onComplete(resource.split(","), increment);
            viewHolder.progressBar.setProgress(0);
        } else {
            viewHolder.progressBar.setProgress(nextValue);
        }
    }

    public void setIncrement (int p_increment) {
        increment = p_increment;
    }
    public void setResource (String p_resource) {
        resource = p_resource;
    }

    public void setCost (int cost) { m_cost = cost;}
    public int getCost () { return m_cost; }

    public Boolean unlocked () {
        return unlocked;
    }
    public void enablePurchaseButton () {
        viewHolder.enablePurchaseBtn();
    }

    private class UnlockableActionRowFragmentViewHolder {
        public ProgressBar progressBar;
        public Button purchaseButton;

        public UnlockableActionRowFragmentViewHolder (View view) {
            View rightActionButtons = view.findViewById(R.id.right_action_container);
            rightActionButtons.setVisibility(View.VISIBLE);

            progressBar = view.findViewById(R.id.progress_bar);
            purchaseButton = view.findViewById(R.id.purchase_btn);

            hideProgressBar();
            showPurchaseBtn ();

            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unlocked = true;

                    progressBar.setProgress(0);
                    hidePurchaseBtn();
                    showProgressBar();
                }
            });

            // setup initial state
            unlocked = false;
            disablePurchaseBtn();
            hideProgressBar();
        }

        private void hideProgressBar () { progressBar.setVisibility(View.INVISIBLE); }
        private void showProgressBar () { progressBar.setVisibility(View.VISIBLE); }

        private void hidePurchaseBtn () {
            purchaseButton.setVisibility(View.INVISIBLE);
        }

        private void showPurchaseBtn () {
            purchaseButton.setVisibility(View.VISIBLE);
        }

        private void disablePurchaseBtn () {
            purchaseButton.setEnabled(false);
        }

        private void enablePurchaseBtn () {
            purchaseButton.setEnabled(true);
        }
    }
}

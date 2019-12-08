package com.ualr.idlegame.fragments.rows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ualr.idlegame.R;

import org.w3c.dom.Text;

public class PurchaseableActionRowFragment extends ActionRowFragment {
    private PurchaseableActionRowFragmentViewHolder viewHolder;
    private boolean purchased;

    public OnPurchaseListener onPurchaseListener;
    public interface OnPurchaseListener {
        void onPurchase (String name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actionrow_fragment, container, false);
        viewHolder = new PurchaseableActionRowFragmentViewHolder(view);

        super.populateView(view, getArguments());
        return view;
    }


    private class PurchaseableActionRowFragmentViewHolder {
        public Button purchaseButton;
        public TextView purchaseLabel;

        public PurchaseableActionRowFragmentViewHolder (View view) {
            View rightActionButtons = view.findViewById(R.id.right_action_container);
            rightActionButtons.setVisibility(View.VISIBLE);

            purchaseLabel = view.findViewById(R.id.purchased_label);

            purchaseButton = view.findViewById(R.id.purchase_btn);
            purchaseButton.setVisibility(View.VISIBLE);

            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    purchased = true;

                    purchaseButton.setVisibility(View.INVISIBLE);
                    purchaseLabel.setVisibility(View.VISIBLE);

                    onPurchaseListener.onPurchase(PurchaseableActionRowFragment.super.getCenterTextLabel());
                }
            });

            // setup initial state
            purchased = false;
        }
    }
}

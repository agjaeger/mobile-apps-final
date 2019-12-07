package com.ualr.idlegame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ualr.idlegame.R;

public class UpgradeRowFragment extends Fragment {
    private UpgradeRowFragmentViewHolder viewHolder;

    public UpgradeRowFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upgraderow_fragment, container, false);
        viewHolder = new UpgradeRowFragmentViewHolder(view);

        viewHolder.setCostTextLabel(getArguments().getString("costLabel"));
        viewHolder.setTypeTextLabel(getArguments().getString("typeLabel"));
        viewHolder.setGainedTextLabel(getArguments().getString("valueLabel"));

        return view;

    }

    public class UpgradeRowFragmentViewHolder{
        private TextView costTextLabel;
        private TextView nameTextLabel;
        private TextView bonusTextLabel;
        private TextView purchasedTextLabel;

        private Button purchaseButton;
        private boolean purchased;

        public UpgradeRowFragmentViewHolder (View view){
            costTextLabel = view.findViewById(R.id.required_gold_value);
            nameTextLabel = view.findViewById(R.id.upgrade_type);
            bonusTextLabel = view.findViewById(R.id.bonus_value);
            purchasedTextLabel = view.findViewById(R.id.purchased_label);

            purchaseButton = view.findViewById((R.id.purchase_btn));
            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    purchased = true;

                    hidePurchaseButton();
                    disablePurchaseButton();
                    showPurchaseTextLabel();
                }
            });

            purchased = false;

            enablePurchaseButton();
            hidePurchaseTextLabel();
        }

        public void setCostTextLabel (String textLabel) { costTextLabel.setText(textLabel); }
        public void setTypeTextLabel (String textLabel) { nameTextLabel.setText(textLabel); }
        public void setGainedTextLabel (String textLabel) { bonusTextLabel.setText(textLabel); }

        public void enablePurchaseButton () {
            purchaseButton.setEnabled(true);
        }

        public void disablePurchaseButton () {
            purchaseButton.setEnabled(false);}

        public Boolean getPurchased () {
            return purchased;
        }

        private void hidePurchaseButton () { purchaseButton.setVisibility(View.INVISIBLE); }
        private void showPurchaseButton () { purchaseButton.setVisibility(View.VISIBLE); }
        private void hidePurchaseTextLabel () { purchasedTextLabel.setVisibility(View.INVISIBLE); }
        private void showPurchaseTextLabel () { purchasedTextLabel.setVisibility(View.VISIBLE); }

    }
}



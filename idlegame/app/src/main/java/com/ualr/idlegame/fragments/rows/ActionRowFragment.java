package com.ualr.idlegame.fragments.rows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.interfaces.OnProgressViewHolder;


public class ActionRowFragment extends Fragment {
    private ActionRowFragmentViewHolder viewHolder;
    private boolean bottomBorder = false;
    private int topMargin = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actionrow_fragment, container, false);
        populateView(view, getArguments());

        return view;
    }

    public void populateView (View view, Bundle bundle) {
        viewHolder = new ActionRowFragmentViewHolder(view);

        viewHolder.leftTextLabel.setText(getArguments().getString("leftLabel"));
        viewHolder.centerTextLabel.setText(getArguments().getString("centerLabel"));
        viewHolder.rightTextLabel.setText(getArguments().getString("rightLabel"));

        if (bottomBorder) {
            viewHolder.cardParent.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bottom_borderline));
        }

        viewHolder.cardParent.requestLayout();
    }

    public void setBottomBorder () {
        if (viewHolder == null) {
            bottomBorder = true;
        } else {
            viewHolder.cardParent.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bottom_borderline));
        }
    }

    public String getLeftTextLabel () {
        return viewHolder.leftTextLabel.getText().toString();
    }

    public String getCenterTextLabel () {
        return viewHolder.centerTextLabel.getText().toString();
    }

    public String getRightTextLabel () {
        return viewHolder.rightTextLabel.getText().toString();
    }

    private class ActionRowFragmentViewHolder {
        public TextView leftTextLabel;
        public TextView centerTextLabel;
        public TextView rightTextLabel;
        public View cardParent;

        public ActionRowFragmentViewHolder (View view) {
            leftTextLabel = view.findViewById(R.id.leftTextView);
            centerTextLabel = view.findViewById(R.id.centerTextView);
            rightTextLabel = view.findViewById(R.id.rightTextView);

            cardParent = view.findViewById(R.id.cardParent);
        }
    }
}

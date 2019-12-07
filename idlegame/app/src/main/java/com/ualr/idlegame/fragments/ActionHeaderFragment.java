package com.ualr.idlegame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ualr.idlegame.R;

import org.w3c.dom.Text;

public class ActionHeaderFragment extends Fragment {
    private ActionHeaderFragmentViewHolder viewHolder;

    private String lText;
    private String cText;
    private String rText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actionheader_fragment, container, false);
        viewHolder = new ActionHeaderFragmentViewHolder(view);

        // set values on startup
        viewHolder.leftTextView.setText(lText);
        viewHolder.centerTextView.setText(cText);
        viewHolder.rightTextView.setText(rText);

        return view;
    }

    public void setLeftTextView (String value) {
        lText = value;

        if (viewHolder != null) {
            viewHolder.leftTextView.setText(value);
        }
    }

    public void setCenterTextView (String value) {
        cText = value;

        if (viewHolder != null) {
            viewHolder.centerTextView.setText(value);
        }
    }

    public void setRightTextView (String value) {
        rText = value;

        if (viewHolder != null) {
            viewHolder.rightTextView.setText(value);
        }
    }

    private class ActionHeaderFragmentViewHolder {
        public TextView leftTextView;
        public TextView centerTextView;
        public TextView rightTextView;

        public ActionHeaderFragmentViewHolder (View view) {
            leftTextView = view.findViewById(R.id.leftTextView);
            centerTextView = view.findViewById(R.id.centerTextView);
            rightTextView = view.findViewById(R.id.rightTextView);
        }

    }
}

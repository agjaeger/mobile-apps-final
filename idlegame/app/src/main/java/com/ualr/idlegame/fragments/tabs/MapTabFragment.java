package com.ualr.idlegame.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.interfaces.TabFragment;

public class MapTabFragment extends Fragment implements TabFragment {

    private MapTabFragmentViewHolder viewHolder = null;
    private boolean mActive = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tab_map_fragment, container, false);
        viewHolder = new MapTabFragmentViewHolder(view);

        return inflater.inflate(R.layout.tab_map_fragment, container, false);
    }

    @Override
    public void onTick () {

    }

    @Override
    public boolean isActive () {
        return mActive;
    }

    @Override
    public void activate () {
        mActive = true;
    }

    @Override
    public void deactivate () {
        mActive = false;
    }

    public class MapTabFragmentViewHolder {
        private ImageView imageView;

        public MapTabFragmentViewHolder(View view){
            imageView = view.findViewById(R.id.map_placeholder);
        }

    }

}

package com.ualr.idlegame.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

public class MapTabFragment extends Fragment implements TabFragment {

    private MapTabFragmentViewHolder viewHolder = null;
    private AppDataViewModel viewModel;
    private boolean mActive = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tab_map_fragment, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(AppDataViewModel.class);

        viewHolder = new MapTabFragmentViewHolder(view);


        return view;
        //return inflater.inflate(R.layout.tab_map_fragment, container, false);
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
        private LinearLayout customButton;
        private ImageView blankSpace;

        public MapTabFragmentViewHolder(View view){
            imageView = view.findViewById(R.id.map_placeholder);

            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    if(viewModel.getResourceValue("power") >= 100000) {
                        setImage(R.drawable.map4);
                    }
                    else if (viewModel.getResourceValue("power") >= 10000) {
                        setImage(R.drawable.map3);
                    }
                    else if (viewModel.getResourceValue("power") >= 1000) {
                        setImage(R.drawable.map2);
                   }
            }

            });

        }

        public void setImage(int imageID) {
            this.imageView.setImageResource(imageID);
        }
    }

}

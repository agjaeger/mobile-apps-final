package com.ualr.idlegame.fragments.tabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        private ImageView referenceImageView;

        private Bitmap reference;

        private boolean unlockedNorth = false;
        private boolean unlockedEast = false;
        private boolean unlockedWest = false;

        public MapTabFragmentViewHolder(View view){
            imageView = view.findViewById(R.id.map);
            referenceImageView = view.findViewById(R.id.reference_map);
            referenceImageView.setVisibility(View.INVISIBLE);

            reference = ((BitmapDrawable) referenceImageView.getDrawable()).getBitmap();

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    float[] eventXY = new float[] {x, y};

                    Matrix invertMatrix =
                    ((ImageView)imageView).getImageMatrix();

                    invertMatrix.mapPoints(eventXY);
                    x = Integer.valueOf((int)eventXY[0]);
                    y = Integer.valueOf((int)eventXY[1]);


                    if (x < 0) {
                        x = 0;
                    } else if (x > reference.getWidth()) {
                        x = reference.getWidth() - 1;
                    }

                    if (y < 0) {
                        y = 0;
                    } else if (y > reference.getHeight()) {
                        y = reference.getHeight() - 1;
                    }

                    int pixel = reference.getPixel(x, y);

                    int colorR = Color.red(pixel);
                    int colorG = Color.green(pixel);
                    int colorB = Color.blue(pixel);

                    System.out.println("REF COLOR " + colorR + " " + colorG + " " + colorB);

                    if (colorR > 200 && !unlockedNorth && !unlockedWest && !unlockedEast) {
                        setImage(R.drawable.map2);
                        unlockedNorth = true;
                    }

                    if (colorB > 200 && unlockedNorth && !unlockedWest && !unlockedEast) {
                        setImage(R.drawable.map3);
                        unlockedWest = true;
                    }

                    if (colorG > 200 && unlockedNorth && unlockedWest && !unlockedEast) {
                        setImage(R.drawable.map4);
                        unlockedEast = true;
                    }
                    return false;
                }
            });
        }

        public void setImage(int imageID) {
            this.imageView.setImageResource(imageID);
        }
    }

}

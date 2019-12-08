package com.ualr.idlegame.fragments.tabs;

import android.content.Context;
import android.content.res.Resources;
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
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.ualr.idlegame.R;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

import java.util.HashMap;
import java.util.Map;

public class MapTabFragment extends Fragment implements TabFragment {
    private Context context = null;
    private Resources resources = null;

    private MapTabFragmentViewHolder viewHolder = null;
    private AppDataViewModel viewModel;

    private boolean mActive = false;

    private Map<String, Boolean> unlockedNations = new HashMap<>();
    private Map<String, Boolean> unlockableNations = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        resources = context.getResources();

        View view =  inflater.inflate(R.layout.tab_map_fragment, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(AppDataViewModel.class);
        viewHolder = new MapTabFragmentViewHolder(view);

        return view;
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

    public void tryUnlockAll (int powerEarned) {
        if (viewHolder != null) {
            for (Map.Entry<String, NationCardViewHolder> nation : viewHolder.nations.entrySet()) {
                unlockableNations.put(nation.getKey(), powerEarned > nation.getValue().cost);
            }
        }
    }

    public Boolean allUnlocked () {
        if (viewHolder == null) {
            return false;
        } else {
            boolean all = true;

            for (Map.Entry<String, NationCardViewHolder> nation : viewHolder.nations.entrySet()) {
                all = all && unlockedNations.get(nation.getKey());
            }

            return all;
        }
    }

    private class NationCardViewHolder {
        public TextView titleTextView;
        public TextView powerValueTextView;

        public int cost;

        public NationCardViewHolder (String nationName, View view) {
            int nationTitleId = resources.getIdentifier("nation_"+nationName+"_title", "id", context.getPackageName());
            int nationPowerValueId = resources.getIdentifier("nation_"+nationName+"_power_value", "id", context.getPackageName());

            cost =
                resources.getInteger(resources.getIdentifier(
                    "map_cost_"+nationName,
                    "integer",
                    context.getPackageName())
                );

            titleTextView = view.findViewById(nationTitleId);
            powerValueTextView = view.findViewById(nationPowerValueId);

            titleTextView.setText(
                    resources.getString(resources.getIdentifier(
                            "map_title_"+nationName,
                            "string",
                            context.getPackageName())
                    )
            );

            powerValueTextView.setText("" + cost);
        }
    }

    private class MapTabFragmentViewHolder {
        private ImageView imageView;
        private ImageView referenceImageView;

        private Bitmap reference;

        public Map<String, NationCardViewHolder> nations = new HashMap<>();

        public MapTabFragmentViewHolder(View view){
            imageView = view.findViewById(R.id.map);
            referenceImageView = view.findViewById(R.id.reference_map);
            referenceImageView.setVisibility(View.INVISIBLE);

            reference = ((BitmapDrawable) referenceImageView.getDrawable()).getBitmap();


            String[] nationNames = resources.getStringArray(R.array.map_nations);
            for (String nation : nationNames) {
                NationCardViewHolder nc = new NationCardViewHolder(nation, view);
                nations.put(nation, nc);

                unlockableNations.put(nation, false);
                unlockedNations.put(nation, false);
            }

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // get screen coordinates
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    float[] eventXY = new float[] {x, y};

                    // convert to image coordinates
                    Matrix imageMatrix =
                    ((ImageView)imageView).getImageMatrix();

                    imageMatrix.mapPoints(eventXY);
                    x = Integer.valueOf((int)eventXY[0]);
                    y = Integer.valueOf((int)eventXY[1]);

                    // clamp just in case
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

                    // get pixel and color
                    int pixel = reference.getPixel(x, y);

                    int colorR = Color.red(pixel);
                    int colorG = Color.green(pixel);
                    int colorB = Color.blue(pixel);

                    // determine if we should unlock
                    if (colorR > 200) {
                        System.out.println("Clicked FIre Namtion");
                        if (unlockableNations.get("fire") && !unlockedNations.get("fire") && !unlockedNations.get("water") && !unlockedNations.get("earth")) {
                            setImage(R.drawable.map2);
                            unlockedNations.put("fire", true);
                        }
                    }

                    if (colorB > 200 && unlockableNations.get("water") && unlockedNations.get("fire") && !unlockedNations.get("water") && !unlockedNations.get("earth")) {
                        setImage(R.drawable.map3);
                        unlockedNations.put("water", true);
                    }

                    if (colorG > 200 && unlockableNations.get("earth") && unlockedNations.get("fire") && unlockedNations.get("water") && !unlockedNations.get("earth")) {
                        setImage(R.drawable.map4);
                        unlockedNations.put("earth", true);
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

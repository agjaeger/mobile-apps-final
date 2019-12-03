package com.ualr.idlegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.ualr.idlegame.fragments.TabFragmentPager;
import com.ualr.idlegame.tasks.UpdateProgressBarsTask;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, UpdateProgressBarsTask.OnTickListener {

    private UpdateProgressBarsTask bgTickThread = new UpdateProgressBarsTask();
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // launch background thread to calculate ticks
        bgTickThread.onTickListener = this;
        bgTickThread.execute();

        // find tablayout view and populate with Tabs
        tabLayout = findViewById(R.id.tablayout_parent);

        tabLayout.addTab(tabLayout.newTab().setText("Recruit"));
        tabLayout.addTab(tabLayout.newTab().setText("Army"));
        tabLayout.addTab(tabLayout.newTab().setText("Upgrades"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // find and setup viewpager
        viewPager = findViewById(R.id.viewPager);
        TabFragmentPager adapter = new TabFragmentPager (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(this);

        /*View progressbar = findViewById(R.id.id_progressbar_2);
        progressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            System.out.println("Hello World");
            }
        });

        try {
            DB snappydb = DBFactory.open(getApplicationContext());

            if (snappydb.exists("myint")) {
                int myint = snappydb.getInt("myint");

                snappydb.putInt("myint", myint + 1);
            } else {
                snappydb.putInt("myint", 0);
            }

            System.out.println(snappydb.getInt("myint"));

            snappydb.close();
        } catch (SnappydbException sde) {
            System.out.println("SNAPPY EXCEPTION");
            System.out.println(sde);
        }*/
    }

    @Override
    public void onTabSelected (TabLayout.Tab tab) {
       viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected (TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected (TabLayout.Tab tab) {

    }

    @Override
    public void onTick () {
        System.out.println("Tick");
    }
}

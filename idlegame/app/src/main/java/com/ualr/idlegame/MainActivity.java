package com.ualr.idlegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.ualr.idlegame.db.DatabaseManager;
import com.ualr.idlegame.fragments.TabFragmentPager;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.fragments.tabs.RecruitTabFragment;
import com.ualr.idlegame.tasks.AutoSaveTask;
import com.ualr.idlegame.tasks.UpdateProgressBarsTask;

import com.snappydb.SnappydbException;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private UpdateProgressBarsTask bgTickThread = new UpdateProgressBarsTask();
    private AutoSaveTask bgAutosaveThread = new AutoSaveTask();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabFragmentPager tabFragmentPager;

    private AppDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // launch background thread to calculate ticks
        bgTickThread.onTickListener = new UpdateProgressBarsTask.OnTickListener() {
            @Override
            public void onTick() {
                Object[] fragments = tabFragmentPager.getFragments();

                for (Object f : fragments) {
                    ((TabFragment)f).onTick();
                }
            }
        };
        bgTickThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        // launch autosave thread
        bgAutosaveThread.onAutoSaveListener = new AutoSaveTask.OnAutoSaveListener() {
            @Override
            public void onAutosave() {
                System.out.println("Autosaving!");
                System.out.println(viewModel.getResourceValue("power"));
            }
        };
        bgAutosaveThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        // get App Data View Model
        viewModel = ViewModelProviders.of(this).get(AppDataViewModel.class);

        // register resources
        viewModel.registerResource("power");

        // open the database for the application
        try {
            DatabaseManager.getInstance().open(getApplicationContext());
        } catch (SnappydbException sde) {
            System.out.println("Unable to open the database!");
            System.exit(1);
        }

        // find tablayout view and populate with Tabs
        tabLayout = findViewById(R.id.tablayout_parent);

        tabLayout.addTab(tabLayout.newTab().setText("Recruit"));
        tabLayout.addTab(tabLayout.newTab().setText("Army"));
        tabLayout.addTab(tabLayout.newTab().setText("Upgrades"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // find and setup viewpager
        viewPager = findViewById(R.id.viewPager);
        tabFragmentPager = new TabFragmentPager (getSupportFragmentManager());
        viewPager.setAdapter(tabFragmentPager);

        // View Pager will persistently store every page in the background
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());

        tabLayout.addOnTabSelectedListener(this);

        // set the first tab as active on startup
        tabFragmentPager.setActive(0);
        viewPager.setCurrentItem(0, true);
    }

    @Override
    public void onTabSelected (TabLayout.Tab tab) {
       tabFragmentPager.setActive(tab.getPosition());
       viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected (TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected (TabLayout.Tab tab) {

    }
}

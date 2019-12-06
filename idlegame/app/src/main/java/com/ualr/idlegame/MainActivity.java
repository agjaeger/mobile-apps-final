package com.ualr.idlegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import com.ualr.idlegame.db.DatabaseManager;
import com.ualr.idlegame.fragments.ResourcesPaneFragment;
import com.ualr.idlegame.fragments.TabFragmentPager;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.tasks.CounterTask;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

import com.snappydb.SnappydbException;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private CounterTask bgTickThread = new CounterTask();
    private CounterTask bgAutosaveThread = new CounterTask();
    private CounterTask bgResourceDisplayUpdateThread = new CounterTask();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabFragmentPager tabFragmentPager;
    private ResourcesPaneFragment resourcesPane;

    private AppDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // launch background thread to calculate ticks
        bgTickThread.onCountListener = new CounterTask.OnCountListener() {
            @Override
            public void onCount () {
                Object[] fragments = tabFragmentPager.getFragments();

                for (Object f : fragments) {
                    ((TabFragment)f).onTick();
                }
            }
        };
        bgTickThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 10);

        // launch autosave thread
        bgAutosaveThread.onCountListener = new CounterTask.OnCountListener() {
            @Override
            public void onCount () {
                Context context = getApplicationContext();
                CharSequence text = "Autosaving!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        };
        bgAutosaveThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 10000);

        // launch thread to periodically update the resource pane
        bgResourceDisplayUpdateThread.onCountListener = new CounterTask.OnCountListener() {
            @Override
            public void onCount () {
                resourcesPane.update();
            }
        };
        bgResourceDisplayUpdateThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 500);


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
        tabLayout.addOnTabSelectedListener(this);

        // View Pager will persistently store every page in the background
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());

        // set the first tab as active on startup
        tabFragmentPager.setActive(0);
        viewPager.setCurrentItem(0, true);

        // load resources pane
        resourcesPane = new ResourcesPaneFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.resourcepane_placeholder, resourcesPane);
        ft.commit();
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

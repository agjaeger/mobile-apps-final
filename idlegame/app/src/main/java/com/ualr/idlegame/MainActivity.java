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
import com.ualr.idlegame.fragments.CongratsFragment;
import com.ualr.idlegame.fragments.ResourcesPaneFragment;
import com.ualr.idlegame.fragments.TabFragmentPager;
import com.ualr.idlegame.fragments.interfaces.TabFragment;
import com.ualr.idlegame.fragments.tabs.ArmyTabFragment;
import com.ualr.idlegame.fragments.tabs.MapTabFragment;
import com.ualr.idlegame.fragments.tabs.RecruitTabFragment;
import com.ualr.idlegame.fragments.tabs.UpgradesTabFragment;
import com.ualr.idlegame.tasks.CounterTask;
import com.ualr.idlegame.viewmodel.AppDataViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private CounterTask bgTickThread = new CounterTask();
    private CounterTask bgAutosaveThread = new CounterTask();
    private CounterTask bgResourceUpdateThread = new CounterTask();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabFragmentPager tabFragmentPager;
    private ResourcesPaneFragment resourcesPane;

    private AppDataViewModel viewModel;
    private boolean checkGameFinished = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load data
        DatabaseManager.getInstance().open(getApplicationContext());
        loadViewModel();

        // launch background threads
        launchBGThreads();

        // initialize UI
        setupTabs();
        setupViewPager();
        loadResourcePane();
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

    private void loadViewModel () {
        // get App Data View Model
        viewModel = ViewModelProviders.of(this).get(AppDataViewModel.class);

        // clear old data
        viewModel.resetData();

        // register resources
        String[] resourceNames = getResources().getStringArray(R.array.resource_names);
        for (String resourceName : resourceNames) {
            viewModel.registerResource(resourceName);

        }

        // register resources
        String[] armyUnits = getResources().getStringArray(R.array.army_units);
        for (String unitname : armyUnits) {
            viewModel.registerResource(unitname);
        }

        // restore data from database
        viewModel.restoreDataFromDatabase();
    }

    private void launchBGThreads () {
        // -----------------------------------------------------------------------------------------
        // launch background thread to calculate ticks
        bgTickThread.onCountListener = new CounterTask.OnCountListener() {
            @Override
            public void onCount () {
                Object[] fragments = tabFragmentPager.getFragments();

                for (Object f : fragments) {
                    ((TabFragment)f).onTick();
                }

                UpgradesTabFragment upgradesTabFragment = (UpgradesTabFragment) tabFragmentPager.getItem(2);
                    if (upgradesTabFragment != null) {
                        List<String> upgrades = upgradesTabFragment.getUpgrades();
                        for(int x = 0; x < upgradesTabFragment.getUpgradeSize(); x++){
                            if (upgrades.get(x).equals("Double-Time")) {
                                for (Object f : fragments) {
                                    ((TabFragment)f).onTick();
                               }
                        }

                            if (upgrades.get(x).equals("Power Up")){
                                if ( viewModel.getResourceValue(getResources().getString(R.string.money_resource)) <= 1000){
                                    viewModel.setResourceValue(getResources().getString(R.string.money_resource), 1001);
                                }
                                if ( viewModel.getResourceValue(getResources().getString(R.string.power_resource)) <= 1000){
                                    viewModel.setResourceValue(getResources().getString(R.string.power_resource), 1001);
                                }

                            }
                    }
                }
            }
        };
        bgTickThread.executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR,
                getResources().getInteger(R.integer.bg_tick_time_ms)
        );

        // -----------------------------------------------------------------------------------------
        // launch autosave thread
        bgAutosaveThread.onCountListener = new CounterTask.OnCountListener() {
            @Override
            public void onCount () {
                Context context = getApplicationContext();
                CharSequence text = "Autosaving!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                viewModel.saveDataToDB();
            }
        };
        bgAutosaveThread.executeOnExecutor(
            AsyncTask.THREAD_POOL_EXECUTOR,
            getResources().getInteger(R.integer.bg_autosave_time_ms)
        );


        // -----------------------------------------------------------------------------------------
        // launch thread to periodically update the resource pane
        bgResourceUpdateThread.onCountListener = new CounterTask.OnCountListener() {
            @Override
            public void onCount () {
                resourcesPane.update();

                // unlock upgrades
                ((RecruitTabFragment) tabFragmentPager.getItem(0)).tryUnlockAll(
                    viewModel.getResourceValue(getResources().getString(R.string.money_resource))
                );

                ((ArmyTabFragment) tabFragmentPager.getItem(1)).tryUnlockAll(
                    viewModel.getResourceValue(getResources().getString(R.string.power_resource))
                );

                ((UpgradesTabFragment) tabFragmentPager.getItem(2)).tryUnlockAll(
                        viewModel.getResourceValue(getResources().getString(R.string.money_resource))
                );

                ((MapTabFragment) tabFragmentPager.getItem(3)).tryUnlockAll(
                    viewModel.getResourceValue(getResources().getString(R.string.power_resource))
                );


                if (checkGameFinished) {
                    if (((MapTabFragment) tabFragmentPager.getItem(3)).allUnlocked()) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.activityRoot, new CongratsFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        checkGameFinished = false;
                    }
                }
            }
        };
        bgResourceUpdateThread.executeOnExecutor(
            AsyncTask.THREAD_POOL_EXECUTOR,
            getResources().getInteger(R.integer.bg_resource_update_time_ms)
        );

    }

    private void setupTabs () {
        tabLayout = findViewById(R.id.tablayout_parent);

        String[] tabNames = getResources().getStringArray(R.array.tab_names);
        for (String tabName : tabNames) {
            tabLayout.addTab(tabLayout.newTab().setText(tabName));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void setupViewPager () {
        // find and setup viewpager
        viewPager = findViewById(R.id.viewPager);
        tabFragmentPager = new TabFragmentPager (getSupportFragmentManager());
        viewPager.setAdapter(tabFragmentPager);
        tabLayout.addOnTabSelectedListener(this);

        // View Pager will persistently store every page in the background
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());

        // set the first tab as active on startup
        tabFragmentPager.setActive(getResources().getInteger(R.integer.default_active_tab));
        viewPager.setCurrentItem(getResources().getInteger(R.integer.default_active_tab), true);
    }

    private void loadResourcePane () {
        resourcesPane = new ResourcesPaneFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.resourcepane_placeholder, resourcesPane);
        ft.commit();
    }
}

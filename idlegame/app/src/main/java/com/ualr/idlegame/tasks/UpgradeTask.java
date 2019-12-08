package com.ualr.idlegame.tasks;

import android.os.AsyncTask;
import android.view.View;

public class UpgradeTask extends AsyncTask<Integer, View, Void> {

    public UpgradeTask.OnUpgradeListener onUpgradeListener;
    public interface  OnUpgradeListener {
        void onUpgrade();
    }


    protected Void doInBackground(Integer... params){

        try {
            while (true) {
                Thread.sleep(params[0]);
                publishProgress();
                if (isCancelled()) break;
            }
        } catch (Exception e) {}

        return null;
    }

}

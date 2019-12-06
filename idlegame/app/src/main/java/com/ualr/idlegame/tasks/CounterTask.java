package com.ualr.idlegame.tasks;

import android.os.AsyncTask;
import android.view.View;

public class CounterTask extends AsyncTask<Integer, View, Void> {

    public CounterTask.OnCountListener onCountListener;
    public interface OnCountListener {
        void onCount ();
    }

    protected Void doInBackground(Integer... params) {
        try {
            while (true) {
                Thread.sleep(params[0]);
                publishProgress();
                if (isCancelled()) break;
            }
        } catch (Exception e) {}

        return null;
    }

    protected void onProgressUpdate(View... progress) {
        onCountListener.onCount();
    }
}

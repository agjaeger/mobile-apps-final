package com.ualr.idlegame.tasks;

import android.os.AsyncTask;
import android.view.View;

public class UpdateProgressBarsTask extends AsyncTask<Void, View, Void> {

    public UpdateProgressBarsTask.OnTickListener onTickListener;
    public interface OnTickListener {
        void onTick ();
    }

    protected Void doInBackground(Void... params) {
        try {
            while (true) {
                publishProgress();
                Thread.sleep(10);
                if (isCancelled()) break;
            }
        } catch (Exception e) {}

        return null;
    }

    protected void onProgressUpdate(View... progress) {
        onTickListener.onTick();
    }
}

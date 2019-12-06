package com.ualr.idlegame.tasks;

import android.os.AsyncTask;
import android.view.View;

public class AutoSaveTask extends AsyncTask<Void, View, Void> {

    public AutoSaveTask.OnAutoSaveListener onAutoSaveListener;
    public interface OnAutoSaveListener {
        void onAutosave ();
    }

    protected Void doInBackground(Void... params) {
        try {
            while (true) {
                publishProgress();
                Thread.sleep(5000);
                if (isCancelled()) break;
            }
        } catch (Exception e) {}

        return null;
    }

    protected void onProgressUpdate(View... progress) {
        onAutoSaveListener.onAutosave();
    }
}

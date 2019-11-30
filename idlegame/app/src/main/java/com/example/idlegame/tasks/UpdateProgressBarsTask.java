package com.example.idlegame.tasks;

import android.os.AsyncTask;
import android.view.View;

import com.example.idlegame.R;

public class UpdateProgressBarsTask extends AsyncTask<Void, View, Void> {
    protected Void doInBackground(Void... params) {
        try {
            while (true) {
                publishProgress();
                Thread.sleep(1000);
                if (isCancelled()) break;
            }
        } catch (Exception e) {}

        return null;
    }

    protected void onProgressUpdate(View... progress) {
        System.out.println("Tick");
    }
}

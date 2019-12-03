package com.ualr.idlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ualr.idlegame.tasks.UpdateProgressBarsTask;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class MainActivity extends AppCompatActivity {

    private UpdateProgressBarsTask bgTickThread = new UpdateProgressBarsTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgTickThread.onTickListener = new UpdateProgressBarsTask.OnTickInterface() {
            @Override
            public void onTick() {
                System.out.println("TETS");
            }
        };

        bgTickThread.execute(); // launch background thread to calculate ticks

        View progressbar = findViewById(R.id.id_progressbar_2);
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
        }
    }
}

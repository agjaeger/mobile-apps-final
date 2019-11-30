package com.example.idlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.idlegame.tasks.UpdateProgressBarsTask;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View progressbar = findViewById(R.id.id_progressbar_2);
        progressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Hello World");
            }
        });

        // new UpdateProgressBarsTask().execute();


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
        };

    }
}

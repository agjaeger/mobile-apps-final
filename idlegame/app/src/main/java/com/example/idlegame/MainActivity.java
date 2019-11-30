package com.example.idlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.idlegame.tasks.UpdateProgressBarsTask;

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

        new UpdateProgressBarsTask().execute();

    }
}

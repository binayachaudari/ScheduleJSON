package com.example.binaya.json_schedule;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    JSONParser parser;
    String URL = "https://binayachaudari.github.io/ScheduleFile/Schedule.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parser = new JSONParser();
        parser.getJson(URL);
    }
}

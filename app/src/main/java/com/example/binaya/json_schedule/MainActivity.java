package com.example.binaya.json_schedule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    JSONParser parser;
    ProgressDialog progressDialog;
    String Data;
    String URL = "https://binayachaudari.github.io/ScheduleFile/Schedule.json";



    String Subject;
    String Lecturer;
    String Day;
    String Start;
    String End;
    String Dept;
    String Year;
    String Sem;

    String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetSchedule().execute();
    }


    /**
     * Async task class to get JSON by making JSONParser call
     */
    private class GetSchedule extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Updating Schedule!");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            parser = new JSONParser();
            Data = parser.getJson(URL);

            if(Data != null) {
                try {
                    JSONObject jsonObject = new JSONObject(Data);
                    version = jsonObject.getString("version");
                    Log.d(TAG, "doInBackground: "+version);
                    JSONArray schedule = jsonObject.getJSONArray("schedule");

                    for(int i=0; i<schedule.length(); i++){
                        JSONObject eachObject = schedule.getJSONObject(i);
                        Subject = eachObject.getString("subject");
                        Lecturer = eachObject.getString("lecturer");
                        Day = eachObject.getString("day");
                        Start =  eachObject.getString("start");
                        End = eachObject.getString("end");
                        Dept = eachObject.getString("dept");
                        Year = eachObject.getString("year");
                        Sem = eachObject.getString("sem");

                        Log.d(TAG, "doInBackground: " +Subject);
                        Log.d(TAG, "doInBackground: " +Lecturer);
                        Log.d(TAG, "doInBackground: " +Day);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();

        }


    }
}

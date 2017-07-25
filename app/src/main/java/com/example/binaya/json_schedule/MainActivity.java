package com.example.binaya.json_schedule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    DatabaseHelper myDB;

    GetSchedule gd;

    JSONParser parser;
    ProgressDialog progressDialog;
    String Data;
    String[] URL = {
            "https://binayachaudari.github.io/KUScheduleFiles/IIYIIS.json",
            "https://binayachaudari.github.io/KUScheduleFiles/IIIYIIS.json",
            "https://binayachaudari.github.io/KUScheduleFiles/IVYIIS.json"
    };

    String Subject;
    String Lecturer;
    String Day;
    String Start;
    String End;
    String Dept;
    String Year;
    String Sem;

    String version;

    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetSchedule().execute();

        display = (TextView) findViewById(R.id.display);

        myDB = new DatabaseHelper(this);

        StringBuffer sb = new StringBuffer();


    }


    /**
     * Async task class to get JSON by making JSONParser call
     */
    public class GetSchedule extends AsyncTask<String,Void,String>{
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
            for (int j = 0; j<URL.length;j++) {
                Data = parser.getJson(URL[j]);
                if (Data != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(Data);
                        version = jsonObject.getString("version");
                        Log.d(TAG, "doInBackground: " + version);
                        JSONArray schedule = jsonObject.getJSONArray("schedule");

                        for (int i = 0; i < schedule.length(); i++) {
                            JSONObject eachObject = schedule.getJSONObject(i);
                            Subject = eachObject.getString("subject");
                            Lecturer = eachObject.getString("lecturer");
                            Day = eachObject.getString("day");
                            Start = eachObject.getString("start");
                            End = eachObject.getString("end");
                            Dept = eachObject.getString("dept");
                            Year = eachObject.getString("year");
                            Sem = eachObject.getString("sem");
                            myDB.insertData(Subject, Lecturer, Day, Start, End, Dept, Year, Sem);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

            StringBuffer sb = new StringBuffer();

            Cursor result = myDB.DisplayData();
            if (result!= null && result.getCount()>0){
                while(result.moveToNext()){
                    sb.append("Subject: "+result.getString(0)+"\n");
                    sb.append("Lecturer: "+result.getString(1)+"\n");
                    sb.append("Time: "+result.getString(3)+" - "+result.getString(4)+"\n");
                }
            }
            display.setText(sb.toString());
        }

    }


}

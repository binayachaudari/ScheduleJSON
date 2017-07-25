package com.example.binaya.json_schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Binaya on 7/14/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String TAG = "DatabaseHelper";
    //final: a variable who's value can't be modified after it is declared
    //static: no instance necessary to use it
    public static final String DATABASE_NAME = "Syllabus.db";
    public static final String TABLE_NAME = "Schedule";

    public static final String SUBJECT = "SUBJECT";
    public static final String LECTURER = "LECTURER";
    public static final String DEPT = "DEPARTMENT";
    public static final String START = "START";
    public static final String END = "END";
    public static final String DAY = "DAY";
    public static final String YEAR = "YEAR";
    public static final String SEM = "SEMESTER";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(SUBJECT TEXT, LECTURER TEXT, DEPARTMENT TEXT, START TEXT, END TEXT, DAY TEXT, YEAR TEXT, SEMESTER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " +TABLE_NAME);
        onCreate(db);

    }

    public void insertData(String Sub, String lec, String day, String start, String end, String dept, String year, String sem){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SUBJECT, Sub);
        contentValues.put(LECTURER, lec);
        contentValues.put(DAY, day);
        contentValues.put(START, start);
        contentValues.put(END, end);
        contentValues.put(DEPT, dept);
        contentValues.put(YEAR, year);
        contentValues.put(SEM, sem);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor DisplayData(){
        SQLiteDatabase db = getReadableDatabase();
        String Query = "SELECT * FROM " + TABLE_NAME +" WHERE DAY = 'Monday' AND DEPARTMENT = 'CS' AND YEAR = '3rd' AND SEMESTER = '2nd'";
        Cursor result = db.rawQuery(Query,null);
        return result;
    }

}

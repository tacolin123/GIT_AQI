package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SqliteHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "database_aqi";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = SqliteHelper.class.getSimpleName();

    public SqliteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqliteTable.DB_CREATE_AQI_CONFIG);
        db.execSQL(SqliteTable.DB_CREATE_AQI_CONTENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SqliteTable.TABLE_AQI_CONTENTS);
        db.execSQL("DROP TABLE IF EXISTS " + SqliteTable.TABLE_AQI_CONFIG);
        onCreate(db);
    }

    /**
     * This method check if the publishtime existing or not, and it will return boolean value.
     * @param publishtime carrier the publishtime of aqi_config.
     * @return true/false.
     **/
    public boolean checkCPTime(String publishtime){

        SQLiteDatabase db = this.getReadableDatabase();

        //String selection = SqliteTable.COL_AQI_CONFIG_PUBLISHTIME + " =? ";

        // validation
        String selection = SqliteTable.COL_AQI_CONFIG_PUBLISHTIME + " =? And " + SqliteTable.COL_AQI_CONFIG_CONFIRM + " =?";
        Log.d(this.getClass().getName(), "select : " + selection);
        //arguments
        String[] args = {publishtime, "1"}; // 0:無資料, 1: 已下載, 2:有刪除過資料料

        Cursor cursor = db.query(SqliteTable.TABLE_AQI_CONTENTS, null,
                selection, args, null, null, null);
        int count = cursor.getCount();
        Log.d(TAG, "cursor count : " + count);
        cursor.close();
        if (count > 0){
            Log.d(TAG, "return true");
            return true;
        }

        return false;
/*
        //columns to fetch
        String[] columns = {};

        SQLiteDatabase db = this.getReadableDatabase();
        // validation
        //String selection = SqliteTable.COL_AQI_CONFIG_PUBLISHTIME + " =? And " + SqliteTable.COL_AQI_CONFIG_CONFIRM + " =?";

        //arguments
        //String[] args = {"publishtime, "1"}; // 0:無資料, 1: 已下載, 2:有刪除過資料

        // validation
        String selection = SqliteTable.COL_AQI_CONFIG_PUBLISHTIME + " =? ";

        //arguments
        String[] args = {publishtime}; // 0:無資料, 1: 已下載, 2:有刪除過資料

        //query to user table
        Cursor cursor = db.query(SqliteTable.TABLE_AQI_CONFIG,
                null, //return
                selection, //where clause
                args, // value of the clause
                null,
                null,
                null);

        int count = cursor.getCount();
        Log.d(TAG, "cursor count : " + count);
        cursor.close();
        db.close();
        if (count > 0){
            Log.d(TAG, "return true");
            return true;
        }

        return false;
        */
    }

    public boolean insertData(String table, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(table, null, values);
        if (result == -1){
            Log.d(TAG, "failed to save data!");
            return false;
        }else{
            Log.d(TAG, "insert data successful");
            return true;
        }
    }

    /**
     * This method edit the user details and it return int value.
     * @param table this param provide table that you want to update.
     * @param values values param provide col value.
     * @param email to identify the you use want to edit.
     * @return int value.
     **/
    public int updateData(String table, ContentValues values, String whereClause, String[] whereArgs){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(table, values,
                whereClause, whereArgs);
    }


    /**
     * This method delete aqi contents permanently, and it returns boolean value.
     * @param datetime, sitename, country
     * @return true/false.
     **/
    public boolean deleteAQIContents(String publishtime, String sitename, String country){
        String args[] ={ publishtime, sitename, country};
        //String clause = "[" + SqliteTable.COL_AQI_CONTENTS_PUBLISHTIME + "]=?"
        //              + " AND [" + SqliteTable.COL_AQI_CONTENTS_SITENAME + "]=?"
        //              + " AND [" + SqliteTable.COL_AQI_CONTENTS_COUNTY + "]=?";
        String clause = SqliteTable.COL_AQI_CONTENTS_PUBLISHTIME + " =? "
                + " AND " + SqliteTable.COL_AQI_CONTENTS_SITENAME + " =? "
                + " AND " + SqliteTable.COL_AQI_CONTENTS_COUNTY + " =? ";
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(SqliteTable.TABLE_AQI_CONTENTS, clause, args) > 0;
    }

    public AQIContents getAQIContents(String sitename, String county, String publishtime) {

        AQIContents data;
        SQLiteDatabase db = this.getReadableDatabase();
        /*
        String[] columns={
                SqliteTable.COL_USER_ID,
                SqliteTable.COL_USER_FNAME,
                SqliteTable.COL_USER_LNAME,
                SqliteTable.COL_USER_ADDRESS,
                SqliteTable.COL_USER_PHONE,
                SqliteTable.COL_USER_EMAIL,
                SqliteTable.COL_USER_PASSWORD
        };
        */

        String selection = SqliteTable.COL_AQI_CONTENTS_SITENAME + " =? " +
                "AND " + SqliteTable.COL_AQI_CONTENTS_COUNTY + " =? " +
                "AND " + SqliteTable.COL_AQI_CONTENTS_PUBLISHTIME + " =? ";
        Log.d(this.getClass().getName(), "select : " + selection);

        String[] args={sitename, county, publishtime};

        Cursor cursor = db.query(SqliteTable.TABLE_AQI_CONTENTS, null,
                selection, args, null, null, null);
        int count = cursor.getCount();
        Log.d(TAG, "cursor count : " + count);

        if (cursor != null && cursor.moveToFirst()){
            data = new AQIContents();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                data.setContents(i, cursor.getString(i));
            }
            cursor.close();
            return data;
        }
        cursor.close();
        return null;
    }

    public List<Map<String,String>> getPublishTimeAQIContents(String publishtime) {

        AQIContents data;
        List<Map<String,String>>  aqiArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = SqliteTable.COL_AQI_CONTENTS_PUBLISHTIME + " =? " ;
        Log.d(this.getClass().getName(), "select : " + selection);

        String[] args={publishtime};

        Cursor cursor = db.query(SqliteTable.TABLE_AQI_CONTENTS, null,
                selection, args, null, null, null);
        //int count = cursor.getCount();
        Log.d(TAG, "cursor count : " + cursor.getCount());

        if (cursor != null && cursor.getCount() > 0){
            data = new AQIContents();
            cursor.moveToFirst();    // 移到第 1 筆資料
            do{        // 逐筆讀出資料
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    data.setContents(i, cursor.getString(i));
                }
                aqiArrayList.add(data.getHashMap());
                if (cursor.isFirst())
                    aqiArrayList.add(data.getHashMap());;
            } while(cursor.moveToNext());    // 有一下筆就繼續迴圈

            if (aqiArrayList.size() > 1)
                aqiArrayList.remove(aqiArrayList.size() - 1);
        }
        cursor.close();
        return aqiArrayList;
    }


}

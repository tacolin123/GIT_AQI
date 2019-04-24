package com.example.myapplication.data;

import android.content.ContentValues;

import java.util.HashMap;
import java.util.Map;

import static com.example.myapplication.data.SqliteTable.getAqiContentsColName;

public class AQIContents {

    private String id = "";
    private String sitename = "";
    private String county = "";
    private String aqi = "";
    private String pollutant = "";
    private String status = "";
    private String so2 = "";
    private String co = "";
    private String co_8hr = "";
    private String o3 = "";
    private String o3_8hr = "";
    private String pm10 = "";
    private String pm25 = "";
    private String no2 = "";
    private String nox = "";
    private String no = "";
    private String windspeed = "";
    private String winddirec = "";
    private String publishtime = "";
    private String pm25_avg = "";
    private String pm10_avg = "";
    private String so2_avg = "";
    private String longitude = "";
    private String latitude = "";
    private String confirm = "";

    private String[] colname = null;

    public AQIContents(){
        colname = new String[SqliteTable.TABLE_AQI_CONTENTS_COL_COUNT];
    }

    public AQIContents(int rolCount) {
        colname = new String[rolCount];
    }

    public String getSitename()
    {
        return  this.sitename;
    }

    public String getPublishtime()
    {
        return  this.publishtime;
    }

    public String getCounty()
    {
        return  this.county;
    }

    public void setColName(int rolIndex, String rolName) {
        if (colname == null)
            return;

        colname[rolIndex] = rolName;
    }

    public String[] getColName() {
        return colname;
    }

    public void setContents(int index, String value)
    {
        switch (index)
        {
            case 0:
                id = value;
                break;
            case 1:
                sitename = value;
                break;
            case 2:
                county = value;
                break;
            case 3:
                aqi = value;
                break;
            case 4:
                pollutant = value;
                break;
            case 5:
                status = value;
                break;
            case 6:
                so2 = value;
                break;
            case 7:
                co = value;
                break;
            case 8:
                co_8hr = value;
                break;
            case 9:
                o3 = value;
                break;
            case 10:
                o3_8hr = value;
                break;
            case 11:
                pm10 = value;
                break;
            case 12:
                pm25 = value;
                break;
            case 13:
                no2 = value;
                break;
            case 14:
                nox = value;
                break;
            case 15:
                no = value;
                break;
            case 16:
                windspeed = value;
                break;
            case 17:
                winddirec = value;
                break;
            case 18:
                publishtime = value;
                break;
            case 19:
                pm25_avg = value;
                break;
            case 20:
                pm10_avg = value;
                break;
            case 21:
                so2_avg = value;
                break;
            case 22:
                longitude = value;
                break;
            case 23:
                latitude = value;
                break;
            case 24:
                confirm = value;
                break;
            default:

        }
    }

    public String getContents(int index)
    {
        switch (index)
        {
            case 0:
                return id;
            case 1:
                return sitename;
            case 2:
                return county;
            case 3:
                return aqi;
            case 4:
                return pollutant;
            case 5:
                return status;
            case 6:
                return so2;
            case 7:
                return co;
            case 8:
                return co_8hr;
            case 9:
                return o3;
            case 10:
                return o3_8hr;
            case 11:
                return pm10;
            case 12:
                return pm25;
            case 13:
                return no2;
            case 14:
                return nox;
            case 15:
                return no;
            case 16:
                return windspeed;
            case 17:
                return winddirec;
            case 18:
                return publishtime;
            case 19:
                return pm25_avg;
            case 20:
                return pm10_avg;
            case 21:
                return so2_avg;
            case 22:
                return longitude;
            case 23:
                return latitude;
            case 24:
                return confirm;
            default:
                return "";

        }
    }

    public ContentValues getContentValues(Map<String,String> map) {
        ContentValues cv;
        if (map == null) return null;
        cv = new ContentValues();
        for (int i = 0; i < map.size(); i++)
        {
            String key = SqliteTable.getAqiContentsColName(i);
            setContents(i + 1, map.get(key));
            cv.put(key, map.get(key));
        }

        return cv;
    }

    public Map<String,String> getHashMap()
    {
        Map<String,String> map = new HashMap<>();

        for (int i = 1; i < 24; i++)
        {
            String key = SqliteTable.getAqiContentsColName(i -1);
            map.put(key, getContents(i));
        }
        return map;
    }


}

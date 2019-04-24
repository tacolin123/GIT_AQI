package com.example.myapplication.data;

public class SqliteTable {

    /**
     * This class provide tables for aqi
     **/

    public static final String TABLE_AQI_CONFIG = "table_aqi_config";
    public static final String COL_AQI_CONFIG_ID = "id";
    public static final String COL_AQI_CONFIG_PUBLISHTIME = "publishtime";
    public static final String COL_AQI_CONFIG_CONFIRM = "confirm";


    public static final String TABLE_AQI_CONTENTS= "table_aqi_contents";
    public static final int TABLE_AQI_CONTENTS_COL_COUNT = 24;
    public static final String COL_AQI_CONTENTS_ID = "id";
    public static final String COL_AQI_CONTENTS_SITENAME = "sitename";
    public static final String COL_AQI_CONTENTS_COUNTY = "county";
    public static final String COL_AQI_CONTENTS_AQI = "aqi";
    public static final String COL_AQI_CONTENTS_POLLUTANT = "pollutant";
    public static final String COL_AQI_CONTENTS_STATUS = "status";
    public static final String COL_AQI_CONTENTS_SO2 = "so2";
    public static final String COL_AQI_CONTENTS_CO = "co";
    public static final String COL_AQI_CONTENTS_CO_8HR = "co_8hr";
    public static final String COL_AQI_CONTENTS_O3 = "o3";
    public static final String COL_AQI_CONTENTS_O3_8HR = "o3_8hr";
    public static final String COL_AQI_CONTENTS_PM10 = "pm10";
    public static final String COL_AQI_CONTENTS_PM25 = "pm25";
    public static final String COL_AQI_CONTENTS_NO2 = "no2";
    public static final String COL_AQI_CONTENTS_NOX = "nox";
    public static final String COL_AQI_CONTENTS_NO = "no";
    public static final String COL_AQI_CONTENTS_WINDSPEED = "windspeed";
    public static final String COL_AQI_CONTENTS_WINDDIREC = "winddirec";
    public static final String COL_AQI_CONTENTS_PUBLISHTIME = "publishtime";
    public static final String COL_AQI_CONTENTS_PM25_AVG = "pm25_avg";
    public static final String COL_AQI_CONTENTS_PM10_AVG = "pm10_avg";
    public static final String COL_AQI_CONTENTS_SO2_AVG = "so2_avg";
    public static final String COL_AQI_CONTENTS_LONGITUDE = "longitude";
    public static final String COL_AQI_CONTENTS_LATIUDE = "latitude";
    public static final String COL_AQI_CONTENTS_CONFIRM = "confirm";

    public static final String DB_CREATE_AQI_CONFIG = "CREATE TABLE " + TABLE_AQI_CONFIG + "(" + COL_AQI_CONFIG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_AQI_CONFIG_PUBLISHTIME + " TEXT, "
            + COL_AQI_CONFIG_CONFIRM + " INTEGER )";

    public static final String DB_CREATE_AQI_CONTENTS = "CREATE TABLE " + TABLE_AQI_CONTENTS + "(" + COL_AQI_CONTENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_AQI_CONTENTS_SITENAME + " TEXT, "
            + COL_AQI_CONTENTS_COUNTY + " TEXT, "
            + COL_AQI_CONTENTS_AQI + " TEXT, "
            + COL_AQI_CONTENTS_POLLUTANT + " TEXT, "
            + COL_AQI_CONTENTS_STATUS + " TEXT, "
            + COL_AQI_CONTENTS_SO2 + " TEXT, "
            + COL_AQI_CONTENTS_CO + " TEXT, "
            + COL_AQI_CONTENTS_CO_8HR + " TEXT, "
            + COL_AQI_CONTENTS_O3 + " TEXT, "
            + COL_AQI_CONTENTS_O3_8HR + " TEXT, "
            + COL_AQI_CONTENTS_PM10 + " TEXT, "
            + COL_AQI_CONTENTS_PM25 + " TEXT, "
            + COL_AQI_CONTENTS_NO2 + " TEXT, "
            + COL_AQI_CONTENTS_NOX + " TEXT, "
            + COL_AQI_CONTENTS_NO + " TEXT, "
            + COL_AQI_CONTENTS_WINDSPEED + " TEXT, "
            + COL_AQI_CONTENTS_WINDDIREC + " TEXT, "
            + COL_AQI_CONTENTS_PUBLISHTIME + " TEXT, "
            + COL_AQI_CONTENTS_PM25_AVG + " TEXT, "
            + COL_AQI_CONTENTS_PM10_AVG + " TEXT, "
            + COL_AQI_CONTENTS_SO2_AVG + " TEXT, "
            + COL_AQI_CONTENTS_LONGITUDE + " TEXT, "
            + COL_AQI_CONTENTS_LATIUDE + " TEXT, "
            + COL_AQI_CONTENTS_CONFIRM + " INTEGER )";

    public static final String getAqiContentsColName(int index)
    {
        switch (index)
        {
            case 0:
                return COL_AQI_CONTENTS_SITENAME;
            //break;
            case 1:
                return COL_AQI_CONTENTS_COUNTY;
            //break;
            case 2:
                return COL_AQI_CONTENTS_AQI;
            //break;
            case 3:
                return COL_AQI_CONTENTS_POLLUTANT;
            //break;
            case 4:
                return COL_AQI_CONTENTS_STATUS;
            //break;
            case 5:
                return COL_AQI_CONTENTS_SO2;
            //break;
            case 6:
                return COL_AQI_CONTENTS_CO;
            //break;
            case 7:
                return COL_AQI_CONTENTS_CO_8HR;
            //break;
            case 8:
                return COL_AQI_CONTENTS_O3;
            //break;
            case 9:
                return COL_AQI_CONTENTS_O3_8HR;
            //break;
            case 10:
                return COL_AQI_CONTENTS_PM10;
            //break;
            case 11:
                return COL_AQI_CONTENTS_PM25;
            //break;
            case 12:
                return COL_AQI_CONTENTS_NO2;
            //break;
            case 13:
                return COL_AQI_CONTENTS_NOX;
            //break;
            case 14:
                return COL_AQI_CONTENTS_NO;
            //break;
            case 15:
                return COL_AQI_CONTENTS_WINDSPEED;
            //break;
            case 16:
                return COL_AQI_CONTENTS_WINDDIREC;
            //break;
            case 17:
                return COL_AQI_CONTENTS_PUBLISHTIME;
            //break;
            case 18:
                return COL_AQI_CONTENTS_PM25_AVG;
            //break;
            case 19:
                return COL_AQI_CONTENTS_PM10_AVG;
            //break;
            case 20:
                return COL_AQI_CONTENTS_SO2_AVG;
            //break;
            case 21:
                return COL_AQI_CONTENTS_LONGITUDE;
            //break;
            case 22:
                return COL_AQI_CONTENTS_LATIUDE;
            //break;
            case 23:
                return COL_AQI_CONTENTS_CONFIRM;
            //break;
            default:
                return "";
        }
    }
}

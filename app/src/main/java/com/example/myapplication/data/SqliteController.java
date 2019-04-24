package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;
import java.util.Map;

public class SqliteController {
    private SqliteHelper sqliteHelper;
    Context context;

    public SqliteController(Context mContext){
        this.context = mContext;
        sqliteHelper = new SqliteHelper(context);
    }

    public boolean checkCPTime(String publishTime){

        return sqliteHelper.checkCPTime(publishTime);
    }

    public boolean updateAQIData(List<Map<String,String>> list)
    {
        return true;
    }

    public List<Map<String,String>> getPublishTimeAQIContents(String publishtime)
    {
        return sqliteHelper.getPublishTimeAQIContents(publishtime);
    }

    public AQIContents getAQIContents(String sitename, String county, String publishtime)
    {
        return sqliteHelper.getAQIContents(sitename, county, publishtime);
    }

    public boolean setAQIContents(ContentValues cv)
    {
        return sqliteHelper.insertData(SqliteTable.TABLE_AQI_CONTENTS, cv);
    }

    public boolean setAQIConfig(ContentValues cv)
    {
        return sqliteHelper.insertData(SqliteTable.TABLE_AQI_CONFIG, cv);
    }

    public boolean deleteAQIContents(Map<String,String> map)
    {
        return sqliteHelper.deleteAQIContents(map.get(SqliteTable.COL_AQI_CONTENTS_PUBLISHTIME),
                map.get(SqliteTable.COL_AQI_CONTENTS_SITENAME),
                map.get(SqliteTable.COL_AQI_CONTENTS_COUNTY));

    }
}

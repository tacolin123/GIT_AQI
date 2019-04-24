package com.example.myapplication.aqi;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.myapplication.IMainActivity;
import com.example.myapplication.data.AQIContents;
import com.example.myapplication.data.SqliteController;
import com.example.myapplication.data.SqliteTable;
import com.example.myapplication.utils.JsoupCallbackListener;
import com.example.myapplication.utils.JsoupUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AQIMode implements IAQIMode{
    IMainActivity iMainActivityView;
    SqliteController sqlctrl;// = new SqliteController((Context)iMainActivityView);

    public AQIMode(IMainActivity iMainActivity) {
        this.iMainActivityView = iMainActivity;
    }

    @Override
    public void getAQI(String url, final OnAQIListener onAQIListener) {

        JsoupUtil.sendHttpRequest(url, new JsoupCallbackListener() {
            @Override
            public void onFinish(List<Map<String,String>> list) {
                //String dailquote = AQIJsoup.handleDQXmlResponse(response);
                sqlctrl = new SqliteController((Context)iMainActivityView);
                onAQIListener.onSuccess(checkData(list));
            }

            @Override
            public void onError(Exception e) {
                onAQIListener.onError();
            }
        });
    }

    private List<Map<String,String>> checkData(List<Map<String,String>> list)
    {
        if (list.size() <= 1)
            return list;

        // 190424_t+ 先檢查 TABLE_AQI_CONFIG 中是否有資料
        // COL_AQI_CONFIG_CONFIRM > 0 已下載過資料，載入資料庫資料

        boolean check = sqlctrl.checkCPTime(list.get(1).get("publishtime"));

        if (check) // 已下載過資料
        {
            return loadAQIData(list.get(1).get("publishtime"));
        }

        return saveAQIData(list);

    }

    // 載入資料庫資料
    private List<Map<String,String>> loadAQIData(String publishtime)
    {
        return sqlctrl.getPublishTimeAQIContents(publishtime);
    }

    // 存入新資料
    private List<Map<String,String>> saveAQIData(List<Map<String,String>> list)
    {
        //List<ContentValues> listdata = new ArrayList<>();
        AQIContents aqics = new AQIContents();

        for (int i = 1; i < list.size(); i++)
        {
            ContentValues cv = aqics.getContentValues(list.get(i));
            Log.d(this.getClass().getName(), cv.get("sitename") + "," + cv.get("county"));

            AQIContents temp = sqlctrl.getAQIContents(aqics.getSitename(), aqics.getCounty(), aqics.getPublishtime());
            //if (temp == null) listdata.add(cv);

            if (temp == null) {
                if (sqlctrl.setAQIContents(cv) == false)
                    return null;
            }
        }

        ContentValues config = new ContentValues();
        config.put(SqliteTable.COL_AQI_CONFIG_PUBLISHTIME, list.get(1).get("publishtime"));
        config.put(SqliteTable.COL_AQI_CONFIG_CONFIRM, 1);
        if (sqlctrl.setAQIContents(config) == false)
            return null; // Config 資料表新增失敗


        return list;
    }
}

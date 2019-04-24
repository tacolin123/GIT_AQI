package com.example.myapplication.aqi;

import android.os.Handler;
import com.example.myapplication.IMainActivity;

import java.util.List;
import java.util.Map;

public class AQIPresenter {
    IMainActivity iMainActivityView;
    IAQIMode iAQIMode;
    Handler handler;


    public AQIPresenter(IMainActivity iMainActivity) {
        this.iMainActivityView = iMainActivity;
        iAQIMode = new AQIMode(iMainActivityView);
        handler = new Handler();
    }

    public void getAQIData(String url) {
        // Presenter控制View的变化
        //iMainActivityView.showDailQuote("Loading .......");
        iMainActivityView.showLoading();

        iAQIMode.getAQI(url, new OnAQIListener() {
            @Override
            public void onSuccess(final List<Map<String,String>> data) {
                // 更新UI
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iMainActivityView.dismissLoading();
                        iMainActivityView.showAQIData(data);
                        //iMainActivityView.showDailQuote(data);
                    }
                });
            }

            @Override
            public void onError() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iMainActivityView.dismissLoading();
                    }
                });
            }
        });
    }
}

package com.example.myapplication.dailquote;

import android.os.Handler;


import com.example.myapplication.IMainActivity;
import com.example.myapplication.dailquote.IDQMode;
import com.example.myapplication.dailquote.DQMode;
import com.example.myapplication.dailquote.OnDQListener;

public class DQPresenter {
    IMainActivity iMainActivityView;
    IDQMode iDQMode;
    Handler handler;


    public DQPresenter(IMainActivity iMainActivity) {
        this.iMainActivityView = iMainActivity;
        iDQMode = new DQMode();
        handler = new Handler();
    }

    public void getDailQuote(String url) {
        // Presenter控制View的变化
        iMainActivityView.showDailQuote("Loading .......");
        iMainActivityView.showLoading();

        iDQMode.getDailQuote(url, new OnDQListener() {
            @Override
            public void onSuccess(final String dailQuote) {
                // 更新UI
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iMainActivityView.dismissLoading();
                        iMainActivityView.showDailQuote(dailQuote);
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

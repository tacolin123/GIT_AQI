package com.example.myapplication.dailquote;

import com.example.myapplication.utils.HttpUtil;
import com.example.myapplication.utils.HttpCallbackListener;

public class DQMode implements IDQMode {
    @Override
    public void getDailQuote(String url, final OnDQListener onDQListener) {
        HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                // 190424_t~ httpUtil 改為jason後直接抓取
                //String dailquote = DQUtil.handleDQXmlResponse(response);
                //onDQListener.onSuccess(dailquote);
                onDQListener.onSuccess(response);
            }

            @Override
            public void onError(Exception e) {
                onDQListener.onError();
                onDQListener.onSuccess("Loading ..... Error");
            }
        });
    }
}
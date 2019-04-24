package com.example.myapplication.aqi;

import com.example.myapplication.utils.JsoupCallbackListener;
import com.example.myapplication.utils.JsoupUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AQIMode implements IAQIMode{
    @Override
    public void getAQI(String url, final OnAQIListener onAQIListener) {

        JsoupUtil.sendHttpRequest(url, new JsoupCallbackListener() {
            @Override
            public void onFinish(List<Map<String,String>> list) {
                //String dailquote = AQIJsoup.handleDQXmlResponse(response);
                onAQIListener.onSuccess(list);
            }

            @Override
            public void onError(Exception e) {
                onAQIListener.onError();
            }
        });
    }
}

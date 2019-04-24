package com.example.myapplication.utils;

import java.util.List;
import java.util.Map;

public interface JsoupCallbackListener {
    //void onFinish(String response);
    void onFinish(List<Map<String,String>> list); // 190424_t~
    void onError(Exception e);
}

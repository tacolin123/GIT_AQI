package com.example.myapplication.aqi;

import java.util.List;
import java.util.Map;

public interface OnAQIListener {
    void onSuccess(List<Map<String,String>> data);
    void onError();
}

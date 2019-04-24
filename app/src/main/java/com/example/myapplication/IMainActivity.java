package com.example.myapplication;

import java.util.List;
import java.util.Map;

public interface IMainActivity {
    void showLoading();
    void dismissLoading();
    void showDailQuote(String dailQuote);
    void showAQIData(List<Map<String,String>> list); // 190424_t+
}

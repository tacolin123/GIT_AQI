package com.example.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.dailquote.DQPresenter;


public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String URL_DAILQUOTE = "http://www.appledaily.com.tw/index/dailyquote/";
    private static final String URL_AQI = "https://opendata.epa.gov.tw/Data/Contents/AQI/";
    //private static final String URL_AQI = "https://opendata.epa.gov.tw/Data/ContentsView/AQI/";

    private Button btnStart;
    private TextView dailyText;
    private TextView publisTime;
    //private ProgressBar progressBarLoading;
    private Dialog loadingDialog;
    private DQPresenter dailyQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dailyText = (TextView)findViewById(R.id.text_dailquote); // 每日一句
        publisTime = (TextView) findViewById(R.id.publistime);  // 空氣品質指標時間
        //progressBarLoading = (ProgressBar) findViewById(R.id.progress_loading);

        btnStart = (Button)findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 取蘋果日報每日一句
                dailyQuote.getDailQuote(URL_DAILQUOTE);
            }
        });

        dailyQuote = new DQPresenter(this);

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle("Loading . . . . . ");
    }

    @Override
    public void showLoading() {
        //progressBarLoading.setVisibility(View.VISIBLE);
        loadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        //progressBarLoading.setVisibility(View.GONE);
        loadingDialog.dismiss();
    }

    @Override
    public void showDailQuote(String dailQuote) {

        dailyText.setText(dailQuote);
    }
}

package com.example.myapplication.utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    public static void sendHttpRequest(final String httpAddress, final HttpCallbackListener httpCallbackListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                /* 190423_t- 模擬器可以動，但是在手機重導後得到status 200 但還是重導的網址 後續再查 改jsoup比較簡單
                boolean redirect = false;
                String redirectAddr = httpAddress;
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) (new URL(httpAddress)).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setInstanceFollowRedirects(true);
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    // 190422_t+ 網址會重導 增加取得重導網址重新連接
                    // normally, 3xx is redirect
                    int status = connection.getResponseCode();
                    if (status != HttpURLConnection.HTTP_OK) {
                        if (status == HttpURLConnection.HTTP_MOVED_TEMP
                                || status == HttpURLConnection.HTTP_MOVED_PERM
                                || status == HttpURLConnection.HTTP_SEE_OTHER) {
                            redirect = true;
                            redirectAddr = connection.getHeaderField("Location");
                        }
                    }

                    if (redirect)
                    {
                        connection.disconnect();
                        connection = (HttpURLConnection) (new URL(redirectAddr)).openConnection();
                        //connection.setRequestMethod("GET");
                        //connection.setInstanceFollowRedirects(true);
                        //connection.setConnectTimeout(5000);
                        //connection.setReadTimeout(5000);

                    }

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        line=line.replaceAll("&","&amp;"); // 190422_t+ 字串中有&字元 xml 會解析錯誤
                        stringBuilder.append(line);
                    }

                    // 回调
                    if (httpCallbackListener != null) {
                        httpCallbackListener.onFinish(stringBuilder.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    // 回调
                    httpCallbackListener.onError(e);
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                */

                String content = "";
                try {
                    URL url = new URL(httpAddress);
                    Document doc = Jsoup.parse(url, 15000); //使用Jsoup解析網頁
                    //Document doc = Jsoup.connect(httpAddress).validateTLSCertificates(false).timeout(5000).get();
                    //Jsoup.connect(httpAddress).validateTLSCertificates(false).timeout(5000).execute();

                    Elements metas = doc.select("meta");

                    for (Element meta : metas) {

                        if (meta.attr("name").equals("description"))
                        {
                            content = meta.attr("content");
                            break;
                        }
                    }

                    // 回调
                    if (httpCallbackListener != null) {
                        httpCallbackListener.onFinish(content);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // 回调
                    httpCallbackListener.onError(e);
                }
            }
        }).start();
    }
}

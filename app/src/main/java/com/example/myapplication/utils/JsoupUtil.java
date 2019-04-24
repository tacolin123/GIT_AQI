package com.example.myapplication.utils;



import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;

import com.example.myapplication.MainActivity;
import com.example.myapplication.data.SqliteTable;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;



public class JsoupUtil {

    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public static void sendHttpRequest(final String httpAddress, final JsoupCallbackListener jsoupCallbackListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // boolean redirect = false;
                //String redirectURL = httpAddress;
                List<Map<String,String>>  aqiArrayList = new ArrayList<>();
                String dataTitle = "";

                trustEveryone();
                try {
                    URL url = new URL(httpAddress);
                    Document doc = Jsoup.parse(url, 15000); //使用Jsoup解析網頁
                    //Document doc = Jsoup.connect(httpAddress).validateTLSCertificates(false).timeout(5000).get();
                    //Jsoup.connect(httpAddress).validateTLSCertificates(false).timeout(5000).execute();

                    Element table = doc.getElementById("contentsTable");
                    Elements tRows = table.select("tr");

                    for (Element row : tRows) {
                        Map<String,String> rowData = new HashMap<>();
                        Elements tds = row.select("td p");
                        Log.i("JsoupTag","tds_ " + tds.size()  + ":" + tds.text());
                        dataTitle = "";
                        for (int j = 0; j < tds.size(); j++) {
                            if (j == 17) {
                                Log.i("JsoupTag", "tdd:" + tds.get(j).text());

                                //sqliteController.checkCPTime(tds.get(j).text());
                            }
                            rowData.put(SqliteTable.getAqiContentsColName(j),tds.get(j).text());
                            dataTitle += " ";
                            dataTitle += tds.get(j).text();
                        }

                        aqiArrayList.add(rowData);
/*
                        if (tds.size()!=0 && (!tds.get(0).text().equals(""))){
                            rowData.put("dKey",tds.get(0).text());
                            rowData.put("dValue",tds.get(1).text());
                            rowData.put("dComment",tds.get(2).text());
                            rowData.put("dHltValue",tds.get(3).text());
                            rowData.put("dDevValue",tds.get(4).text());
                            testData.add(rowData);
                        }
                        */

                        Log.i("JsoupTag","rowData:" + rowData.toString());
                        // break;
                    }
                    /*
                    Elements url = doc.select("div.detail").select("a");
                    Log.i("mytag", "url:" + url.get(i).attr("href"));

                            //Elements tables = xmlDoc.select("center&gt;table");
                    // tmp.delete(0,tmp.length());
                    for(int i =0; i&lt;title.size() ; i++){
                        Log.<em>e</em>("Kevin Test",title.text());
                        tmp.append(i+1+title.get(i).text()+"&nbsp;&nbsp;&nbsp; "+"\n\n");
                    */

                    // 回调
                    if (jsoupCallbackListener != null) {
                        jsoupCallbackListener.onFinish(aqiArrayList);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    // 回调
                    jsoupCallbackListener.onError(e);
                }
            }

        }).start();
    }
}

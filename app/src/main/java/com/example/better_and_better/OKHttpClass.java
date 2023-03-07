package com.example.better_and_better;

import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpClass {

    String Name = StaticClass.PlayerName;
    String ServerIP = StaticClass.ServerIP;



    OkHttpClient client = new OkHttpClient();

    public OKHttpClass(Context context) {
        this.context = context;
    }

    Context context;
    public void GameRecord(int Score){
        // 取得當下的時間與指定格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String Datetime = dateFormat.format(cal.getTime());

        // 放入資料
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("Datetime", Datetime)
                .addFormDataPart("Name", Name)
                .addFormDataPart("Score", String.valueOf(Score))
                .build();

        // 建構Requests
        Request request = new Request.Builder()
                .url("http://" + ServerIP + "/InsertFunction.php")
                .post(requestBody)
                .build();

        // 建構呼叫物件
        Call call = client.newCall(request);

        // 將傳送資料包再多執行續
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = call.execute();
                    System.out.println(response.code());
                    String respBody = response.body().string();

                    System.out.println(respBody); // 顯示儲存成功

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void SelectRecord(String Name){
        // 放入資料
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("Name", Name)
                .build();

        // 建構Requests
        Request request = new Request.Builder()
                .url("http://" + ServerIP + "/SelectFunction.php")
                .post(requestBody)
                .build();

        // 建構呼叫物件
        Call call = client.newCall(request);

        // 將傳送資料包再多執行續
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = call.execute();
                    String respBody = response.body().string();
                    System.out.println(respBody);
                    Intent intent = new Intent();
                    intent.setAction("RestSelectResponse"); // 設定廣播代號
                    intent.putExtra("ResponseTxt", respBody);
                    context.sendBroadcast(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void SelectPlayerRecord(String Name){
        // 放入資料
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("Name", Name)
                .build();

        // 建構Requests
        Request request = new Request.Builder()
                .url("http://" + ServerIP + "/SelectAllPlayerFunction.php")
                .post(requestBody)
                .build();

        // 建構呼叫物件
        Call call = client.newCall(request);

        // 將傳送資料包再多執行續
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = call.execute();
                    String respBody = response.body().string();
                    Intent intent = new Intent();
                    intent.setAction("RestSelectResponse"); // 設定廣播代號
                    intent.putExtra("ResponseTxt", respBody);
                    context.sendBroadcast(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }





}

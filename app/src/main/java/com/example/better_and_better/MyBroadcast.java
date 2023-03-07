package com.example.better_and_better;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

            String Action = intent.getAction(); // 取得intent的Action

            // 收到來自 TCPClient 傳送過來的資料
            if(Action == "GetTCPReceive") {
                String msg = intent.getStringExtra("ReceiveString");
                TextView ShowText = (TextView) ((Upload) context).findViewById(R.id.MainScreen); // 設定非本類別頁面的物體
                System.out.println(msg);
                ShowText.setText(msg);
            } else if (Action == "RestSelectResponse"){ // 來自RestAPI
                String respBody = intent.getStringExtra("ResponseTxt"); // 取得字串
                try {
                    JSONArray jsonArray = new JSONArray(respBody); // 以JsonArray解析字串

                    String Result = ""; // 要顯示的結果文字
                    String ScoreResult = "";
                    // 解析jsonArray
                    for(int i = 0; i < jsonArray.length(); i++){
                        String Datetime = jsonArray.getJSONObject(i).getString("時間");
                        String Name = jsonArray.getJSONObject(i).getString("姓名");
                        String Score = jsonArray.getJSONObject(i).getString("分數");
                        Result+= Datetime + "\t\t" + Name + "\n";
                    }
                    // 分離 資訊 跟 分數
                    for(int i = 0; i < jsonArray.length(); i++){
                        String Score = jsonArray.getJSONObject(i).getString("分數");
                        ScoreResult+= Score + "\n";
                    }

                    // SHOW IT OFF
                    TextView RecordInput = (TextView) ((Upload) context).findViewById(R.id.MainScreen);
                    TextView ScoreBoard = (TextView) ((Upload) context).findViewById(R.id.textViewScore);
                    RecordInput.setText(Result);
                    ScoreBoard.setText(ScoreResult);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
    }
}

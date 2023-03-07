package com.example.better_and_better;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Upload extends AppCompatActivity {
    MediaPlayer mp4;

    String ServerIP;
    Integer ServerPORT;

    TCPClient tcpClient;

    ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        TextView MainScreen = findViewById(R.id.MainScreen);
        TextView TextIP = findViewById(R.id.textViewIP);
        TextView TextPORT = findViewById(R.id.textViewPORT);
        TextView TextName = findViewById(R.id.textViewName);
        TextView TextONOFF = findViewById(R.id.textViewONOFF);

        EditText EditIP = findViewById(R.id.EditTextIP);
        EditText EditPORT = findViewById(R.id.EditTextPORT);
        EditText EditNAME = findViewById(R.id.EditName);


        Button Back = findViewById(R.id.back);
        Button GetUR = findViewById(R.id.GetUR);
        Button GetBest = findViewById(R.id.GetBest);
        GetBest.setEnabled(false);
        GetUR.setEnabled(false);

        Switch ONOFF = findViewById(R.id.switch3);

        SharedPreferences sharedPreferences = getSharedPreferences("my_pref",MODE_PRIVATE);
        String sharedPreServerIP = sharedPreferences.getString("ServerIP" , "");
        Integer sharedPrePORT = sharedPreferences.getInt("ServerPORT" , 8787);
        String sharedPrePlayerName = sharedPreferences.getString("PlayerName" , "Little_Sponge");

        EditIP.setText(sharedPreServerIP);
        EditNAME.setText(sharedPrePlayerName);
        EditPORT.setText(String.valueOf(sharedPrePORT));

        MyBroadcast myBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter( "GetTCPReceive");
        registerReceiver(myBroadcast , intentFilter);

        IntentFilter intentFilter2 = new IntentFilter( "RestSelectResponse");
        registerReceiver(myBroadcast, intentFilter2);

        IntentFilter intentFilter3 = new IntentFilter( "RestSelectResponse");
        registerReceiver(myBroadcast, intentFilter3);






        ONOFF.setOnCheckedChangeListener((buttonView , isChecked)-> {
            if(isChecked){
                String ServerIP = EditIP.getText().toString();
                Integer ServerPORT = Integer.parseInt( EditPORT.getText().toString() );
                String PlayerName = EditNAME.getText().toString();

                sharedPreferences.edit().putString("ServerIP", ServerIP).apply();
                sharedPreferences.edit().putString("PlayerName", PlayerName).apply();
                sharedPreferences.edit().putInt("ServerPORT", ServerPORT).apply();

                StaticClass.ServerIP = ServerIP;
                StaticClass.ServerPort = ServerPORT;
                StaticClass.PlayerName = PlayerName;

                // 關閉UI
                EditIP.setEnabled(false);
                EditNAME.setEnabled(false);
                EditPORT.setEnabled(false);

                GetBest.setEnabled(true);
                GetUR.setEnabled(true);

                TextONOFF.setText("ONLINE");
                TextONOFF.setTextColor(Color.rgb(0,255,0));

                OKHttpClass okhttpclass = new OKHttpClass(this);
                Integer NewScore = StaticClass.NewScore ;
                okhttpclass.GameRecord(NewScore);

                ONOFF.setEnabled(false);  // 避免亂按增加重複資料

                // 測試連線是否正常用
//                tcpClient = new TCPClient(ServerIP , ServerPORT , this);
//                executorService.execute(tcpClient);
            }else{
                // tcpClient.closeClient();  //如果有連線就關掉
                // 開放UI
                EditIP.setEnabled(true);
                EditNAME.setEnabled(true);
                EditPORT.setEnabled(true);
                GetBest.setEnabled(false);
                GetUR.setEnabled(false);
                TextONOFF.setText("OFFLINE");
                TextONOFF.setTextColor(Color.rgb(255,0,0));
            }
        });




        // 提交 按鈕
        GetUR.setOnClickListener(view ->{
            OKHttpClass okhttpclass = new OKHttpClass(this);
            String PlayerName = EditNAME.getText().toString();
            okhttpclass.SelectPlayerRecord(PlayerName);
        });

        // 提交按鈕
        GetBest.setOnClickListener(view -> {
            String PlayerName = EditNAME.getText().toString();
            OKHttpClass okhttpclass = new OKHttpClass(this);
            okhttpclass.SelectRecord(PlayerName);
        });

        // 上一頁
        Back.setOnClickListener(view -> {
            if (tcpClient != null){
                tcpClient.closeClient();  //如果有連線就關掉
            }

            mp4.stop();
            finish(); // 關閉頁面
        });

        }



    @Override
    protected void onResume() {
        super.onResume();
        mp4 = MediaPlayer.create(this,R.raw.spamton_happy);
        mp4.start();
        mp4.setLooping(true);
    }


    }
















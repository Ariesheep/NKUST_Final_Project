package com.example.better_and_better;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import pl.droidsonroids.gif.GifImageView;



public class MainActivity extends AppCompatActivity {
    MediaPlayer mp1;
    MediaPlayer spamtonbattle;
    MediaPlayer spiceshot;
    MediaPlayer bigshot;
    static MediaPlayer BGM;


    static int pointsplus;  // 加分
    static int pipisnumbers;  // pipis 個數
    static int pipisAcceler;  // pipis加速度
    static int hp;  // hp



    @Override
    protected void onResume() {
        super.onResume();
        mp1 = MediaPlayer.create(this,R.raw.spamton_meeting);
        mp1.start();
        mp1.setLooping(true);

        // 彩蛋
       RadioButton Disk = findViewById(R.id.Disk);
        int Loaded_Disk = StaticClass.NewScore;
        if (Loaded_Disk >= 2000){
            Disk.setVisibility(View.VISIBLE);
        }else {
            Disk.setVisibility(View.INVISIBLE);
        }
    }

    // 初始畫面設定
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void startGame(View view) {
        // BGM setting
        spiceshot = MediaPlayer.create(this,R.raw.bug_spice_shot_short);
        bigshot = MediaPlayer.create(this,R.raw.big_shot);
        spamtonbattle = MediaPlayer.create(this,R.raw.spamton_battle);

        // 取頁面元素
        GifImageView Car = findViewById(R.id.spamtoncar);
        RadioButton Easy = findViewById(R.id.Easy);
        RadioButton Normal = findViewById(R.id.Normal);
        RadioButton Hard = findViewById(R.id.Hard);
        RadioButton Disk = findViewById(R.id.Disk);




        //設定難度
        if(Easy.isChecked()){
            pointsplus=1;
            pipisnumbers=20;
            pipisAcceler=15;
            hp=4;
            BGM=spamtonbattle;
        }else if(Normal.isChecked()){
            pointsplus=3;
            pipisnumbers=30;
            pipisAcceler=18;
            hp=3;
            BGM=spamtonbattle;
        }else if(Hard.isChecked()){
            pointsplus=5;
            pipisnumbers=40;
            pipisAcceler=21;
            hp=3;
            BGM=bigshot;
        }else if(Disk.isChecked()){
            pointsplus=100;
            pipisnumbers=20;
            pipisAcceler=25;
            hp=3;
            BGM=bigshot;
        }else{
            pointsplus=0;
            pipisnumbers=15;
            pipisAcceler=20;
            hp=3;
            BGM=spiceshot;
        }

        StaticClass.onlinebtn = true;

        mp1.stop();
        BGM.start();
        BGM.setLooping(true);

        // 開始遊戲
        GameView gameView = new GameView(this);
        setContentView(gameView);

    }
}
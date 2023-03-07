package com.example.better_and_better;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {
    MediaPlayer mp3;
    MediaPlayer laugh;

    Button onlineBTN;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView HighestScore = findViewById(R.id.HS);
        TextView YourScore = findViewById(R.id.YS);
        TextView NEWHighestScore = findViewById(R.id.ShowTXT);
        Button restartBTN = findViewById(R.id.restart);
        Button exitBTN = findViewById(R.id.exit);
        onlineBTN = findViewById(R.id.online);
        ImageView Spamton = findViewById(R.id.spamtongspamton);
        laugh = MediaPlayer.create(this,R.raw.spamton_laugh_noise);


        //紀錄本局分數
        int points = getIntent().getIntExtra("points",0);
        YourScore.setText("Your Score : " + points);
        StaticClass.NewScore = points;

        if(points == 0){
            NEWHighestScore.setText("EV3RY BUDDY'S FAVORITE [[NULL DIFFICULTY]]");
            Spamton.setImageResource(R.drawable.bigshot_terry);
        }




        //紀錄最高分
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("my_pref",MODE_PRIVATE);
        int highest = sharedPreferences.getInt("highest",0);

        if(points > highest){
            NEWHighestScore.setTextColor(Color.rgb(255,0,0));
            NEWHighestScore.setText("NEW HIGHEST SCORE !!!");
            highest = points;
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("highest",highest);
            editor.commit();
        }
        HighestScore.setText("Highest Points : "+ highest );


        //RESTART Game
        restartBTN.setOnClickListener(view -> {
            Intent intent = new Intent(GameOver.this,MainActivity.class);
            intent.putExtra("Empty_Disk",points);
            mp3.stop();
            laugh.start();
            startActivity(intent);
            finish();


//            mp3.stop();
//            restart();
        });

        //ENDGAME
        exitBTN.setOnClickListener(view -> {
            mp3.stop();
            exit();
        });

        // GO to Upload page
        onlineBTN.setOnClickListener(view -> {
            mp3.stop();
            Intent intent2 = new Intent(GameOver.this,Upload.class);
            intent2.putExtra("Points",points);
            mp3.stop();
            laugh.start();
            startActivity(intent2);
        });
    }





//    public void restart(){
//        Intent intent = new Intent(GameOver.this,MainActivity.class);
//        startActivity(intent);
//        finish();
//    }

    public void exit(){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(StaticClass.onlinebtn == true){
            onlineBTN.setEnabled(true);
        }else{
            onlineBTN.setEnabled(false);
        }

        mp3 =MediaPlayer.create(this,R.raw.spamton_neo_after);
        mp3.start();
        mp3.setLooping(true);


    }
}

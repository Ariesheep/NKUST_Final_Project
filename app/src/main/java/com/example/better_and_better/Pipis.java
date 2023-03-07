package com.example.better_and_better;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Pipis {
    Bitmap pipis[] = new Bitmap[7];
    int pipisframe = 0 ;
    int pipisX , pipisY , pipisSpeed;
    Random random;



    // 七彩 pipis 圖片
    public Pipis(Context context){
        pipis[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pipis);
        pipis[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pipis);
        pipis[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pipis);
        pipis[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pipis);
        pipis[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pipis);
        pipis[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pipis);
        pipis[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pipis);

        random = new Random();
        resetPosition();
    }

    // 取得圖片框架
    public Bitmap getPipis(int pipisframe){
        return  pipis[pipisframe];
    }
    // 取得圖片寬度
    public int getPipisWidth(){
        return pipis[0].getWidth();
    }   //圖寬
    // 取得圖片高度
    public int getPipisHeight(){
        return pipis[0].getHeight();
    }   //圖高

    // pipis重生
    public void resetPosition(){
        pipisX = random.nextInt(GameView.dWidth - getPipisWidth());   //隨機X軸
        pipisY = -200 + random.nextInt(600) * -1;  //固定Y軸
        pipisSpeed = 20 +random.nextInt(MainActivity.pipisAcceler);  //隨機加速度
    }



}

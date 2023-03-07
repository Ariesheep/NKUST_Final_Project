package com.example.better_and_better;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {
    Bitmap explosion [] = new Bitmap[4];
    int explosionFrame = 0;
    int explosionX , explosionY;

    // 爆炸圖片
    public Explosion(Context context){
        explosion[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.explode11);
        explosion[1]= BitmapFactory.decodeResource(context.getResources(),R.drawable.explode12);
        explosion[2]= BitmapFactory.decodeResource(context.getResources(),R.drawable.explode13);
        explosion[3]= BitmapFactory.decodeResource(context.getResources(),R.drawable.explode14);
    }

    // 圖片框架
    public  Bitmap getExplosion(int explosionFrame){
        return explosion[explosionFrame];
    }

















}

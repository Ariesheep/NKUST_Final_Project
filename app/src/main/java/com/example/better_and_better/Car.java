package com.example.better_and_better;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Car {
    Bitmap Car[] = new Bitmap[2];
    int CarFrame = 0;
    int CarX, CarY;

    // 車的圖片
    public Car(Context context) {
        Car[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spamton_littlecar_01);
        Car[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spamton_littlecar_02);
    }

 // 取車車圖片框架
    public Bitmap getCar(int CarFrame) {
        return Car[CarFrame];
    }
}

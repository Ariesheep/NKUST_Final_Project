package com.example.better_and_better;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.Window;


import java.util.ArrayList;
import java.util.Random;



public class GameView extends View {

    Bitmap background , ground ,car;
    Rect rectbackground , rectground;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS =30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int points = 0 ;
    static int dWidth , dHeight;
    Random random;
    float carX ,carY;
    float oldX;
    float oldCarX;
    ArrayList<Pipis> pipises;
    ArrayList<Explosion> explosions ;







    //難度設定
    int pipisnumbers = MainActivity.pipisnumbers;
    int pointsplus = MainActivity.pointsplus ;
    int hp =MainActivity.hp ;








    public GameView(Context context) {
        super(context);
        this.context = context ;

        // 設定 背景 & 地面 圖片
        background =  BitmapFactory.decodeResource(context.getResources(),R.drawable.neo_background);
        ground =  BitmapFactory.decodeResource(context.getResources(),R.drawable.road);


        // 設定car圖
        if(pointsplus==100){
            car =  BitmapFactory.decodeResource(context.getResources(),R.drawable.little_spamton_neo);}
        else if(pointsplus==0){
            car =  BitmapFactory.decodeResource(context.getResources(),R.drawable.bigshot_terry_small);}
        else{car =  BitmapFactory.decodeResource(context.getResources(),R.drawable.spamton_littlecar_02);}



        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;  // 裝置橫向長度
        dHeight = size.y;  // 裝置縱向高度
        rectbackground = new Rect(0,0,dWidth,dHeight);  // 背景反應
        rectground = new Rect(0,dHeight-ground.getHeight(),dWidth,dHeight);  // 地面反應
        handler = new Handler() ;
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        // SCREEN UI
        textPaint.setColor(Color.rgb(255,255,255));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);

        healthPaint.setTextSize(TEXT_SIZE);
        healthPaint.setTextAlign(Paint.Align.RIGHT);





        // 物件導入
        random = new Random();
        pipises = new ArrayList<>();
        explosions = new ArrayList<>();

        // 車子資料
        carX = dWidth/2 - car.getWidth()/2;
        carY= dHeight-ground.getHeight()-car.getHeight();



        // 產出pipis
            for(int i=0;i<pipisnumbers;i++){
            Pipis pipis = new Pipis(context);
            pipises.add(pipis);
            //System.out.println(pipisnumbers); //查看 確認產出的pipis數量
            }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // 初始化畫面
        super.onDraw(canvas);
        canvas.drawBitmap(background,null,rectbackground,null);  // 背景
        canvas.drawBitmap(ground,null,rectground,null); // 地面
        canvas.drawBitmap(car,carX,carY,null); // 車



        // pipis初始物理屬性
        for (int i=0; i<pipises.size();i++ ){
            canvas.drawBitmap( pipises.get(i).getPipis(pipises.get(i).pipisframe),
                    pipises.get(i).pipisX,
                    pipises.get(i).pipisY, null);
            // pipis 下降
            pipises.get(i).pipisframe++;
            if (pipises.get(i).pipisframe >2){
                pipises.get(i).pipisframe=0;
            }

            //當pipis碰觸到地面
            pipises.get(i).pipisY += pipises.get(i).pipisSpeed;
            if (pipises.get(i).pipisY + pipises.get(i).getPipisHeight() >= dHeight-ground.getHeight()){
                points += pointsplus;  // 加分

                // 設置爆炸
                Explosion explosion = new Explosion(context);
                explosion.explosionX = pipises.get(i).pipisX;
                explosion.explosionY = pipises.get(i).pipisY;
                explosions.add(explosion);

                // 重置 pipis
                pipises.get(i).resetPosition();
            }
        }

        // pipis觸碰到car
        for(int i=0; i < pipises.size(); i++){
            if(pipises.get(i).pipisX + pipises.get(i).getPipisWidth() >= carX  // 碰到車左
                    && pipises.get(i).pipisX <= carX+car.getWidth() //碰到車右
                    && pipises.get(i).pipisY +pipises.get(i).getPipisWidth() >= carY  // 碰到車底，下面車頂
                    && pipises.get(i).pipisY + pipises.get(i).getPipisWidth() <= carY+car.getHeight()){

                // 扣血 & 重置 pipis
                hp--;
                pipises.get(i).resetPosition();

                // 遊戲結束
                if(hp == 0){
                    MainActivity.BGM.stop();
                    Intent intent= new Intent(context,GameOver.class);
                    intent.putExtra("points",points);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }

            }

            }

        //爆炸範圍
        for ( int i =0;i < explosions.size(); i++ ){
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame),
                    explosions.get(i).explosionX,explosions.get(i).explosionY,null);
            explosions.get(i).explosionFrame++;

            // 爆炸動畫跑完
            if(explosions.get(i).explosionFrame>3){
                explosions.remove(i);
            }
        }

        // HP UI
        healthPaint.setColor(Color.GREEN);
        if(hp==2){
            healthPaint.setColor(Color.YELLOW);
        }else if(hp==1){
            healthPaint.setColor(Color.RED);
        }else if(hp<=0){
            healthPaint.setColor(Color.WHITE);
        }

        // 分數 & HP 資訊
        canvas.drawText(""+hp,(dWidth-20),TEXT_SIZE,healthPaint);
        canvas.drawText(""+points,20,TEXT_SIZE,textPaint);
        handler.postDelayed(runnable, UPDATE_MILLIS);

        }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //觸碰控制
        float touchX = event.getX();
        float touchY = event.getY();

        // 鎖定Y軸 (Y軸不影響移動判定)
        if(touchY >= carY){
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldCarX = carX;
            }

            // 移動X軸 (X軸影響移動判定)
            if (action == MotionEvent.ACTION_MOVE){
                float shift = oldCarX-touchX;
                float newCarX = oldCarX - shift;
                if (newCarX <=0){
                    carX=0;
                }else if(newCarX >= dWidth-car.getWidth()){
                    carX = dWidth-car.getWidth();
                }else{
                    carX = newCarX;
                }
            }
        }
        return true;
    }
}




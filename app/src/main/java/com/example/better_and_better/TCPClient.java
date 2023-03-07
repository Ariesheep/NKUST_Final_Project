package com.example.better_and_better;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPClient implements Runnable {

    PrintWriter pw; // 傳送給伺服器的物件
    InputStream is; // 接收伺服器資料的輸入串流
    DataInputStream dis; // 解析輸入串流的物件
    Context context; // 建構時的的頁面資訊
    String serverIP; // 要連接的伺服器IP
    int serverPort; // 要連接的伺服器端口
    boolean isRun = true; // 預設建構後即連接，將變數設為run，以便控制關閉
    private Socket socket; // Socket 的物件
    ExecutorService exec = Executors.newCachedThreadPool(); // 多線程池
    // 建構子，傳入連接的ip、端口、以及頁面的容器
    public TCPClient(String ip, int port, Context context){
        this.serverIP = ip;
        this.serverPort = port;
        this.context = context;
    }

    // 傳送訊息
    public void send(String msg){
        // 要將傳送包裝成多線程，上實機才不會有線程問題
        SendMsg sendMsg = new SendMsg(socket,msg);
        exec.execute(sendMsg);
    }

    // 啟動線程
    @Override
    public void run() {
        byte[] buff = new byte[100]; // 準備接收伺服器傳來的的byte陣列
        // 查看連接的伺服器IP與端口
        System.out.println(serverIP);
        System.out.println(serverPort);

        // 建構Socket 與 DataInputStream
        try{
            socket = new Socket(serverIP, serverPort); // 傳入IP與端口建構Socket
            socket.setSoTimeout(5000); // 連接時限(5000毫秒，即5秒)
            is = socket.getInputStream(); // 讀取從伺服器收到的資訊
            dis = new DataInputStream(is); // 解析收到的資訊
        }catch(IOException e){
            e.printStackTrace(); // 若連接失敗，則顯示錯誤訊息
        }

        // 當Socket連接中，則持續等待讀取從伺服器傳來的資訊
        while (isRun){
            try{
                int rcvLen = dis.read(buff); // 讀取到伺服器傳來的資料
                // 將收到的byte以utf8編碼進行轉換
                String rcvMsg = new String(buff, 0, rcvLen, "utf-8");

                // 設定收到伺服器回傳內容的事件廣播
                Intent intent = new Intent();
                intent.setAction("GetTCPReceive");
                intent.putExtra("ReceiveString", rcvMsg);
                context.sendBroadcast(intent);

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        // isRun變成false後，跳出while，就會關閉所有相關物件
        try{
            is.close(); // 關閉輸入串流
            dis.close(); // 關閉借西輸入串流物件
            socket.close(); // 關閉 Socket
        }catch (IOException e){
            e.printStackTrace(); // 關閉異常時輸出錯誤資訊
        }
    }

    // 關閉客戶端
    public void closeClient() {
        // 啟動此方法後，將isRun變成false，就會自動跳出while迴圈關閉相關物件
        isRun = false;
    }

}

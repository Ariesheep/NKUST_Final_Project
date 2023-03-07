package com.example.better_and_better;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendMsg implements Runnable {
    Socket socket;
    String msg;

    public SendMsg(Socket socket, String msg) {
        this.socket = socket;
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true); // 建構傳送資料的outputString
            pw.print(msg); // 傳送給伺服器
            pw.flush(); // 刷新物件
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

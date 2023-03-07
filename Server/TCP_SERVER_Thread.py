# -*- coding: utf-8 -*-
"""
Created on Mon May 23 21:21:23 2022

@author: user
"""
import socket
from _thread import *
import threading

# 指定協議
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 讓端口可以重複使用
server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
# 绑定ip和端口
#server.bind(('192.168.0.67', 8765))
server.bind(('172.16.0.79', 8888))
# 監聽
server.listen(5)

#print_lock = threading.Lock()

def threaded(c):
    while True:
        # data received from client
        data = c.recv(1024)
        if not data:
            print('Bye')
             
            # lock released on exit
            #print_lock.release()
            break
 
        # reverse the given string from client
        print(data.decode("utf8"))
        
        data = "伺服器收到".encode("utf8") + data
        #data+= input("你好：").encode("utf8")
        # send back reversed string to client
        print(data)
        print(type(data))
        print(123)
        c.send(data)
       
 
    # connection closed
    c.close()
    
while True:
    print("等待連接...")
    clientsocket, address = server.accept()
    print(address)
    # 等待消息
    print("連接成功")

    #clientsocket.send("來自Server的問候".encode("utf8"))
    
    start_new_thread(threaded, (clientsocket,))
    #print_lock.acquire()
# 關閉socket
clientsocket.close()
server.close()
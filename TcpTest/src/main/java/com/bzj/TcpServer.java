package com.bzj;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9090);
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int len;
                char[] chars = new char[1024];
                StringBuilder stringBuilder = new StringBuilder();
                int index;
                while ((len = reader.read(chars)) != -1) {
                    String message = new String(chars, 0, len);
                    if ((index = message.indexOf("##")) != -1) {//不包含结束符"##"时，index会返回-1,说明没有结束
                        stringBuilder.append(message, 0, index);//去掉结束符
                        break;//读取数据结束
                    } else {
                        stringBuilder.append(message);
                    }
                }
                System.out.println("TcpServer recieve message:[" + stringBuilder.toString() + "]");
                OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
                writer.write("hello word:" + stringBuilder.toString() + "##");//回复消息携带结束符
                writer.flush();
                writer.close();
                reader.close();
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

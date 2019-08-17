package com.bzj;


import java.io.*;
import java.net.Socket;

public class TcpClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9090);
        OutputStream outputStream = socket.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        String name = "bzj##";
        System.out.println("TcpClient seng message, i am " + name);
        writer.write(name);
        writer.flush();
        InputStreamReader reader = new InputStreamReader(socket.getInputStream());
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
        System.out.println("TcpClient recieve TcpServer response:[" + stringBuilder.toString() + "]");
        reader.close();
        writer.close();
        socket.close();
    }
}

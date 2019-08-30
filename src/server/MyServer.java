package server;

import util.PropertiesUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    private ServerSocket serverSocket;
    private int port;
    private String nodePath;
    private String welcomeFile;

    public MyServer() throws IOException {
        this.port = Integer.parseInt(PropertiesUtil.get("file","port"));
        this.nodePath = PropertiesUtil.get("file","nodePath");
        this.welcomeFile = PropertiesUtil.get("file","welcomeFile");
        this.serverSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        System.out.println("服务器启动");
        while(true) {
            //接受客户端
            Socket accept = serverSocket.accept();
            System.out.println("连接成功");
            new Thread(new ServerSocketThread(accept,nodePath,welcomeFile)).start();
        }

    }

    public static void main(String[] args) {
        try {
            new MyServer().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

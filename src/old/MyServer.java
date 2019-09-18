package old;

import PropertiesUtil.FileUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    private ServerSocket serverSocket;
    private int port;

    public MyServer() throws IOException {
        this.port = Integer.parseInt(FileUtil.getValue("port"));
        this.serverSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        System.out.println("服务器启动");
        while(true) {
            //接受客户端
            Socket accept = serverSocket.accept();
            System.out.println("连接成功");
            new Thread(new ServerSocketThread(accept)).start();
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

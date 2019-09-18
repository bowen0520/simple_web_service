package old;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerSocketThread implements Runnable {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ServerSocketThread(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = this.socket.getInputStream();
        this.outputStream = this.socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            System.out.println(this+"____________________________________________");
            RequestUtil request = new RequestUtil(inputStream);
            String fileName = request.getFileName();
            String method = request.getMethod();

            if("favicon.ico".equals(fileName)||"".equals(method)){
                return ;
            }

            request.print();

            ResponseUtil response = new ResponseUtil(request,outputStream);

            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

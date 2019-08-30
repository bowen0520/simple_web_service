package server;

import util.RequestUtil;
import util.ResponseUtil;

import java.io.*;
import java.net.Socket;

public class ServerSocketThread implements Runnable {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String nodePath;
    private String welcomeFile;

    public ServerSocketThread(Socket socket, String nodePath, String welcomeFile) throws IOException {
        this.socket = socket;
        this.inputStream = this.socket.getInputStream();
        this.outputStream = this.socket.getOutputStream();
        this.nodePath = nodePath;
        this.welcomeFile = welcomeFile;
    }

    @Override
    public void run() {
        try {
            System.out.println(this+"____________________________________________");
            RequestUtil request = new RequestUtil(inputStream);
            String agreement = request.getAgreement();
            String fileName = request.getFileName();
            String method = request.getMethod();
            String userid = request.getMessage("userid");
            String password = request.getMessage("password");

            if("favicon.ico".equals(fileName)||"".equals(method)){
                return ;
            }
            request.print();

            ResponseUtil response = new ResponseUtil(userid,password,agreement,fileName,nodePath,welcomeFile,outputStream);

            /*
            File file;
            if(fileName.equals("")){
                file = new File(nodePath, welcomeFile);
            }else{
                file = new File(nodePath,fileName);
                if(!file.exists()){
                    file = new File(nodePath, errorFile);
                }
            }
            if(method.equals("POST")){
                file = new File("resourse/PostLogin.html");
            }else{
                file = new File("resourse/GetLogin.html");
            }
            ResponseUtil.sendHead(agreement,"200 OK",outputStream);
            ResponseUtil.sendFile(file,outputStream);
            */

            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public String getPath() throws IOException{
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        String s = new String(bytes, 0, len);
        String firstLine = s.substring(0, s.indexOf('\n'));
        System.out.println(firstLine);
        String[] strings = firstLine.split(" ");
        String fileName = strings[1].substring(1);
        return fileName;
    }*/
    /*public void getResponse(String status,File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 ");
        stringBuilder.append(status+'\n');
        stringBuilder.append("Content-Type:text/html;charset=GBK\n");
        stringBuilder.append("Content-Language:zh-cn\n");
        stringBuilder.append('\n');
        outputStream.write(stringBuilder.toString().getBytes("GBK"));
        if(file!=null){
            String fileName = file.getName();
            if(fileName.endsWith(".txt")||fileName.endsWith(".html")){
                sendTxt(file);
            }else{
                sendOther(file);
            }
        }
        outputStream.flush();
    }
    public void sendTxt(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = fis.read(bytes))!=-1) {
            byteArrayOutputStream.write(bytes, 0, len);
        }
        byte[] gbks = byteArrayOutputStream.toString("UTF-8").getBytes("GBK");
        outputStream.write(gbks);
    }

    public void sendOther(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = fis.read(bytes))!=-1) {
            outputStream.write(bytes, 0, len);
        }
    }*/
}

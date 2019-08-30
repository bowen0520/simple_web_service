package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {
    //客户端请求方法
    private String method = "GET";

    //客户端请求文件文件名
    private String fileName = "";

    //客户端请求协议
    private String agreement = "HTTP/1.1";

    //客户端请求头信息
    private Map<String,String> heads = new HashMap<>();

    //客户端发送的消息
    private Map<String,String> messages = new HashMap<>();

    public RequestUtil(InputStream inputStream) throws IOException {
        getRequestMsg(inputStream);
    }

    //获取请求的所有信息
    public void getRequestMsg(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = br.readLine();
        if(line==null||"".equals(line)){
            return ;
        }
        handleLine(line);

        String head = "";
        while(!"".equals(head = br.readLine())){
            handleHead(head);
        }

        if(method.equals("POST")){
            //handleMessage(br.readLine());

            char[] cs = new char[1024];
            int len = br.read(cs);
            handleMessage(new String(cs,0,len));
        }
    }

    public void handleLine(String line){
        String[] msgs = line.split(" ");
        method = msgs[0];
        int index = msgs[1].indexOf("?");
        if(method.equals("GET")&&index!=-1){
            fileName = msgs[1].substring(0,index).substring(1);
            handleMessage(msgs[1].substring(index+1));
        }else{
            fileName = msgs[1].substring(1);
        }
        agreement = msgs[2];
    }

    public void handleHead(String head){
        String[] msgs = head.split(":");
        if(msgs.length==2) {
            heads.put(msgs[0], msgs[1]);
        }
    }

    public void handleMessage(String message){
        if(message==null||"".equals(message)){
            return;
        }
        String[] maps = message.split("&");
        for(String m:maps){
            String[] ss = m.split("=");
            if(ss.length==2){
                messages.put(ss[0],ss[1]);
            }
        }
    }

    public void print(){
        System.out.println("method:"+method);
        System.out.println("fileName:"+fileName);
        System.out.println("agreement:"+agreement);
        heads.forEach((o1,o2)->{
            System.out.println(o1+":"+o2);
        });
        messages.forEach((o1,o2)->{
            System.out.println(o1+":"+o2);
        });
    }

    public String getMethod() {
        return method;
    }

    public String getFileName() {
        return fileName;
    }

    public String getAgreement() {
        return agreement;
    }

    public String getHead(String key){
        return heads.get(key);
    }

    public String getMessage(String key){
        return messages.get(key);
    }

    public String getHeads() {
        StringBuilder sb = new StringBuilder();
        heads.forEach((o1,o2)->{
            sb.append(o1+":"+o2+"\r\n");
        });
        return sb.toString();
    }

    public String getMessages() {
        StringBuilder sb = new StringBuilder();
        messages.forEach((o1,o2)->{
            sb.append(o1+":"+o2+"\r\n");
        });
        return sb.toString();
    }
}

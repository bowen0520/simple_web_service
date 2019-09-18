package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: web_project_study
 * @package: request
 * @filename: RequestImpl.java
 * @create: 2019/09/17 20:03
 * @author: 29314
 * @description: .请求消息处理实现类
 **/

public class RequestImpl implements Request{
    //获取请求消息的输入流
    InputStream is;

    //客户端请求方法
    private String method = "GET";

    //客户端请求文件文件名
    private String resourse = null;

    //客户端请求协议
    private String agreement = "HTTP/1.1";

    //客户端请求头信息
    private Map<String,String> configs = new HashMap<>();

    //客户端发送的消息
    private Map<String,String> msgs = new HashMap<>();

    public RequestImpl(InputStream is) throws IOException {
        this.is = is;
        getRequestMsg();
    }

    //获取请求的所有信息
    public void getRequestMsg() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"ISO-8859-1"));
        String line = null;

        //获取请求行
        line = br.readLine();
        if(line==null||"".equals(line)){
            return ;
        }

        //处理请求行
        handleLine(line);

        //获取请求头信息
        while(!"".equals(line = br.readLine())){
            //处理请求头信息
            handleHead(line);
        }

        //获取并处理请求体
        if(method.equals("POST")){
            //handleMessage(br.readLine());
            char[] cs = new char[1024];
            int len = br.read(cs);
            handleMessage(new String(cs,0,len));
        }
    }

    public void handleLine(String line){
        System.out.println(line);
        String[] msgs = line.split(" ");
        System.out.println(Arrays.toString(msgs));
        method = msgs[0];
        int index = msgs[1].indexOf("?");
        if(method.equals("GET")&&index!=-1){
            resourse = msgs[1].substring(0,index).substring(1);
            handleMessage(msgs[1].substring(index+1));
        }else{
            resourse = msgs[1].substring(1);
        }
        agreement = msgs[2];
    }

    public void handleHead(String head){
        String[] msgs = head.split(":");
        if(msgs.length==2) {
            configs.put(msgs[0], msgs[1]);
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
                msgs.put(ss[0],ss[1]);
            }
        }
    }

    public void print(){
        System.out.println("method:"+method);
        System.out.println("resourse:"+resourse);
        System.out.println("agreement:"+agreement);
        configs.forEach((o1,o2)->{
            System.out.println(o1+":"+o2);
        });
        msgs.forEach((o1,o2)->{
            System.out.println(o1+":"+o2);
        });
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getResourse() {
        return resourse;
    }

    @Override
    public String getAgreement() {
        return agreement;
    }

    @Override
    public String getConfig(String configKey) {
        return configs.get(configKey);
    }

    @Override
    public String getMsg(String msgKey) {
        return msgs.get(msgKey);
    }
}

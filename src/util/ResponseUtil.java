package util;

import java.io.*;

public class ResponseUtil {
    private String userid;
    private String password;
    private String agreement;
    private String type;
    private String fileName;
    private String nodePath;
    private String welcomeFile;
    private OutputStream outputStream;


    public ResponseUtil(String userid, String password, String agreement, String fileName, String nodePath, String welcomeFile, OutputStream outputStream) throws IOException {
        this.userid = userid;
        this.password = password;
        this.agreement = agreement;
        this.type = "txt";
        this.fileName = fileName;
        this.nodePath = nodePath;
        this.welcomeFile = welcomeFile;
        this.outputStream = outputStream;
        setResponseMsg();
    }

    public void setResponseMsg() throws IOException {
        if(checkPermissions()){
            File file = checkFile();
            if(file==null){
                outputStream.write(sendHead("404",type).getBytes());
            }else{
                sendFile(file);
            }
        }else{
            outputStream.write(sendHead("401",type).getBytes());
        }
        outputStream.flush();
    }

    private boolean checkPermissions(){
        if(userid==null||password==null){
            return false;
        }
        String pw = PropertiesUtil.get("user",userid);
        if(pw==null){
            return false;
        }
        return password.equals(pw);
    }

    private File checkFile(){
        File file = null;
        if(fileName.equals("")){
            file = new File(nodePath, welcomeFile);
        }else{
            file = new File(nodePath,fileName);
            if(!file.exists()){
                file = null;
            }
        }
        return file;
    }

    private String sendHead(String statusID,String type){
        StringBuilder stringBuilder = new StringBuilder();
        String status = statusID+" "+PropertiesUtil.get("status",statusID);
        stringBuilder.append(agreement+" "+status+'\n');
        String contentType = PropertiesUtil.get("mime",type);
        if(contentType.indexOf("text")!=-1){
            contentType = "Content-Type: "+contentType+";charset=GBK\n";
        }else {
            contentType = "Content-Type: "+contentType+"\n";
        }
        System.out.println(contentType);
        stringBuilder.append(contentType);
        stringBuilder.append("Content-Language: zh-cn\n");
        stringBuilder.append("Connection: close\n");
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }

    private void sendFile(File file) throws IOException {
        String filename = file.getName();
        int index = filename.lastIndexOf(".");
        if(index!=-1){
            type = filename.substring(index+1);
        }
        outputStream.write(sendHead("200",type).getBytes());
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = fis.read(bytes))!=-1) {
            outputStream.write(bytes, 0, len);
        }
        outputStream.flush();
        fis.close();
    }
}

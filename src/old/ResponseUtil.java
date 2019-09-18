package old;

import PropertiesUtil.FileUtil;
import PropertiesUtil.MimeUtil;
import PropertiesUtil.StatusUtil;
import PropertiesUtil.UserUtil;

import java.io.*;

public class ResponseUtil {
    private String userid;
    private String password;
    private String agreement;
    private String type;
    private String fileName;

    private RequestUtil req;

    private String nodePath;
    private String welcomeFile;
    private OutputStream outputStream;

    private String dynClassName;

    public ResponseUtil(RequestUtil req, OutputStream outputStream) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.req = req;

        this.userid = req.getMessage("userid");
        this.password = req.getMessage("password");
        this.agreement = req.getAgreement();
        this.fileName = req.getFileName();

        this.type = "txt";
        this.nodePath = FileUtil.getValue("nodePath");
        this.welcomeFile = FileUtil.getValue("welcomeFile");
        this.outputStream = outputStream;
        setResponseMsg();
    }

    public void setResponseMsg() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (checkPermissions()) {
            File file = checkFile();
            if (file == null) {
                outputStream.write(sendHead("404", type).getBytes());
            } else {
                sendFile(file);
            }
        } else {
            outputStream.write(sendHead("401", type).getBytes());
        }
        outputStream.flush();
    }

    //检查用户权限
    public boolean checkPermissions(){
        if(userid==null||password==null){
            return false;
        }
        String pw = UserUtil.getValue(userid);
        if(pw==null){
            return false;
        }
        return password.equals(pw);
    }

    //检查是否有需要发送的资源
    public File checkFile(){
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

    //获取响应头信息
    public String sendHead(String statusID,String type){
        StringBuilder stringBuilder = new StringBuilder();
        String status = statusID+" "+ StatusUtil.getValue(statusID);
        stringBuilder.append(agreement+" "+status+'\n');
        String contentType = MimeUtil.getValue(type);
        if(contentType.indexOf("text")!=-1){
            contentType = "Content-Type: "+contentType+";charset=GBK\n";
        }else {
            contentType = "Content-Type: "+contentType+"\n";
        }
        //System.out.println(contentType);
        stringBuilder.append(contentType);
        stringBuilder.append("Content-Language: zh-cn\n");
        stringBuilder.append("Connection: close\n");
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }

    public void sendMsg(String msg) throws IOException {
        outputStream.write(msg.getBytes());
        outputStream.flush();
    }

    //发送文件

    public void sendFile(File file) throws IOException {
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
    //判断是否为动态资源文件
    /*
    public boolean isDynamicClass(){
        File file = new File(nodePath,fileName);
        if(file.exists()){
            return false;
        }
        if(!fileName.endsWith(".action")){
            return false;
        }
        int index = fileName.lastIndexOf(".action");
        dynClassName = ServletUtil.getValue(fileName.substring(0,index));
        if(dynClassName==null){
            return false;
        }
        return true;
    }*/
}

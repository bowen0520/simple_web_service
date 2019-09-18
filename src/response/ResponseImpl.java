package response;

import PropertiesUtil.FileUtil;
import PropertiesUtil.MimeUtil;
import PropertiesUtil.ServletUtil;
import PropertiesUtil.StatusUtil;
import PropertiesUtil.UserUtil;
import request.Request;
import servlet.Servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: web_project_study
 * @package: response
 * @filename: ResponseImpl.java
 * @create: 2019/09/17 21:09
 * @author: 29314
 * @description: .响应实现类
 **/

public class ResponseImpl implements Response{
    private Request req;
    private OutputStream os;

    private String nodePath;
    private String welcome;
    private String error;

    //动态资源类全名
    private String dynClassName;

    public ResponseImpl(Request req, OutputStream os) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        this.req = req;
        this.os = os;
        nodePath = FileUtil.getValue("nodePath");
        welcome = FileUtil.getValue("welcomeFile");
        error = FileUtil.getValue("errorFile");
        setResponseMsg();
    }

    public void setResponseMsg() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String userid = req.getMsg("userid");
        String password = req.getMsg("password");
        if(checkPermissions(userid,password)){//判断是否有权限
            String resourse = req.getResourse();
            if(isDynamicClass(resourse)){//判断是否为动态资源
                Class clazz = Class.forName(dynClassName);
                Servlet servlet = (Servlet) clazz.newInstance();
                servlet.service(req,this);
            }else{
                File file = getResBody(resourse);
                String status = "200";
                if(file==null){
                    status = "404";
                    file = new File(nodePath,error);
                }
                String resLine = getResLine(req.getAgreement(),status);
                String head = getResHead(getFileType(file));
                sendMsg(resLine+head);
                sendFile(file);
            }
        }else{
            String resLine = getResLine(req.getAgreement(),"401");
            File file = new File(nodePath,error);
            String head = getResHead(getFileType(file));
            sendMsg(resLine+head);
            sendFile(file);
        }
    }

    //检查用户权限
    public boolean checkPermissions(String userid,String password){
        if(userid==null||password==null){
            return false;
        }
        String pw = UserUtil.getValue(userid);
        if(pw==null){
            return false;
        }
        return password.equals(pw);
    }

    //判断是否为动态资源文件
    public boolean isDynamicClass(String resourse){
        dynClassName = ServletUtil.getValue(resourse);
        return dynClassName!=null;
    }

    //获取文件类型
    public String getFileType(File file){
        String filename = file.getName();
        int index = filename.lastIndexOf(".");
        String fileType = "html";
        if(index!=-1){
            fileType = filename.substring(index+1);
        }
        return fileType;
    }
    @Override
    public String getResLine(String agreement, String status) {
        return agreement+" "+status+" "+ StatusUtil.getValue(status)+"\n";
    }

    @Override
    public String getResHead(String filetype) {
        StringBuilder stringBuilder = new StringBuilder();
        String contentType = "Content-Type:"+MimeUtil.getValue(filetype)+";charset=GBK\n";
        stringBuilder.append(contentType);
        stringBuilder.append("Content-Language: zh-cn\n");
        stringBuilder.append("Connection: close\n");
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }

    @Override
    public File getResBody(String resourse) {
        File file = new File(nodePath,resourse);
        if(file.exists()){
            if("".equals(resourse)){
                file = new File(nodePath,welcome);
            }
        }else{
            file = null;
        }
        return file;
    }

    @Override
    public void sendMsg(String msg) throws IOException {
        os.write(msg.getBytes());
        os.flush();
    }

    @Override
    public void sendFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = fis.read(bytes))!=-1) {
            os.write(bytes, 0, len);
        }
        os.flush();
        fis.close();
    }
}

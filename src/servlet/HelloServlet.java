package servlet;

import request.Request;
import request.RequestImpl;
import response.Response;
import response.ResponseImpl;

import java.io.File;
import java.io.IOException;

/**
 * @program: web_project_study
 * @package: util
 * @filename: HelloServlet.java
 * @create: 2019/09/17 18:20
 * @author: 29314
 * @description: .Servlet的hello实现类
 **/

public class HelloServlet implements Servlet {
    @Override
    public void service(Request req, Response res) {
        RequestImpl reqImpl = (RequestImpl) req;
        ResponseImpl resImpl = (ResponseImpl) res;
        try {
            String resLine = resImpl.getResLine(reqImpl.getAgreement(),"200");
            File file = new File("servletFile/hello.html");
            String head = res.getResHead(resImpl.getFileType(file));
            resImpl.sendMsg(resLine+head);
            resImpl.sendFile(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

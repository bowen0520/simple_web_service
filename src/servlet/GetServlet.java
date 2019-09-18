package servlet;

import request.Request;
import request.RequestImpl;
import response.Response;
import response.ResponseImpl;

import java.io.File;
import java.io.IOException;

/**
 * @program: web_project_study
 * @package: servlet
 * @filename: GetServlet.java
 * @create: 2019/09/18 10:50
 * @author: 29314
 * @description: .get的动态资源
 **/

public class GetServlet implements Servlet{
    @Override
    public void service(Request req, Response res) {
        RequestImpl reqImpl = (RequestImpl) req;
        ResponseImpl resImpl = (ResponseImpl) res;
        try {
            String resLine = resImpl.getResLine(reqImpl.getAgreement(),"200");
            File file = new File("servletFile/GetLogin.html");
            String head = res.getResHead(resImpl.getFileType(file));
            resImpl.sendMsg(resLine+head);
            resImpl.sendFile(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

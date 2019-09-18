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
 * @filename: PostServlet.java
 * @create: 2019/09/18 10:51
 * @author: 29314
 * @description: .post的动态资源获取
 **/

public class PostServlet implements Servlet{
    @Override
    public void service(Request req, Response res) {
        RequestImpl reqImpl = (RequestImpl) req;
        ResponseImpl resImpl = (ResponseImpl) res;
        try {
            String resLine = resImpl.getResLine(reqImpl.getAgreement(),"200");
            File file = new File("servletFile/PostLogin.html");
            String head = res.getResHead(resImpl.getFileType(file));
            resImpl.sendMsg(resLine+head);
            resImpl.sendFile(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

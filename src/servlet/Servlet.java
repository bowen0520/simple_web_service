package servlet;

import request.Request;
import response.Response;

/**
 * @program: web_project_study
 * @package: util
 * @filename: Servlet.java
 * @create: 2019/09/17 18:15
 * @author: 29314
 * @description: .动态资源实现接口
 **/

public interface Servlet {
    public void service(Request req, Response res);
}

package response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @program: web_project_study
 * @package: response
 * @filename: Response.java
 * @create: 2019/09/17 20:29
 * @author: 29314
 * @description: .响应的实现接口
 **/

public interface Response {
    //获取响应行
    public String getResLine(String agreement,String status);

    //获取响应头
    public String getResHead(String filetype);

    //获取响应资源
    public File getResBody(String resourse);

    //发送消息
    public void sendMsg(String msg) throws IOException;

    //发送文件
    public void sendFile(File file) throws IOException;
}

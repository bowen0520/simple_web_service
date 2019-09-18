package request;

/**
 * @program: web_project_study
 * @package: request
 * @filename: request.java
 * @create: 2019/09/17 19:51
 * @author: 29314
 * @description: .请求消息处理接口
 **/

public interface Request {
    //获取请求方法的方法类型
    public String getMethod();

    //获取请求的资源
    public String getResourse();

    //获取请求消息的请求协议
    public String getAgreement();

    //获取请求头的配置信息
    //获取单个信息
    public String getConfig(String configKey);

    //获取请求发送过来的信息
    //获取单个信息
    public String getMsg(String msgKey);
}

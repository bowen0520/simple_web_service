package PropertiesUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @program: web_project_study
 * @package: PropertiesUtil
 * @filename: UserUtil.java
 * @create: 2019/09/17 19:46
 * @author: 29314
 * @description: .获取用户账号密码信息
 **/

public class UserUtil {
    private static Properties util = new Properties();

    static {
        try {
            util.load(new FileInputStream("user.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return util.getProperty(key);
    }
}

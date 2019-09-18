package PropertiesUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @program: web_project_study
 * @package: PropertiesUtil
 * @filename: ServletUtil.java
 * @create: 2019/09/17 19:41
 * @author: 29314
 * @description: .获取动态资源信息
 **/

public class ServletUtil {
    private static Properties util = new Properties();

    static {
        try {
            util.load(new FileInputStream("servlet.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return util.getProperty(key);
    }
}

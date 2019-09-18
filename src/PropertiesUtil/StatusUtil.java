package PropertiesUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 获取状态码信息
 */
public class StatusUtil {
    private static Properties util = new Properties();

    static {
        try {
            util.load(new FileInputStream("status.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return util.getProperty(key);
    }
}

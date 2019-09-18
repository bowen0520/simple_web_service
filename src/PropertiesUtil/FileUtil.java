package PropertiesUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 获取文件配置信息
 */
public class FileUtil{
    private static Properties util = new Properties();

    static {
        try {
            util.load(new FileInputStream("file.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return util.getProperty(key);
    }
}

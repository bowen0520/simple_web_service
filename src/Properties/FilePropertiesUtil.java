package Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FilePropertiesUtil {
    private static Properties file = new Properties();
    private static Properties mime = new Properties();
    private static Properties status = new Properties();
    private static Properties user = new Properties();

    static {
        try {
            file.load(new FileInputStream("file.properties"));
            mime.load(new FileInputStream("mime.properties"));
            status.load(new FileInputStream("status.properties"));
            user.load(new FileInputStream("user.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getfilePath(String key){
        return file.getProperty(key);
    }
}

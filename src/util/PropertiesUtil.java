package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
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

    public static String get(String filenName,String key){
        if("file".equals(filenName)){
            return getfile(key);
        }else if("mime".equals(filenName)){
            return getmime(key);
        }else if("status".equals(filenName)){
            return getstatus(key);
        }else if("user".equals(filenName)){
            return getuser(key);
        }else{
            return null;
        }
    }

    private static String getuser(String key){
        return user.getProperty(key);
    }

    private static String getstatus(String key){
        return status.getProperty(key);
    }

    private static String getmime(String key){
        return mime.getProperty(key);
    }

    private static String getfile(String key){
        return file.getProperty(key);
    }
}

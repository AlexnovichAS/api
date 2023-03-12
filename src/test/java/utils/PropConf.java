package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropConf {
    public static Properties prop = new Properties();

    static {
        try (InputStream input = new FileInputStream("src/test/resources/properties/conf.properties")) {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String nameKey) {
        return prop.getProperty(nameKey);
    }
}

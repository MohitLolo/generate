package util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Ming
 * @version 1.0
 * @date 2019/3/12 9:56
 * @describe
 */
public class PropertiesUtil {
    private Properties config = null;

    public PropertiesUtil(InputStream in) {
        config = new Properties();
        try {
            config.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        if (config.containsKey(key)) {
            String value = config.getProperty(key);
            return value;
        }else {
            return "";
        }
    }

    public int getValueInt(String key) {
        String value = getValue(key);
        int valueInt = 0;
        try {
            valueInt = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return valueInt;
        }
        return valueInt;
    }

    public Map<String,String> toMap() {
        Map<String,String> map = new HashMap<>();
        //! K toUpperCase
        this.config.stringPropertyNames().forEach(k->map.put(k.toUpperCase(),this.getValue(k)));
        return map;
    }
}

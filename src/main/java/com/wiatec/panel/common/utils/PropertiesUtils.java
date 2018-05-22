package com.wiatec.panel.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author patrick
 */
public class PropertiesUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    private static Properties properties;

    static {
        String fileName = "panel.properties";
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
        } catch (IOException e) {
            logger.error("properties load error", e);
        }
    }

    public static String get(String key){
        String value = properties.getProperty(key.trim());
        if(TextUtil.isEmpty(value)){
            return "";
        }
        return value.trim();
    }

    public static String get(String key, String defaultValue){
        String value = properties.getProperty(key.trim());
        if(TextUtil.isEmpty(value)){
            value = defaultValue;
        }
        return value.trim();
    }


}

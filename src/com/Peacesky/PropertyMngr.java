package com.Peacesky;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by lhongda on 2015/6/10.
 */
public class PropertyMngr {
    static Properties props = new Properties();

    static {
        try {
            props.load(PropertyMngr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 封闭构造方法 使得无法被new 只能被直接调用静态方法
    private PropertyMngr() {
    }

    ;

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}

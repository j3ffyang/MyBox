package mara.mybox.tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import mara.mybox.objects.CommonValues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Author Mara
 * @CreateDate 2018-6-9 7:46:58
 * @Description
 * @License Apache License Version 2.0
 */
public class ConfigTools {

    private static final Logger logger = LogManager.getLogger();

    public static String readConfigValue(String key) {
        try {
            String value = null;
            try (InputStream in = new BufferedInputStream(new FileInputStream(CommonValues.UserConfigFile))) {
                Properties conf = new Properties();
                conf.load(in);
                value = conf.getProperty(key);
            }
            return value;
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    public static boolean writeConfigValue(String key, String value) {
        try {
            Properties conf = new Properties();
            InputStream in = new FileInputStream(CommonValues.UserConfigFile);
            conf.load(in);
            in.close();
            OutputStream out = new FileOutputStream(CommonValues.UserConfigFile);
            if (value == null) {
                conf.remove(key);
            } else {
                conf.setProperty(key, value);
            }
            conf.store(out, "Update " + key);
            out.close();
            return true;
        } catch (Exception e) {
//            logger.error(e.toStsring());
            return false;
        }
    }

}
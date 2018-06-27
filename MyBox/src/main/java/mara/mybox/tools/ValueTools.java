package mara.mybox.tools;

/**
 * @Author Mara
 * @CreateDate 2018-6-11 17:43:53
 * @Description
 * @License Apache License Version 2.0
 */
public class ValueTools {

    public static String fillNumber(int value, int digit) {
        String v = value + "";
        for (int i = v.length(); i < digit; i++) {
            v = "0" + v;
        }
        return v;
    }

    public static double roundDouble3(double invalue) {
        return (double) Math.round(invalue * 1000) / 1000;
    }

    public static double roundDouble2(double invalue) {
        return (double) Math.round(invalue * 100) / 100;
    }

    public static double roundDouble4(double invalue) {
        return (double) Math.round(invalue * 10000) / 10000;
    }
}
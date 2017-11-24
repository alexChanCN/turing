package cn.edu.hdu.lab505.tlts.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hhx on 2017/1/16.
 */
public class StringUtil {
    public static String getNumbers(String s) {
        String regEx = "[^0-9]";
        return getString(s, regEx);
    }

    public static String getWords(String s) {
        String regEx = "[^\u4E00-\u9FA5]+";
        return getString(s, regEx);
    }

    public static String getString(String s, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.replaceAll("").trim();
    }
}

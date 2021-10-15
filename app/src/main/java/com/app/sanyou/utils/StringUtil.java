package com.app.sanyou.utils;

public class StringUtil {

    public static boolean isNull(String str){
        return (str == null || "".equals(str)) ? true : false;
    }

    public static boolean isNotNull(String str){
        return !isNull(str);
    }
}

package com.ygc.miaosha.utils;

public class StringUtil {
    public static boolean isNotBlank(String str){
        if (str != null && !str.trim().isEmpty()){
            return true;
        }
        return false;
    }
    public static boolean isBlank(String str){
        return !isNotBlank(str);
    }
}

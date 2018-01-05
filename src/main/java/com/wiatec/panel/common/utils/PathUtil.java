package com.wiatec.panel.common.utils;

import javax.servlet.http.HttpServletRequest;

public class PathUtil {

    public static void main (String [] args){
        System.out.println(getUserDir());
        System.out.println(getClassPath());
    }


    public static String getUserDir(){
        return System.getProperty("user.dir");
    }

    public static String getClassPath(){
        return Class.class.getClass().getResource("/").getPath();
    }

    public static String getRealPath(HttpServletRequest request){
        return request.getSession().getServletContext().getRealPath("");
    }
}

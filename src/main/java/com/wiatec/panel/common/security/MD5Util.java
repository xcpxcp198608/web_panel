package com.wiatec.panel.common.security;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * token util
 * @author patrick
 */
public class MD5Util {

    public static String create16(String s1){
        return DigestUtils.md5Hex(s1 + System.currentTimeMillis()).substring(8,24);
    }

    public static String create32(String s1){
        return DigestUtils.md5Hex(s1 + System.currentTimeMillis());
    }

    public static String create64(String s1){
        return DigestUtils.md5Hex(s1 + System.currentTimeMillis()) +
                DigestUtils.md5Hex(s1 + System.currentTimeMillis() + System.currentTimeMillis()) ;
    }

    public static void main (String [] args){
        String t = create64("dsfdss");
        System.out.println(t);
    }

}

package com.wiatec.panel.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * token util
 * @author patrick
 */
public class TokenUtil {

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

    public static boolean macValidate(String token){
        String tokenAfterDecrypt = AESUtil.decrypt(token, AESUtil.KEY);
        return tokenAfterDecrypt.startsWith("5c:41:e7");
    }

    public static String createKey(int i){
        return TokenUtil.create16("www.wiatec.com");
    }

    public static void main (String [] args){
        String t = create64("dsfdss");
        System.out.println(t);
    }

}

package com.wiatec.panel.common.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author patrick
 */
public class RSAUtil {

    private final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    private static KeyPair generateKeyPair(){
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        return keyPair;
    }

    private static String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s.toUpperCase());
        }

        return sb.toString();
    }

    private static byte[] str2ByteArray(String s) {
        int byteArrayLength = s.length() / 2;
        byte[] b = new byte[byteArrayLength];
        for (int i = 0; i < byteArrayLength; i++) {
            byte b0 = (byte) Integer.valueOf(s.substring(i * 2, i * 2 + 2), 16)
                    .intValue();
            b[i] = b0;
        }
        return b;
    }


    private static RSAPrivateKey genratePrivateKey(){
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) generateKeyPair().getPrivate();
        String privateKey = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        System.out.println(privateKey);
        return rsaPrivateKey;
    }

    private static RSAPublicKey genratePublicKey(){
        RSAPublicKey rsaPublicKey = (RSAPublicKey) generateKeyPair().getPublic();
        String publicKey = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        System.out.println(publicKey);
        return rsaPublicKey;
    }

    //私钥加密
    public static String privateEncrypt(RSAPrivateKey rsaPrivateKey, String value){
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey1 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey1);
            byte[] result = cipher.doFinal(value.getBytes());
            return byte2HexStr(result);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    //公钥解密
    public static String publicDecrypt(RSAPublicKey rsaPublicKey, String encodeString){
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(str2ByteArray(encodeString));
            return new String(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }



    //公钥加密
    public static String publicEncrypt(RSAPublicKey rsaPublicKey, String value){
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(value.getBytes());
            return byte2HexStr(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    //私钥解密
    public static String privateDecrypt(RSAPrivateKey rsaPrivateKey, String encodeString){
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey1 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey1);
            byte[] result = cipher.doFinal(str2ByteArray(encodeString));
            return new String(result);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void main (String [] args){
        String encrypt = privateEncrypt(genratePrivateKey(), "sdfsfsffsd");
        System.out.println(encrypt);
        String decrypt = publicDecrypt(genratePublicKey(), encrypt);
        System.out.println(decrypt);
    }


}

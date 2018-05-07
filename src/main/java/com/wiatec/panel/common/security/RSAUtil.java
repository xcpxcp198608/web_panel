package com.wiatec.panel.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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

    private static Map<String, RSAKey> generateKeyMap(){
        KeyPair keyPair = generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKey = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());
        System.out.println(publicKey);
        System.out.println(privateKey);
        Map<String, RSAKey> keyMap = new HashMap<>(2);
        keyMap.put("private", rsaPrivateKey);
        keyMap.put("public", rsaPublicKey);
        return keyMap;
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
            byte[] result = cipher.doFinal(value.getBytes(Charset.forName("utf-8")));
            return Base64.getEncoder().encodeToString(result);
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
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(encodeString));
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
            return Base64.getEncoder().encodeToString(bytes);
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
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(encodeString));
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
        Map<String, RSAKey> stringRSAKeyMap = generateKeyMap();

        String encrypt = privateEncrypt((RSAPrivateKey) stringRSAKeyMap.get("private"), "sdfsfsffsd");
        System.out.println(encrypt);
        String decrypt = publicDecrypt((RSAPublicKey) stringRSAKeyMap.get("public"), encrypt);
        System.out.println(decrypt);





        String encrypt1 = publicEncrypt((RSAPublicKey) stringRSAKeyMap.get("public"), "sdfsfsffsd");
        System.out.println(encrypt1);
        String decrypt1 = privateDecrypt((RSAPrivateKey) stringRSAKeyMap.get("private"), encrypt1);
        System.out.println(decrypt1);
    }


}

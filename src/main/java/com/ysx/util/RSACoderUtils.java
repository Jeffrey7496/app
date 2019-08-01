package com.ysx.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;


/**
 * RSA非对称加密
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/2 16:36
 */
public class RSACoderUtils {
    public static final String KEY_ALGORITHM = "RSA";
    private static final Logger LOGGER = LoggerFactory.getLogger(RSACoderUtils.class);


    /**
     * 编码
     * @param bytes
     * @return
     */
    public static String encryptByBase64(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 解码
     * @param data
     * @return
     */
    public static byte[] decryptByBase64(String data){
        return Base64.decodeBase64(data);
    }

    /**
     * 生成密码对，使用Base64进行编码
     * @return JSONObject
     * @throws Exception
     */
    public static JSONObject initKey() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info("生成RSA密码对失败：{}",e);
            return null;
        }
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        String publicKey = encryptByBase64(keyPair.getPublic().getEncoded());
        String privateKey = encryptByBase64(keyPair.getPrivate().getEncoded());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("publicKey",publicKey);
        jsonObject.put("privateKey",privateKey);
        return jsonObject;
    }

    /**
     * 解密
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptData(String data,String privateKey) throws Exception {
        byte[] bytes = decryptByBase64(privateKey);
        // 获取真正私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKeyObj = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE,privateKeyObj);
        byte[] finalBytes = cipher.doFinal(decryptByBase64(data));
        return new String(finalBytes);
    }
  /*  public static String encrypt(String data,String publicKey){
        byte[] bytes = de
    }*/
}

package aes;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/28 10:26
 */
public class AESUtils {
    /**
     * 加密
     * @param content 待加密内容
     * @param password  加密密钥
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        try {
            Key key= initKeyForAES(password);
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            Key key= initKeyForAES(password);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * linux需要
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static Key initKeyForAES(String password) throws NoSuchAlgorithmException {
        if (null == password || password.length() == 0) {
            throw new NullPointerException("key is null");
        }
        SecretKeySpec key;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException();
        }
        return key;
    }


    //编码函数
    public static String encode(String content, String key) throws Exception {
        byte[] encrypt = encrypt(content, key);
        return Base64.encode(encrypt);
    }
    //解码函数
    public static String decode(String encode, String key) throws Exception {
        byte[] encrypt = Base64.decode(encode);
        byte[] content = decrypt(encrypt, key);
        return new String(content);
    }



    //0-正常使用
    public static void main(String[] args) throws Exception{
        String content = "holybin";
        String password = "12345678";

        System.out.println("加密前1：" + content);
        byte[] encryptResult1 = encrypt(content, password); //普通加密

        byte[] decryptResult1 = decrypt(encryptResult1,password);   //普通解密
        System.out.println("解密后1：" + new String(decryptResult1));

        System.out.println("\n加密前2：" + content);
        String encryptResult2 = encode(content, password);  //先编码再加密
        System.out.println("加密后2：" + encryptResult2);
        String decryptResult2 = decode(encryptResult2, password);   //先解码再解密
        System.out.println("解密后2：" + decryptResult2);
    }
}

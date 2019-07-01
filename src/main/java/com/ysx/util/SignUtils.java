package com.ysx.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * sign签名工具
 * 先SHA256 再MD5
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/28 16:36
 */
public class SignUtils {
    public static String signBySha256AndMd5(String str){
        String sha256 = DigestUtils.sha256Hex(str);
        return DigestUtils.md5Hex(sha256);
    }
}

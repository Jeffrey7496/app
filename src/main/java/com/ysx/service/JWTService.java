package com.ysx.service;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ysx.constants.Constants;
import com.ysx.util.RSACoderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * jtw 工具包
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/3 13:27
 */
@Component
public class JWTService {
    private static final long EXPIRE_TIME = 15*60*1000;// 设置过期时间
    @Autowired
    private StringRedisTemplate stringRedisTemplate;// 直接缓存

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTService.class);
    /**
     * 生成token，并缓存token密码
     * @param account
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    public String generateToken(String account,long id) throws Exception {
        // 附带公钥
        JSONObject key = RSACoderUtils.initKey();
        if (key==null){
            LOGGER.info("生成RSA密钥失败");
            throw new Exception("生成RSA密钥失败");
        }
        String publicKey = key.getString("publicKey");
        String privateKey = key.getString("privateKey");

        // token密码
        String tokenSecret = privateKey;
        // 过期时间
        Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);// 设置过期时间
        // 秘钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
        // 设置头信息
        Map<String,Object> header = new HashMap<>(2);
        header.put("typ","jtw");
        header.put("alg","HMAC256");
        // 附带account/id信息，生成签名


        String token = JWT.create()
                .withHeader(header)
                .withClaim("account",account)
                .withClaim("id",id)
                .withExpiresAt(date)
                .sign(algorithm)
                +"##"+publicKey;
        // 缓存token密码--15分钟过期--用户使用该token最后的公钥进行加密，然后后台获取请求后使用该私钥进行解密
        stringRedisTemplate.opsForValue().set(Constants.RedisKey.APP_TOKEN_SECRET_PREFIX+token,tokenSecret,EXPIRE_TIME,TimeUnit.MILLISECONDS);
        return token;
    }

    public boolean verify(String token){
        try {
            String secret = stringRedisTemplate.opsForValue().get(Constants.RedisKey.APP_TOKEN_SECRET_PREFIX+token);
            if (StringUtils.isEmpty(secret)){
                return false;
            }
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            if (decodedJWT!=null){
                return true;
            }
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.info("解析token失败");
            return false;
        }
    }
}

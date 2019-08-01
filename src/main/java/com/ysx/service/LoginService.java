package com.ysx.service;

import com.alibaba.fastjson.JSONObject;
import com.ysx.dao.LoginMapper;
import com.ysx.dto.ResultInfo;
import com.ysx.util.JsonCheckUtils;
import com.ysx.util.RSACoderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * 登录服务
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/2 17:49
 */
@Service
public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private JWTService jwtService;
    public ResultInfo<JSONObject> getPublicKey() {
        ResultInfo<JSONObject> resultInfo = new ResultInfo();
        JSONObject key = RSACoderUtils.initKey();
        if (key==null){
            resultInfo.failOfSystem("生成密码对失败");
            return resultInfo;
        }
        // 缓存密钥
        String publicKey = key.getString("publicKey");
        String privateKey = key.getString("privateKey");
        redisTemplate.opsForValue().set(publicKey,privateKey);
        redisTemplate.expire(publicKey,2,TimeUnit.HOURS);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("publicKey",publicKey);
        resultInfo.success("获取公钥成功",jsonObject);
        return resultInfo;
    }

    public ResultInfo<JSONObject> loginByPass(JSONObject param) {
        ResultInfo<JSONObject> resultInfo = new ResultInfo<>();
        String failMsg = JsonCheckUtils.checkEmpty(param,"oaAccount","oaPassword","publicKey");
        if (!StringUtils.isEmpty(failMsg)){// 如果不成功直接返回
            resultInfo.failOfBusiness(failMsg);
            return resultInfo;
        }
        String oaAccount = param.getString("oaAccount");
        String oaPassword = param.getString("oaPassword");
        String publicKey = param.getString("publicKey");
        String account;
        String password;
        try {
            String privateKey = redisTemplate.opsForValue().get(publicKey);
            account = RSACoderUtils.decryptData(oaAccount,privateKey);
            password = RSACoderUtils.decryptData(oaPassword,privateKey);

        } catch (Exception e) {
            LOGGER.info("RSA解密异常：{}",e);
            resultInfo.failOfSystem("RSA解密异常");
            return resultInfo;
        }
        JSONObject user = loginMapper.getUserCount(account,password);
        if (user==null){
            resultInfo.failOfBusiness("账户密码验证失败");
            return resultInfo;
        }
        // 缓存token--将信息传给前端后，每次携带token进行验证--TODO--token的作用是通行证
        String token;
        try {
            token = jwtService.generateToken(account,user.getLong("id"));
        } catch (Exception e) {
            LOGGER.info("生成token失败：{}",e);
            resultInfo.failOfSystem("生成token失败");
            return resultInfo;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token",token);
        resultInfo.success("帐号密码验证成功",user);
        return resultInfo;
    }
}

package com.ysx.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ysx.dto.ResultInfo;
import com.ysx.service.LoginService;
import com.ysx.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录模块
 * 密码登录采用RSA非对称加密，将明文账户密码转成密文进行传输--作用：密码即使被截获也不会被解析；如果黑客直接获取后 转发怎么办？没法办~~
 * 验证码登录
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/2 16:00
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    /**
     * 登录之前获取公钥，
     * @return
     */
    @PostMapping("/getPublicKey")
    public ResultInfo<JSONObject> getPublicKey(){// 每次都生成一个新的秘钥，共一个人使用
        ResultInfo<JSONObject> resultInfo;
        resultInfo = loginService.getPublicKey();
        return resultInfo;
    }

    /**
     * 根据账户密码登录
     * @param param
     * @return
     */
    @PostMapping("/loginByPass")
    public ResultInfo<JSONObject> loginByPass(@RequestBody JSONObject param){
        ResultInfo<JSONObject> resultInfo;
        String uuid = CommonUtils.getUUID();
        LOGGER.info("start===========>uuid:[{}],desc:[{}],param:[{}]",uuid,"密码登录",JSON.toJSONString(param));
        resultInfo = loginService.loginByPass(param);
        LOGGER.info("end===========>uuid:[{}],desc:[{}],result:[{}]",uuid,"密码登录",JSON.toJSONString(resultInfo));
        return resultInfo;
    }

    /**
     * 发送验证码登录
     * @param param
     * @return
     */
    @PostMapping("/sendValidCode")
    public ResultInfo<JSONObject> sendValidCode(@RequestBody JSONObject param){
        ResultInfo<JSONObject> resultInfo;
        return null;
    }

}

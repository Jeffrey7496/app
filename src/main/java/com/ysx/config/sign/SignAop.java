package com.ysx.config.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ysx.config.annotation.Sign;
import com.ysx.constants.Constants;
import com.ysx.dto.ResultInfo;
import com.ysx.util.CommonUtils;
import com.ysx.util.SignUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * sign 切面
 * 采用的对称加密，使用sign签名
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/28 13:46
 */
@Aspect
@Order(6)
@Component
public class SignAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignAop.class);

    @Pointcut("@annotation(com.ysx.config.annotation.Sign)")
    public void safePointcut(){};

    @Around("safePointcut()")
    public Object around(ProceedingJoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();// 获取请求，目的：得到请求参数（不能只获取 注解方法 的参数，因为可能不全）
        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Sign sign = methodSignature.getMethod().getAnnotation(Sign.class);

            if (sign!=null){// 进行验证
                if(sign.ipCheck()){
                    // 白名单
                }
                String method = request.getMethod();// post/get标识
                if (!StringUtils.isEmpty(method)){
                    String[] signFields = sign.signFields();//获取加密的字段
                    JSONObject signObj = new JSONObject();// 存储sign参数及加密密码
                    String secret = request.getHeader("secret");// 获取加密密码--这个到底什么用?~

                    //获取request中的参数信息
                    if ("post".equalsIgnoreCase(method)){// 如果是post方法
                        BufferedReader reader = request.getReader();
                        StringBuilder sb = new StringBuilder();// 不涉及线程安全
                        String line = "";
                        while ((line=reader.readLine())!=null){
                            sb.append(line);
                        }
                        JSONObject paramMap = JSON.parseObject(sb.toString());
                        for (String key:signFields ) {
                            signObj.put(key,paramMap.getString(key));
                        }
                    }else if("get".equalsIgnoreCase(method)){
                        for (String key:signFields ) {
                            signObj.put(key,request.getParameter(key));
                        }
                    }
                    signObj.put("secret",secret);
                    String sort = CommonUtils.sort(signObj);
                    LOGGER.debug(signObj.toJSONString());
                    LOGGER.debug(sort);
                    String mySignStr = SignUtils.signBySha256AndMd5(sort);
                    String clientSignStr = request.getParameter(sign.checkField());
                    LOGGER.debug("客户端签名============"+clientSignStr);
                    LOGGER.debug("服务端签名============"+mySignStr);
                    if (!mySignStr.equals(clientSignStr)){// 如果不同，则不行
                        ResultInfo<JSONObject> result = new ResultInfo<>();
                        result.quickSet(Constants.Msg.ILLEGAL_ARGUMENT_MSG,false);
                        return result;
                    }else {
                        LOGGER.debug("签名验证通过");
                        return joinPoint.proceed();
                    }
                }
            }
        }  catch (Throwable throwable) {
            throwable.printStackTrace();
            ResultInfo resultInfo = new ResultInfo();
            LOGGER.info("异常",throwable);
            resultInfo.quickSet("系统错误",false);
            return resultInfo;
        }
        LOGGER.debug("=============> jtp method name:{}",joinPoint.toLongString());
        return null;
    }
}

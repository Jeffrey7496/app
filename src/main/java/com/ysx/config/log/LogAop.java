package com.ysx.config.log;

import com.alibaba.fastjson.JSON;
import com.ysx.util.CommonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 切面处理日志
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/18 17:33
 */
@Aspect
@Component
public class LogAop {
    private static Logger logger = LoggerFactory.getLogger(LogAop.class);

    @Pointcut("execution(public * com.ysx.web..*.*(..))")
    public void webLog() {}
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint)throws Exception{
        Object res = null;
        String uuid = CommonUtils.getUUID();
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String token = request.getParameter("token");
            //记录下请求内容
            logger.info("TOKEN:{},UUID:{},URL:{},HTTP_METHOD:{},IP:{},CLASS_METHOD:{},ARGS:{}",
                    token,
                    uuid,
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    CommonUtils.getServletRemoteIp(request),
                    joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()));

            //开始执行请求
            res = joinPoint.proceed();

            //记录返回结果
            logger.info("TOKEN:{},UUID:{},RESPONSE : {}", token, uuid, JSON.toJSONString(res));
        }catch (Throwable throwable) {
            logger.info("===========>throwable",throwable);
        }
        return res;
    }
}

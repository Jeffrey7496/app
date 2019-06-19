package com.ysx.config.page;

import com.github.pagehelper.PageHelper;
import com.ysx.config.datasource.DataSourceAop;
import com.ysx.config.datasource.DataSourceTypeHolder;
import com.ysx.constants.Constants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * pageHelper分页AOP
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/19 13:48
 */
@Aspect
@Component
public class pageHelperAop {
    private static Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

    @Pointcut("execution(public * com.ysx.service..*.*list(..))")
    public void pageHelper() {}

    @Around("pageHelper()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] names = methodSignature.getParameterNames();// 获取参数名称
        Object[] values = joinPoint.getArgs();//获取参数名
        int pageNum=0;
        int pageSize=0;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals("pageNum")){
                pageNum= (int) values[i];
            }
            if (names[i].equals("pageSize")){
                pageSize= (int) values[i];
            }
        }
        if (pageNum==0&&pageSize==0){
            pageNum=Constants.Page.PAGE_NUM;
            pageSize=Constants.Page.PAGE_SIZE;
        }
        PageHelper.startPage(pageNum,pageSize);
        logger.info("pageHelper设置分页查询参数：pageNum[{}],pageSize[{}]",pageNum,pageSize);
        return joinPoint.proceed();
    }
}

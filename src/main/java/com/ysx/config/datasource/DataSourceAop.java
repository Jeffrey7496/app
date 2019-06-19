package com.ysx.config.datasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 切面类，用于切换 ThreadLocal中的数据源类型
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/18 12:17
 */
@Aspect
@Component
@Order(1) //最高优先级
public class DataSourceAop {
    private static Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

    @Pointcut("execution(public * com.ysx.web..*.*(..))")
    public void webLog() {}

    @Before("@annotation(com.ysx.config.annotation.Write)")
    public void setWriteDataSourceType(){
        logger.info("dataSource 切换到：Write");
        DataSourceTypeHolder.change2Write();
    }
    @Before("@annotation(com.ysx.config.annotation.Read)")
    public void setReadDataSourceType(){
        logger.info("dataSource 切换到：Read");
        DataSourceTypeHolder.change2Read();
    }
}

package com.ysx.config.transaction;

import com.github.pagehelper.PageHelper;
import com.ysx.config.datasource.DataSourceBeanConfig;
import com.ysx.config.datasource.DataSourceProxy;
import com.ysx.config.datasource.DataSourceTypeEnum;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * MyBatis配置+事物
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/18 15:55
 */
@Configuration
@AutoConfigureAfter({ DataSourceBeanConfig.class })// 创建完datasource实例后才能处理此类
@MapperScan(basePackages = "com.ysx.dao")
public class MyBatisConfig {
    private static Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    /**
     * 将多个的datasource注入到该bean，形成一个最终的DS代理类，为sqlSession/事物 做准备
     * @param wds
     * @param rds
     * @return
     */
    @Bean
    public DataSourceProxy dataSourceProxy(@Qualifier("writeDataSource") DataSource wds,
                                           @Qualifier("readDataSource") DataSource rds) {
        logger.info("-------------------- dataSourceProxy init ---------------------");
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DataSourceTypeEnum.write.getType(), wds);
        targetDataSources.put(DataSourceTypeEnum.read.getType(), rds);

        DataSourceProxy proxy = new DataSourceProxy();
        proxy.setDefaultTargetDataSource(wds);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    /**
     * 注入sqlSessionFactory，MyBatis使用
     * @param ds
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceProxy") DataSourceProxy ds) throws Exception {
        logger.info("-------------------- sqlSessionFactory init ---------------------");
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);
        fb.setPlugins(new Interceptor[]{getPageHelper()} );
        return fb.getObject();
    }

    @Bean
    public PageHelper getPageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("autoRuntimeDialect","true");
        properties.setProperty("supportMethodsArguments","true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}

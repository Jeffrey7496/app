package com.ysx.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 配置数据源，配置后才会生成对应的数据源实例
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/18 11:46
 */
@Configuration
public class DataSourceBeanConfig {
    private static Logger logger = LoggerFactory.getLogger(DataSourceBeanConfig.class);

    @Value("${datasource.type}")
    private Class <? extends DataSource> dataSourceType;// 指定数据源类：此处使用阿里Druid数据源,不指定默认

    @Bean(name = "writeDataSource",destroyMethod = "close")//BasicDataSource提供了close()方法关闭数据源
    @Primary
    @ConfigurationProperties("datasource.write")// 将读库信息配置-- 直接配置,如何配置进去的
    public DataSource writeDataSource(){
        logger.info("===================writeDataSource init===================");

        return DataSourceBuilder.create().type(dataSourceType).build();
    }
    @Bean(name = "readDataSource",destroyMethod = "close")//BasicDataSource提供了close()方法关闭数据源
    @ConfigurationProperties("datasource.read")// 将读库信息配置
    public DataSource readDataSource(){
        logger.info("===================readDataSource init===================");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }
}

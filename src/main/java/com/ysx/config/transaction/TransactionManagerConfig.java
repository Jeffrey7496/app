package com.ysx.config.transaction;

import com.ysx.config.datasource.DataSourceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/18 15:53
 */
@Configuration
@AutoConfigureAfter({ MyBatisConfig.class })
@EnableTransactionManagement
public class TransactionManagerConfig {
    private static Logger logger = LoggerFactory.getLogger(TransactionManagerConfig.class);
    /**
     * 自定义事务 MyBatis自动参与到spring事务管理中，无需额外配置，
     * 只要org.mybatis.spring.SqlSessionFactoryBean引用的数据源与DataSourceTransactionManager引用的数据源一致即可，否则事务管理会不起作用。
     *
     * @return
     */
    @Bean  // dataSource事物管理器，Spring使用（@Transaction）
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSourceProxy") DataSourceProxy ds) {
        logger.info("-------------------- transactionManager init ---------------------");
        return new DataSourceTransactionManager(ds);
    }
}

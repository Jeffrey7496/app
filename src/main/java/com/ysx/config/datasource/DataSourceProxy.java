package com.ysx.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自动判断数据源,返回当前数据源类型,父类回调方法，返回对应的实例（模板模式）
 * 当使用数据库的时候会自动调用发方法判断，选择数据库
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/18 12:08
 */
public class DataSourceProxy extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {// 父类方法会将数据源中的关键字存储，然后调用该方法获取关键字，再去不同的数据源实例中匹配，比如read数据源，关键字read
        return DataSourceTypeHolder.getDBType();// 根据当前返回read或者write返回数据源，自动判断
    }
}

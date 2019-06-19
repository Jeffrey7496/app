package com.ysx.config.datasource;

/**
 * 切换库，使用ThreadLocal持有当前线程库,以及进行库的切换
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/18 11:26
 */
public class DataSourceTypeHolder {
    private static final ThreadLocal<String> localDBType = new ThreadLocal<>();
    public static String getDBType(){
        return localDBType.get();
    }

    /**
     * 切换到读库
     */
    public static void change2Read(){
        localDBType.set(DataSourceTypeEnum.read.getType());
    }

    /**
     * 切换到写库
     */
    public static void change2Write(){
        localDBType.set(DataSourceTypeEnum.write.getType());
    }
}

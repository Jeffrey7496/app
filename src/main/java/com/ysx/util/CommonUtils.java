package com.ysx.util;

import java.util.UUID;

/**
 * uuid工具
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/13 17:10
 */
public class CommonUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}

package com.ysx.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

/**
 * ali JSONObject 检测类
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/13 16:21
 */
public class JsonCheckUtils {
    /**
     * 检查对应key是否为空，则返回空的信息，如果都不是空，则返回null
     * json key-value必须都是字符类型
     * @param jsonObject
     * @param keys
     * @return
     */
    public static String checkEmpty(JSONObject jsonObject, String... keys){
        for (String key:keys) {
            if(StringUtils.isEmpty(jsonObject.getString(key))){
                return key+"不能为空";
            }
        }
        return "";
    }
}

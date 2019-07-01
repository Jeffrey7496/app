package com.ysx.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
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

    /**
     * 获取IP信息
     * @param request
     * @return
     */
    public static String getServletRemoteIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL_Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknow".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return  ip;
    }

    /**
     * 对map<String,String>的元素key按字母表顺序进行排序，返回字符串，格式：key=value&key=value
     * @param map
     * @return
     */
    public static String sort(Map map){
        Set<String> keys = map.keySet();
        String[] arr = keys.toArray(new String[]{});// 注意格式
        Arrays.sort(arr);// 排序
        StringBuilder sb = new StringBuilder();
        for (String key:arr) {
            String value = (String) map.get(key);
            if (!StringUtils.isEmpty(value)){
                sb.append(key+"="+value+"&");
            }
        }
        String result = sb.deleteCharAt(sb.lastIndexOf("&")).toString();
        return result;
    }
}

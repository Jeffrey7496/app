package com.ysx.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
}

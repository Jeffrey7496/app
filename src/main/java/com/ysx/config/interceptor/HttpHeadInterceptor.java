package com.ysx.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ysx.dto.ResultInfo;
import com.ysx.service.JWTService;
import com.ysx.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 请求验证，例如登录验证
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/3 14:22
 */
@Component
public class HttpHeadInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHeadInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = CommonUtils.getUUID();
        LOGGER.info("",uuid);
        String token = request.getHeader("token");

        // 无法注入，必须使用的时候获取bean
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        JWTService jwtService = (JWTService) factory.getBean("jwtService");

        if (!jwtService.verify(token)){
            ResultInfo<JSONObject> resultInfo = new ResultInfo<>();
            resultInfo.failOfBusiness("登录验证失败");
            printResponse(response,resultInfo);
            return false;
        }
        return super.preHandle(request, response, handler);
    }

    private void printResponse(HttpServletResponse response, ResultInfo<JSONObject> resultInfo) throws IOException {
        response.setContentType("application/json:charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(JSON.toJSONString(resultInfo));
    }
}

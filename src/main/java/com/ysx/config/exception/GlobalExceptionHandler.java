package com.ysx.config.exception;

import com.ysx.dto.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理类：相当于环controller切面编程
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/13 15:56
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultInfo<String> defaultErrorHandler(HttpServletRequest request,Exception e){
        ResultInfo<String> result= new ResultInfo<>();
        LOGGER.info("===========> GlobalException ",e);
        result.failOfSystem("全局异常");
        result.setInfo(e.getMessage());
        result.setReqUrl(request.getRequestURL().toString());
        LOGGER.info(e.getMessage());
        return result;
    }
}

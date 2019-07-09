package com.ysx.constants;

/**
 * 常量类
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/13 15:28
 */
public class Constants {
    // 编码常量
    public static class Code {
        public static final String FAIL_SYSTEM_CODE = "-1000";// 系统异常--开发人员处理
        public static final String FAIL_BUSINESS_CODE = "-10";// 正常业务终止--消息展现给客户
        public static final String SUCCESS_CODE = "0";
    }
    public static class Msg {
        public static final String FAIL_SYSTEM_MSG = "系统错误";
        public static final String SUCCESS_MSG = "操作成功";
        public static final String ILLEGAL_ARGUMENT_MSG = "参数非法，请检查后重试";
    }

    public static class RedisKey{
        public static final String DEMO_MAP_KEY = "DEMO_LIST_KEY";
        public static final String APP_RSA_PUBLIC_KEY_PREFIX = "APP_RSA_PUBLIC_KEY_PREFIX";
        public static final String APP_TOKEN_SECRET_PREFIX = "APP_TOKEN_SECRET_PREFIX";
    }

    public static class Page{
        public static final int PAGE_NUM = 0;
        public static final int PAGE_SIZE = 10;
    }
}

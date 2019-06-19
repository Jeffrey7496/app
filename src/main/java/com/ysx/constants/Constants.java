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
        public static final String FAIL_CODE = "-100";
        public static final String SUCCESS_CODE = "0";
    }
    public static class Msg {
        public static final String FAIL_MSG = "失败";
        public static final String SUCCESS_MSG = "成功";
    }

    public static class RedisKey{
        public static final String DEMO_MAP_KEY = "DEMO_LIST_KEY";
    }

    public static class Page{
        public static final int PAGE_NUM = 0;
        public static final int PAGE_SIZE = 10;
    }
}

package com.ysx.config.annotation;

import java.lang.annotation.*;

/**
 * 写库标志，标记后，切换为写数据库
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/18 11:05
 */
@Target({ElementType.TYPE,ElementType.METHOD}) // 修饰范围
@Retention(RetentionPolicy.RUNTIME) // 保留策略
@Documented // 文档
public @interface Write {
    String value() default "";
}

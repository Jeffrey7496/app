package com.ysx.config.annotation;

import java.lang.annotation.*;

/**
 * token验证注标识注解--加密注解--
 * 只能防止修改，不能防止被查看--明文传输，可以被截获，但是不能修改，因为会进行对比--还是有风险，因为如果获取信息后，不修改，但是不断发送，这样就会不断请求
 * 如果消息被截获后，黑客不断模拟发送怎么办？--只能分步模式，比如支付宝：第一步：扫码后客户端发送请求解析，后台准备接收密码，然后给客户端提示密码；第二步：客户端再发送密码，后台接收后闭合操作；
 * 另外，防止黑客不断的进行第一步操作攻击，我们需要对ip/账号 请求进行频率限制
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/25 16:29
 */
@Documented
@Target(ElementType.METHOD)
@Inherited// 继承标识，子类实现父类中带有Sign注解的方法，则子类对应的实现方法也自动拥有Sign注解
@Retention(RetentionPolicy.RUNTIME)
public @interface Sign {
    String[] signFields() default {};// 加密需要的字段
    String checkField() default "token";// 计算之后的密文
    boolean ipCheck() default false;// 是否需要ip核查
}

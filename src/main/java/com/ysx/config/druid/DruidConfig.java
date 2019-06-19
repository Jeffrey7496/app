package com.ysx.config.druid;


import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义Druid监控
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/18 14:03
 */

@Configuration
public class DruidConfig {
    /**
     * 注册一个StatViewServlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() { // 注册一个servlet-指向druid控制servlet--StatViewServlet，相当于原始的指向方法~
        ServletRegistrationBean srb = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 添加初始化参数：initParams
        // 白名单：没有配置或者为空，则允许所有访问
        srb.addInitParameter("allow", "127.0.0.1");
        // IP黑名单 (存在共同时，deny优先于allow) :
        // - 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        srb.addInitParameter("deny", "192.168.1.73");
        // 登录查看信息的账号密码.
        srb.addInitParameter("loginUsername", "admin");
        srb.addInitParameter("loginPassword", "123456");
        // 是否能够重置数据.禁用HTML页面上的“Reset All”功能
        srb.addInitParameter("resetEnable", "false");
        return srb;
    }

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return
     */
//	@Bean
    public FilterRegistrationBean druidStatFilter() {// 过滤，遇到这些直接pass，相当于原始的filter
        FilterRegistrationBean frb = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则.
        frb.addUrlPatterns("/*");
        // 添加要忽略的格式信息.
        frb.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return frb;
    }
}

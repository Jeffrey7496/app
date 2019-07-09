package com.ysx.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 登录模块
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/3 9:21
 */
@Repository
public interface LoginMapper {
    @Select("select * from user where account=#{account} and password=#{password}")
    JSONObject getUserCount(@Param("account") String account, @Param("password") String password);
}

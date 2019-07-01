package com.ysx.dao;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.ysx.dto.Demo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Demo
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/5/31 10:49
 */
@Repository
public interface DemoMapper {
    @Insert("insert into demo (name) values (#{name})")
    public boolean insert(Demo demo);
    @Select("select * from demo where id = #{id}")
    JSONObject getOne(JSONObject param);
    @Update({
            "<script> ",
            "update demo ",
            "<trim prefix=\"SET\" suffixOverrides=\",\"> ",
            "<if test=\"name!=null\">name=#{name},</if>",
            "</trim>",
            "where id=#{id}",
            "</script> ",
    })
    boolean update(Demo demo);
    @Delete("delete from demo where id =#{id}")
    boolean delete(JSONObject param);
    @Select("select * from demo")
    Page<JSONObject> list();// pageHelper自动加上分页
}

package com.ysx.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ysx.config.annotation.Read;
import com.ysx.config.annotation.Write;
import com.ysx.constants.Constants;
import com.ysx.dao.DemoMapper;
import com.ysx.dto.Demo;
import com.ysx.dto.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/5/31 10:49
 */
@Service
public class DemoService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DemoMapper mapper;
    private static Logger logger = LoggerFactory.getLogger(DemoService.class);
    @Write
    @Transactional
    public ResultInfo save(JSONObject param) {// 增加或修改
        ResultInfo<JSONObject> resultInfo = new ResultInfo<>();//默认 失败
        String id = param.getString("id");
        String name = param.getString("name");
        Demo demo = new Demo();
        demo.setId(id);
        demo.setName(name);
        stringRedisTemplate.delete(Constants.RedisKey.DEMO_MAP_KEY);
        //根据id 判断 新增还是修改
        if (StringUtils.isEmpty(param.getString("id"))){//没有id 则是新增
            boolean b = mapper.insert(demo);
            return resultInfo.quickSet("新增Demo",b);
        }else {//修改
            boolean b = mapper.update(demo);
            return resultInfo.quickSet("修改Demo",b);
        }
    }
    @Read
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true,isolation = Isolation.DEFAULT)
    public ResultInfo info(JSONObject param) {
        ResultInfo<JSONObject> resultInfo = new ResultInfo<>();//默认 失败
        JSONObject jsonObject=null;
        String key = Constants.RedisKey.DEMO_MAP_KEY;
        String id = param.getString("id");
        if (!stringRedisTemplate.opsForHash().hasKey(key,id)){// 如果没有则查询数据库
            jsonObject = mapper.getOne(param);
            //设置缓存
            if (jsonObject!=null){
                stringRedisTemplate.opsForHash().put(key,id,JSON.toJSONString(jsonObject));
                stringRedisTemplate.expire(key,2,TimeUnit.MINUTES);
            }
        }
        if (!stringRedisTemplate.opsForHash().hasKey(key,id)){// 必须查询对应的key
            resultInfo.quickSet("查询Demo",false);
        }else {
            jsonObject = JSONObject.parseObject((String) stringRedisTemplate.opsForHash().get(key,id));
            logger.info("info使用缓存");
            resultInfo.quickSet("查询Demo",jsonObject,true);
        }
        return resultInfo;
    }

    @Write
    @Transactional
    public ResultInfo<JSONObject> delete(JSONObject param) {
        ResultInfo<JSONObject> resultInfo = new ResultInfo<>();//默认 失败
        boolean b = mapper.delete(param);
        resultInfo.quickSet("删除Demo",b);
        if(b){ // 删除对应id
            stringRedisTemplate.opsForHash().delete(Constants.RedisKey.DEMO_MAP_KEY,param.getString("id"));
        }
        return resultInfo;
    }
    @Read
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true,isolation = Isolation.DEFAULT)
    public ResultInfo<JSONObject> list(int pageNum,int pageSize) {
        ResultInfo<JSONObject> resultInfo = new ResultInfo<>();//默认 失败
        Page<JSONObject> page = null;
        page= mapper.list();
        JSONObject data = new JSONObject();
        data.put("list",page.getResult());
        data.put("pages",page.getPages());
        data.put("total",page.getTotal());
        if (page!=null){
            resultInfo.quickSet("查询Demo",data,true);
        }else {
            resultInfo.quickSet("查询Demo",null,false);
        }
        return resultInfo;
    }    @Read
    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true,isolation = Isolation.DEFAULT)
    public ResultInfo<JSONObject> listAll() {
        ResultInfo<JSONObject> resultInfo = new ResultInfo<>();//默认 失败
        Page<JSONObject> page = null;
        String key =Constants.RedisKey.DEMO_MAP_KEY;
        if (!stringRedisTemplate.hasKey(key)){// 如果没有则查询数据库
            page= mapper.list();
            Map<String,String> map = new HashMap<>();
            if (!page.isEmpty()){// 如果不是空，则设置缓存
                for (JSONObject j:
                        page.getResult()) {
                    map.put(j.getString("id"), JSON.toJSONString(j));
                }
                //设置缓存
                stringRedisTemplate.opsForHash().putAll(key,map);
                stringRedisTemplate.expire(key,2,TimeUnit.MINUTES);
            }
        }
        if (stringRedisTemplate.hasKey(key)){
            logger.info("listAll使用缓存");
            //查询缓存--一般不缓存，只有所有都需要的时候才缓存
            List<Object> list = stringRedisTemplate.opsForHash().values(key);
            List<JSONObject> dataList = new ArrayList<>();
            for (Object o:// 转成JSONObject
                    list ) {
                JSONObject jsonObject =JSONObject.parseObject(o.toString());
                dataList.add(jsonObject);
            }
            JSONObject data = new JSONObject();
            data.put("list",dataList);
            resultInfo.quickSet("查询Demo",data,true);
        }else {
            resultInfo.quickSet("查询Demo",null,false);
        }

        return resultInfo;
    }
}

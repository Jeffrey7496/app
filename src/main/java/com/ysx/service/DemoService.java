package com.ysx.service;

import com.alibaba.fastjson.JSONObject;
import com.ysx.dao.DemoMapper;
import com.ysx.dto.Demo;
import com.ysx.dto.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private DemoMapper mapper;
    public ResultInfo save(JSONObject param) {// 增加或修改
        ResultInfo<JSONObject> resultInfo = new ResultInfo<>();//默认 失败
        String id = param.getString("id");
        String name = param.getString("name");
        Demo demo = new Demo();
        demo.setId(id);
        demo.setName(name);
        //根据id 判断 新增还是修改
        if (StringUtils.isEmpty(param.getString("id"))){//没有id 则是新增
            boolean b = mapper.insert(demo);
            return resultInfo.quickSet("新增Demo",b);
        }else {//修改
            boolean b = mapper.update(demo);
            return resultInfo.quickSet("修改Demo",b);
        }
    }

    public ResultInfo getOne(JSONObject param) {
        ResultInfo<JSONObject> resultInfo = new ResultInfo<>();//默认 失败
        JSONObject jsonObject = mapper.getOne(param);
        if (jsonObject!=null){
            resultInfo.quickSet("查询Demo",jsonObject,true);
        }else {
            resultInfo.quickSet("查询Demo",null,false);
        }
        return resultInfo;
    }

    public ResultInfo<JSONObject> delete(JSONObject param) {
        ResultInfo<JSONObject> resultInfo = new ResultInfo<>();//默认 失败
        boolean b = mapper.delete(param);
        resultInfo.quickSet("删除Demo",b);
        return resultInfo;
    }
}

package com.ysx.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ysx.constants.Constants;
import com.ysx.dto.ResultInfo;
import com.ysx.service.DemoService;
import com.ysx.util.CommonUtils;
import com.ysx.util.JsonCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/5/31 10:48
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoService service;
    private Logger logger = LoggerFactory.getLogger(DemoController.class);
    @PostMapping("/insert")
    public ResultInfo<JSONObject> insert(@RequestBody JSONObject param){
        ResultInfo<JSONObject> resultInfo;
        String uuid = CommonUtils.getUUID();
        logger.info("start====UUID:[{}],functionDesc:[{}],params:[{}]",uuid,"增加demo",JSON.toJSONString(param));
        // 参数检测
        resultInfo = JsonCheckUtils.checkEmpty(param,"name");// 空值检测--返回错误信息，如果没有，则没有错误
        if (Constants.Code.FAIL_CODE.equals(resultInfo.getRespCode())){// 如果有错误信息，则范返回
            return resultInfo;
        }
        resultInfo =  service.save(param);
        logger.info("end====UUID:[{}],functionDesc:[{}],result:[{}]",uuid,"增加demo",JSON.toJSONString(resultInfo));
        return resultInfo;
    }
    @PostMapping("/delete")
    public ResultInfo<JSONObject> delete(@RequestBody JSONObject param){
        ResultInfo<JSONObject> resultInfo;
        String uuid = CommonUtils.getUUID();
        logger.info("start====UUID:[{}],functionDesc:[{}],params:[{}]",uuid,"删除demo",JSON.toJSONString(param));
        // 参数检测
        resultInfo = JsonCheckUtils.checkEmpty(param,"id");// 空值检测--返回错误信息，如果没有，则没有错误
        if (Constants.Code.FAIL_CODE.equals(resultInfo.getRespCode())){// 如果有错误信息，则范返回
            return resultInfo;
        }
        resultInfo =  service.delete(param);
        logger.info("end====UUID:[{}],functionDesc:[{}],result:[{}]",uuid,"增加demo",JSON.toJSONString(resultInfo));
        return resultInfo;
    }
    @PostMapping("/edit")
    public ResultInfo<JSONObject> edit(@RequestBody JSONObject param){
        ResultInfo<JSONObject> resultInfo;
        String uuid = CommonUtils.getUUID();
        logger.info("start====UUID:[{}],functionDesc:[{}],params:[{}]",uuid,"修改demo",JSON.toJSONString(param));
        // 参数检测
        resultInfo = JsonCheckUtils.checkEmpty(param,"id");// 空值检测--返回错误信息，如果没有，则没有错误
        if (Constants.Code.FAIL_CODE.equals(resultInfo.getRespCode())){// 如果有错误信息，则范返回
            return resultInfo;
        }
        resultInfo = service.save(param);
        logger.info("end====UUID:[{}],functionDesc:[{}],result:[{}]",uuid,"修改demo",JSON.toJSONString(resultInfo));
        return resultInfo;
    }
    @GetMapping("/info")
    public ResultInfo<JSONObject> info(@RequestParam("id") String id){
        ResultInfo<JSONObject> resultInfo;
        String uuid = CommonUtils.getUUID();
        JSONObject param = new JSONObject();
        param.put("id",id);
        logger.info("start====UUID:[{}],functionDesc:[{}],param:[{}]",uuid,"查询demo",JSON.toJSONString(param));
        resultInfo = service.info(param);
        logger.info("end====UUID:[{}],functionDesc:[{}],result:[{}]",uuid,"查询demo",JSON.toJSONString(resultInfo));
        return resultInfo;
    }
    @GetMapping("/list")
    public ResultInfo<JSONObject> list(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize){
        ResultInfo<JSONObject> resultInfo;
        String uuid = CommonUtils.getUUID();
        JSONObject param = new JSONObject();
        param.put("pageNum",pageNum);
        param.put("pageSize",pageSize);
        logger.info("start====UUID:[{}],functionDesc:[{}],param:[{}]",uuid,"查询demo",JSON.toJSONString(param));
        resultInfo = service.list(pageNum,pageSize);
        logger.info("end====UUID:[{}],functionDesc:[{}],result:[{}]",uuid,"查询demo",JSON.toJSONString(resultInfo));
        return resultInfo;
    }    @GetMapping("/listAll")
    public ResultInfo<JSONObject> listAll(){
        ResultInfo<JSONObject> resultInfo;
        String uuid = CommonUtils.getUUID();
        JSONObject param = new JSONObject();
        logger.info("start====UUID:[{}],functionDesc:[{}],param:[{}]",uuid,"查询demo",JSON.toJSONString(param));
        resultInfo = service.listAll();
        logger.info("end====UUID:[{}],functionDesc:[{}],result:[{}]",uuid,"查询demo",JSON.toJSONString(resultInfo));
        return resultInfo;
    }
}

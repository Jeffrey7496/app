package com.ysx.util;

import com.alibaba.fastjson.JSONObject;
import com.ysx.constants.Constants;
import com.ysx.dto.ResultInfo;
import org.springframework.util.StringUtils;

/**
 * ali JSONObject 检测类
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/13 16:21
 */
public class JsonCheckUtils {
    /**
     * 检查对应key是否为空，则返回空的信息，如果都不是空，则返回null
     * json key-value必须都是字符类型
     * @param jsonObject
     * @param keys
     * @return
     */
    public static ResultInfo checkEmpty(JSONObject jsonObject, String... keys){
        ResultInfo resultInfo = new ResultInfo();
        for (String key:keys) {
            if(StringUtils.isEmpty(jsonObject.getString(key))){
                resultInfo.setRespMsg(key+"不能为空");
                return resultInfo;
            }
        }
        resultInfo.setRespCode(Constants.Code.SUCCESS_CODE);
        resultInfo.setRespMsg(Constants.Msg.SUCCESS_MSG);
        return resultInfo;
    }
}

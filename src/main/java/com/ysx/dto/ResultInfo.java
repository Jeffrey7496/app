package com.ysx.dto;

import com.ysx.constants.Constants;

/**
 * 返回包装类
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/6/13 15:40
 */
public class ResultInfo<T> {
    private String respCode = Constants.Code.FAIL_CODE;
    private String respMsg = Constants.Msg.FAIL_MSG;
    private String reqUrl = "";
    private T info;

    public ResultInfo() {
    }

    public ResultInfo(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    /**
     * 便捷写法
     * @param msg
     * @param success
     */
    public ResultInfo quickSet(String msg,boolean success){
        if (success){
            this.respCode=Constants.Code.SUCCESS_CODE;
            this.respMsg=msg+Constants.Msg.SUCCESS_MSG;
        }else {
            this.respCode=Constants.Code.FAIL_CODE;
            this.respMsg=msg+Constants.Msg.FAIL_MSG;
        }
        return this;
    }
    /**
     * 便捷写法
     * @param msg
     * @param success
     */
    public ResultInfo quickSet(String msg,T info,boolean success){
        if (success){
            this.respCode=Constants.Code.SUCCESS_CODE;
            this.respMsg=msg+Constants.Msg.SUCCESS_MSG;
        }else {
            this.respCode=Constants.Code.FAIL_CODE;
            this.respMsg=msg+Constants.Msg.FAIL_MSG;
        }
        this.info = info;
        return this;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "respCode='" + respCode + '\'' +
                ", respMsg='" + respMsg + '\'' +
                ", reqUrl='" + reqUrl + '\'' +
                ", info=" + info +
                '}';
    }
}

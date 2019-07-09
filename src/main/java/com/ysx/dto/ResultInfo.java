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
    private String respCode = Constants.Code.FAIL_SYSTEM_CODE;// 默认系统错误
    private String respMsg = Constants.Msg.FAIL_SYSTEM_MSG;
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

    /*
    * 应该再加一种情况：
    * 正常业务中止/系统异常
    */

    /**
     * 便捷写法--成功
     * @param msg
     */
    public void success(String msg){
        this.quickSet(msg,null,true,false);
    }
    /**
     * 便捷写法--成功
     * @param msg
     * @param info
     */
    public void success(String msg, T info){
        this.quickSet(msg,info,true,false);// 成功后面参数无用
    }

    /**
     * 便捷写法--失败：开发人员解决
     * @param msg
     */
    public void failOfSystem(String msg){
        this.quickSet(msg,null,false,false);
    }
    /**
     * 便捷写法--失败：展示给客户
     * @param msg
     */
    public void failOfBusiness(String msg){
        this.quickSet(msg,null,false,true);
    }


    private void quickSet(String msg,T info,boolean success,boolean businessFail){
        if (success){// 成功
            this.respCode=Constants.Code.SUCCESS_CODE;
            this.respMsg=msg+Constants.Msg.SUCCESS_MSG;
        }else {// 失败
            if(businessFail){// 正常业务终止--比如校验不通过
                this.respCode=Constants.Code.FAIL_BUSINESS_CODE;
                this.respMsg=msg;
            }else {// 异常退出
                this.respCode=Constants.Code.FAIL_SYSTEM_CODE;
                this.respMsg=Constants.Msg.FAIL_SYSTEM_MSG;
            }
        }
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

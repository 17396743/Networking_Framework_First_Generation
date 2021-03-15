package com.example.myapplication20.interceptor.beans;

import com.google.gson.JsonElement;

/**
 * 项目名： My Application20
 * 包名：   com.example.myapplication20.beans
 * 文件名： BaseResponse
 * 创建者： 小狼不是哈士奇
 * B站：    幻雨之秋
 * 个人主页: https://hyzqacg.github.io/
 * 创建时间：2021/3/12 15:02
 * 描述：TODO
 */
public class BaseResponse {
    private int errorCode;
    private String errorMsg;
    private JsonElement data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}

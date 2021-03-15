package com.example.myapplication20.interceptor;

import com.example.myapplication20.beans.BaseResponse;
import com.example.myhttplibrary.callback.BaseCallback;
import com.google.gson.Gson;

/**
 * 项目名： My Application20
 * 包名：   com.example.myapplication20.interceptor
 * 文件名： HttpCallback
 * 创建者： 小狼不是哈士奇
 * B站：    幻雨之秋
 * 个人主页: https://hyzqacg.github.io/
 * 创建时间：2021/3/12 11:45
 * 描述：TODO
 */
public abstract class HttpCallback<T> extends BaseCallback<T> {

    BaseResponse baseResponse = null;

    @Override
    protected T onConvert(String jsonElement) {
        T t = null;
        baseResponse = new Gson().fromJson(jsonElement, BaseResponse.class);
        if (baseResponse!= null && isSucessfull()){
            t = convert(baseResponse.getData());
        }else {
            onFail(baseResponse.getErrorMsg(),baseResponse.getErrorCode());
        }
        return t;
    }

    @Override
    protected boolean isSucessfull() {
        return baseResponse.getErrorCode()==0;
    }
}


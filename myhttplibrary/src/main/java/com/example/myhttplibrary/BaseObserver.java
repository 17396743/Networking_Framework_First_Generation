package com.example.myhttplibrary;

import com.example.myhttplibrary.exception.ApiException;
import com.example.myhttplibrary.exception.ExceptionEngine;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 项目名： My Application20
 * 包名：   com.example.myhttplibrary.callback
 * 文件名： BaseObserver
 * 创建者： 小狼不是哈士奇
 * B站：    幻雨之秋
 * 个人主页: https://hyzqacg.github.io/
 * 创建时间：2021/3/12 11:47
 * 描述：TODO
 */
public abstract class BaseObserver implements Observer {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        if(e instanceof ApiException){
            ApiException apiException= (ApiException) e;
            onFail(apiException.getMsg(),apiException.getCode());
        }else {
            onFail("未知异常", ExceptionEngine.UN_KNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {

    }
    protected abstract void onFail(String error,int code);
}

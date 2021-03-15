package com.example.myhttplibrary.callback;

import com.google.gson.JsonElement;


/**
 * 项目名： My Application20
 * 包名：   com.example.myhttplibrary.callback
 * 文件名： BaseCallback
 * 创建者： 小狼不是哈士奇
 * B站：    幻雨之秋
 * 个人主页: https://hyzqacg.github.io/
 * 创建时间：2021/3/12 14:37
 * 描述：TODO
 */
public abstract class BaseCallback<T> extends BaseObserver {

    @Override
    public void onNext(Object o) {
        T t = onConvert(o.toString());
        if (t!=null){
            onSucess(t);
        }
    }
    protected abstract T onConvert(String jsonElement);

    public abstract T convert(JsonElement jsonElement);

    protected abstract void onSucess(T t);

    protected abstract boolean isSucessfull();

}

package com.example.myapplication20;

import android.app.Application;
import android.util.Log;

import com.example.myhttplibrary.HttpGlobleConfig;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 项目名： My Application20
 * 包名：   com.example.myapplication20
 * 文件名： MyApplication
 * 创建者： 小狼不是哈士奇
 * B站：    幻雨之秋
 * 个人主页: https://hyzqacg.github.io/
 * 创建时间：2021/3/12 15:35
 * 描述：TODO
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("liangxq", "log: "+message );
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpGlobleConfig.getInstance()
                .addInterceptors(httpLoggingInterceptor);

    }
}

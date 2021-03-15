package com.example.myapplication20;

import android.os.Bundle;
import android.util.Log;

import com.example.myapplication20.beans.Bean;
import com.example.myhttplibrary.client.HttpClient;
import com.example.myhttplibrary.http.HttPManager;
import com.example.myhttplibrary.http.HttpServer;
import com.example.myapplication20.interceptor.HttpCallback;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttPManager.getInstance().createServer("https://wanandroid.com/",5, TimeUnit.SECONDS, HttpServer.class).get("article/listproject/0/json",new HashMap<String,Object>(),new HashMap<String,Object>())
                .subscribeOn(Schedulers.io())
                .map(new Function<JsonElement, Object>() {

                    @Override
                    public Object apply(@NonNull JsonElement jsonElement) throws Exception {
                        return new Gson().toJson(jsonElement);
                    }
                }).compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Bean>() {
                    @Override
                    protected void onFail(String error, int code) {

                    }

                    @Override
                    public Bean convert(JsonElement jsonElement) {
                        return new Gson().fromJson(jsonElement,Bean.class);
                    }

                    @Override
                    protected void onSucess(Bean bean) {
                        Log.e("liangxq", "onSucess: " + bean.getDatas());
                    }
                });

        new HttpClient.Builder()
                .setLifecycleProvider(this)
                .setBaseUrl("https://wanandroid.com/")
                .setApiUrl("article/listproject/0/json")
                .get()
                .build().excute(new HttpCallback<Bean>() {
            @Override
            protected void onFail(String error, int code) {
                Log.e("liangxq", "onFail: 框架"+error );
            }

            @Override
            public Bean convert(JsonElement jsonElement) {
                return new Gson().fromJson(jsonElement, Bean.class);
            }

            @Override
            protected void onSucess(Bean bean) {
                Log.e("liangxq", "onSucess:框架 " + bean.getDatas());
            }
        });
    }
}
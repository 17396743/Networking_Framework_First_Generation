package com.example.myhttplibrary.client;

import android.text.TextUtils;


import com.example.myhttplibrary.Constans;
import com.example.myhttplibrary.HttpGlobleConfig;
import com.example.myhttplibrary.Method;
import com.example.myhttplibrary.callback.BaseCallback;
import com.example.myhttplibrary.http.HttPManager;
import com.example.myhttplibrary.http.HttpServer;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 项目名： My Application20
 * 包名：   com.example.myhttplibrary.client
 * 文件名： HttpClient
 * 创建者： 小狼不是哈士奇
 * B站：    幻雨之秋
 * 个人主页: https://hyzqacg.github.io/
 * 创建时间：2021/3/12 14:29
 * 描述：TODO
 */
public class HttpClient {
    //请求方式
    Method method;
    //请求参数
    Map<String, Object> paramser;
    //请求头信息
    Map<String, Object> headres;
    //Rxjava绑定生命周期
    LifecycleProvider lifecycleProvider;
    //绑定Activity具体的生命周的
    ActivityEvent activityEvent;
    //绑定Fragment的具体的生命周期的
    FragmentEvent fragmentEvent;
    String baseUrl;
    //拼接的url
    String apiUrl;
    //是否是json上传表示
    boolean isJson;
    //json字符串
    String jsonbody;
    //超时时间
    long time;
    //时间单位
    TimeUnit timeUnit;
    //回调接口
    BaseCallback baseCallBack;


    public HttpClient(Builder builder) {
        this.paramser = builder.paramser;
        this.headres = builder.headres;
        this.lifecycleProvider = builder.lifecycleProvider;
        this.activityEvent = builder.activityEvent;
        this.fragmentEvent = builder.fragmentEvent;
        this.baseUrl = builder.baseUrl;
        this.apiUrl = builder.apiUrl;
        this.isJson = builder.isJson;
        this.jsonbody = builder.jsonbody;
        this.time = builder.time;
        this.timeUnit = builder.timeUnit;
        this.method=builder.method;
    }

    public HttpClient() {
        this(new Builder());
    }


    public void excute(BaseCallback baseCallBack) {
        if (baseCallBack == null) {
            new RuntimeException("callback not  null");
        }
        this.baseCallBack = baseCallBack;
        buildParams();
        buildHeaders();
        request();
    }


    //构建参数
    private void buildParams() {
        if (HttpGlobleConfig.getInstance().getBaseparams() != null) {
            this.paramser.putAll(HttpGlobleConfig.getInstance().getBaseparams());
        }
    }

    //构建请求头
    public void buildHeaders() {
        if (HttpGlobleConfig.getInstance().getBaseHeades() != null) {
            this.headres.putAll(HttpGlobleConfig.getInstance().getBaseHeades());
        }
    }

    //发起请求
    private void request() {
        if (this.baseUrl == null) {
            this.baseUrl = HttpGlobleConfig.getInstance().getBaseUrl();
        }
        if (this.baseUrl == null) {
            new RuntimeException("baseUrl not null");
        }
        HttpServer server = HttPManager.getInstance().createServer(baseUrl, this.time, this.timeUnit, HttpServer.class);
        Observable observable = createObservable(server);
        HttpObservable httpObservable=new HttpObservable.Buidler(observable)
                .setActivityEvent(activityEvent)
                .setLifecycleProvider(lifecycleProvider)
                .setFragmentEvent(fragmentEvent)
                .build();
        httpObservable.changeThread().subscribe(baseCallBack);

    }

    private Observable createObservable(HttpServer server) {
        boolean hasBodyString = !TextUtils.isEmpty(jsonbody);
        RequestBody requestBody = null;
        if (hasBodyString) {
            String mediaType = isJson ? "application/json; charset=utf-8" : "text/plain;charset=utf-8";
            requestBody = RequestBody.create(okhttp3.MediaType.parse(mediaType), jsonbody);
        }
        Observable observable = null;
        switch (this.method) {
            case GET:
                observable = server.get(apiUrl, paramser, headres);
                break;
            case POST:
                if (isJson) {
                    observable = server.postjson(apiUrl, requestBody, headres);
                } else {
                    observable = server.post(apiUrl, paramser, headres);
                }
                break;

        }
        return observable;
    }

    public static class Builder {
        //请求方式
        Method method;
        //请求参数
        Map<String, Object> paramser;
        //请求头信息
        Map<String, Object> headres;
        //Rxjava绑定生命周期
        LifecycleProvider lifecycleProvider;
        //绑定Activity具体的生命周的
        ActivityEvent activityEvent;
        //绑定Fragment的具体的生命周期的
        FragmentEvent fragmentEvent;
        String baseUrl;
        //拼接的url
        String apiUrl;
        //是否是json上传表示
        boolean isJson;
        //json字符串
        String jsonbody;
        //超时时间
        long time;
        //时间单位
        TimeUnit timeUnit;
        //回调接口
        BaseCallback baseCallBack;

        public Builder() {
            this.paramser = new HashMap<>();
            this.headres = new HashMap<>();
            this.time = Constans.TIME_OUT;
            this.timeUnit = Constans.TIME_UNIT;
        }


        public Builder get() {
            this.method = Method.GET;
            return this;
        }

        public Builder post() {
            this.method = Method.POST;
            return this;
        }

        public Builder delete() {
            this.method = Method.DELETE;
            return this;
        }

        public Builder put() {
            this.method = Method.PUT;
            return this;
        }

        public Builder setParamser(Map<String, Object> paramser) {
            this.paramser.putAll(paramser);
            return this;
        }

        public Builder setHeadres(Map<String, Object> headres) {
            this.headres.putAll(headres);
            return this;
        }

        public Builder setLifecycleProvider(LifecycleProvider lifecycleProvider) {
            this.lifecycleProvider = lifecycleProvider;
            return this;
        }

        public Builder setActivityEvent(ActivityEvent activityEvent) {
            this.activityEvent = activityEvent;
            return this;
        }

        public Builder setFragmentEvent(FragmentEvent fragmentEvent) {
            this.fragmentEvent = fragmentEvent;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
            return this;
        }

        public Builder setTime(long time) {
            this.time = time;
            return this;
        }

        public Builder setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder setJson(boolean isJson, String jsonbody) {
            this.isJson = isJson;
            this.jsonbody = jsonbody;
            return this;
        }

        public HttpClient build() {
            return new HttpClient(this);
        }
    }

}

package com.example.myhttplibrary;

import android.content.Context;
import android.os.Looper;
import android.os.Handler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;

/**
 * 项目名： My Application20
 * 包名：   com.example.myapplication20
 * 文件名： HttpGlobleConfig
 * 创建者： 小狼不是哈士奇
 * B站：    幻雨之秋
 * 个人主页: https://hyzqacg.github.io/
 * 创建时间：2021/3/12 10:52
 * 描述：TODO
 */
public class HttpGlobleConfig {
    //baseUrl
    private String baseUrl;
    private long timeout;
    private TimeUnit timeUnit;
    //公共请求参数
    private Map<String, Object> baseparams;
    //公共的请求头信息
    private Map<String, Object> baseHeades;
    //公共的拦截器
    private List<Interceptor> interceptors;
    //日志开关
    private boolean isShowLog;

    private Context context;

    private Handler handler;

    //存储各种appkey的集合
    private Map<String, String> appkeys;

    private static class HttpGlobleConfigHodle {
        private static HttpGlobleConfig INSTANCE = new HttpGlobleConfig();
    }

    public static HttpGlobleConfig getInstance() {
        return HttpGlobleConfigHodle.INSTANCE;
    }


    public HttpGlobleConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public HttpGlobleConfig setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public HttpGlobleConfig setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public HttpGlobleConfig setBaseparams(Map<String, Object> baseparams) {
        this.baseparams = baseparams;
        return this;
    }

    public HttpGlobleConfig addParams(String key, Object value) {
        if (this.baseparams == null) {
            this.baseparams = new HashMap<>();
        }
        this.baseparams.put(key, value);
        return this;
    }

    public HttpGlobleConfig setBaseHeades(Map<String, Object> baseHeades) {
        this.baseHeades = baseHeades;
        return this;
    }

    public HttpGlobleConfig addheads(String key, Object value) {
        if (this.baseHeades == null) {
            this.baseHeades = new HashMap<>();
        }
        this.baseHeades.put(key, value);
        return this;
    }

    public HttpGlobleConfig setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }

    public HttpGlobleConfig addInterceptors(Interceptor interceptors) {
        if (this.interceptors == null) {
            this.interceptors = new ArrayList<>();
        }
        this.interceptors.add(interceptors);
        return this;
    }

    public HttpGlobleConfig setShowLog(boolean showLog) {
        isShowLog = showLog;
        return this;
    }

    public HttpGlobleConfig setContext(Context context) {
        this.context = context;
        return this;
    }

    public HttpGlobleConfig setHandler(Handler handler) {
        this.handler = new Handler(Looper.getMainLooper());
        return this;
    }

    public HttpGlobleConfig setAppkeys(Map<String, String> appkeys) {
        this.appkeys = appkeys;
        return this;
    }

    public HttpGlobleConfig setAppkey(String key, String value) {
        if (this.appkeys == null) {
            this.appkeys = new HashMap<>();
        }
        this.appkeys.put(key, value);
        return this;
    }


    public String getValue(String key) {
        if (key != null && appkeys != null) {
            if (appkeys.get(key)==null) {
                new RuntimeException("key not find value");
            }
        }
        return appkeys.get(key);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public long getTimeout() {
        return timeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public Map<String, Object> getBaseparams() {
        return baseparams;
    }

    public Map<String, Object> getBaseHeades() {
        return baseHeades;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public boolean isShowLog() {
        return isShowLog;
    }

    public Context getContext() {
        return context;
    }

    public Handler getHandler() {
        return handler;
    }

    public Map<String, String> getAppkeys() {
        return appkeys;
    }



}

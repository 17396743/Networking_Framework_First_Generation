package com.example.myhttplibrary.http;

import com.example.myhttplibrary.HttpGlobleConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 小狼不是哈士奇
 * @date 2021/3/12 10:32
 * @QQ 1481583730
 */
public class HttPManager {
    private static volatile HttPManager httPManager;

    public static HttPManager getInstance(){
        if (httPManager == null){
            synchronized (HttPManager.class){
                if (httPManager == null){
                    httPManager = new HttPManager();
                }
            }
        }
        return httPManager;
    }

    private Retrofit getRetrofit(String baseurl, long timeout, TimeUnit timeUnit){
        return new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkhttpClient(timeout,timeUnit))
                .build();
    }

    private OkHttpClient getOkhttpClient(long timeout, TimeUnit timeUnit) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(timeout,timeUnit);
        builder.readTimeout(timeout,timeUnit);
        builder.writeTimeout(timeout,timeUnit);
        builder.retryOnConnectionFailure(true);
        if (HttpGlobleConfig.getInstance().getInterceptors() != null){
            for (Interceptor interceptor : HttpGlobleConfig.getInstance().getInterceptors()){
                builder.addInterceptor(interceptor);
            }
        }
        return builder.build();
    }

    public  <T> T createServer(String baseurl , long timeout, TimeUnit timeUnit , Class<T> mClass){
        return getRetrofit(baseurl,timeout,timeUnit).create(mClass);
    }
}

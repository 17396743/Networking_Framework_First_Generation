package com.example.myhttplibrary.client;


import com.example.myhttplibrary.exception.ExceptionEngine;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * 项目名： My Application20
 * 包名：   com.example.myhttplibrary.client
 * 文件名： HttpObservable
 * 创建者： 小狼不是哈士奇
 * B站：    幻雨之秋
 * 个人主页: https://hyzqacg.github.io/
 * 创建时间：2021/3/12 15:23
 * 描述：TODO
 */
public class HttpObservable {
    //Rxjava绑定生命周期
    LifecycleProvider lifecycleProvider;
    //绑定Activity具体的生命周的
    ActivityEvent activityEvent;
    //绑定Fragment的具体的生命周期的
    FragmentEvent fragmentEvent;
    Observable observable;

    public HttpObservable(Buidler buidler) {
        this.lifecycleProvider = buidler.lifecycleProvider;
        this.activityEvent = buidler.activityEvent;
        this.fragmentEvent = buidler.fragmentEvent;
        this.observable = buidler.observable;
    }

    public static class Buidler {
        LifecycleProvider lifecycleProvider;
        ActivityEvent activityEvent;
        FragmentEvent fragmentEvent;
        Observable observable;

        public Buidler(Observable observable) {
            this.observable = observable;
        }

        public Buidler setLifecycleProvider(LifecycleProvider lifecycleProvider) {
            this.lifecycleProvider = lifecycleProvider;
            return this;
        }

        public Buidler setActivityEvent(ActivityEvent activityEvent) {
            this.activityEvent = activityEvent;
            return this;
        }

        public Buidler setFragmentEvent(FragmentEvent fragmentEvent) {
            this.fragmentEvent = fragmentEvent;
            return this;
        }


        public HttpObservable build() {
            return new HttpObservable(this);
        }
    }

    Observable map() {
        return observable.map(new Function<JsonElement, Object>() {
            @Override
            public Object apply(JsonElement jsonElement) throws Exception {
                return new Gson().toJson(jsonElement);
            }
        });
    }

    Observable onErrorResumeNext() {
        return map().onErrorResumeNext(new Function<Throwable, ObservableSource>() {
            @Override
            public ObservableSource apply(Throwable throwable) throws Exception {
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });
    }

    Observable bindToLifecycle() {
        Observable observable = null;
        if (lifecycleProvider != null) {
            if (activityEvent != null || fragmentEvent != null) {
                if (activityEvent != null && fragmentEvent != null) {
                    observable = onErrorResumeNext().compose(lifecycleProvider.bindUntilEvent(activityEvent));
                }

                if (activityEvent != null) {
                    observable = onErrorResumeNext().compose(lifecycleProvider.bindUntilEvent(activityEvent));
                }

                if (fragmentEvent != null) {
                    observable = onErrorResumeNext().compose(lifecycleProvider.bindUntilEvent(fragmentEvent));
                }
            }else{
                observable=onErrorResumeNext().compose(lifecycleProvider.bindToLifecycle());
            }
        } else {
            observable = onErrorResumeNext();
        }
        return observable;
    }

    Observable changeThread() {
        return bindToLifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

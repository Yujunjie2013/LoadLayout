package com.junjie.loadinglayout;

import android.app.Application;

import com.junjie.loading.base.BaseLoadView;
import com.junjie.loading.base.DefaultErrorLoad;
import com.junjie.loading.base.DefaultLoading;
import com.junjie.loading.core.LoadView;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoadView.newInstance()
                .bindView(new DefaultErrorLoad())
                .bindView(new DefaultLoading(false))
                .bindDefaultView(DefaultLoading.class);
    }
}

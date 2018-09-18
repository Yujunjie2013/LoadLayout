package com.junjie.loading.core;

import android.support.annotation.NonNull;

import com.junjie.loading.base.BaseLoadView;
import com.junjie.loading.base.DefaultErrorLoad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadView {
    private static volatile LoadView loadState;
    private Class<? extends BaseLoadView> defaultView;
    private HashMap<Class<? extends BaseLoadView>, BaseLoadView> map;

    public static LoadView newInstance() {
        synchronized (LoadView.class) {
            if (loadState == null) {
                loadState = new LoadView();
            }
            return loadState;
        }
    }

    public HashMap<Class<? extends BaseLoadView>, BaseLoadView> getLoadViewList() {
        return map;
    }

    public Class<? extends BaseLoadView> getDefaultView() {
        return defaultView;
    }

    private LoadView() {
        map = new HashMap<>();
    }

    //绑定所有状态的View
    public LoadView bindView(@NonNull BaseLoadView loadView) {
        map.put(loadView.getClass(), loadView);
        return this;
    }

    //默认显示的View
    public LoadView bindDefaultView(@NonNull Class<? extends BaseLoadView> defaultClass) {
        this.defaultView = defaultClass;
        return this;
    }
}

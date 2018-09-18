package com.junjie.loading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.junjie.loading.base.BaseLoadView;
import com.junjie.loading.base.DefaultContentView;
import com.junjie.loading.base.DefaultErrorLoad;
import com.junjie.loading.base.DefaultLoading;
import com.junjie.loading.core.ErrorClickListener;
import com.junjie.loading.core.LoadLayout;
import com.junjie.loading.core.LoadView;
import com.junjie.loading.util.LoadUtil;

import java.util.HashMap;
import java.util.List;

public class LoadManager {

    private final LoadLayout loadLayout;

    private LoadManager(ViewParam viewParam, ErrorClickListener clickListener, boolean isCancel) {
        ViewGroup rootParentView = viewParam.rootParentView;
        Context context = viewParam.context;
        View rootView = viewParam.rootView;
        loadLayout = new LoadLayout(context);
        loadLayout.setContenView(new DefaultContentView(rootView, context));
        if (rootParentView != null) {
            //这里只有是Activity的时候才不会为null
            rootParentView.addView(loadLayout, 0, rootView.getLayoutParams());
        }
        initBindView(loadLayout, clickListener);
    }

    private void initBindView(LoadLayout loadLayout, ErrorClickListener clickListener) {
        Class<? extends BaseLoadView> defaultView = LoadView.newInstance().getDefaultView();
        HashMap<Class<? extends BaseLoadView>, BaseLoadView> loadViewList = LoadView.newInstance().getLoadViewList();
        //将初始化绑定的所有View添加到LoadLayout中
        if (loadViewList != null && loadViewList.size() != 0) {
            for (Class cls : loadViewList.keySet()) {
                BaseLoadView loadView = loadViewList.get(cls);
                loadLayout.addLoadView(loadView, clickListener);
            }
        }
        if (defaultView != null) {
            showView(defaultView);
        }
    }

    public LoadLayout getLoadLayout() {
        return loadLayout;
    }

    public void showContent() {
        loadLayout.showView(DefaultContentView.class);
    }

    public void showDefaultError() {
        loadLayout.showView(DefaultErrorLoad.class);
    }

    public void showView(Class<? extends BaseLoadView> showView) {
        showView(showView, null);
    }

    public void showView(Class<? extends BaseLoadView> showView, Object tag) {
        loadLayout.showView(showView, tag);
    }

    public void showDefaultLoading() {
        loadLayout.showView(DefaultLoading.class);
    }

    public static class Builder {
        private ViewParam viewParam;
        private ErrorClickListener listener;
        private boolean isCancel;

        public Builder setRootView(@NonNull Object obj) {
            viewParam = LoadUtil.getViewParams(obj);
            return this;
        }

        public Builder setOnClick(ErrorClickListener listener) {
            this.listener = listener;
            return this;
        }

        //是否可以取消加载
        public Builder setCancelLoad(boolean isCancel) {
            this.isCancel = isCancel;
            return this;
        }

        public LoadManager build() {
            return new LoadManager(viewParam, listener, isCancel);
        }
    }

    public static class ViewParam {
        private View rootView;
        private Context context;
        private ViewGroup rootParentView;

        public ViewParam(View rootView, Context context, ViewGroup rootParentView) {
            this.rootView = rootView;
            this.context = context;
            this.rootParentView = rootParentView;
        }
    }

}

package com.junjie.loading.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import com.junjie.loading.core.ErrorClickListener;

public abstract class BaseLoadView {
    //Whether it can be clicked, the default can be clicked
    private boolean isClickEnable = true;
    private View rootView;
    private Context context;

    public BaseLoadView() {
    }

    public BaseLoadView(boolean isClickEnable) {
        this.isClickEnable = isClickEnable;
    }

    public BaseLoadView(View rootView, Context context) {
        this.rootView = rootView;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setStateView(Context context, ErrorClickListener clickListener) {
        this.context = context;
        onCreateView(clickListener);
    }

    private View onCreateView(final ErrorClickListener clickListener) {
        int res = getLayoutId();
        if (res == 0 && rootView != null) {
            return rootView;
        }
        if (rootView == null) {
            rootView = LayoutInflater.from(context).inflate(res, null);
        }
        if (isClickEnable && clickListener != null) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(v);
                }
            });
        }
        onViewCreate(context, rootView);
        return rootView;
    }

    public View getRootView(Object tag) {
        if (rootView != null) {
            if (tag != null) {
                rootView.setTag(tag);
            }
            return rootView;
        }
        rootView = LayoutInflater.from(context).inflate(getLayoutId(), null);
        if (tag != null) {
            rootView.setTag(tag);
        }
        onViewCreate(context, rootView);
        return rootView;
    }

    public View getRootView() {
        return getRootView(null);
    }

    public void show() {
    }

    public void hide() {
    }
    /**
     * 1Layout id
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 2findViewById；setText;...
     *
     * @param context
     * @param rootView
     */
    protected abstract void onViewCreate(Context context, View rootView);

    /**
     * 3
     *
     * @param context
     * @param rootView
     */
    public abstract void onAttach(Context context, View rootView);

    /**
     * 4
     */
    public abstract void onDetach();


}

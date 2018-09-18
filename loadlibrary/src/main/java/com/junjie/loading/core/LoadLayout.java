package com.junjie.loading.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.junjie.loading.base.BaseLoadView;
import com.junjie.loading.base.DefaultContentView;
import com.junjie.loading.util.LoadUtil;

import java.util.HashMap;
import java.util.Map;


public class LoadLayout extends FrameLayout {
    private final int STATEVIEW_CUSTOM_INDEX = 1;
    private Map<Class<? extends BaseLoadView>, BaseLoadView> stateViews = new HashMap<>();
    private Class<? extends BaseLoadView> preStateView;
    private Class<? extends BaseLoadView> curStateView;

    public LoadLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContenView(BaseLoadView baseLoadView) {
        addMap(baseLoadView);
        View rootView = baseLoadView.getRootView(null);
        rootView.setVisibility(View.GONE);
        addView(rootView);
        curStateView = DefaultContentView.class;
    }

    public void addLoadView(BaseLoadView loadView, ErrorClickListener clickListener) {
        loadView.setStateView(getContext(), clickListener);
        addMap(loadView);
    }

    private void addMap(BaseLoadView loadView) {
        if (!stateViews.containsKey(loadView.getClass())) {
            stateViews.put(loadView.getClass(), loadView);
        }
    }

    public void showView(Class<? extends BaseLoadView> showView) {
        showView(showView, null);
    }

    public void showView(Class<? extends BaseLoadView> showView, Object tag) {
        if (showView == null) {
            throw new NullPointerException("showView cannot be null");
        }
        checkExist(showView);
        if (LoadUtil.isMainThread()) {
            showStateView(showView, tag);
        } else {
            postMainThread(showView, tag);
        }
    }

    private void postMainThread(final Class<? extends BaseLoadView> showView, final Object tag) {
        post(new Runnable() {
            @Override
            public void run() {
                showStateView(showView, tag);
            }
        });
    }

    private void showStateView(Class<? extends BaseLoadView> status, Object tag) {
        if (preStateView != null) {
            if (curStateView == status) {
                return;
            }
            stateViews.get(preStateView).onDetach();
        }
        if (getChildCount() > 1) {
            removeViewAt(STATEVIEW_CUSTOM_INDEX);
        }

        BaseLoadView showView = stateViews.get(status);
        DefaultContentView defaultContentView = (DefaultContentView) stateViews.get(DefaultContentView.class);
        if (showView == defaultContentView) {
            defaultContentView.show();
        } else {
            defaultContentView.hide();
            addView(showView.getRootView(tag));
            showView.onAttach(getContext(), showView.getRootView(tag));
        }
        preStateView = status;
        curStateView = status;
    }

    private void checkExist(Class<? extends BaseLoadView> showView) {
        if (!stateViews.containsKey(showView)) {
            throw new IllegalArgumentException(String.format("The BaseLoadView (%s) is nonexistent.please BindView", showView.getClass()
                    .getSimpleName()));
        }
    }
}
